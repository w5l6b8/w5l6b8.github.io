package net.ebaolife.husqvarna.platform.service;

import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.TreeNode;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectadditionfuncion;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectbasefuncion;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FRole;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FRolefunctionlimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserfunctionlimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserrole;
import net.ebaolife.husqvarna.framework.dao.entity.module.FCompanymodule;
import net.ebaolife.husqvarna.framework.dao.entity.module.FCompanymodulegroup;
import net.ebaolife.husqvarna.framework.dao.entity.module.FModule;
import net.ebaolife.husqvarna.framework.dao.entity.module.FModulefunction;
import net.ebaolife.husqvarna.framework.dao.entity.system.FCompany;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
public class UserRoleService {

	@Resource
	private DaoImpl dao;

	public List<TreeNode> getUserAllLimitTree(String userid) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		FUser user = dao.findById(FUser.class, userid);
		Set<FModulefunction> checkedfunction = new HashSet<FModulefunction>();
		for (FUserrole userrle : user.getFUserroles())
			for (FRolefunctionlimit fl : userrle.getFRole().getFRolefunctionlimits())
				checkedfunction.add(fl.getFModulefunction());
		for (FUserfunctionlimit fl : user.getFUserfunctionlimits())
			checkedfunction.add(fl.getFModulefunction());
		FCompany company = user.getFPersonnel().getFOrganization().getFCompany();
		for (FCompanymodulegroup group : company.getFCompanymodulegroups()) {
			if (group.getFCompanymodulegroup() != null)
				continue;
			TreeNode subnode = getTreeNodeFromGroup(group, checkedfunction);
			if (subnode != null)
				result.add(subnode);
		}
		for (TreeNode childnode : result)
			childnode.setChecked(adjustParentChecked(childnode));
		return result;
	}

	public List<TreeNode> getRoleLimitTree(String roleid) {

		if (roleid == null) {
			return null;
		}

		List<TreeNode> result = new ArrayList<TreeNode>();
		FRole role = dao.findById(FRole.class, roleid);
		Set<FModulefunction> checkedfunction = new HashSet<FModulefunction>();
		for (FRolefunctionlimit fl : role.getFRolefunctionlimits()) {
			checkedfunction.add(fl.getFModulefunction());
		}
		FCompany company = role.getFCompany();
		for (FCompanymodulegroup group : company.getFCompanymodulegroups()) {
			if (group.getFCompanymodulegroup() != null)
				continue;
			TreeNode subnode = getTreeNodeFromGroup(group, checkedfunction);
			if (subnode != null)
				result.add(subnode);
		}
		for (TreeNode childnode : result) {
			childnode.setChecked(adjustParentChecked(childnode));
		}
		return result;
	}

	public boolean adjustParentChecked(TreeNode node) {
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			node.setChecked(true);
			for (TreeNode childnode : node.getChildren()) {
				node.setChecked(adjustParentChecked(childnode) && node.getChecked());
			}
		}
		return node.getChecked();
	}

	public TreeNode getTreeNodeFromGroup(FCompanymodulegroup group, Set<FModulefunction> checkedfunction) {
		if (group.getFCompanymodulegroups().size() > 0 || group.getFCompanymodules().size() > 0) {
			TreeNode record = new TreeNode(group.getGroupname());
			record.setLeaf(false);
			record.setChecked(false);
			record.setIconCls("x-fa fa-object-group");
			record.setChildren(new ArrayList<TreeNode>());
			record.setExpanded(true);
			if (group.getFCompanymodulegroups().size() > 0) {
				for (FCompanymodulegroup subgroup : group.getFCompanymodulegroups()) {
					TreeNode subnode = getTreeNodeFromGroup(subgroup, checkedfunction);
					if (subnode != null)
						record.getChildren().add(subnode);
				}
			} else {
				for (FCompanymodule cmodule : group.getFCompanymodules()) {
					TreeNode subnode = new TreeNode(cmodule.getFModule().getModulename());
					subnode.setChecked(false);
					subnode.setLeaf(true);

					subnode.setText(subnode.getText());
					FDataobject object = cmodule.getFModule().getFDataobject();
					if (object != null && object.getHasenable() != null && object.getHasenable()) {
						subnode.setIconCls(object.getIconcls());
						subnode.setChildren(getFCompanymoduleFunction(cmodule, checkedfunction));
						subnode.setLeaf(false);
						record.getChildren().add(subnode);
					}
				}
			}
			if (record.getChildren().size() > 0)
				return record;
		}
		return null;
	}

	public List<TreeNode> getFCompanymoduleFunction(FCompanymodule cmodule, Set<FModulefunction> checkedfunction) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		FDataobject object = cmodule.getFModule().getFDataobject();
		for (FModulefunction cfunction : cmodule.getFModulefunctions()) {
			if (!cfunction.getIsvalid())
				continue;
			if (cfunction.getFDataobjectbasefuncion() != null) {
				FDataobjectbasefuncion basefunction = cfunction.getFDataobjectbasefuncion();
				if (basefunction.getIsdisable() != null && basefunction.getIsdisable())
					continue;
				if (basefunction.getFcode().indexOf("new") == 0)
					if (!(object.getHasinsert() != null && object.getHasinsert()))
						continue;
				if (basefunction.getFcode().indexOf("edit") == 0)
					if (!(object.getHasedit() != null && object.getHasedit()))
						continue;
				if (basefunction.getFcode().indexOf("delete") == 0)
					if (!(object.getHasdelete() != null && object.getHasdelete()))
						continue;
				if (basefunction.getFcode().indexOf("attachment") >= 0)
					if (!(object.getHasattachment() != null && object.getHasattachment()))
						continue;
				TreeNode functionnode = new TreeNode(basefunction.getTitle());
				functionnode.setChecked(false);

				functionnode.setObjectid(cfunction.getFunctionid());
				functionnode.setText(functionnode.getText());
				functionnode.setLeaf(true);
				functionnode.setCls("numbercolor");
				functionnode.setChecked(checkedfunction.contains(cfunction));
				result.add(functionnode);
			} else if (cfunction.getFDataobjectadditionfuncion() != null) {
				FDataobjectadditionfuncion additionfunecion = cfunction.getFDataobjectadditionfuncion();
				TreeNode functionnode = new TreeNode(additionfunecion.getTitle());
				functionnode.setChecked(false);

				functionnode.setObjectid(cfunction.getFunctionid());
				functionnode.setText(functionnode.getText());
				functionnode.setLeaf(true);
				functionnode.setCls("numbercolor");
				functionnode.setChecked(checkedfunction.contains(cfunction));
				result.add(functionnode);
			}
		}
		return result;
	}

	public ActionResult updateAdditionFunctionToCModule(String functionid) {
		String msg = "";
		int number = 0;
		FDataobjectadditionfuncion af = dao.findById(FDataobjectadditionfuncion.class, functionid);
		for (FModule module : af.getFDataobject().getFModules()) {
			for (FCompanymodule cmodule : module.getFCompanymodules()) {
				boolean found = false;
				for (FModulefunction function : cmodule.getFModulefunctions()) {
					if (af.equals(function.getFDataobjectadditionfuncion())) {
						found = true;
					}
					number = function.getOrderno();
				}
				if (!found) {
					FModulefunction function = new FModulefunction();
					function.setFCompanymodule(cmodule);
					function.setFDataobjectadditionfuncion(af);
					function.setIsvalid(true);
					function.setOrderno(number + 1);
					dao.save(function);
					msg = msg + cmodule.getFCompany().getCompanyname() + ";<br/>";
				}
			}
		}
		ActionResult result = new ActionResult();
		result.setMsg(msg);
		return result;
	}

	public ActionResult saveRoleLimit(String roleid, String ids) {
		FRole role = dao.findById(FRole.class, roleid);
		Iterator<FRolefunctionlimit> iterator = role.getFRolefunctionlimits().iterator();
		while (iterator.hasNext()) {
			FRolefunctionlimit fl = iterator.next();
			iterator.remove();
			fl.setFRole(null);
			dao.delete(fl);
		}
		if (ids != null && ids.length() > 0) {
			for (String flid : ids.split(",")) {
				FRolefunctionlimit fl = new FRolefunctionlimit();
				fl.setFRole(role);
				fl.setFModulefunction(new FModulefunction(flid));
				dao.save(fl);
			}
		}
		return new ActionResult();
	}

	public List<TreeNode> getUserLimitTree(String roleid) {
		if (roleid == null) {
			return null;
		}
		List<TreeNode> result = new ArrayList<TreeNode>();
		FUser role = dao.findById(FUser.class, roleid);
		Set<FModulefunction> checkedfunction = new HashSet<FModulefunction>();
		for (FUserfunctionlimit fl : role.getFUserfunctionlimits()) {
			checkedfunction.add(fl.getFModulefunction());
		}
		FCompany company = role.getFPersonnel().getFOrganization().getFCompany();
		for (FCompanymodulegroup group : company.getFCompanymodulegroups()) {
			if (group.getFCompanymodulegroup() != null)
				continue;
			TreeNode subnode = getTreeNodeFromGroup(group, checkedfunction);
			if (subnode != null)
				result.add(subnode);
		}
		for (TreeNode childnode : result) {
			childnode.setChecked(adjustParentChecked(childnode));
		}
		return result;
	}

	public ActionResult saveUserLimit(String roleid, String ids) {
		FUser role = dao.findById(FUser.class, roleid);
		Iterator<FUserfunctionlimit> iterator = role.getFUserfunctionlimits().iterator();
		while (iterator.hasNext()) {
			FUserfunctionlimit fl = iterator.next();
			iterator.remove();
			fl.setFUser(null);
			dao.delete(fl);
		}
		if (ids != null && ids.length() > 0) {
			for (String flid : ids.split(",")) {
				FUserfunctionlimit fl = new FUserfunctionlimit();
				fl.setFUser(role);
				fl.setFModulefunction(new FModulefunction(flid));
				dao.save(fl);
			}
		}
		return new ActionResult();
	}

}
