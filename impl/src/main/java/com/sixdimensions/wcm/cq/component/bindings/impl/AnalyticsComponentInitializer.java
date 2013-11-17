/*
ç * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.sixdimensions.wcm.cq.bootstrap.services.analytics.AnalyticsService;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;

/**
 * Initializes the analytics component.
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.impl.AnalyticsComponentInitializer", label = "Analytics Component Initalizer", description = "Initalizes the analytics component")
@Properties({ @Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = "ext/bootstrap/components/general/analytics") })
public class AnalyticsComponentInitializer implements ComponentInitializer {

	/**
	 * A reference to the BootStrap Analytics Service.
	 */
	@Reference
	private AnalyticsService analyticsService;

	/**
	 * A reference to the CQ Externalizer service.
	 */
	@Reference
	private Externalizer externalizer;

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(AnalyticsComponentInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQBindings,
	 * javax.servlet.jsp.PageContext)
	 */
	@Override
	public void initialize(CQVariables binding, Bindings bindings) {
		log.trace("initialize");

		bindings.put("pageName", analyticsService
				.getSiteCatalystPageName(binding.getCurrentPage()));
		bindings.put("siteCategory",
				analyticsService.getPageCategory(binding.getCurrentPage()));

		bindings.put(
				"analyticsJs",
				externalizer.relativeLink(binding.getRequest(), binding
						.getResource().getPath())
						+ ".js");
	}

}
