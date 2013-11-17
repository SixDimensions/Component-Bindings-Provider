/*
 * Copyright 2013 - Six Dimensions
 * All Rights Reserved 
 */
package com.sixdimensions.wcm.cq.bootstrap.services.ci.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Doctype;
import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.foundation.Image;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables;
import com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer;

/**
 * Initializes the textimage component.
 * 
 * @author dklco
 */
@Service(value = ComponentInitializer.class)
@Component(name = "com.sixdimensions.wcm.cq.bootstrap.services.impl.TextImageComponentInitializer", label = "TextImage Component Initalizer", description = "Initalizes the sidebar")
@Properties({ @Property(name = ComponentInitializer.RESOURCE_TYPE_PROP, value = {
		"ext/bootstrap/components/general/textimage",
		"ext/bootstrap/components/general/image" }) })
@SuppressWarnings("deprecation")
// I have to use this deprecated API because of the Image class
public class TextImageComponentInitializer implements ComponentInitializer {

	private static final Logger log = LoggerFactory
			.getLogger(TextImageComponentInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sixdimensions.wcm.cq.bootstrap.services.ci.ComponentInitializer#
	 * initialize(com.sixdimensions.wcm.cq.bootstrap.services.ci.CQVariables,
	 * javax.script.Bindings)
	 */
	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		Image image = new Image(variables.getResource(), "image");
		if (image.hasContent()
				|| WCMMode.fromRequest(variables.getRequest()) == WCMMode.EDIT) {
			image.loadStyleData(variables.getCurrentStyle());
			// add design information if not default (i.e. for reference paras)
			if (!variables.getCurrentDesign().equals(
					variables.getResourceDesign())) {
				image.setSuffix(variables.getCurrentDesign().getId());
			}
			// drop target css class = dd prefix + name of the drop target in
			// the edit config
			// image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
			image.setSelector(".img");
			image.setDoctype(Doctype.fromRequest(variables.getRequest()));

			// div around image for additional formatting
			String align = variables.getProperties().get("imagePosition", "");
			if ("right".equals(align)) {
				image.addCssClass("position-right");
			} else if ("left".equals(align)) {
				image.addCssClass("position-left");
			}
			if (variables.getProperties().get("responsive", false)) {
				image.addCssClass("img-responsive");
			}
			Resource imgResource = variables.getResource().getChild("image");
			if (imgResource != null) {
				ValueMap imgProps = imgResource.adaptTo(ValueMap.class);
				String orginalSrc = imgProps.get("fileReference", String.class);
				String src = orginalSrc;
				RequestPathInfo requestPathInfo = variables.getRequest()
						.getRequestPathInfo();
				String[] selectors = requestPathInfo.getSelectors();
				String suffix = requestPathInfo.getSuffix();
				if (src != null) {

					Resource actualResource = variables.getResource()
							.getResourceResolver().getResource(src);
					String rendition = variables.getProperties().get(
							"rendition", "original");
					if (rendition == "original") {
						rendition = "original.oimg"
								+ orginalSrc.substring(
										orginalSrc.lastIndexOf('.'))
										.toLowerCase();
					}
					String fileName = actualResource != null ? actualResource
							.getName() : "";

					Resource renditionRetinaResource = null;
					Resource renditionAppResource = null;

					boolean isRetina = false;
					String rendtionPath = "jcr:content/renditions/";
					if (Arrays.asList(selectors).contains("app")
							&& actualResource != null) {
						StringBuilder strBuilder = new StringBuilder(fileName);
						strBuilder.insert(fileName.lastIndexOf("."), "-app");
						String appRendtionPath = strBuilder.toString();
						renditionAppResource = actualResource
								.getChild(rendtionPath + appRendtionPath);
						if (suffix != null && suffix.contains("retina")) {
							strBuilder = new StringBuilder(fileName);
							strBuilder.insert(fileName.lastIndexOf("."),
									"-retina");
							String retinaRendtionPath = strBuilder.toString();
							renditionRetinaResource = actualResource
									.getChild(rendtionPath + retinaRendtionPath);
							if (renditionRetinaResource != null) {
								src += "/" + rendtionPath + retinaRendtionPath;
								isRetina = true;
							}
						}
						if (renditionAppResource != null
								&& renditionRetinaResource == null)
							src += "/" + rendtionPath + appRendtionPath;

					}
					if (rendition != null && rendition.trim().length() != 0
							&& renditionRetinaResource == null
							&& renditionAppResource == null) {
						src += "/" + rendtionPath + rendition;
					}

					String imageDataPath = src + "/jcr:content";
					if (orginalSrc.equals(src)) {
						imageDataPath = imageDataPath
								+ "/renditions/original/jcr:content";
					}

					try {
						Resource imageRes = variables.getResource()
								.getResourceResolver()
								.getResource(imageDataPath);
						ValueMap map = imageRes.adaptTo(ValueMap.class);
						ImageInputStream iis = ImageIO
								.createImageInputStream(map.get("jcr:data",
										InputStream.class));
						Iterator<ImageReader> readers = ImageIO
								.getImageReaders(iis);
						if (readers.hasNext()) {
							// pick the first available ImageReader
							ImageReader reader = readers.next();
							// attach source to the reader
							reader.setInput(iis, true);
							if (isRetina) {
								image.addAttribute("width", Integer
										.toString(reader.getWidth(0) / 2));
								image.addAttribute("height", Integer
										.toString(reader.getHeight(0) / 2));
							} else {
								image.addAttribute("width",
										Integer.toString(reader.getWidth(0)));
								image.addAttribute("height",
										Integer.toString(reader.getHeight(0)));
							}
						}
					} catch (Exception e) {
						log.warn("Exception reading image attributes", e);
					}

					image.setSrc(src);
				}
			}
		}
		StringWriter writer = new StringWriter();
		try {
			image.draw(writer);
			bindings.put("image", writer.toString());
		} catch (IOException e) {
			log.warn("Exception writing image tag to string", e);
		}

	}

}
