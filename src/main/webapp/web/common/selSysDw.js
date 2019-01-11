/**
 * 选择单位
 */
var selDwId;
// 单位属性store
var selDwsxStore = Ext.create('Ext.data.JsonStore', {
	model : 'ComboboxData',
	proxy : {
		type : 'ajax',
		url : basePath + '/common/listPcodeCombobox.do?pcode=9003',
		reader : {
			type : 'json'
		}
	},
	autoLoad : true
});
// 单位类型Store
var selDwlxStore = Ext.create('Ext.data.JsonStore', {
	model : 'ComboboxData',
	proxy : {
		type : 'ajax',
		url : basePath + '/common/listPcodeCombobox.do?pcode=9008',
		reader : {
			type : 'json'
		}
	},
	autoLoad : true
});
// 省份Store
var selSfdmStore = Ext.create('Ext.data.JsonStore', {
	model : 'ComboboxData',
	proxy : {
		type : 'ajax',
		url : basePath + '/common/listPcodeCombobox.do?pcode=9002',
		reader : {
			type : 'json'
		}
	},
	autoLoad : true
});

// 单位级别
var selDwjbStore = Ext.create('Ext.data.JsonStore', {
	model : 'ComboboxData',
	proxy : {
		type : 'ajax',
		url : basePath + '/common/listPcodeCombobox.do?pcode=9007',
		reader : {
			type : 'json'
		}
	},
	autoLoad : true
});

Ext.define('SelSysDw', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'dwid',
		type : 'int'
	}, {
		name : 'dwmc',
		type : 'string'
	}, {
		name : 'dwjc',
		type : 'string'
	}, {
		name : 'dwdm',
		type : 'string'
	}, {
		name : 'sjdw',
		type : 'string'
	}, {
		name : 'bbssdw',
		type : 'int'
	}, {
		name : 'dwsx',
		type : 'string'
	}, {
		name : 'dwsxmc',
		type : 'string'
	}, {
		name : 'dwlx',
		type : 'string'
	}, {
		name : 'dwlxmc',
		type : 'string'
	}, {
		name : 'sfdm',
		type : 'string'
	}, {
		name : 'sfmc',
		type : 'string'
	}, {
		name : 'sfmc',
		type : 'string'
	}, {
		name : 'dwjb',
		type : 'string'
	}, {
		name : 'dwjbmc',
		type : 'string'
	}, {
		name : 'dwfzr',
		type : 'string'
	}, {
		name : 'cjsj',
		type : 'string'
	}, {
		name : 'xgsj',
		type : 'string'
	}, {
		name : 'px',
		type : 'int'
	} ]
});
// 下拉树store
var selComboboxTreeStore = Ext.create('Ext.data.TreeStore', {
	fields : [ 'id', 'text', 'parent_id' ],
	root : {
		id : -1,
		expanded : false
	},
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/dw/getComboboxTree.do'
	},
	autoLoad : false
})

var selComboboxDwTree = Ext.create('Ext.ux.TreePicker', {
	name : 'selComboboxSjdw',
	displayField : 'text',
	emptyText : '请选择...',
	width : 160,
	allowBlank : false,
	forceSelection : true,
	store : selComboboxTreeStore,
	value : 0
});
var selDwStore = Ext.create('Ext.data.Store', {
	model : 'SelSysDw',
	pageSize : pagelimit,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/dw/search.do',
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
				dwdm : Ext.getCmp('txtSelDwdm').getValue(),
				dwsx : Ext.getCmp('comboSelDwsx').getValue(),
				dwlx : Ext.getCmp('comboSelDwlx').getValue(),
				dwjb : Ext.getCmp('comboSelDwjb').getValue(),
				sjdw : selComboboxDwTree.getValue()
			});
		}
	}
});
var selDwModel = Ext.create('Ext.selection.CheckboxModel', {
	id : 'selDwModel',
	mode : 'MULTI'
});
var selDwToolbar = Ext.create('Ext.toolbar.Toolbar', {
	layout : {
		overflowHandler : 'Menu'
	},
	items : [ '上级单位:', selComboboxDwTree, '单位代码:', {
		id : 'txtSelDwdm',
		xtype : 'textfield',
		fieldStyle : 'text-transform: uppercase',
		width : 80
	}, '单位属性:', {
		id : 'comboSelDwsx',
		xtype : 'combobox',
		valueField : 'value',
		displayField : 'content',
		store : selDwsxStore,
		queryMode : 'local',
		width : 80,
		typeAhead : true,
		editable : true
	}, '单位类型:', {
		id : 'comboSelDwlx',
		xtype : 'combobox',
		valueField : 'value',
		displayField : 'content',
		store : selDwlxStore,
		queryMode : 'local',
		width : 80,
		typeAhead : true,
		editable : true
	}, '单位级别:', {
		id : 'comboSelDwjb',
		xtype : 'combobox',
		valueField : 'value',
		displayField : 'content',
		store : selDwjbStore,
		queryMode : 'local',
		width : 80,
		typeAhead : true,
		editable : true
	}, {
		xtype : 'button',
		glyph : glyphSearch,
		text : '查询',
		handler : function() {
			selDwStore.load();
		}
	}, {
		xtype : 'button',
		glyph : glyphClear,
		hidden : true,
		text : '清空',
		handler : function() {
			Ext.getCmp('txtSelDwdm').setValue("");
			Ext.getCmp('comboSelDwsx').setValue("");
			Ext.getCmp('comboSelDwlx').setValue("");
			Ext.getCmp('comboSelDwjb').setValue("");
		}
	} ]
});

