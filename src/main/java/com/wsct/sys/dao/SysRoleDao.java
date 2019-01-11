package com.wsct.sys.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.common.vo.ComboboxVO;
import com.wsct.sys.model.SysRole;
import com.wsct.sys.model.SysRoleFunction;
import com.wsct.sys.model.SysRoleModule;

@Repository
public class SysRoleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int add(SysRole sysRole) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into t_sys_role(id,name,code,description,display_order,is_admin,default_module)"
				+ " values(seq_t_sys_role.nextval,:name,:code,:description,:display_order,:is_admin,:default_module)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysRole);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	public int update(SysRole sysRole) {
		String sql = "update t_sys_role set name=:name,description=:description,display_order=:display_order,is_admin=:is_admin,default_module=:default_module where id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysRole);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	public int delete(int id) {
		String sql = "delete from t_sys_role where id=?";
		return jdbcTemplate.update(sql, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysRole get(int id) {
		String sql = "select * from t_sys_role where id=?";
		Object[] params = new Object[] { id };
		List<SysRole> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysRole.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysRole> list() {
		String sql = "select * from t_sys_role order by display_order ";
		List<SysRole> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysRole.class));
		return list;
	}

	/**
	 * 根据角色id获取所有模块
	 * 
	 * @param role_id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysRoleModule> listRoleModule(int role_id) {
		String sql = "select * from t_sys_rolemodule where role_id=? ";
		Object[] params = new Object[] { role_id };
		List<SysRoleModule> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysRoleModule.class));
		return list;
	}

	/**
	 * 删除角色对应的模块
	 * 
	 * @param role_id
	 */
	public void deleteRoleModule(int role_id) {
		jdbcTemplate.update("delete from t_sys_rolemodule where role_id=?", role_id);
	}

	/**
	 * 新增角色对应模块
	 * 
	 * @param role_id
	 * @param module_id
	 */
	public void addRoleModule(int role_id, int module_id) {
		String sql = "insert into t_sys_rolemodule(id,role_id,module_id) values(seq_t_sys_rolemodule.nextval,?,?)";
		Object[] params = new Object[] { role_id, module_id };
		jdbcTemplate.update(sql, params);
	}

	/**
	 * 删除角色对应的功能
	 * 
	 * @param role_id
	 */
	public void deleteRoleFunction(int role_id) {
		jdbcTemplate.update("delete from t_sys_rolefunction where role_id=?", role_id);
	}

	/**
	 * 新增角色对应功能
	 * 
	 * @param role_id
	 * @param module_id
	 */
	public void addRoleFunction(int role_id, int function_id) {
		String sql = "insert into t_sys_rolefunction(id,role_id,function_id) values(seq_t_sys_rolefunction.nextval,?,?)";
		Object[] params = new Object[] { role_id, function_id };
		jdbcTemplate.update(sql, params);
	}

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ComboboxVO> listCombobox(int is_admin) {
		String sql = "select id value,name content from t_sys_role ";
		if (is_admin != 1)
			sql += " where is_admin=0";
		sql += "order by display_order ";
		List<ComboboxVO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ComboboxVO.class));
		return list;
	}

	public void addRoleFunction(final List<SysRoleFunction> list) {
		String sql = "insert into t_sys_rolefunction(id,role_id,function_id) values(seq_t_sys_rolefunction.nextval,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SysRoleFunction sysRoleFunction = list.get(i);
				ps.setInt(1, sysRoleFunction.getRole_id());
				ps.setInt(2, sysRoleFunction.getFunction_id());
			}

			public int getBatchSize() {
				return list.size();
			}
		});
	}

	public void addRoleModule(final List<SysRoleModule> list) {
		String sql = "insert into t_sys_rolemodule(id,role_id,module_id) values(seq_t_sys_rolemodule.nextval,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SysRoleModule sysRoleModule = list.get(i);
				ps.setInt(1, sysRoleModule.getRole_id());
				ps.setInt(2, sysRoleModule.getModule_id());
			}

			public int getBatchSize() {
				return list.size();
			}
		});
	}
}