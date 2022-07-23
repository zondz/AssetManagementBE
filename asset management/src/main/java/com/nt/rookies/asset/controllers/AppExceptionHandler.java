package com.nt.rookies.asset.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.nt.rookies.asset.exception.ApiError;
import com.nt.rookies.asset.exception.NotFoundException;
import com.nt.rookies.asset.exception.UserException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@AllArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception,
			Object body,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		// for all exceptions that are not overriden, the body is null, so we can
		// just provide new body based on error message and call super method
		var apiError = Objects.isNull(body)
				? new ApiError(status, exception.getMessage()) // <--
				: body;
		return super.handleExceptionInternal(exception, apiError, headers, status, request);
	}

//	@Override
//	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		logger.error(ex.getMessage());
//		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity handleNotFoundException(
			NotFoundException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		logger.warn(errors.toString());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity handleUrlException(UserException userException) {
		logger.warn(userException.getCodeResponse().getMessage());
		return new ResponseEntity<>(userException.getCodeResponse().getMessage(),
				userException.getCodeResponse().getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity globalExceptionHandler(Exception ex, WebRequest request) {
		logger.warn(ex.getMessage());
		return new ResponseEntity<>("Error: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
