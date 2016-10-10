package org.bitsea.alarmSender.routes.out;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;
import org.apache.camel.spi.DataFormat;
import org.bitsea.alarmSender.ProcessStream;


public class SenderRouteBuilder extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		DataFormat hl7 = new HL7DataFormat();
		
		from("file:src/data?fileName=alarmexamples.txt")
		.split().tokenize("\r\n")//.marshal(hl7)
		.setExchangePattern(ExchangePattern.InOut)
		.process(new Processor() {
			public void process(Exchange exch) {
				System.out.println("test");
			}
		}).to("mina2://tcp://127.0.0.1:8000?sync=true&codec=#hl7codec") 
		//.bean(new ProcessStream(), "process")
		
		.end();
		
		from("mina2://tcp://127.0.0.1:8000?sync=true&codec=#hl7codec").unmarshal(hl7).process(new Processor() {
			public void process(Exchange exch) {
				System.out.println(exch.getIn().getBody(String.class));
			}
		}).end();
	}
	
	
}
