/**
 * 通知公告
 * 
 * @author 崔明超
 */
Ext.onReady(function() {

	var addOrUpdate = 0;

	// 列表实体
	Ext.define('SysTzgg', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'int'
		}, {
			name : 'bt',
			type : 'string'
		}, {
			name : 'nr',
			type : 'string'
		}, {
			name : 'fbr',
			type : 'string'
		}, {
			name : 'fbsj',
			type : 'string'
		}, {
			name : 'px',
			type : 'int'
		}, {
			name : 'wjlj',
			type : 'string'
		} ]
	});

	// 基本信息查询
	var sysTzggStore = Ext.create('Ext.data.Store', {
		model : 'SysTzgg',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/tzgg/search.do',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				rootProperty : 'root'
			}
		},
		autoLoad : false,
		listeners : {
			beforeload : function(store) {
				Ext.apply(store.proxy.extraParams, {
					bt : Ext.getCmp('txtBt').getValue()
				});
			}
		}
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});

	var sysTzggToolbar = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ {
			text : '新增',
			glyph : glyphAdd,
			hidden : hideSysTzggAdd,
			handler : addSysTzgg
		}, '-', '标题:', {
			xtype : 'textfield',
			width : 120,
			id : 'txtBt'
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '查询',
			handler : function() {
				searchSysTzgg();
			}
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '清空',
			handler : function() {
				clearSysTzgg();
			}
		} ]
	});

	// 显示列表
	var sysTzggGrid = Ext.create('Ext.grid.Panel', {
		title : '通知公告',
		frame : true,
		glyph : glyphGrid,
		width : '100%',
		height : pageH,
		// columnLines : true,
		collapsible : true,// 表头缩回按钮
		store : sysTzggStore,
		selModel : selModel,
		renderTo : Ext.getBody(),
		columns : [ {
			xtype : 'rownumberer',
			width : 40,
			align : 'center'
		}, {
			dataIndex : 'id',
			hidden : true
		}, {
			header : '标题',
			dataIndex : 'bt',
			width : 120,
			align : 'center'
		}, {
			header : '内容',
			dataIndex : 'nr',
			width : 240,
			align : 'center',
			renderer : function(value) {
				return '<div align="left">' + value + '</div>';
			}
		}, {
			header : '发布人',
			width : 120,
			dataIndex : 'fbr',
			align : 'center'
		}, {
			header : '发布时间',
			dataIndex : 'fbsj',
			width : 100,
			align : 'center'
		}, {
			header : '排序',
			dataIndex : 'px',
			width : 100,
			align : 'center',
			renderer : function(value) {
				return '<div align="right">' + value + '</div>';
			}
		}, {
			header : '文件路径',
			dataIndex : 'wjlj',
			width : 100,
			align : 'center',
			hidden:true
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '文件路径',
			align : 'center',
			items : [ { 
				text:'下载',
				getClass : function(v, meta, record) {
					if (record.data.wjlj == ''|| record.data.wjlj == null)
						return 'x-hidden';
				},
				handler : function(grid, rowIndex, colIndex) {
					location.href = basePath + '/common/downloadFromSource.do?source_code='+ grid.getStore().getAt(rowIndex).data.wjlj;
				}
			} ]
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '操作',
			align : 'center',
			items : [ {
				text : '修改',
				hidden : hideSysTzggUpdate,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					updateSysTzgg(grid.getStore().getAt(rowIndex));
				}
			}, {
				text : '删除',
				hidden : hideSysTzggDelete,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					delSysTzgg(grid.getStore().getAt(rowIndex));
				}
			} ]
		} ],
		viewConfig : {
			stripeRows : true,
			enableTextSelection : true
		},
		tbar : sysTzggToolbar,
		bbar : Ext.create('Ext.PagingToolbar', {
			store : sysTzggStore,
			displayInfo : true
		})
	});

	// 新增列表
	var sysTzggForm = Ext.create('Ext.form.Panel', {
		id : 'sysTzggForm',
		frame : true,
		border : true,
		width : '100%',
		height : 300,
		bodyPadding : 4,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 90,
			anchor : '100%',
			margin : '0 0 5 0',
			msgTarget : 'qtip'
		},
		defaultType : 'textfield',
		items : [ {
			name : 'id',
			fieldLabel : 'id',
			hidden : true
		}, {
			name : 'bt',
			fieldLabel : '标题',
			maxlength : 20,
			allowBlank : false
		}, {
			name : 'nr',
			xtype : 'textarea',
			fieldLabel : '内容',
			maxlength : 512,
			height : 100,
			allowBlank : false
		},{
			name : 'fbr',
			fieldLabel : '发布人',
			maxlength : 10,
			allowBlank : false,
			readOnly:true,
			value : username
		}, {
			name : 'fbsj',
			fieldLabel : '发布时间',
			maxlength : 10,
			allowBlank : true,
			xtype : 'datefield',
			editable : false,
			format : 'Y-m-d',
			maxValue : Ext.Date.format(new Date(), 'Y-m-d'),
			enabled : true,
			readOnly:true,
			value:new Date()
		}, {
			name : 'px',
			fieldLabel : '排序',
			maxlength : 6,
			allowBlank : false
		}, {
			xtype : 'container',
			layout : 'hbox',
			defaultType : 'textfield',
			items : [ {
				name : 'wjlj',
				fieldLabel : '附件',
				readOnly : true,
				maxlength : 50,
				width:400,
				allowBlank : false
			}, {
				xtype : 'button',
				id : 'btnSelWjlj',
				text : '选择文件',
				handler : function() {
					showEsFileUpload('sysTzggForm', 'tz','wjlj');
				}
			} ]
		} ],
		buttons : [ {
			text : '保存',
			iconCls : 'save',
			glyph : glyphSave,
			handler : saveSysTzgg
		}, {
			text : '返回',
			iconCls : 'close',
			glyph : glyphBack,
			handler : function() {
				sysTzggWin.hide();
			}
		} ]
	});
	var sysTzggWin = new Ext.Window({
		width : 600,
		height : 350,
		glyph : glyphWindow,
		title : '新增/修改通知公告',
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ sysTzggForm ]
	});

	searchSysTzgg();

	/** *******************按钮方法******************************* */
	// 基本信息查询
	function searchSysTzgg() {
		sysTzggStore.loadPage(1);
	}

	// 清空
	function clearSysTzgg() {
		Ext.getCmp('txtBt').setValue('');
	}
	// 基本信息新增
	function addSysTzgg() {
		sysTzggForm.getForm().reset();
		sysTzggForm.getForm().findField('id').setValue('0');
		sysTzggWin.show();
		addOrUpdate = 1;
	}
	// 基本信息修改
	function updateSysTzgg(record) {
		sysTzggForm.getForm().loadRecord(record);
		sysTzggWin.show();
		addOrUpdate = 2;
	}
	// 基本信息保存
	function saveSysTzgg() {
		var url = '';
		if (addOrUpdate == 1)
			url = basePath + '/sys/tzgg/add.do';
		else
			url = basePath + '/sys/tzgg/update.do';
		if (sysTzggForm.getForm().isValid()) {
			sysTzggForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					Ext.Msg.alert('操作提示', '保存成功', function() {
						sysTzggWin.hide();
						sysTzggStore.reload();
					});
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}
	// 基本信息删除
	function delSysTzgg(record) {
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/tzgg/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								sysTzggStore.reload();
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
	Ext.GlobalEvents.on('resize', function() {
		sysTzggGrid.getView().refresh();
		sysTzggGrid.setWidth('100%')
	})
});