package backend.exception;

public class MailSendingException extends RuntimeException {
    public MailSendingException(String message, Exception e) {
        super(message);
    }
}
