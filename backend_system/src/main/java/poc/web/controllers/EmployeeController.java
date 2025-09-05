package poc.web.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poc.persistence.entities.Employee;
import poc.persistence.repositories.EmployeeRepository;
import poc.web.models.NewEmployee;
import poc.web.models.SavedEmployee;
import poc.web.validators.Validator;

@RestController
@RequestMapping("employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private Validator validator;
		
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<SavedEmployee> findById(@PathVariable int id) {
		
		Employee employeeEntity = employeeRepository.findById(id);
		
		SavedEmployee savedEmployee = new SavedEmployee();
		savedEmployee.setId(id);
		savedEmployee.setName(employeeEntity.getName());
		savedEmployee.setTitle(employeeEntity.getTitle());		
		
		return new ResponseEntity<SavedEmployee>(savedEmployee, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SavedEmployee>> findByPage(@RequestParam int pageNumber, @RequestParam int pageSize) {
		
		TreeSet<Employee> employees = employeeRepository.findByPage(pageNumber, pageSize);
		
		List<SavedEmployee> collectedElements = new ArrayList<SavedEmployee>();
		Iterator<Employee> iterator = employees.iterator();		
		while (iterator.hasNext()) {
			
			Employee currentElement = iterator.next();
			
			SavedEmployee savedEmployee = new SavedEmployee();
			savedEmployee.setId(currentElement.getId());
			savedEmployee.setName(currentElement.getName());
			savedEmployee.setTitle(currentElement.getTitle());
			
			collectedElements.add(savedEmployee);
		}		
		
		return new ResponseEntity<List<SavedEmployee>>(collectedElements, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "server_error")
	public void doServerErrorWithGET() {
		
		if (true) {
			throw new RuntimeException("forced exception => doServerErrorWithGET()");
		}
	}
	
	/* ******************************************************************************************************** */	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> save(@RequestBody NewEmployee newEmployee) {
		
		validator.validate(newEmployee);
		
		Employee employeeEntity = new Employee();
		employeeEntity.setName(newEmployee.getName());
		employeeEntity.setTitle(newEmployee.getTitle());	
		
		employeeRepository.save(employeeEntity);		
    	
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "server_error")
	public void doServerErrorWithPOST(@RequestBody NewEmployee newEmployee) {
		
		if (true) {
			throw new RuntimeException("forced exception => doServerErrorWithPOST()");
		}
	}
	
	/* ******************************************************************************************************** */
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		
		employeeRepository.delete(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "server_error")
	public void doServerErrorWithDELETE() {
				
		if (true) {
			throw new RuntimeException("forced exception => doServerErrorWithDELETE()");
		}
	}
	
	/* ******************************************************************************************************** */
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<Object> updateById(@PathVariable int id, @RequestBody NewEmployee newEmployee) {
		
		validator.validate(newEmployee);
		
		Employee employeeEntity = new Employee();
		employeeEntity.setId(id);
		employeeEntity.setName(newEmployee.getName());
		employeeEntity.setTitle(newEmployee.getTitle());
		
		employeeRepository.update(employeeEntity);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "server_error")
	public void doServerErrorWithPUT(@RequestBody NewEmployee newEmployee) {
						
		if (true) {
			throw new RuntimeException("forced exception => doServerErrorWithPUT()");
		}
	}

}
