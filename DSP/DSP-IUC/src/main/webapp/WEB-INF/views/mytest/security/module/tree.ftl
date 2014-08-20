<@dwz.layout_content>
	<@dwz.tabs>
		<@dwz.tabs_content>
			<div>
				<div layoutH="0" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				<#if type=='i'>
				<@dwz.tree object=module target="ajax" class="tree collapse" href="${request.contextPath}/management/security/module/list" rel="jbsxBox2module" isRootexpand="true"/>
				<#else>
				<@dwz.tree object=module target="ajax" class="tree collapse" href="${request.contextPath}/management/security/module/list" rel="ojbsxBox2module" isRootexpand="true"/>
				</#if>
				</div>
				<#if type=='i'>
					<div layoutH="0" id="jbsxBox2module" class="unitBox" style="margin-left:246px;">
				</div>
				<#else>
				  <div layoutH="0" id="ojbsxBox2module" class="unitBox" style="margin-left:246px;">
				</div>
				</#if>

			</div>				
		</@dwz.tabs_content>
	</@dwz.tabs>	
</@dwz.layout_content>