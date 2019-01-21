package org.openpreservation.api.api.handler;

import org.openpreservation.api.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<GenericResponse> handleUserNotFound(RuntimeException ex, WebRequest request) {
		GenericResponse response = new GenericResponse(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@Getter
	@Setter
	public static class GenericResponse {
		private String time;
		private String message;

		GenericResponse(String message) {
			this.time = LocalDateTime.now().toString();
			this.message = message;
		}
	}
}