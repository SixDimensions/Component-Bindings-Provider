package com.sixdimensions.wcm.cq.component.bindings;

import javax.script.Bindings;
import javax.servlet.jsp.PageContext;

/**
 * A service for retrieving CQVariables.
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
	 * @param binding
	 *            the current script bindings
	 * @return the CQ Variables
	 */
	CQVariables getVariables(Bindings bindings);
}
