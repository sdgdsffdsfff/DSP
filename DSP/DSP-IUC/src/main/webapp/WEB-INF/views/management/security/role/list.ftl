<@dwz.pagerForm action="${request.contextPath}/management/security/role/list" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/security/role/list">
	<@dwz.label_input2 content="角色名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
	
	<li>
	    <label>系统权限：</label>
	    <select style="height:22px;width:150px;" name="moduleId">
	        <option value ="">---全部---</option>
	        <#list sysModule as module> 	           
	           <#if moduleId??&&moduleId==module.id>
                   <option value ="${module.id}" selected="selected">${module.name}</option>
               <#else>
                   <option value ="${module.id}">${module.name}</option>   
               </#if>
	        </#list> 
	    </select>
	</li>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Role:view">
			<@dwz.tool_button content="查看角色" class="magnifier" width="1200" height="600" href="${request.contextPath}/management/security/role/view/{slt_uid}"/>
		</@shiro.hasPermission>		
		<@shiro.hasPermission name="Role:save">
			<@dwz.tool_button content="添加角色" class="shield_add" width="1200" height="600" href="${request.contextPath}/management/security/role/create"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Role:edit">
			<@dwz.tool_button content="编辑角色" class="shield_go" width="1200" height="600" href="${request.contextPath}/management/security/role/update/{slt_uid}" rel="dlg_page1"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Role:delete">
			<@dwz.tool_button content="删除角色" class="shield_delete" target="ajaxTodo" href="${request.contextPath}/management/security/role/delete/{slt_uid}" title="确认要删除该角色?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
				<th width="200">角色名称</th>
				<th>所属系统</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
			<#list roles as item>
			
			<tr target="slt_uid" rel="${item.id!}">
				<td >
				  <@shiro.hasPermission name="Role:view">
				   <a class="shield_go" width="1200" href="${request.contextPath}/management/security/roleUser/userlist/${item.id!''}" target="dialog"  mask="true">
				           ${item.name!}
				   </a>
				  </@shiro.hasPermission>
				</td>
				<td>
				   <#if item.module??>
				      ${item.module.name}
				   <#else>
				     ${item.sysnames!''}
				   </#if>
				   
				</td>
				<td>
				    ${item.description!''}
				</td>
			</tr>
			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>
