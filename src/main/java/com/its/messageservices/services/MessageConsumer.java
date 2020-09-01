package com.its.messageservices.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@EnableJms
public class MessageConsumer {
    private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination = "email-queue")
    public void listener(Map message) {
        logger.info("Message received {} ", message);
        try {
            StringBuilder content = getEmailIssueDesign(message);

            SendEmail.send(message.get("ISSUE_ASSIGNED_EMAIL").toString(), "ITS - New Issue", content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder getEmailIssueDesign(Map message) {
        StringBuilder content = new StringBuilder();
        content.append("ID: ");
        content.append(message.get("ISSUE_ID"));
        content.append(System.getProperty("line.separator"));
        content.append("From: ");
        content.append(message.get("ISSUE_OWNER"));
        content.append(System.getProperty("line.separator"));
        content.append("Title: ");
        content.append(message.get("ISSUE_TITLE"));
        content.append(System.getProperty("line.separator"));
        content.append("Description: ");
        content.append(message.get("ISSUE_DESC"));
        return content;
    }
}