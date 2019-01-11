/**
 * 系统单位Js
 */
Ext.onReady(function() {
	var addOrUpdate = 0;
	var selSjdw;// 选中上级单位
	// 定义用到的各个store
	// 单位属性store
	var dwsxStore = Ext.create('Ext.data.JsonStore', {
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
	var dwlxStore = Ext.create('Ext.data.JsonStore', {
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
	var sfdmStore = Ext.create('Ext.data.JsonStore', {
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
	var dwjbStore = Ext.create('Ext.data.JsonStore', {
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

	Ext.define('SysDw', {
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

	var dwStore = Ext.create('Ext.data.Store', {
		model : 'SysDw',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/dw/search.do?showZgs=1',
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
					dwdm : Ext.getCmp('txtDwdm').getValue(),
					dwsx : Ext.getCmp('comboDwsx').getValue(),
					dwlx : Ext.getCmp('comboDwlx').getValue(),
					dwjb : Ext.getCmp('comboDwjb').getValue(),
					sjdw : selSjdw
				});
			}
		}
	});
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});
	var dwToolbar = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ {
			text : '新增',
			hidden : hideSysDwAdd,
			glyph : glyphAdd,
			handler : addDw
		}, '-', , '单位代码:', {
			id : 'txtDwdm',
			xtype : 'textfield',
			fieldStyle : 'text-transform: uppercase',
			width : 100
		}, '单位属性:', {
			id : 'comboDwsx',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : dwsxStore,
			queryMode : 'local',
			width : 100,
			typeAhead : true,
			editable : true
		} ]
	});
	var dwToolbar2 = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ '单位类型:', {
			id : 'comboDwlx',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : dwlxStore,
			queryMode : 'local',
			width : 100,
			typeAhead : true,
			editable : true
		}, '单位级别:', {
			id : 'comboDwjb',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : dwjbStore,
			queryMode : 'local',
			width : 80,
			typeAhead : true,
			editable : true
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '查询',
			handler : function() {
				searchDw();
			}
		}, {
			xtype : 'button',
			glyph : glyphClear,
			text : '清空',
			handler : function() {
				clear();
			}
		} ]
	});

	var dwGrid = Ext.create('Ext.grid.Panel', {
		title : '下级单位列表',
		frame : true,
		glyph : glyphGrid,
		columnWidth : 0.75,
		height : pageH,
		collapsible : true,// 表头缩回按钮
		store : dwStore,
		selModel : selModel,
		columns : [ {
			xtype : 'rownumberer'
		}, {
			text : '上级单位id',
			dataIndex : 'sjdw',
			width : 120,hidden:true,
			sortable : false
		}, {
			text : '单位id',hidden:false,
			dataIndex : 'dwid',
			width : 120,
			sortable : false
		}, {
			text : '单位名称',
			dataIndex : 'dwmc',
			width : 120,
			sortable : false
		}, {
			text : '单位简称',
			dataIndex : 'dwjc',
			width : 120,
			sortable : false
		}, {
			text : '单位代码',
			dataIndex : 'dwdm',
			width : 80,
			sortable : false
		}, {
			text : '单位属性',
			dataIndex : 'dwsxmc',
			width : 80,
			sortable : false
		}, {
			text : '单位类型',
			dataIndex : 'dwlxmc',
			width : 80,
			sortable : false
		}, {
			text : '省份',
			dataIndex : 'sfmc',
			width : 80,
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
		}, {
			text : '创建时间',
			dataIndex : 'cjsj',
			width : 140,
			align : 'left',
			sortable : false
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '操作',
			width : 80,
			align : 'left',
			items : [ {
				text : '修改',
				hidden : hideSysDwUpdate,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					updateDw(grid.getStore().getAt(rowIndex));
				}
			}, {
				text : '删除',
				hidden : hideSysDwDelete,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					deleteDw(grid.getStore().getAt(rowIndex))
				}
			} ]
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
			items : [ dwToolbar, dwToolbar2 ]
		},
		bbar : Ext.create('Ext.PagingToolbar', {
			store : dwStore,
			displayInfo : true
		})
	});

	// 下拉树store
	var comboboxTreeStore = Ext.create('Ext.data.TreeStore', {
		id : 'parentIdStore',
		fields : [ 'id', 'text', 'parent_id' ],
		root : {
			id : -1,
			expanded : false
		},
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/dw/getComboboxTree.do?selType=1'
		},
		autoLoad : false
	})

	var comboboxDwTree = Ext.create('Ext.ux.TreePicker', {
		fieldLabel : '上级单位',
		name : 'sjdw',
		displayField : 'text',
		emptyText : '请选择...',
		allowBlank : true,
		forceSelection : true,
		store : comboboxTreeStore,
		value : 0
	});

	var dwForm = new Ext.create("Ext.form.Panel", {
		id : 'dwForm',
		frame : true,
		border : true,
		width : '100%',
		height : 360,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		layout : 'column',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 90,
			margin : '0 0 5 0',
			msgTarget : 'qtip',
			columnWidth : 1
		},
		items : [ {
			name : 'dwid',
			hidden : true
		}, comboboxDwTree, {
			name : 'dwmc',
			fieldLabel : '单位名称',
			maxLength : 16,
			allowBlank : false
		}, {
			name : 'dwjc',
			fieldLabel : '单位简称',
			maxLength : 16,
			allowBlank : false
		}, {
			name : 'dwdm',
			fieldLabel : '单位代码',
			maxLength : 6,
			fieldStyle : 'text-transform: uppercase',
			allowBlank : false
		}, {
			name : 'dwsx',
			fieldLabel : '单位属性',
			allowBlank : false,
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : dwsxStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false
		}, {
			name : 'dwlx',
			fieldLabel : '单位类型',
			allowBlank : false,
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : dwlxStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false
		}, {
			name : 'sfdm',
			fieldLabel : '省份',
			allowBlank : false,
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : sfdmStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false
		}, {
			name : 'dwjb',
			fieldLabel : '单位级别',
			allowBlank : false,
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : dwjbStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false
		}, {
			name : 'dwfzr',
			fieldLabel : '单位负责人',
			maxLength : 5,
			allowBlank : false
		}, {
			name : 'px',
			fieldLabel : '排序',
			allowBlank : false,
			vtype : 'posInt'
		} ],
		buttons : [ {
			text : '保 存',
			iconCls : "save",
			glyph : glyphSave,
			handler : saveDw
		}, {
			text : '返回',
			iconCls : "close",
			glyph : glyphBack,
			handler : function() {
				dwWin.hide();
			}
		} ]
	});

	var dwWin = new Ext.Window({
		width : 400,
		height : 400,
		title : '新增/修改单位',
		glyph : glyphWindow,
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ dwForm ]
	});
	/************************左侧单位树****************************/
	Ext.define('SysDwTree', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'dwid',
			type : 'int'
		}, {
			name : 'dwmc',
			type : 'string'
		}, {
			name : 'dwdm',
			type : 'string'
		} ]
	});

	var dwTreestore = Ext.create('Ext.data.TreeStore', {
		model : 'SysDwTree',
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/dw/getTreeGrid.do?selType=1'
		},
		root : {
			expanded : true
		}
	});
	var dwTreeToolbar = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ {
			xtype : 'button',
			glyph : glyphSearch,
			text : '刷新',
			handler : function() {
				dwTreestore.reload();
			}
		} ]
	});
	var dwTree = Ext.create('Ext.tree.Panel', {
		title : '单位列表',
		frame : true,
		glyph : glyphTree,
		useArrows : false,
		rootVisible : false,
		columnWidth : 0.25,
		height : pageH,
		store : dwTreestore,
		columns : [ {
			xtype : 'treecolumn',
			text : '单位名称',
			width : 200,
			sortable : false,
			dataIndex : 'dwmc'
		}, {
			text : '单位代码',
			dataIndex : 'dwdm',
			width : 80,
			sortable : false
		} ],
		tbar : dwTreeToolbar,
		viewConfig : {
			stripeRows : true
		}
	});
	// 单击行事件
	dwTree.on('select', function(grid, record, rowIndex, e) {
		if (record.data.dwid == null)
			return;
		selSjdw = record.data.dwid;
		searchDw();
	});
	var mainPanel = Ext.create('Ext.panel.Panel', {
		renderTo : Ext.getBody(),
		// layout : 'border',
		layout : 'column',
		width : '100%',
		height : pageH,
		defaults : {
			collapsible : true,
			split : false,
			bodyPadding : 0
		},
		bodyBorder : false,
		items : [ dwTree, dwGrid ]
	})

	/****************************************************/
	/**
		 * 清空
		 */
	function clear() {
		Ext.getCmp('txtDwdm').setValue("");
		Ext.getCmp('comboDwsx').setValue("");
		Ext.getCmp('comboDwlx').setValue("");
		Ext.getCmp('comboDwjb').setValue("");
	}
	// 用户查询
	function searchDw() {
		dwStore.loadPage(1);
	}

	function addDw() {
		dwForm.getForm().reset();
		dwForm.getForm().findField("dwid").setValue("0");
		// comboboxTree.setValue(1);
		comboboxTreeStore.load();
		comboboxDwTree.setValue(selSjdw);
		dwWin.setTitle("『新增单位』");
		dwWin.show();
		addOrUpdate = 1;
	}

	function updateDw(record) {
		// comboboxTreeStore.reload();
		dwForm.getForm().loadRecord(record);
		dwWin.setTitle("『修改单位』");
		comboboxTreeStore.load();
		dwWin.show();
		addOrUpdate = 2;
	}

	function saveDw() {
		var url = "";
		if (addOrUpdate == 1)
			url = basePath + '/sys/dw/add.do';
		else
			url = basePath + '/sys/dw/update.do';
		if (dwForm.getForm().isValid()) {
			dwForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					dwWin.hide();
					dwStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}
	/**
	 * 删除单位
	 */
	function deleteDw(record) {
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/dw/delete.do',
					params : {
						dwid : record.data.dwid
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								dwStore.reload();
							});
						} else {
							Ext.Msg.alert('操作提示', result.msgText);
						}
					}
				});
			}
		});
	}
	// 自适应大小
	Ext.GlobalEvents.on('resize', function(width, height, eOpts) {
		mainPanel.setWidth('100%');
	})
});