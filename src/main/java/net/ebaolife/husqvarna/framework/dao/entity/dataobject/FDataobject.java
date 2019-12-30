package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean._ModuleAdditionField;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.datainorout.FRecordexcelscheme;
import net.ebaolife.husqvarna.framework.dao.entity.datamining.FDataminingexpandgroup;
import net.ebaolife.husqvarna.framework.dao.entity.datamining.FDataminingscheme;
import net.ebaolife.husqvarna.framework.dao.entity.module.FModule;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.*;
import net.ebaolife.husqvarna.framework.utils.HierarchyIDPIDUtils;
import net.ebaolife.husqvarna.framework.utils.ObjectFunctionUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

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

@Entity
@DynamicUpdate
@Table(name = "f_dataobject", uniqueConstraints = {
		@UniqueConstraint(columnNames = "nativename", name = "IX_dataobjectnativename"),
		@UniqueConstraint(columnNames = { "objectname" }, name = "IX_dataobjectname"),
		@UniqueConstraint(columnNames = { "classname" }, name = "IX_dataobjectclassname") })
public class FDataobject implements java.io.Serializable {

	private static final long serialVersionUID = 672376593959947691L;

	private String objectid;
	private FDataobjectgroup FDataobjectgroup;
	private String objectname;
	private String nativename;
	private String schemaname;
	private String datasourcename;
	private String tablename;
	private String classname;
	private String title;
	private String shortname;
	private String englishname;
	private String primarykey;
	private String parentkey;
	private String namefield;
	private String codefield;
	private String datefield;
	private String yearfield;
	private String monthfield;
	private String seasonfield;
	private String orderfield;
	private String orderfieldcontroltable;
	private String orderby;
	private String description;
	private String iconurl;
	private String iconcls;
	private byte[] iconfile;
	private String selectedmode;
	private Boolean istreemodel;
	private String codelevel;
	private Boolean hasenable;
	private Boolean hasbrowse;
	private Boolean hasinsert;
	private Boolean hasedit;
	private Boolean hasdelete;
	private Boolean hasaudit;
	private Boolean hasapprove;
	private Boolean hasattachment;
	private Boolean hasrecordicon;
	private Boolean allowinsertexcel;
	private Boolean alloweditexcel;
	private Boolean haschart;
	private Boolean hasdatamining;
	private Boolean withoutnean;
	private Boolean issystem;

	private Boolean griddesign;
	private Boolean gridshare;
	private Boolean formdesign;
	private Boolean formshare;
	private Boolean viewdesign;
	private Boolean viewshare;
	private Boolean navigatedesign;
	private Boolean navigateshare;
	private Boolean filterdesign;
	private Boolean filtershare;
	private Boolean conditiondesign;
	private Boolean conditionshare;
	private Boolean sortdesign;
	private Boolean sortshare;

	private Integer orderno;
	private String sqlstatement;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;
	private Set<FDataobjectdefaultorder> FDataobjectdefaultorders = new HashSet<FDataobjectdefaultorder>(0);
	private Set<FovGridsortscheme> fovGridsortschemes = new HashSet<FovGridsortscheme>(0);
	private Set<FovGridscheme> fovGridschemes = new HashSet<FovGridscheme>(0);
	private Set<FovFormscheme> fovFormschemes = new HashSet<FovFormscheme>(0);
	private Set<FovFormschemedetail> fovFormschemedetails = new HashSet<FovFormschemedetail>(0);
	private Set<FovGridnavigatescheme> fovGridnavigateschemes = new HashSet<FovGridnavigatescheme>(0);
	private Set<FDataobjectfield> FDataobjectfields = new HashSet<FDataobjectfield>(0);
	private Set<FModule> FModules = new HashSet<FModule>(0);
	private Set<FDataobjectcondition> FDataobjectconditions = new HashSet<FDataobjectcondition>(0);
	private Set<FovFilterscheme> fovFilterschemes = new HashSet<FovFilterscheme>(0);
	private Set<FDataobjectview> FDataobjectviews = new HashSet<FDataobjectview>(0);
	private Set<FDataobjectadditionfuncion> FDataobjectadditionfuncions = new HashSet<FDataobjectadditionfuncion>(0);
	private Set<FovDataobjectassociate> fovDataobjectassociates = new HashSet<FovDataobjectassociate>(0);
	private Set<FovChartscheme> fovChartschemes = new HashSet<FovChartscheme>(0);
	private Set<FDataobjectfieldconstraint> FDataobjectfieldconstraints = new HashSet<FDataobjectfieldconstraint>(0);
	private Set<FDataobjectsqlparam> FDataobjectsqlparams = new HashSet<FDataobjectsqlparam>(0);
	private Set<FDataminingscheme> FDataminingschemes = new HashSet<FDataminingscheme>(0);
	private Set<FDataminingexpandgroup> FDataminingexpandgroups = new HashSet<FDataminingexpandgroup>(0);
	private Set<FRecordexcelscheme> FRecordexcelschemes = new HashSet<FRecordexcelscheme>(0);

