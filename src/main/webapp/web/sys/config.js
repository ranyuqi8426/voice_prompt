/**
 * 系统配置界面
 * @author 孔垂云
 * @date 2016-12-06
 */
Ext.onReady(function() {
	Ext.setGlyphFontFamily('FontAwesome');
	Ext.define('Config', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'syskey',
			type : 'string'
		}, {
			name : 'sysvalue',
			type : 'string'
		}, {
			name : 'description',
			type : 'string'
		}, {
			name : 'display_order',
			type : 'int'
		} ]
	});

	var configStore = Ext.create('Ext.data.Store', {
		model : 'Config',
		pageSize : pagelimit,
		proxy : {
			type : 'ajax',
			url : basePath + '/sys/config/search.do',
			actionMethods : {
				read : "POST"
			}
		},
		autoLoad : true
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		mode : 'SINGLE'
	});

	var configGrid = Ext.create('Ext.grid.Panel', {
		title : '系统配置列表',
		frame : true,
		glyph : glyphGrid,
		width : '100%',
		height : pageH,
		// columnLines : true,
		collapsible : true,// 表头缩回按钮
		store : configStore,
		selModel : selModel,
		renderTo : Ext.getBody(),
		columns : [ {
			xtype : 'rownumberer'
		}, {
			text : '参数',
			dataIndex : 'syskey',
			width : 180,
			sortable : false
		}, {
			text : '参数值',
			dataIndex : 'sysvalue',
			width : 140,
			sortable : false
		}, {
			text : '描述',
			dataIndex : 'description',
			flex : 1,
			sortable : false
		}, {
			text : '排序',
			dataIndex : 'display_order',
			width : 50,
			sortable : false
		} ],
		viewConfig : {
			stripeRows : true
		},
		tbar : Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				text : '修改',
				glyph : glyphUpdate,
				handler : update
			} ]
		})
	});
	var configForm = new Ext.create("Ext.form.Panel", {
		id : 'configForm',
		frame : true,
		border : true,
		width : '100%',
		height : 200,
		buttonAlign : 'center',
		defaultType : 'textfield',
		waitTitle : '正在提交中',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 90,
			msgTarget : 'qtip',
			anchor : '100%'
		},
		items : [ {
			name : 'syskey',
			hidden : true
		}, {
			name : 'showSyskey',
			fieldLabel : '参数',
			maxLength : 20,
			maxLengthText : '不能超过20个字符',
			allowBlank : false
		}, {
			name : 'sysvalue',
			fieldLabel : '参数值',
			maxLength : 40,
			maxLengthText : '不能超过40个字符',
			allowBlank : false
		}, {
			name : 'description',
			fieldLabel : '描述',
			maxLength : 50,
			maxLengthText : '不能超过50个字符',
			allowBlank : false
		}, {
			name : 'display_order',
			fieldLabel : '排序',
			vtype : 'posInt'
		} ],
		buttons : [ {
			text : '保存',
			glyph : glyphSave,
			handler : save
		}, {
			text : '清空',
			id : 'btnClear',
			glyph : glyphClear,
			handler : function() {
				configForm.getForm().reset();
			}
		}, {
			text : '返回',
			glyph : glyphBack,
			handler : function() {
				configWin.hide();
			}
		} ]
	});

	var configWin = new Ext.Window({
		width : 400,
		height : 240,
		glyph : glyphWindow,
		title : '新增/修改配置信息',
		modal : true,
		closeAction : 'hide',
		padding : 4,
		items : [ configForm ]
	});

	/** *******************按钮方法******************************* */
	// 修改
	function update() {
		var record = configGrid.getSelectionModel().getLastSelected();
		if (!record)
			return;
		configForm.getForm().loadRecord(record);
		showUpdateCode(configForm, 'showSyskey', record.data.syskey);
		configWin.setTitle("『修改配置信息』");
		Ext.getCmp('btnClear').hide();
		configWin.show();
	}
	// 保存数据
	function save() {
		if (configForm.getForm().isValid()) {
			var url = "";
			if (configForm.getForm().findField("syskey").getValue() == "")
				url = basePath + '/sys/config/add.do';
			else
				url = basePath + '/sys/config/update.do';
			configForm.getForm().submit({
				clientValidation : true,
				url : url,
				success : function(form, action) {
					configWin.hide();
					configStore.reload();
				},
				failure : function(form, action) {
					Ext.Msg.alert('操作提示', action.result.msgText)
				}
			});
		}
	}

	// 自适应大小
	Ext.GlobalEvents.on('resize', function() {
		configGrid.getView().refresh();
		configGrid.setWidth('100%')
	})
});