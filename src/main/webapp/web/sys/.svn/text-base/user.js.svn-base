Ext.onReady(function() {
	var department_id = 0;// 定义选中的组织结构节点id
	var selUser_id;// 选中的user_id
	var addOrUpdate = 1;// 定义新增还是修改
	// 状态
	function renderStatus(val) {
		if (val == 1)
			return '<span style="color:' + "green" + ';">' + '正常' + '</span>';
		else if (val == 2)
			return '<span style="color:' + "orange" + ';">' + '锁定' + '</span>';
	}
	// 类型
	function renderUserlevel(val) {
		if (val == 1)
			return '总公司';
		else if (val == 2)
			return '路局';
		else if (val == 3)
			return '客运段';
		else if (val == 4)
			return '车辆段';
		else if (val == 5)
			return '动车段';
		else if (val == 6)
			return '车站';
	}
	// 状态store
	var statusStore = Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			"value" : "1",
			"content" : "正常"
		}, {
			"value" : "2",
			"content" : "锁定"
		} ]
	});

	var roleUrl = "";
	if (user_name == 'admin')
		roleUrl = basePath + '/common/listRoleCombobox.do?is_admin=1';
	else
		roleUrl = basePath + '/common/listRoleCombobox.do?is_admin=0';
	// 角色store
	var roleSelectStore = Ext.create('Ext.data.JsonStore', {
		model : 'ComboboxData',
		// fields : [ 'value', 'content' ],
		proxy : {
			type : 'ajax',
			url : roleUrl,
			reader : {
				type : 'json'
			}
		},
		autoLoad : true
	});
	// 部门下拉树store，新增用到
	var userlevelStore = Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			"value" : "1",
			"content" : "总公司"
		}, {
			"value" : "2",
			"content" : "路局"
		}, {
			"value" : "3",
			"content" : "客运段"
		}, {
			"value" : "4",
			"content" : "车辆段"
		}, {
			"value" : "5",
			"content" : "动车段"
		}, {
			"value" : "6",
			"content" : "车站"
		} ]
	});
	Ext.define('SysUser', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'realname',
			type : 'string'
		}, {
			name : 'username',
			type : 'string'
		}, {
			name : 'status',
			type : 'int'
		}, {
			name : 'realname',
			type : 'string'
		}, {
			name : 'gender',
			type : 'string'
		}, {
			name : 'mobile',
			type : 'string'
		}, {
			name : 'phone',
			type : 'string'
		}, {
			name : 'user_id',
			type : 'int'
		}, {
			name : 'create_date',
			type : 'string'
		}, {
			name : 'dwid',
			type : 'int'
		}, {
			name : 'userRoleStr',
			type : 'string'
		} ]
	});

	var userStore = Ext.create('Ext.data.Store', {
		model : 'SysUser',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/user/search.do',
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
					username : Ext.getCmp('txtUsername').getValue(),
					realname : Ext.getCmp('txtRealname').getValue(),
					status : Ext.getCmp('comboStatus').getValue(),
					userlevel : Ext.getCmp('comboUserlevel').getValue()
				});
			}
		}
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});

	var userToolbar1 = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : []
	});

	var userToolbar2 = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ {
			text : '新增',
			hide : hideSysUserAdd,
			glyph : glyphAdd,
			handler : addUser
		}, '-', '账号:', {
			xtype : 'textfield',
			width : 120,
			id : 'txtUsername'
		}, '真实姓名:', {
			xtype : 'textfield',
			width : 80,
			id : 'txtRealname'
		}, '状态:', {
			id : 'comboStatus',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : statusStore,
			queryMode : 'local',
			width : 100,
			typeAhead : true,
			editable : true
		}, '用户级别:', {
			id : 'comboUserlevel',
			xtype : 'combobox',
			valueField : 'value',
			displayField : 'content',
			store : userlevelStore,
			queryMode : 'local',
			width : 100,
			typeAhead : true,
			editable : true
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '查询',
			handler : function() {
				searchUser();
			}
		} ]
	});

	var userGrid = Ext.create('Ext.grid.Panel', {
		title : '用户列表1',
		frame : true,
		glyph : glyphGrid,
		width : '100%',
		height : pageH,
		// columnLines : true,
		collapsible : true,// 表头缩回按钮
		store : userStore,
		selModel : selModel,
		renderTo : Ext.getBody(),
		columns : [ {
			xtype : 'rownumberer',
			width : 40
		}, {
			text : '登录账号',
			dataIndex : 'username',
			width : 100,
			sortable : false
		}, {
			text : '真实姓名',
			dataIndex : 'realname',
			width : 80,
			sortable : false
		}, {
			text : '手机号',
			dataIndex : 'mobile',
			width : 100,
			sortable : false
		}, {
			text : '座机',
			dataIndex : 'phone',
			width : 100,
			sortable : false
		}, {
			text : '所在单位',
			dataIndex : 'dwmc',
			width : 120,
			sortable : false
		}, {
			text : '状态',
			dataIndex : 'status',
			width : 60,
			align : 'center',
			sortable : false,
			renderer : renderStatus
		}, {
			text : '角色',
			dataIndex : 'userRoleStr',
			width : 100,
			sortable : false
		}, {
			text : '创建时间',
			dataIndex : 'create_date',
			width : 100,
			sortable : false
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '密码重置',
			width : 80,
			align : 'left',
			items : [ {
				text : '密码重置',
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					resetPass(grid.getStore().getAt(rowIndex))
				}
			} ]
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '锁定/解锁',
			width : 80,
			align : 'left',
			items : [ {
				text : '锁定',
				hidden : hideSysUserLock,
				getClass : function(v, meta, record) {
					if (record.data.status == 1)
						return '';
					else
						return 'x-hidden';
				},
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					lockUser(grid.getStore().getAt(rowIndex))
				}
			}, {
				text : '解锁',
				hidden : hideSysUserLock,
				getClass : function(v, meta, record) {
					if (record.data.status == 2)
						return '';
					else
						return 'x-hidden';
				},
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					lockUser(grid.getStore().getAt(rowIndex))
				}
			} ]
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '操作',
			width : 240,
			align : 'left',
			items : [ {
				text : '修改',
				hide : hideSysUserUpdate,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					updateUser(grid.getStore().getAt(rowIndex));
				}
			}, {
				text : '删除',
				hide : hideSysUserDelete,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					deleteUser(grid.getStore().getAt(rowIndex))
				}
			}, {
				text : '查看',
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					viewUser(grid.getStore().getAt(rowIndex))
				}
			}, {
				text : '查看密码',
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					var rec = grid.getStore().getAt(rowIndex);
					if (rec.data.username != 'admin')
						Ext.Msg.alert('查看密码', "该用户密码为【" + rec.data.password + "】");
					else
						Ext.Msg.alert('操作密码', "管理员密码不允许查看");
				}
			}, {
				text : '登录历史',
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					viewLogin(grid.getStore().getAt(rowIndex))
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
			items : [ userToolbar1, userToolbar2 ]
		},
		bbar : Ext.create('Ext.PagingToolbar', {
			store : userStore,
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
		fieldLabel : '所在单位',
		name : 'dwid',
		displayField : 'text',
		emptyText : '请选择...',
		allowBlank : false,
		forceSelection : true,
		columnWidth : 1,
		maxPickerHeight : 250,
		store : comboboxTreeStore,
		value : 0
	});
	var userForm = new Ext.create("Ext.form.Panel", {
		id : 'userForm',
		frame : true,
		border : true,
		width : '100%',
		height : 450,
		bodyPadding : 4,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 90,
			anchor : '100%',
			margin : '0 0 5 0',
			msgTarget : 'qtip',
			columnWidth : 0.5
		},
		items : [ {
			xtype : 'fieldset',
			layout : 'column',
			border : true,
			title : '登录信息',
			autoHeight : false,
			defaultType : 'textfield',
			items : [ {
				name : 'id',
				hidden : true
			}, {
				name : 'username',
				fieldLabel : '账号',
				allowBlank : false
			} ]
		}, {
			xtype : 'fieldset',
			layout : 'column',
			border : true,
			title : '基本信息',
			autoHeight : false,
			defaultType : 'textfield',
			items : [ {
				name : 'realname',
				fieldLabel : '真实姓名',
				maxLength : 20,
				maxLengthText : '不能超过20个字符',
				allowBlank : false
			}, {
				xtype : 'radiogroup',
				fieldLabel : '性别',
				layout : {
					autoFlex : false
				},
				defaults : {
					name : 'gender',
					margin : '0 15 0 0'
				},
				items : [ {
					inputValue : '男',
					boxLabel : '男',
					checked : true
				}, {
					inputValue : '女',
					boxLabel : '女'
				} ]
			}, {
				name : 'mobile',
				fieldLabel : '手机号',
				vtype : 'mobile'
			}, {
				name : 'phone',
				fieldLabel : '座机',
				maxLength : 20,
				maxLengthText : '不能超过20个字符',
				allowBlank : true
			}, comboboxDwTree ]
		}, {
			xtype : 'fieldset',
			layout : 'column',
			border : true,
			title : '角色信息',
			autoHeight : false,
			defaultType : 'textfield',
			items : [ {
				name : 'role_id',
				fieldLabel : '角色',
				allowBlank : false,
				xtype : 'combobox',
				valueField : 'value',
				displayField : 'content',
				store : roleSelectStore,
				width : 300,
				multiSelect : false,
				queryMode : 'local',
				typeAhead : true,
				editable : false
			} ]
		} ],
		buttons : [ {
			text : '保存',
			glyph : glyphSave,
			handler : saveUser
		}, {
			text : '返回',
			glyph : glyphBack,
			handler : function() {
				userWin.hide();
			}
		} ]
	});

	var userWin = new Ext.Window({
		width : 600,
		height : 500,
		glyph : glyphWindow,
		title : '新增/修改用户',
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ userForm ]
	});

	var userViewForm = new Ext.create("Ext.form.Panel", {
		id : 'userViewForm',
		width : '100%',
		height : 450,
		bodyPadding : 4,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 90,
			anchor : '100%',
			margin : '0 0 5 0',
			msgTarget : 'qtip',
			columnWidth : 0.5
		},
		items : [ {
			xtype : 'fieldset',
			layout : 'column',
			border : true,
			title : '登录信息',
			autoHeight : false,
			defaultType : 'textfield',
			items : [ {
				name : 'id',
				hidden : true
			}, {
				name : 'username',
				fieldLabel : '账号',
				allowBlank : false
			} ]
		}, {
			xtype : 'fieldset',
			layout : 'column',
			border : true,
			title : '基本信息',
			autoHeight : false,
			defaultType : 'textfield',
			items : [ {
				name : 'realname',
				fieldLabel : '真实姓名',
				maxLength : 20,
				maxLengthText : '不能超过20个字符',
				allowBlank : false
			}, {
				xtype : 'radiogroup',
				fieldLabel : '性别',
				layout : {
					autoFlex : false
				},
				defaults : {
					name : 'gender',
					margin : '0 15 0 0'
				},
				items : [ {
					inputValue : '男',
					boxLabel : '男',
					checked : true
				}, {
					inputValue : '女',
					boxLabel : '女'
				} ]
			}, {
				name : 'mobile',
				fieldLabel : '手机号',
				vtype : 'mobile'
			}, {
				name : 'phone',
				fieldLabel : '座机',
				maxLength : 20,
				maxLengthText : '不能超过20个字符',
				allowBlank : true
			}, Ext.create('Ext.ux.TreePicker', {
				fieldLabel : '所在单位',
				name : 'dwid',
				displayField : 'text',
				emptyText : '请选择...',
				forceSelection : true,
				store : comboboxTreeStore
			}) ]
		}, {
			xtype : 'fieldset',
			layout : 'column',
			border : true,
			title : '角色信息',
			autoHeight : false,
			defaultType : 'textfield',
			items : [ {
				name : 'role_id',
				fieldLabel : '角色',
				allowBlank : false,
				xtype : 'combobox',
				valueField : 'value',
				displayField : 'content',
				store : roleSelectStore,
				width : 300,
				multiSelect : false,
				queryMode : 'local',
				typeAhead : true,
				editable : false
			} ]
		} ],
		buttons : [ {
			text : '返回',
			glyph : glyphBack,
			handler : function() {
				userViewWin.hide();
			}
		} ]
	});
	var userViewWin = new Ext.Window({
		width : 600,
		height : 500,
		glyph : glyphWindow,
		title : '『查看用户信息』',
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ userViewForm ]
	});
	searchUser();

	Ext.define('SysUserLogin', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'login_date',
			type : 'string'
		}, {
			name : 'login_ip',
			type : 'string'
		}, {
			name : 'terminal',
			type : 'string'
		}, {
			name : 'explorerType',
			type : 'string'
		}, {
			name : 'explorerVersion',
			type : 'string'
		} ]
	});

	var userLoginStore = Ext.create('Ext.data.Store', {
		model : 'SysUserLogin',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/user/searchUserLogin.do',
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
					id : selUser_id
				});
			}
		}
	});

	var userLoginGrid = Ext.create('Ext.grid.Panel', {
		width : '100%',
		height : 450,
		border : true,
		frame : true,
		store : userLoginStore,
		columns : [ {
			xtype : 'rownumberer',
			width : 40
		}, {
			text : '登录时间',
			dataIndex : 'login_date',
			width : 160,
			sortable : false
		}, {
			text : 'IP',
			dataIndex : 'login_ip',
			width : 140,
			sortable : false
		}, {
			text : '终端',
			dataIndex : 'terminal',
			width : 100,
			sortable : false
		}, {
			text : '浏览器类型',
			dataIndex : 'explorerType',
			width : 100,
			sortable : false
		}, {
			text : '浏览器版本号',
			dataIndex : 'explorerVersion',
			flex : 1,
			sortable : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		bbar : Ext.create('Ext.PagingToolbar', {
			store : userLoginStore,
			displayInfo : true
		})
	});
	var userLoginWin = new Ext.Window({
		width : 800,
		height : 500,
		glyph : glyphWindow,
		title : '『查看登录历史』',
		modal : true,
		closeAction : 'hide',
		items : [ userLoginGrid ]
	});

	/** *******************按钮方法******************************* */
	// 用户查询
	function searchUser() {
		userStore.loadPage(1);
	}
	function addUser() {
		userForm.getForm().reset();
		userForm.getForm().findField("id").setValue("0");
		userWin.setTitle("『新增用户』");
		setFieldReadOnly(userForm, 'username', false);
		comboboxTreeStore.load();
		userWin.show();
		addOrUpdate = 1;
	}

	function updateUser(record) {
		userForm.getForm().loadRecord(record);
		userWin.setTitle("『修改用户』");
		setFieldReadOnly(userForm, 'username', true);
		comboboxTreeStore.load();
		userWin.show();
		addOrUpdate = 2;
	}

	function saveUser() {
		var url = "";
		if (addOrUpdate == 1)
			url = basePath + '/sys/user/add.do';
		else
			url = basePath + '/sys/user/update.do';
		if (userForm.getForm().isValid()) {
			userForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					userWin.hide();
					userStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}
	/**
	 * 删除用户
	 */
	function deleteUser(record) {
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/user/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								userStore.reload();
							});
						} else {
							Ext.Msg.alert('操作提示', result.msgText);
						}
					}
				});
			}
		});
	}

	// 查看
	function viewUser(record) {
		userViewForm.getForm().loadRecord(record);
		setFormReadOnly(userViewForm, true);
		userViewWin.show();
	}

	// 重置密码
	function resetPass(record) {
		Ext.Msg.confirm('确认重置', '你确定重置选中用户密码吗?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/user/resetPass.do',
					params : {
						id : record.data.id
					}, // 请求参数
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '重置成功,重置密码为123456');
						} else {
							Ext.Msg.alert('操作提示', result.msgText);
						}
					}
				});
			}
		});
	}

	// 锁定用户
	function lockUser(record) {
		var status = record.data.status;
		var confirmText = "";
		if (status == 1) {
			confirmText = '你确认加锁用户吗？';
			status = 2;
		} else if (status == 2) {
			confirmText = '你确认解锁用户吗？';
			status = 1;
		}
		Ext.Msg.confirm('确认', confirmText, function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/user/lockUser.do',
					params : {
						id : record.data.id,
						status : status
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '操作成功', function() {
								userStore.reload();
							});
						} else {
							Ext.Msg.alert('操作提示', result.msgText);
						}
					}
				});
			}
		});
	}

	// 查看登录历史
	function viewLogin(record) {
		selUser_id = record.data.id;
		userLoginStore.load()
		userLoginWin.show();
	}

	// 自适应大小
	Ext.GlobalEvents.on('resize', function() {
		userGrid.getView().refresh();
		userGrid.setWidth('100%')
	})
});