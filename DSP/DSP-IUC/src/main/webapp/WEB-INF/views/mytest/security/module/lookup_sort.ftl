 
<@dwz.layout_content>
<@dwz.layout_form_content layoutH="58">
  <div class="accordionContent">
	<ul class="tree">
		<#list sort as child>
	    	<li><a href="javascript:" onclick="$.bringBack({id:'${child.id}', name:'${child.name}'})">${child.name}</a></li>
		</#list>
	 </ul>
 </div>
	</@dwz.layout_form_content>
	<@dwz.form_bar closeTitle="关闭"/>	
</@dwz.layout_content>
