package com.wsct.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsct.sys.dao.SysFileUploadDao;
import com.wsct.sys.model.SysFileUpload;
import com.wsct.sys.vo.SysFileUploadVO;

@Service
public class SysFileUploadService {
	@Autowired
	private SysFileUploadDao sysFileUploadDao;

	// 增加
	public int add(SysFileUpload sysFileUpload) {
		int flag = sysFileUploadDao.add(sysFileUpload);
		return flag;
	}

	// 更新
	public int update(SysFileUpload sysFileUpload) {
		int flag = sysFileUploadDao.update(sysFileUpload);
		return flag;
	}

	// 删除
	public int delete(int id) {
		return sysFileUploadDao.delete(id);
	}

	// 总条数
	public int listCount(SysFileUploadVO sysFileUploadVO) {
		return sysFileUploadDao.listCount(sysFileUploadVO);
	}

	// 查询
	public List<SysFileUpload> list(SysFileUploadVO sysFileUploadVO, int pageIndex, int pageSize) {
		List<SysFileUpload> list = sysFileUploadDao.list(sysFileUploadVO, pageIndex, pageSize);
		return list;
	}

}
