package net.ebaolife.husqvarna.framework.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class TypeChange {

	public static boolean superClassCheck(Class<?> son, Class<?> father) {
		if (son.getSuperclass() == Object.class || son.getSuperclass() == null)
			return false;
		else if (son.getSuperclass() == father)
			return true;
		else
			return superClassCheck(son.getSuperclass(), father);
	}

	public static Date StringToDate(String date) {
		Date tf_result = null;
		try {
			if ((date == null) || ("".equals(date)) || date.equals("null"))
				return null;
			date = date.replaceAll("/", "-");
			if (date.length() > 10) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					tf_result = dateFormat.parse(date);
				} catch (Exception e) {
				}
			} else
				tf_result = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (Exception e) {

			if (date.indexOf("-") == 2)
				date = "20" + date;
			if (date.length() == 4)
				date += "-01-01";
			if (date.length() == 7)
				date += "-01";
			try {
				tf_result = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		return tf_result;
	}

	public static Date StringToDate(Object date) {
		Date tf_result = null;
		try {
			if ((date == null) || ("".equals(date.toString())) || ("null".equals(date.toString())))
				return null;
			if (date.toString().length() > 10)
				tf_result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date.toString());
			else
				tf_result = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
		} catch (ParseException e) {

		}
		return tf_result;
	}

	public static Date StringToDateFormat(Object date, SimpleDateFormat Format) {
		Date tf_result = null;
		try {
			if ((date == null) || ("".equals(date)))
				return null;
			tf_result = Format.parse(date.toString());
		} catch (ParseException e) {

		}
		return tf_result;
	}

	public static Double StringtoDouble(String str) {
		try {
			return Double.parseDouble(str.replaceAll(",", ""));
		} catch (Exception e) {
			return 0.;
		}
	}

	public static Integer StringtoInteger(String str) {
		try {
			return Integer.parseInt(str.replaceAll(",", ""));
		} catch (Exception e) {
			try {
				return StringtoDouble(str).intValue();
			} catch (Exception e1) {
				return 0;
			}
		}
	}

	public static Boolean StringtoBoolean(String str) {
		if (str == null)
			return null;
		str = str.toLowerCase();
		if (str.equals("true") || str.equals("yes") || str.equals("1"))
			return true;
		else
			return false;
	}

	public static Double dtod(Object d) {
		if (d == null)
			return 0.0;
		else if (d instanceof BigDecimal)
			return ((BigDecimal) d).doubleValue();
		else if (d instanceof Double)
			return (Double) d;
		else
			return StringtoDouble(d.toString());

	}

	public static Double dtod(BigDecimal d) {
		if (d == null)
			return 0.0;
		else
			return d.doubleValue();
	}

	public static Double dtod(Double d) {
		if (d == null)
			return 0.0;
		else
			return d;
	}

	public static Integer itoi(Integer d) {
		if (d == null)
			return 0;
		else
			return d;
	}

	public static Integer itoi(Object d) {
		if (d == null)
			return 0;
		else
			try {
				return Integer.parseInt(d.toString());
			} catch (Exception e) {
				return StringtoDouble(d.toString()).intValue();
			}
	}

	public static String DoubletoString(Double money) {
		try {
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			String tf_result = nf.format(money).replaceAll("￥", "").replaceAll("$", "");
			if (tf_result.equals("0.00"))
				return "";
			else
				return tf_result;
		} catch (Exception e) {
			return "";
		}
	}

	public static String DoubletoString_(Double money) {
		try {
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			String tf_result = nf.format(money);
			if (money == 0)
				return "";
			else
				return tf_result;
		} catch (Exception e) {
			return "";
		}
	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String DateToString(Date date) {
		String tf_result = "";
		if (date != null)
			tf_result = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return tf_result;
	}

	public static String ZerotoSpace(Object value) {
		if (value == null)
			return "";
		String s = value.toString();
		if (s.equals("0") || s.equals("0.0") || s.equals("0.00"))
			return "";
		else
			return s;
	}

	public static double jsbl(double v1, double v2) {
		double vv1 = dtod(v1);
		double vv2 = dtod(v2);
		double r = 0.0;
		try {
			r = round(vv1 / vv2, 4);
		} catch (Exception e) {
			return 0.0;
		}
		return r;
	}

	public static int toInt(Object v) {
		if (v instanceof Integer)
			return ((Integer) v).intValue();
		if (v instanceof BigInteger)
			return ((BigInteger) v).intValue();
		if (v instanceof BigDecimal)
			return ((BigDecimal) v).toBigInteger().intValue();
		return 0;
	}

	private static final String[] pattern = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private static final String[] cPattern = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
	private static final String[] cfPattern = { "", "角", "分" };
	private static final String ZEOR = "零";

	public static String moneyFormatToUpper(Object money) {
		if (money == null)
			return "";
		try {
			return moneyFormatToUpper(Double.parseDouble(money.toString()));
		} catch (Exception e) {
			return money.toString();
		}
	}

	public static String moneyFormatToUpper(Double money) {

		String moneyString = DoubletoString(money).replaceAll(",", "");
		int dotPoint = moneyString.indexOf(".");
		String moneyStr;
		if (dotPoint != -1) {
			moneyStr = moneyString.substring(0, moneyString.indexOf("."));
		} else {
			moneyStr = moneyString;
		}
		StringBuffer fraction = null;
		StringBuffer ms = new StringBuffer();
		for (int i = 0; i < moneyStr.length(); i++) {
			ms.append(pattern[moneyStr.charAt(i) - 48]);
		}

		int cpCursor = 1;
		for (int j = moneyStr.length() - 1; j > 0; j--) {
			ms.insert(j, cPattern[cpCursor]);

			cpCursor = cpCursor == 8 ? 1 : cpCursor + 1;
		}

		while (ms.indexOf("零拾") != -1) {

			ms.replace(ms.indexOf("零拾"), ms.indexOf("零拾") + 2, ZEOR);
		}
		while (ms.indexOf("零佰") != -1) {
			ms.replace(ms.indexOf("零佰"), ms.indexOf("零佰") + 2, ZEOR);
		}
		while (ms.indexOf("零仟") != -1) {
			ms.replace(ms.indexOf("零仟"), ms.indexOf("零仟") + 2, ZEOR);
		}
		while (ms.indexOf("零万") != -1) {
			ms.replace(ms.indexOf("零万"), ms.indexOf("零万") + 2, "万");
		}
		while (ms.indexOf("零亿") != -1) {
			ms.replace(ms.indexOf("零亿"), ms.indexOf("零亿") + 2, "亿");
		}
		while (ms.indexOf("零零") != -1) {
			ms.replace(ms.indexOf("零零"), ms.indexOf("零零") + 2, ZEOR);
		}
		while (ms.indexOf("亿万") != -1) {
			ms.replace(ms.indexOf("亿万"), ms.indexOf("亿万") + 2, "亿");
		}
		while (ms.lastIndexOf("零") == ms.length() - 1) {
			ms.delete(ms.lastIndexOf("零"), ms.lastIndexOf("零") + 1);
		}

		int end;
		if ((dotPoint = moneyString.indexOf(".")) != -1) {
			String fs = moneyString.substring(dotPoint + 1, moneyString.length());
			if (fs.indexOf("00") == -1 || fs.indexOf("00") >= 2) {
				end = fs.length() > 2 ? 2 : fs.length();
				fraction = new StringBuffer(fs.substring(0, end));
				for (int j = 0; j < fraction.length(); j++) {
					fraction.replace(j, j + 1, pattern[fraction.charAt(j) - 48]);
				}
				for (int i = fraction.length(); i > 0; i--) {
					fraction.insert(i, cfPattern[i]);
				}
				fraction.insert(0, "元");
			} else {
				fraction = new StringBuffer("元整");
			}

		} else {
			fraction = new StringBuffer("元整");
		}

		ms.append(fraction);
		return ms.toString();
	}

}
