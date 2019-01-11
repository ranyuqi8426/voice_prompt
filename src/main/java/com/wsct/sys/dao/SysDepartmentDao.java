package com.wsct.sys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.sys.model.SysDepartment;

@Repository
public class SysDepartmentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 新增
	 * @param baseDepartment
	 * @return
	 */
	public int add(SysDepartment sysDepartment) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into t_sys_department(code,name,create_person,create_date,parent_code,curst) values(:code,:name,:create_person,:create_date,:parent_code,0)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysDepartment);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 修改
	 * @param baseDepartment
	 * @return
	 */
	public int update(SysDepartment sysDepartment) {
		String sql = "update t_sys_department set name=:name,parent_code=:parent_code where code=:code ";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysDepartment);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 团队列表
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysDepartment> list() {
		String sql = "select m.*,(select realname from t_sys_user where code=create_person) create_personname,(select count(*) from t_sys_department where parent_code=m.code) cnt from t_sys_department m  where curst=0 order by parent_code";
		List<SysDepartment> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysDepartment.class));
		return list;
	}

	/**
	 * 取得最大编号
	 * 
	 * @return
	 */
	public String getMaxCode() {
		String sql = "select ifnull(max(code),'') code from t_sys_department";
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 取出下级节点个数
	 * @param code
	 * @return
	 */
	public int getChildCount(String code) {
		String sql = "select count(*) from t_sys_department where parent_code=? and curst=0";
		Object[] objects = new Object[] { code };
		return jdbcTemplate.queryForObject(sql, objects, Integer.class);
	}

	public int getUserCount(String code) {
		String sql = "select count(*) from t_sys_user where Department=? and  status!=9";
		Object[] objects = new Object[] { code };
		return jdbcTemplate.queryForObject(sql, objects, Integer.class);
	}

	/**
	 * 删除
	 * @param code
	 * @return
	 */
	public int delete(String code) {
		String sql = "update t_sys_department set curst=1 where code=? ";
		return jdbcTemplate.update(sql, code);
	}
}
