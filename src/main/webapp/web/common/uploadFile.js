var setSource_code;
var setSource_type;
var setSource_path;
var setSource_name;
var esFileUploadForm = new Ext.form.FormPanel({
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

var esFileUploadWin = new Ext.Window({
	title : '资源上传',
	width : 400,
	height : 200,
	layout : 'fit',
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	waitTitle : '正在提交中',
	items : esFileUploadForm,
	buttons : [
			{
				text : '上 传',
				handler : function() {
					if (esFileUploadForm.form.isValid()) {
						esFileUploadForm.getForm().submit(
								{
									url : basePath + '/common/upload.do',
									params : {
										fileType : setSource_type
									},
									success : function(form, action) {
										Ext.Msg.alert('成功', '上传成功.', function() {
											if(Ext.getCmp(setSource_code).getForm().findField(setSource_path)){
												Ext.getCmp(setSource_code).getForm().findField(setSource_path).setValue(
														action.result.createFilename);
											}
											if(Ext.getCmp(setSource_code).getForm().findField(setSource_name)){
												Ext.getCmp(setSource_code).getForm().findField(setSource_name).setValue(
														action.result.old_filename);
											}
											esFileUploadWin.hide();
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
					esFileUploadWin.hide();
				}
			} ]
});
function showEsFileUpload(formId, bglx, filePath,fileName) {
	setSource_code = formId;
	setSource_type = bglx;
	if(filePath){
		setSource_path = filePath;  //文件真实名称对应form中的path
	}else{
		setSource_path = 'filePath';
	}
	if(fileName){
		setSource_name = fileName;  //文件真实名称对应form中的name
	}else{
		setSource_name = 'fileName';
	}
	
	esFileUploadWin.show();
}