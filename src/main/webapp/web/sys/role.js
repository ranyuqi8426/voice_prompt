Ext.onReady(function() {

	// 是否管理
	function renderIs_admin(val) {
		if (val == 0)
			return '<span style="color:' + "blue" + ';">' + '否' + '</span>';
		else if (val == 1)
			return '<span style="color:' + "red" + ';">' + '是' + '</span>';
	}
	// 是否管理store
	var is_adminStore = Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			"value" : "0",
			"content" : "否"
		}, {
			"value" : "1",
			"content" : "是"
		} ]
	});
	// 把按钮转换成checkbox
	function renderButtonCheck(val) {
		if (val == '')
			return '';
		var checkArr = new Array();
		checkArr = val.split(",");
		var ret = "";
		for (var i = 0; i < checkArr.length; i++) {
			var butArr = new Array();
			butArr = checkArr[i].split("@");
			ret += "<input type='checkbox' name='chkButton' value='" + butArr[0] + "' id='chkButton" + butArr[0]
					+ "' style='vertical-align:middle; margin-top:-2px; margin-bottom:-2px;'";
			if (butArr[2] == '1') {
				ret += " checked";
			}
			ret += " />" + butArr[1]
		}
		return ret;
	}

	Ext.define('SysRole', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'code',
			type : 'string'
		}, {
			name : 'name',
			type : 'string'
		}, {
			name : 'default_module',
			type : 'string'
		}, {
			name : 'is_admin',
			type : 'int'
		}, {
			name : 'description',
			type : 'string'
		}, {
			name : 'display_order',
			type : 'int'
		} ]
	});

	var roleStore = Ext.create('Ext.data.Store', {
		model : 'SysRole',
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/role/listJson.do'
		},
		autoLoad : true
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});
	var roleGrid = Ext.create('Ext.grid.Panel', {
		title : '角色列表',
		frame : true,
		glyph : glyphGrid,
		// width : 600,
		columnWidth : 0.4,
		margin : '0 2 0 0',
		height : pageH,
		// columnLines : true,
		collapsible : false,
		store : roleStore,
		selModel : selModel,
		columns : [ {
			xtype : 'rownumberer'
		}, {
			text : '角色名称',
			dataIndex : 'name',
			width : 100,
			sortable : false
		}, {
			text : '是否管理',
			dataIndex : 'is_admin',
			width : 80,
			sortable : false,
			renderer : renderIs_admin
		}, {
			text : '默认模块',
			dataIndex : 'default_module',
			width : 80,
			sortable : false
		}, {
			text : '角色描述',
			dataIndex : 'description',
			width : 120,
			sortable : false
		}, {
			text : '排序',
			dataIndex : 'display_order',
			width : 60,
			sortable : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		tbar : [ {
			text : '新增',
			hidden : hideSysRoleAdd,
			glyph : glyphAdd,
			handler : add
		}, {
			text : '修改',
			hidden : hideSysRoleUpdate,
			glyph : glyphUpdate,
			handler : update
		}, {
			text : '删除',
			hidden : hideSysRoleDelete,
			glyph : glyphDelete,
			handler : del
		}, '-', {
			text : '刷新',
			glyph : glyphRefresh,
			handler : function() {
				roleStore.reload();
			}
		} ]
	});

	// 单击行事件
	roleGrid.on('select', function(grid, record, rowIndex, e) {
		if (record.data.id == null)
			return;
		moduleStore.load({
			params : {
				role_id : record.data.id
			}
		})
	});

	var roleForm = new Ext.create("Ext.form.Panel", {
		id : 'roleForm',
		frame : true,
		border : true,
		width : '100%',
		height : 300,
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
			name : 'name',
			fieldLabel : '角色名称',
			maxLength : 20,
			allowBlank : false
		}, {
			name : 'is_admin',
			fieldLabel : '是否管理',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : is_adminStore,
			queryMode : 'local',
			typeAhead : true,
			editable : false,
			allowBlank : false
		}, {
			name : 'default_module',
			fieldLabel : '默认模块',
			allowBlank : true
		}, {
			name : 'description',
			fieldLabel : '角色描述',
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
			handler : roleSave
		}, {
			text : '返回',
			iconCls : "close",
			glyph : glyphBack,
			handler : function() {
				roleWin.hide();
			}
		} ]
	});

	var roleWin = new Ext.Window({
		width : 400,
		height : 340,
		glyph : glyphWindow,
		title : '新增/修改角色',
		closeAction : 'hide',
		resizable : false,
		// shadow : true,
		modal : true,
		closeAction : 'hide',
		bodyStyle : 'padding:4 4 4 4',
		items : [ roleForm ]
	});

	// 定义角色对应权限
	var moduleStore = Ext.create('Ext.data.TreeStore', {
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/role/listRoleModuleJson.do'
		},
		autoLoad : true
	});
	var moduleTree = Ext.create('Ext.tree.Panel', {
		store : moduleStore,
		margin : '0 2 0 2',
		rootVisible : false,
		useArrows : false,
		frame : true,
		bufferedRenderer : false,
		title : '角色权限设置',
		// width : 400,
		columnWidth : 0.6,
		columnLines : true,
		rowLines : true,
		collapsible : false,
		glyph : glyphTree,
		height : pageH,
		columns : [ {
			xtype : 'treecolumn',
			text : '模块名称',
			width : 200,
			sortable : false,
			dataIndex : 'text'
		}, {
			text : '功能列表',
			dataIndex : 'function',
			flex : 1,
			sortable : false,
			renderer : renderButtonCheck
		} ],
		tbar : [ {
			glyph : glyphSave,
			text : '保存角色权限',
			handler : saveRoleModlue
		}, {
			glyph : 61765,
			text : '功能全选',
			handler : selectAll
		} ]
	});

	moduleTree.on('checkchange', function(node, checked) {
		checkBoxTreeCheck(node, checked);
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
		items : [ roleGrid, moduleTree ]
	})

	/** ***********************功能操作************************************ */

	function add() {
		roleForm.getForm().reset();
		roleForm.getForm().findField("id").setValue("0");
		roleWin.setTitle("新增角色");
		roleWin.show();
	}

	function update() {
		var record = roleGrid.getSelectionModel().getLastSelected();
		if (!record)
			return;
		roleForm.getForm().loadRecord(record);
		roleWin.setTitle("修改角色");
		roleWin.show();
	}

	function roleSave() {
		var url = "";
		if (roleForm.getForm().findField("id").getValue() == 0)
			url = basePath + '/sys/role/add.do';
		else
			url = basePath + '/sys/role/update.do';
		roleForm.getForm().submit({
			clientValidation : true,
			url : url,
			success : function(form, action) {
				roleWin.hide();
				roleStore.reload();
			},
			failure : function(form, action) {
				Ext.Msg.alert('操作提示', action.result.msgText)
			}
		});
	}
	/**
	 * 删除角色
	 */
	function del() {
		var record = roleGrid.getSelectionModel().getLastSelected();
		if (!record)
			return;
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();

				Ext.Ajax.request({
					url : basePath + '/sys/role/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								roleStore.reload();
							});
						} else {
							Ext.Msg.alert('操作提示', result.msgText);
						}
					}
				});
			}
		});
	}
	/**
	 * 保存角色权限
	 */
	function saveRoleModlue() {
		var roleRecord = roleGrid.getSelectionModel().getLastSelected();
		if (!roleRecord)
			return;
		var records = moduleTree.getView().getChecked(), names = [];
		// 为存储ID而创建数组
		ids = [];
		functionId = [];
		Ext.Array.each(records, function(rec) {
			names.push(rec.get('text'));
			ids.push(rec.get('id'));
			// 将选中的节点的ID保存
		});
		var chkButtonArr = document.getElementsByName('chkButton');
		for (var i = 0; i < chkButtonArr.length; i++) {
			if (chkButtonArr[i].checked)
				functionId.push(chkButtonArr[i].value)
		}

		Ext.Ajax.request({
			url : basePath + '/sys/role/updateRoleModule.do',
			params : {
				moduleList : ids.join('@@'),
				role_id : roleRecord.data.id,
				functionList : functionId.join("@@")
			},
			success : function(response) {
				unMask();
				var result = Ext.decode(response.responseText);
				if (result.success) {
					Ext.Msg.alert('操作提示', '权限设置成功');
				} else {
					Ext.Msg.alert('操作提示', result.msgText);
				}
			}
		});
	}
	/**
	 * 功能权限
	 */
	function selectAll() {
		var chkButtonArr = document.getElementsByName('chkButton');
		for (var i = 0; i < chkButtonArr.length; i++) {
			chkButtonArr[i].checked = true;
		}
	}
	// 自适应大小
	Ext.GlobalEvents.on('resize', function() {
		roleGrid.setWidth('100%');
	})
});