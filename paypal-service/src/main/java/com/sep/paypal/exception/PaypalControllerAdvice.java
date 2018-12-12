package com.sep.paypal.exception;

import com.paypal.base.exception.HttpErrorException;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.PaypalController;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;

@ControllerAdvice(assignableTypes = PaypalController.class)
@RequestMapping(produces = "application/vnd.error+json")
public class PaypalControllerAdvice {

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<VndErrors> notAuthorized(final NotAuthorizedException e) {
        return error(e, HttpStatus.UNAUTHORIZED, e.getMail());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<VndErrors> notFound(final NotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getParam());
    }

    private ResponseEntity<VndErrors> error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<VndErrors> assertionException(final IllegalArgumentException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ExceptionHandler(HttpErrorException.class)
    public ResponseEntity<VndErrors> internalError(final HttpErrorException e) {
        return error(e, HttpStatus.BAD_REQUEST, e.getErrorResponse());
    }

}
