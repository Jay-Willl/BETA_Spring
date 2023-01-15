package edu.sustech.ooad.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailClient {

    public void sendEmail(String receiver, String content) throws UnsupportedEncodingException, MessagingException {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("mail.properties")))
        {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Properties host = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("passwd.properties")))
        {
            host.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String title = node.get("title").asText();
        String email_content = node.get("content").asText();

        String from = (String) host.get("admin");
        String passwd = (String) host.get("passwd");

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from, "VideoSite", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver, "Student", "UTF-8"));
        message.setSubject(title, "UTF-8");
        message.setContent(email_content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();

        Transport transport = mailSession.getTransport();
        transport.connect(from, passwd);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

//        OutputStream out = new FileOutputStream("MyEmail.eml");
//        message.writeTo(out);
//        out.flush();
//        out.close();
    }

}
