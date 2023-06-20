package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.InputStreamSource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import jakarta.activation.DataHandler;
//import jakarta.mail.Authenticator;
//import jakarta.mail.Message;
//import jakarta.mail.Multipart;
//import jakarta.mail.Session;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMultipart;
//import jakarta.mail.util.ByteArrayDataSource;
//
//import java.io.IOException;
//import java.util.Properties;
//
//import javax.mail.MessagingException;
//
//@Service
public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    
//    public void shareExcelFile(String toEmail, String subject, String body, byte[] attachmentData, String attachmentName) throws MessagingException, IOException {
//        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper;
//		try {
//			helper = new MimeMessageHelper(message, true);
//			 helper.setTo(toEmail);
//		        helper.setSubject(subject);
//		        helper.setText(body);
//
//		        // Attach the file from byte[]
//		        InputStreamSource attachmentSource = new ByteArrayResource(attachmentData);
//		        helper.addAttachment(attachmentName, attachmentSource);
//		} catch (jakarta.mail.MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//       
//
//        mailSender.send(message);
//    }
//
//       
//        private final String host;
//        private final int port;
//        private final String username;
//        private final String password;
//
//        public EmailService(String host, int port, String username, String password) {
//            this.host = host;
//            this.port = port;
//            this.username = username;
//            this.password = password;
//        }
//
//        public void sendEmailWithAttachment(String recipient, String subject, String body, byte[] attachmentData, String attachmentName) throws MessagingException {
//            // Create properties for the SMTP connection
//            Properties properties = new Properties();
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", port);
//
//            // Create a session with the SMTP server
//            Session session = Session.getInstance(properties, new Authenticator() {
//                @Override
//             //   protected PasswordAuthentication getPasswordAuthentication() {
//               //     return new PasswordAuthentication(username, password);
//                }
//           // });
//
//            // Create a new email message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//            message.setSubject(subject);
//
//            // Create the email body part
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setText(body);
//
//            // Create the attachment body part
//            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//            attachmentBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachmentData, "application/octet-stream")));
//            attachmentBodyPart.setFileName(attachmentName);
//
//            // Create a multipart message and add the parts to it
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//            multipart.addBodyPart(attachmentBodyPart);
//
//            // Set the multipart message as the content of the email
//            message.setContent(multipart);
//
//            // Send the email
//          //  Transport.send(message);
//        }
//    
//}
//
}