	public FDataobject() {
	}

	public FDataobject(String objectid) {
		this.objectid = objectid;
	}

	private Set<_ModuleAdditionField> additionFields = new HashSet<_ModuleAdditionField>(0);

	@Transient
	public Set<_ModuleAdditionField> getAdditionFields() {
		return this.additionFields;
	}

	public boolean addAdditionField(String fieldname, FDataobjectfield field, String fieldahead, String aggregate,
			FDataobjectcondition condition) {
		for (_ModuleAdditionField f : additionFields) {
			if (f.getFieldname().equals(fieldname))
				return false;
		}
		_ModuleAdditionField af = new _ModuleAdditionField();
		af.setFDataobjectfield(field);
		af.setFieldahead(fieldahead);
		af.setAggregate(aggregate);
		af.setFieldname(fieldname);
		af.setFDataobjectconditionBySubconditionid(condition);
		additionFields.add(af);
		return true;
	}

	@Transient
	public Map<String, Boolean> getBaseFunctions() {
		return ObjectFunctionUtils.getBaseFunctions(objectid);
	}

	@Transient
	public List<FDataobjectadditionfuncion> getAdditionFunctions() {
		return ObjectFunctionUtils.getAdditionFunctions(this);
	}

	@Transient
	public Map<String, Set<FovGridscheme>> getGridSchemes() {
		Map<String, Set<FovGridscheme>> result = new LinkedHashMap<String, Set<FovGridscheme>>();
		Set<FovGridscheme> system = new HashSet<FovGridscheme>();
		Set<FovGridscheme> owner = new HashSet<FovGridscheme>();
		Set<FovGridscheme> othershare = new HashSet<FovGridscheme>();
		for (FovGridscheme scheme : fovGridschemes) {
			if (scheme.getFUser() == null)
				system.add(scheme);
			else if (scheme.getFUser().getUserid().equals(Local.getUserid()))
				owner.add(scheme);
			else {
				scheme.setSchemename(scheme.getSchemename() + "(" + scheme.getFUser().getUsername() + ")");
				othershare.add(scheme);
			}
		}
		if (system.size() > 0)
			result.put("system", system);
		if (owner.size() > 0)
			result.put("owner", owner);
		if (othershare.size() > 0)
			result.put("othershare", othershare);
		return result;
	}

	@Transient
	public Map<String, Set<FDataobjectview>> getViewSchemes() {
		Map<String, Set<FDataobjectview>> result = new LinkedHashMap<String, Set<FDataobjectview>>();
		Set<FDataobjectview> system = new HashSet<FDataobjectview>();
		Set<FDataobjectview> owner = new HashSet<FDataobjectview>();
		Set<FDataobjectview> othershare = new HashSet<FDataobjectview>();
		for (FDataobjectview scheme : FDataobjectviews) {
			if (scheme.getFUser() == null)
				system.add(scheme);
			else if (scheme.getFUser().getUserid().equals(Local.getUserid()))
				owner.add(scheme);
			else {
				scheme.setTitle(scheme.getTitle() + "(" + scheme.getFUser().getUsername() + ")");
				othershare.add(scheme);
			}
		}
		if (system.size() > 0)
			result.put("system", system);
		if (owner.size() > 0)
			result.put("owner", owner);
		if (othershare.size() > 0)
			result.put("othershare", othershare);
		return result;
	}

