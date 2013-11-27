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
	 * A constant for the resource type property. Each service instance must
	 * have this property set. This property will be used to retrieve the
	 * component initializer.
	 */
	String RESOURCE_TYPE_PROP = "resourceType";

	/**
	 * A constant for the priority property. This property is to optionally set
	 * the integer value for the priority for the service in the execution
	 * chain. Lower numbers will be called after higher numbers. By default,
	 * services will be assigned the value 0.
	 */
	String PRIORITY = "priority";

	/**
	 * Add objects to the bindings object.
	 * 
	 * @param variables
	 *            the variables for CQ
	 * @param bindings
	 *            the bindings for invoking the script
	 */
	void addBindings(CQVariables variables, Bindings bindings);
}
