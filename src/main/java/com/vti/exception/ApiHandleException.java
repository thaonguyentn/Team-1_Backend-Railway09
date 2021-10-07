/**
 * Represents a ApiHandleException
 *
 * @author P Tr Xuan
 * Created on Sep 15, 2021
 */
package com.vti.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vti.response.ErrorResponse;

@RestControllerAdvice
public class ApiHandleException extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	public ApiHandleException(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleAllException(Exception e, WebRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setMessage(e.getMessage());
		return super.handleExceptionInternal(e, error, null, HttpStatus.INTERNAL_SERVER_ERROR, request);

	}

	@ExceptionHandler
	public ResponseEntity<Object> handlerValidationExceptionException(ValidationErrorException ex, WebRequest request) {
		ErrorResponse errors = new ErrorResponse();
		if (ex instanceof ValidationErrorException) {

			for (FieldError error : ex.getErrors().getFieldErrors()) {
				errors.addError(error.getField(), getMessage(error, request));
			}
		}
		return super.handleExceptionInternal(ex, errors, null, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}
	
	@ExceptionHandler({ AccessDeniedException.class, UsernameNotFoundException.class })
	public ResponseEntity<Object> handleForbidenException(AccessDeniedException ex, WebRequest request) {
		ErrorResponse errors = new ErrorResponse();
		errors.setField(String.format(HttpStatus.FORBIDDEN.name() + ": %s ", request.getRemoteUser()));
		errors.setMessage(ex.getMessage());
		return super.handleExceptionInternal(ex, errors, null, HttpStatus.FORBIDDEN, request);
	}

	private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}

}
