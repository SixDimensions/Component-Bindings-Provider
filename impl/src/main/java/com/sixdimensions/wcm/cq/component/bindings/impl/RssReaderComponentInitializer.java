/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import java.net.URL;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * Initalizes the RSS reader component, loading the RSS feed for the reader to
 * display.
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.ci.impl.RssReaderComponentInitializer", label = "RSS Reader Component Initalizer", description = "Initalizes the RSS reader component, loading the RSS feed for the reader to display")
@Properties({ @Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = { "ext/bootstrap/components/general/rssreader" }) })
public class RssReaderComponentInitializer implements ComponentInitializer {

	private static final Logger log = LoggerFactory
			.getLogger(RssReaderComponentInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables,
	 * javax.script.Bindings)
	 */
	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		ValueMap properties = variables.getProperties();

		String feedUrl = properties.get("url", String.class);
		int end = properties.get("show", 5) - 1;

		SyndFeedInput input = new SyndFeedInput();
		try {
			SyndFeed feed = input.build(new XmlReader(new URL(feedUrl)));
			bindings.put("feed", feed);
			bindings.put("end", end);
		} catch (Exception e) {
			log.warn("Exception retrieving feed " + feedUrl, e);
			bindings.put("exception", e);
		}
	}

}
