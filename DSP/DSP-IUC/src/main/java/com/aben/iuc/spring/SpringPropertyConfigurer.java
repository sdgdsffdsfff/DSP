package com.aben.iuc.spring;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
 

public class SpringPropertyConfigurer extends PropertyPlaceholderConfigurer {
	Log log = LogFactory.getLog(SpringPropertyConfigurer.class);
	public void setLocations(Resource[] locations) {
		Map<String, Resource> resourceMap = new HashMap<String, Resource>();
		for (Resource loc : locations) {
			File file = null;
			try {
				file = loc.getFile();
			} catch (IOException e) {
				log.error("load properties error",e);
			}
			String fileName = file.getName();
			Resource source = resourceMap.get(fileName);
			if (source != null) {
				if (file.getPath().indexOf("classes") > 0) {
					continue;
				}
			}
			resourceMap.put(fileName, loc);
		}
		
		Resource[] newResouce=resourceMap.values().toArray(new Resource[resourceMap.size()]);
		if (log.isDebugEnabled()){
			log.debug("resouce is : "+resourceMap);
		}
		super.setLocations(newResouce);
	}
}
