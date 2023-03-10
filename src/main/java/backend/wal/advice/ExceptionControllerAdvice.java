package backend.wal.advice;

import backend.wal.advice.dto.ExceptionResponse;
import backend.wal.advice.exception.WalException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
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

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * <b>Wal Custom Exception</b>
     */
    @ExceptionHandler(WalException.class)
    protected ResponseEntity<ExceptionResponse> handleBaseException(WalException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(ExceptionResponse.create(exception.getMessage()));
    }

    /**
     * <b>400 Bad Request</b><br>
     * Spring Validation
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ExceptionResponse> handleBadRequest(final BindException e) {
        log.error(e.getMessage());
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponse.create(
                        String.format("(%s) %s", fieldError.getDefaultMessage(), fieldError.getField()))
                );
    }

    /**
     * <b>400 Bad Request</b><br>
     * ????????? Enum ?????? ????????? ?????? ???????????? Exception
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>400 Bad Request</b><br>
     * RequestParam, RequestPath, RequestPart ?????? ????????? ???????????? ?????? ?????? ???????????? Exception
     */
    @ExceptionHandler(MissingRequestValueException.class)
    protected ResponseEntity<ExceptionResponse> handle(final MissingRequestValueException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>400 Bad Request</b><br>
     * ????????? ????????? ????????? ?????? ???????????? Exception
     */
    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<ExceptionResponse> handleTypeMismatchException(final TypeMismatchException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>400 Bad Request</b>
     */
    @ExceptionHandler({
            InvalidFormatException.class,
            ServletRequestBindingException.class,
            ConstraintViolationException.class
    })
    protected ResponseEntity<ExceptionResponse> handleInvalidFormatException(final Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>405 Method Not Allowed</b><br>
     * ???????????? ?????? HTTP method ?????? ??? ?????? ???????????? Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>406 Not Acceptable</b>
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(NOT_ACCEPTABLE).body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>415 UnSupported Media Type</b> <br>
     * ???????????? ?????? ????????? ????????? ?????? ???????????? Exception
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMediaTypeException(final HttpMediaTypeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(ExceptionResponse.create(e.getMessage()));
    }

    /**
     * <b>500 Internal Server</b>
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ExceptionResponse.create(e.getMessage()));
    }
}
