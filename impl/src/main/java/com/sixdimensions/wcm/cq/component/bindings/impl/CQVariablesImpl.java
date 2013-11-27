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
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getComponentContext
	 * ()
	 */
	public ComponentContext getComponentContext() {
		return this.get(COMPONENT_CONTEXT, ComponentContext.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getComponent()
	 */
	public Component getComponent() {
		return this.get(COMPONENT, Component.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getCurrentDesign
	 * ()
	 */
	public Design getCurrentDesign() {
		return this.get(CURRENT_DESIGN, Design.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getCurrentPage()
	 */
	public Page getCurrentPage() {
		return this.get(CURRENT_PAGE, Page.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getCurrentStyle()
	 */
	public Style getCurrentStyle() {
		return this.get(CURRENT_STYLE, Style.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getDesigner()
	 */
	public Designer getDesigner() {
		return this.get(DESIGNER, Designer.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getEditContext()
	 */
	public EditContext getEditContext() {
		return this.get(EDIT_CONTEXT, EditContext.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getPageManager()
	 */
	public PageManager getPageManager() {
		return this.get(PAGE_MANAGER, PageManager.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getPageProperties
	 * ()
	 */
	public InheritanceValueMap getPageProperties() {
		return this.get(PAGE_PROPERTIES, InheritanceValueMap.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getProperties()
	 */
	public ValueMap getProperties() {
		return this.get(PROPERTIES, ValueMap.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getResourceDesign
	 * ()
	 */
	public Design getResourceDesign() {
		return this.get(RESOURCE_DESIGN, Design.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sixdimensions.wcm.cq.component.bindings.CQVariables#getResourcePage()
	 */
	public Page getResourcePage() {
		return this.get(RESOURCE_PAGE, Page.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.component.bindings.CQVariables#getSession()
	 */
	@Override
	public Session getSession() {
		return this.get(SESSION, Session.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.component.bindings.CQVariables#getXssAPI()
	 */
	@Override
	public XSSAPI getXssAPI() {
		return this.get(XSS_API, XSSAPI.class);
	}

}
