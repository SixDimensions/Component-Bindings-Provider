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
	String COMPONENT_CONTEXT = "componentContext";

	/**
	 * The key for the current AEM component object of the current resource.
	 */
	String COMPONENT = "component";

	/**
	 * They key for the current design object of the current page.
	 */
	String CURRENT_DESIGN = "currentDesign";

	/**
	 * The key for the current AEM WCM page object.
	 */
	String CURRENT_PAGE = "currentPage";

	/**
	 * The key for the current style object of the current cell.
	 */
	String CURRENT_STYLE = "currentStyle";

	/**
	 * The key for the designer object used to access design information.
	 */
	String DESIGNER = "designer";

	/**
	 * The key for the edit context object of the AEM component.
	 */
	String EDIT_CONTEXT = "editContext";

	/**
	 * The key for the page manager object for page level operations.
	 */
	String PAGE_MANAGER = "pageManager";

	/**
	 * The key for the page properties object of the current page.
	 */
	String PAGE_PROPERTIES = "pageProperties";

	/**
	 * The key for the properties object of the current resource.
	 */
	String PROPERTIES = "properties";

	/**
	 * The key for the design object of the resource page.
	 */
	String RESOURCE_DESIGN = "resourceDesign";

	/**
	 * The key for the page containing the current resource.
	 */
	String RESOURCE_PAGE = "resourcePage";

	/**
	 * The key for the Sling helper.
	 */
	String SLING = "sling";

	/**
	 * The key for the JCR Session.
	 */
	String SESSION = "session";

	/**
	 * The key for the XSS API.
	 */
	String XSS_API = "xssAPI";

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
	 * Gets the style of the current cell.
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
	 * Returns the flush property if not <code>null</code> and a
	 * <code>boolean</code>. Otherwise <code>false</code> is returned.
	 * 
	 * @return the flush flag
	 */
	boolean getFlush();

	/**
	 * Returns the log property if not <code>null</code> and a
	 * <code>org.slf4j.Logger</code> instance. Otherwise <code>null</code> is
	 * returned.
	 * 
	 * @return the SLF4J Logger
	 */
	Logger getLog();

	/**
	 * Returns the out property if not <code>null</code> and a
	 * <code>PrintWriter</code> instance. Otherwise <code>null</code> is
	 * returned.
	 * 
	 * @return the output writer
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
	 * Returns the reader property if not <code>null</code> and a
	 * <code>Reader</code> instance. Otherwise <code>null</code> is returned.
	 * 
	 * @return the input reader
	 */
	Reader getReader();

	/**
	 * Returns the request property if not <code>null</code> and a
	 * <code>SlingHttpServletRequest</code> instance. Otherwise
	 * <code>null</code> is returned.
	 * 
	 * @return the current Sling Request
	 */
	SlingHttpServletRequest getRequest();

	/**
	 * Returns the resource property if not <code>null</code> and a
	 * <code>Resource</code> instance. Otherwise <code>null</code> is returned.
	 * 
	 * @return the current resource
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
	 * Returns the response property if not <code>null</code> and a
	 * <code>SlingHttpServletResponse</code> instance. Otherwise
	 * <code>null</code> is returned.
	 * 
	 * @return the current Sling Servlet Response
	 */
	SlingHttpServletResponse getResponse();

	/**
	 * Returns the sling property if not <code>null</code> and a
	 * <code>SlingScriptHelper</code> instance. Otherwise <code>null</code> is
	 * returned.
	 * 
	 * @return the Sling helper
	 */
	SlingScriptHelper getSling();

	/**
	 * Returns the JCR session.
	 * 
	 * @return the jcr session
	 */
	Session getSession();

	/**
	 * Returns the XSS API for the current request.
	 * 
	 * @return the XSS API instance
	 */
	XSSAPI getXssAPI();

}
