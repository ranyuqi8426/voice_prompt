package com.wsct.sys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wsct.common.vo.ComboboxVO;
import com.wsct.sys.model.SysUser;
import com.wsct.sys.vo.SysUserSearchVO;
import com.wsct.util.page.PageUtil;
import com.wsct.util.string.StringUtil;

/**
 * 用户管理Dao
 * @author chykong
 * @date 2016年12月6日
 */
@Repository
public class SysUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int add(SysUser sysUser) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into t_sys_user(id,username,password,randomcode,status,realname,gender,mobile,phone, role_id,dwid,create_date,create_person,create_id)";
		sql += " values(seq_t_sys_user.nextval,:username,:password,:randomcode,1,:realname,:gender,:mobile,:phone,:role_id,:dwid,sysdate,:create_person,:create_id)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysUser);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rc = namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[] { "id" });
		if (rc > 0) {
			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	public int update(SysUser sysUser) {
		String sql = "update t_sys_user set realname=:realname,gender=:gender,mobile=:mobile,phone=:phone,role_id=:role_id,dwid=:dwid where id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysUser);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 修改密码
	 * @param code
	 * @param newPass
	 * @param randowmcode
	 * @return
	 */
	public int updatePass(int id, String newPass) {
		String sql = "update t_sys_user set password=?  where id=?";
		Object[] objects = new Object[] { newPass, id };
		return jdbcTemplate.update(sql, objects);
	}

	/**
	 * 修改个人信息，用户自己操作
	 * @param sysUser
	 * @return
	 */
	public int updateInfo(SysUser sysUser) {
		String sql = "update t_sys_user set realname=:realname,gender=:gender,mobile=:mobile,"
				+ "phone=:phone where id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysUser);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 修改状态
	 * @param code
	 * @param status
	 * @return
	 */
	public int updateStatus(int id, int status) {
		String sql = "update t_sys_user set status=?  where id=?";
		Object[] objects = new Object[] { status, id };
		return jdbcTemplate.update(sql, objects);
	}

	public int delete(int id) {
		String sql = "delete from t_sys_user where id=?";
		return jdbcTemplate.update(sql, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysUser get(int id) {
		String sql = "select t.*,(select dwmc from es_sys_dw where dwid=t.dwid) dwmc"
				+ ",(select dwjb from es_sys_dw where dwid=t.dwid) dwjb" + " from t_sys_user t where id=?";
		Object[] params = new Object[] { id };
		List<SysUser> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysUser.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 根据email获取sysUser
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysUser getByUsername(String username) {
		String sql = "select u.*,r.default_module,r.name userRoleStr  from t_sys_user u,t_sys_role r where u.role_id=r.id and upper(username)=upper(?)";
		Object[] params = new Object[] { username };
		List<SysUser> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysUser.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 查询该用户名是否已存在
	 * 
	 * @param username
	 * @return
	 */
	public int getUsernameCount(String username) {
		String sql = "select count(*) from t_sys_user where username=? ";
		Object[] objects = new Object[] { username };
		return jdbcTemplate.queryForObject(sql, objects, Integer.class);
	}

	/**
	 * 查询用户信息
	 * 
	 * @param sysUserSearchVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysUser> list(SysUserSearchVO sysUserSearchVO, int pageIndex, int pageSize) {
		String sql = "select t.*,(select name from t_sys_role where id=role_id) userRoleStr,"
				+ "(select dwmc from es_sys_dw where dwid=t.dwid) dwmc  from t_sys_user t where 1=1 ";
		sql += createSearchSql(sysUserSearchVO);
		sql += " order by realname asc";
		sql = PageUtil.createOraclePageSQL(sql, pageIndex, pageSize);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysUserSearchVO);
		List<SysUser> list = namedParameterJdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper(SysUser.class));
		return list;
	}

	/**
	 * 查询用户总数
	 * 
	 * @param sysUserSearchVO
	 * @return
	 */
	public int listCount(SysUserSearchVO sysUserSearchVO) {
		String sql = "select count(*) from t_sys_user where 1=1 ";
		sql += createSearchSql(sysUserSearchVO);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysUserSearchVO);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
	}

	private String createSearchSql(SysUserSearchVO sysUserSearchVO) {
		String sql = "";
		if (StringUtil.isNotNullOrEmpty(sysUserSearchVO.getUsername())) {
			sql += " and username=:username";
		}
		if (StringUtil.isNotNullOrEmpty(sysUserSearchVO.getRealname())) {
			sql += " and realname =:realname";
		}
		//当前状态
		if (sysUserSearchVO.getStatus() != null) {
			sql += " and status=:status";
		}
		//创建人
		if (sysUserSearchVO.getCreate_id() != null) {
			sql += " and create_id=:create_id";
		}
		return sql;
	}

	/**
	 * 所有人员列表，查询日志使用
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ComboboxVO> listAllUser(int create_id) {
		String sql = "select id value,realname content from t_sys_user where create_id=? order by realname asc";
		Object[] objects = new Object[] { create_id };
		return jdbcTemplate.query(sql, objects, new BeanPropertyRowMapper(ComboboxVO.class));
	}

}
