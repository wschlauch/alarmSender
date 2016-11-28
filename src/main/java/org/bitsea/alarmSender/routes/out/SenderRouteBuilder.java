package org.bitsea.alarmSender.routes.out;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;
import org.apache.camel.spi.DataFormat;

import ca.uhn.hl7v2.HL7Exception;


public class SenderRouteBuilder extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		DataFormat hl7 = new HL7DataFormat();

		Predicate p21 = header("CamelHL7MessageType").isEqualTo("ORU");
		Predicate p22 = header("CamelHL7TriggerEvent").isEqualTo("R01");
		Predicate isORU = PredicateBuilder.and(p21, p22);
		
		Predicate isACK = header("CamelHL7MessageType").isEqualTo("ACK");
		
		from("file:src/data?fileName=alarmexamples.txt")
		.setExchangePattern(ExchangePattern.InOnly)
		.split().tokenize("\r\n").streaming()//.delay(5000)//.unmarshal(hl7)
		.doTry()
			.choice()
				.when(isORU).marshal().hl7(false)
					.to("mina2://udp://127.0.0.1:" + System.getProperty("port")	+ "?sync=false&codec=#hl7codec")
					.endChoice()
				.otherwise().marshal().hl7(false)
					.to("mina2://tcp://127.0.0.1:22400?sync=true&codec=#hl7codec")
			.end()
			.unmarshal().hl7(false)
			.choice()
				.when(isACK)
				.endChoice()
			.otherwise()
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						System.out.println("This was a failed message delivery:\n" + exchange.getIn().getBody(String.class));
					}
				})
			.end()
			.endDoTry()
		.doCatch(HL7Exception.class)
			.to("mina2://udp://127.0.0.1:" + System.getProperty("port")	+ "?sync=false&codec=#hl7codec")
		.endDoTry()
		.end();
	}
	
	
}
