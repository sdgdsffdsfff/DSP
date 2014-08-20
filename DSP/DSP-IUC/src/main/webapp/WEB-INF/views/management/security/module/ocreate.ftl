 <script type="text/javascript">
<!--
// top
jQuery(document).ready(function(){
   $("#permissionForm1").submit(function(event){
         event.preventDefault();
    	 event.stopPropagation();
     	 if (!$(this).valid()) {
     	 	return false;	
     	 } 
     	 
    	 return validateCallback(this, dialogReloadRel);
     });
});
//-->
</script>
<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/security/module/create" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="parent.id" value="${parentModuleId}"/>
	<input type="hidden" name="dtype" value="SYS"/>
	<input type="hidden" name="permissions[0].name" value="访问"/>
	<input type="hidden" name="permissions[0].shortName" value="view"/>
		<@dwz.layout_form_content layoutH="58">
		<@dwz.label_input content="系统名称：">
			<input type="text" name="name" class="required" size="32" maxlength="32" alt="请输入名称"/>
		</@dwz.label_input>
		<@dwz.label_input content="URL：">
			<input type="text" name="url" class="required" size="32" maxlength="255" alt="请输入首页地址"/>
		</@dwz.label_input>			
		<@dwz.label_input content="授权名称：">
			<input type="text" name="sn" class="required" size="32" maxlength="100" alt="授权名称"/>
		</@dwz.label_input>	
		<@dwz.label_input content="描述：">
			<input type="text" name="description" size="32" maxlength="255" alt="描述"/>
		</@dwz.label_input>		

	</@dwz.layout_form_content>
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>
