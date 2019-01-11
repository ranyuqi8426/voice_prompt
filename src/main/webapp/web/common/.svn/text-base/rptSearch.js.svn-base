var rptBottomToolbar = Ext.create('Ext.toolbar.Toolbar', {
	layout : {
		overflowHandler : 'Menu'
	},
	items : [ '单位负责人:', {
		id : 'txtRptDwfzr',
		readOnly : true,
		xtype : 'textfield',
		width : 120
	}, '制表人:', {
		id : 'txtRptZbr',
		readOnly : true,
		xtype : 'textfield',
		width : 120
	}, '电话:', {
		id : 'txtRptZbrdh',
		readOnly : true,
		xtype : 'textfield',
		width : 120
	}, '报出日期:', {
		id : 'txtRptBcrq',
		readOnly : true,
		xtype : 'textfield',
		width : 120
	} ]
});
/**
 * 查询报表的基本信息
 * @param bh编号
 * @param bbzl报表种类
 * @param bbrq报表日期
 * @param dwsx单位属性
 * @param hzdw汇总单位即报表单位
 * @returns
 */
function searchSbxx(bh, bbzl, bbrq, dwsx, dwid) {
	Ext.Ajax.request({
		url : basePath + '/rpt/sbxx/getSbxxInRptSearch.do',
		params : {
			bh : bh,
			bbzl : bbzl,
			bbrq : bbrq,
			dwsx : dwsx,
			dwid : dwid
		}, 
		success : function(response) {
			var result = Ext.decode(response.responseText);
			if (result.id != 0) {
				Ext.getCmp('txtRptDwfzr').setValue(result.dwfzr);
				Ext.getCmp('txtRptZbr').setValue(result.sbr);
				Ext.getCmp('txtRptZbrdh').setValue(result.sbrdh);
				if (result.sbrq != null && result.sbrq != '') {
					Ext.getCmp('txtRptBcrq').setValue(
							result.sbrq.substr(0, 4) + '年' + result.sbrq.substr(5, 2) + '月' + result.sbrq.substr(8, 2)
									+ '日');
				} else {
					Ext.getCmp('txtRptBcrq').setValue('');
				}
			} else {
				Ext.getCmp('txtRptDwfzr').setValue('');
				Ext.getCmp('txtRptZbr').setValue('');
				Ext.getCmp('txtRptZbrdh').setValue('');
				Ext.getCmp('txtRptBcrq').setValue('');
				Ext.Msg.alert('操作提示', '数据不存在！');
			}
		}
	});

}