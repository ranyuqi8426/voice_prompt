<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>铁路行管理对账平台-登录</title>
<link rel="stylesheet" type="text/css" href="login/style.css" />
<script type="text/javascript" src="resources/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="web/userBrower.js?versionNo=${versionNo}"></script>
<script type="text/javascript">
$(function(){
	$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());//jquery1.9之前可以使用$.browser
	if ($.browser.msie && 8 >= $.browser.version) {
		$("#box").hide();
		$("#box2").show();
	}
});
</script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

html, body, #box {
	height: 100%;
	font: small/1.5 "宋体", serif;
}

body {
	text-align: center;
}

#box {
	background: #19609e;
	text-align: left;
	display: table;
	width: 100%;
	margin: 0 auto;
	position: relative;
}

#box>#main {
	display: table-row;
}

#header {
	background-color: #ffffff;
	height: 80px;
	width: 100%;
}

#footer {
	display: table-row;
	background-color: #ffffff;
	height: 90px;
	vertical-align: bottom;
}

#main {
	
}

#wrap {
	position: relative;
	display: table-cell;
	vertical-align: middle;
	background-image: url(login/bg1.png);
	background-size: 954px 454px;
	background-repeat: no-repeat;
	background-position: 50% 45%;
	margin: 0 auto;
}

#text1 {
	width: 954px;
	height: 454px;
	margin: 0 auto;
}

#tp {
	float: left;
	width: 650px;
	height: 454px;
}

#userName {
	line-height: 40px;
	font-size: 15px;
	background: white;
	width: 200px;
	height: 40px;
	border: 0px;
}

#passWord {
	line-height: 42px;
	font-size: 15px;
	background: white;
	width: 200px;
	height: 42px;
	border: 0px;
}

#loginForm {
	float: left;
	margin-top: 95px;
}

#tp1 {
	
}

.errMsg {
	margin-top: 8px;
	color: red;
}
</style>

<style type="text/css">

	#box2 {
		position: relative;
		background-color:#f7f7f7;
		width: 100%;
		height: 100%;
		text-align: left;
		font-size: medium;
	}
	
	#errMsgCon{
		position: absolute;
		top: 50%;
		left: 0;
		height: 200px;
		width: 100%;
		margin-top: -100px;
		background-color: #f5fbff;
		border-top: 1px solid #c7d7e1;
		border-bottom: 1px solid #c7d7e1;
		padding-top: 3em;
	}
	#errMsgCon > div{
		margin: 0 auto;
		width: 400px;
	}
	#errMsgCon > div > ul{
		padding-left: 18px;
		margin-top: 2em;
	}
	#errMsgCon > div > ul > li{
		height: 2.5em;
		line-height: 2.5em;
	}
</style>

</head>
<body>
	<div id="box">
		<div id="header">
			<div style="height: 10px;"></div>
			<img id="tp1" style="height: 70px; margin-left: 15%;" src="login/logo1.png" />
		</div>
		<div id="main">
			<div id="wrap">
				<div id="text1">
					<div id="tp"></div>
					<form id="loginForm" action="" method="post" onSubmit="return false;">
						<table id="tabLog" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="2" height="20px"></td>
							</tr>
							<tr>
								<td colspan="2" style="font-size: 27px;" id="ga"><input type="text" title="用户名" id="userName"
									name="userName" alt="用户名" /></td>
							</tr>
							<tr>
								<td colspan="2" height="29px" id="gb"></td>
							</tr>
							<tr>
								<td colspan="2" style="font-size: 27px" id="gc"><input type="password" title="密码" id="passWord"
									name="passWord" alt=“密码”></td>
							</tr>
							<tr>
								<td colspan="2" height="15px"></td>
							</tr>
							<tr>
								<td colspan="2" height="30px" id="gf"></td>
							</tr>
							<tr>
								<td colspan="2" height="18px" id="gm" style="display: none;"></td>
							</tr>
							<tr style="text-align: center;">
								<td colspan="2" id="ge"><input alt="登录" type="image" id="logBtn" src="login/logbtn1.png"
									onclick="checkLogin();"></td>
							</tr>
							<tr style="text-align: center;">
								<td colspan="2"><span id="lblMessage" class="errMsg" style="display: none"> 账号或密码输入错误！ </span></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="footer">
			<div></div>
		</div>
	</div>
	
	<div id="box2" style="display:none;">
		<div id="errMsgCon">
			<div>
				<p>很抱歉，你当前的浏览器版本过低，请下载谷歌浏览器</p>
				<ul>
					<li>xp用户<a href="">点击此处下载</a></li>
					<li>win7及以上用户<a href="">点击此处下载</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		function checkLogin() {
			var username = $("#userName").val();
			var password = $("#passWord").val();
			$.ajax({
				type : "post",
				url : "checkLogin.do",
				data : {
					username : username,
					password : password,
					terminal : getUserTerminalType(),
					explorerType : getExplorerInfo().browser,
					explorerVersion : getExplorerInfo().version
				},
				dataType : "json",
				success : function(data) {
					if (data.success) {
						$("#lblMessage").hide();
						window.location.href = "index.do";
					} else {
						$("#lblMessage").html(data.msgText);
						$("#lblMessage").show();
					}
				},
				error : function(data) {
					$("#lblMessage").html('登录失败');
					$("#lblMessage").show();
				}
			});
		}
	</script>
</body>
</html>
