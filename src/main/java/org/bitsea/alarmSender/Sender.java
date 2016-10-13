package org.bitsea.alarmSender;

import org.apache.camel.component.hl7.HL7MLLPCodec;
import org.apache.camel.main.Main;
import org.bitsea.alarmSender.routes.out.SenderRouteBuilder;


public class Sender {
	
	private Main main;
	
	public static void main(String[] args) throws Exception {
		Sender app = new Sender();
		final String port = (args.length >= 1 ? args[0] : "8000");
		app.boot(port);
	}
	
	public void boot(String port) throws Exception {
		System.setProperty("port", port);
		main = new Main();
		
		main.bind("hl7codec", new HL7MLLPCodec());
		main.enableHangupSupport();
		main.addRouteBuilder(new SenderRouteBuilder());
		main.run();
	}
	
}