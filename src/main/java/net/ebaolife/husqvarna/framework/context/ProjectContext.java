package net.ebaolife.husqvarna.framework.context;

public class ProjectContext {

	private static ProjectSpace projectSpace;

	public static ProjectSpace getProjectSpace() {
		if (projectSpace == null) {
			projectSpace = new ProjectSpace();
		}
		return projectSpace;
	}

	public void setProjectSpace(ProjectSpace projectSpace) {
		ProjectContext.projectSpace = projectSpace;
	}

}
