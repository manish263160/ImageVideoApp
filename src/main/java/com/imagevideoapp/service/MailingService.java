package com.imagevideoapp.service;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface MailingService {
	 boolean sendMail(final String from, final String[] to, final String[] cc, final String subject, final String msg);
   	 public void send(SimpleMailMessage msg, Map<Object, Object> hTemplateVariables,String templateFileName);

}
