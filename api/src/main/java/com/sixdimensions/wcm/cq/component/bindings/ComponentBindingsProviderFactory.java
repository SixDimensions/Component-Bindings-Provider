/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.component.bindings;

import java.util.List;

/**
 * A factory for retrieving component bindings provider.
 * 
 * @author dklco
 */
public interface ComponentBindingsProviderFactory {

	/**
	 * Get the component bindings providers based on the specified resource
	 * type.
	 * 
	 * @param resourceType
	 *            the current resource type
	 * @return the component initializers or an empty collection if none is
	 *         specified for the resource type
	 */
	List<ComponentBindingsProvider> getComponentBindingsProviders(
			String resourceType);
}
