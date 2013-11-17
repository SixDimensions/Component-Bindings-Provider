/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;

/**
 * Initializes the breadcrumb component.
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.impl.BreadcrumbComponentInitializer", label = "Breadcrumb Component Initalizer", description = "Initalizes the sidebar")
@Properties({ @Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = "ext/bootstrap/components/navigation/cqbreadcrumb") })
public class BreadcrumbComponentInitializer implements ComponentInitializer {

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(BreadcrumbComponentInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables,
	 * javax.script.Bindings)
	 */
	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		log.trace("initialize");

		Page currentPage = variables.getCurrentPage();
		Style currentStyle = variables.getCurrentStyle();

		Map<String, Object> properties = new HashMap<String, Object>();

		// get starting point of trail
		int level = currentStyle.get("absParent", 2);
		int endLevel = currentStyle.get("relParent", 0);

		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		int currentLevel = currentPage.getDepth();
		while (level < currentLevel - endLevel) {
			Page trail = currentPage.getAbsoluteParent((int) level);
			Map<String, String> item = new HashMap<String, String>();
			if (trail == null) {
				break;
			}

			// load the title
			String title = null;
			if (!StringUtils.isEmpty(trail.getNavigationTitle())) {
				title = trail.getNavigationTitle();
			} else if (!StringUtils.isEmpty(trail.getTitle())) {
				title = trail.getTitle();
			} else {
				title = trail.getName();
			}
			item.put("title", title);

			item.put("url", trail.getPath() + ".html");

			items.add(item);

			level++;
		}
		properties.put("items", items);
		bindings.put("breadcrumb", properties);
	}

}
