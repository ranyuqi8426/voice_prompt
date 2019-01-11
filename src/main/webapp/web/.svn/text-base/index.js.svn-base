Ext.onReady(function() {
	var tabPanelHeight;
	tabPanelHeight = pageH - 150;
	var menu;
	var topPanel = Ext.create('Ext.panel.Panel', {
		layout : 'column',
		items : [ Ext.create('Ext.panel.Panel', {
			items : [ {
				xtype : 'image',
				autoEl : 'div',
				width : 400,
				height : 80,
				src : basePath + '/resources/images/index_1.jpg'
			} ]
		}), Ext.create('Ext.panel.Panel', {
			width : pageW - 780,
			bodyStyle : 'background-color:#037FC3',
			items : [ {
				xtype : 'image',
				autoEl : 'div',
				width : '100%',
				height : 80,
				src : basePath + '/resources/images/index_2.jpg'
			} ]
		}), Ext.create('Ext.panel.Panel', {
			width : 380,
			bodyStyle : 'background-image:url(resources/images/index_3.jpg);padding-top:45px;padding-left:200px;',
			height : 80,
			items : [ Ext.create('Ext.Button', {
				text : '修改个人信息',
				glyph : 0xf013,
				style : 'background:transparent;border:0',
				handler : function() {
					showUpdateInfo();
				}
			}), Ext.create('Ext.Button', {
				text : '注销',
				style : 'background:transparent;border:0',
				glyph : 0xf011,
				handler : logout
			}) ]
		}) ]
	});

	var leftPanel = Ext.create('Ext.panel.Panel', {
		glyph : 0xf0c9,
		header : false,
		layout : {
			type : 'accordion',
			animate : true
		}
	})

	// 中间的tabpanel
	var tabPanel = Ext.create('Ext.tab.Panel', {
		frame : false,
		defaults : {
			scrollable : true
		},
		bodyPadding : 4,
		items : [ {
			title : '系统首页',
			glyph : 0xf015,
			disabled : false,
			html : '<iframe frameborder=0 width="100%" height=' + pageH + ' scrolling="auto" src=""></iframe>'
		// html:'<img src="resources/images/login.jpg" width="100%"
		// height="100%" ></img>'
		} ],
		plugins : new Ext.create('Ext.ux.TabCloseMenu', {
			closeTabText : '关闭面板',
			closeOthersTabsText : '关闭其他',
			closeAllTabsText : '关闭所有'
		}),
		listeners : {
		// tabchange : onTabChange
		}
	});

	var footPanel = Ext.create('Ext.toolbar.Toolbar', {
		style : 'background-color:#037FBF;color:white',
		items : [ {
			xtype : 'label',
			text : '用户：' + realname
		}, '-', {
			xtype : 'label',
			text : '所在单位：' + dwmc
		}, '-', {
			xtype : 'label',
			text : '当前日期：' + today
		}, '-', {
			xtype : 'label',
			text : '当前在线人数:' + onlineCount
		}, '-', '->', '->', {
			xtype : 'label',
			text : '版权所有© 2016-2017 中国铁路信息技术中心 '
		}, {
			xtype : 'label',
			text : '【V1.0版】'
		} ]
	});

	var viewport = Ext.create('Ext.container.Viewport', {
		layout : 'border',
		// renderTo :Ext.getBody(),
		// width : '100%',
		// layout:'fit',
		// height : 600,
		// bodyBorder : false,
		items : [ {
			// title : 'Header',
			region : 'north',
			height : 80,
			padding : '0 0 0 0',
			items : [ topPanel ]
		}, {
			title : '系统菜单',
			glyph : 0xf0c9,
			region : 'west',
			id : 'leftPosition',
			collapsible : true,
			split : true,
			layout : 'fit',
			margin : '2 0 2 0',
			width : 200,
			minWidth : 100,
			maxWidth : 300,
			items : []
		}, {
			// title : 'Main Content',
			header : false,
			collapsible : false,
			region : 'center',
			margin : '0 0 0 0',
			items : [ tabPanel ]
		}, {
			region : 'south',
			height : 30,
			items : [ footPanel ]
		} ]
	});
	Ext.define('treeMenu', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'string'
		}, {
			name : 'module',
			type : 'string'
		}, {
			name : 'parent_id',
			type : 'string'
		}, {
			name : 'text',
			type : 'string'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'expand'
		} ]
	});
	// 生成菜单
	Ext.Ajax.request({
		url : basePath + '/createMenu.do',
		method : 'POST',
		success : function(response) {
			var menus = Ext.decode(response.responseText);
			for ( var i in menus) {
				var menugroup = menus[i];
				var accpanel = Ext.create('Ext.panel.Panel', {
					menuAccordion : true,
					xtype : 'panel',
					title : menugroup.text,
					layout : 'fit',
					bodyStyle : {
						padding : '4px'
					},
					glyph : menugroup.glyph
				});
				var treeStore = Ext.create('Ext.data.TreeStore', {
					model : 'treeMenu',
					root : {
						expanded : true,
						children : menugroup.children
					}
				});
				var treePanel = Ext.create('Ext.tree.Panel', {
					glyph : 0xf0c9,
					header : false,selType : 'treemodel',
					useArrows : true,
					store : treeStore,
					rootVisible : false,
					listeners : {
						itemclick : menuClick
					}
				})
				accpanel.add(treePanel);
				leftPanel.add(accpanel);
			}
			Ext.getCmp('leftPosition').add(leftPanel);
		}
	});
	function dealTitle(val) {
		if (val.indexOf('-') > -1)
			return val.substr(0, val.indexOf('-'));
		else
			return val;
	}
	function menuClick(a, record, item, index, e, eOpts) {
		if (record.raw.url && record.raw.url != '#') {
			var tab = tabPanel.getComponent("tab" + record.raw.id);
			if (!tab) {
				tabPanel.add({
					autoWidth : true,
					'id' : "tab" + record.raw.id,
					'title' : dealTitle(record.raw.text),
					closable : true,
					html : '<iframe style="margin:0;padding:0" scrolling="auto" frameborder="0" width="100%" height='
							+ tabPanelHeight + ' src="' + record.raw.url + '"></iframe>'
				});
			}
			tabPanel.setActiveTab("tab" + record.raw.id)
		}
	}
	// 打开用户默认的模块tab
	if (default_module != '') {
	}

	function logout() {
		Ext.Ajax.request({
			url : basePath + '/logout.do',
			method : 'POST',
			success : function(response) {
				location.href = 'login.do';
			},
			failure : function() {
				Ext.Msg.alert('提示', '操作失败');
			}
		});
	}

	var descText = "";// '1、每天中午12点半至1点半，晚上6点至7点为系统维护时间，会对系统进行升级维护操作，请业务操作人员在这段时间内不要进行数据核对操作。\r\n';
	var descForm = new Ext.form.FormPanel({
		buttonAlign : 'center',
		bodyStyle : 'padding:5px',
		frame : true,
		xtype : 'container',
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 150
		},
		items : [ {
			margin : '0 0 10 0',
			xtype : 'textareafield',
			name : 'desc',
			height : 350,
			fieldStyle : 'font-size:14px;color:red;line-height:150%;border:0',
			value : descText,
			anchor : '100%'
		} ],
		buttons : [ {
			id : "submitButton",
			iconCls : 'userLogin',
			glyph : 0xf007,
			text : '关闭',
			handler : function() {
				descWin.hide();
			}
		} ]
	});

	var descWin = new Ext.Window({
		width : 540,
		height : 360,
		title : '系统提示',
		resizable : false,
		glyph : 0xf015,
		frame : true,
		layout : 'fit',
		frame : true,
		modal : true,
		items : [ descForm ],
	});

	openTab = function(tab_id, tab_text, tab_url) {
		var tab = tabPanel.getComponent("tab" + tab_id);
		if (!tab) {
			tabPanel.add({
				autoWidth : true,
				'id' : "tab" + tab_id,
				'title' : Ext.util.Format.trim(tab_text),
				// 'glyph' : record.raw.glyph,
				closable : true, // 自动载入jsp页面，该页面含有JavaScript，不缓存
				html : '<iframe style="margin:0;padding:0" scrolling="auto" frameborder="0" width="100%" height='
						+ tabPanelHeight + ' src="' + tab_url + '"></iframe>'
			});
		}
		tabPanel.setActiveTab("tab" + tab_id);
	}

	openTabRefresh = function(tab_id, tab_text, tab_url) {
		var tab = tabPanel.getComponent("tab" + tab_id);
		if (!tab) {
			tabPanel.add({
				autoWidth : true,
				'id' : "tab" + tab_id,
				'title' : Ext.util.Format.trim(tab_text),
				// 'glyph' : record.raw.glyph,
				closable : true, // 自动载入jsp页面，该页面含有JavaScript，不缓存
				html : '<iframe id="tabIframe' + tab_id
						+ '" style="margin:0;padding:0" scrolling="auto" frameborder="0" width="100%" height='
						+ tabPanelHeight + ' src="' + tab_url + '"></iframe>'
			});
		} else {
			document.getElementById("tabIframe" + tab_id).src = tab_url;
		}
		tabPanel.setActiveTab("tab" + tab_id);
	}
	
	//打开欢迎界面
//	openTabRefresh(100,'线路示意图','./web/xlxx.jsp');
	// descWin.show();
});