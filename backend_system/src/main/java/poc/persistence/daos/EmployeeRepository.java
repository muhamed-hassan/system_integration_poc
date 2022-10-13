package poc.persistence.daos;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import poc.persistence.data.MockedDataSource;
import poc.persistence.models.EmployeeEntity;

@Component
public class EmployeeRepository {
	
	private final MockedDataSource mockedDataSource;
	
	public EmployeeRepository(MockedDataSource mockedDataSource) {
		this.mockedDataSource = mockedDataSource;
	}

	@PostConstruct
	public void print() {
		mockedDataSource.getEmployees()
							.stream()
							.forEach(System.out::println);
	}
	
	public EmployeeEntity findById(int id) {	
		return mockedDataSource.getEmployees()
								.stream()
								.filter(element -> element.getId() == id)
								.findFirst()
								.orElseThrow(() -> new RuntimeException("Employee with id " + id + " is not found!"));
	}
	
	public List<EmployeeEntity> findByPage(int pageNumber, int pageSize) {	
		if (pageNumber < 1) {
			throw new IllegalArgumentException("pageNumber should be a positive value");
		}
		
		if (pageSize < 1) {
			throw new IllegalArgumentException("pageSize should be a positive value");
		}
		
		var totalSize = mockedDataSource.getEmployees().size();
		if (pageSize > totalSize) {
			throw new IllegalArgumentException("pageSize is out of range");
		}
		
		if (pageNumber > (totalSize / pageSize)) {
			throw new IllegalArgumentException("pageNumber is out of range");
		}			
			
		return mockedDataSource.getEmployees()
								.stream()
								.sorted((e1, e2) -> e1.getId() - e2.getId())
								.skip((pageNumber - 1) * pageSize)
								.limit(pageSize)
								.collect(Collectors.toList());
	}
	
	public int save(EmployeeEntity employee) {
		var idOfCreatedEmployee = mockedDataSource
				.getEmployees()
				.stream()
				.mapToInt(EmployeeEntity::getId)
				.max().getAsInt() + 1;
		employee.setId(idOfCreatedEmployee);
		mockedDataSource.getEmployees().add(employee);
		return idOfCreatedEmployee;
	}
	
	public boolean delete(int id) {
		var employeeNotFound = !mockedDataSource
									.getEmployees()
									.stream()
									.anyMatch(employee -> employee.getId() == id);
		if (employeeNotFound) {
			throw new RuntimeException("Employee with id " + id + " is not found!");
		}
		
		return mockedDataSource
				.getEmployees()
				.removeIf(element -> element.getId() == id);
	}
	
	public boolean update(EmployeeEntity employee) {
		var employees = mockedDataSource.getEmployees();
		for (var cursor = 0; cursor < employees.size(); cursor++) {
			if (employees.get(cursor).equals(employee)) {
				employees.remove(cursor);				
				return employees.add(employee);
			}
		}
		return false;
	}
	
}
