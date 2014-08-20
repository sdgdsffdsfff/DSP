<script type="text/javascript">
<!--
// top
jQuery(document).ready(function(){
    
    $(".treeCustom li div #div").toggle(function(){
      
      if($(this).attr("class") == "collapsable" || $(this).attr("class") == "collapsable collapsable"){
         $(this).removeClass("collapsable").addClass("expandable");
         $(this).parent().siblings().css({"display":"none"});
      }else if($(this).attr("class") == "last_collapsable"){
         $(this).removeClass("last_collapsable").addClass("last_expandable");
         $(this).parent().siblings().css({"display":"none"});
      }else if($(this).attr("class") == "last_collapsable collapsable"){
         $(this).removeClass("last_collapsable collapsable").addClass("last_expandable");
         $(this).parent().siblings().css({"display":"none"});
      }else{
          $(this).removeClass("expandable").addClass("collapsable"); 
          $(this).parent().siblings().css({"display":"block"});
      }
      
    },function(){
      
      if($(this).attr("class") == "expandable"){
         $(this).removeClass("expandable").addClass("collapsable"); 
         $(this).parent().siblings().css({"display":"block"});
      }else if($(this).attr("class") == "last_expandable"){
         $(this).removeClass("last_expandable").addClass("last_collapsable"); 
         $(this).parent().siblings().css({"display":"block"});
      }else{
         $(this).removeClass("collapsable").addClass("expandable");
         $(this).parent().siblings().css({"display":"none"});
      }
      
    });

   $("#select").change(function(){
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
      } 
      
     });
   });
  
});
//-->
   
   
   
</script>
<#macro role_tree children>
    <#if children?exists && children?size gt 0>
    	<ul>
        <#list children as child>
        	<li<#if (child_index+1)==children?size> class="last"</#if>>
        	
        	<div class="">
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
			<a href="javascript:void(0);" class="permissionList">
			${child.name}
			</a>
			<span class="inputValueRole">
				<#assign hit=0>
				<#list child.permissions as permission>
			 		${permission.name}<input type="checkbox" onclick="return false;" name="rolePermissions[${moduleIndex}].permission.id" value="${permission.id}"<#rt/>
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
				全选<input type="checkbox" onclick="return false;" class="setAll"<#if hit==child.permissions?size> checked="checked"</#if>/>
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
<@dwz.form action="#">
	<input type="hidden" name="id" value="${role.id }">
	<@dwz.layout_form_content layoutH="58">
	<@dwz.label_input content="角色名称：">
		<input type="text" name="name" class="required" size="30" maxlength="32" alt="请输入角色名称" value="${role.name }" readonly="readonly"/>
	</@dwz.label_input>		
	<p>
	<label>系统名称（搜索）：</label>
	
	<select id="select" style="margin-left:70px;">
	  <#list sysModule as module> 
	   <option value ="${module.name}">${module.name}</option> 
	  </#list> 
	</select>
	<label class="alt" for="description_2345" style="width: 177px; top: 5px; left: 130px; opacity: 0.3; display: none;">请输入描述</label>
	</p>
	<@dwz.layout_divider/>
	<ul class="treeCustom" id="first_tree">
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
	<@dwz.form_bar closeTitle="关闭"/>	
</@dwz.form>
</@dwz.layout_content>