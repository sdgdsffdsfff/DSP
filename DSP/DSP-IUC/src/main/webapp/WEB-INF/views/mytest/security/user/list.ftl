<@dwz.pagerForm action="${request.contextPath}/management/security/user/list" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/security/user/list">
	<@dwz.label_input2 content="登录名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
	<@dwz.label_input2 content="真实名字：">
		<input type="text" name="realname" value="${realname!''}"/>
	</@dwz.label_input2>
	
</@dwz.search_form>

<@dwz.layout_content>
	<@dwz.tool_bar>
			<@dwz.tool_button content="添加用户" class="user_add" rel="lookup2organization_add" width="530" height="330" href="${request.contextPath}/management/security/user/create"/>
		
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="138">
		<thead>
			<tr>
				<th width="100">登录名称</th>
				<th width="100">真实名字</th>
				<th width="200">邮箱地址</th>
				<th width="100">电话</th>
				<th width="150" orderField=organization.name class="${(page.orderField=='organization.name')?string(page.orderDirection,'')}">所在组织</th>
				<th width="150" >角色</th>
				<th width="100" orderField="status" class="${(page.orderField=='status')?string(page.orderDirection,'')}">账户状态</th>
				<th orderField="createTime" class="${(page.orderField=='createTime')?string(page.orderDirection,'')}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<#list users as item>
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.username}</td>
				<td>${item.realname}</td>
				<td>${item.email!''}</td>
				<td>${item.phone!''}</td>
				<td>${item.organization.name}</td>
				<td>
					<#list item.userRoles as ur>
						${ur.role.name }&nbsp;&nbsp;
					</#list>
				</td>
				<td>${(item.status == "enabled")?string("可用","不可用")}</td>
				<td>${item.createTime}</td>
			</tr>			
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>
