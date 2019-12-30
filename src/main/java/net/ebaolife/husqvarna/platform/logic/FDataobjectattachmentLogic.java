package net.ebaolife.husqvarna.platform.logic;

import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.attachment.FDataobjectattachment;
import net.ebaolife.husqvarna.platform.logic.define.AbstractBaseLogic;
import net.ebaolife.husqvarna.platform.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

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
public class FDataobjectattachmentLogic extends AbstractBaseLogic<FDataobjectattachment> {

	@Autowired
	private AttachmentService attachmentService;

	@Override
	public void beforeDelete(FDataobjectattachment bean) {

		if (bean.getFDataobjectattachmentfile() != null) {
			Local.getDao().delete(bean.getFDataobjectattachmentfile());
		}

		if (bean.getFDataobjectattachmentpdffile() != null) {
			Local.getDao().delete(bean.getFDataobjectattachmentpdffile());
		}

		if (bean.getLocalpathname() != null) {
			attachmentService.deleteFile(bean);
		}

	}

}
