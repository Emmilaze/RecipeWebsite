package app.util;

import io.sentry.Sentry;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class EmailSenderTLS {

    private String username;
    private String password;
    private Properties props;

    public EmailSenderTLS() {
        this.username = getDates()[0];
        this.password = getDates()[1];

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
    }

    /**
     * Method gets dates about connection to the data base.
     */
    public static String[] getDates() {
        Properties property = new Properties();
        //"./src/main/resources/WEB_INF/config.properties"
        try (FileInputStream fis = new FileInputStream("./config.properties")) {
            property.load(fis);

            String email = property.getProperty("em.email");
            String password = property.getProperty("em.password");
            return new String[]{email, password};
        } catch (IOException e) {
            System.err.println("File not found!");
            Sentry.capture(e);
            return null;
        }
    }

    public void send(String subject, String text, String toEmail) {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            Sentry.capture(e);
            throw new RuntimeException(e);

        }
    }

    private MimeBodyPart createFileAttachment(String filepath)
            throws MessagingException {
        MimeBodyPart mbp = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(filepath);
        mbp.setDataHandler(new DataHandler(fds));
        mbp.setFileName(fds.getName());
        return mbp;
    }

    public void send(String subject, String text, String toEmail, ArrayList<String> paths) {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            for (int i = 0; i < paths.size(); i++) {
                File file = new File(paths.get(i));
                MimeBodyPart mbr = createFileAttachment(file.getAbsolutePath());
                mmp.addBodyPart(mbr);
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(mmp);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            Sentry.capture(e);
            throw new RuntimeException(e);
        }
    }
}
