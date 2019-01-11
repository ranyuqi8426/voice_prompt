/**
 *导入数据公共js
 *共分为三步，1、上传文件，2校验数据，3导入数据
 *@author 孔垂云
 *@date 2016-12-15 
 */
var modelPath = "";// 定义模板路径
var validateDataUrl = "";// 定义校验url
var importDataUrl = "";// 定义导入数据url
var validateFlag=0; //校验标识  0:未校验 1：校验通过 2：校验不通过

var importDataWin = new Ext.Window({
	width : 600,
	height : 300,
	id : 'importDataWin',
	title : '数据导入',
	glyph : glyphWindow,
	modal : true,
	closeAction : 'hide',
	padding : 4,
	items : [ {
		xtype : 'container',
		layout : 'hbox',
		items : [ {
			fieldLabel : '模板下载',
			xtype : 'displayfield',
			labelAlign : 'right'
		}, {
			xtype : 'button',
			text : '下载',
			handler : function() {
				downloadModel();
			}
		} ]
	}, {
		xtype : 'container',
		layout : 'hbox',
		margin : '4 0 4 0',
		defaultType : 'textfield',
		items : [ {
			fieldLabel : '第一步上传文件',
			readOnly : true,
			xtype : 'displayfield',
			labelAlign : 'right'
		}, {
			xtype : 'textfield',
			width : 300,
			readOnly : true,
			id : 'uploadFilePath'
		}, {
			xtype : 'button',
			text : '选择文件',
			handler : function() {
				uploadFileWin.show();
			}
		} ]
	}, {
		xtype : 'container',
		layout : 'hbox',
		margin : '4 0 4 0',
		items : [ {
			fieldLabel : '第二步校验文件',
			xtype : 'displayfield',
			labelAlign : 'right'
		}, {
			xtype : 'button',
			id : 'btnValidateData',
			text : '校验文件',
			handler : function() {
				uploadValidateFile();
			}
		} ]
	}, {
		xtype : 'container',
		layout : 'hbox',
		margin : '4 0 4 0',
		items : [ {
			fieldLabel : '第三步数据导入',
			xtype : 'displayfield',
			labelAlign : 'right'
		}, {
			xtype : 'button',
			id : 'btnImportData',
			text : '导入文件',
			handler : function() {
				importFile();
			}
		}, {
			xtype : 'fieldcontainer',
			fieldLabel : '导入方式',
			labelAlign : 'right',
			defaultType : 'radiofield',
			defaults : {
				flex : 1
			},
			layout : 'hbox',
			items : [ {
				boxLabel : '增量导入',
				name : 'importType',
				checked : true,
				id : 'importType1',
				inputValue : '1'
			}, {
				boxLabel : '覆盖导入',
				id : 'importType2',
				name : 'importType',
				inputValue : '2'
			} ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '返回',
		glyph : glyphBack,
		handler : function() {
			importDataWin.hide();
		}
	} ]
});

var uploadFileForm = new Ext.form.FormPanel({
	labelWidth : 90,
	fileUpload : true,
	defaultType : 'textfield',
	fieldDefaults : {
		labelAlign : 'right',
		labelWidth : 120,
		msgTarget : 'qtip',
		anchor : '100%'
	},
	items : [ {
		xtype : 'filefield',
		emptyText : '请选择要上传的文件',
		fieldLabel : '文件路径',
		allowBlank : false,
		width : 350,
		readOnly : true,
		name : 'file',
		buttonText : '',
		buttonConfig : {
			iconCls : 'icon-upload'
		}
	} ]
});

var uploadFileWin = new Ext.Window({
	title : '上传文件',
	width : 400,
	height : 200,
	layout : 'fit',
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	waitTitle : '正在提交中',
	items : uploadFileForm,
	buttons : [ {
		text : '上 传',
		handler : function() {
			validateFlag = 0; //上传文件后，校验标识重置
			if (uploadFileForm.form.isValid()) {
				uploadFileForm.getForm().submit({
					url : basePath + '/import/upload.do',
					success : function(form, action) {
						Ext.Msg.alert('成功', '上传成功.', function() {
							Ext.getCmp('uploadFilePath').setValue(action.result.createFilename);
							uploadFileWin.hide();
						});
					},
					failure : function(form, action) {
						Ext.Msg.alert('上传错误', action.result.msgText);
					}
				})
			}
		}
	}, {
		text : '取 消',
		handler : function() {
			uploadFileWin.hide();
		}
	} ]
});
// 校验结果
var valiResultWin = new Ext.Window({
	title : '上传文件',
	width : 500,
	height : 340,
	layout : 'fit',
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	waitTitle : '正在提交中',
	items : [ {
		xtype : 'textareafield',
		id : 'valiResult',
		labelAlign : 'right',
		width : 500,
		height : 260,
		fieldStyle : 'font-size:14px;',
		fieldLabel : '校验错误'
	} ],
	buttons : [ {
		text : '取 消',
		handler : function() {
			valiResultWin.hide();
		}
	} ]
});

/**
 * 定义文件下载
 */
var downloadModel = function() {
	window.location.href = basePath + modelPath;
}
/**
 * 校验文件
 */
var uploadValidateFile = function() {
	if (Ext.getCmp('uploadFilePath').getValue() == '') {
		Ext.Msg.alert('操作提示', '请先上传文件');
		return;
	}
	if (Ext.getCmp('importType1').getValue())
		importType = 1
	else if (Ext.getCmp('importType2').getValue())
		importType = 2
	showMask();
	Ext.Ajax.request({
		url : basePath + validateDataUrl,
		params : {
			uploadFilePath : Ext.getCmp('uploadFilePath').getValue(),
			importType : importType
		},
		success : function(response) {
			unMask();
			var result = Ext.decode(response.responseText);
			if (result.success) {
				Ext.Msg.alert('操作提示', '校验成功');
				validateFlag = 1;//通过
			} else {
				Ext.getCmp('valiResult').setValue(result.msgText);
				valiResultWin.show();
				validateFlag = 2; //未通过
			}
		}
	});
}
/**
 * 导入文件
 */
var importFile = function() {
	if (Ext.getCmp('uploadFilePath').getValue() == '') {
		Ext.Msg.alert('操作提示', '请先上传文件');
		return;
	}
	if(validateFlag == 0){
		Ext.Msg.alert('操作提示', '请先校验上传文件');
		return;
	}else if(validateFlag == 2){
		Ext.Msg.alert('操作提示', '检验不通过，请修改');
		return;
	}
	
	var importType = 1;
	if (Ext.getCmp('importType1').getValue())
		importType = 1
	else if (Ext.getCmp('importType2').getValue())
		importType = 2
	Ext.Ajax.request({
		url : basePath + importDataUrl,
		params : {
			uploadFilePath : Ext.getCmp('uploadFilePath').getValue(),
			importType : importType
		},
		success : function(response) {
			unMask();
			var result = Ext.decode(response.responseText);
			if (result.success) {
				Ext.Msg.alert('操作提示', '导入成功', function() {
					importDataWin.hide();
				});
			} else {
				Ext.Msg.alert('操作提示', result.msgText);
			}
		}
	});
}
function startImportData(p1, p2, p3) {
	modelPath = p1;// 定义模板路径
	validateDataUrl = p2// 定义校验url
	importDataUrl = p3;// 定义导入数据url
	Ext.getCmp('uploadFilePath').setValue('');
	importDataWin.show();
}
Ext.onReady(function() {
	Ext.create('Ext.Button', {
		text : '数据导入',
		width : 200,
		height : 30,
		// renderTo : Ext.getBody(),
		handler : function() {
			startImportData('/upload/template/xl/jbxx.xls', '/import/validateFile.do', '/import/importFile.do');
		}
	});

})