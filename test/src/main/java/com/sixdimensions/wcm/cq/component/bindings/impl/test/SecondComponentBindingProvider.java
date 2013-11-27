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
package com.sixdimensions.wcm.cq.component.bindings.impl.test;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import com.sixdimensions.wcm.cq.component.bindings.CQVariables;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;

/**
 * A test binding for testing the ordering.
 * 
 * @author dklco
 */
@Component
@Service
@Properties({
		@Property(name = ComponentBindingsProvider.RESOURCE_TYPE_PROP, value = "test/bindings/order"),
		@Property(name = ComponentBindingsProvider.PRIORITY, intValue = -1) })
public class SecondComponentBindingProvider implements
		ComponentBindingsProvider {

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
		bindings.put("message", "Second");
	}
}
