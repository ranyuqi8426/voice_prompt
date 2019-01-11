var setUserID;
Ext.define('User', { // 定义Book Model
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'user_id', // id
		type : 'int'
	}, {
		name : 'user_name',
		type : 'string'
	}, {
		name : 'mobile',
		type : 'string'
	}, {
		name : 'user_status',
		type : 'string'
	} ]
});

function renderUserStatus(val) {
	if (val == '0') {
		return '冻结';
	} else if (val == '1') {
		return '激活';
	}else
		return '';
}

var selStatusStore = Ext.create('Ext.data.Store', {
	model : 'ComboboxData',
	data : [ {
		'value' : '0',
		'content' : '冻结'
	}, {
		'value' : '1',
		'content' : '激活'
	} ]
});

var selUserStore = Ext.create('Ext.data.Store', {
	model : 'User',
	pageSize : pagelimit,
	proxy : {
		type : 'ajax',
		url : basePath + '/user/info/selSearch.do',
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
				user_name : Ext.getCmp("txtSelUserName").getValue(),
				mobile : Ext.getCmp("txtSelMobile").getValue(),
				status : Ext.getCmp('comboSelStatus').getValue()
			});
		}
	}
});

var selUserModel = Ext.create('Ext.selection.CheckboxModel', {
	mode : 'SINGLE'
});

var selUserGrid = Ext.create('Ext.grid.Panel', { // 表格panel
	width : '100%',
	height : 420,
	// columnLines : true,
	store : selUserStore,
	selModel : selUserModel,
	columns : [ {
		xtype : 'rownumberer',
		width : 40
	}, {
		text : '用户名',
		dataIndex : 'user_name',
		width : 140,
		sortable : true
	}, {
		text : '手机号',
		dataIndex : 'mobile',
		width : 120,
		sortable : false
	}, {
		text : '状态',
		dataIndex : 'user_status',
		renderer:renderUserStatus,
		flex : 1,
		sortable : false
	} ],
	viewConfig : {
		stripeRows : true,
		enableTextSelection : true
	},
	tbar : Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ '用户名', {
			xtype : 'textfield',
			width : 100,
			id : 'txtSelUserName'
		}, '手机号', {
			xtype : 'textfield',
			width : 100,
			id : 'txtSelMobile'
		}, '状态', {
			xtype : 'combobox',
			width : 100,
			id : 'comboSelStatus',
			store : selStatusStore,
			valueField : 'value',
			displayField : 'content',
			queryMode : 'local'
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '查询',
			handler : function() {
				selUserStore.loadPage(1);
			}
		} ]
	}),
	bbar : Ext.create('Ext.PagingToolbar', {
		store : selUserStore,
		displayInfo : true
	})
});
// 双击事件
selUserGrid.on('itemdblclick', function(grid, rec, item, rowIndex, e, eOpts) {
	selClickUserRow(rec);
});

// 选择用户信息窗口
var selUserWin = new Ext.Window({
	width : 600,
	height : 500,
	glyph : glyphWindow,
	title : '选择用户信息',
	modal : true,
	closeAction : 'hide',
	padding : 4,
	items : [ selUserGrid ],
	buttonAlign : 'center',
	buttons : [ {
		text : '选择',
		glyph : glyphSelect,
		handler : function() {
			var rec = selUserGrid.getSelectionModel().getLastSelected();
			if (!rec)
				return;
			selClickUserRow(rec);
		}
	} ]
});

function selClickUserRow(rec) {
	if (setUserID == 'SellLimitUser') { // 销售订单
		Ext.getCmp("user_id").setValue(rec.data.user_id);
		Ext.getCmp("user_name").setValue(rec.data.user_name);
	}
	selUserWin.hide();
}
// /凡是需要选择用户编号的地方，传一个编号输入框的id。
function showUser(cust_code) {
	selUserStore.load();
	setUserID = cust_code;
	selUserWin.show();
}
