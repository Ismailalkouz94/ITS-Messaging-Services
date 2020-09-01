package com.its.messageservices.services;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class SendEmail {

    private final static String key="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";

    public static Boolean send(String to, String subject,String contentStr) throws IOException {
        Email fromEmail = new Email("ismailkouz2015@gmail.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", contentStr);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        return send(mail);
    }

    private static Boolean send(Mail mail) throws IOException {
        SendGrid sg = new SendGrid(key);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            return response.getStatusCode() == 200 || response.getStatusCode() == 202;
        } catch (IOException ex) {
            throw ex;
        }
    }


}