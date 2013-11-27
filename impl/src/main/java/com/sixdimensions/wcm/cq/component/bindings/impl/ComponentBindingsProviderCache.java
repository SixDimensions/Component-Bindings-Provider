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
package com.sixdimensions.wcm.cq.component.bindings.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;

/**
 * A cache of the ComponentBindingsProvider Services.
 * 
 * @author dklco
 */
public class ComponentBindingsProviderCache extends
		HashMap<String, Set<ServiceReference>> {

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(ComponentBindingsProviderCache.class);

	/**
	 * The Serialization UID
	 */
	private static final long serialVersionUID = -2441168551436910375L;

	/**
	 * Gets the ComponentBindingProvider references for the specified resource
	 * type.
	 * 
	 * @param resourceType
	 *            the resource type for which to retrieve the references
	 * @return the references for the resource type
	 */
	public List<ServiceReference> getReferences(String resourceType) {
		List<ServiceReference> references = new ArrayList<ServiceReference>();
		if (containsKey(resourceType)) {
			references.addAll(get(resourceType));
			Collections.sort(references, new Comparator<ServiceReference>() {
				@Override
				public int compare(ServiceReference r0, ServiceReference r1) {
					Integer p0 = OsgiUtil.toInteger(
							r0.getProperty(ComponentBindingsProvider.PRIORITY),
							0);
					Integer p1 = OsgiUtil.toInteger(
							r1.getProperty(ComponentBindingsProvider.PRIORITY),
							0);
					return -1 * p0.compareTo(p1);
				}

			});
		}
		return references;
	}

	/**
	 * Registers the ComponentBindingsProvider specified by the
	 * ServiceReference.
	 * 
	 * @param reference
	 *            the reference to the ComponentBindingsProvider service
	 */
	public void registerComponentBindingsProvider(ServiceReference reference) {
		log.info("registerComponentBindingsProvider");
		log.info("Registering Component Bindings Provider {}",
				reference.getProperty(Constants.SERVICE_ID));
		String[] resourceTypes = OsgiUtil.toStringArray(reference
				.getProperty(ComponentBindingsProvider.RESOURCE_TYPE_PROP),
				new String[0]);
		for (String resourceType : resourceTypes) {
			if (!this.containsKey(resourceType)) {
				put(resourceType, new HashSet<ServiceReference>());
			}
			log.debug("Adding to resource type {}", resourceType);
			get(resourceType).add(reference);
		}
	}

	/**
	 * UnRegisters the ComponentBindingsProvider specified by the
	 * ServiceReference.
	 * 
	 * @param reference
	 *            the reference to the ComponentBindingsProvider service
	 */
	public void unregisterComponentBindingsProvider(ServiceReference reference) {
		log.info("unregisterComponentBindingsProvider");
		String[] resourceTypes = OsgiUtil.toStringArray(reference
				.getProperty(ComponentBindingsProvider.RESOURCE_TYPE_PROP),
				new String[0]);
		for (String resourceType : resourceTypes) {
			if (!this.containsKey(resourceType)) {
				continue;
			}
			get(resourceType).remove(reference);
		}
	}
}
