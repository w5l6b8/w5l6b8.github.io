package net.ebaolife.husqvarna.framework.bean;

import net.ebaolife.husqvarna.framework.utils.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.InputStream;

public class DownloadFile {

	private int type = 0;
	private HSSFWorkbook hwb;
	private String fileName;
	private InputStream is;
	private Object tag;
	private boolean dialog;
	private String contentType;

	public DownloadFile(File file) {
		this(FileUtils.getFileInputStream(file), file.getName(), true, null);
	}

	public DownloadFile(File file, String fileName) {
		this(FileUtils.getFileInputStream(file), fileName, true, null);
	}

	public DownloadFile(File file, String fileName, String contentType) {
		this(FileUtils.getFileInputStream(file), fileName, true, contentType);
	}

	public DownloadFile(HSSFWorkbook hwb, String fileName) {
		this(hwb, fileName, true);
	}

	public DownloadFile(HSSFWorkbook hwb, String fileName, boolean dialog) {
		this.type = 1;
		this.hwb = hwb;
		this.fileName = fileName;
		this.dialog = dialog;
	}

	public DownloadFile(InputStream is, String fileName) {
		this(is, fileName, true, null);
	}

	public DownloadFile(File file, boolean dialog) {
		this(FileUtils.getFileInputStream(file), file.getName(), dialog, null);
	}

	public DownloadFile(File file, String fileName, boolean dialog) {
		this(FileUtils.getFileInputStream(file), fileName, dialog, null);
	}

	public DownloadFile(File file, String fileName, boolean dialog, String contentType) {
		this(FileUtils.getFileInputStream(file), fileName, dialog, null);
	}

	public DownloadFile(InputStream is, String fileName, boolean dialog, String contentType) {
		this.is = is;
		this.fileName = fileName;
		this.dialog = dialog;
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		return this.is;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public String getSuffix() {
		return this.fileName.indexOf(".") > 0 ? this.fileName.substring(this.fileName.lastIndexOf(".") + 1) : "";
	}

	public boolean isDialog() {
		return dialog;
	}

	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}

	public int getType() {
		return this.type;
	}

	public HSSFWorkbook getExcel() {
		return this.hwb;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}