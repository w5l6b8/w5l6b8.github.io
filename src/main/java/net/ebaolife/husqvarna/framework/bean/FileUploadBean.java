package net.ebaolife.husqvarna.framework.bean;

import net.ebaolife.husqvarna.framework.dao.entity.attachment.FDataobjectattachment;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@SuppressWarnings("serial")
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
public class FileUploadBean extends FDataobjectattachment {

	private CommonsMultipartFile file;

	private String objectid;

	public FileUploadBean() {

	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

}
