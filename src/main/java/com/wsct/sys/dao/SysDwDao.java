package com.wsct.sys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.wsct.common.vo.ComboboxVO;
import com.wsct.sys.model.SysDw;
import com.wsct.sys.vo.SysDwSearchVO;
import com.wsct.util.global.EnumGgDwlx;
import com.wsct.util.page.PageUtil;
import com.wsct.util.string.StringUtil;

/**
 * 系统单位Dao
 * @author chykong
 * @date 2016年12月6日
 */
@Repository
public class SysDwDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 新增单位
	 * @param sysDw
	 * @return
	 */
	public int add(SysDw sysDw) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "insert into es_sys_dw(dwid,dwmc,dwjc,dwdm,sjdw,shdw,dwsx,dwlx,sfdm,dwjb,dwfzr,cjsj,px)";
		sql += " values(seq_es_sys_dw.nextval,:dwmc,:dwjc,upper(:dwdm),:sjdw,:shdw,:dwsx,:dwlx,:sfdm,:dwjb,:dwfzr,sysdate,:px)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysDw);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 修改单位
	 * @param sysUser
	 * @return
	 */
	public int update(SysDw sysDw) {
		String sql = "update es_sys_dw set dwmc=:dwmc,dwjc=:dwjc,dwdm=upper(:dwdm),sjdw=:sjdw,shdw=:shdw,dwsx=:dwsx,dwlx=:dwlx,sfdm=:sfdm,dwjb=:dwjb,dwfzr=:dwfzr,xgsj=sysdate,px=:px where dwid=:dwid";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysDw);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 删除单位
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		String sql = "delete from es_sys_dw where dwid=?";
		return jdbcTemplate.update(sql, id);
	}

	/**
	 * 根据id获取单位信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysDw get(int id) {
		String sql = "select * from es_sys_dw where dwid=?";
		Object[] params = new Object[] { id };
		List<SysDw> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysDw.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 根据单位查找
	 * @param dwdm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysDw getByDwdm(String dwdm) {
		String sql = "select * from es_sys_dw where dwdm=?";
		Object[] params = new Object[] { dwdm };
		List<SysDw> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysDw.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 根据单位名称查找
	 * @param dwdm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysDw getByDwmc(String dwmc) {
		String sql = "select * from es_sys_dw where dwmc=?";
		Object[] params = new Object[] { dwmc };
		List<SysDw> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(SysDw.class));
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 查询单位
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysDw> list(SysDwSearchVO sysDwSearchVO, int pageIndex, int pageSize) {
		String sql = "select t.*,(select name from es_dic_pcode where code=dwsx) dwsxmc"
				+ ",(select name from es_dic_pcode where code=dwlx) dwlxmc"
				+ ",(select name from es_dic_pcode where code=dwjb) dwjbmc"
				+ ",(select name from es_dic_pcode where code=sfdm) sfmc  from es_sys_dw t where 1=1 ";
		sql += createSearchSql(sysDwSearchVO);
		sql += " order by px";
		sql = PageUtil.createOraclePageSQL(sql, pageIndex, pageSize);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysDwSearchVO);
		List<SysDw> list = namedParameterJdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper(SysDw.class));
		return list;
	}

	/**
	 * 查询下级单位数
	 * 
	 * @param sysUserSearchVO
	 * @return
	 */
	public int getChildrenCount(int dwid) {
		String sql = "select count(*) from es_sys_dw where sjdw=? ";
		Object[] params = new Object[] { dwid };
		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	/**
	 * 查询用户总数
	 * 
	 * @param sysUserSearchVO
	 * @return
	 */
	public int listCount(SysDwSearchVO sysDwSearchVO) {
		String sql = "select count(*) from es_sys_dw where 1=1 ";
		sql += createSearchSql(sysDwSearchVO);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysDwSearchVO);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
	}

	/**
	 * 生成查询条件sql
	 * @param sysDwSearchVO
	 * @return
	 */
	private String createSearchSql(SysDwSearchVO sysDwSearchVO) {
		String sql = "";
		// 单位id
		if (sysDwSearchVO.getDwid() != null) {
			sql += " and dwid=:dwid";
		}
		// 单位名称
		if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getDwmc())) {
			sql += " and dwmc =:dwmc";
		}
		// 单位简称
		if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getDwjc())) {
			sql += " and dwjc=:dwjc";
		}
		// 单位代码
		if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getDwdm())) {
			sql += " and dwdm=:dwdm";
		}
		// 上级单位,如果上级单位不选择，默认显示总公司下级的
		if (sysDwSearchVO.getSjdw() != null) {
			if (sysDwSearchVO.getSjdw() == 1) {// 如果点击总公司，同时把总公司也列出来
				if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getShowZgs())) {// 判断是否显示总公司
					sql += " and (sjdw=1 or sjdw=0)";
				} else {
					sql += " and  sjdw=1  ";
				}
			} else {
				sql += " and sjdw=:sjdw";
			}
		} else {
			sql += " and sjdw=1";
		}
		// 报表收审单位
		if (sysDwSearchVO.getBbssdw() != null) {
			sql += " and bbssdw=:bbssdw";
		}
		// 单位属性
		if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getDwsx())) {
			sql += " and dwsx=:dwsx";
		}
		// 单位类型
		if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getDwlx())) {
			sql += " and dwlx=:dwlx";
		}
		// 单位级别
		if (StringUtil.isNotNullOrEmpty(sysDwSearchVO.getDwjb())) {
			sql += " and dwjb=:dwjb";
		}

		return sql;
	}

	/**
	 * 获取所有单位
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysDw> listAll() {
		String sql = "select dwid,dwmc,dwdm,sjdw,dwlx,(select count(*) from es_sys_dw where sjdw=m.dwid) cnt  from es_sys_dw m where 1=1 order by px asc ";
		List<SysDw> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysDw.class));
		return list;
	}

	/**
	 * 按照站段查
	 * @param dwsx
	 * @param dwid
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ComboboxVO> listSjdwByZd(String dwsx, int dwid) {
		String sql = "select d.dwid value,d.dwmc content from es_sys_dw d where sjdw=? and d.dwlx=? order by px ";
		Object[] params = new Object[] { dwid, EnumGgDwlx.HZGS.getCode() };
		List<ComboboxVO> list = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(ComboboxVO.class));
		return list;
	}

}
