package com.aws.codestar.projecttemplates.services;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.aws.codestar.projecttemplates.model.ClientInboundMessage;
import com.aws.codestar.projecttemplates.model.ClientOutboundCommand;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Controller
public class LightController {
	private static final Logger log = LoggerFactory.getLogger(LightController.class);
	@Autowired
	ClientInboundMessage toClientMessage;

	@MessageMapping("/client-outbound-command")
	@SendTo("/topic/client-inbound-messages")
	public ClientInboundMessage lightCommand(final ClientOutboundCommand command) {
		// create GPIO controller
		final GpioController gpio = GpioFactory.getInstance();

		// provision Broadcom GPIO pin #02 as an output pin and turn on
		final GpioPinDigitalOutput green = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);// GPIO

		// set shutdown state for this pin
		green.setShutdownOptions(true, PinState.LOW);

		log.info("Command received from client: {}", command);
		if (command.getStatus().equalsIgnoreCase("ON")) {
			log.info("Setting LED ON ...");
			green.high();
		} else if (command.getStatus().equalsIgnoreCase("OFF")) {
			log.info("Setting LED OFF ...");
			green.low();
		}

		log.info("WebSocket Controller - /client-outbound-command triggered ...");
		toClientMessage.setTimeStamp(DateFormatUtils.format(new Date(), "YYYY/MM/dd @ HH:mm:ss"));
		toClientMessage.setUser(command.getUser());
		toClientMessage.setMessage("Light is turned ".concat(command.getStatus()));
		log.info("Sent response: {}", toClientMessage);
		return toClientMessage;
	}
}
