package net.ebaolife.husqvarna.framework.bean;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
public enum FieldAggregationType {

	NORMAL("normal"), COUNT("count"), SUM("sum"), AVG("avg"), MAX("max"), MIN("min"), WAVG("wavg"), VAR("var"), VARP(
			"varp"), STDEV("stdev"), STDEVP("stdevp"),

	ADDITIONCOUNT("additioncount");

	private String value;

	public static final Map<FieldAggregationType, String> AGGREGATION = genMapInfo();

	FieldAggregationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	private static Set<FieldAggregationType> wavgCommonlyType = null;

	private static Set<FieldAggregationType> numberCommonlyType = null;

	private static Set<FieldAggregationType> dateCommonlyType = null;

	private static Set<FieldAggregationType> strCommonlyType = null;

	private static Map<FieldAggregationType, String> genMapInfo() {
		Map<FieldAggregationType, String> result = new LinkedHashMap<FieldAggregationType, String>();

		result.put(COUNT, "计数");
		result.put(SUM, "求和");
		result.put(AVG, "平均值");
		result.put(MAX, "最大值");
		result.put(MIN, "最小值");
		result.put(WAVG, "加权平均");
		result.put(VAR, "方差");
		result.put(VARP, "总体方差");
		result.put(STDEV, "标准偏差");
		result.put(STDEVP, "总体标准偏差");

		return result;
	}

	public static Set<FieldAggregationType> getNumberCommonlyType() {
		if (numberCommonlyType == null) {
			numberCommonlyType = new LinkedHashSet<FieldAggregationType>();
			numberCommonlyType.add(COUNT);
			numberCommonlyType.add(SUM);
			numberCommonlyType.add(AVG);
			numberCommonlyType.add(MAX);
			numberCommonlyType.add(MIN);
		}
		return numberCommonlyType;
	}

	public static Set<FieldAggregationType> getDateCommonlyType() {
		if (dateCommonlyType == null) {
			dateCommonlyType = new LinkedHashSet<FieldAggregationType>();
			dateCommonlyType.add(COUNT);

			dateCommonlyType.add(MAX);
			dateCommonlyType.add(MIN);
		}
		return dateCommonlyType;
	}

	public static Set<FieldAggregationType> getStrCommonlyType() {
		if (strCommonlyType == null) {
			strCommonlyType = new LinkedHashSet<FieldAggregationType>();
			strCommonlyType.add(COUNT);
		}
		return strCommonlyType;
	}

	public static Set<FieldAggregationType> getWavgCommonlyType() {
		if (wavgCommonlyType == null) {
			wavgCommonlyType = new LinkedHashSet<FieldAggregationType>();
			wavgCommonlyType.add(WAVG);
		}
		return wavgCommonlyType;
	}

}
