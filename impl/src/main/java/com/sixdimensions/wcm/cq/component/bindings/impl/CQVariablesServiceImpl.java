/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved 
 */
package com.sixdimensions.wcm.cq.component.bindings.impl;

import java.util.Enumeration;

import javax.jcr.Session;
import javax.script.Bindings;
import javax.servlet.jsp.PageContext;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.ComponentContext;
import com.day.cq.wcm.api.components.EditContext;
import com.day.cq.wcm.api.designer.Design;
import com.day.cq.wcm.api.designer.Designer;
import com.day.cq.wcm.api.designer.Style;
import com.day.cq.wcm.commons.WCMUtils;
import com.sixdimensions.wcm.cq.component.bindings.CQVariables;
import com.sixdimensions.wcm.cq.component.bindings.CQVariablesService;

/**
 * An implementation of the CQVariablessService.
 * 
 * @author dklco
 */
@Component(name = "com.sixdimensions.wcm.cq.component.bindings.impl.CQVariablesServiceImpl", label = "CQ Variables Service", description = "Retrieves the CQ variables")
@Service(value = CQVariablesService.class)
public class CQVariablesServiceImpl implements CQVariablesService {

	/**
	 * The SL4FJ Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(CQVariablesServiceImpl.class);

	/**
	 * The constant for retrieving the design
	 */
	private static final String REQ_ATTR_PREFIX = "com.day.cq.wcm.tags.DefineObjectsTag:design:";

	/**
	 * A reference to the XSS API
	 */
	@Reference
	private XSSAPI xssApi;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariablesService#getVariables
	 * (javax.servlet.jsp.PageContext)
	 */
	@Override
	public CQVariables getVariables(PageContext pageContext) {
		log.trace("getBindings");

		CQVariablesImpl binding = new CQVariablesImpl();

		// Load all of the attributes from the current page context
		Enumeration<?> attrs = pageContext
				.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
		while (attrs.hasMoreElements()) {
			String key = String.class.cast(attrs.nextElement());
			binding.put(key, pageContext.getAttribute(key));
		}

		// Put the request and response
		binding.put(SlingBindings.REQUEST, pageContext.getRequest());
		binding.put(SlingBindings.RESPONSE, pageContext.getResponse());

		if (pageContext.getAttribute("bindings") != null) {
			SlingBindings sb = SlingBindings.class.cast(pageContext
					.getAttribute("bindings"));
			for (String key : sb.keySet()) {
				binding.put(key, sb.get(key));
			}
		}

		return binding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariablesService#getVariables
	 * (javax.script.Bindings)
	 */
	@Override
	public CQVariables getVariables(Bindings bindings) {
		CQVariablesImpl variables = new CQVariablesImpl();

		// Add all of the sling bindings
		SlingHttpServletRequest request = (SlingHttpServletRequest) bindings
				.get("request");
		variables.putAll(bindings);

		// Add all of the CQ stuff
		Resource resource = request.getResource();
		ResourceResolver resolver = request.getResourceResolver();
		XSSAPI xssAPI = xssApi.getRequestSpecificAPI(request);
		ComponentContext componentContext = WCMUtils
				.getComponentContext(request);
		EditContext editContext = componentContext != null ? componentContext
				.getEditContext() : null;
		ValueMap properties = ResourceUtil.getValueMap(resource);
		PageManager pageManager = (PageManager) resolver
				.adaptTo(PageManager.class);
		ValueMap pageProperties = null;
		Page currentPage = null;
		Page resourcePage = null;
		if (pageManager != null) {
			resourcePage = pageManager.getContainingPage(resource);
			currentPage = componentContext != null ? componentContext.getPage()
					: null;
			if (currentPage == null) {
				currentPage = resourcePage;
			}
			if (currentPage == null) {
				pageProperties = null;
			} else {
				pageProperties = new HierarchyNodeInheritanceValueMap(
						currentPage.getContentResource());
			}
		}
		com.day.cq.wcm.api.components.Component component = WCMUtils
				.getComponent(resource);
		Designer designer = (Designer) request.getResourceResolver().adaptTo(
				Designer.class);
		if (designer != null) {
			Design currentDesign;
			if (currentPage == null) {
				currentDesign = null;
			} else {
				String currentDesignKey = REQ_ATTR_PREFIX
						+ currentPage.getPath();
				Object cachedCurrentDesign = request
						.getAttribute(currentDesignKey);
				if (cachedCurrentDesign != null) {
					currentDesign = (Design) cachedCurrentDesign;
				} else {
					currentDesign = designer.getDesign(currentPage);
					request.setAttribute(currentDesignKey, currentDesign);
				}
			}
			Design resourceDesign;
			if (resourcePage == null) {
				resourceDesign = null;
			} else {
				String resourceDesignkey = REQ_ATTR_PREFIX
						+ resourcePage.getPath();
				Object cachedresourceDesign = request
						.getAttribute(resourceDesignkey);
				if (cachedresourceDesign != null) {
					resourceDesign = (Design) cachedresourceDesign;
				} else {
					resourceDesign = designer.getDesign(resourcePage);
					request.setAttribute(resourceDesignkey, resourceDesign);
				}
			}
			Style currentStyle = currentDesign != null
					&& componentContext != null ? currentDesign
					.getStyle(componentContext.getCell()) : null;
			variables.put(CQVariables.DESIGNER, designer);
			variables.put(CQVariables.CURRENT_DESIGN, currentDesign);
			variables.put(CQVariables.RESOURCE_DESIGN, resourceDesign);
			variables.put(CQVariables.CURRENT_STYLE, currentStyle);
		}
		variables.put(CQVariables.XSS_API, xssAPI);
		variables.put(CQVariables.COMPONENT_CONTEXT, componentContext);
		variables.put(CQVariables.EDIT_CONTEXT, editContext);
		variables.put(CQVariables.PROPERTIES, properties);
		variables.put(CQVariables.PAGE_MANAGER, pageManager);
		variables.put(CQVariables.CURRENT_PAGE, currentPage);
		variables.put(CQVariables.RESOURCE_PAGE, resourcePage);
		variables.put(CQVariables.PAGE_PROPERTIES, pageProperties);
		variables.put(CQVariables.COMPONENT, component);
		variables.put(CQVariables.SESSION, resource.getResourceResolver()
				.adaptTo(Session.class));
		return variables;
	}
}
