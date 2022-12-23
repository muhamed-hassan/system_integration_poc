package poc.interfaces.rest.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import poc.interfaces.rest.models.NewEmployee;
import poc.interfaces.rest.models.SavedEmployee;
import poc.persistence.entities.Employee;
import poc.persistence.repositories.EmployeeRepository;

@RestController
@RequestMapping("v2/employees")
@Validated
public class EmployeeControllerV2 {
	
	@Autowired
	private EmployeeRepository employeeRepository;
		
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
	public ResponseEntity<Object> doServerErrorWithGET() {
		
		// RestErrorHandler::handleGeneralException() shall take care of generating the response
		if (true) throw new RuntimeException("forced exception => doServerErrorWithGET()");
		
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "other_error")
	public ResponseEntity<Object> doOtherErrorWithGET() throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(10);
		return new ResponseEntity<Object>(HttpStatus.REQUEST_TIMEOUT);
	}
	
	/* ******************************************************************************************************** */	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> save(@Valid @RequestBody NewEmployee newEmployee) {
		
		Employee employeeEntity = new Employee();
		employeeEntity.setName(newEmployee.getName());
		employeeEntity.setTitle(newEmployee.getTitle());	
		
		int idOfSavedEmployee = employeeRepository.save(employeeEntity);
    	URI uriOfCreatedEmployee = UriComponentsBuilder.fromPath("v2/employees/{id}").build(idOfSavedEmployee);
		MultiValueMap<String, String> httpHeaders = new HttpHeaders();    	
		httpHeaders.add(HttpHeaders.LOCATION, uriOfCreatedEmployee.toString());
    	
		return new ResponseEntity<Object>(httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "server_error")
	public ResponseEntity<Object> doServerErrorWithPOST(@Valid @RequestBody NewEmployee newEmployee) {
		
		// RestErrorHandler::handleGeneralException() shall take care of generating the response
		if (true) throw new RuntimeException("forced exception => doServerErrorWithPOST()");
				
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "other_error")
	public ResponseEntity<Object> doOtherErrorWithPOST(@Valid @RequestBody NewEmployee newEmployee) throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(10);
		return new ResponseEntity<Object>(HttpStatus.REQUEST_TIMEOUT);
	}
	
	/* ******************************************************************************************************** */
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		
		employeeRepository.delete(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "server_error")
	public ResponseEntity<Object> doServerErrorWithDELETE() {
		
		// RestErrorHandler::handleGeneralException() shall take care of generating the response
		if (true) throw new RuntimeException("forced exception => doServerErrorWithDELETE()");
				
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "other_error")
	public ResponseEntity<Object> doOtherErrorWithDELETE() throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(10);
		return new ResponseEntity<Object>(HttpStatus.REQUEST_TIMEOUT);
	}
	
	/* ******************************************************************************************************** */
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<Object> updateById(@PathVariable int id, @Valid @RequestBody NewEmployee newEmployee) {
		
		Employee employeeEntity = new Employee();
		employeeEntity.setId(id);
		employeeEntity.setName(newEmployee.getName());
		employeeEntity.setTitle(newEmployee.getTitle());
		
		employeeRepository.update(employeeEntity);
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "server_error")
	public ResponseEntity<Object> doServerErrorWithPUT(@Valid @RequestBody NewEmployee newEmployee) {
		
		// RestErrorHandler::handleGeneralException() shall take care of generating the response
		if (true) throw new RuntimeException("forced exception => doServerErrorWithPUT()");
				
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "other_error")
	public ResponseEntity<Object> doOtherErrorWithPUT(@Valid @RequestBody NewEmployee newEmployee) throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(10);
		return new ResponseEntity<Object>(HttpStatus.REQUEST_TIMEOUT);
	}

}
