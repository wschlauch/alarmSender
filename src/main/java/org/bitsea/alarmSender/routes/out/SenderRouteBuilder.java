package org.bitsea.alarmSender.routes.out;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;
import org.apache.camel.spi.DataFormat;


public class SenderRouteBuilder extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		DataFormat hl7 = new HL7DataFormat();
		
		from("file:src/data?fileName=alarmexamples.txt")
		.split().tokenize("\r\n").streaming().delay(400)
		.marshal(hl7)
		.setExchangePattern(ExchangePattern.InOut)
		.to("mina2://tcp://127.0.0.1:" + System.getProperty("port")	+ "?sync=true&codec=#hl7codec") 		
		.end();
				
	}
	
	
}
