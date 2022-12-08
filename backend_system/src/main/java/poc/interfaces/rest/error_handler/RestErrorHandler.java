package poc.interfaces.rest.error_handler;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

@RestControllerAdvice
public class RestErrorHandler {

	private static final String ERROR = "error";

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleServerWebInputException(ServerWebInputException exception) {
		var message = exception.getCause() == null ? 
				"Unable to process this request." : exception.getCause().getMessage();
		var error = Collections.singletonMap(ERROR, message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleWebExchangeBindException(WebExchangeBindException exception) {
		var message = exception.getAllErrors()
				.stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(" and, "));
		var error = Collections.singletonMap(ERROR, message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception exception) {
		var message = exception.getMessage() == null ? 
				"Unable to process this request." : exception.getMessage();
		var error = Collections.singletonMap(ERROR, message);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
