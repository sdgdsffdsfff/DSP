<script type="text/javascript">
<!--
// top

jQuery(document).ready(function(){
    
    $(".treeCustom li div #div ").toggle(function(){
      
      if($(this).attr("class") == "collapsable"){
         $(this).removeClass("collapsable").addClass("expandable");
         $(this).parent().siblings().css({"display":"none"});
      }else if($(this).attr("class") == "last_collapsable"){
         $(this).removeClass("last_collapsable").addClass("last_expandable");
         $(this).parent().siblings().css({"display":"none"});
      }else if($(this).attr("class") == "last_collapsable collapsable"){
         $(this).removeClass("last_collapsable collapsable").addClass("last_expandable");
         $(this).parent().siblings().css({"display":"none"});
      }else {
          $(this).removeClass("expandable").addClass("collapsable"); 
          $(this).parent().siblings().css({"display":"block"});
      }
      
    },function(){
      
      if($(this).attr("class") == "expandable"){
         $(this).removeClass("expandable").addClass("collapsable"); 
         $(this).parent().siblings().css({"display":"block"});
      }else if($(this).attr("class") == "last_expandable" ){
         $(this).removeClass("last_expandable").addClass("last_collapsable"); 
         $(this).parent().siblings().css({"display":"block"});
      }else{
         $(this).removeClass("collapsable").addClass("expandable");
         $(this).parent().siblings().css({"display":"none"});
      }
      
    });
    
    $(".setAll").click(function(){
    	var isChecked = $(this).is(":checked");
    
		var $inputSpan = $(this).parent("span");
		$("input[type=checkbox]", $inputSpan).each(function(){
			if (isChecked) {
				$(this).attr("checked", "checked");
			} else {
				$(this).removeAttr("checked");
			}
		});
    });
    
   
    
   $("#select").change(function(){
      //alert($(this).val());
      href="${request.contextPath}/management/security/role/filter?id="+$("#roleId").val()+"&moduleId="+$(this).val();
      //window.open("http://www.baidu.com");
      //window.location.href = href;
      //alert("href:"+href);
      //$.pdialog.closeCurrent();
      //$.pdialog.close("dlg_page1");
      var options = '{width:1200px,height:600px,mask:true}';
      //$.pdialog.open(href, "dlg_page1", "编辑角色");
      $.pdialog.reload(href,"","dlg_page1");


      /*
      $.ajax({
            type: 'GET',
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            url: "${request.contextPath}/management/security/role/filter?id="+${role.id}+"&moduleId="+$(this).val(),
            error: function() { 
    	 		alertMsg.error('查询失败！');
    		},
    		success: function() { 
    	    	
    	    	// 删除已分配
    	    	var $remove = $("#userRoleRow_" + userRoleId).remove();
	        	var roleName = $remove.find("td").eq(0).text()
		    	// 添加分配
				var	$div = $('tr[class="selected"]', getCurrentNavtabRel()).find("td").eq(5).find("div");
				var text = $div.text();
				$div.text(text.replace(roleName, ""));
				
    		}
           });
       */    


      
      /*     
      var keywords = $("#select").val();
      
      if(keywords == ""){
          keywords = "all";
      }
      
      $("#first_tree li ul li div a").each(function(){
      
      if($.trim($("#select").val()) == $.trim($(this).html())){
        //对自己的操作
        //1对自身做显示
        $(this).parent().parent().parent().parent().children("ul").css({"display":"block"});//先让父类显示
        $(this).parent().parent().children("ul").css({"display":"block"});
        //2对图标的处理
        $(this).parent().children("div").removeClass("expandable").addClass("collapsable");
        //对其他节点的操作
        //3对其他兄弟节点做隐藏       
        $(this).parent().parent().siblings().children("ul").css({"display":"none"});
        //4对其他兄弟节点图标做处理
        $(this).parent().parent().siblings().children("div").children("div").removeClass("collapsable").addClass("expandable");
        //5对父节点的兄弟节点做隐藏
        $(this).parent().parent().parent().parent().siblings().children("ul").css({"display":"none"});
        //6对父节点的兄弟节点图标做隐藏
        $(this).parent().parent().parent().parent().siblings().children("div").children("div").removeClass("collapsable").addClass("expandable");
        
        
        $(this).parent().parent().siblings().css({"display":"none"});
        $(this).parent().parent().css({"display":"block"});
        //$(this).parent().parent().children("ul").children("li").children("div").children("div:eq(2)").removeClass("line").addClass("indent");
        
        
      } 
      
     });
     */
   });
   
   
   
   
   
//   $("ul li ul li ul div a").parent().parent().children("ul").hide();
   //$("ul li div a").click(function(){$(this).parent().parent().children("ul").show();$(this).parent().parent().siblings().children("ul").hide();});
   //$("ul li div a").toggle(function(){$(this).parent().parent().siblings().children("ul").removeClass("treeCustom expand").addClass("treeCustom collapse");},function(){$(this).parent().parent().siblings().children("ul").removeClass("treeCustom collapse").addClass("treeCustom expand");});
  
  
  
  
});
//-->
   
   
   