	@Transient
	public Map<String, Set<JSONObject>> getSortSchemes() {
		if (sortdesign != null && sortdesign) {
			Map<String, Set<JSONObject>> result = new LinkedHashMap<String, Set<JSONObject>>();
			Set<JSONObject> system = new HashSet<JSONObject>();
			Set<JSONObject> owner = new HashSet<JSONObject>();
			Set<JSONObject> othershare = new HashSet<JSONObject>();
			for (FovGridsortscheme scheme : fovGridsortschemes) {
				if (scheme.getFUser() == null)
					system.add(scheme._genJson());
				else if (scheme.getFUser().getUserid().equals(Local.getUserid()))
					owner.add(scheme._genJson());
				else {
					JSONObject jo = scheme._genJson();
					jo.put("schemename", jo.get("schemename") + "(" + scheme.getFUser().getUsername() + ")");
					othershare.add(jo);
				}
			}
			if (system.size() > 0)
				result.put("system", system);
			if (owner.size() > 0)
				result.put("owner", owner);
			if (othershare.size() > 0)
				result.put("othershare", othershare);
			return result;
		} else
			return null;
	}

	@Transient
	public String getGridDefaultSchemeId() {
		FUser user = Local.getDao().findById(FUser.class, Local.getUserid());
		for (FovUserdefaultgridscheme scheme : user.getFovUserdefaultgridschemes()) {
			if (scheme.getFovGridscheme().getFDataobject().getObjectid().equals(getObjectid()))
				return scheme.getFovGridscheme().getGridschemeid();
		}
		return null;
	}

	@Transient
	public Map<String, Set<FovFilterscheme>> getFilterSchemes() {
		Map<String, Set<FovFilterscheme>> result = new LinkedHashMap<String, Set<FovFilterscheme>>();
		Set<FovFilterscheme> system = new HashSet<FovFilterscheme>();
		Set<FovFilterscheme> owner = new HashSet<FovFilterscheme>();
		Set<FovFilterscheme> othershare = new HashSet<FovFilterscheme>();
		for (FovFilterscheme scheme : fovFilterschemes) {
			if (scheme.getFUser() == null)
				system.add(scheme);
			else if (scheme.getFUser().getUserid().equals(Local.getUserid()))
				owner.add(scheme);
			else {
				scheme.setSchemename(scheme.getSchemename() + "(" + scheme.getFUser().getUsername() + ")");
				othershare.add(scheme);
			}
		}
		if (system.size() > 0)
			result.put("system", system);
		if (owner.size() > 0)
			result.put("owner", owner);
		if (othershare.size() > 0)
			result.put("othershare", othershare);
		return result;
	}

	@Transient
	public String getFilterDefaultSchemeId() {
		FUser user = Local.getDao().findById(FUser.class, Local.getUserid());
		for (FovUserdefaultfilterscheme scheme : user.getFovUserdefaultfilterschemes()) {
			if (scheme.getFovFilterscheme().getFDataobject().getObjectid().equals(getObjectid()))
				return scheme.getFovFilterscheme().getFilterschemeid();
		}
		return null;
	}

	@Transient
	public JSONArray getNavigateSchemes() {
		JSONArray schemes = new JSONArray();

		for (FovGridnavigatescheme scheme : getFovGridnavigateschemes())
			if (scheme.getEnabled())
				schemes.add(scheme.genJson());

		for (FDataobjectfield field : FDataobjectfields) {
			if (field.getIsdisable() == null || !field.getIsdisable())
				if (field.getShownavigatortree() != null && field.getShownavigatortree()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("navigateschemeid", field.getFieldname());
					jsonObject.put("tf_order", "item_" + field.getFieldid());
					jsonObject.put("tf_text", field.getFieldtitle());
					jsonObject.put("tf_iconCls", field.getIconcls());
					jsonObject.put("tf_allLevel", 1);
					jsonObject.put("noscheme", true);
					jsonObject.put("issystem", true);
					jsonObject.put("tf_username", "系统方案");
					if (field._isManyToOne() || field._isOneToOne())
						jsonObject.put("tf_allowNullRecordButton", true);
					schemes.add(jsonObject);
				}
		}
		if (schemes.size() > 0)
			return schemes;
		else
			return null;
	}

