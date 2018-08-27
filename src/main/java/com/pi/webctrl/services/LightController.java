package com.pi.webctrl.services;

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
	private static GpioPinDigitalOutput yellow = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_04);//GPIO 23
	private static GpioPinDigitalOutput blue = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_05);//GPIO 24
	private static GpioPinDigitalOutput red = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_06);//GPIO 25
	
	@Autowired ClientInboundMessage toClientMessage;

	@MessageMapping("/client-outbound-command")
	@SendTo("/topic/client-inbound-messages")
	public ClientInboundMessage lightCommand(final ClientOutboundCommand command) throws InterruptedException {
		
		toClientMessage.setTimeStamp(DateFormatUtils.format(new Date(), "YYYY/MM/dd @ HH:mm:ss"));
		toClientMessage.setUser(command.getUser());
		
		// set shutdown state for this pin
		green.setShutdownOptions(true, PinState.LOW);
		yellow.setShutdownOptions(true, PinState.LOW);
        blue.setShutdownOptions(true, PinState.LOW);
        red.setShutdownOptions(true, PinState.LOW);
        
		log.info("Command received from client: {}", command);

		if (command.getStatus().equalsIgnoreCase("ON")) {
			log.info("Setting LED ON ...");
			green.high();
			toClientMessage.setMessage("Light is turned ON");
		} else if (command.getStatus().equalsIgnoreCase("OFF")) {
			log.info("Setting LED OFF ...");
			green.low();
			toClientMessage.setMessage("Light is turned OFF");
		} else if (command.getStatus().equalsIgnoreCase("BLINK")) {
			log.info("Blink each of the LED; GREEN --> RED");
	        // HIGH for 1 sec
	        // set second argument to 'true' use a blocking call
	        green.pulse(1000, true);
	        yellow.pulse(1000, true);
	        blue.pulse(1000, true);
	        red.pulse(1000, true);
	        
	        log.info("Turn ON each of the LED; GREEN --> RED");
	        green.high();
	        Thread.sleep(1000);
	        yellow.high();
	        Thread.sleep(1000);
	        blue.high();
	        Thread.sleep(1000);
	        red.high();
	        Thread.sleep(1000);
	        
	        log.info("Turn OFF each of the LED; RED --> GREEN");
	        red.low();
	        Thread.sleep(1000);
	        blue.low();
	        Thread.sleep(1000);
	        yellow.low();
	        Thread.sleep(1000);
	        green.low();
	        Thread.sleep(1000);

			toClientMessage.setMessage("Light is blinking");
		}

		log.info("WebSocket Controller - /client-outbound-command triggered ...");

		log.info("Sent response: {}", toClientMessage);
		return toClientMessage;
	}
}
