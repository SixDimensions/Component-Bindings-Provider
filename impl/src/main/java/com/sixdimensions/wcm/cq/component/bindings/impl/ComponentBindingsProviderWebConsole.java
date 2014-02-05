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
package com.sixdimensions.wcm.cq.component.bindings.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.webconsole.AbstractWebConsolePlugin;
import org.apache.felix.webconsole.WebConsoleConstants;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProviderFactory;

@Component
@Service(value = { Servlet.class })
@Properties({
		@Property(name = WebConsoleConstants.PLUGIN_LABEL, value = ComponentBindingsProviderWebConsole.LABEL),
		@Property(name = WebConsoleConstants.PLUGIN_TITLE, value = ComponentBindingsProviderWebConsole.TITLE) })
public class ComponentBindingsProviderWebConsole extends
		AbstractWebConsolePlugin {

	/**
	 * This console's label aka url
	 */
	public static final String LABEL = "componentbindingsprovider";

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(ComponentBindingsProviderWebConsole.class);

	/**
	 * The Serialization UID for this class
	 */
	private static final long serialVersionUID = -4099861429225408171L;

	/**
	 * The title for this web console
	 */
	public static final String TITLE = "Component Bindings Provider";

	/**
	 * A reference to the ComponentBindingsProviderFactory
	 */
	@Reference
	ComponentBindingsProviderFactory factory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.felix.webconsole.AbstractWebConsolePlugin#getCssReferences()
	 */
	@Override
	protected String[] getCssReferences() {
		return new String[] { "/res/ui/bundles.css" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.felix.webconsole.AbstractWebConsolePlugin#getLabel()
	 */
	@Override
	public String getLabel() {
		return LABEL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.felix.webconsole.AbstractWebConsolePlugin#getTitle()
	 */
	@Override
	public String getTitle() {
		return TITLE;
	}

	/**
	 * Loads the properties from the specified Service Reference into a map.
	 * 
	 * @param reference
	 *            the reference from which to load the properties
	 * @return the properties
	 */
	private Map<String, String> loadProperties(ServiceReference reference) {
		log.trace("loadProperties");
		Map<String, String> properties = new HashMap<String, String>();

		properties.put("id", OsgiUtil.toString(
				reference.getProperty(Constants.SERVICE_ID), ""));
		properties.put("class", OsgiUtil.toString(
				reference.getProperty(Constants.SERVICE_PID), ""));
		properties.put("description", OsgiUtil.toString(
				reference.getProperty(Constants.SERVICE_DESCRIPTION), ""));
		properties.put(
				"vendor",
				OsgiUtil.toString(
						reference.getProperty(Constants.SERVICE_VENDOR), ""));
		properties
				.put("resourceTypes",
						Arrays.toString(OsgiUtil.toStringArray(
								reference
										.getProperty(ComponentBindingsProvider.RESOURCE_TYPE_PROP),
								new String[0])));
		properties.put("priority", OsgiUtil.toString(
				reference.getProperty(ComponentBindingsProvider.PRIORITY), ""));
		properties.put("bundle_id",
				String.valueOf(reference.getBundle().getBundleId()));
		properties.put("bundle_name", reference.getBundle().getSymbolicName());
		log.debug("Loaded properties {}", properties);
		return properties;
	}

	/**
	 * Loads the template with the specified name from the classloader and uses
	 * it to templatize the properties using Apache Commons Lang's
	 * StrSubstitutor and writes it to the response.
	 * 
	 * @param res
	 *            the response to write to
	 * @param template
	 *            the template file to load from the classpath
	 * @param properties
	 *            the properties to templatize
	 * @throws IOException
	 */
	private void renderBlock(HttpServletResponse res, String templateName,
			Map<String, String> properties) throws IOException {
		InputStream is = null;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	String template = null;
    	try{
    		is = getClass().getClassLoader().getResourceAsStream(templateName);
			if(is != null){		
				IOUtils.copy(is, baos);
				template = baos.toString();
			} else {
				throw new IOException("Unable to load template "+templateName);
			}
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(baos);
		}
		StrSubstitutor sub = new StrSubstitutor(properties);
		res.getWriter().write(sub.replace(template));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.felix.webconsole.AbstractWebConsolePlugin#renderContent(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderContent(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		log.trace("renderContent");

		Map<String,String> pageProps = new HashMap<String,String>();
		pageProps.put("message", "");
		pageProps.put("searchAllChecked", "");
		pageProps.put("resourceType", "");
		
		ComponentBindingsProviderFactoryImpl factoryImpl = (ComponentBindingsProviderFactoryImpl) factory;

		if ("true".equals(req.getParameter("reloadCache"))) {
			factoryImpl.reloadCache();
			pageProps.put("message", "Cache Reloaded");
		}
		List<ServiceReference> bindingProviders = new ArrayList<ServiceReference>();
		String resourceType = req.getParameter("resourceType");
		if (!StringUtils.isEmpty(resourceType)) {
			log.debug("Searching for resource type: {}", resourceType);
			pageProps.put("resourceType", resourceType);
			bindingProviders = factoryImpl.getReferences(req
					.getParameter("resourceType"));
		} else if ("true".equals(req.getParameter("searchAll"))) {
			pageProps.put("searchAllChecked", "checked");
			log.debug("Searching for all binding providers");
			for (String rt : factoryImpl.getLoadedResourceTypes()) {
				log.debug("Searching for resource type: {}", rt);
				for (ServiceReference reference : factoryImpl.getReferences(rt)) {
					if (!bindingProviders.contains(reference)) {
						bindingProviders.add(reference);
					}
				}
			}
		}
		
		renderBlock(res, "header.html", pageProps);
		renderBlock(res, "search.html", pageProps);
		
		for (int i = 0; i < bindingProviders.size(); i++) {
			Map<String, String> properties = loadProperties(bindingProviders
					.get(i));
			properties.put("state", (i + 1) % 2 == 0 ? "even" : "odd");
			renderBlock(res, "result.html", properties);
		}
		res.getWriter().write("</tbody></table>");
	}
}