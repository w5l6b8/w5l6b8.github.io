package net.ebaolife.husqvarna.framework.utils;

import com.alibaba.fastjson.JSONObject;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
public class CommonFunction {

	public static String getNextCode(String code) {
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(code);
		StringBuffer resultBuffer = new StringBuffer();
		int pos = -1;
		while (matcher.find()) {
			pos++;
		}
		if (pos == -1)
			return code + "-1";
		int i = -1;
		matcher = pattern.matcher(code);
		while (matcher.find()) {
			i++;
			if (i == pos) {
				Integer next = Integer.parseInt(matcher.group()) + 1;
				int len = matcher.group().length();
				String p = next.toString();
				for (int j = p.length(); j < len; j++)
					p = "0" + p;
				matcher.appendReplacement(resultBuffer, p);
			} else
				matcher.appendReplacement(resultBuffer, matcher.group());
		}
		matcher.appendTail(resultBuffer);
		return resultBuffer.toString();
	}

	@SuppressWarnings("unchecked")
	public static String fu_jsonToString(Object value) {
		String r = "";
		if (value instanceof com.alibaba.fastjson.JSONObject) {

			DecimalFormat a = new DecimalFormat("0.00");

			Iterator<String> k = (Iterator<String>) ((JSONObject) value).keySet();
			while (k.hasNext()) {
				String lfd = (String) k.next();
				try {
					Object o = ((JSONObject) value).get(lfd);
					if (o instanceof Double)
						r = r + a.format((Double) o) + ", ";
					else
						r = r + o.toString() + ", ";
				} catch (Exception e) {

				}
			}

			return r;
		} else
			return value.toString();
	}

	public static Boolean fu_hasIncludeFilter__OR(String filter[], Object source) {
		String s = fu_jsonToString(source);
		for (String f : filter) {
			if (s.indexOf(f) != -1)
				return true;
		}
		return false;
	}

	public static Boolean fu_hasIncludeFilter__AND(String filter[], Object source) {
		String s = fu_jsonToString(source);
		for (String f : filter) {
			if (s.indexOf(f) == -1)
				return false;
		}
		return true;
	}

	public static Boolean fu_hasIncludeFilter(String filter, Object source) {
		if (fu_jsonToString(source).indexOf(filter) == -1)
			return false;
		else {

			return true;
		}
	}

