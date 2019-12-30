package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONArray;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.TableFieldBean;
import net.ebaolife.husqvarna.framework.bean.TreeValueText;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.platform.service.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
@Controller
@RequestMapping("/platform/database")
public class Database {

	@Resource
	private DatabaseService databaseService;

	@RequestMapping(value = "/createuserview")
	@ResponseBody
	public ActionResult createUserView(String viewid) {
		return databaseService.createUserView(viewid);
	}

	@RequestMapping(value = "/dropuserview")
	@ResponseBody
	public ActionResult dropUserView(String viewid) {
		return databaseService.dropUserView(viewid);
	}

	@SystemLogs("数据库schema信息查询")
	@RequestMapping(value = "/getschemas")
	@ResponseBody
	public JSONArray getSchemas() throws SQLException {
		return databaseService.getSchemas();
	}

	@SystemLogs("数据库表和视图信息查询")
	@RequestMapping(value = "/getnotimporttableview")
	@ResponseBody
	public TreeValueText getNotImportTableAndViews(String schema) throws SQLException {
		return databaseService.getNotImportTableAndViews(schema);
	}

	@SystemLogs("数据库表和视图的字段查询")
	@RequestMapping(value = "/getfields")
	@ResponseBody
	public List<TableFieldBean> getFields(String schema, String tablename) {
		return databaseService.getFields(schema == null || schema.length() == 0 ? null : schema, tablename);
	}

	@SystemLogs("数据库表和视图的字段查询")
	@RequestMapping(value = "/importtableorview")
	@ResponseBody
	public ActionResult importTableOrView(String schema, String tablename, String title, String namefield,
			boolean addtoadmin, boolean addtomenu) throws IllegalAccessException, InvocationTargetException {
		return databaseService.importTableOrView(schema == null || schema.length() == 0 ? null : schema, tablename,
				title, namefield, addtoadmin, addtomenu, null);
	}
}
