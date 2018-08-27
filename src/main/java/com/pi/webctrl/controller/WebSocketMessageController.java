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
import com.pi.webctrl.model.ClientOutboundMessage;

@Controller
public class WebSocketMessageController {
	private static final Logger log = LoggerFactory.getLogger(WebSocketMessageController.class);
	
	@Autowired ClientInboundMessage toClientMessage;
	
	@MessageMapping("/client-outbound-messages")
    @SendTo("/topic/client-inbound-messages")
    public ClientInboundMessage inboundMessage(final ClientOutboundMessage message) throws Exception {
        log.info("WebSocket Controller - /client-outbound-messages triggered ...");
		toClientMessage.setTimeStamp(DateFormatUtils.format(new Date(), "YYYY/MM/dd @ HH:mm:ss"));
		toClientMessage.setUser(message.getUser());
		toClientMessage.setMessage(message.getText());
		log.info("Sent response: {}", toClientMessage);
        return toClientMessage;
    }
}