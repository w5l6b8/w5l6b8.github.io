package net.ebaolife.husqvarna.framework.context;


import net.ebaolife.husqvarna.framework.critical.Local;

import java.io.File;

public class ProjectSpace {
	private String workSpaceRoot;

	public String getWorkSpaceRoot() {
		return workSpaceRoot;
	}

	public void setWorkSpaceRoot(String workSpaceRoot) {
		this.workSpaceRoot = workSpaceRoot;
	}

	public String getRoot() {
		return Local.getCriticalObject().getRoot();
	}

	public String getWebInfo() {
		return getRoot() + "WEB-INF" + File.separatorChar;
	}

	public String getClassRoot() {
		return getWebInfo() + "classes" + File.separator;
	}

	public String getLibraryRoot() {
		return getWebInfo() + "lib" + File.separator;
	}

	public String getImages() {
		return getRoot() + "images" + File.separatorChar;
	}
}
