package net.ebaolife.husqvarna.framework.exception;


public class DownloadException extends ProjectException {
	private static final long serialVersionUID = 1L;
	
	private boolean showmsg = false;
	
	
	public DownloadException() {
		super();
	}
	public DownloadException(String message) {
		super(message);
	}
	
	public DownloadException(String message,boolean showmsg) {
		super(message);
		this.showmsg = showmsg;
	}
	
	public DownloadException(Throwable cause) {
		super(cause);
	}
	
	public DownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public boolean isShowmsg() {
		return showmsg;
	}

	public void setShowmsg(boolean showmsg) {
		this.showmsg = showmsg;
	}
	
	

	

}