</script>
<#macro role_tree children>
    <#if children?exists && children?size gt 0>
    	<ul class="treeCustom ztree">
        <#list children as child>
        	<li<#if (child_index+1)==children?size> class="last"</#if> >
        	
        	<div class="first_collapsable">
        	${treeStyle}
        	<#if (child.children?size) gt 0>
        		<#-- 使用treeStyle来递归tree的样式
        			下一层时，样式叠加
        			上一层时，样式递减
        			<#assign treeStyle = treeStyle?substring(0, treeStyle?last_index_of("<div"))/>
        		 -->
        		<#if child!=children?last>
        		   <#assign treeStyle=treeStyle + "<div class=\"line\"></div>">
        		   <div class="collapsable" id="div"></div>
        		<#else>
                   <#assign treeStyle=treeStyle + "<div class=\"indent\"></div>"> 
                   <div class="last_collapsable" id="div"></div>  
        		</#if>
        		
        	<#else>
        	    <#assign treeStyle=treeStyle + "<div class=\"indent\"></div>"> 
        		<div class="node"></div>
        	</#if>
			<a href="javascript:void(0);">
			${child.name}
			</a>
			<span class="inputValueRole">
				<#assign hit=0>
				<#list child.permissions as permission>
					<#if (child.sort??&&child.sort.sort.name=='portal')>
			 		${permission.name}&nbsp;&nbsp;<input type="checkbox" name="rolePermissions[${moduleIndex}].permission.id" value="${permission.id}" checked="checked" disabled="true"<#rt/>
			 		<#else>
			 		 ${permission.name}<input type="checkbox" name="rolePermissions[${moduleIndex}].permission.id" value="${permission.id}"<#rt/>
			 		</#if>
			 
			 		<#assign isFind=false>
			 		<#list role.rolePermissions as rolePermission>
			 		<#lt/><#if (rolePermission.permission.id == permission.id)> checked="checked"/>
			 				<input type="hidden" name="rolePermissions[${moduleIndex}].id" value="${rolePermission.id}"/>
			 				<#assign isFind=true>
			 				<#assign hit=hit+1>
			 				<#rt/>
			 				<#break/>
			 			</#if>
			 		</#list>
			 		<#if isFind == false>
			 		<#lt/>/>
			 		</#if>
			 		<#assign moduleIndex=moduleIndex+1>
			 	</#list>
			 	<#if child.dtype!='ROOT' && child.dtype!='SYS'>
				全选<input type="checkbox" class="setAll"<#if hit==child.permissions?size> checked="checked"</#if>/>
				</#if>
			</span>
			</div>
            <@role_tree children=child.children/>
            <#assign treeStyle = treeStyle?substring(0, treeStyle?last_index_of("<div"))/>
            </li>
		</#list>
		</ul>
	</#if>	
</#macro>




<@dwz.layout_content>
<@dwz.form action="${request.contextPath}/management/security/role/update" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${role.id }" id="roleId">
	<@dwz.layout_form_content layoutH="58">
	<@dwz.label_input content="角色名称：">
		<input type="text" name="name" class="required" size="30" maxlength="32" alt="请输入角色名称" value="${role.name }"/>
	</@dwz.label_input>
	<@dwz.label_input content="描述：">
		<input type="text" name="description" size="30" maxlength="255" alt="请输入描述" value="${role.description!'' }"/>
	</@dwz.label_input>
	
	<p>
	<label>系统名称（搜索）：</label>
	<select id="select" style="margin-left:70px;" name="moduleId">
	  <#list sysModule as module>
	     <#if moduleId??&&moduleId==module.id> 
	        <option value ="${module.id}" selected="selected">${module.name}</option>
	     <#else> 
	        <option value ="${module.id}">${module.name}</option>   
	     </#if>
	  </#list> 
	</select>
	<label class="alt" for="description_2345" style="width: 177px; top: 5px; left: 130px; opacity: 0.3; display: none;">请输入描述</label>
	</p>
	
	<@dwz.layout_divider/>
	
	<ul class="treeCustom " id="first_tree">
		<li>
			<div class=""><div class="last_collapsable"></div>
			<a href="javascript:void(0);" class="permissionList">
				${module.name }
			</a>
			</div>
			<#assign treeStyle="<div class=\"indent\"></div>">
			<#assign moduleIndex=0>
			<@role_tree children=module.children/>
		</li>
	</ul>
	</@dwz.layout_form_content>	
	<@dwz.form_bar submitTitle="确定" closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>