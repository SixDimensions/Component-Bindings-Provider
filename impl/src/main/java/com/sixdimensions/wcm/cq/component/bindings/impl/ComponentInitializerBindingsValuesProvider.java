/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import java.util.Collection;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariablesService;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializerFactory;

/**
 * Initializes all components using the ComponentInitializerFactory to retrieve
 * the correct component initializer based on the resource type for the resource
 * to
 * 
 * @author dklco
 * 
 */
@Component
@Service
@Properties({ @Property(name = Constants.SERVICE_VENDOR, value = "Six Dimensions") })
public class ComponentInitializerBindingsValuesProvider implements
		BindingsValuesProvider {

	private static final Logger log = LoggerFactory
			.getLogger(ComponentInitializerBindingsValuesProvider.class);

	/**
	 * A reference to the component initializer factory.
	 */
	@Reference
	private ComponentInitializerFactory cif;

	/**
	 * A reference to the bindings service
	 */
	@Reference
	private CQVariablesService bs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.sling.scripting.api.BindingsValuesProvider#addBindings(javax
	 * .script.Bindings)
	 */
	@Override
	public void addBindings(Bindings bindings) {
		try {
			SlingHttpServletRequest request = (SlingHttpServletRequest) bindings
					.get("request");
			String resourceType = request.getResource().getResourceType();
			Collection<ComponentInitializer> cis = cif
					.getComponentInitializer(resourceType);
			if (cis != null && cis.size() > 0) {
				CQVariables binding = bs.getVariables(bindings);
				for (ComponentInitializer ci : cis) {
					try {
						log.debug("Invoking component initializer {}", ci);
						ci.initialize(binding, bindings);
					} catch (Exception e) {
						log.error("Exception invoking component initializer "
								+ ci, e);
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception invoking component initializers", e);
		}
	}
}
