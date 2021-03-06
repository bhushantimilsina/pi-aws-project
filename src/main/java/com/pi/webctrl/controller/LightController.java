package com.pi.webctrl.controller;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.pi.webctrl.model.ClientInboundMessage;
import com.pi.webctrl.model.ClientOutboundCommand;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Controller
public class LightController {
	private static final Logger log = LoggerFactory.getLogger(LightController.class);
	
	// This instance needs to be static since it will be associated 
	private static GpioPinDigitalOutput green = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_01);// GPIO;
		
	@Autowired ClientInboundMessage toClientMessage;

	@MessageMapping("/client-outbound-command")
	@SendTo("/topic/client-inbound-message")
	public ClientInboundMessage lightCommand(final ClientOutboundCommand command) throws InterruptedException {
		log.info("WebSocket Controller - /client-outbound-command triggered ...");

		toClientMessage.setTimeStamp(DateFormatUtils.format(new Date(), "YYYY/MM/dd @ HH:mm:ss"));
		toClientMessage.setUser(command.getUser());
		
		// set shutdown state for this pin
		green.setShutdownOptions(true, PinState.LOW);
		
		log.info("Command received from client: {}", command);

		if (command.getStatus().equalsIgnoreCase("ON")) {
			log.info("Setting LED ON ...");
			green.high();
			toClientMessage.setMessage("Light is turned ON");
		} else if (command.getStatus().equalsIgnoreCase("OFF")) {
			log.info("Setting LED OFF ...");
			green.low();
			toClientMessage.setMessage("Light is turned OFF");
		}

		log.info("Sent response: {}", toClientMessage);
		return toClientMessage;
	}
}
