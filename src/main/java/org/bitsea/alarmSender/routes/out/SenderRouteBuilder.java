package org.bitsea.alarmSender.routes.out;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;
import org.apache.camel.spi.DataFormat;


public class SenderRouteBuilder extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		DataFormat hl7 = new HL7DataFormat();
		
		from("file:src/data?fileName=alarmexamplesv2.txt")
		.setExchangePattern(ExchangePattern.InOnly)
		.split().tokenize("\r\n").streaming().delay(400)
		.marshal(hl7)
		.to("mina2://tcp://127.0.0.1:" + System.getProperty("port")	+ "?sync=false&codec=#hl7codec")
		//.unmarshal(hl7)
		.end();
	}
	
	
}
