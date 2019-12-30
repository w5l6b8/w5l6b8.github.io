package net.ebaolife.husqvarna.framework.core.dataobjectquery.column;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.groupquery.FGroupquerycolumn;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;

import java.util.ArrayList;
import java.util.List;

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
public class ColumnField implements ParentChildField {

	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private FDataobjectcondition FDataobjectconditionBySubconditionid;
	private String condition;
	private String text;
	private String title;
	private String fieldahead;
	private String aggregate;
	private Integer columnwidth;
	private Integer autosizetimes;
	private Integer flex;
	private Boolean hidden;
	private Boolean locked;
	private String othersetting;
	private String remark;
	private List<ColumnField> columns;

	public ColumnField() {

	}

	public static List<ColumnField> genColumnsFromGroupquerycolumn(List<FGroupquerycolumn> querycolumns,
			List<ColumnField> allField) {
		List<ColumnField> result = new ArrayList<ColumnField>();
		for (FGroupquerycolumn columnObject : querycolumns) {
			ColumnField column = new ColumnField();

			column.setTitle(columnObject.getTitle());
			column.setLocked(columnObject.getLocked());
			column.setHidden(columnObject.getHidden());
			column.setOthersetting(columnObject.getOthersetting());
			column.setRemark(columnObject.getRemark());
			if (columnObject.getFGroupquerycolumns() != null && columnObject.getFGroupquerycolumns().size() > 0) {
				column.setColumns(genColumnsFromGroupquerycolumn(columnObject.getFGroupquerycolumns(), allField));
			} else {
				column.setAggregate(columnObject.getAggregate());
				column.setFieldahead(columnObject.getFieldahead());
				column.setFDataobjectfield(columnObject.getFDataobjectfield());
				column.setFDataobjectconditionBySubconditionid(columnObject.getFDataobjectconditionBySubconditionid());
				column.setColumnwidth(columnObject.getColumnwidth());
				column.setAutosizetimes(columnObject.getAutosizetimes());
				column.setFlex(columnObject.getFlex());
				if (allField != null)
					allField.add(column);
			}
			result.add(column);
		}
		return result;
	}

	public static List<ColumnField> genColumnsFromJsonObject(JSONArray arrays, List<ColumnField> allField) {
		List<ColumnField> result = new ArrayList<ColumnField>();
		for (int i = 0; i < arrays.size(); i++) {
			JSONObject columnObject = arrays.getJSONObject(i);
			ColumnField column = new ColumnField();
			if (columnObject.containsKey("text"))
				column.setText(columnObject.getString("text"));
			if (columnObject.containsKey("tf_title"))
				column.setTitle(columnObject.getString("tf_title"));
			if (columnObject.containsKey("tf_locked"))
				column.setLocked(columnObject.getBoolean("tf_locked"));
			if (columnObject.containsKey("tf_hidden"))
				column.setHidden(columnObject.getBoolean("tf_hidden"));
			if (columnObject.containsKey("tf_otherSetting"))
				column.setOthersetting(columnObject.getString("tf_otherSetting"));
			if (columnObject.containsKey("tf_remark"))
				column.setRemark(columnObject.getString("tf_remark"));
			if (columnObject.containsKey("children")) {
				column.setColumns(genColumnsFromJsonObject((JSONArray) columnObject.get("children"), allField));
			} else {
				ParentChildField.updateToField(column, columnObject.getString("tf_itemId"));

				column.setFDataobjectfield(
						DataObjectUtils.getDataobjectfields().get(column.getFDataobjectfield().getFieldid()));

				if (column.getFDataobjectconditionBySubconditionid() != null)
					column.setFDataobjectconditionBySubconditionid(Local.getDao().findById(FDataobjectcondition.class,
							column.getFDataobjectconditionBySubconditionid().getConditionid()));
				if (columnObject.containsKey("tf_width"))
					column.setColumnwidth(columnObject.getInteger("tf_width"));
				if (columnObject.containsKey("tf_autosizetimes"))
					column.setAutosizetimes(columnObject.getInteger("tf_autosizetimes"));
				if (columnObject.containsKey("tf_flex"))
					column.setFlex(columnObject.getInteger("tf_flex"));
				if (allField != null)
					allField.add(column);
			}
			result.add(column);
		}
		return result;
	}

	public FDataobjectfield getFDataobjectfield() {
		return FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield fDataobjectfield) {
		FDataobjectfield = fDataobjectfield;
	}

	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return FDataobjectconditionBySubconditionid;
	}

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition fDataobjectconditionBySubconditionid) {
		FDataobjectconditionBySubconditionid = fDataobjectconditionBySubconditionid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldahead() {
		return fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	public String getAggregate() {
		return aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	public Integer getColumnwidth() {
		return columnwidth;
	}

	public void setColumnwidth(Integer columnwidth) {
		this.columnwidth = columnwidth;
	}

	public Integer getAutosizetimes() {
		return autosizetimes;
	}

	public void setAutosizetimes(Integer autosizetimes) {
		this.autosizetimes = autosizetimes;
	}

	public Integer getFlex() {
		return flex;
	}

	public void setFlex(Integer flex) {
		this.flex = flex;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getOthersetting() {
		return othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ColumnField> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnField> columns) {
		this.columns = columns;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
