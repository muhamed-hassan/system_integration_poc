package poc.interfaces.rest.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import poc.persistence.daos.EmployeeRepository;
import poc.persistence.models.EmployeeEntity;
import poc.interfaces.rest.models.NewEmployee;
import poc.interfaces.rest.models.SavedEmployee;

@RestController
@RequestMapping("v2/employees")
@Validated
public class EmployeeControllerV2 {
	
	private final EmployeeRepository employeeRepository;

	public EmployeeControllerV2(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
		
	@GetMapping("{id}")
	public ResponseEntity<SavedEmployee> findById(@PathVariable int id) {
		var employeeEntity = employeeRepository.findById(id);
		var webModel = new SavedEmployee()
							.withId(employeeEntity.getId())
							.withName(employeeEntity.getName())
							.withTitle(employeeEntity.getTitle());
		return ResponseEntity.ok(webModel);
	}
	
	@GetMapping
	public ResponseEntity<List<SavedEmployee>> findByPage
			(@RequestParam int pageNumber, @RequestParam int pageSize) {
		var webModels = employeeRepository.findByPage(pageNumber, pageSize)
											.stream()
											.map(employeeEntity -> new SavedEmployee()
																		.withId(employeeEntity.getId())
																		.withName(employeeEntity.getName())
																		.withTitle(employeeEntity.getTitle()))
											.collect(Collectors.toList());		
		return ResponseEntity.ok(webModels);
	}
	
	@GetMapping("client_error")
	public ResponseEntity<Void> doClientErrorWithGET() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("server_error")
	public ResponseEntity<Void> doServerErrorWithGET() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping("other_error")
	public ResponseEntity<Void> doOtherErrorWithGET() 
			throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
	}
	
	/* ******************************************************************************************************** */	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody NewEmployee newEmployee) {
		var employeeEntity = new EmployeeEntity()
				.withName(newEmployee.getName())
				.withTitle(newEmployee.getTitle());
		return ResponseEntity.created(UriComponentsBuilder.fromPath("v2/employees/{id}")
                	.build(employeeRepository.save(employeeEntity)))
				.build();
	}
	
	@PostMapping("client_error")
	public ResponseEntity<Void> doClientErrorWithPOST(@Valid @RequestBody NewEmployee newEmployee) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping("server_error")
	public ResponseEntity<Void> doServerErrorWithPOST(@Valid @RequestBody NewEmployee newEmployee) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping("other_error")
	public ResponseEntity<Void> doOtherErrorWithPOST(@Valid @RequestBody NewEmployee newEmployee) 
			throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
	}
	
	/* ******************************************************************************************************** */
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable int id) {
		employeeRepository.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("client_error")
	public ResponseEntity<Void> doClientErrorWithDELETE() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@DeleteMapping("server_error")
	public ResponseEntity<Void> doServerErrorWithDELETE() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("other_error")
	public ResponseEntity<Void> doOtherErrorWithDELETE() 
			throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
	}
	
	/* ******************************************************************************************************** */
	@PatchMapping("{id}")
	public ResponseEntity<Void> updateById(@PathVariable int id, @Valid @RequestBody NewEmployee newEmployee) {
		var employeeEntity = new EmployeeEntity()
				.withId(id)
				.withName(newEmployee.getName())
				.withTitle(newEmployee.getTitle());
		employeeRepository.update(employeeEntity);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("client_error")
	public ResponseEntity<Void> doClientErrorWithPATCH(@Valid @RequestBody NewEmployee newEmployee) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PatchMapping("server_error")
	public ResponseEntity<Void> doServerErrorWithPATCH(@Valid @RequestBody NewEmployee newEmployee) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PatchMapping("other_error")
	public ResponseEntity<Void> doOtherErrorWithPATCH(@Valid @RequestBody NewEmployee newEmployee) 
			throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
	}

}
