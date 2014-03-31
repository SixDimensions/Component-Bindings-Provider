/*
 * Copyright 2014 - Six Dimensions
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

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Holds the data for an uncaught Exception from a ComponentBindingsProvider
 * execution.
 * 
 * @author dklco
 */
public class ComponentBindingsExceptionData {

	/**
	 * The uncaught exception from executing a ComponentBindingsProvider
	 */
	private final Exception ex;

	/**
	 * The ComponentBindingsProvider which had an uncaught Exception
	 */
	private final ComponentBindingsProvider provider;

	/**
	 * Construct a new ComponentBindingsExceptionData
	 * 
	 * @param provider
	 *            the ComponentBindingsProvider which triggered the exception
	 * @param ex
	 *            the exception which occurred
	 */
	public ComponentBindingsExceptionData(ComponentBindingsProvider provider,
			Exception ex) {
		this.provider = provider;
		this.ex = ex;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		return ex;
	}

	/**
	 * @return the provider
	 */
	public ComponentBindingsProvider getProvider() {
		return provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<strong>Exception invoking " + provider + "</strong><br/>"
				+ ExceptionUtils.getFullStackTrace(ex).replace("\n", "<br/>");
	}
}
