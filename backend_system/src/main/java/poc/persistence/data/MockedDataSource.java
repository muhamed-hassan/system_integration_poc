package poc.persistence.data;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import poc.persistence.models.EmployeeEntity;

@Component
public class MockedDataSource {
	
	private List<EmployeeEntity> employees;
	
	@PostConstruct
	public void initDataSource() {
		System.out.println();
		System.out.println("initDataSource is starting ...");		
		employees = new ArrayList<>();
		for (int cursor = 1; cursor <= 100; cursor++) {
			var employee = new EmployeeEntity();
			employee.setId(cursor);
			employee.setName("name _ " + cursor);
			employee.setTitle("title _ " + cursor);		
			employees.add(employee);
		}		
		System.out.println("100 elements get allocated 👍");
		System.out.println();
	}

	public List<EmployeeEntity> getEmployees() {
		return employees;
	}
	
}
