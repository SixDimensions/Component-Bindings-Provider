---
layout: default
title: API
releases: ["0.1.3", "0.1.2", "0.1.1", "0.1.0"]
---

[6D Labs](http://labs.sixdimensions.com) / [{{ site.name }}]({{ site.baseurl }}/) / {{ page.title }}

## Component Bindings Provider API

Download the JavaDoc API for the Component Bindings Provider.

 * [Current](api/current)
 
### Past Releases

{% for release in page.releases %}
 * [{{ release }}]({{ site.baseurl }}/api/com.sixdimensions.wcm.cq.component.bindings.api-{{ release }}-javadoc.jar)
{% endfor %}