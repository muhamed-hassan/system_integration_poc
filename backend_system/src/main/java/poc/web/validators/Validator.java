package poc.web.validators;

import org.springframework.stereotype.Component;

import poc.web.models.NewEmployee;

//https://en.wikipedia.org/wiki/Fail-fast approach is used to report validation errors
@Component
public class Validator {
	
	public void validate(NewEmployee newEmployee) {
		
		String name = newEmployee.getName();
		if (name == null) {
			throw new IllegalArgumentException("name is required");
		}
		name = name.trim();
		if (name.length() == 0) {
			throw new IllegalArgumentException("name is required");
		}		
		
		String title = newEmployee.getTitle();
		if (title == null) {
			throw new IllegalArgumentException("title is required");
		}
		title = title.trim();
		if (title.length() == 0) {
			throw new IllegalArgumentException("title is required");
		}
	}

}
