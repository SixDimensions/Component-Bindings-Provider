/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved 
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializerFactory;

/**
 * An implementation of the ComponentInitializerFactory.
 * 
 * @author dklco
 */
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.ci.impl.ComponentInitializerFactoryImpl", label = "Component Initalizer Factory", description = "Invoked by the ext:init taglib to retrieve the component initalizer for the specified resource.")
@Service(value = ComponentInitializerFactory.class)
public class ComponentInitializerFactoryImpl implements
		ComponentInitializerFactory {

	/**
	 * The current bundle context.
	 */
	private BundleContext bundleContext;

	/**
	 * The SLF4J Logger
	 */
	public static final Logger log = LoggerFactory
			.getLogger(ComponentInitializerFactoryImpl.class);

	/**
	 * Executed when the service is activated.
	 * 
	 * @param context
	 *            the service's context
	 */
	protected void activate(ComponentContext context) {
		log.info("activate");

		bundleContext = context.getBundleContext();

		log.info("Activation successful");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializerFactory
	 * #getComponentInitializer(java.lang.String)
	 */
	@Override
	public Collection<ComponentInitializer> getComponentInitializer(
			String resourceType) {
		Collection<ComponentInitializer> cis = new ArrayList<ComponentInitializer>();

		try {
			ServiceReference[] references = bundleContext.getServiceReferences(
					ComponentInitializer.class.getCanonicalName(), "("
							+ ComponentInitializer.RESOURCE_TYPE_PROP + "="
							+ resourceType + ")");
			if (references != null) {
				List<ServiceReference> orderedReferences = Arrays
						.asList(references);
				Collections.sort(orderedReferences,
						new Comparator<ServiceReference>() {
							@Override
							public int compare(ServiceReference reference0,
									ServiceReference reference1) {
								Integer priority0 = OsgiUtil.toInteger(
										reference0.getProperty(ComponentInitializer.PRIORITY),
										0);
								Integer priority1 = OsgiUtil.toInteger(
										reference1.getProperty(ComponentInitializer.PRIORITY),
										0);
								return -1 * priority0.compareTo(priority1);
							}

						});

				for (ServiceReference reference : orderedReferences) {
					ComponentInitializer ci = ComponentInitializer.class
							.cast(bundleContext.getService(reference));
					cis.add(ci);
				}
			}
		} catch (InvalidSyntaxException e) {
			log.error(
					"Unable to find Component Initializer due to invalid syntax "
							+ resourceType, e);
		}

		if (cis.size() != 0) {
			log.debug("Found {} initializer classes for resource type {}",
					cis.size(), resourceType);
		}

		return cis;
	}

}
