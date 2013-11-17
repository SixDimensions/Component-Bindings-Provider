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
import org.apache.sling.api.resource.ValueMap;

import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;

/**
 * Component initializer for the Share component. Since the combobox ignores
 * values, this class strips out the text and saves the values as the binding
 * "networks"
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.impl.ShareComponentInitializer", label = "Share Component Initalizer", description = "Initalizes the share component")
@Properties({
		@Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = "ext/bootstrap/components/social/share"),
		@Property(name = ComponentInitializer.PRIORITY, intValue = Integer.MIN_VALUE) })
public class ShareComponentInitializer implements ComponentInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables,
	 * javax.script.Bindings)
	 */
	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		ValueMap sharedDesign = (ValueMap) bindings.get("sharedDesign");
		String[] networks = sharedDesign.get("networks", new String[0]);
		for (int i = 0; i < networks.length; i++) {
			networks[i] = networks[i].substring(networks[i].indexOf("(") + 1,
					networks[i].length() - 1);
		}
		bindings.put("networks", networks);
	}

}
