package net.ebaolife.husqvarna.framework.dao.entity.limit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectFieldUtils;
import net.ebaolife.husqvarna.framework.utils.OperateUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_datafilterrolelimit")

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
public class FDatafilterrolelimit implements java.io.Serializable, ParentChildField {

	private String limitid;
	private FDatafilterrole FDatafilterrole;
	private FDatafilterrolelimit FDatafilterrolelimit;
	private FDataobjectcondition FDataobjectconditionBySubconditionid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction FFunction;
	private String title;
	private int orderno;
	private String fieldahead;
	private String aggregate;
	private String userfunction;
	private String operator;
	private String ovalue;
	private String recordids;
	private String recordnames;
	private boolean isvalid;
	private String remark;
	private List<FDatafilterrolelimit> limits;

	public FDatafilterrolelimit() {
	}

	public FDatafilterrolelimit(FDatafilterrole fDatafilterrole,
			FDataobjectcondition FDataobjectconditionBySubconditionid, FDatafilterrolelimit fDatafilterrolelimit,
			FDataobjectfield fDataobjectfield, FFunction fFunction, String title, int orderno, String userfunction,
			String fieldahead, String aggregate, String operator, String ovalue, String remark, String recordids,
			String recordnames) {
		super();
		this.FDatafilterrole = fDatafilterrole;
		this.FDataobjectconditionBySubconditionid = FDataobjectconditionBySubconditionid;
		this.FDatafilterrolelimit = fDatafilterrolelimit;
		this.FDataobjectfield = fDataobjectfield;
		this.FFunction = fFunction;
		this.title = title;
		this.orderno = orderno;
		this.userfunction = userfunction;
		this.fieldahead = fieldahead;
		this.aggregate = aggregate;
		this.operator = operator;
		this.ovalue = ovalue;
		this.recordids = recordids;
		this.recordnames = recordnames;
		this.remark = remark;
	}

	public String _getConditionText(FDataobject dataobject, boolean istext) {
		String result = null;
		String functionString = null;
		if (FFunction != null)
			functionString = FFunction.getExpression();
		else if (userfunction != null)
			functionString = userfunction;
		if (functionString != null) {
			StringBuffer resultBuffer = new StringBuffer();
			String s = "\\d+.\\d+|\\w+";
			Pattern patternthis = Pattern.compile(s);
			Matcher matcherthis = patternthis.matcher(functionString);
			while (matcherthis.find()) {
				if (matcherthis.group().equals("this")) {
					if (FDataobjectfield == null)
						return "error:表达式中有this,但是没有选择字段";
					matcherthis
							.appendReplacement(resultBuffer,
									istext ? DataObjectFieldUtils.getTitle(FDataobjectfield, fieldahead, aggregate,
											FDataobjectconditionBySubconditionid, dataobject)
											: DataObjectFieldUtils
													.getFieldnameJson(FDataobjectfield, fieldahead, aggregate,
															FDataobjectconditionBySubconditionid, dataobject)
													.toString());
				}
			}
			matcherthis.appendTail(resultBuffer);
			Pattern pattern = Pattern.compile("\\d+?\\%");
			String s1 = resultBuffer.toString();
			Matcher matcher = pattern.matcher(s1);
			resultBuffer = new StringBuffer();
			while (matcher.find()) {
				String group = matcher.group();
				Integer number = Integer.parseInt(group.substring(0, group.length() - 1));
				if (number == 0)
					return "error:表达式中第一个参数是1%，不是0%";
				if (number > limits.size())
					return "error:表达式中的" + group + "大于参数记录的个数";
				String childcondition = limits.get(number - 1)._getConditionText(dataobject, istext);
				matcher.appendReplacement(resultBuffer, childcondition);
			}
			matcher.appendTail(resultBuffer);
			result = resultBuffer.toString();
		} else if (FDataobjectfield != null) {
			result = istext
					? DataObjectFieldUtils.getTitle(FDataobjectfield, fieldahead, aggregate,
							FDataobjectconditionBySubconditionid, dataobject)
					: DataObjectFieldUtils.getFieldnameJson(FDataobjectfield, fieldahead, aggregate,
							FDataobjectconditionBySubconditionid, dataobject).toString();
		} else
			return "error:既无表达式，又无字段";
		result = OperateUtils.getCondition(result, operator, ovalue);

		return result;
	}

