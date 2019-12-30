package net.ebaolife.husqvarna.framework.core.dataobject.field;

import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;

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
public class AttachmentFieldGenerate {
	public static final String ATTACHMENTTABLE = "f_dataobjectattachment";

	public static String getCountField(BaseModule baseModule) {
		String result = "( select count(*) from " + ATTACHMENTTABLE + " where objectid = '"
				+ baseModule.getModule().getObjectid() + "' and idvalue = "
				+ baseModule.getModule()._getPrimaryKeyField()._getSelectName(baseModule.getAsName()) + " )";
		return result;
	}

	public static String getTooltipField(BaseModule baseModule) {

		String result = "(SELECT GROUP_CONCAT(at_.attachmentid,'|',IFNULL(at_.title, ''),'|',"
				+ " IFNULL(at_.filename, '') SEPARATOR '|||') FROM f_dataobjectattachment at_ "
				+ " where at_.objectid = '" + baseModule.getModule().getObjectid() + "' and at_.idvalue = "
				+ baseModule.getModule()._getPrimaryKeyField()._getSelectName(baseModule.getAsName()) + " )";

		return result;
	}

}
