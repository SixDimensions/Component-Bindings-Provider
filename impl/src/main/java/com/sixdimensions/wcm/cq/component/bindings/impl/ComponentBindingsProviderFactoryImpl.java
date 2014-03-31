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
import java.util.List;
import java.util.Set;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProviderFactory;

/**
 * An implementation of the ComponentBindingsProviderFactory.
 * 
 * @author dklco
 */
@Component(name = "com.sixdimensions.wcm.cq.component.bindings.impl.ComponentBindingsProviderFactory", label = "Component Bindings Provider Factory", description = "Invoked by the ext:init taglib to retrieve the component initalizer for the specified resource.", immediate = true)
@Service(value = ComponentBindingsProviderFactory.class)
public class ComponentBindingsProviderFactoryImpl implements
		ComponentBindingsProviderFactory {

	/**
	 * The SLF4J Logger
	 */
	public static final Logger log = LoggerFactory
			.getLogger(ComponentBindingsProviderFactoryImpl.class);

	/**
	 * The current bundle context.
	 */
	private BundleContext bundleContext;

	/**
	 * The cache of Component Bindings Providers
	 */
	private ComponentBindingsProviderCache cache = new ComponentBindingsProviderCache();

	/**
	 * The listener for checking for new component binding provider
	 * registrations
	 */
	private ServiceListener sl;

	/**
	 * Executed when the service is activated.
	 * 
	 * @param context
	 *            the service's context
	 * @throws InvalidSyntaxException
	 */
	protected void activate(final ComponentContext context)
			throws InvalidSyntaxException {
		log.info("activate");

		bundleContext = context.getBundleContext();

		sl = new ServiceListener() {
			public void serviceChanged(ServiceEvent event) {
				if (event.getType() == ServiceEvent.UNREGISTERING) {
					cache.unregisterComponentBindingsProvider(event
							.getServiceReference());
				} else if (event.getType() == ServiceEvent.REGISTERED) {
					cache.registerComponentBindingsProvider(event
							.getServiceReference());
				}
			}
		};
		bundleContext.addServiceListener(sl, "(" + Constants.OBJECTCLASS + "="
				+ ComponentBindingsProvider.class.getName() + ")");
		reloadCache();
		log.info("Activation successful");
	}

	/**
	 * Executed when the service is deactivated.
	 * 
	 * @param context
	 *            the service's context
	 */
	protected void deactivate(ComponentContext context) {
		log.info("deactivate");

		bundleContext = context.getBundleContext();
		bundleContext.removeServiceListener(sl);

		log.info("Deactivate successful");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProviderFactory
	 * #getComponentBindingsProviders(java.lang.String)
	 */
	@Override
	public List<ComponentBindingsProvider> getComponentBindingsProviders(
			String resourceType) {
		List<ComponentBindingsProvider> cis = new ArrayList<ComponentBindingsProvider>();
		for (ServiceReference reference : getReferences(resourceType)) {
			ComponentBindingsProvider ci = ComponentBindingsProvider.class
					.cast(bundleContext.getService(reference));
			cis.add(ci);
		}

		if (cis.size() != 0 && log.isDebugEnabled()) {
			log.debug(
					"Found {} Component Bindings Providers Services for resource type {}",
					cis.size(), resourceType);
		}

		return cis;
	}

	protected List<ServiceReference> getReferences(String resourceType) {
		return cache.getReferences(resourceType);
	}

	/**
	 * Retrieves a set of all of the loaded resource types.
	 * 
	 * @return the loaded resource types
	 */
	protected Set<String> getLoadedResourceTypes() {
		log.debug("Getting resources from {}", cache);
		return this.cache.keySet();
	}

	/**
	 * Reloads the cache of Component Binding Providers
	 */
	protected void reloadCache() {
		log.info("reloadCache");
		cache.clear();
		try {
			ServiceReference[] references = bundleContext
					.getAllServiceReferences(
							ComponentBindingsProvider.class.getCanonicalName(),
							null);
			if (references != null) {
				for (ServiceReference reference : references) {
					cache.registerComponentBindingsProvider(reference);
				}
			}

		} catch (Exception e) {
			log.error(
					"Exception reloading cache of component binding providers",
					e);
		}
	}

}
