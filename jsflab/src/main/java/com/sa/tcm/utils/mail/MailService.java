package com.sa.tcm.utils.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailService {

    /**
     * Sends a subject and message to a recipient
     * @param recipient Internet address of the recipient
     * @param subject the subject of the message
     * @param message the message
     * @throws MessagingException
     */
    public static void sendMessage(String recipient, String subject, String message) throws MessagingException {

        if ( theService == null ) {
            theService = new MailService();
        }

        MimeMessage mimeMessage = new MimeMessage(mailSession);

	mimeMessage.setFrom(new InternetAddress(FROM));
	mimeMessage.setSender(new InternetAddress(FROM));
	mimeMessage.setSubject(subject);
        mimeMessage.setContent(message, "text/plain");

        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(HOST, PORT, USER, PASSWORD);

        transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
        transport.close();

    }

    private MailService() {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
       

        mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
    }

    private static MailService theService = null;

    private static Session mailSession;

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USER = "";     // Must be valid user in d.umn.edu domain, e.g. "smit0012"
    private static final String PASSWORD = ""; // Must be valid password for smit0012
    private static final String FROM = "";     // Full info for user, e.g. "Fred Smith <smit0012@d.umn.edu>"
}