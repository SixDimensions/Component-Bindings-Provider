/*
 * Copyright 2014 - Six Dimensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sixdimensions.wcm.cq.component.bindings.jmx;

import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;

import com.adobe.granite.jmx.annotation.Description;
import com.adobe.granite.jmx.annotation.Name;

/**
 * MBean exposes the current values from the Component Bindings Provided Factory
 * 
 * @author dklco
 */
@Description("MBean exposes the current values from the Component Bindings Provided Factory")
public interface ComponentBindingsProviderFactoryMBean {

	/**
	 * Reloads the cache of Component Bindings Providers
	 */
	@Description("Reloads the cache of Component Bindings Providers")
	void reloadCache();

	/**
	 * Gets all of the service references of the Component Bindings Providers
	 * for the specified resource
	 * 
	 * @param resourceType
	 *            the resource type for which to retrieves the references
	 * @return
	 * @throws OpenDataException
	 */
	@Description("Gets all of the service references of the Component Bindings Providers for the specified resource")
	TabularData getReferences(@Name("ResourceType") String resourceType)
			throws OpenDataException;

	/**
	 * Gets all of the resource types which have bound Component Bindings
	 * Providers
	 * 
	 * @return the loaded resource types
	 */
	@Description("Gets all of the resource types which have bound Component Bindings Providers")
	String[] getLoadedResourceTypes();

}
