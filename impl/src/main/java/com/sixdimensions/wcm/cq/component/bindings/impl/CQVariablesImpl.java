/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.component.bindings.impl;

import javax.jcr.Session;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingBindings;

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
import com.sixdimensions.wcm.cq.component.bindings.CQVariables;

/**
 * Extends the Sling Bindings to make available all of the objects the
 * &lt;cq:defineObjects /&gt; tag adds as well as the sling objects.
 * 
 * @author dklco
 * @see org.apache.sling.api.scripting.SlingBindings
 */
public class CQVariablesImpl extends SlingBindings implements CQVariables {

	/**
	 * The Serialization UID
	 */
	private static final long serialVersionUID = 1379082884069511903L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#
	 * getComponentContext()
	 */
	public ComponentContext getComponentContext() {
		return this.get(COMPONENT_CONTEXT, ComponentContext.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getComponent()
	 */
	public Component getComponent() {
		return this.get(COMPONENT, Component.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getCurrentDesign
	 * ()
	 */
	public Design getCurrentDesign() {
		return this.get(CURRENT_DESIGN, Design.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getCurrentPage
	 * ()
	 */
	public Page getCurrentPage() {
		return this.get(CURRENT_PAGE, Page.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getCurrentStyle
	 * ()
	 */
	public Style getCurrentStyle() {
		return this.get(CURRENT_STYLE, Style.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getDesigner()
	 */
	public Designer getDesigner() {
		return this.get(DESIGNER, Designer.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getEditContext
	 * ()
	 */
	public EditContext getEditContext() {
		return this.get(EDIT_CONTEXT, EditContext.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getPageManager
	 * ()
	 */
	public PageManager getPageManager() {
		return this.get(PAGE_MANAGER, PageManager.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getPageProperties
	 * ()
	 */
	public InheritanceValueMap getPageProperties() {
		return this.get(PAGE_PROPERTIES, InheritanceValueMap.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getProperties
	 * ()
	 */
	public ValueMap getProperties() {
		return this.get(PROPERTIES, ValueMap.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getResourceDesign
	 * ()
	 */
	public Design getResourceDesign() {
		return this.get(RESOURCE_DESIGN, Design.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getResourcePage
	 * ()
	 */
	public Page getResourcePage() {
		return this.get(RESOURCE_PAGE, Page.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getSession()
	 */
	@Override
	public Session getSession() {
		return this.get(SESSION, Session.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables#getXssAPI()
	 */
	@Override
	public XSSAPI getXssAPI() {
		return this.get(XSS_API, XSSAPI.class);
	}

}
