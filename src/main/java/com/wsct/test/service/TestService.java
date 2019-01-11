package com.wsct.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wsct.test.model.Test;
import com.wsct.test.dao.TestDao;
import com.wsct.test.vo.TestVO;

@Service
public class TestService {
	@Autowired
	private TestDao testDao;

	public List<Test> list(TestVO testVO) {
		return testDao.list(testVO);
	}
	public Test get(int id) {
		return testDao.get(id);
	}
	@Transactional
	public int add(TestVO testVO) {
		testDao.add(testVO);
		
		return testDao.add2(testVO);
	}
	public int update(TestVO testVO) {
		return testDao.update(testVO);
	}
	public int delete(String id) {
		return testDao.delete(id);
	}
}