	public static HttpServletResponse downloadExceptionString(HttpServletResponse response, String fn, String msg)
			throws IOException {

		byte[] buffer = msg.getBytes();
		long l = buffer.length;
		response.reset();
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(fn.getBytes("gb2312"), "iso8859-1"));
		response.addHeader("Content-Length", "" + l);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		return response;
	}

	public static HttpServletResponse downloadfilenotfound(HttpServletResponse response) throws IOException {

		byte[] buffer = "下载文件失败:没有找到要下载的文件!".getBytes();
		long l = buffer.length;
		String fn = "下载的文件未找到.txt";
		response.reset();
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(fn.getBytes("gb2312"), "iso8859-1"));
		response.addHeader("Content-Length", "" + l);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		return response;
	}

	public static HttpServletResponse downloadFileError(HttpServletResponse response, String errorMessage)
			throws IOException {
		byte[] buffer = errorMessage.getBytes("utf-8");
		long l = buffer.length;
		String fn = "下载文件时发生错误.txt";
		response.reset();
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(fn.getBytes("gb2312"), "iso8859-1"));
		response.addHeader("Content-Length", "" + l);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		return response;
	}

	public static HttpServletResponse download(OutputStream os, String downloadfilename, HttpServletResponse response)
			throws IOException {

		InputStream br = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
		return download(br, downloadfilename, "attachment", response);
	}

	public static HttpServletResponse downloadAndOpen(OutputStream os, String downloadfilename,
			HttpServletResponse response) throws IOException {

		InputStream br = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
		return download(br, downloadfilename, "inline", response);
	}

	public static void downloadAndOpenPDF(OutputStream br, String fn, HttpServletResponse response) throws IOException {

		response.reset();
		response.addHeader("Content-Disposition", "inline;filename=" + new String(fn.getBytes("gb2312"), "iso8859-1"));

		response.addHeader("Content-Length", "" + ((ByteArrayOutputStream) br).size());

		String mimetype = FileTypeVSMimeType.getMimeType(".pdf");
		response.setContentType(mimetype + ";charset=gb2312");

		response.setBufferSize(5 * 1024 * 1024);

		OutputStream out = response.getOutputStream();
		InputStream is = new ByteArrayInputStream(((ByteArrayOutputStream) br).toByteArray());
		byte[] buffer = new byte[1024 * 10];
		int len = 0;
		try {
			while ((len = is.read(buffer)) > 0) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (Exception e) {
		} finally {
			is.close();
			out.close();
		}

	}

	public static HttpServletResponse download(InputStream br, String downloadfilename, String attachmentORinline,
			HttpServletResponse response) throws IOException {

		response.reset();
		response.setBufferSize(5 * 1024 * 1024);

		response.addHeader("Content-Disposition",
				attachmentORinline + ";filename=" + new String(downloadfilename.getBytes("gb2312"), "iso8859-1"));

		response.setContentType("application/octet-stream");
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024 * 10];
		int len = 0;
		try {
			while ((len = br.read(buffer)) > 0) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (Exception e) {
		} finally {
			br.close();
			out.close();
		}
		return response;
	}

	public static HttpServletResponse download(String path, String downloadfilename, HttpServletResponse response,
			Boolean deleted) throws IOException {

		File file = new File(path);
		if (!file.exists())
			return downloadfilenotfound(response);
		InputStream br = new BufferedInputStream(new FileInputStream(path));
		response.reset();
		response.setBufferSize(5 * 1024 * 1024);

		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(downloadfilename.getBytes("gb2312"), "iso8859-1"));
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream");
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024 * 10];
		int len = 0;

		try {
			while ((len = br.read(buffer)) > 0) {
				out.write(buffer, 0, len);
				out.flush();

			}
		} catch (Exception e) {

		} finally {

			br.close();
			out.close();
		}
		if (deleted)
			file.delete();
		return response;
	}

	public static HttpServletResponse downloadFilePreviewIcon(String path, String filename,
			HttpServletResponse response) throws IOException {
		String ext = FileOperate.getFileExtName(filename);
		String extIconFileName = "no.png";
		if (ext.length() > 0)
			extIconFileName = ext + ".png";

		File file = new File(path + extIconFileName);
		if (!file.exists())
			extIconFileName = "otherfile.png";

		InputStream br = new BufferedInputStream(new FileInputStream(path + extIconFileName));
		response.reset();
		response.setBufferSize(5 * 1024);
		response.setContentType("image/png");
		response.setHeader("Cache-Control", "max-age=" + 60);
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024 * 10];
		int len = 0;

		try {
			while ((len = br.read(buffer)) > 0) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (Exception e) {
		} finally {
			br.close();
			out.close();
		}
		return response;
	}

	public static OutputStream excelStreamToPDFStream(InputStream inputStream) {
		String fileext = "xls";
		ByteArrayOutputStream pdfos = new ByteArrayOutputStream();
		try {
			OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
			connection.connect();
			DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
			DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
			DocumentFormat docFormat = formatReg.getFormatByFileExtension(fileext);
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputStream, docFormat, pdfos, pdfFormat);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdfos;
	}

	public static Object getProperty(Object des, String proName) throws NoSuchFieldException, IllegalAccessException {

		Class<?> classType = des.getClass();

		String fieldName = proName;

		String stringLetter = fieldName.substring(0, 1).toUpperCase();

		String getName = "get" + stringLetter + fieldName.substring(1);

		Method getMethod = null;
		try {
			getMethod = classType.getMethod(getName, new Class[] {});
		} catch (Exception e) {

			if (classType.getGenericSuperclass() != null)
				try {
					classType = classType.getSuperclass();
					getMethod = classType.getMethod(getName, new Class[] {});
				} catch (Exception e1) {
					if (classType.getGenericSuperclass() != null)
						classType = classType.getSuperclass();
					try {
						getMethod = classType.getSuperclass().getMethod(getName, new Class[] {});
					} catch (Exception e2) {
						return ("**" + des.getClass().getSimpleName() + "的值" + proName);
					}
				}

		}

		Object value = null;
		try {
			value = getMethod.invoke(des, new Object[] {});
		} catch (Exception e) {

		}
		return value;

	}

	public static void setProperty(Object des, String proName, Object value)
			throws NoSuchFieldException, IllegalAccessException {
		Class<? extends Object> classType = des.getClass();
		Field field = des.getClass().getDeclaredField(proName);
		String fieldName = field.getName();
		String stringLetter = fieldName.substring(0, 1).toUpperCase();

		String setName = "set" + stringLetter + fieldName.substring(1);

		Method setMethod = null;
		try {
			setMethod = classType.getMethod(setName, new Class[] { field.getType() });
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			setMethod.invoke(des, new Object[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setPropertyToSuperClass(Object des, String proName, Object value)
			throws NoSuchFieldException, IllegalAccessException {
		Class<? extends Object> classType = des.getClass();
		Field field = null;

		try {
			field = des.getClass().getDeclaredField(proName);
		} catch (Exception e) {
			try {
				field = des.getClass().getSuperclass().getDeclaredField(proName);
			} catch (Exception e1) {
				field = des.getClass().getSuperclass().getSuperclass().getDeclaredField(proName);

			}
		}

		String fieldName = field.getName();
		String stringLetter = fieldName.substring(0, 1).toUpperCase();

		String setName = "set" + stringLetter + fieldName.substring(1);

		Method setMethod = null;
		try {
			setMethod = classType.getMethod(setName, new Class[] { field.getType() });
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			setMethod.invoke(des, new Object[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Boolean fu_isImgFile(String fext) {
		if (fext == null)
			return false;
		return fext.equals(".jpg") || fext.equals(".jpeg") || fext.equals(".gif") || fext.equals(".png")
				|| fext.equals(".bmp") || fext.equals(".ico") || fext.equals(".tif");
	}

	private static Integer count = 0;
	private static String lastDate = null;

	public static synchronized String fu_GenXH() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HHmm");
		String dataString = sdf.format(new Date());
		if (dataString.equals(lastDate)) {
			count++;
		} else {
			lastDate = dataString;
			count = 1;
		}
		return dataString + count;
	}

	public static synchronized String fu_GenDownLoadXH() {
		String tf_result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HHmm");
		tf_result = sdf.format(new Date());
		return tf_result;
	}

	public static String fu_GenDivCase(String fz, String fm) {
		String tf_result = "CASE when " + fm + "=0 then null else " + fz + "/" + fm + " end";
		return tf_result;
	}

	public static String fu_TwoTwoChange(String sour) {
		byte[] b = sour.getBytes();
		int len = b.length;
		for (int i = 0; i < (len) / 2; i++) {
			byte tmp = b[i * 2];
			b[i * 2] = b[i * 2 + 1];
			b[i * 2 + 1] = tmp;
		}
		return new String(b);
	}

	public static Object depthClone(Object srcObj) {
		Object cloneObj = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(srcObj);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(in);
			cloneObj = oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cloneObj;
	}

	public static String fu_getSerialNum() {
		return null;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

	public static Boolean Zipfile(String filename, String zipfilename) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "ISO8859_1"));
			FileOutputStream f = new FileOutputStream(zipfilename);
			CheckedOutputStream ch = new CheckedOutputStream(f, new CRC32());
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(ch));
			int c;
			out.putNextEntry(new ZipEntry(filename));
			while ((c = in.read()) != -1)
				out.write(c);
			in.close();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Date getNextDate(Date inputDate) {
		Calendar nextDate = Calendar.getInstance();
		nextDate.setTime(inputDate);
		nextDate.add(Calendar.DATE, 1);
		return nextDate.getTime();
	}

	public static String encodeByMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
}
