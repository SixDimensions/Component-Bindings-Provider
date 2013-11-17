/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci;

import java.io.PrintWriter;
import java.io.Reader;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.slf4j.Logger;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentContext;
import com.day.cq.wcm.api.components.EditContext;
import com.day.cq.wcm.api.designer.Design;
import com.day.cq.wcm.api.designer.Designer;
import com.day.cq.wcm.api.designer.Style;

/**
 * Extends the Sling Bindings to make available all of the objects the
 * &lt;cq:defineObjects /&gt; tag adds as well as the sling objects.
 * 
 * @author dklco
 * @see org.apache.sling.api.scripting.SlingBindings
 */
public interface CQVariables {

	/**
	 * The key for the current component context object of the request.
	 */
	public static final String COMPONENT_CONTEXT = "componentContext";

	/**
	 * The key for the current AEM component object of the current resource
	 */
	public static final String COMPONENT = "component";

	/**
	 * They key for the current design object of the current page
	 */
	public static final String CURRENT_DESIGN = "currentDesign";

	/**
	 * The key for the current AEM WCM page object
	 */
	public static final String CURRENT_PAGE = "currentPage";

	/**
	 * The key for the current style object of the current cell
	 */
	public static final String CURRENT_STYLE = "currentStyle";

	/**
	 * The key for the designer object used to access design information
	 */
	public static final String DESIGNER = "designer";

	/**
	 * The key for the edit context object of the AEM component
	 */
	public static final String EDIT_CONTEXT = "editContext";

	/**
	 * The key for the page manager object for page level operations
	 */
	public static final String PAGE_MANAGER = "pageManager";

	/**
	 * The key for the page properties object of the current page
	 */
	public static final String PAGE_PROPERTIES = "pageProperties";

	/**
	 * The key for the properties object of the current resource
	 */
	public static final String PROPERTIES = "properties";

	/**
	 * The key for the design object of the resource page
	 */
	public static final String RESOURCE_DESIGN = "resourceDesign";

	/**
	 * The key for the page containing the current resource
	 */
	public static final String RESOURCE_PAGE = "resourcePage";

	/**
	 * The key for the Sling helper
	 */
	public static final String SLING = "sling";

	/**
	 * The key for the JCR Session
	 */
	public static final String SESSION = "session";

	/**
	 * The key for the XSS API
	 */
	public static final String XSS_API = "xssAPI";

	/**
	 * Gets the current CQ component class.
	 * 
	 * @return the current component
	 */
	Component getComponent();

	/**
	 * Gets the current component context.
	 * 
	 * @return the current component context
	 */
	ComponentContext getComponentContext();

	/**
	 * Gets the current design.
	 * 
	 * @return the current design
	 */
	Design getCurrentDesign();

	/**
	 * Gets the current page. This is the page which is currently rendering the
	 * resource which may be different than the page under which the resource is
	 * physically located.
	 * 
	 * @return the current page
	 */
	Page getCurrentPage();

	/**
	 * Gets the style of the current cell
	 * 
	 * @return the style
	 */
	Style getCurrentStyle();

	/**
	 * Gets the current designer.
	 * 
	 * @return the current designer
	 */
	Designer getDesigner();

	/**
	 * Gets the edit context for the current component.
	 * 
	 * @return the edit context
	 */
	EditContext getEditContext();

	/**
	 * Returns the {@link #FLUSH} property if not <code>null</code> and a
	 * <code>boolean</code>. Otherwise <code>false</code> is returned.
	 */
	boolean getFlush();

	/**
	 * Returns the {@link #LOG} property if not <code>null</code> and a
	 * <code>org.slf4j.Logger</code> instance. Otherwise <code>null</code> is
	 * returned.
	 */
	Logger getLog();

	/**
	 * Returns the {@link #OUT} property if not <code>null</code> and a
	 * <code>PrintWriter</code> instance. Otherwise <code>null</code> is
	 * returned.
	 */
	PrintWriter getOut();

	/**
	 * Gets the page manager.
	 * 
	 * @return the page manager
	 */
	PageManager getPageManager();

	/**
	 * Gets the properties for the current page.
	 * 
	 * @return the properties for the current page
	 */
	InheritanceValueMap getPageProperties();

	/**
	 * Gets the properties of the current resource.
	 * 
	 * @return the properties
	 */
	ValueMap getProperties();

	/**
	 * Returns the {@link #READER} property if not <code>null</code> and a
	 * <code>Reader</code> instance. Otherwise <code>null</code> is returned.
	 */
	Reader getReader();

	/**
	 * Returns the {@link #REQUEST} property if not <code>null</code> and a
	 * <code>SlingHttpServletRequest</code> instance. Otherwise
	 * <code>null</code> is returned.
	 */
	SlingHttpServletRequest getRequest();

	/**
	 * Returns the {@link #RESOURCE} property if not <code>null</code> and a
	 * <code>Resource</code> instance. Otherwise <code>null</code> is returned.
	 */
	Resource getResource();

	/**
	 * Returns the design for the current resource, this may be different than
	 * the design for the page.
	 * 
	 * @return the design for the current resource
	 */
	Design getResourceDesign();

	/**
	 * Gets the page containing the current resource. This may be different than
	 * getPage if the resource is included from another page.
	 * 
	 * @return the page containing the current resource
	 */
	Page getResourcePage();

	/**
	 * Returns the {@link #RESPONSE} property if not <code>null</code> and a
	 * <code>SlingHttpServletResponse</code> instance. Otherwise
	 * <code>null</code> is returned.
	 */
	SlingHttpServletResponse getResponse();

	/**
	 * Returns the {@link #SLING} property if not <code>null</code> and a
	 * <code>SlingScriptHelper</code> instance. Otherwise <code>null</code> is
	 * returned.
	 */
	SlingScriptHelper getSling();

	/**
	 * Returns the JCR session.
	 * 
	 * @returnt the jcr session
	 */
	Session getSession();

	/**
	 * Returns the XSS API for the current request.
	 * 
	 * @return the XSS API instance
	 */
	XSSAPI getXssAPI();

}
