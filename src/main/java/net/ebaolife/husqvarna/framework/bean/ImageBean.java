package net.ebaolife.husqvarna.framework.bean;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.awt.image.BufferedImage;

public class ImageBean {

	private int width;

	private int height;

	private int pictype = XWPFDocument.PICTURE_TYPE_PNG;

	private int type = 0;

	private BufferedImage image;

	private String file;

	public ImageBean(BufferedImage image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.type = 0;
	}

	public ImageBean(String file, int width, int height) {
		this.file = file;
		this.width = width;
		this.height = height;
		this.type = 1;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPictype() {
		return pictype;
	}

	public void setPictype(int pictype) {
		this.pictype = pictype;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.type = 0;
		this.image = image;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.type = 1;
		this.file = file;
	}

	public int getType() {
		return type;
	}
}
