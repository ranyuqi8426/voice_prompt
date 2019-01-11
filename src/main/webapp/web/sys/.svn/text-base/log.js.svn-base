Ext.onReady(function() {

	// 用户store
	var userStore = Ext.create('Ext.data.JsonStore', {
		model : 'ComboboxData',
		// fields : [ 'value', 'content' ],
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/user/listAllSysUser.do',
			reader : {
				type : 'json'
			}
		},
		autoLoad : true
	});

	Ext.define('SysLog', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'user_id',
			type : 'string'
		}, {
			name : 'realname',
			type : 'string'
		}, {
			name : 'opera_date',
			type : 'string'
		}, {
			name : 'opera_ip',
			type : 'string'
		}, {
			name : 'module_name',
			type : 'string'
		}, {
			name : 'opera_name',
			type : 'string'
		}, {
			name : 'oprea_url',
			type : 'string'
		}, {
			name : 'opera_params',
			type : 'string'
		} ]
	});

	var logStore = Ext.create('Ext.data.Store', {
		model : 'SysLog',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/log/search.do',
			actionMethods : {
				read : "POST"
			},
			reader : {
				rootProperty : 'root'
			}
		},
		autoLoad : false,
		listeners : {
			beforeload : function(store) {
				Ext.apply(store.proxy.extraParams, {
					user_id : Ext.getCmp('comboUser').getValue(),
					s_date : Ext.Date.format(Ext.getCmp('txtS_date').getValue(), 'Y-m-d'),
					e_date : Ext.Date.format(Ext.getCmp('txtE_date').getValue(), 'Y-m-d')
				});
			}
		}
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});

	var logGrid = Ext.create('Ext.grid.Panel', {
		title : '操作日志列表',
		frame : true,
		glyph : glyphGrid,
		width : '100%',
		height : pageH,
		// columnLines : true,
		collapsible : true,// 表头缩回按钮
		store : logStore,
		selModel : selModel,
		renderTo : Ext.getBody(),
		columns : [ {
			xtype : 'rownumberer',
			width : 60
		}, {
			text : '登录账号',
			dataIndex : 'username',
			width : 100,
			sortable : false
		}, {
			text : '用户姓名',
			dataIndex : 'realname',
			width : 100,
			sortable : false
		}, {
			text : '操作时间',
			dataIndex : 'opera_date',
			width : 160,
			sortable : false
		}, {
			text : 'IP',
			dataIndex : 'opera_ip',
			width : 120,
			sortable : false
		}, {
			text : '模块名称',
			dataIndex : 'module_name',
			width : 120,
			sortable : false
		}, {
			text : '操作名称',
			dataIndex : 'opera_name',
			width : 80,
			sortable : false
		}, {
			text : '操作url',
			dataIndex : 'opera_url',
			width : 160,
			sortable : false
		}, {
			text : '参数',
			dataIndex : 'opera_params',
			flex : 1,
			align : 'left',
			sortable : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		tbar : [ '用户：', {
			id : 'comboUser',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : userStore,
			queryMode : 'local',
			width : 180,
			typeAhead : true,
			editable : true
		}, '起始日期:', {
			xtype : 'datefield',
			format : 'Y-m-d',
			width : 120,
			value : s_date,
			id : 'txtS_date'
		}, '终止日期', {
			xtype : 'datefield',
			format : 'Y-m-d',
			width : 120,
			value : e_date,
			id : 'txtE_date'
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '查询',
			handler : function() {
				searchLog();
			}
		}, '-', {
			xtype : 'button',
			glyph : glyphExcel,
			text : '导出',
			handler : function() {
				exportLog();
			}
		} ],
		bbar : Ext.create('Ext.PagingToolbar', {
			store : logStore,
			displayInfo : true
		})
	});

	/** *******************按钮方法******************************* */
	// 用户查询
	function searchLog() {
		logStore.loadPage(1);
	}

	// 导出数据
	function exportLog() {
		var user_id = Ext.getCmp('comboUser').getValue();
		if (!user_id)
			user_id = 0;
		location.href = basePath + '/sys/log/downloadLog.do?user_id=' + user_id + '&s_date='
				+ Ext.Date.format(Ext.getCmp('txtS_date').getValue(), 'Y-m-d') + "&e_date="
				+ Ext.Date.format(Ext.getCmp('txtE_date').getValue(), 'Y-m-d');
	}

	// 自适应大小
	Ext.GlobalEvents.on('resize', function() {
		logGrid.getView().refresh();
		// userRoleGrid.getView().refresh();
		logGrid.setWidth('100%')
	})
});