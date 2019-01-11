Ext.onReady(function() {
	// 操作类型
	function renderType(val) {
		if (val == 0)
			return '读';
		else
			return '写';
	}
	// /图标
	function renderIcon(val) {
		return '<span style="font-family:FontAwesome;">&#' + val + '</span>';
	}
	// 是否锁定store
	var typeStore = Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			"value" : "0",
			"content" : "读"
		}, {
			"value" : "1",
			"content" : "写"
		} ]
	});
	Ext.define('SysModule', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'name',
			type : 'string'
		}, {
			name : 'code',
			type : 'string'
		}, {
			name : 'parent_id',
			type : 'int'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'iconImg',
			type : 'string'
		}, {
			name : 'target',
			type : 'string'
		}, {
			name : 'display_order',
			type : 'int'
		} ]
	});

	var moduleStore = Ext.create('Ext.data.TreeStore', {
		model : 'SysModule',
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/module/getTreeGrid.do'
		},
		root : {
			expanded : true
		}
	});
	var moduleTree = Ext.create('Ext.tree.Panel', {
		title : '模块列表',
		frame : true,
		glyph : glyphTree,
		// useArrows : true,
		layout : 'fit',
		rootVisible : false,
		columnWidth : 0.5,
		// columnLines: true,
		// layout : 'column',
		// autoWidth : true,
		// width : '100%',
		height : pageH,
		collapsible : true,
		store : moduleStore,
		bufferedRenderer : false,
		columns : [ {
			xtype : 'treecolumn',
			text : '模块名称',
			width : 150,
			sortable : false,
			dataIndex : 'name'
		}, {
			text : '模块代码',
			dataIndex : 'code',
			width : 120,
			sortable : false
		},{
			text : 'ID',
			dataIndex : 'id',
			width : 60,
			sortable : false
		},  {
			text : '链接',
			dataIndex : 'url',
			width : 120,
			sortable : false
		}, {
			header : '链接目标',
			dataIndex : 'target',
			width : 60,
			sortable : false
		}, {
			header : '图标',
			dataIndex : 'iconImg',
			width : 60,
			sortable : false,
			renderer : renderIcon
		}, {
			header : '排序',
			dataIndex : 'display_order',
			flex : 1,
			sortable : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		tbar : [ {
			text : '新增',
			glyph : glyphAdd,
			hidden : hideSysModuleAdd,
			handler : add
		}, {
			text : '修改',
			glyph : glyphUpdate,
			hidden : hideSysModuleUpdate,
			handler : update
		}, {
			text : '删除',
			hidden : hideSysModuleDelete,
			glyph : glyphDelete,
			handler : del
		}, '-', {
			text : '刷新',
			glyph : glyphRefresh,
			handler : function() {
				moduleStore.reload();
			}
		}, {
			text : '清空缓存',
			glyph : glyphClear,
			hidden : hideSysModuleClearCache,
			handler : function() {
				Ext.Ajax.request({
					url : basePath + '/sys/module/clearCache.do',
					success : function(response) {
						Ext.Msg.alert('操作提示', '操作成功');
					}
				});
			}
		} ]
	});
	// 单击行事件
	moduleTree.on('select', function(grid, record, rowIndex, e) {
		if (record.data.id == null)
			return;
		functionStore.load({
			params : {
				module_id : record.data.id
			}
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
			url : basePath + '/sys/module/getComboboxTree.do'
		},
		autoLoad : true
	})

	var comboboxTree = Ext.create('Ext.ux.TreePicker', {
		fieldLabel : '上级节点',
		name : 'parent_id',
		displayField : 'text',
		emptyText : '请选择...',
		allowBlank : false,
		forceSelection : true,
		store : comboboxTreeStore,
		value : 0
	});

	var targetStore = Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			"value" : "main",
			"content" : "main"
		}, {
			"value" : "blank",
			"content" : "blank"
		} ]
	});
	var moduleForm = new Ext.create("Ext.form.Panel", {
		id : 'moduleForm',
		frame : true,
		border : true,
		width : '100%',
		height : 250,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		fieldDefaults : {
			anchor : '100%',
			labelAlign : 'right',
			labelWidth : 90,
			msgTarget : 'qtip'
		},
		items : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'name',
			fieldLabel : '模块名称',
			maxLength : 20,
			maxLengthText : '不能超过20个字符',
			allowBlank : false
		}, comboboxTree, {
			name : 'code',
			fieldLabel : '模块代码',
			allowBlank : false
		}, {
			name : 'url',
			fieldLabel : '模块链接',
			allowBlank : false
		}, {
			name : 'target',
			fieldLabel : '链接目标',
			allowBlank : false,
			value : 'main',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : targetStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false
		}, {
			xtype : 'container',
			layout : 'hbox',
			defaultType : 'textfield',
			margin : '0 0 5 0',
			items : [ {
				name : 'iconImg',
				fieldLabel : '图标',
				id : 'iconImg',
				allowBlank : false
			}, {
				xtype : 'button',
				text : '选择',
				handler : function() {
					iconWin.show();
				}
			} ]
		}, {
			name : 'display_order',
			fieldLabel : '排序',
			allowBlank : false,
			regex : /^\d+$/
		} ],
		buttons : [ {
			text : '保 存',
			iconCls : "save",
			glyph : glyphSave,
			handler : moduleSave
		}, {
			text : '返回',
			iconCls : "close",
			glyph : glyphBack,
			handler : function() {
				moduleWin.hide();
			}
		} ]
	});

	var moduleWin = new Ext.Window({
		width : 400,
		height : 300,
		title : '新增/修改模块',
		glyph : glyphWindow,
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ moduleForm ]
	});

	var iconWin = new Ext.Window({
		id : 'iconWin',
		width : 800,
		height : 450,
		title : '选择图标',
		glyph : glyphWindow,
		modal : false,
		closeAction : 'hide',
		padding : 4,
		dockedItems : icons,
		buttons : [ {
			text : '关闭',
			iconCls : "close",
			glyph : glyphClose,
			handler : function() {
				iconWin.hide();
			}
		} ]
	});

	/** *****************功能操作定义*************************** */
	Ext.define('SysFunction', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'name',
			type : 'string'
		}, {
			name : 'code',
			type : 'string'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'type',
			type : 'int'
		}, {
			name : 'description',
			type : 'string'
		}, {
			name : 'display_order',
			type : 'int'
		} ]
	});

	var functionStore = Ext.create('Ext.data.Store', {
		model : 'SysFunction',
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/function/listJson.do'
		},
		autoLoad : false
	});

	var selFunctionModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});
	var functionGrid = Ext.create('Ext.grid.Panel', {
		title : '功能操作列表',
		frame : true,
		glyph : glyphGrid,
		// width : 600,
		columnWidth : 0.5,
		margin : '0 0 0 2',
		height : pageH,
		// columnLines : true,
		collapsible : true,
		store : functionStore,
		selModel : selFunctionModel,
		columns : [ {
			xtype : 'rownumberer'
		}, {
			text : '操作名称',
			dataIndex : 'name',
			width : 80,
			sortable : false
		}, {
			text : '操作代码',
			dataIndex : 'code',
			width : 100,
			sortable : false
		}, {
			text : '操作url',
			dataIndex : 'url',
			width : 120,
			sortable : false
		}, {
			text : '操作类型',
			dataIndex : 'type',
			width : 80,
			sortable : false,
			renderer : renderType
		}, {
			text : '描述',
			dataIndex : 'description',
			width : 100,
			sortable : false
		}, {
			text : '排序',
			dataIndex : 'display_order',
			flex : 1,
			sortable : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		tbar : [ {
			text : '新增',
			glyph : glyphAdd,
			handler : addFunction
		}, {
			text : '修改',
			glyph : glyphUpdate,
			handler : updateFunction
		}, {
			text : '删除',
			glyph : glyphDelete,
			handler : delFunction
		}, '-', {
			text : '刷新',
			glyph : glyphRefresh,
			handler : function() {
				functionStore.reload();
			}
		} ]
	});

	var functionForm = new Ext.create("Ext.form.Panel", {
		id : 'functionForm',
		frame : true,
		border : true,
		width : '100%',
		height : 250,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 90,
			anchor : '100%',
			msgTarget : 'qtip'
		},
		items : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'module_id',
			hidden : true
		}, {
			name : 'name',
			fieldLabel : '操作名称',
			maxLength : 20,
			allowBlank : false
		}, {
			name : 'code',
			fieldLabel : '操作代码',
			maxLength : 30,
			allowBlank : false
		}, {
			name : 'url',
			fieldLabel : '操作url',
			maxLength : 100,
			allowBlank : false
		}, {
			name : 'type',
			fieldLabel : '操作类型',
			allowBlank : false,
			value : '1',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : typeStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false
		}, {
			name : 'description',
			fieldLabel : '操作描述',
			maxLength : 50
		}, {
			name : 'display_order',
			fieldLabel : '排序',
			allowBlank : false,
			regex : /^\d+$/
		} ],
		buttons : [ {
			text : '保 存',
			iconCls : "save",
			glyph : glyphSave,
			handler : functionSave
		}, {
			text : '返回',
			iconCls : "close",
			glyph : glyphBack,
			handler : function() {
				functionWin.hide();
			}
		} ]
	});

	var functionWin = new Ext.Window({
		width : 400,
		height : 300,
		glyph : glyphWindow,
		title : '新增/修改操作功能',
		closeAction : 'hide',
		resizable : false,
		// shadow : true,
		modal : true,
		closeAction : 'hide',
		bodyStyle : 'padding:4 4 4 4',
		items : [ functionForm ]
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
		items : [ moduleTree, functionGrid ]
	})

	/** ****************事件操作************************************* */
	function add() {
		moduleForm.getForm().reset();
		moduleForm.getForm().findField("id").setValue("0");
		comboboxTree.setValue(1);
		moduleWin.setTitle("新增模块");
		moduleWin.show();
	}

	function update() {
		var record = moduleTree.getSelectionModel().getLastSelected();
		if (!record)
			return;
		moduleForm.getForm().loadRecord(record);
		moduleWin.setTitle("修改模块");
		moduleWin.show();
	}

	function moduleSave() {
		var url = "";
		if (moduleForm.getForm().findField("id").getValue() == '0')
			url = basePath + '/sys/module/add.do';
		else
			url = basePath + '/sys/module/update.do'
		if (moduleForm.getForm().isValid()) {
			moduleForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					moduleWin.hide();
					moduleStore.reload();
					comboboxTreeStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}
	/**
	 * 删除模块
	 */
	function del() {
		var record = moduleTree.getSelectionModel().getLastSelected();
		if (!record)
			return;
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/module/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.JSON.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								moduleStore.reload();
								comboboxTreeStore.reload();
							});
						} else {
							Ext.Msg.alert('操作提示', result.msgText);
						}
					}
				});
			}
		});
	}

	function addFunction() {
		var moduleRecord = moduleTree.getSelectionModel().getLastSelected();
		if (!moduleRecord)
			return;
		functionForm.getForm().reset();
		functionForm.getForm().findField("id").setValue("0");
		functionForm.getForm().findField("module_id").setValue(moduleRecord.data.id);
		functionWin.setTitle("新增功能");
		functionWin.show();
	}

	function updateFunction() {
		var record = functionGrid.getSelectionModel().getLastSelected();
		if (!record)
			return;
		functionForm.getForm().loadRecord(record);
		functionWin.setTitle("修改功能");
		functionWin.show();
	}

	function functionSave() {
		var url = "";
		if (functionForm.getForm().findField("id").getValue() == '0')
			url = basePath + '/sys/function/add.do';
		else
			url = basePath + '/sys/function/update.do'
		if (functionForm.getForm().isValid()) {
			functionForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					functionWin.hide();
					functionStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}
	/**
	 * 删除功能
	 */
	function delFunction() {
		var record = functionGrid.getSelectionModel().getLastSelected();
		if (!record)
			return;
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/function/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.JSON.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								functionStore.reload();
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
		// tree.getView().refresh();
		moduleTree.setWidth('100%');
	})
});