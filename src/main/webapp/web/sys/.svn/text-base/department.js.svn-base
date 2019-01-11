Ext.onReady(function() {
	Ext.define('SysDepartment', {
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
			name : 'principal',
			type : 'string'
		}, {
			name : 'description',
			type : 'string'
		}, {
			name : 'display_order',
			type : 'int'
		} ]
	});

	var store = Ext.create('Ext.data.TreeStore', {
		model : 'SysDepartment',
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/department/getTreeGrid.do'
		},
		root : {
			expanded : true
		}
	});
	var tree = Ext.create('Ext.tree.Panel', {
		title : '部门列表',
		frame : true,
		renderTo : Ext.getBody(),
		glyph : glyphGrid,
		useArrows : false,
		layout : 'fit',
		rootVisible : false,
		// columnLines: true,
		glyph : glyphTree,
		width : '100%',
		height : pageH,
		store : store,
		columns : [ {
			xtype : 'treecolumn',
			text : '部门名称',
			width : 200,
			sortable : false,
			dataIndex : 'name'
		}, {
			text : '部门代码',
			dataIndex : 'code',
			width : 100,
			sortable : false
		}, {
			header : '负责人',
			dataIndex : 'principal',
			width : 120,
			sort : false
		}, {
			header : '说明',
			dataIndex : 'description',
			width : 250,
			sort : false
		}, {
			header : '排序',
			dataIndex : 'display_order',
			flex : 1,
			sort : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		tbar : [ {
			text : '新增',
			glyph : glyphAdd,
			handler : add
		}, {
			text : '修改',
			glyph : glyphUpdate,
			handler : update
		}, {
			text : '删除',
			glyph : glyphDelete,
			handler : del
		}, '-', {
			text : '刷新',
			glyph : glyphRefresh,
			handler : function() {
				store.reload();
			}
		} ]
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
			url : basePath + '/common/getDepartmentComboboxTree.do'
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

	var departmentForm = new Ext.create("Ext.form.Panel", {
		id : 'departmentForm',
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
			msgTarget : 'qtip'
		},
		items : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'code',
			fieldLabel : '部门代码',
			maxLength : 20,
			maxLengthText : '不能超过20个字符',
			allowBlank : false
		}, {
			name : 'name',
			fieldLabel : '部门名称',
			maxLength : 20,
			maxLengthText : '不能超过20个字符',
			allowBlank : false
		}, comboboxTree, {
			name : 'principal',
			fieldLabel : '负责人'
		}, {
			name : 'description',
			fieldLabel : '说明'
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
			handler : departmentSave
		}, {
			text : '返回',
			iconCls : "close",
			glyph : glyphBack,
			handler : function() {
				departmentWin.hide();
			}
		} ]
	});

	var departmentWin = new Ext.Window({
		width : 400,
		height : 300,
		title : '新增/修改部门',
		glyph : glyphWindow,
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ departmentForm ]
	});
	function add() {
		departmentForm.getForm().reset();
		departmentForm.getForm().findField("id").setValue("0");
		comboboxTree.setValue(1);
		departmentWin.setTitle("新增部门");
		departmentWin.show();
	}

	function update() {
		// comboboxTreeStore.reload();
		var record = tree.getSelectionModel().getLastSelected();
		if (!record)
			return;
		departmentForm.getForm().loadRecord(record);
		departmentWin.setTitle("修改部门");
		departmentWin.show();
	}

	function departmentSave() {
		if (departmentForm.getForm().isValid()) {
			departmentForm.getForm().submit({
				clientValidation : true,
				url : basePath + '/sys/department/save.do',
				success : function(form, action) {
					departmentWin.hide();
					store.reload();
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
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				var record = tree.getSelectionModel().getLastSelected();
				if (!record)
					return;
				Ext.Ajax.request({
					url : basePath + '/sys/department/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								store.reload();
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
	// 自适应大小
	Ext.GlobalEvents.on('resize', function(width, height, eOpts) {
		// tree.getView().refresh();
		tree.setWidth('100%');
	})
});