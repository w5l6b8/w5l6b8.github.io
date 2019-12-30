package net.ebaolife.husqvarna.framework.exception;




public class MD5Exception extends ProjectException {
 
	private static final long serialVersionUID = 1L;

	public MD5Exception() {
		super();
	}
	
	public MD5Exception(String msg) {
		super(msg);
	}
	
	public MD5Exception(String msg,Throwable obj) {
		super(msg,obj);
	}
}
