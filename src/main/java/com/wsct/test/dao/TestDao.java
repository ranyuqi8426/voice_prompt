package com.wsct.test.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.util.string.StringUtil;
import com.wsct.test.model.Test;
import com.wsct.test.vo.TestVO;

@Repository
public class TestDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Test> list(TestVO testVO) {
		String sql = "select t.* from bns_topic_costrecord t where 1=1";
		sql += createSearchSql(testVO);
		sql += " order by update_time asc";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(testVO);
		List<Test> list = namedParameterJdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<>(Test.class));
		return list;
	}
	public Test get(int id) {
		String sql = "select t.* from bns_topic_costrecord t where 1=1 and topic_cost_id=?";
		Object[] params = new Object[] { id };
		List<Test> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Test.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	public int add(TestVO testVO) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into bns_topic_costrecord(topic_cost_id,pay_type) values(:topic_cost_id,:pay_type)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(testVO);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}
	public int add2(TestVO testVO) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into bns_topic(contract_id,topic_names) values(:contract_id,:topic_name)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(testVO);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}
	public int update(TestVO testVO) {
		String sql = "update bns_topic_costrecord set pay_type=:pay_type where topic_cost_id=:topic_cost_id ";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(testVO);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}
	public int delete(String id) {
		String sql = "delete from bns_topic  where contract_id=? ";
		return jdbcTemplate.update(sql, id);
	}
	/**
	 * 生成查询条件
	 */
	private String createSearchSql(TestVO testVO) {
		String sql = "";
		if (StringUtil.isNotNullOrEmpty(testVO.getPay_type())) {
			sql += " and pay_type = :pay_type";
		}
		if (StringUtil.isNotNullOrEmpty(testVO.getUpdate_cd())) {
			sql += " and update_cd = :update_cd";
		}
		return sql;
	}
}
