package net.ebaolife.husqvarna.framework.exception;

import java.util.ArrayList;
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
public class DataDeleteException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private List<String> errorMessage;

  public DataDeleteException(List<String> errorMessage) {
    super();
    this.errorMessage = errorMessage;
  }

  public DataDeleteException(String msg) {
    super(msg);
    errorMessage = new ArrayList<String>();
    errorMessage.add(msg);
  }

  public DataDeleteException(Throwable cause) {
    super(cause);
  }

  public DataDeleteException(String msg, Throwable obj) {
    super(msg, obj);
  }

  public List<String> getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(List<String> errorMessage) {
    this.errorMessage = errorMessage;
  }



}