	@Transient
	public String getNavigateDefaultSchemeId() {
		FUser user = Local.getDao().findById(FUser.class, Local.getUserid());
		for (FovUserdefaultnavigatescheme scheme : user.getFovUserdefaultnavigateschemes()) {
			if (scheme.getFovGridnavigatescheme().getFDataobject().getObjectid().equals(getObjectid()))
				return scheme.getFovGridnavigatescheme().getSchemeid();
		}
		return null;
	}

	@Transient
	public JSONArray getExcelschemes() {
		JSONArray result = new JSONArray();
		for (FRecordexcelscheme scheme : getFRecordexcelschemes()) {
			if (scheme.getIsdisable() == null || !scheme.getIsdisable())
				result.add(scheme._getJsonData());
		}
		return result.size() > 0 ? result : null;
	}

	public FDataobjectfield _getParentKeyField() {
		for (FDataobjectfield field : FDataobjectfields)
			if (field.getFieldname().equals(getParentkey())) {
				return field;
			}
		System.out.println("模块：" + this.getObjectname() + ":未找到父键");
		return null;
	}

	public FDataobjectfield _getPrimaryKeyField() {
		for (FDataobjectfield field : FDataobjectfields)
			if (field.getFieldname().equals(getPrimarykey())) {
				return field;
			}
		System.out.println("模块：" + this.getObjectname() + ":未找到主键");
		return null;
	}

	public FDataobjectfield _getNameField() {
		for (FDataobjectfield field : FDataobjectfields)
			if (field.getFieldname() != null && field.getFieldname().equalsIgnoreCase(namefield)) {
				return field;
			}
		return _getPrimaryKeyField();
	}

	public FDataobjectfield _getPidField() {
		for (FDataobjectfield field : FDataobjectfields)
			if (field.getFieldname() != null && field.getFieldname().equalsIgnoreCase(parentkey)) {
				return field;
			}
		return _getPrimaryKeyField();
	}

	public FDataobjectfield _getModuleFieldByFieldName(String fieldName) {
		for (FDataobjectfield field : FDataobjectfields)
			if (field.getFieldname().equals(fieldName)) {
				return field;
			}
		return null;
	}

	public FDataobjectfield _getModuleFieldByFieldId(String fieldid) {
		for (FDataobjectfield field : FDataobjectfields)
			if (field.getFieldid().equals(fieldid)) {
				return field;
			}
		return null;
	}

	public int _getCodeLevelLength(int level) {
		String[] levels = codelevel.split(",");
		int length = 0;
		for (int i = 0; i < level; i++) {
			if (i < levels.length) {
				length += Integer.parseInt(levels[i]);
			} else
				throw new RuntimeException("取得" + title + "的_getLevelExpression时,level数值太大！");
		}
		return length;
	}

	public String _getLevelExpression(int level, String fieldexpression) {

		if (codelevel != null && codelevel.length() > 0) {

			String[] levels = codelevel.split(",");
			int length = 0;
			for (int i = 0; i < level; i++) {
				if (i < levels.length) {
					length += Integer.parseInt(levels[i]);
				} else
					throw new RuntimeException("取得" + title + "的_getLevelExpression时,level数值太大！");
			}
			return "substr( " + fieldexpression + " ,1," + length + ")";
		} else {
			return HierarchyIDPIDUtils._getIDPIDExpression(level, fieldexpression,
					HierarchyIDPIDUtils.getHierarchyIDPIDFromRequest(this));
		}
	}

	public boolean _isCodeLevel() {
		return (codelevel != null && codelevel.length() > 0);
	}

