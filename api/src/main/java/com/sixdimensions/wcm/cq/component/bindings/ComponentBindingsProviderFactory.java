/*
 * Copyright 2013 - Six Dimensions
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
