package net.ebaolife.husqvarna.framework.exception;


public class ConnectOpenOfficeException extends ProjectException {
  private static final long serialVersionUID = 1L;


  public ConnectOpenOfficeException() {
    super("不能打开把文件转换成pdf的OpenOffice服务!");
  }

  public ConnectOpenOfficeException(String message) {
    super(message);
  }

  public ConnectOpenOfficeException(Throwable cause) {
    super(cause);
  }

  public ConnectOpenOfficeException(String message, Throwable cause) {
    super(message, cause);
  }



}
