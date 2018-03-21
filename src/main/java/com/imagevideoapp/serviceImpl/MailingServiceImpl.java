package com.imagevideoapp.serviceImpl;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.imagevideoapp.models.EmailDTO;
import com.imagevideoapp.service.MailingService;

/**
 * @author manishkm
 *
 */
@Service
public class MailingServiceImpl implements MailingService {

	@Autowired
	private JavaMailSender mailSender;
 
	private static final Logger logger = Logger.getLogger(MailingServiceImpl.class);
	
//	@Autowired
//	ApplicationProperties applicationProperties;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	/**
	 * method sends mail using applicationProperties by parameters from,to,cc,subject,msg
	 */
	public boolean sendMail(final String from, final String[] to, final String[] cc, final String subject, final String msg) {
        logger.info("from  "+from+"   to  "+to.length);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
					mimeMessage.setContent(msg, "text/html");
					helper.setTo(to);
					if(cc != null)
					helper.setCc(cc);
					helper.setSubject(subject);
					helper.setFrom(from);
					mailSender.send(mimeMessage);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error( " sendMail() MailException");
				}
			}
		});
		
		thread.start();
		
		return true;
	}

	public void send(final SimpleMailMessage msg, Map<Object, Object> hTemplateVariables, final String templateFileName) {
		final Map<String, Object>  hTemplateVariablesConverted = new HashMap<String, Object>();
		for (Object o : hTemplateVariables.keySet()) {
			hTemplateVariablesConverted.put((String)o, hTemplateVariables.get(o));
		}
		new Thread(new Runnable() {
			public void run() {
				MimeMessagePreparator preparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
						message.setTo(msg.getTo());
						if(msg.getCc()!=null){
							message.setCc(msg.getCc());
						}
						message.setFrom(msg.getFrom());
						message.setSubject(msg.getSubject());
						String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateFileName,"utf-8", hTemplateVariablesConverted);
						message.setText(body, true);
					}
				};
				try
				{
					mailSender.send(preparator);
					EmailDTO emailDTO = new EmailDTO();
					emailDTO.setTo(msg.getTo());
					emailDTO.setCc(msg.getCc());
					emailDTO.setBcc(msg.getBcc());
					emailDTO.setFrom(msg.getFrom());
					emailDTO.setSubject(msg.getSubject());
					emailDTO.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateFileName, "", hTemplateVariablesConverted));
				} catch(Throwable t) {
					logger.error("got error!!!!");
					t.printStackTrace();
				}
			}
		   }).start();
	}
}

