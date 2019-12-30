package net.ebaolife.husqvarna.framework.bean;

import com.alibaba.fastjson.JSON;

import java.util.List;

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
public class SortParameter {

	private String property;

	private String direction;

	public SortParameter() {

	}

	public SortParameter(String property, String direction) {
		super();
		this.property = property;
		this.direction = direction;
	}

	public static List<SortParameter> changeToSortParameters(String str) {
		if (str != null && str.length() > 1) {
			return JSON.parseArray(str, SortParameter.class);
		} else
			return null;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "SortParameter [property=" + property + ", direction=" + direction + "]";
	}

}
