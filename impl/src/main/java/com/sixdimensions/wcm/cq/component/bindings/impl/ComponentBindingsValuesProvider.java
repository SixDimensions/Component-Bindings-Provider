/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.component.bindings.impl;

import java.util.Collection;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.component.bindings.CQVariables;
import com.sixdimensions.wcm.cq.component.bindings.CQVariablesService;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProviderFactory;

/**
 * Initializes all components using the ComponentBindingsProviderFactory to
 * retrieve the correct component bindings provider based on the resource type
 * for the resource.
 * 
 * @author dklco
 * 
 */
@Component
@Service
public class ComponentBindingsValuesProvider implements BindingsValuesProvider {

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(ComponentBindingsValuesProvider.class);

	/**
	 * A reference to the component bindings providers factory.
	 */
	@Reference
	private ComponentBindingsProviderFactory cif;

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
			Collection<ComponentBindingsProvider> cis = cif
					.getComponentBindingsProviders(resourceType);
			if (cis != null && cis.size() > 0) {
				CQVariables binding = bs.getVariables(bindings);
				for (ComponentBindingsProvider ci : cis) {
					try {
						log.debug("Invoking component initializer {}", ci);
						ci.addBindings(binding, bindings);
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
