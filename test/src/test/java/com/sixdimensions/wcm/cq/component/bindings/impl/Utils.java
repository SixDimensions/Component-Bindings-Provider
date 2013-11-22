package com.sixdimensions.wcm.cq.component.bindings.impl;

import java.io.IOException;

import org.apache.sling.testing.tools.sling.SlingClient;

public class Utils {

	public static final void createFolders(SlingClient slingClient, String path)
			throws IOException {
		String currentPath = "";
		for (String part : path.split("/")) {
			if (part == null || part.trim().length() == 0) {
				continue;
			}
			currentPath += "/" + part;
			if (!slingClient.exists(currentPath)) {
				slingClient.mkdir(currentPath);
			}
		}
	}
}
