package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FAdditionfield;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FAdditionfieldexpression;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import net.ebaolife.husqvarna.framework.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

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
public class DataobjectFieldService {

	@Resource
	private DaoImpl dao;

	public JSONObject getUserDefineFieldExpression(String additionfieldid) {
		JSONArray array = new JSONArray();
		FAdditionfield additionfield = dao.findById(FAdditionfield.class, additionfieldid);
		for (FAdditionfieldexpression expression : additionfield.getExpressions()) {
			array.add(expression.genJsonObject(additionfield.getFDataobject()));
		}
		JSONObject result = new JSONObject();
		result.put("leaf", false);
		result.put("children", array);
		return result;
	}

	public ActionResult updateUserDefineFieldExpression(String additionfieldid, String schemeDefine) {
		FAdditionfield additionField = dao.findById(FAdditionfield.class, additionfieldid);
		for (FAdditionfieldexpression limit : additionField.getExpressions()) {
			dao.delete(limit);
		}
		JSONObject object = JSONObject.parseObject("{ children :" + schemeDefine + "}");
		JSONArray arrays = (JSONArray) object.get("children");
		saveNewDetails(additionField, arrays, null);
		ActionResult result = new ActionResult();
		return result;
	}

	private void saveNewDetails(FAdditionfield additionField, JSONArray arrays, FAdditionfieldexpression p) {
		for (int i = 0; i < arrays.size(); i++) {
			JSONObject detailObject = arrays.getJSONObject(i);
			FAdditionfieldexpression detail = new FAdditionfieldexpression();
			detail.setFAdditionfield(additionField);
			detail.setFAdditionfieldexpression(p);
			if (detailObject.containsKey("title"))
				detail.setTitle(detailObject.getString("title"));
			if (detailObject.containsKey("userfunction"))
				detail.setUserfunction(detailObject.getString("userfunction"));
			if (detailObject.containsKey("remark"))
				detail.setRemark(detailObject.getString("remark"));
			if (detailObject.containsKey("functionid"))
				detail.setFFunction(new FFunction(detailObject.getString("functionid")));
			detail.setOrderno((i + 1) * 10);
			if (detailObject.containsKey("fieldid"))
				ParentChildField.updateToField(detail, detailObject.getString("fieldid"));
			dao.save(detail);
			if (detailObject.containsKey("children")) {
				saveNewDetails(null, (JSONArray) detailObject.get("children"), detail);
			}
		}
	}

	public ActionResult createManyToManyField(String fieldtype1, String fieldtype2, String linkedobjectid,
			String userid) {

		FDataobject object1 = DataObjectUtils.getDataObject(fieldtype1);
		FDataobject object2 = DataObjectUtils.getDataObject(fieldtype2);
		FDataobject linkedobject = dao.findById(FDataobject.class, linkedobjectid);

		FDataobjectfield field1 = new FDataobjectfield();
		field1.setFieldname(fieldtype2 + "s");
		field1.setFieldtitle(object2.getTitle());
		field1.setFieldrelation("manytomany");
		field1.setFieldtype("Set<" + object2.getObjectname() + ">");
		field1.setFieldgroup("多对多组");
		field1.setJointable(linkedobject.getObjectname());
		field1.setOrderno(10);
		field1.setCreater(userid == null ? Local.getUserid() : userid);
		field1.setCreatedate(new Timestamp(new Date().getTime()));
		field1.setFDataobject(object1);
		dao.save(field1);

		FDataobjectfield field2 = new FDataobjectfield();
		field2.setFieldname(fieldtype1 + "s");
		field2.setFieldtitle(object1.getTitle());
		field2.setFieldrelation("manytomany");
		field2.setFieldtype("Set<" + object1.getObjectname() + ">");
		field2.setFieldgroup("多对多组");
		field2.setJointable(linkedobject.getObjectname());
		field2.setOrderno(10);
		field2.setCreater(userid == null ? Local.getUserid() : userid);
		field2.setCreatedate(new Timestamp(new Date().getTime()));
		field2.setFDataobject(object2);
		dao.save(field2);

		return new ActionResult();
	}

	public ActionResult createOneToManyField(String fieldid) {
		FDataobjectfield objectfield = dao.findById(FDataobjectfield.class, fieldid);
		FDataobject oneobject = DataObjectUtils.getDataObject(objectfield.getFieldtype());
		FDataobject manyobject = objectfield.getFDataobject();
		FDataobjectfield onetomanyfield = new FDataobjectfield();
		onetomanyfield.setFDataobject(oneobject);
		onetomanyfield.setCreatedate(DateUtils.getTimestamp());
		onetomanyfield.setCreater(Local.getUserid());
		onetomanyfield.setFieldrelation("onetomany");
		onetomanyfield.setJointable(manyobject.getTablename());
		onetomanyfield.setJoincolumnname(objectfield.getJoincolumnname());
		onetomanyfield.setFieldtitle(objectfield.getFieldtitle() + "的" + manyobject.getTitle());
		onetomanyfield.setFieldlen(0);
		onetomanyfield.setFieldtype("Set<" + manyobject.getObjectname() + ">");
		onetomanyfield.setFieldgroup("一对多组");

		if (oneobject._getPrimaryKeyField()._getSelectName(null).equals(objectfield.getJoincolumnname())) {
			onetomanyfield.setFieldname(manyobject.getObjectname() + "s");
		} else {

			onetomanyfield.setFieldname(
					manyobject.getObjectname() + "By" + objectfield.getJoincolumnname().substring(0, 1).toUpperCase()
							+ objectfield.getJoincolumnname().substring(1) + "s");
		}
		onetomanyfield.setFieldahead(manyobject.getObjectname() + ".with." + objectfield.getFieldname());
		System.out.println(onetomanyfield.getFieldtitle() + "    " + onetomanyfield.getFieldname());
		dao.save(onetomanyfield);
		return new ActionResult();
	}
}
