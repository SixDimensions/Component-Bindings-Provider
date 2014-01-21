---
layout: default
title: Installation
---

[6D Labs](http://labs.sixdimensions.com) / [{{ site.name }}]({{ site.baseurl }}/) / {{ page.title }}

## Installing Component Bindings Provider

The Component Bindings Provider code is divided into two bundles:

 * `com.sixdimenions.wcm.cq.component.bindings.api` &mdash; The API for the Component 
     Bindings Provider.  This should be imported in your projects and installed into CQ.
 * `com.sixidimenions.wcm.cq.component.bindings.impl` &mdash; The implementation of the 
     required services to run the Component Bindings Provider, this must be installed in 
    CQ, but is not required for custom projects.
 
## Supported Versions

The Component Bindings Provider is supported in AEM (Adobe CQ) versions 5.5 - 5.6.1.  It 
has not been validated against newer versions and due to it's inclusion of the Granite XSS 
API, it is not compatible with CQ 5.4 or older.  If you need a version compatible with 
older versions of CQ, please create an issue and I will create a specific release.
 
### Installing the Bundles
 
The latest version of each bundle can be downloaded from the 
[Project Releases](https://github.com/SixDimensions/Component-Bindings-Provider/releases/) 
page and then installed through the 
[OSGi Console](https://sling.apache.org/documentation/tutorials-how-tos/installing-and-upgrading-bundles.html) 
or via a [CRX Package](http://helpx.adobe.com/experience-manager/kb/SlingHowToInstallBundlesViaJCRInstall.html).

### Maven

Additionally, the Component Bindings Provider API is available through the Maven Central 
Repository.  To add the Component Bindings Provider API into your project, add the 
following dependency into your POM:

	<dependency>
		<groupId>com.sixdimensions.wcm.cq</groupId>
        	<artifactId>com.sixdimensions.wcm.cq.component.bindings.api</artifactId>
        	<version>0.1.1</version>
    	</dependency>
