package backend.handler;

import backend.dto.ErrorResponse;
import backend.exception.AccessDeniedException;
import backend.exception.AlreadyExistsException;
import backend.exception.MailSendingException;
import backend.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage(), ZonedDateTime.now());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExist(AlreadyExistsException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage(), ZonedDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(AccessDeniedException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage(), ZonedDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGlobal(Exception ex) {
        log.error(ex.getMessage());
        return new ErrorResponse("Internal server error: " + ex.getMessage(), ZonedDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJsonParseError(HttpMessageNotReadableException ex) {
        log.error("JSON parse error: {}", ex.getMessage());
        return new ErrorResponse(
                "Invalid JSON format. Please check your request syntax.",
                ZonedDateTime.now()
        );
    }

    @ExceptionHandler(MailSendingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleMailSendingException(MailSendingException ex) {
        log.error("Mail sending failed", ex);
        return new ErrorResponse(ex.getMessage(), ZonedDateTime.now());
    }
}
