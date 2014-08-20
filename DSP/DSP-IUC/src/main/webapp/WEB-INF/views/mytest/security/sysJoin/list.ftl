<@dwz.pagerForm action="${request.contextPath}/management/security/join/list" page=page>
<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>
<@dwz.layout_content>
	<@dwz.tool_bar>
		<@shiro.hasPermission name="User:save">
			<@dwz.tool_button content="接入系统" class="user_add" rel="lookup2organization_add" width="400" height="175" href="${request.contextPath}/management/security/join/create/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="User:edit">
			<@dwz.tool_button content="修改接入IP" class="user_edit" rel="lookup2organization_edit" width="400" height="175" href="${request.contextPath}/management/security/join/update/{slt_uid}"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="User:delete">
			<@dwz.tool_button content="删除接入" class="user_delete" target="ajaxTodo" href="${request.contextPath}/management/security/join/delete/{slt_uid}" title="确认要删除该用户?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="76">
		<thead>
			<tr>
				<th width="150">系统名称</th>
				<th width="270">APPKEY</th>
				<th width="270">APPID</th>
				<th width="400">接入IP</th>
				<th orderField="createTime" class="${(page.orderField=='createTime')?string(page.orderDirection,'')}">接入时间</th>
			</tr>
		</thead>
		<tbody>
			<#list join as item>
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.moudle.name}</td>
				<td>${item.appkey}</td>
				<td>${item.appid}</td>
				<td>${item.joinip}</td>
				<td>${item.createTime}</td>
			</tr>			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>
