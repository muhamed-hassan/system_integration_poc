package poc.interfaces.rest.error_handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

	private static final String ERROR = "error";
		
	@ExceptionHandler
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        
		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
		String collectedViolations = "";
		
		Iterator<ObjectError> iterator = errors.iterator();		
		while (iterator.hasNext()) {
			ObjectError currentElement = iterator.next();						
			collectedViolations += currentElement.getDefaultMessage() + ", and ";
		}
		collectedViolations = collectedViolations.substring(0, collectedViolations.length() - 6);
		
		Map<String, String> error = new HashMap<String, String>(1);
		error.put(ERROR, collectedViolations);
		
        return new ResponseEntity<Map<String, String>>(error, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception exception) {
		
		String message = exception.getMessage() == null ? 
				"Unable to process this request." : exception.getMessage();
		
		Map<String, String> error = new HashMap<String, String>(1);
		error.put(ERROR, message);
		
		return new ResponseEntity<Map<String, String>>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
