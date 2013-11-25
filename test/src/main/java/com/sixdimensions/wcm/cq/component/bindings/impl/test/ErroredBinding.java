package com.sixdimensions.wcm.cq.component.bindings.impl.test;

import javax.script.Bindings;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import com.sixdimensions.wcm.cq.component.bindings.CQVariables;
import com.sixdimensions.wcm.cq.component.bindings.ComponentBindingsProvider;

@Component
@Service
@Properties({ @Property(name = ComponentBindingsProvider.RESOURCE_TYPE_PROP, value = "test/bindings/errored") })
public class ErroredBinding implements ComponentBindingsProvider {

	@Override
	public void initialize(CQVariables variables, Bindings bindings) {
		bindings.put("message", "Something");
		if (1 == 1) {
			throw new RuntimeException();
		}
		bindings.put("error", 0);
	}

}
