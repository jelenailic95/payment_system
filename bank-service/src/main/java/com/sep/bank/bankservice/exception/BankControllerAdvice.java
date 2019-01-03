package com.sep.bank.bankservice.exception;

import com.sep.bank.bankservice.controller.AcquirerController;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@ControllerAdvice(assignableTypes = AcquirerController.class)
@RequestMapping(produces = "application/vnd.error+json")
public class BankControllerAdvice {



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

}