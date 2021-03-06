<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>用户中心管理平台</title>
<link href="${request.contextPath}/styles/management/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${request.contextPath}/styles/management/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${request.contextPath}/styles/management/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${request.contextPath}/styles/management/themes/css/custom.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${request.contextPath}/styles/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

<!--[if IE]>
<link href="${request.contextPath}/styles/management/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="${request.contextPath}/styles/management/js/speedup.js" type="text/javascript"></script>
<script src="${request.contextPath}/styles/management/js/jquery-1.7.2.min.js" type="text/javascript"></script>

<script src="${request.contextPath}/styles/management/js/jquery.cookie.js" type="text/javascript"></script>

<script src="${request.contextPath}/styles/management/js/jquery.validate.js" type="text/javascript"></script>
<script src="${request.contextPath}/styles/management/js/jquery.bgiframe.js" type="text/javascript"></script>
<#--
<script src="${request.contextPath}/styles/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/styles/uploadify/scripts/swfobject.js" type="text/javascript"></script>
<script src="${request.contextPath}/styles/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>
-->

<script src="${request.contextPath}/styles/management/js/dwz.min.js" type="text/javascript"></script>

<script src="${request.contextPath}/styles/management/js/dwz.regional.zh.js" type="text/javascript"></script>

<!-- 自定义JS -->
<script src="${request.contextPath}/styles/management/js/customer.js" type="text/javascript"></script>

<!-- jqueryform -->
<script src="${request.contextPath}/styles/jqueryform/2.8/jquery.form.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
$(function(){	
	DWZ.init("${request.contextPath}/styles/management/dwz.frag.xml", {
		loginUrl:"${request.contextPath}/login/timeout", 
		loginTitle:"登录",	// 弹出登录对话框
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${request.contextPath}/styles/management/themes"});
		}
	});
});
</script>
</head>
<body scroll="no">
<div id="layout">
	<div id="header">
		<div class="headerNav">
			<a class="logo" href="${request.contextPath}/management/index">Logo</a>
			<ul class="nav">
				<li><a href="${request.contextPath}/management/index">主页</a></li>
				<li><a href="${request.contextPath}/management/index/updateBase" target="dialog" mask="true" width="550" height="250">修改用户信息</a></li>
				<li><a href="${request.contextPath}/management/index/updatePwd" target="dialog" mask="true" width="550" height="200">修改密码</a></li>
				<li><a href="${request.contextPath}/logout">退出</a></li>
			</ul>
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">blue</div></li>
				<li theme="green"><div>green</div></li>
				<li theme="purple"><div>purple</div></li>
				<li theme="silver"><div>silver</div></li>
				<li theme="azure"><div>天蓝</div></li>
			</ul>
		</div>
	</div>
	<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse"><div></div></div>
			</div>
		</div>
		<div id="sidebar">
			<div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>
			<@dwz.accordion fillSpace="sideBar">
				<#list (menuModule.children) as level1>
					<@dwz.accordion_header title=level1.name />
					<@dwz.accordion_content menu=level1.children target="navTab" urlPrefix="${request.contextPath}"/>
				</#list>
				<div class="accordionContent">
		<ul class="tree treeFolder expand">
			<li>
				<a href="${request.contextPath}/management/security/user/list" target="navTab" rel="User_5">用户管理</a>
			</li>	
			<li>
				<a href="/management/security/role/list" target="navTab" rel="Role_6">角色管理</a>
			</li>	
			<li>
				<a href="/management/security/module/tree" target="navTab" rel="Module_7">模块管理</a>
    	<ul>
			<li>
				<a href="/management/security/module/tree" target="navTab" rel="iModule_9">用户中心模块管理</a>
            </li>
			<li>
				<a href="/management/security/module/olist" target="navTab" rel="oManager_10">外部系统管理</a>
    	<ul>
			<li>
				<a href="/management/security/module/olist" target="navTab" rel="oSys_11">系统管理</a>
            </li>
			<li>
				<a href="/management/security/module/tree?type=o" target="navTab" rel="oModule_12">模块管理</a>
            </li>
		</ul>
            </li>
		</ul>
			</li>	
			<li>
				<a href="/management/security/organization/tree" target="navTab" rel="Organization_8">组织管理</a>
			</li>	
			<li>
				<a href="/management/security/join/list" target="navTab" rel="sysJoin_13">系统接入管理</a>
			</li>	
			<li>
				<a href="/management/security/cacheManage/index" target="navTab" rel="CacheManage_14">缓存管理</a>
			</li>	
		</ul>
	</div>	
			</@dwz.accordion>
		</div>
	</div>
	<div id="container">
		<div id="navTab" class="tabsPage">
			<div class="tabsPageHeader">
				<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
					<ul class="navTab-tab">
						<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">主页</span></span></a></li>
					</ul>
				</div>
				<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
				<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
				<div class="tabsMore">more</div>
			</div>
			<ul class="tabsMoreList">
				<li><a href="javascript:void(0)">主页</a></li>
			</ul>
			<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page unitBox">
					<div class="accountInfo">
						<div class="right">
							<p>${.now?string("yyyy-MM-dd EEEE")}</p>
						</div>
						<p><span>欢迎, qq .</span></p>
					</div>
					<@dwz.layout_form_content layoutH="80">
						<@dwz.fieldset title="基本信息">
							<@dwz.dl_fieldset title="登录名称：" content="qq"/>
																															
						</@dwz.fieldset>
					</@dwz.layout_form_content><!-- end div class="pageFormContent" -->
				</div>
			</div>
		</div>
	</div>
</div>

<div id="footer">Copyright @ 2013 Gionee All Rights Reserved. 粤ICP备05087105号 </div>
</body>
</html>