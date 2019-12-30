package net.ebaolife.husqvarna.framework.utils;


import net.ebaolife.husqvarna.framework.exception.FileStreamException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class FileUtils {

	private final static Map<String, String> suffixmap = new HashMap<String, String>() {
		{
			put("DOC", "01");
			put("XLS", "02");
			put("PPT", "03");
			put("PDF", "04");
			put("BMP", "11");
			put("JPG", "12");
			put("GIF", "13");
			put("PNG", "14");
			put("AVI", "21");
			put("MPEG", "22");
			put("DOCX", "51");
			put("XLSX", "52");
			put("PPTX", "53");
		}
	};

	public static FileInputStream getFileInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new FileStreamException(e);
		}
	}

	public static FileOutputStream getFileOutputStream(File file) {
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new FileStreamException(e);
		}
	}

	public static long copy(File input, File output) {
		return copy(getFileInputStream(input), getFileOutputStream(output));
	}

	public static long copy(InputStream input, File output) {
		return copy(input, getFileOutputStream(output));
	}

	public static long copy(File input, OutputStream output) {
		return copy(getFileInputStream(input), output);
	}

	public static long copy(InputStream is, OutputStream os) {
		try {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = is instanceof BufferedInputStream ? (BufferedInputStream) is : new BufferedInputStream(is);
				bos = os instanceof BufferedOutputStream ? (BufferedOutputStream) os : new BufferedOutputStream(os);
				int n = -1;
				while ((n = bis.read()) != -1) {
					bos.write(n);
				}
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		} catch (Exception e) {
			throw new FileStreamException(e);
		}
		return 0L;
	}

	public static long copy(InputStream is, StringBuffer sb) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			try {
				String n = null;
				while ((n = br.readLine()) != null) {
					sb.append(n);
				}
			} finally {
				if (br != null)
					br.close();
			}
		} catch (Exception e) {
			throw new FileStreamException(e);
		}
		return 0L;
	}

	public static String getDocType(String suffix) {
		return getDocType(Integer.parseInt(getSuffixType(suffix)));
	}

	private static String getSuffixType(String suffix) {
		String type = suffixmap.get(suffix.trim().toUpperCase());
		return type == null ? "99" : type;
	}

	private static String getDocType(int type) {
		switch (type) {
		case 11:
		case 12:
		case 13:
		case 14:
			return "02";
		case 21:
		case 22:
			return "03";
		default:
			return "01";
		}
	}

	public static List<File> getAllChildFiles(File parent, final boolean onlyDir) {
		return getAllChildFiles(parent, onlyDir, null, null, null, null);
	}

	public static List<File> getAllChildFiles(File parent, boolean onlyDir, String dirRegex, String dirIgnoreRegex,
			String fileRegex, String fileIgnoreRegex) {
		List<File> list = new ArrayList<File>();
		getAllChildFiles(list, parent, onlyDir, dirRegex, dirIgnoreRegex, fileRegex, fileIgnoreRegex);
		return list;
	}

	private static void getAllChildFiles(List<File> list, File parent, boolean onlyDir, String dirRegex,
			String dirIgnoreRegex, String fileRegex, String fileIgnoreRegex) {
		List<File> files = getChildFiles(parent, onlyDir, dirRegex, dirIgnoreRegex, fileRegex, fileIgnoreRegex);
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			list.add(file);
			if (file.isDirectory()) {
				getAllChildFiles(list, file, onlyDir, dirRegex, dirIgnoreRegex, fileRegex, fileIgnoreRegex);
			}
		}
	}

	public static List<File> getChildFiles(File parent, final boolean onlyDir) {
		return getChildFiles(parent, onlyDir, null, null, null, null);
	}

	public static List<File> getChildFiles(File parent, final boolean onlyDir, final String dirRegex,
			final String dirIgnoreRegex, final String fileRegex, final String fileIgnoreRegex) {
		File[] fs = parent.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (onlyDir && !pathname.isDirectory())
					return false;
				String name = pathname.getName();
				if (pathname.isDirectory()) {
					if (dirRegex != null && !name.matches(dirRegex))
						return false;
					if (dirIgnoreRegex != null && name.matches(dirIgnoreRegex))
						return false;
				} else {
					if (fileRegex != null && !name.matches(fileRegex))
						return false;
					if (fileIgnoreRegex != null && name.matches(fileIgnoreRegex))
						return false;
				}
				return true;
			}
		});
		List<File> files = new ArrayList<File>();
		if (fs != null) {
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].isDirectory())
					files.add(fs[i]);
			}
			for (int i = 0; i < fs.length; i++) {
				if (!fs[i].isDirectory())
					files.add(fs[i]);
			}
		}
		return files;
	}

	public static boolean deleteDirectory(Object sPath) {
		return deleteDirectory(sPath, false);
	}

	public static boolean deleteDirectory(Object sPath, boolean deldir) {
		File dirFile = null;
		if (sPath instanceof String) {
			if (!((String) sPath).endsWith(File.separator)) {
				sPath = sPath + File.separator;
			}
			dirFile = new File((String) sPath);
		} else {
			dirFile = (File) sPath;
		}
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath(), deldir);
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		if (!deldir)
			return true;
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

}
