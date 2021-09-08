package com.prueba.email;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;





@SpringBootApplication
public class SpringBootEmailApplication implements CommandLineRunner {
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
    private JavaMailSender emailSender;
	@Autowired
	private TemplateEngine templateEngine;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mensajeSimple();
		mensajeConTemplate();
				
	}
	
	public void mensajeSimple(){
		
		String bodyMessage = "Este es un ejemplo de correo ID=" + UUID.randomUUID().toString();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("MailDestinatario");
		message.setFrom("MailEmisor");
		message.setSubject("Mensaje de Correo Electronico");
		message.setText(bodyMessage);

		emailSender.send(message);
		
		logger.info("El correo se envio de manera exitosa!!");

	}
	
	public void mensajeConTemplate() throws MessagingException {
		
		Context context = new Context();
		String htmlContent = templateEngine.process("index", context);
		
		//System.out.println(htmlContent);
		
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setTo("MailDestinatario");
		message.setFrom("MailEmisor");
		message.setSubject("Mensaje de Correo Electronico");
		message.setText(htmlContent, true);

		emailSender.send(mimeMessage);
		
		logger.info("El correo se envio de manera exitosa!!");
	}

}
