package com.wsct.sys.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsct.common.vo.ComboboxVO;
import com.wsct.sys.dao.SysFunctionDao;
import com.wsct.sys.dao.SysModuleDao;
import com.wsct.sys.dao.SysUserDao;
import com.wsct.sys.model.SysFunction;
import com.wsct.sys.model.SysModule;
import com.wsct.sys.model.SysRole;
import com.wsct.sys.model.SysUser;
import com.wsct.sys.vo.SysUserSearchVO;
import com.wsct.util.code.SerialNumUtil;
import com.wsct.util.string.StringUtil;

/**
 * 用户管理Service
 * @author chykong
 * @date 2016-12-06
 */
@Service
public class SysUserService {
	private static Logger logger = LoggerFactory.getLogger(SysUserService.class);
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysModuleDao sysModuleDao;
	@Autowired
	private SysFunctionDao sysFunctionDao;
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 用户新增，先判断用户名是否存在
	 * 返回2，email已存在，-1编号已经用完，返回1操作成功
	 * 
	 * @param sysUser
	 * @return
	 */
	public int add(SysUser sysUser) {
		int flag = 0;
		int usernameCount = sysUserDao.getUsernameCount(sysUser.getUsername());
		if (usernameCount > 0)
			flag = 2;
		else {
			// 设置密码
			String password = "123456";
			String randomcode = SerialNumUtil.createRandowmNum(6);
			sysUser.setPassword(password);
			sysUser.setRandomcode(randomcode);
			sysUserDao.add(sysUser);
			flag = 1;
		}
		return flag;
	}

	public int update(SysUser sysUser) {
		int flag = sysUserDao.update(sysUser);
		return flag;
	}

	public int delete(int id) {
		return sysUserDao.delete(id);
	}

	public SysUser get(int id) {
		return sysUserDao.get(id);
	}

	/**
	 * 根据username获取用户
	 * 
	 * @param username
	 * @return
	 */
	public SysUser getByUsername(String username) {
		return sysUserDao.getByUsername(username);
	}

	/**
	 * 用户列表
	 * 
	 * @param sysUserSearchVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysUser> list(SysUserSearchVO sysUserSearchVO, int pageIndex, int pageSize) {
		List<SysUser> list = sysUserDao.list(sysUserSearchVO, pageIndex, pageSize);
		return list;
	}

	/**
	 * 用户列表总数
	 * 
	 * @param sysUserSearchVO
	 * @return
	 */
	public int listCount(SysUserSearchVO sysUserSearchVO) {
		return sysUserDao.listCount(sysUserSearchVO);
	}

	/**
	 * 生成menu json串
	 * 
	 * @param user_id
	 * @return
	 */
	public String createMenu(int role_id) {
		List<SysModule> listModule = sysModuleDao.listByRole_id(role_id);
		String json = "";
		json = createMenu(listModule, 1);
		return "[" + json + "]";
	}

	private String createMenu(List<SysModule> list, int parent_id) {
		String str = "";
		for (SysModule sysModule : list) {
			if (sysModule.getParent_id() == parent_id) {
				str += "{\"id\":\"" + sysModule.getId() + "\",\"parent_id\":" + sysModule.getParent_id()
						+ ",\"text\":\"" + sysModule.getName() + "\",\"glyph\":" + sysModule.getIconImg()
						+ ",\"target\":\"" + sysModule.getTarget() + "\"" + ",\"url\":\"" + sysModule.getUrl()
						+ "\",\"expanded\":" + true + "";
				if (sysModule.getCnt() > 0)
					str += ",\"children\":[" + createMenu(list, sysModule.getId()) + "]";
				else {
					str += ",\"leaf\":true";
				}
				str += "},";
			}
		}
		str = StringUtil.subTract(str);
		return str;
	}

	/**
	 * 获取用户页面操作权限,把页面的按钮置为,默认显示所有只读按钮
	 * @param user_id
	 * @return
	 */
	public String createBtnAutho(int role_id, String moduleCode) {
		StringBuilder sb = new StringBuilder();
		SysModule sysModule = sysModuleDao.getByModuleCode(moduleCode);
		if(sysModule==null)return "";
		List<SysFunction> listFunctions = sysFunctionDao.listByModule_id(sysModule.getId());// 获取页面所有写操作按钮
		HashMap<Integer, HashMap<Integer, String>> hashRolesFunctions = sysRoleService.hashRolesFunctions();
		for (SysFunction sysFunction : listFunctions) {
			boolean checked = false;
			SysRole sysRole = sysRoleService.get(role_id);
			if (hashRolesFunctions.get(sysRole.getId()).containsKey(sysFunction.getId())) {
				checked = true;
			}
			if (checked == false && sysFunction.getType() == 1) {
				// sb.append("Ext.getCmp('btn" + sysFunction.getCode() +
				// "').setVisible(false);");
				sb.append("var hide" + sysFunction.getCode() + "=" + true + ";");
			} else {
				sb.append("var hide" + sysFunction.getCode() + "=" + false + ";");
			}
		}
		return sb.toString();
	}

	/**
	 * 校验按钮权限，防止不通过浏览器提交
	 * @param listRoles
	 * @param path
	 * @return
	 */
	public boolean checkAuthority(int role_id, String path) {
		HashMap<Integer, HashMap<Integer, String>> hashRolesFunctions = sysRoleService.hashRolesFunctions();
		boolean checked = false;
		SysRole sysRole = sysRoleService.get(role_id);
		if (hashRolesFunctions.get(sysRole.getId()).containsValue(path)) {
			checked = true;
		}
		return checked;
	}

	/**
	 * 登录后修改个人信息
	 * 返回0失败，1成功，2原密码输入错误
	 * @param sysUser
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public int updateInfo(SysUser sysUser, String oldPass, String newPass) {
		int flag = 0;
		SysUser getUser = sysUserDao.get(sysUser.getId());
		// 判断原密码是否为空，不为空则修改新密码
		if (StringUtil.isNotNullOrEmpty(oldPass)) {
			if (oldPass.equals(getUser.getPassword())) {
				sysUserDao.updatePass(sysUser.getId(), newPass);
				flag = 1;
			} else {
				flag = 2;
			}
		}

		if (flag != 2) {
			sysUserDao.updateInfo(sysUser);
			flag = 1;
		}
		// // 如果为未激活，改为开通
		// if (getUser.getStatus() == 0) {
		// sysUserDao.updateStatus(getUser.getId(), 1);
		// }
		return flag;
	}

	/**
	 * 校验密码是否正确
	 * @param sysUser
	 * @param password
	 * @return
	 */
	public boolean checkPass(SysUser sysUser, String password) {
		if (password.equals(sysUser.getPassword()))
			return true;
		else
			return false;
	}

	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	public int saveResetPass(int id) {
		int flag = 0;
		try {
			String password = "123456";
			sysUserDao.updatePass(id, password);
			sysUserDao.updateStatus(id, 0);
			flag = 1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = 0;
		}
		return flag;
	}

	/**
	 * 修改状态，锁定解锁用户时使用
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatus(int id, int status) {
		return sysUserDao.updateStatus(id, status);
	}

	/**
	 * 所有人员列表，查询日志使用
	 * @return
	 */
	public List<ComboboxVO> listAllUser(int create_id) {
		return sysUserDao.listAllUser(create_id);
	}

}