var selDwGrid = Ext.create('Ext.grid.Panel', {
	header : false,
	frame : true,
	width : '100%',
	height : 460,
	collapsible : true,// 表头缩回按钮
	store : selDwStore,
	selModel : selDwModel,
	columns : [ {
		xtype : 'rownumberer'
	}, {
		text : '单位名称',
		dataIndex : 'dwmc',
		width : 120,
		sortable : false
	}, {
		text : '单位简称',
		dataIndex : 'dwjc',
		width : 80,
		sortable : false
	}, {
		text : '单位代码',
		dataIndex : 'dwdm',
		width : 100,
		sortable : false
	}, {
		text : '单位属性',
		dataIndex : 'dwsxmc',
		width : 100,
		sortable : false
	}, {
		text : '单位类型',
		dataIndex : 'dwlxmc',
		width : 100,
		sortable : false
	}, {
		text : '省份',
		dataIndex : 'sfmc',
		width : 100,
		sortable : false
	}, {
		text : '单位级别',
		dataIndex : 'dwjbmc',
		width : 80,
		sortable : false
	}, {
		text : '单位负责人',
		dataIndex : 'dwfzr',
		width : 100,
		align : 'left',
		sortable : false
	}, {
		text : '排序',
		dataIndex : 'px',
		width : 80,
		align : 'left',
		sortable : false
	} ],
	viewConfig : {
		stripeRows : true,
		enableTextSelection : true
	},
	tbar : {
		xtype : 'container',
		layout : 'anchor',
		defaults : {
			anchor : '0'
		},
		defaultType : 'toolbar',
		items : [ selDwToolbar ]
	},
	bbar : Ext.create('Ext.PagingToolbar', {
		store : selDwStore,
		displayInfo : true
	})
});

var selDwWin = new Ext.Window({
	width : 1000,
	height : 500,
	title : '选择单位',
	glyph : glyphWindow,
	modal : true,
	closeAction : 'hide',
	padding : 4,
	items : [ selDwGrid ],
	buttonAlign : 'center',
	buttons : [ {
		text : '选择',
		glyph : glyphSelect,
		handler : function() {
			var rec = selDwGrid.getSelectionModel().getLastSelected();
			if (!rec)
				return;
			if (selDwId == 'bbdwjSelDw') {// 报表单位集选择单位
				var recs = selDwGrid.getSelectionModel().getSelection();
				if (recs.length == 0) {
					Ext.Msg.alert('操作提示', '请选择单位');
					return;
				}
				var sjdwGrid = Ext.getCmp('sjdwGrid');
				for (var i = 0; i < recs.length; i++) {
					var sjdw = Ext.create('DicBbdwj', {
						bh : '',
						dqdw : '',
						hzdw : '',
						sjdw : recs[i].data.dwid,
						sjdwmc : recs[i].data.dwmc,
						px : sjdwGrid.getStore().getCount() + 1
					});
					var flag = true;// 判断是否存在,存在则不添加
					for (var j = 0; j < sjdwGrid.getStore().getCount(); j++) {
						var existRec = sjdwGrid.getStore().getAt(j);
						if (existRec.data.sjdw == recs[i].data.dwid) {
							flag = false;
							break;
						}
					}
					if (flag == true)
						sjdwGrid.getStore().insert(sjdwGrid.getStore().getCount(), sjdw);
				}

			}
			selDwWin.hide();
		}
	} ]
});

// /凡是需要选择线路的地方，线路的表单编号。
function showSelDw(curPageCode) {
	selComboboxTreeStore.load();
	selDwId = curPageCode;
	if (curPageCode == '') {
		selDwModel.mode = 'MULTI';
	}
	selDwWin.show();
}
