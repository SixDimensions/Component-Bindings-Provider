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

import org.apache.sling.testing.tools.sling.SlingClient;
import org.apache.sling.testing.tools.sling.SlingTestBase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for the Component Bindings Provider
 * 
 * @author dklco
 */
public class ComponentBindingsProviderIT extends SlingTestBase {

	/**
	 * The SlingClient can be used to interact with the repository when it is
	 * started. By retrieving the information for the Server URL, username and
	 * password, the Sling instance will be automatically started.
	 */
	private SlingClient slingClient = new SlingClient(this.getServerBaseUrl(),
			this.getServerUsername(), this.getServerPassword()) {

	};

	/**
	 * The SLF4J Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(ComponentBindingsProviderIT.class);

	/**
	 * Execute before the actual test, this will be used to setup the test data
	 * 
	 * @throws Exception
	 */
	@Before
	public void init() throws Exception {
		log.info("init");

		if (slingClient.exists("/content/test/bindings")) {
			slingClient.delete("/content/test/bindings");
		}

		log.info("Initialization successful");
	}

	/**
	 * Basic test of the binding providers
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBasic() throws Exception {

		log.info("Creating testing component...");
		Utils.createFolders(slingClient, "/apps/test/bindings/basic");
		slingClient.upload("/apps/test/bindings/basic/basic.jsp",
				ComponentBindingsProviderIT.class.getClassLoader()
						.getResourceAsStream("basic.jsp"), -1, true);
		log.info(getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/apps/test/bindings/basic.3.json")
								.withCredentials("admin", "admin"))
				.assertStatus(200).getContent());

		log.info("Creating testing content...");
		Utils.createFolders(slingClient, "/content/test/bindings");
		slingClient.createNode("/content/test/bindings/basic",
				"jcr:primaryType", "nt:unstructured", "sling:resourceType",
				"test/bindings/basic");
		log.info(getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/basic.json")
								.withCredentials("admin", "admin"))
				.assertStatus(200).getContent());

		log.info("Asserting Basic Binding Provider works");
		getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/basic.html")
								.withCredentials("admin", "admin"))
				.assertStatus(200).assertContentContains("Hello World");

		log.info("All Basic tests successful");
	}

	/**
	 * Test to ensure the component binding providers are retrieved in the
	 * correct order.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOrder() throws Exception {

		log.info("Creating test component");
		Utils.createFolders(slingClient, "/apps/test/bindings/order");
		slingClient.upload("/apps/test/bindings/order/order.jsp",
				ComponentBindingsProviderIT.class.getClassLoader()
						.getResourceAsStream("order.jsp"), -1, true);
		log.info(getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/apps/test/bindings/order.3.json")
								.withCredentials("admin", "admin"))
				.assertStatus(200).getContent());

		log.debug("Creating test content");
		slingClient.createNode("/content/test/bindings/order",
				"jcr:primaryType", "nt:unstructured", "sling:resourceType",
				"test/bindings/order");
		log.info(getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/order.json")
								.withCredentials("admin", "admin"))
				.assertStatus(200).getContent());

		log.info("Asserting Binding Provider ordering works");
		getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/order.html")
								.withCredentials("admin", "admin"))
				.assertStatus(200).assertContentContains("Second");

		log.info("Asserting First Binding Provider is still executing");
		getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/order.html")
								.withCredentials("admin", "admin"))
				.assertStatus(200).assertContentContains("Third");

		log.info("All Order tests successful");
	}

	/**
	 * Test to ensure errors from the component binding providers do not cause
	 * errors in the JSP
	 * 
	 * @throws Exception
	 */
	@Test
	public void testErrorHandling() throws Exception {

		log.info("Creating test component");
		Utils.createFolders(slingClient, "/apps/test/bindings/errored");
		slingClient.upload("/apps/test/bindings/errored/errored.jsp",
				ComponentBindingsProviderIT.class.getClassLoader()
						.getResourceAsStream("errored.jsp"), -1, true);
		log.info(getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/apps/test/bindings/errored.3.json")
								.withCredentials("admin", "admin"))
				.assertStatus(200).getContent());

		log.debug("Creating test content");
		slingClient.createNode("/content/test/bindings/errored",
				"jcr:primaryType", "nt:unstructured", "sling:resourceType",
				"test/bindings/errored");
		log.info(getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/errored.json")
								.withCredentials("admin", "admin"))
				.assertStatus(200).getContent());

		log.info("Asserting error's don't go to the page");
		getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/errored.html")
								.withCredentials("admin", "admin"))
				.assertStatus(200).assertContentContains("Something--");

		log.info("All error handling tests successful");
	}
}