package net.ebaolife.husqvarna.framework.utils;

public class FileTypeVSMimeType {

	public static final String fileExtendsVSMimeType[][] = { { "dib", "image/bmp" },
			{ "bmp", "image/bmp" }, { "gif", "image/gif" }, { "ief", "image/ief" },
			{ "jpe", "image/jpeg" }, { "jpeg", "image/jpeg" }, { "jpg", "image/jpeg" },
			{ "pct", "image/pict" }, { "tif", "image/tiff" }, { "tiff", "image/tiff" },
			{ "pic", "image/pict" }, { "pict", "image/pict" }, { "png", "image/png" },
			{ "psd", "image/x-photoshop" }, { "swf", "application/x-shockwave-flash" },
			{ "txt", "text/plain" }, { "pdf", "application/pdf" }, { "mp3", "audio/x-mpeg" },
			{ "mp4", "video/mp4" }, { "mpe", "video/mpeg" }, { "mpg", "video/mpeg" },
			{ "mpeg", "video/mpeg" }, { "htm", "text/html" }, { "html", "text/html" },
			{ "xls", "application/vnd.ms-excel" }, { "doc", "application/msword" },
			{ "xlsx", "application/vnd.ms-excel" }, { "docx", "application/msword" },
			{ "ppt", "application/vnd.ms-powerpoint" },
			{ "pps", "application/vnd.ms-powerpoint" }, { "xml", "application/xml" },
			{ "wav", "audio/x-wav" }, { "avi", "video/x-msvideo" } };

	public static String getMimeType(String filename) {
		for (String[] s : fileExtendsVSMimeType)
			if (filename.toLowerCase().endsWith("." + s[0]))
				return s[1];
		return null;
	}

	public static Boolean isImageFile(String filename) {
		String mimetype = getMimeType(filename);
		if (mimetype == null)
			return false;
		else
			return mimetype.startsWith("image");
	}

	public static Boolean isCanPreviewFile(String filename) {

		String f = filename.toLowerCase();
		if (f.endsWith(".pdf") || f.endsWith(".swf") || f.endsWith(".txt") || f.endsWith(".mp3")
				|| f.endsWith(".mp4") || f.endsWith(".mpe") || f.endsWith(".mpg")
				|| f.endsWith(".mpeg") || f.endsWith(".htm") || f.endsWith(".html")
				|| f.endsWith(".xml") || f.endsWith(".wav") || f.endsWith(".avi"))
			return true;
		else
			return false;
	}

}
