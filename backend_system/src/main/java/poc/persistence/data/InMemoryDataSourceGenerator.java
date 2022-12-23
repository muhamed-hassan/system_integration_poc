package poc.persistence.data;

import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import poc.persistence.entities.Employee;

@Component
public class InMemoryDataSourceGenerator {
	
	private TreeSet<Employee> employees;
	
	@PostConstruct
	public void initDataSource() {
		
		System.out.println();
		System.out.println("initDataSource is starting ...");	
		
		employees = new TreeSet<Employee>();
		for (int cursor = 1; cursor <= 100; cursor++) {
			
			Employee employee = new Employee();
			employee.setId(cursor);
			employee.setName("name _ " + cursor);
			employee.setTitle("title _ " + cursor);		
			employees.add(employee);
		}		
		
		System.out.println("100 elements get allocated 👍");
		System.out.println();
	}

	public TreeSet<Employee> getEmployees() {
		return employees;
	}
	
}
