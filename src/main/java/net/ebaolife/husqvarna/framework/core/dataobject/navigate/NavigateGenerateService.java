package net.ebaolife.husqvarna.framework.core.dataobject.navigate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.TreeValueText;
import net.ebaolife.husqvarna.framework.core.dataobject.ModuleDataDAO;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.column.ColumnField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.generate.SqlGenerate;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigatescheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigateschemedetail;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
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
public class NavigateGenerateService {

	@Resource
	private ModuleDataDAO moduleDataDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public JSONObject genNavigateTree(String moduleName, String navigateschemeId, String parentFilter,
			Boolean cascading, Boolean isContainNullRecord) {
		isContainNullRecord = isContainNullRecord == null ? false : isContainNullRecord;
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		List<FovGridnavigateschemedetail> navigateDetails = new ArrayList<FovGridnavigateschemedetail>();
		FovGridnavigatescheme navigateScheme = Local.getDao().findById(FovGridnavigatescheme.class, navigateschemeId);
		if (navigateScheme != null) {
			navigateDetails.addAll(navigateScheme.getDetails());
		} else {
			FovGridnavigateschemedetail fieldDetail = new FovGridnavigateschemedetail();
			FDataobjectfield mf = module._getModuleFieldByFieldName(navigateschemeId);
			fieldDetail.setTitle(mf.getFieldtitle());
			fieldDetail.setFDataobjectfield(mf);
			fieldDetail.setOrderno(1);
			FDataobject m = DataObjectUtils.getDataObject(mf.getFieldtype());
			if (m != null && m._isCodeLevel())
				fieldDetail.setAddcodelevel(true);
			navigateDetails.add(fieldDetail);
		}
		List<UserParentFilter> userParentFilters = UserParentFilter.changeToParentFilters(parentFilter, moduleName);
		SqlGenerate sqlgenerate = new SqlGenerate(module);
		sqlgenerate.setAddIdField(true);
		sqlgenerate.setAddNameField(false);
		sqlgenerate.setAddBaseField(false);
		sqlgenerate.setAddAllFormScheme(false);
		sqlgenerate.setAddAllGridScheme(false);
		sqlgenerate.setUserParentFilters(userParentFilters);
		JSONObject result = new JSONObject();
		result.put("expanded", true);
		JSONArray children = new JSONArray();
		for (FovGridnavigateschemedetail schemeDetail : navigateDetails) {
			List<FovGridnavigateschemedetail> sds = new ArrayList<FovGridnavigateschemedetail>();
			sds.add(schemeDetail);
			children.add(genAllLevel(sqlgenerate, isContainNullRecord, sds.get(0)));
		}
		result.put("children", children);
		return result;

	}








	private JSONObject genAllLevel(SqlGenerate sqlgenerate, Boolean isContainNullRecord,
			FovGridnavigateschemedetail navigateSchemeDetail) {

		FovGridnavigateschemedetail detail = navigateSchemeDetail;
		JSONArray datas = null;

		if (isContainNullRecord && !(detail.getFieldahead() == null && detail.getFDataobjectfield()._isBaseField())) {
			String[] paths = detail._getFactAheadPath().split("\\.");
			FDataobject module = sqlgenerate.getBaseModule().getModule();
			FDataobject pm = sqlgenerate.getBaseModule().getModule(); 
			for (String path : paths) {
				pm = DataObjectUtils.getDataObject(pm._getModuleFieldByFieldName(path).getFieldtype());
			}
			
			String ahead = module.getObjectname() + ".with." + detail._getFactAheadPath();
			List<ColumnField> cFields = new ArrayList<ColumnField>();
			ColumnField aField = new ColumnField();
			aField.setRemark("count_");
			aField.setAggregate("count");
			aField.setFDataobjectfield(module._getPrimaryKeyField());
			aField.setFieldahead(ahead);
			cFields.add(aField);
			
			SqlGenerate sqlgenerate1 = new SqlGenerate(pm);

			sqlgenerate1.setAddIdField(false);
			sqlgenerate1.setAddNameField(false);
			sqlgenerate1.setAddBaseField(false);
			sqlgenerate1.setAddAllFormScheme(false);
			sqlgenerate1.setAddAllGridScheme(false);
			sqlgenerate1.setColumnFields(cFields);

			NavigateSQLGenerate sqlGenerate = new NavigateSQLGenerate(sqlgenerate1, navigateSchemeDetail);
			datas = moduleDataDAO.getData(sqlGenerate.generageNavigateSqlWithAllParentField(),
					sqlGenerate.getFields().keySet(), -1, -1);

		} else {
			NavigateSQLGenerate sqlGenerate = new NavigateSQLGenerate(sqlgenerate, navigateSchemeDetail);
			datas = moduleDataDAO.getData(sqlGenerate.generageNavigateSql(), sqlGenerate.getFields().keySet(), -1, -1);
		}
		List<NavigateData> navigateDatas = new ArrayList<NavigateData>();
		for (int i = 0; i < datas.size(); i++) {
			NavigateData data = new NavigateData(datas.getJSONObject(i), 1);
			navigateDatas.add(data);
		}
		int allcount = 0;
		JSONArray firstlevels = new JSONArray();
		for (NavigateData data : navigateDatas) {
			allcount += data.getCount();
			firstlevels.add(data.genJsonObject(sqlgenerate, navigateSchemeDetail));
		}

		JSONObject result = new JSONObject();
		result.put("text", navigateSchemeDetail.getTitle());
		result.put("iconCls", navigateSchemeDetail.getIconcls());
		result.put("leaf", false);
		result.put("expanded", true);
		result.put("count", allcount);
		result.put("children", firstlevels);
		return result;
	}

	public List<NavigateData> fromTreeValueToNavigateData(List<TreeValueText> valueTexts,
			List<NavigateData> navigateDatas, int level, Boolean isContainNullRecord) {
		List<NavigateData> result = new ArrayList<NavigateData>();
		for (TreeValueText treeValue : valueTexts) {
			
			NavigateData navigateData = null;
			for (NavigateData navData : navigateDatas) {
				if (navData.getKey().equals(treeValue.getValue())) {
					navigateData = navData; 
					break;
				}
			}
			if (navigateData == null) {
				
				navigateData = new NavigateData();
				navigateData.setKey(treeValue.getValue());
				navigateData.setLevel(level);
				navigateData.setCount(0);
				navigateData.setName(treeValue.getText());
				navigateData.setOperator("=");
			}
			if (treeValue.hasChildren()) {
				List<NavigateData> children = fromTreeValueToNavigateData(treeValue.getChildren(), navigateDatas, level,
						isContainNullRecord);
				if (navigateData.getChildren() != null)
					navigateData.getChildren().addAll(children);
				else
					navigateData.setChildren(children);
				for (NavigateData na : navigateData.getChildren()) {
					navigateData.setCount(navigateData.getCount() + na.getCount());
				}
				if (treeValue.getParenttype() != null && treeValue.getParenttype() == ModuleDataDAO.PARENTWITHPARENTID)
					navigateData.setOperator("allchildren");
				else
					navigateData.setOperator("like");
			}
			if (isContainNullRecord || navigateData.getCount() > 0)
				result.add(navigateData);
			System.out.println(navigateData.getKey());
		}
		return result;
	}

}
