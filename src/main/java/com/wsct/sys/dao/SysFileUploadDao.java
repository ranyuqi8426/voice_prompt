package com.wsct.sys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.sys.model.SysFileUpload;
import com.wsct.sys.vo.SysFileUploadVO;
import com.wsct.util.page.PageUtil;
import com.wsct.util.string.StringUtil;

@Repository
public class SysFileUploadDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 增加
	 * 
	 * @param sysFileUpload
	 * @return
	 */
	public int add(SysFileUpload sysFileUpload) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into es_sys_wdgl(id,wjm,wjlj,scr,scsj)";
		sql += " values (seq_es_sys_wdgl.nextval,:wjm,:wjlj,:scr,:scsj)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysFileUpload);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	public int update(SysFileUpload sysFileUpload) {
		String sql = "update es_sys_wdgl set wjm=:wjm,wjlj=:wjlj,scr=:scr,scsj=:scsj where id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysFileUpload);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	public int delete(int id) {
		String sql = "delete from es_sys_wdgl where id=?";
		return jdbcTemplate.update(sql, id);
	}

	public int listCount(SysFileUploadVO sysFileUploadVO) {
		String sql = "select count(*)  from es_sys_wdgl s where 1=1";
		sql += createSearchSql(sysFileUploadVO);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysFileUploadVO);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysFileUpload> list(SysFileUploadVO sysFileUploadVO, int pageIndex, int pageSize) {
		String sql = "select * from  es_sys_wdgl s where 1=1";

		sql += createSearchSql(sysFileUploadVO);
		sql = PageUtil.createOraclePageSQL(sql, pageIndex, pageSize);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysFileUploadVO);
		List<SysFileUpload> list = namedParameterJdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper(SysFileUpload.class));
		return list;
	}

	/**
	 * 拼装线路电气化信息查询条件
	 * 
	 * @param xlGgxxSearchVO
	 * @return
	 */
	private String createSearchSql(SysFileUploadVO sysFileUploadVO) {
		String sql = "";
		if (StringUtil.isNotNullOrEmpty(sysFileUploadVO.getScr())) {
			sql += " and s.scr=:scr"; // 上传人
		}
		if (StringUtil.isNotNullOrEmpty(sysFileUploadVO.getWjm())) {
			sysFileUploadVO.setWjm("%" + sysFileUploadVO.getWjm() + "%");
			sql += " and s.wjm like :wjm"; //文件名
		}

		return sql;
	}

}
