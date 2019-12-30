package net.ebaolife.husqvarna.framework.utils;

public class DBFieldType {

  public static String valueOf(String format) {
    String type = format;
    switch (format.toLowerCase()) {
      case "char":
      case "varchar":
      case "nvarchar":
      case "nvarchar2":
      case "varchar2":
        type = "String";
        break;
      case "tinyint":
      case "smallint":
      case "bit":
        type = "Boolean";
        break;
      case "datetime":
      case "timestamp(6)":
        type = "Timestamp";
        break;
      case "date":
        type = "Date";
        break;
      case "int":
      case "integer":
        type = "Integer";
        break;
      case "blob":
      case "longblob":
        type = "Image";
        break;
      case "float":
        type = "Float";
        break;
      case "decimal":
      case "double":
      case "number":
      case "numeric":
        type = "Double";
        break;
    }
    return type;
  }

  public static boolean isNumber(String type) {
    String fieldtype = type.toLowerCase();
    return (fieldtype.equals("double") || fieldtype.equals("integer") || fieldtype.equals("bigdecimal")
        || fieldtype.equals("float"));
  }

}
