package net.ebaolife.husqvarna.framework.bean;
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
public enum FieldType {
	String("String"), Boolean("Boolean"), Integer("Integer"), Date("Date"), DateTime("DateTime"), Timestamp(
			"Timestamp"), Double("Double"), Float("Float"), Money("Money"), Percent("Percent"), Byte("Byte");

	private String value;

	FieldType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;

	}

}
