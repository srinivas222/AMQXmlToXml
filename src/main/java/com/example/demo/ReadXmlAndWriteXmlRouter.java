package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.springframework.stereotype.Component;

import com.example.domain.Customer;
@Component
public class ReadXmlAndWriteXmlRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		Map<String,String> reference = new HashMap<String,String>();
		reference.put("customer", Customer.class.getName());
		XStreamDataFormat dataformat = new XStreamDataFormat();
		dataformat.setAliases(reference);
		dataformat.setPermissions(Customer.class.getName());
		 
		from("activemq:queue:readxml")
		.log("Reading message is ${body}")
		.unmarshal(dataformat)
		.log("After Unmarshall the data is ${body}")
		.marshal(dataformat)
		.log("After Marshal the object is ${body}")
		.to("activemq:queue:outputXml");

	}

}