	public boolean _isIdPidLevel() {
		return (parentkey != null && parentkey.length() > 0);
	}

	public String _getTablename() {
		return ((schemaname == null || schemaname.length() == 0) ? "" : schemaname + ".")
				+ (tablename == null || tablename.length() == 0 ? objectname : tablename);
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "objectid", unique = true, nullable = false, length = 40)

	public String getObjectid() {
		return this.objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectgroupid")

	public FDataobjectgroup getFDataobjectgroup() {
		return this.FDataobjectgroup;
	}

	public void setFDataobjectgroup(FDataobjectgroup FDataobjectgroup) {
		this.FDataobjectgroup = FDataobjectgroup;
	}

	@Column(name = "schemaname", length = 50)
	public String getSchemaname() {
		return schemaname;
	}

	public void setSchemaname(String schemaname) {
		this.schemaname = schemaname;
	}

	@Column(name = "datasourcename", length = 50)

	public String getDatasourcename() {
		return datasourcename;
	}

	public void setDatasourcename(String datasourcename) {
		this.datasourcename = datasourcename;
	}

	@Column(name = "tablename", nullable = false, length = 30)

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	@Column(name = "objectname", nullable = false, length = 50)
	public String getObjectname() {
		return objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	@Column(name = "nativename", unique = true, length = 4)
	public String getNativename() {
		return this.nativename;
	}

	public void setNativename(String nativename) {
		this.nativename = nativename;
	}

	@Column(name = "classname", length = 200)

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Column(name = "title", length = 100)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "shortname", length = 60)

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "englishname", length = 60)

	public String getEnglishname() {
		return this.englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	@Column(name = "primarykey", length = 60)

	public String getPrimarykey() {
		return this.primarykey;
	}

	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}

	@Column(name = "parentkey", length = 60)

	public String getParentkey() {
		return this.parentkey;
	}

	public void setParentkey(String parentkey) {
		this.parentkey = parentkey;
	}

	@Column(name = "namefield", length = 60)

	public String getNamefield() {
		return this.namefield;
	}

	public void setNamefield(String namefield) {
		this.namefield = namefield;
	}

	@Column(name = "codefield", length = 60)

	public String getCodefield() {
		return this.codefield;
	}

	public void setCodefield(String codefield) {
		this.codefield = codefield;
	}

	@Column(name = "datefield", length = 60)

	public String getDatefield() {
		return this.datefield;
	}

	public void setDatefield(String datefield) {
		this.datefield = datefield;
	}

	@Column(name = "yearfield", length = 60)

	public String getYearfield() {
		return this.yearfield;
	}

	public void setYearfield(String yearfield) {
		this.yearfield = yearfield;
	}

	@Column(name = "monthfield", length = 60)

	public String getMonthfield() {
		return this.monthfield;
	}

	public void setMonthfield(String monthfield) {
		this.monthfield = monthfield;
	}

	@Column(name = "seasonfield", length = 60)

	public String getSeasonfield() {
		return this.seasonfield;
	}

	public void setSeasonfield(String seasonfield) {
		this.seasonfield = seasonfield;
	}

	@Column(name = "orderfield", length = 60)

	public String getOrderfield() {
		return this.orderfield;
	}

	public void setOrderfield(String orderfield) {
		this.orderfield = orderfield;
	}

	@Column(name = "orderfieldcontroltable", length = 60)

	public String getOrderfieldcontroltable() {
		return this.orderfieldcontroltable;
	}

	public void setOrderfieldcontroltable(String orderfieldcontroltable) {
		this.orderfieldcontroltable = orderfieldcontroltable;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	@Column(name = "description", length = 200)

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "iconurl", length = 200)

	public String getIconurl() {
		return this.iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	@Column(name = "iconcls", length = 50)

	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "iconfile")

	public byte[] getIconfile() {
		return this.iconfile;
	}

	public void setIconfile(byte[] iconfile) {
		this.iconfile = iconfile;
	}

	@Column(name = "selectedmode", length = 60)

	public String getSelectedmode() {
		return this.selectedmode;
	}

