package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    public static void sendEmail(String toEmail, String subject, String messageText) throws MessagingException {
        final String fromEmail = "mixishop2004@gmail.com";
        final String password = "nnfw vqys jsgd taxs";  // Mật khẩu ứng dụng Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setContent(messageText, "text/html; charset=UTF-8");

        try {
            Transport.send(msg);
            System.out.println("Email đã được gửi đến: " + toEmail);  // Log khi email đã được gửi
        } catch (MessagingException e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
            throw e;
        }
    }
}
