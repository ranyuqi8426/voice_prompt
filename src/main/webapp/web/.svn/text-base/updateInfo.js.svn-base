var userForm = new Ext.create("Ext.form.Panel", {
	id : 'userForm',
	width : '100%',
	height : 450,
	bodyPadding : 4,
	buttonAlign : 'center',
	defaultType : 'textfield',
	waitTitle : '正在提交中',
	fieldDefaults : {
		labelAlign : 'right',
		labelWidth : 90,
		anchor : '50%',
		margin : '0 0 5 0',
		msgTarget : 'qtip'
	},
	items : [ {
		xtype : 'fieldset',
		border : true,
		title : '登录信息',
		defaultType : 'textfield',
		items : [ {
			name : 'id',
			hidden : true
		}, {
			name : 'username',
			fieldLabel : '账号',
			disabled : true
		}, {
			xtype : 'checkbox',
			name : 'chkUpdatePass',
			boxLabel : '修改登录密码',
			hideLabel : true,
			checked : true,
			margin : '0 0 10 60',
			scope : this,
			handler : onUpdatePassChange
		}, {
			inputType : 'password',
			name : 'oldPass',
			fieldLabel : '原密码',
			allowBlank : false,
			minLength : 3,
			maxLength : 20
		}, {
			inputType : 'password',
			name : 'newPass',
			id : 'newPass',
			fieldLabel : '新密码',
			allowBlank : false,
			minLength : 3,
			maxLength : 20
		}, {
			inputType : 'password',
			name : 'reNewPass',
			fieldLabel : '确认新密码',
			allowBlank : false,
			minLength : 3,
			maxLength : 20,
			vtype : 'confirmPassword',
			confirmTo : "newPass"
		} ]
	}, {
		xtype : 'fieldset',
		layout : 'column',
		border : true,
		title : '基本信息',
		defaultType : 'textfield',
		columnWidth : 0.5,
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
			vtype : 'mobile',
			allowBlank : false
		}, {
			name : 'phone',
			fieldLabel : '座机',
			maxLength : 20,
			maxLengthText : '不能超过20个字符',
			allowBlank : true
		} ]
	}, {
		xtype : 'displayfield',
		fieldLabel : '提示：',
		value : '首次登录请修改密码，并完善个人信息',
		fieldStyle : {
			width : '400px',
			color : 'red'
		},
		width : 500,
		hidden : true,
		id : 'showDesc'
	} ],
	buttons : [ {
		text : '保存',
		iconCls : "save",
		glyph : glyphSave,
		handler : updateInfo
	}, {
		text : '返回',
		iconCls : "close",
		id : 'btnClose',
		glyph : glyphBack,
		handler : function() {
			userWin.hide();
		}
	} ]
});
function onUpdatePassChange(box, checked) {
	if (checked) {
		userForm.getForm().findField("oldPass").setDisabled(false);
		userForm.getForm().findField("newPass").setDisabled(false);
		userForm.getForm().findField("reNewPass").setDisabled(false);
	} else {
		userForm.getForm().findField("oldPass").setDisabled(true);
		userForm.getForm().findField("newPass").setDisabled(true);
		userForm.getForm().findField("reNewPass").setDisabled(true);
	}
}
var userWin = new Ext.Window({
	width : 600,
	height : 500,
	glyph : glyphWindow,
	title : '『修改个人信息』',
	modal : true,
	closeAction : 'hide',
	padding : 4,
	items : [ userForm ]
});

function showUpdateInfo() {
	Ext.Ajax.request({
		url : basePath + '/getUserInfo.do',
		method : 'POST',
		success : function(response) {
			var result = Ext.decode(response.responseText);
			userForm.getForm().findField("id").setValue(result.id);
			userForm.getForm().findField("username").setValue(result.username);
			userForm.getForm().findField("realname").setValue(result.realname);
			userForm.getForm().findField("gender").setValue(result.gender);
			userForm.getForm().findField("mobile").setValue(result.mobile);
			userForm.getForm().findField("phone").setValue(result.phone);
			userWin.show();
		},
		failure : function() {
			Ext.Msg.alert('提示', '操作失败');
		}
	});
}

function updateInfo() {
	var url = "";
	url = basePath + '/updateInfo.do';
	if (userForm.getForm().isValid()) {
		userForm.getForm().submit({
			clientValidation : true,
			url : url,
			success : function(form, action) {
				Ext.Msg.alert('操作提示', action.result.msgText, function() {
					userWin.hide();
					Ext.getCmp('showDesc').hide();
				})

			},
			failure : function(form, action) {
				Ext.Msg.alert('操作提示', action.result.msgText)
			}
		});
	}
}