	public void setSelectedmode(String selectedmode) {
		this.selectedmode = selectedmode;
	}

	@Column(name = "istreemodel")

	public Boolean getIstreemodel() {
		return this.istreemodel;
	}

	public void setIstreemodel(Boolean istreemodel) {
		this.istreemodel = istreemodel;
	}

	@Column(name = "codelevel", length = 30)

	public String getCodelevel() {
		return this.codelevel;
	}

	public void setCodelevel(String codelevel) {
		this.codelevel = codelevel;
	}

	@Column(name = "hasenable")

	public Boolean getHasenable() {
		return this.hasenable;
	}

	public void setHasenable(Boolean hasenable) {
		this.hasenable = hasenable;
	}

	@Column(name = "hasbrowse")

	public Boolean getHasbrowse() {
		return this.hasbrowse;
	}

	public void setHasbrowse(Boolean hasbrowse) {
		this.hasbrowse = hasbrowse;
	}

	@Column(name = "hasinsert")

	public Boolean getHasinsert() {
		return this.hasinsert;
	}

	public void setHasinsert(Boolean hasinsert) {
		this.hasinsert = hasinsert;
	}

	@Column(name = "hasedit")

	public Boolean getHasedit() {
		return this.hasedit;
	}

	public void setHasedit(Boolean hasedit) {
		this.hasedit = hasedit;
	}

	@Column(name = "hasdelete")

	public Boolean getHasdelete() {
		return this.hasdelete;
	}

	public void setHasdelete(Boolean hasdelete) {
		this.hasdelete = hasdelete;
	}

	@Column(name = "hasaudit")

	public Boolean getHasaudit() {
		return this.hasaudit;
	}

	public void setHasaudit(Boolean hasaudit) {
		this.hasaudit = hasaudit;
	}

	@Column(name = "hasapprove")

	public Boolean getHasapprove() {
		return this.hasapprove;
	}

	public void setHasapprove(Boolean hasapprove) {
		this.hasapprove = hasapprove;
	}

	@Column(name = "hasattachment")

	public Boolean getHasattachment() {
		return this.hasattachment;
	}

	public void setHasattachment(Boolean hasattachment) {
		this.hasattachment = hasattachment;
	}

	@Column(name = "hasrecordicon")

	public Boolean getHasrecordicon() {
		return this.hasrecordicon;
	}

	public void setHasrecordicon(Boolean hasrecordicon) {
		this.hasrecordicon = hasrecordicon;
	}

	@Column(name = "allowinsertexcel")

	public Boolean getAllowinsertexcel() {
		return this.allowinsertexcel;
	}

	public void setAllowinsertexcel(Boolean allowinsertexcel) {
		this.allowinsertexcel = allowinsertexcel;
	}

	@Column(name = "alloweditexcel")

	public Boolean getAlloweditexcel() {
		return this.alloweditexcel;
	}

	public void setAlloweditexcel(Boolean alloweditexcel) {
		this.alloweditexcel = alloweditexcel;
	}

	@Column(name = "haschart")

	public Boolean getHaschart() {
		return this.haschart;
	}

	public void setHaschart(Boolean haschart) {
		this.haschart = haschart;
	}

	public Boolean getHasdatamining() {
		return hasdatamining;
	}

	public void setHasdatamining(Boolean hasdatamining) {
		this.hasdatamining = hasdatamining;
	}

	@Column(name = "withoutnean")

	public Boolean getWithoutnean() {
		return this.withoutnean;
	}

	public void setWithoutnean(Boolean withoutnean) {
		this.withoutnean = withoutnean;
	}

	@Column(name = "issystem")

	public Boolean getIssystem() {
		return this.issystem;
	}

	public void setIssystem(Boolean issystem) {
		this.issystem = issystem;
	}

	public Boolean getGriddesign() {
		return griddesign;
	}

	public void setGriddesign(Boolean griddesign) {
		this.griddesign = griddesign;
	}

	public Boolean getGridshare() {
		return gridshare;
	}

	public void setGridshare(Boolean gridshare) {
		this.gridshare = gridshare;
	}

