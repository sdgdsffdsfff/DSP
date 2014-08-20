<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/security/join/create" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="接入IP:">
			<input type="text" name="joinip" class="required" size="20" maxlength="32" value="${sysjoin.joinip!""}"/>
		</@dwz.label_input>										
		<@dwz.label_input content="系统:">
			<input name="moudle.id" value="" type="hidden"/>
			<input class="required" name="moudle.name" type="text" readonly="readonly"/>
			<a class="btnLook" href="${request.contextPath}/management/security/join/lookupmodule" lookupGroup="moudle" title="系统" width="400">查找带回</a>
		</@dwz.label_input>
	</@dwz.layout_form_content>
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>