	public JSONObject genJsonObject(FDataobject dataobject) {
		JSONObject result = new JSONObject();
		result.put("title", this.getTitle());
		result.put("operator", this.getOperator());
		result.put("ovalue", this.getOvalue());
		result.put("userfunction", this.getUserfunction());
		result.put("remark", this.getRemark());
		if (FFunction != null)
			result.put("functionid", this.getFFunction().getFunctionid());
		if (FDataobjectfield != null) {
			result.put("fieldid", DataObjectFieldUtils.getItemId(getFDataobjectfield(), getFieldahead(), getAggregate(),
					getFDataobjectconditionBySubconditionid()));
			result.put("fieldtitle", DataObjectFieldUtils.getTitle(getFDataobjectfield(), getFieldahead(),
					getAggregate(), getFDataobjectconditionBySubconditionid(), dataobject));
		}
		if (limits != null && limits.size() > 0) {
			result.put("leaf", false);
			result.put("expanded", true);
			JSONArray array = new JSONArray();
			for (FDatafilterrolelimit s : limits)
				array.add(s.genJsonObject(dataobject));
			result.put("children", array);
		} else
			result.put("leaf", true);
		String text = this.getTitle();
		if (text == null) {
			text = "";
			if (FFunction != null)
				text = text + FFunction.getTitle();
			if (FDataobjectfield != null)
				text = text + (text.length() > 0 ? "--" : "") + result.getString("fieldtitle");
		}
		result.put("text", text);
		return result;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "limitid", unique = true, nullable = false, length = 40)
	public String getLimitid() {
		return this.limitid;
	}

	public void setLimitid(String limitid) {
		this.limitid = limitid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public FDatafilterrole getFDatafilterrole() {
		return this.FDatafilterrole;
	}

	public void setFDatafilterrole(FDatafilterrole FDatafilterrole) {
		this.FDatafilterrole = FDatafilterrole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FDatafilterrolelimit getFDatafilterrolelimit() {
		return this.FDatafilterrolelimit;
	}

	public void setFDatafilterrolelimit(FDatafilterrolelimit FDatafilterrolelimit) {
		this.FDatafilterrolelimit = FDatafilterrolelimit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subconditionid")
	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return this.FDataobjectconditionBySubconditionid;
	}

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition FDataobjectcondition) {
		this.FDataobjectconditionBySubconditionid = FDataobjectcondition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldid")
	public FDataobjectfield getFDataobjectfield() {
		return this.FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield FDataobjectfield) {
		this.FDataobjectfield = FDataobjectfield;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public FFunction getFFunction() {
		return this.FFunction;
	}

	public void setFFunction(FFunction FFunction) {
		this.FFunction = FFunction;
	}

	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	@Column(name = "fieldahead", length = 200)
	public String getFieldahead() {
		return this.fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	@Column(name = "aggregate", length = 20)
	public String getAggregate() {
		return this.aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	@Column(name = "userfunction", length = 200)
	public String getUserfunction() {
		return this.userfunction;
	}

	public void setUserfunction(String userfunction) {
		this.userfunction = userfunction;
	}

	@Column(name = "operator", length = 50)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "ovalue", length = 200)
	public String getOvalue() {
		return this.ovalue;
	}

	public void setOvalue(String ovalue) {
		this.ovalue = ovalue;
	}

	@Column(name = "recordids", length = 65535)
	public String getRecordids() {
		return this.recordids;
	}

	public void setRecordids(String recordids) {
		this.recordids = recordids;
	}

	@Column(name = "recordnames", length = 65535)
	public String getRecordnames() {
		return this.recordnames;
	}

	public void setRecordnames(String recordnames) {
		this.recordnames = recordnames;
	}

	@Column(name = "isvalid", nullable = false)
	public boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(boolean isvalid) {
		this.isvalid = isvalid;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDatafilterrolelimit")
	@OrderBy("orderno")
	public List<FDatafilterrolelimit> getLimits() {
		return this.limits;
	}

	public void setLimits(List<FDatafilterrolelimit> limits) {
		this.limits = limits;
	}

	@Override
	@Transient
	public String getCondition() {

		return null;
	}

	@Override
	public void setCondition(String value) {

	}

}
