package com.wsct.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsct.common.vo.ComboboxVO;
import com.wsct.common.dao.CommonDao;

/**
 * 系统公共方法service
 * 
 *
 */
@Service
public class CommonService {
	@Autowired
	private CommonDao commonDao;
	/**
	 * 下拉框列表公共方法
	 * 
	 * @return
	 */
	public List<ComboboxVO> listCombobox(String categoryValue) {
		return commonDao.listCombobox("cfg_pay_param","para_id","para_desc","cfg_para_id","","category_value='"+categoryValue+"'");
	}
	/**
	 * 银行下拉框列表公共方法
	 * 
	 * @return
	 */
	public List<ComboboxVO> listBankCombobox() {
		return commonDao.listBankCombobox("pay_dic_bank","bank_code","bank_name");
	}

}
