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
public class GroupParameter {

	private String property;

	private String direction;

	private String title;

	public GroupParameter() {

	}

	public GroupParameter(String property, String direction) {
		super();
		this.property = property;
		this.direction = direction;
	}

	public static List<GroupParameter> changeToGroupParameters(String str) {
		if (str != null && str.length() > 1) {
			return JSON.parseArray(str, GroupParameter.class);
		} else
			return null;
	}

	public static GroupParameter changeToGroupParameter(String str) {
		if (str != null && str.length() > 1) {
			return JSON.parseObject(str, GroupParameter.class);
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
		return "GroupParameter [property=" + property + ", direction=" + direction + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
