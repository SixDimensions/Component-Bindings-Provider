/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;

/**
 * Initializes the Random Children component, generating and randomizing the
 * list of children
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.ci.impl.RandomChildrenComponentInitializer", label = "Random Children Component Initalizer", description = "Initializes the Random Children component, generating and randomizing the list of children")
@Properties({ @Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = { "ext/bootstrap/components/general/randomchildren" }) })
public class RandomChildrenComponentInitializer implements ComponentInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables,
	 * javax.script.Bindings)
	 */
	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		ValueMap properties = variables.getProperties();

		// load the max number of children
		int max = Integer.MAX_VALUE;
		if (properties.containsKey("max")) {
			max = properties.get("max", Integer.class);
		}
		bindings.put("max", max - 1);

		// locate the parent page
		Page page = variables.getCurrentPage();
		if (properties.containsKey("parent")) {
			page = variables.getPageManager().getContainingPage(
					properties.get("parent", String.class));
		}

		// load and randomize the children
		List<Page> children = new ArrayList<Page>();
		Iterator<Page> childrenIt = page.listChildren(new PageFilter());
		while (childrenIt.hasNext()) {
			children.add(childrenIt.next());
		}
		Collections.shuffle(children);
		bindings.put("children", children);
	}

}
