package com.sixdimensions.wcm.cq.component.bindings.impl;

import org.apache.sling.testing.tools.sling.SlingClient;
import org.apache.sling.testing.tools.sling.SlingTestBase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentBindingsProviderIT extends SlingTestBase {

	/**
	 * The SlingClient can be used to interact with the repository when it is
	 * started. By retrieving the information for the Server URL, username and
	 * password, the Sling instance will be automatically started.
	 */
	private SlingClient slingClient = new CQClient(this.getServerBaseUrl(),
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

		if (slingClient.exists("/apps/test/bindings")) {
			slingClient.delete("/apps/test/bindings");
		}
		if (slingClient.exists("/content/test/bindings")) {
			slingClient.delete("/content/test/bindings");
		}

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

		log.info("Initialization successful");
	}

	/**
	 * The actual test, will be executed once the Sling instance is started and
	 * the setup is complete.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSample() throws Exception {

		log.info("Asserting Basic Binding Provider works");
		getRequestExecutor()
				.execute(
						getRequestBuilder().buildGetRequest(
								"/content/test/bindings/basic.html")
								.withCredentials("admin", "admin"))
				.assertStatus(200).assertContentContains("Hello World");

	}
}