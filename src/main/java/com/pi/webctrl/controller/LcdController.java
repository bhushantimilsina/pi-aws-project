package com.pi.webctrl.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.pi.webctrl.model.ClientInboundMessage;
import com.pi.webctrl.model.ClientOutboundMessage;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.I2CLcdDisplay;

@Controller
public class LcdController {
	private static final Logger log = LoggerFactory.getLogger(LcdController.class);

	public final static int LCD_ROWS = 2;
	public final static int LCD_COLUMNS = 16;
	public final static int LCD_BITS = 4;

	@Autowired
	ClientInboundMessage toClientMessage;

	@MessageMapping("/client-outbound-message")
	@SendTo("/topic/client-inbound-message")
	public ClientInboundMessage lcdMessage(final ClientOutboundMessage message) throws NumberFormatException, Exception {

		toClientMessage.setTimeStamp(DateFormatUtils.format(new Date(), "YYYY/MM/dd @ HH:mm:ss"));
		toClientMessage.setUser(message.getUser());
		toClientMessage.setMessage(message.getText());
		log.info("Sending message to LCD: [{}]", message.getText());
		
		// LCD Display with I2C PCF8574 chip
		I2CLcdDisplay lcd = new I2CLcdDisplay(4, 20, 1, Integer.parseInt("27", 16), 3, 0, 1, 2, 7, 6, 5, 4);

		// switch on the light
		lcd.setBacklight(true);
		
		lcd.writeln(0, new SimpleDateFormat("M/d/yy @ HH:mm:ss").format(new Date()), LCDTextAlignment.ALIGN_LEFT);
		lcd.writeln(1, message.getText(), LCDTextAlignment.ALIGN_LEFT);
		
		log.info("Message sent to LCD");
		
		return toClientMessage;
	}
}
