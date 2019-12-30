package net.ebaolife.husqvarna.platform.controller;


import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.dao.entity.system.FSystemcfg;
import net.ebaolife.husqvarna.platform.service.SystemcfgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/platform/systemcfg")
public class Systemcfg {

	@Resource
	private SystemcfgService service;
	
	@SystemLogs("查询系统配置列表信息")
	@RequestMapping(value="/getlist")
	@ResponseBody
	public List<FSystemcfg> getList(FSystemcfg bean){
		return service.getList(bean);
	}
	
	@SystemLogs("查询系统配置信息")
	@RequestMapping(value="/getinfo")
	@ResponseBody
	public FSystemcfg getInfo(FSystemcfg bean){
		return service.getInfo(bean);
	}
	
	@SystemLogs("保存/修改系统配置信息")
	@RequestMapping(value="/saveorupdate")
	@ResponseBody
	public String saveOrUpdate(FSystemcfg bean){
		return service.saveOrUpdate(bean);
	}
	
	@SystemLogs("删除系统配置信息")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Integer delete(FSystemcfg bean){
		return service.delete(bean);
	}
	@SystemLogs("查询系统配置列表信息")
	@RequestMapping(value="/getmenulist")
	@ResponseBody
	public List<Map<String,Object>> getMenucfgList(FSystemcfg bean){
		return service.getMenucfgList(bean);
	}
}
