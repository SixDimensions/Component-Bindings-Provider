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

import javax.script.Bindings;
import javax.servlet.jsp.PageContext;

/**
 * A service for retrieving CQ Variables.
 * 
 * @author dklco
 */
public interface CQVariablesService {

	/**
	 * Retrieve the CQVariables from the page context.
	 * 
	 * @param pageContext
	 *            the current page context
	 * @return the CQ Variables
	 */
	CQVariables getVariables(PageContext pageContext);

	/**
	 * Retrieve the CQVariables from the Scripting bindings.
	 * 
	 * @param bindings
	 *            the current script bindings
	 * @return the CQ Variables
	 */
	CQVariables getVariables(Bindings bindings);
}
