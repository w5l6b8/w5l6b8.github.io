package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public abstract class AbstractModule {

  private FDataobject module;
  private String asName; 

  private List<UserDefineFilter> querys;
  private List<UserDefineFilter> userDefineFilters; 
  private List<UserNavigateFilter> userNavigateFilters;
  private List<UserParentFilter> userParentFilters;

  

  public static String SHORTSCALE = "scale___";
  int scaleCount = 0;
  Map<String, String> longToShortScales = new HashMap<String, String>();

  public String changeLongScaleToShort(String scale) {
    if (scale.indexOf(".") == -1) 
    {
      if (scale.length() <= 30)
        return scale;
      else { 
        scaleCount++;
        String shortScale = SHORTSCALE + scaleCount;
        longToShortScales.put(scale, shortScale);
        return shortScale;
      }

    } else {
      if (longToShortScales.containsKey(scale))
        return longToShortScales.get(scale);
      else {
        scaleCount++;
        String shortScale = SHORTSCALE + scaleCount;
        longToShortScales.put(scale, shortScale);
        return shortScale;
      }
    }
  }

  public String getShortScaleFormScale(String scale) {
    if (scale.indexOf(".") == -1)
      return scale;
    else {
      if (longToShortScales.containsKey(scale))
        return longToShortScales.get(scale);
      else {
        System.out.println("scale:" + scale + "未找到");
        return "scale:" + scale + "未找到";
      }
    }
  }

  public Map<String, String> getLongToShortScales() {
    return longToShortScales;
  }

  public void setLongToShortScales(Map<String, String> longToShortScales) {
    this.longToShortScales = longToShortScales;
  }

  public List<UserDefineFilter> getUserDefineFilters() {
    return userDefineFilters;
  }

  public void setUserDefineFilters(List<UserDefineFilter> userDefineFilters) {
    this.userDefineFilters = userDefineFilters;
  }

  public List<UserNavigateFilter> getUserNavigateFilters() {
    return userNavigateFilters;
  }

  public void setUserNavigateFilters(List<UserNavigateFilter> userNavigateFilters) {
    this.userNavigateFilters = userNavigateFilters;
  }

  public FDataobject getModule() {
    return module;
  }

  public void setModule(FDataobject module) {
    this.module = module;
  }

  public String getAsName() {
    return asName;
  }

  public void setAsName(String asName) {
    this.asName = asName;
  }

  public List<UserParentFilter> getUserParentFilters() {
    return userParentFilters;
  }

  public void setUserParentFilters(List<UserParentFilter> userParentFilters) {
    this.userParentFilters = userParentFilters;
  }

  public List<UserDefineFilter> getQuerys() {
    return querys;
  }

  public void setQuerys(List<UserDefineFilter> querys) {
    this.querys = querys;
  }

}
