package com.wsct.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wsct.sys.dao.SysTzggDao;
import com.wsct.sys.model.SysTzgg;
import com.wsct.sys.vo.SysTzggSearchVO;

@Service
public class SysTzggService {

	@Autowired
	private SysTzggDao sysTzggDao;

	/**
	 * 新增通知公告信息
	 * 
	 * @return
	 */
	public int add(SysTzgg sysTzgg) {
			int flag = sysTzggDao.add(sysTzgg);
			return flag;
	}

	/**
	 * 修改通知公告信息
	 * 
	 * @return
	 */
	public int update(SysTzgg sysTzgg) {
			int flag = sysTzggDao.update(sysTzgg);
			return flag;
	}

	/**
	 * 删除通知公告信息
	 * 
	 * @return
	 */
	public int delete(int id) {
		return sysTzggDao.delete(id);
	}

	/**
	 * 通知公告信息列表
	 * 
	 * @param sysTzggSearchVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysTzgg> list(SysTzggSearchVO sysTzggSearchVO, int pageIndex, int pageSize) {
		List<SysTzgg> list = sysTzggDao.list(sysTzggSearchVO, pageIndex, pageSize);
		return list;
	}

	/**
	 * 通知公告信息列表总数
	 * 
	 * @param sysTzggSearchVO
	 * @return
	 */
	public int listCount(SysTzggSearchVO sysTzggSearchVO) {
		return sysTzggDao.listCount(sysTzggSearchVO);
	}


}

