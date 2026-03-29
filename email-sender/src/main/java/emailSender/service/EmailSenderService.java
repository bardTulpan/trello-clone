package emailSender.service;

import emailSender.config.EmailProperties;
import emailSender.dto.EmailTask;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public void sendTask(EmailTask task) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailProperties.getAddress(), emailProperties.getName());
            helper.setTo(task.getRecipient());
            helper.setSubject(task.getTitle());

            String html = emailProperties.getHtmlTemplate().formatted(task.getBody());
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", task.getRecipient());

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to build email for {}: {}", task.getRecipient(), e.getMessage(), e);
        } catch (MailException e) {
            log.error("SMTP error while sending email to {}: {}", task.getRecipient(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while sending email: {}", e.getMessage(), e);
        }
    }
}