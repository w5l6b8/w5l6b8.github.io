package net.ebaolife.husqvarna.platform.service;

import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.exception.DataDeleteException;
import net.ebaolife.husqvarna.framework.exception.DataUpdateException;
import ognl.Ognl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
public class CodeLevelDataobjectService {

	@Resource
	private DaoImpl dao;

	public String addCodeLevelModuleKey(FDataobject module, String keyid, Class<?> beanclass) {
		if (!isCodeLengthRight(module.getCodelevel(), keyid))
			throw new DataUpdateException(module.getPrimarykey(), "代码的长度不符合级次规范:" + module.getCodelevel());
		else if (keyid.length() != getThisLevellen(module.getCodelevel(), 0)) {
			int parentl = getKeyLevel(module.getCodelevel(), keyid.length()) - 1;
			String parentkey = keyid.substring(0, getcodeLevellen(module.getCodelevel(), parentl));
			if (dao.findById(beanclass, parentkey) == null)
				throw new DataUpdateException(module.getPrimarykey(),
						"代码:『" + keyid + "』未找到其父代码『" + parentkey + "』的记录值!");
		}
		return null;
	}

	public void deleteCodeLevelModuleKey(FDataobject module, String keyid) {
		List<?> result = dao.executeSQLQuery(" select * from " + module._getTablename() + " where "
				+ module._getPrimaryKeyField()._getSelectName(null) + " like '" + keyid + "%' order by "
				+ module._getPrimaryKeyField()._getSelectName(null));
		String name = null;
		if (result.size() > 1) {
			try {
				name = Ognl.getValue(module._getNameField()._getSelectName(null), result.get(0)).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new DataDeleteException(
					module.getTitle() + ":『" + name + "』下有" + String.valueOf(result.size() - 1) + "条子记录，请先删除所有子记录!");
		}
	}

	public void replaceCodeLevelModuleKey(FDataobject module, String oldkeyid, String newkeyid, Class<?> beanclass) {

		deleteCodeLevelModuleKey(module, oldkeyid);
		addCodeLevelModuleKey(module, newkeyid, beanclass);

	}

	private boolean isCodeLengthRight(String codeLevel, String keyid) {
		if (keyid == null)
			return false;
		int l = keyid.length();
		for (int i = 0; i < getCodeLevelNum(codeLevel); i++)
			if (l == getcodeLevellen(codeLevel, i))
				return true;
		return false;
	}

	private int getKeyLevel(String codeLevel, int num) {
		for (int i = 0; i < getCodeLevelNum(codeLevel); i++)
			if (num == getcodeLevellen(codeLevel, i))
				return i;
		return -1;
	}

	public int getCodeLevelNum(String codeLevel) {

		return codeLevel.split(",").length;

	}

	public int getThisLevellen(String codeLevel, int level) {
		return Integer.parseInt(codeLevel.split(",")[level]);
	}

	public int getcodeLevellen(String codeLevel, int level) {
		String[] levels = codeLevel.split(",");
		int result = 0;
		for (int i = 0; i <= level; i++)
			result += Integer.parseInt(levels[i]);
		return result;
	}

}
