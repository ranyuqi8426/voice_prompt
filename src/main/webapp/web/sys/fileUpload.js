/**
 * 通知公告
 * 
 * @author   贺新明
 */
Ext.onReady(function() {
	var addOrUpdate = 0;
	Ext.define('SysFileUpload', {
		extend : 'Ext.data.Model',
		fields : [  {
			name : 'id',
			type : 'int'
		},{
			name : 'wjm',
			type : 'string'
		}, {
			name : 'wjlx',
			type : 'string'
		}, {
            name:'scr',
            type:'string'
		} , {
            name:'scsj',
            type:'string'
		} ]
	});
	// 基本信息查询
	var sysFileStore = Ext.create('Ext.data.Store', {
		model : 'SysFileUpload',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/fileUpload/search.do',
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
					wjm : Ext.getCmp('txtWjm').getValue(),
					scr : Ext.getCmp('txtScr').getValue()
				});
			}
		}
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});

	var sysFileToolbar = Ext.create('Ext.toolbar.Toolbar', {
		layout : {
			overflowHandler : 'Menu'
		},
		items : [ {
			text : '新增',
			glyph : glyphAdd,
			hidden : hideSysFileAdd,
			handler : addSysFile
		}, '-', '文件名:', {
			xtype : 'textfield',
			width : 120,
			id : 'txtWjm'
		},'-', '上传人:', {
			xtype : 'textfield',
			width : 120,
			id : 'txtScr'
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '查询',
			handler : function() {
				searchSysFile();
			}
		}, {
			xtype : 'button',
			glyph : glyphSearch,
			text : '清空',
			handler : function() {
				clearSysFile();
			}
		} ]
	});

	// 显示列表
	var sysFileGrid = Ext.create('Ext.grid.Panel', {
		title : '通知公告',
		frame : true,
		glyph : glyphGrid,
		width : '100%',
		height : pageH,
		// columnLines : true,
		collapsible : true,// 表头缩回按钮
		store : sysFileStore,
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
			header : '文件名',
			dataIndex : 'wjm',
			width : 260,
			align : 'center',
			renderer : function(value) {
				return '<div align="left">' + value + '</div>';
			}
		}, {
			header : '上传人',
			dataIndex : 'scr',
			width : 100,
			align : 'center',
			hidden:false
		}, {
			header : '上传时间',
			dataIndex : 'scsj',
			width : 100,
			align : 'center'
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
					var fileName = encodeURI(encodeURI(grid.getStore().getAt(rowIndex).data.wjm));
					location.href = basePath + '/common/downloadFromSource.do?source_code='+ grid.getStore().getAt(rowIndex).data.wjlj+'&source_name='+fileName;
				}
			}  ]
		}, {
			xtype : 'actioncolumn',
			width : 120,
			text : '操作',
			align : 'center',
			items : [ {
				text : '修改',
				hidden : hideSysFileUpdate,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					updateSysFile(grid.getStore().getAt(rowIndex));
				}
			}, {
				text : '删除',
				hidden : hideSysFileDelete,
				handler : function(grid, rowIndex, colIndex) {
					setGridSelect(grid, rowIndex);
					delSysFile(grid.getStore().getAt(rowIndex));
				}
			} ]
		} ],
		viewConfig : {
			stripeRows : true,
			enableTextSelection : true
		},
		tbar : sysFileToolbar,
		bbar : Ext.create('Ext.PagingToolbar', {
			store : sysFileStore,
			displayInfo : true
		})
	});

	// 新增列表
	var sysFileForm = Ext.create('Ext.form.Panel', {
		id : 'sysFileForm',
		frame : true,
		border : true,
		width : '100%',
		height:200,
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
			name : 'scr',
			fieldLabel : '上传人',
			maxlength : 10,
			allowBlank : false
		}, {
			name : 'scsj',
			fieldLabel : '上传时间',
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
			xtype : 'container',
			layout : 'hbox',
			defaultType : 'textfield',
			items : [ {
				name : 'wjlj',
				fieldLabel : '文件路径',
				readOnly : true,
				maxlength : 50,
				width:400,
				allowBlank : false
			}, {
				xtype : 'button',
				id : 'btnSelWjlj',
				text : '选择文件',
				handler : function() {
					showEsFileUpload('sysFileForm','', 'wjlj','wjm');
				}
			},{
				name : 'wjm',
				fieldLabel : '文件名',
				hidden:true
			} ]
		} ],
		buttons : [ {
			text : '保存',
			iconCls : 'save',
			glyph : glyphSave,
			handler : saveSysFile
		}, {
			text : '返回',
			iconCls : 'close',
			glyph : glyphBack,
			handler : function() {
				sysFileWin.hide();
			}
		} ]
	});
	var sysFileWin = new Ext.Window({
		width : 500,
		height : 250,
		glyph : glyphWindow,
		title : '新增/修改文件上传',
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ sysFileForm ]
	});

	searchSysFile();

	/** *******************按钮方法******************************* */
	// 基本信息查询
	function searchSysFile() {
		sysFileStore.loadPage(1);
	}

	// 清空
	function clearSysFile() {
		Ext.getCmp('txtWjm').setValue('');
		Ext.getCmp('txtScr').setValue('');

	}
	// 基本信息新增
	function addSysFile() {
		sysFileForm.getForm().reset();
		sysFileForm.getForm().findField('id').setValue('0');
		sysFileWin.show();
		addOrUpdate = 1;
	}
	// 基本信息修改
	function updateSysFile(record) {
		sysFileForm.getForm().loadRecord(record);
		sysFileWin.show();
		addOrUpdate = 2;
	}
	// 基本信息保存
	function saveSysFile() {
		var url = '';
		if (addOrUpdate == 1)
			url = basePath + '/sys/fileUpload/add.do';
		else
			url = basePath + '/sys/fileUpload/update.do';
		if (sysFileForm.getForm().isValid()) {
			sysFileForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					Ext.Msg.alert('操作提示', '保存成功', function() {
						sysFileWin.hide();
						sysFileStore.reload();
					});
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}
	// 基本信息删除
	function delSysFile(record) {
		Ext.Msg.confirm('确认删除', '你确定删除该条记录?', function(btn) {
			if (btn == 'yes') {
				showMask();
				Ext.Ajax.request({
					url : basePath + '/sys/fileUpload/delete.do',
					params : {
						id : record.data.id
					},
					success : function(response) {
						unMask();
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.Msg.alert('操作提示', '删除成功', function() {
								sysFileStore.reload();
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
		sysFileGrid.getView().refresh();
		sysFileGrid.setWidth('100%')
	})
});