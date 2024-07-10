package fr.diginamic.hello.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles specific exceptions when no matching cities are found.
	 * 
	 * @param ex      the captured exception
	 * @param request the web request object
	 * @return a ResponseEntity configured with the HTTP NOT_FOUND status
	 */
	@ExceptionHandler(VilleNotFoundException.class)
	protected ResponseEntity<Object> handleVilleNotFoundException(VilleNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/**
	 * Handles specific exceptions when no matching department is found.
	 * 
	 * @param ex      the captured exception
	 * @param request the web request object
	 * @return a ResponseEntity configured with the HTTP NOT_FOUND status
	 */
	@ExceptionHandler(DepartementNotFoundException.class)
	protected ResponseEntity<Object> handleDepartementNotFoundException(DepartementNotFoundException ex,
			WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	/**
	 * Handles exceptions related to illegal arguments, often triggered by invalid
	 * request parameters.
	 * 
	 * @param ex      the captured exception
	 * @param request the web request object
	 * @return a ResponseEntity configured with the HTTP BAD_REQUEST status
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Handles all other exceptions not specifically captured by other handlers.
	 * 
	 * @param ex      the captured exception
	 * @param request the web request object
	 * @return a ResponseEntity configured with the HTTP INTERNAL_SERVER_ERROR
	 *         status
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, "An internal error occurred: " + ex.getMessage(), new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * Helps to construct a structured ResponseEntity with error details for all
	 * managed exceptions.
	 * 
	 * @param ex      the treated exception
	 * @param body    the response body
	 * @param headers HTTP headers
	 * @param status  HTTP status
	 * @param request the web request object
	 * @return a configured ResponseEntity
	 */
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body instanceof String && ex.getClass().getSimpleName() == "VilleNotFoundException") {
			body = new ErrorDetails(LocalDateTime.now(), status.value(), ex.getClass().getSimpleName(), (String) body);
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

}
