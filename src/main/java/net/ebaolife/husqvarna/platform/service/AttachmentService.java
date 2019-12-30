package net.ebaolife.husqvarna.platform.service;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import net.ebaolife.husqvarna.framework.bean.FileUploadBean;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.attachment.*;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.exception.ConnectOpenOfficeException;
import net.ebaolife.husqvarna.framework.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service

/**
 * 
 * @author jiangfeng
 * 
 *         www.jhopesoft.com
 * 
 *         jfok1972@qq.com
 * 
 *         2017-06-01
 * 
 */
public class AttachmentService {

	@Autowired
	private DaoImpl dao;

	@Autowired
	private DataObjectService dataObjectService;

	public void upload(FileUploadBean uploaditem, BindingResult bindingResult)
			throws IllegalAccessException, InvocationTargetException, IOException {

		MultipartFile file = uploaditem.getFile();
		FDataobjectattachment attachment = new FDataobjectattachment();

		BeanUtils.copyProperties(attachment, uploaditem);
		attachment.setFDataobject(DataObjectUtils.getDataObject(uploaditem.getObjectid()));

		if (!ObjectFunctionUtils.allowAddAttachment(attachment.getFDataobject())) {
			throw new RuntimeException("已无权进行附件上传操作");
		}

		if (attachment.getTitle() == null || attachment.getTitle().length() == 0)
			attachment.setTitle(file.getOriginalFilename());
		attachment.setCreater(Local.getUserid());
		attachment.setCreatedate(new Date());
		attachment.setUploaddate(new Date());

		attachment.setFilename(file.getOriginalFilename());
		attachment.setSuffixname(getFileSuffix(attachment.getFilename()));
		attachment.setFilesize(file.getSize());

		dao.save(attachment);

		FAttachmentsetting setting = getAttachmentsetting();
		if (setting.getSaveinfilesystem()) {

			String yyyymm = new SimpleDateFormat("yyyy-MM").format(new Date());
			fileDirExists(setting.getRootpath() + File.separator + yyyymm);
			attachment.setLocalpathname(yyyymm);
			attachment.setLocalfilename(attachment.getAttachmentid());
		}

		String suffix = attachment.getSuffixname();

		if (suffix != null) {
			FAttachmentfiletype filetype = dao.findById(FAttachmentfiletype.class, suffix);
			if (filetype != null) {

				if (filetype.getCanpreview())
					attachment.setOriginalpreviewmode("direct");

				if (setting.getCreatepreviewpdf() && filetype.getCanpdfpreview()) {
					if (setting.getSaveinfilesystem())
						createPdfPreviewFile(attachment, file, setting);
					else
						createPdfPreviewDb(attachment, file);
				}

				if (setting.getCreatepreviewimage() && filetype.getIsimage()) {
					attachment.setOriginalpreviewmode("image");
					createImagePreview(attachment, file);
				}
			}
		}

		if (setting.getSaveinfilesystem()) {

			saveAttachmentToFileSystem(attachment, file, setting);
		} else {

			FDataobjectattachmentfile attachmentfile = new FDataobjectattachmentfile(attachment);
			attachmentfile.setFiledata(file.getBytes());
			dao.save(attachmentfile);
		}
		dao.saveOrUpdate(attachment);
	}

	private void saveAttachmentToFileSystem(FDataobjectattachment attachment, MultipartFile file,
			FAttachmentsetting setting) throws IOException {

		String fullpath = setting.getRootpath() + attachment._getLocalFilename();
		FileOutputStream outputStream = new FileOutputStream(fullpath);
		FileUtils.copy(file.getInputStream(), outputStream);
	}

