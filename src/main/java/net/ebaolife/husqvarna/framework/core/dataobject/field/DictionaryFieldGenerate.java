package net.ebaolife.husqvarna.framework.core.dataobject.field;

import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.framework.utils.ProjectUtils;

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
public class DictionaryFieldGenerate {

  public static String getDictionaryTextField(String fieldname, FDictionary dictionary) {
    String jdbctype = ProjectUtils.getInitParameter("jdbc.dbType");
    String titleField = "title";
    
    if (dictionary.getIsdisplaycode()) {
      switch (jdbctype) {
        case "mysql":
        case "oracle":
          titleField = "concat(vcode,concat('-'," + titleField + "))";
          break;
        case "sqlserver":
          titleField = "vcode+'-'+" + titleField;
          break;
      }
    }
    
    if (dictionary.getIsdisplayorderno()) {
      switch (jdbctype) {
        case "mysql":
        case "oracle":
          titleField = "concat(orderno,concat('-'," + titleField + "))";
          break;
        case "sqlserver":
          titleField = "orderno+'-'+" + titleField;
          break;
      }
    }
    
    String linkname = dictionary.getIslinkedorderno()
        ? "orderno"
        : dictionary.getIslinkedcode() ? "vcode" : dictionary.getIslinkedtext() ? "title" : "ddetailid";

    String result = "( select " + titleField + " from f_dictionarydetail f_d_d where f_d_d.dictionaryid = '"
        + dictionary.getDictionaryid() + "' and f_d_d." + linkname + " = " + fieldname + " )";

    return result;
  }

}
