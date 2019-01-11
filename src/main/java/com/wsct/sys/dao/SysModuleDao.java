package com.wsct.sys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.sys.model.SysModule;

@Repository
public class SysModuleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int add(SysModule sysModule) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into t_sys_module(id,name,code,parent_id,url,target,iconImg,display_order) "
				+ "values(seq_t_sys_module.nextval,:name,:code,:parent_id,:url,:target,:iconImg,:display_order)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysModule);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	public int update(SysModule sysModule) {
		String sql = "update t_sys_module set name=:name,code=:code,url=:url,parent_id=:parent_id,target=:target,iconImg=:iconImg,display_order=:display_order where id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysModule);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	public int delete(int id) {
		String sql = "delete from t_sys_module where id=?";
		return jdbcTemplate.update(sql, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysModule get(int id) {
		String sql = "select * from t_sys_module where id=?";
		Object[] params = new Object[] { id };
		List<SysModule> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysModule.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysModule> list() {
		String sql = "select m.*,(select count(*) from t_sys_module where parent_id=m.id) cnt from t_sys_module m order by parent_id, display_order";
		List<SysModule> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysModule.class));
		return list;
	}

	/**
	 * 获取下级节点总数
	 * @param id
	 * @return
	 */
	public int getChildCount(int id) {
		String sql = "select count(*) from t_sys_module where parent_id=?";
		Object[] objects = new Object[] { id };
		return jdbcTemplate.queryForObject(sql, objects, Integer.class);
	}

	/**
	 * 根据角色id获取模块
	 * @param role_id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysModule> listByRole_id(int role_id) {
		String sql = "select m.*,(select count(*) from t_sys_module where parent_id=m.id) cnt from t_sys_module m where id in (select  module_id from t_sys_rolemodule where role_id =?) order by parent_id, display_order";
		Object[] params = new Object[] { role_id };
		List<SysModule> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysModule.class));
		return list;
	}

	/**
	 * 根据模块代码获取模块信息
	 * @param code
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysModule getByModuleCode(String code) {
		String sql = "select * from t_sys_module where code=?";
		Object[] params = new Object[] { code };
		List<SysModule> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysModule.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}
}
