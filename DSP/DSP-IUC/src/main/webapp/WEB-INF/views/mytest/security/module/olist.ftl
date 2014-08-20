
<@dwz.pagerForm action="${request.contextPath}/management/security/module/olist" page=page>
	<input type="hidden" name="keywords" value="${keywords!''}"/>
</@dwz.pagerForm>

<@dwz.search_form action="${request.contextPath}/management/security/module/olist">
	<@dwz.label_input2 content="模块名称：">
		<input type="text" name="keywords" value="${keywords!''}"/>
	</@dwz.label_input2>
</@dwz.search_form>
<@dwz.layout_content>
	
	<@dwz.tool_bar>
	 <@shiro.hasPermission name="Module:save">
	 	<@dwz.tool_button content="添加系统" class="application_add" rel="lookup2organization_add" width="450" height="250" href="${request.contextPath}/management/security/module/create/${parentModuleId}?dtype=SYS"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Module:edit">
			<@dwz.tool_button content="编辑系统" rel="lookup2organization_edit" class="application_edit" width="450" height="250" href="${request.contextPath}/management/security/module/update/{slt_uid}?dtype=SYS"/>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="Module:delete">
			<@dwz.tool_button content="删除系统" class="application_delete" target="ajaxTodo" callback="reloadRel" href="${request.contextPath}/management/security/module/delete/{slt_uid}?dtype=SYS" title="确认要删除该系统?"/>
		</@shiro.hasPermission>
	</@dwz.tool_bar>
	
	<@dwz.table_common layoutH="142" rel="ojbsxBox2module">
		<thead>
			<tr>
			  <th width="150">系统名称</th>
			 <th width="150">授权名称</th>
			  <th width="150">系统主页地址</th>
			  <th width="150">描述</th>
			</tr>
		</thead>
		<tbody>
			<#list modules as item>
			<tr target="slt_uid" rel="${item.id}">
				<td><a href="${request.contextPath}/management/security/module/list/${item.id}" target="ajax" rel="ojbsxBox2module">${item.name}</a></td>
				<td>${item.sn}</td>
				<td>${item.url}</td>
				<td>${item.description}</td>
			</tr>
			</#list>
		</tbody>
	</@dwz.table_common>
	<!-- 分页 -->
	<@dwz.pagerBar page=page/>
</@dwz.layout_content>