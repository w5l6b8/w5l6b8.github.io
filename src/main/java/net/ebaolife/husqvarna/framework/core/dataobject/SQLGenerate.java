package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate.FromGenerate;
import net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate.OrderByGenerate;
import net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate.WhereGenerate;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class SQLGenerate {

	public static String KEYASNAME = "key_";
	public static String NAMEASNAME = "name_";

	private static final String CR = "\r\n";

	private BaseModule baseModule;
	private GenerateParam generateParam;
	private Map<String, String> fields;

	public SQLGenerate(BaseModule baseModule, GenerateParam generateParam) {
		super();
		this.baseModule = baseModule;
		this.generateParam = generateParam;

	}

	public String generateSql() {

		List<String> sqls = new ArrayList<String>();

		fields = baseModule.getAllFieldsNameAndSql();

		sqls.addAll(generateSelect());

		boolean isCount = false;
		sqls.addAll(FromGenerate.generateFrom(baseModule, isCount));

		sqls.addAll(WhereGenerate.generateWhere(baseModule, generateParam));

		List<String> orders = OrderByGenerate.generateOrderby(baseModule, this.generateParam.getSortParameters());
		if (orders.size() > 0) {
			sqls.add(" order by ");
			for (String order : orders) {
				sqls.add(order);
				sqls.add(" , ");
			}
			sqls.remove(sqls.size() - 1);
		}

		StringBuilder sb = new StringBuilder();
		for (String s : sqls) {
			sb.append(s);
			sb.append(CR);
		}
		return sb.toString();
	}

	public String generateCountSql() {

		List<String> sqls = new ArrayList<String>();
		sqls.add("select count(*)");

		boolean isCount = true;

		sqls.addAll(FromGenerate.generateFrom(baseModule, isCount));
		sqls.addAll(WhereGenerate.generateWhere(baseModule, generateParam));

		StringBuilder sb = new StringBuilder();
		for (String s : sqls) {
			sb.append(s);
			sb.append(CR);
		}
		return sb.toString();

	}

	public String generateIdAndNameSql() {

		fields = new LinkedHashMap<String, String>();
		FDataobjectfield idField = baseModule.getModule()._getPrimaryKeyField();
		FDataobjectfield nameField = baseModule.getModule()._getNameField();

		fields.put(KEYASNAME, idField._getSelectName(baseModule.getAsName()));
		fields.put(NAMEASNAME, nameField._getSelectName(baseModule.getAsName()));

		List<String> sqls = new ArrayList<String>();
		sqls.addAll(generateSelect());

		boolean isCount = true;

		sqls.addAll(FromGenerate.generateFrom(baseModule, isCount));
		sqls.addAll(WhereGenerate.generateWhere(baseModule, null));

		sqls.add(" order by ");
		sqls.add(idField._getSelectName(baseModule.getAsName()));

		StringBuilder sb = new StringBuilder();
		for (String s : sqls) {
			sb.append(s);
			sb.append(CR);
		}
		return sb.toString();

	}

	public List<String> generateSelect() {
		List<String> result = new ArrayList<String>();
		result.add("select ");
		boolean first = true;
		for (String scale : fields.keySet()) {
			result.add((first ? "" : ",") + fields.get(scale) + " as " + baseModule.changeLongScaleToShort(scale));

			first = false;
		}
		return result;
	}

	public BaseModule getBaseModule() {
		return baseModule;
	}

	public void setBaseModule(BaseModule baseModule) {
		this.baseModule = baseModule;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public GenerateParam getGenerateParam() {
		return generateParam;
	}

	public void setGenerateParam(GenerateParam generateParam) {
		this.generateParam = generateParam;
	}

}
