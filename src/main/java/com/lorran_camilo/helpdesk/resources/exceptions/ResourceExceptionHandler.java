package com.lorran_camilo.helpdesk.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.lorran_camilo.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.lorran_camilo.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

        @ExceptionHandler(ObjectNotFoundException.class)
        public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
                        HttpServletRequest request) {

                StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                                "Object Not Found", ex.getMessage(), request.getRequestURI());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<StandardError> noResourceFoundException(NoResourceFoundException ex,
                        HttpServletRequest request) {
                StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                                "Route Not Found",
                                "O endpoint ou recurso solicitado não foi encontrado. Verifique a URL.",
                                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
                        HttpServletRequest request) {
                StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                                "Data Integrity Violation", ex.getMessage(), request.getRequestURI());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationError> validationErrors(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                ValidationError error = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                                "Validation Error", "Erro na validação dos dados.", request.getRequestURI());

                for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                        error.addError(fieldError.getField(), fieldError.getDefaultMessage());
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
}