	public Boolean getFormdesign() {
		return formdesign;
	}

	public void setFormdesign(Boolean formdesign) {
		this.formdesign = formdesign;
	}

	public Boolean getFormshare() {
		return formshare;
	}

	public void setFormshare(Boolean formshare) {
		this.formshare = formshare;
	}

	public Boolean getViewdesign() {
		return viewdesign;
	}

	public void setViewdesign(Boolean viewdesign) {
		this.viewdesign = viewdesign;
	}

	public Boolean getViewshare() {
		return viewshare;
	}

	public void setViewshare(Boolean viewshare) {
		this.viewshare = viewshare;
	}

	public Boolean getConditiondesign() {
		return conditiondesign;
	}

	public void setConditiondesign(Boolean conditiondesign) {
		this.conditiondesign = conditiondesign;
	}

	public Boolean getConditionshare() {
		return conditionshare;
	}

	public void setConditionshare(Boolean conditionshare) {
		this.conditionshare = conditionshare;
	}

	public Boolean getSortdesign() {
		return sortdesign;
	}

	public void setSortdesign(Boolean sortdesign) {
		this.sortdesign = sortdesign;
	}

	public Boolean getSortshare() {
		return sortshare;
	}

	public void setSortshare(Boolean sortshare) {
		this.sortshare = sortshare;
	}

	public Boolean getNavigatedesign() {
		return navigatedesign;
	}

	public void setNavigatedesign(Boolean navigatedesign) {
		this.navigatedesign = navigatedesign;
	}

	public Boolean getNavigateshare() {
		return navigateshare;
	}

	public void setNavigateshare(Boolean navigateshare) {
		this.navigateshare = navigateshare;
	}

	public Boolean getFilterdesign() {
		return filterdesign;
	}

	public void setFilterdesign(Boolean filterdesign) {
		this.filterdesign = filterdesign;
	}

	public Boolean getFiltershare() {
		return filtershare;
	}

	public void setFiltershare(Boolean filtershare) {
		this.filtershare = filtershare;
	}

	@Transient
	public boolean getHassqlstatement() {
		return sqlstatement != null && sqlstatement.length() > 2;
	}

	@Transient
	public boolean getHassqlparam() {
		return getHassqlstatement() && getFDataobjectsqlparams().size() > 0;
	}

	@Column(name = "sqlstatement", length = 600)

	public String getSqlstatement() {
		return this.sqlstatement;
	}

	public void setSqlstatement(String sqlstatement) {
		this.sqlstatement = sqlstatement;
	}

