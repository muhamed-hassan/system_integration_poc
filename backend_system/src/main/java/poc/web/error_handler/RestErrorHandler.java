package poc.web.error_handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestErrorHandler {
	
	@ExceptionHandler
    public ResponseEntity<Map<String, String>> handleExceptions(Exception exception) {
		
		String message = exception.getMessage() == null ? 
				"Unable to process this request." : exception.getMessage();
		
		Map<String, String> error = new HashMap<String, String>(1);
		error.put("error", message);
		
		return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
