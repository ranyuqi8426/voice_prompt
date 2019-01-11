package com.wsct.sys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.sys.model.SysTzgg;
import com.wsct.sys.vo.SysTzggSearchVO;
import com.wsct.util.page.PageUtil;
import com.wsct.util.string.StringUtil;

@Repository
public class SysTzggDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 新增
	 * 
	 * @param SysTzgg
	 * @return
	 */
	public int add(SysTzgg sysTzgg) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into es_sys_tzgg(id,bt,nr,fbr,fbsj,px,wjlj)"
				+ " values (seq_es_sys_tzgg.nextval,:bt,:nr,:fbr,:fbsj,:px,:wjlj)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysTzgg);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 修改
	 * 
	 * @param sysTzgg
	 * @return
	 */
	public int update(SysTzgg sysTzgg) {
		String sql = "update es_sys_tzgg set bt=:bt,nr=:nr,fbr=:fbr,fbsj=:fbsj,px=:px,wjlj=:wjlj where id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysTzgg);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		String sql = "delete from es_sys_tzgg where id=?";
		return jdbcTemplate.update(sql, id);
	}

	/**
	 * 查询车站信息
	 * 
	 * @param xlGgxxSearchVo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysTzgg> list(SysTzggSearchVO sysTzggSearchVO, int pageIndex, int pageSize) {
		String sql = "select * from es_sys_tzgg where 1=1 ";
		sql += createSearchSql(sysTzggSearchVO);
		sql += " order by px ";
		sql = PageUtil.createOraclePageSQL(sql, pageIndex, pageSize);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysTzggSearchVO);
		List<SysTzgg> list = namedParameterJdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper(SysTzgg.class));
		return list;
	}

	/**
	 * 查询车站信息总数
	 * 
	 * @param xlggxxSearchVO
	 * @return
	 */
	public int listCount(SysTzggSearchVO sysTzggSearchVO) {
		String sql = "select count(*)  from es_sys_tzgg where 1=1 ";
		sql += createSearchSql(sysTzggSearchVO);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysTzggSearchVO);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
	}

	/**
	 * 拼装车站信息查询条件
	 * 
	 * @param sysTzggSearchVO
	 * @return
	 */
	private String createSearchSql(SysTzggSearchVO sysTzggSearchVO) {
		String sql = "";
		if (StringUtil.isNotNullOrEmpty(sysTzggSearchVO.getBt())) {
			sql += " and bt=:bt"; // 标题
		}
		return sql;
	}
}
