package net.ebaolife.husqvarna.platform.logic.define;


import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;

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
public abstract class AbstractBaseLogic<T> implements LogicInterface<T> {

  @Override
  public void beforeInsert(T inserted) {
    
  }

  @Override
  public void afterInsert(T inserted) {
    
  }

  @Override
  public void beforeUpdate(String type, T updatedObject, T oldObject) {
    
  }

  @Override
  public void afterUpdate(String type, T updatedObject, T oldObject) {
    
  }

  @Override
  public void beforeDelete(T deleted) {
    
  }

  @Override
  public void afterDelete(T deleted) {
    
  }

  @Override
  public Map<String, Object> getNewDefultValue(List<UserParentFilter> userParentFilters,
      List<UserNavigateFilter> navigateFilters) {
    return null;
    
  }

}
