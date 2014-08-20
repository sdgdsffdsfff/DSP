<@dwz.pagerForm action="${request.contextPath}/management/security/role/list" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/security/role/list">
	<@dwz.label_input2 content="角色名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="Role:view">
			<@dwz.tool_button content="查看角色" class="magnifier" width="530" height="600" href="${request.contextPath}/management/security/role/view/{slt_uid}"/>
		</@shiro.hasPermission>		
		<@shiro.hasPermission name="Role:save">
			<@dwz.tool_button content="添加角色" class="shield_add" width="530" height="600" href="${request.contextPath}/management/security/role/create"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Role:edit">
			<@dwz.tool_button content="编辑角色" class="shield_go" width="530" height="600" href="${request.contextPath}/management/security/role/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Role:delete">
			<@dwz.tool_button content="删除角色" class="shield_delete" target="ajaxTodo" href="${request.contextPath}/management/security/role/delete/{slt_uid}" title="确认要删除该角色?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
					<th width="20%">角色名称</th>
					<th width="20%">登录名称</th>
					<th width="20%">真实名字</th>
					<th width="20%">所在组织</th>
					<th width="20%">优先级（数值越小，优先级越高）</th>
			</tr>
		</thead>
		<tbody>
			<#list roles as item>
			<tr target="slt_uid" rel="${item.id!}">
				<td>${item.role.name!''}</td>
					<td>${item.user.username!''}</td>
					<td>${item.user.realname!''}</td>
					<td>${item.user.organization.name!''}</td>
					<td>
						<div style="float: left;line-height: 21px;margin: 0px 10px;width: 30px;">${item.priority!''}</div>
						
						<@dwz.button title="删除关联" id="submit_${item.id}" class="deleteUserRole"/>
					   
				</td>
			</tr>
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>
