package org.bitsea.alarmSender;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class ProcessStream {

	public void process(Exchange exchange)  {
		CamelContext context = new DefaultCamelContext();
		ProducerTemplate pdtmplt = exchange.getContext().createProducerTemplate();
		System.out.println(exchange.getIn().getBody());
		try {
			context.start();
			
			Object answer = pdtmplt.sendBody("mina2:tcp://127.0.0.1:8000?sync=true&codec=#hl7codec", ExchangePattern.InOut, exchange);
			System.out.println("answer. " + answer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
