package net.ebaolife.husqvarna.framework.bean;


import net.ebaolife.husqvarna.framework.exception.EnumException;

public enum ErrorType {
	E999(999),
	E998(998);

	private final int value;
	
    private ErrorType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static ErrorType valueOf(int v) {
    	switch(v) {
	    	case 999: return E999;
	    	case 998: return E998;
    		default : throw new EnumException(" is wrong ErrorType:'"+v+"'! ");
    	}
    }
	
}
