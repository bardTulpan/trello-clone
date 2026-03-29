package emailSender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(EmailSmtpProperties props) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(props.getHost());
        sender.setPort(props.getPort());
        sender.setUsername(props.getUsername());
        sender.setPassword(props.getPassword());

        Properties p = sender.getJavaMailProperties();
        p.put("mail.smtp.auth", props.isAuth());
        p.put("mail.smtp.starttls.enable", props.isStarttls());
        p.put("mail.smtp.starttls.required", props.isStarttls());

        return sender;
    }
}