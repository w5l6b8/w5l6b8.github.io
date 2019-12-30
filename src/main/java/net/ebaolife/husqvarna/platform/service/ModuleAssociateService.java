package net.ebaolife.husqvarna.platform.service;


import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovDataobjectassociate;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovDataobjectassociatedetail;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFormscheme;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service

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
public class ModuleAssociateService {

	@Resource
	private DaoImpl dao;

	public ActionResult AddSubModule(String objectid, String fieldahead, String title, Integer orderno,
									 Boolean subobjectnavigate, Boolean subobjectsouthregion, Boolean subobjecteastregion, String region) {

		ActionResult result = new ActionResult();
		FDataobject object = dao.findById(FDataobject.class, objectid);
		for (FovDataobjectassociate assoc : object.getFovDataobjectassociates()) {
			if (assoc.getRegion().equals(region)) {
				FovDataobjectassociatedetail detail = new FovDataobjectassociatedetail();
				detail.setFovDataobjectassociate(assoc);
				detail.setFieldahead(fieldahead);
				detail.setSubobjectnavigate(subobjectnavigate);
				detail.setSubobjectsouthregion(subobjectsouthregion);
				detail.setSubobjecteastregion(subobjecteastregion);
				detail.setTitle(title);
				detail.setOrderno(orderno);
				String subobjectname = fieldahead.substring(0, fieldahead.indexOf('.'));
				detail.setFDataobjectBySubobjectid(DataObjectUtils.getDataObject(subobjectname));
				detail.setFUser(dao.findById(FUser.class, Local.getUserid()));
				dao.save(detail);
				result.setTag(detail.getAssociatedetailid());
			}
		}
		return result;
	}

	public ActionResult removeAssociatedetail(String detailid) {
		dao.delete(dao.findById(FovDataobjectassociatedetail.class, detailid));
		return new ActionResult();
	}

	public ActionResult AddForm(String objectid, String formschemeid, String title, Integer orderno, boolean usedfornew,
			boolean usedforredit, String region) {
		ActionResult result = new ActionResult();
		FDataobject object = dao.findById(FDataobject.class, objectid);
		for (FovDataobjectassociate assoc : object.getFovDataobjectassociates()) {
			if (assoc.getRegion().equals(region)) {
				FovDataobjectassociatedetail detail = new FovDataobjectassociatedetail();
				detail.setFovDataobjectassociate(assoc);
				detail.setFovFormscheme(dao.findById(FovFormscheme.class, formschemeid));

				detail.setTitle(title);
				detail.setOrderno(orderno);
				detail.setFUser(dao.findById(FUser.class, Local.getUserid()));
				dao.save(detail);
				result.setTag(detail.getAssociatedetailid());
			}
		}
		return result;
	}

	public ActionResult addUserDefine(String objectid, String name, String region) {
		ActionResult result = new ActionResult();
		FDataobject object = dao.findById(FDataobject.class, objectid);
		for (FovDataobjectassociate assoc : object.getFovDataobjectassociates()) {
			if (assoc.getRegion().equals(region)) {
				FovDataobjectassociatedetail detail = new FovDataobjectassociatedetail();
				detail.setFovDataobjectassociate(assoc);
				detail.setFUser(dao.findById(FUser.class, Local.getUserid()));
				detail.setXtype(name);
				detail.setTitle(name);
				detail.setOrderno(getMaxOrderno(assoc.getFovDataobjectassociatedetails()));
				dao.save(detail);
				result.setTag(detail.getAssociatedetailid());
			}
		}
		return result;
	}

	private int getMaxOrderno(Set<FovDataobjectassociatedetail> details) {
		int result = 0;
		for (FovDataobjectassociatedetail detail : details) {
			if (result < detail.getOrderno()) {
				result = detail.getOrderno();
			}
		}
		return result + 10;
	}

	public ActionResult addAttachment(String objectid, String region) {
		ActionResult result = new ActionResult();
		FDataobject object = dao.findById(FDataobject.class, objectid);
		for (FovDataobjectassociate assoc : object.getFovDataobjectassociates()) {
			if (assoc.getRegion().equals(region)) {
				FovDataobjectassociatedetail detail = new FovDataobjectassociatedetail();
				detail.setFovDataobjectassociate(assoc);
				detail.setFUser(dao.findById(FUser.class, Local.getUserid()));
				detail.setOrderno(getMaxOrderno(assoc.getFovDataobjectassociatedetails()));
				detail.setIsattchment(true);
				detail.setTitle("附件");
				dao.save(detail);
				result.setTag(detail.getAssociatedetailid());
			}
		}
		return result;
	}

}
