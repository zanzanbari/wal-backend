package backend.wal.advice;

import backend.wal.advice.dto.ExceptionResponse;
import backend.wal.advice.exception.WalException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    /**
     * <b>Wal Custom Exception</b>
     */
    @ExceptionHandler(WalException.class)
    protected ResponseEntity<ExceptionResponse> handleBaseException(WalException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ExceptionResponse(exception.getStatus().value(), exception.getMessage()));
    }

    /**
     * <b>400 Bad Request</b><br>
     * Spring Validation
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ExceptionResponse> handleBadRequest(BindException e) {
        LOGGER.error(e.getMessage());
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(
                        BAD_REQUEST.value(),
                        String.format("(%s) %s", fieldError.getDefaultMessage(), fieldError.getField()))
                );
    }

    /**
     * <b>400 Bad Request</b><br>
     * 잘못된 Enum 값이 입력된 경우 발생하는 Exception
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * <b>400 Bad Request</b><br>
     * RequestParam, RequestPath, RequestPart 등의 필드가 입력되지 않은 경우 발생하는 Exception
     */
    @ExceptionHandler(MissingRequestValueException.class)
    protected ResponseEntity<ExceptionResponse> handle(MissingRequestValueException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * <b>400 Bad Request</b><br>
     * 잘못된 타입이 입력된 경우 발생하는 Exception
     */
    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<ExceptionResponse> handleTypeMismatchException(TypeMismatchException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * <b>400 Bad Request</b>
     */
    @ExceptionHandler({
            InvalidFormatException.class,
            ServletRequestBindingException.class,
            ConstraintViolationException.class
    })
    protected ResponseEntity<ExceptionResponse> handleInvalidFormatException(Exception e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * <b>405 Method Not Allowed</b><br>
     * 지원하지 않은 HTTP method 호출 할 경우 발생하는 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED)
                .body(new ExceptionResponse(METHOD_NOT_ALLOWED.value(), e.getMessage()));
    }

    /**
     * <b>406 Not Acceptable</b>
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .body(new ExceptionResponse(NOT_ACCEPTABLE.value(), e.getMessage()));
    }

    /**
     * <b>415 UnSupported Media Type</b> <br>
     * 지원하지 않는 미디어 타입인 경우 발생하는 Exception
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMediaTypeException(HttpMediaTypeException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity
                .status(UNSUPPORTED_MEDIA_TYPE)
                .body(new ExceptionResponse(UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage()));
    }

    /**
     * <b>500 Internal Server</b>
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
