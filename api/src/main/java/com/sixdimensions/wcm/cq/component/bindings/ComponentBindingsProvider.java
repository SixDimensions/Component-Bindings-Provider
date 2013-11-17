/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.component.bindings;

import javax.script.Bindings;

/**
 * A component bindings provider for every script load (including scripts in
 * languages besides JSP). This service should then initialize any variables
 * needed for the component and perform any needed processing.
 * 
 * In JSPs the variables will be available in the context variable 'bindings',
 * eg: <code>${bindings.var}</code>
 * 
 * @author dklco
 */
public interface ComponentBindingsProvider {

	/**
	 * A constant for the resource type property. Each service instance should
	 * have this property set. This property will be used to retrieve the
	 * component initializer.
	 */
	static final String RESOURCE_TYPE_PROP = "resourceType";

	/**
	 * A constant for the priority property. This property is to optionally set
	 * the integer value for the priority for the service in the execution
	 * chain. Lower numbers will be called after higher numbers. By default,
	 * services will be assigned the value 0.
	 */
	static final String PRIORITY = "priority";

	/**
	 * Initialize the component properties.
	 * 
	 * @param variables
	 *            the variables for CQ
	 * @param binding
	 *            the bindings for invoking the script
	 */
	void initialize(CQVariables variables, Bindings bindings);
}
