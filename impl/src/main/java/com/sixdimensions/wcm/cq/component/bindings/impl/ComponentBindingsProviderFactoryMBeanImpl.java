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
package com.sixdimensions.wcm.cq.component.bindings.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.NotCompliantMBeanException;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

import com.adobe.granite.jmx.annotation.AnnotatedStandardMBean;
import com.adobe.granite.jmx.annotation.Description;
import com.adobe.granite.jmx.annotation.Name;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProviderFactory;
import com.sixdimensions.wcm.cq.component.bindings.jmx.ComponentBindingsProviderFactoryMBean;

/**
 * MBean Implementation for exposing the ComponentBindingsProvider functionality
 * over JMX.
 * 
 * @author dklco
 */
@Component(immediate = true)
@Property(name = "jmx.objectname", value = "com.sixdimensions.wcm.cq.component.bindings.jmx:type=ComponentBindingsProviderFactory MBean")
@Service
public class ComponentBindingsProviderFactoryMBeanImpl extends
		AnnotatedStandardMBean implements ComponentBindingsProviderFactoryMBean {

	/**
	 * A reference to the ComponentBindingsProviderFactory service
	 */
	@Reference
	private ComponentBindingsProviderFactory componentBindingsProviderFactory;

	/**
	 * Constructs a new ComponentBindingsProviderFactoryMBeanImpl
	 * 
	 * @throws NotCompliantMBeanException
	 */
	public ComponentBindingsProviderFactoryMBeanImpl()
			throws NotCompliantMBeanException {
		super(ComponentBindingsProviderFactoryMBean.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.component.bindings.jmx.
	 * ComponentBindingsProviderFactoryMBean#reloadCache()
	 */
	@Override
	@Description("Reloads the cache of Component Bindings Providers")
	public void reloadCache() {
		((ComponentBindingsProviderFactoryImpl) componentBindingsProviderFactory)
				.reloadCache();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.component.bindings.jmx.
	 * ComponentBindingsProviderFactoryMBean#getReferences(java.lang.String)
	 */
	@Override
	@Description("Gets all of the service references for the specified resource type")
	public TabularData getReferences(@Name("ResourceType") String resourceType)
			throws OpenDataException {
		List<ServiceReference> references = ((ComponentBindingsProviderFactoryImpl) componentBindingsProviderFactory)
				.getReferences(resourceType);

		String[] itemNames = { Constants.SERVICE_PID,
				ComponentBindingsProvider.PRIORITY,
				ComponentBindingsProvider.RESOURCE_TYPE_PROP };
		String[] itemDescriptions = { "The Service ID",
				"The Priority on which the binding service will be called",
				"The resource types this service will bind to" };
		OpenType<?>[] itemTypes = { SimpleType.STRING, SimpleType.STRING,
				SimpleType.STRING };
		CompositeType snapshotType = new CompositeType("references",
				"References", itemNames, itemDescriptions, itemTypes);
		TabularType quoteTableType = new TabularType("references",
				"References", snapshotType,
				new String[] { Constants.SERVICE_PID });
		TabularData td = new TabularDataSupport(quoteTableType);
		for (ServiceReference reference : references) {
			Map<String, Object> data = new HashMap<String, Object>();
			for (String itemName : itemNames) {
				if (reference.getProperty(itemName) instanceof String[]) {
					data.put(itemName, Arrays.toString((String[]) reference
							.getProperty(itemName)));
				} else {
					data.put(itemName, reference.getProperty(itemName));
				}
			}
			td.put(new CompositeDataSupport(snapshotType, data));
		}
		return td;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.component.bindings.jmx.
	 * ComponentBindingsProviderFactoryMBean#getLoadedResourceTypes()
	 */
	@Override
	@Description("Gets all of th resource types which have bound Component Bindings Providers")
	public String[] getLoadedResourceTypes() {
		Set<String> types = ((ComponentBindingsProviderFactoryImpl) componentBindingsProviderFactory)
				.getLoadedResourceTypes();
		return types.toArray(new String[types.size()]);
	}

}