	private void fileDirExists(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	private void createImagePreview(FDataobjectattachment attachment, MultipartFile file) {
		try {
			Image image = ImageIO.read(file.getInputStream());
			CompressImage(attachment, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createPdfPreviewDb(FDataobjectattachment attachment, MultipartFile file) throws IOException {
		ByteArrayOutputStream pdfos = createPdfPreviewStream(file, attachment.getSuffixname());
		FDataobjectattachmentpdffile pdffile = new FDataobjectattachmentpdffile(attachment);
		pdffile.setFilepdfdata(pdfos.toByteArray());
		dao.save(pdffile);
		attachment.setHaspdfpreviewviewdata(true);
		dao.saveOrUpdate(attachment);
	}

	private void createPdfPreviewFile(FDataobjectattachment attachment, MultipartFile file, FAttachmentsetting setting)
			throws IOException {
		ByteArrayOutputStream pdfos = createPdfPreviewStream(file, attachment.getSuffixname());
		String fullpath = setting.getRootpath() + File.separator + attachment._getLocalPDFFilename();
		File outputFile = new File(fullpath);
		FileUtils.copy(new ByteArrayInputStream(pdfos.toByteArray()), outputFile);
		attachment.setHaspdfpreviewviewdata(true);
		dao.saveOrUpdate(attachment);
	}

	private ByteArrayOutputStream createPdfPreviewStream(MultipartFile file, String suffix) {
		ByteArrayOutputStream pdfos = new ByteArrayOutputStream();
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
		try {
			connection.connect();
		} catch (ConnectException e) {
			throw new ConnectOpenOfficeException();
		}
		DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
		DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
		if (suffix.equalsIgnoreCase("docx"))
			suffix = "doc";
		if (suffix.equalsIgnoreCase("xlsx"))
			suffix = "xls";
		if (suffix.equalsIgnoreCase("pptx"))
			suffix = "ppt";
		DocumentFormat docFormat = formatReg.getFormatByFileExtension(suffix);
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);

		try {
			converter.convert(file.getInputStream(), docFormat, pdfos, pdfFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connection.disconnect();
		return pdfos;
	}

	private int MAXXY = 128;

	public boolean CompressImage(FDataobjectattachment attachment, Image image) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			attachment.setPwidth(width);
			attachment.setPheight(height);
			attachment.setHasimagepreviewdata(true);
			int c_w = MAXXY;
			int c_h = MAXXY * height / width;
			if (height > width) {
				c_h = MAXXY;
				c_w = MAXXY * width / height;
			}
			BufferedImage bufferedImage = new BufferedImage(c_w, c_h, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image.getScaledInstance(c_w, c_h, Image.SCALE_SMOOTH), 0, 0,
					null);
			ImageIO.write(bufferedImage, "png", os);
			attachment.setPreviewdata(os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			attachment.setPwidth(0);
			attachment.setPheight(0);
			attachment.setHasimagepreviewdata(false);
			return false;
		}
		return true;
	}

	public String getFileSuffix(String filename) {
		int pos = filename.lastIndexOf('.');
		if (pos == -1)
			return null;
		else
			return filename.substring(pos + 1);
	}

	public void preview(String attachmentid) throws IOException {
		FDataobjectattachment attachment = dao.findById(FDataobjectattachment.class, attachmentid);

		if (!ObjectFunctionUtils.allowQueryAttachment(attachment.getFDataobject())) {
			throw new RuntimeException("已无权对附件进行查看");
		}

		HttpServletResponse response = Local.getResponse();
		response.setHeader("Cache-Control", "max-age=" + 600);
		response.addHeader("Content-Disposition", "inline");
		if (attachment.getHaspdfpreviewviewdata() != null && attachment.getHaspdfpreviewviewdata()) {
			downloadPdfPreviewData(attachment);
		} else {

			downloadOriginalToPreview(attachment);
		}

	}

	private void downloadOriginalToPreview(FDataobjectattachment attachment) throws IOException {
		InputStream pdfstream = getOriginalFileStream(attachment);
		HttpServletResponse response = Local.getResponse();
		response.addHeader("Content-Length", "" + pdfstream.available());
		String mimetype = getMimeType(attachment.getSuffixname());
		if (mimetype == null)
			mimetype = "application/octet-stream";
		response.setContentType(mimetype + ";charset=gb2312");
		AttachmentUtils.writeStreamToResponse(pdfstream, response);
	}

	private String getMimeType(String suffix) {
		if (suffix == null)
			return null;
		FAttachmentfiletype filetype = dao.findById(FAttachmentfiletype.class, suffix);
		if (filetype == null)
			return null;
		else
			return filetype.getMimetype();
	}

	public InputStream getOriginalFileStream(FDataobjectattachment attachment) {
		FAttachmentsetting setting = getAttachmentsetting();
		InputStream result;
		if (setting.getSaveinfilesystem()) {
			try {
				result = new FileInputStream(
						new File(setting.getRootpath() + File.separator + attachment._getLocalFilename()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return new ByteArrayInputStream(new byte[0]);
			}
		} else {
			FDataobjectattachmentfile ofile = attachment.getFDataobjectattachmentfile();
			if (ofile == null)
				throw new RuntimeException("附件原始文件记录未找到！");
			result = new ByteArrayInputStream(ofile.getFiledata());
		}
		return result;
	}

	private void downloadPdfPreviewData(FDataobjectattachment attachment) throws IOException {
		InputStream pdfstream = getPdfPreviewStream(attachment);
		HttpServletResponse response = Local.getResponse();
		response.addHeader("Content-Length", "" + pdfstream.available());
		response.setContentType("application/pdf;charset=gb2312");
		AttachmentUtils.writeStreamToResponse(pdfstream, response);
	}

	public InputStream getPdfPreviewStream(FDataobjectattachment attachment) throws FileNotFoundException {
		FAttachmentsetting setting = getAttachmentsetting();
		InputStream result;
		if (setting.getSaveinfilesystem()) {
			result = new FileInputStream(
					new File(setting.getRootpath() + File.separator + attachment._getLocalPDFFilename()));
		} else {
			FDataobjectattachmentpdffile pdffile = attachment.getFDataobjectattachmentpdffile();
			if (pdffile == null)
				throw new RuntimeException("转换后的pdf文件记录未找到！");
			result = new ByteArrayInputStream(pdffile.getFilepdfdata());
		}
		return result;
	}

	public void download(String attachmentid) throws UnsupportedEncodingException, IOException {
		FDataobjectattachment attachment = dao.findById(FDataobjectattachment.class, attachmentid);

		if (!ObjectFunctionUtils.allowQueryAttachment(attachment.getFDataobject())) {
			throw new RuntimeException("已无权对附件进行下载");
		}

		HttpServletResponse response = Local.getResponse();
		response.addHeader("Content-Disposition",
				"attachment" + ";filename=" + new String(attachment.getFilename().getBytes("gb2312"), "iso8859-1"));
		response.setContentType("application/octet-stream");
		InputStream pdfstream = getOriginalFileStream(attachment);
		response.addHeader("Content-Length", "" + pdfstream.available());
		AttachmentUtils.writeStreamToResponse(pdfstream, response);
	}

	public void downloadAll(String moduleName, String idkey) throws IOException {
		FDataobject dataobject = DataObjectUtils.getDataObject(moduleName);

		if (!ObjectFunctionUtils.allowQueryAttachment(dataobject)) {
			throw new RuntimeException("已无权对附件进行下载");
		}

		List<FDataobjectattachment> attachments = dao.findByProperty(FDataobjectattachment.class, "objectid",
				dataobject.getObjectid(), "idvalue", idkey);
		Map<String, Object> record = dataObjectService.getObjectRecordMap(moduleName, idkey);
		String recordtitle = record.get(dataobject.getNamefield()).toString();
		OutputStream os = new ByteArrayOutputStream();
		InputStream input = null;
		ZipOutputStream zipOut = new ZipOutputStream(os);

		zipOut.setLevel(Deflater.BEST_COMPRESSION);
		zipOut.setMethod(ZipOutputStream.DEFLATED);
		zipOut.setComment("这是" + dataobject.getTitle() + "\"" + recordtitle + "\"的所有附件的压缩文件");
		Map<String, Integer> filenames = new HashMap<String, Integer>();
		for (FDataobjectattachment attachment : attachments) {
			if (attachment.getFilename() != null) {
				String filename = attachment.getFilename();
				if (filenames.containsKey(filename)) {

					Integer c = filenames.get(filename) + 1;
					filenames.put(filename, c);
					filename = filename + "_" + c;
				} else
					filenames.put(filename, 0);

				input = getOriginalFileStream(attachment);
				if (input != null) {
					ZipEntry zipEntry = new ZipEntry(filename);
					zipEntry.setComment(attachment.getTitle());
					zipOut.putNextEntry(zipEntry);
					int readed = 0;
					byte[] cash = new byte[2048];
					while ((readed = input.read(cash)) > 0)
						zipOut.write(cash, 0, readed);
					input.close();
				}
			}
		}
		zipOut.close();
		HttpServletResponse response = Local.getResponse();
		String filename = dataobject.getTitle() + "--" + recordtitle + "的附件" + ".zip";
		InputStream br = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
		response.addHeader("Content-Disposition",
				"attachment" + ";filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Length", "" + br.available());
		AttachmentUtils.writeStreamToResponse(br, response);
	}

	public void deleteFile(FDataobjectattachment attachment) {
		FAttachmentsetting setting = getAttachmentsetting();
		File file = new File(setting.getRootpath() + File.separator + attachment._getLocalFilename());
		if (file.exists())
			file.delete();
		file = new File(setting.getRootpath() + File.separator + attachment._getLocalPDFFilename());
		if (file.exists())
			file.delete();
	}

	public FAttachmentsetting getAttachmentsetting() {
		return dao.findById(FAttachmentsetting.class, 1);
	}

}
