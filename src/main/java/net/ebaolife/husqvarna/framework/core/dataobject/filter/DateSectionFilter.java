package net.ebaolife.husqvarna.framework.core.dataobject.filter;

import java.util.Calendar;

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
public class DateSectionFilter {

  public static final String THISYEAR = "thisyear";
  public static final String THISQUARTER = "thisquarter";
  public static final String THISMONTH = "thismonth";
  public static final String THISDAY = "thisday";
  public static final String YEAR = "year";
  public static final String YEARQUARTER = "yearquarter";
  public static final String YEARMONTH = "yearmonth";
  public static final String DAY = "day";
  public static final String YEARSECTION = "yearsection";
  public static final String MONTHSECTION = "monthsection";
  public static final String QUARTERSECTION = "quartersection";
  public static final String DATESECTION = "datesection";

  private String property;
  private String type;
  private String value;

  public DateSectionFilter(String property, String type, String value) {
    super();
    this.property = property;
    this.type = type;
    this.value = value;
  }

  public static boolean isDataSectionFilter(String type) {
    return (type != null) && (type.equals(THISYEAR) || type.equals(THISQUARTER) || type.equals(THISMONTH)
        || type.equals(THISDAY) || type.equals(YEAR) || type.equals(YEARQUARTER) || type.equals(YEARMONTH)
        || type.equals(DAY) || type.equals(YEARSECTION) || type.equals(MONTHSECTION) || type.equals(QUARTERSECTION)
        || type.equals(DATESECTION));
  }

  public String getWhereSql() {

    

    Calendar cal = Calendar.getInstance();

    Integer nowyear = cal.get(Calendar.YEAR);
    Integer nowmonth = cal.get(Calendar.MONTH) + 1;
    Integer nowquarter = (nowmonth - 1) / 3 + 1;
    Integer nowday = cal.get(Calendar.DAY_OF_MONTH);
    String result = null;
    if (type.equals(THISYEAR)) {
      
      result = " year(" + property + ") = " + nowyear;
    } else if (type.equals(THISQUARTER)) {
      
      result = String.format(" year( %s ) = %d and month( %s ) between  %d and %d ", property, nowyear, property,
          (nowquarter - 1) * 3 + 1, (nowquarter - 1) * 3 + 3);
    } else if (type.equals(THISMONTH)) {
      
      result = String.format(" year( %s ) = %d and month( %s ) = %d ", property, nowyear, property, nowmonth);
    } else if (type.equals(THISDAY)) {
      
      result = String.format(" year( %s ) = %d and month( %s ) = %d and day( %s ) = %d  ", property, nowyear, property,
          nowmonth, property, nowday);
    } else if (type.equals(YEAR)) {
      
      result = " year(" + property + ") = " + value;
    } else if (type.equals(YEARQUARTER)) {
      
      String yq[] = value.split("-");
      int q = Integer.parseInt(yq[1]);
      result = String.format(" year( %s ) = %s and month( %s ) between  %d and %d ", property, yq[0], property,
          (q - 1) * 3 + 1, (q - 1) * 3 + 3);
    } else if (type.equals(YEARMONTH)) {
      
      String yq[] = value.split("-");
      int q = Integer.parseInt(yq[1]);
      result = String.format(" year( %s ) = %s and month( %s ) = %d ", property, yq[0], property, q);
    } else if (type.equals(DAY)) {
      
      String yq[] = value.split("-");
      result = String.format(" year( %s ) = %s and month( %s ) = %s and day( %s ) = %s  ", property, yq[0], property,
          yq[1], property, yq[2]);
    }

    else if (type.equals(YEARSECTION)) {
      String y[] = value.split("--");
      if (y.length == 2 && y[0].length() > 0 && y[1].length() > 0) {
        result = String.format(" year( %s ) between %s and %s ", property, y[0], y[1]);
      } else {
        if (y[0].length() > 0) {
          
          result = String.format(" year( %s ) >= %s ", property, y[0]);
        } else {
          
          result = String.format(" year( %s ) <= %s ", property, y[1]);
        }
      }

    }

    else if (type.equals(MONTHSECTION)) {
      String y[] = value.split("--");
      if (y.length == 2 && y[0].length() > 0 && y[1].length() > 0) {
        result = getmonthSql(property, y[0], ">") + " and " + getmonthSql(property, y[1], "<");
      } else {
        if (y[0].length() > 0) {
          
          result = getmonthSql(property, y[0], ">");
        } else {
          
          result = getmonthSql(property, y[1], "<");
        }
      }

    } else if (type.equals(QUARTERSECTION)) {
      String y[] = value.split("--");
      if (y.length == 2 && y[0].length() > 0 && y[1].length() > 0) {
        result = getquarterSql(property, y[0], ">") + " and " + getquarterSql(property, y[1], "<");
      } else {
        if (y[0].length() > 0) {
          
          result = getquarterSql(property, y[0], ">");
        } else {
          
          result = getquarterSql(property, y[1], "<");
        }
      }

    } else if (type.equals(DATESECTION)) {
      String y[] = value.split("--");
      if (y.length == 2 && y[0].length() > 0 && y[1].length() > 0) {
        result = getDateSql(property, y[0], ">") + " and " + getDateSql(property, y[1], "<");
      } else {
        if (y[0].length() > 0) {
          
          result = getDateSql(property, y[0], ">");
        } else {
          
          result = getDateSql(property, y[1], "<");
        }
      }

    }
    if (result != null)
      return "(" + result + ")";
    else
      return null;

  }

  
  public String getmonthSql(String fn, String value, String equal) {

    
    
    String yq[] = value.split("-");
    return String.format("( year( %s ) %s %s or ( year( %s ) = %s and month( %s ) %s= %s ))", fn, equal, yq[0], fn,
        yq[0], fn, equal, yq[1]);
  }

  
  public String getquarterSql(String fn, String value, String equal) {

    
    
    String yq[] = value.split("-");
    int jd = Integer.parseInt(yq[1]);
    
    return String.format("( year( %s ) %s %s or ( year( %s ) = %s and month( %s ) %s= %s ))", fn, equal, yq[0], fn,
        yq[0], fn, equal, equal.equals(">") ? (jd - 1) * 3 + 1 : (jd - 1) * 3 + 3);
  }

  
  public String getDateSql(String fn, String value, String equal) {

    
    

    
    String yq[] = value.split("-");

    return String.format(
        "( year( %s ) %s %s or ( year( %s ) = %s and month( %s ) %s %s ) or "
            + " (year( %s ) = %s and month( %s ) = %s and day( %s ) %s= %s ))",
        fn, equal, yq[0], fn, yq[0], fn, equal, yq[1], fn, yq[0], fn, yq[1], fn, equal, yq[2]);
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
