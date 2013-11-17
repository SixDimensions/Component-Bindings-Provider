/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci;

import java.util.Collection;

/**
 * A factory for retrieving component initializers.
 * 
 * @author dklco
 */
public interface ComponentInitializerFactory {

	/**
	 * Get the appropriate component initializer based on the specified resource
	 * type.
	 * 
	 * @param resourceType
	 *            the current resource type
	 * @return the component initializers or an empty collection if none is
	 *         specified for the resource type
	 */
	Collection<ComponentInitializer> getComponentInitializer(String resourceType);
}
