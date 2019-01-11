Ext.onReady(function() {
	Ext.onReady(function() {
		var logPanel = new Ext.Panel({
			height : 100,
			normal : false,
			html : '<div><img id="logo"  height="100" width="100%" src=resources/images/logo.jpg border="1"></div>',
			border : false
		});
		var loginForm = new Ext.form.FormPanel({
			buttonAlign : 'center',
			bodyStyle : 'padding:5px',
			frame : true,
			xtype : 'container',
			fieldDefaults : {
				labelAlign : 'right',
				labelWidth : 150
			},
			items : [ logPanel, {
				margin : '10 0 10 0',
				xtype : 'textfield',
				name : 'username',
				id : 'es_username',
				fieldLabel : '用户账号',
				emptyText : '请输入用户账号',
				allowBlank : false,
				value : '',
				blankText : '请输入用户账号',
				anchor : '80%',
				enableKeyEvents : true,
				listeners : {
					keypress : function(field, e) {
						if (e.getKey() == 13) {
							var obj = loginForm.form.findField("password");
							if (obj) {
								obj.focus(false, 100);
							}
						}
					}
				}
			}, {
				xtype : 'textfield',
				inputType : 'password',
				name : 'password',
				id : 'es_password',
				value : '',
				fieldLabel : '用户密码',
				emptyText : '请输入用户密码',
				allowBlank : false,
				blankText : '请输入密码',
				anchor : '80%',
				enableKeyEvents : true,
				listeners : {
					keypress : function(field, e) {
						if (e.getKey() == 13) {
							submitForm();
						}
					}
				}
			}, {
				xtype : 'container',
				layout : 'hbox',
				defaultType : 'textfield',
				margin : '0 0 10 0',
				items : [ {
					name : 'chk',
					id : 'chk',
					value : '1',
					fieldLabel : '记住用户名',
					labelStyle : 'color:#000;text-align:right;',
					xtype : 'checkboxfield'
				}, {
					xtype : 'label',
					width : 80
				} ]
			} ],
			buttons : [ {
				id : "submitButton",
				iconCls : 'userLogin',
				glyph : 0xf007,
				text : '登录',
				handler : submitForm
			}, {
				text : '重置',
				glyph : glyphReset,
				handler : resetForm
			} ]
		});

		function resetForm() {
			loginForm.form.reset();
			var obj = loginForm.getForm().findField("es_username");
			if (obj) {
				obj.focus();
			}
		}
		var loginWin = new Ext.Window({
			width : 540,
			height : 360,
			title : '系统登录',
			resizable : false,
			glyph : 0xf015,
			frame : true,
			layout : 'fit',
			frame : true,
			modal : true,
			items : [ loginForm ],
			bbar : [ '->', '->', {
				xtype : 'label',
				text : '版权所有@2016 中国铁路信息技术中心'
			} ]
		});

		loginWin.show();
		loginForm.getForm().findField("es_username").focus(false, 200)
		var cookie_chk = Ext.util.Cookies.get('es_chk');
		if (cookie_chk != '' && cookie_chk != null && cookie_chk == '1') {
			Ext.getCmp('chk').checked = true;
			Ext.getCmp('es_username').setValue(Ext.util.Cookies.get('es_username'));
			Ext.getCmp('es_password').setValue(Ext.util.Cookies.get('es_password'));

		}
		function submitForm() {
			if (loginForm.form.isValid()) {
				if (Ext.getCmp('chk').getValue()) {
					Ext.util.Cookies.set('es_chk', '1');
					Ext.util.Cookies.set('es_username', Ext.getCmp('es_username').getValue());
					Ext.util.Cookies.set('es_password', Ext.getCmp('es_password').getValue());
				} else {
					Ext.util.Cookies.set('es_chk', '0');
				}
				loginForm.getForm().submit({
					waitMsg : '正在登录验证',
					waitTitle : '提示',
					url : basePath + '/checkLogin.do',// 提交的Url地址
					params : {
						terminal : getUserTerminalType(),
						explorerType : getExplorerInfo().browser,
						explorerVersion : getExplorerInfo().version
					},
					method : 'post',
					success : function(form, action) {
						window.location = 'index.do';
					},
					failure : function(form, action) {
						Ext.MessageBox.show({
							title : '系统提示',
							msg : action.result.msgText,
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				});
			}
		}
	});
});