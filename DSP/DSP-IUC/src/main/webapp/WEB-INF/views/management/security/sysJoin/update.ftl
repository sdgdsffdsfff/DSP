<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/security/join/update" onsubmit="return validateCallback(this, dialogReloadNavTab);">
   <input type="hidden" name="id" value="${id}"/>
	<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="接入IP:">
			<input type="text" name="joinip" class="required" size="20" maxlength="300"/>
		</@dwz.label_input>										
	</@dwz.layout_form_content>
			
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>