	@Column(name = "orderno", nullable = false)
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "creater", nullable = false, length = 40)

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "createdate", nullable = false, length = 19)

	public Timestamp getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "lastmodifier", length = 40)

	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Column(name = "lastmodifydate", length = 19)

	public Timestamp getLastmodifydate() {
		return this.lastmodifydate;
	}

	public void setLastmodifydate(Timestamp lastmodifydate) {
		this.lastmodifydate = lastmodifydate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataobjectdefaultorder> getFDataobjectdefaultorders() {
		return this.FDataobjectdefaultorders;
	}

	public void setFDataobjectdefaultorders(Set<FDataobjectdefaultorder> FDataobjectdefaultorders) {
		this.FDataobjectdefaultorders = FDataobjectdefaultorders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FovGridsortscheme> getFovGridsortschemes() {
		return this.fovGridsortschemes;
	}

	public void setFovGridsortschemes(Set<FovGridsortscheme> fovGridsortschemes) {
		this.fovGridsortschemes = fovGridsortschemes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FovGridscheme> getFovGridschemes() {
		return this.fovGridschemes;
	}

	public void setFovGridschemes(Set<FovGridscheme> fovGridschemes) {
		this.fovGridschemes = fovGridschemes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataobjectcondition> getFDataobjectconditions() {
		return this.FDataobjectconditions;
	}

	public void setFDataobjectconditions(Set<FDataobjectcondition> FDataobjectconditions) {
		this.FDataobjectconditions = FDataobjectconditions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataobjectfield> getFDataobjectfields() {
		return this.FDataobjectfields;
	}

	public void setFDataobjectfields(Set<FDataobjectfield> FDataobjectfields) {
		this.FDataobjectfields = FDataobjectfields;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobject")

	public Set<FModule> getFModules() {
		return this.FModules;
	}

	public void setFModules(Set<FModule> FModules) {
		this.FModules = FModules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FovFormscheme> getFovFormschemes() {
		return fovFormschemes;
	}

	public void setFovFormschemes(Set<FovFormscheme> fovFormschemes) {
		this.fovFormschemes = fovFormschemes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobjectBySubobjectid")
	@OrderBy("orderno")
	public Set<FovFormschemedetail> getFovFormschemedetails() {
		return fovFormschemedetails;
	}

	public void setFovFormschemedetails(Set<FovFormschemedetail> fovFormschemedetails) {
		this.fovFormschemedetails = fovFormschemedetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FovGridnavigatescheme> getFovGridnavigateschemes() {
		return this.fovGridnavigateschemes;
	}

	public void setFovGridnavigateschemes(Set<FovGridnavigatescheme> fovGridnavigateschemes) {
		this.fovGridnavigateschemes = fovGridnavigateschemes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FovFilterscheme> getFovFilterschemes() {
		return this.fovFilterschemes;
	}

	public void setFovFilterschemes(Set<FovFilterscheme> fovFilterschemes) {
		this.fovFilterschemes = fovFilterschemes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataobjectview> getFDataobjectviews() {
		return this.FDataobjectviews;
	}

	public void setFDataobjectviews(Set<FDataobjectview> FDataobjectviews) {
		this.FDataobjectviews = FDataobjectviews;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataobjectadditionfuncion> getFDataobjectadditionfuncions() {
		return FDataobjectadditionfuncions;
	}

	public void setFDataobjectadditionfuncions(Set<FDataobjectadditionfuncion> additionfuncions) {
		this.FDataobjectadditionfuncions = additionfuncions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	public Set<FovDataobjectassociate> getFovDataobjectassociates() {
		return this.fovDataobjectassociates;
	}

	public void setFovDataobjectassociates(Set<FovDataobjectassociate> fovDataobjectassociates) {
		this.fovDataobjectassociates = fovDataobjectassociates;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FovChartscheme> getFovChartschemes() {
		return this.fovChartschemes;
	}

	public void setFovChartschemes(Set<FovChartscheme> fovChartschemes) {
		this.fovChartschemes = fovChartschemes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	public Set<FDataobjectfieldconstraint> getFDataobjectfieldconstraints() {
		return this.FDataobjectfieldconstraints;
	}

	public void setFDataobjectfieldconstraints(Set<FDataobjectfieldconstraint> FDataobjectfieldconstraints) {
		this.FDataobjectfieldconstraints = FDataobjectfieldconstraints;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataobjectsqlparam> getFDataobjectsqlparams() {
		return this.FDataobjectsqlparams;
	}

	public void setFDataobjectsqlparams(Set<FDataobjectsqlparam> FDataobjectsqlparams) {
		this.FDataobjectsqlparams = FDataobjectsqlparams;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataminingscheme> getFDataminingschemes() {
		return this.FDataminingschemes;
	}

	public void setFDataminingschemes(Set<FDataminingscheme> FDataminingschemes) {
		this.FDataminingschemes = FDataminingschemes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FDataminingexpandgroup> getFDataminingexpandgroups() {
		return this.FDataminingexpandgroups;
	}

	public void setFDataminingexpandgroups(Set<FDataminingexpandgroup> FDataminingexpandgroups) {
		this.FDataminingexpandgroups = FDataminingexpandgroups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobject")
	@OrderBy("orderno")
	public Set<FRecordexcelscheme> getFRecordexcelschemes() {
		return this.FRecordexcelschemes;
	}

	public void setFRecordexcelschemes(Set<FRecordexcelscheme> FRecordexcelschemes) {
		this.FRecordexcelschemes = FRecordexcelschemes;
	}

}
