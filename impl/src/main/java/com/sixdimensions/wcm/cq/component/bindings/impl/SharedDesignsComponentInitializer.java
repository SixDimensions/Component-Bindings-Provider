/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.WCMMode;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;

/**
 * Initializes components which utilized a shared design.
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.impl.SharedDesignsComponentInitializer", label = "Shared Designs Component Initalizer", description = "Initalizes the designs for components which should share designs no matter of placement")
@Properties({ @Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = {
		"ext/bootstrap/components/social/disqus",
		"ext/bootstrap/components/social/disquscount",
		"ext/bootstrap/components/social/share" }) })
public class SharedDesignsComponentInitializer implements ComponentInitializer {

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(SharedDesignsComponentInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQBindings,
	 * javax.servlet.jsp.PageContext)
	 */
	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		log.trace("initialize");

		String sharedDesignPath = variables.getCurrentDesign().getPath()
				+ "/jcr:content/" + variables.getResource().getResourceType();
		if (WCMMode.fromRequest(variables.getRequest()) == WCMMode.DESIGN) {
			log.debug("Setting content path to {}", sharedDesignPath);
			variables.getEditContext().setContentPath(sharedDesignPath);
		}

		Resource disqusDesignRsrc = variables.getResource()
				.getResourceResolver().getResource(sharedDesignPath);
		if (disqusDesignRsrc != null) {
			bindings.put("sharedDesign",
					disqusDesignRsrc.adaptTo(ValueMap.class));
		}

	}
}
