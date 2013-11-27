/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved 
 */
package com.sixdimensions.wcm.cq.component.bindings.impl.test;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import com.sixdimensions.wcm.cq.component.bindings.CQVariables;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;

/**
 * Basic binding test.
 * 
 * @author dklco
 */
@Component
@Service
@Properties({ @Property(name = ComponentBindingsProvider.RESOURCE_TYPE_PROP, value = "test/bindings/basic") })
public class BasicComponentBindingProvider implements ComponentBindingsProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider
	 * #addBindings(com.sixdimensions.wcm.cq.component.bindings.CQVariables,
	 * javax.script.Bindings)
	 */
	@Override
	public void addBindings(CQVariables variables, Bindings bindings) {
		bindings.put("message", "Hello World");
	}
}
