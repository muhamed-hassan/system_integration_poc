package poc.persistence.repositories;

import java.util.Iterator;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import poc.persistence.data.InMemoryDataSourceGenerator;
import poc.persistence.entities.Employee;

@Component
public class EmployeeRepository {
	
	@Autowired
	private InMemoryDataSourceGenerator dataSource;
		
	public Employee findById(int id) {	
				
		Employee foundEmployee = null;		
		Iterator<Employee> iterator = dataSource.getEmployees().iterator();		
		while (iterator.hasNext()) {
			
			Employee currentElement = iterator.next();
			if (currentElement.getId() == id) {
				foundEmployee = currentElement;
				break;
			}
		}
		
		if (foundEmployee == null) {
			throw new RuntimeException("Employee with id " + id + " is not found!");
		}
		
		return foundEmployee;
	}
	
	public TreeSet<Employee> findByPage(int pageNumber, int pageSize) {
		
		if (pageNumber < 1) {
			throw new IllegalArgumentException("pageNumber should be a positive value");
		}
		
		if (pageSize < 1) {
			throw new IllegalArgumentException("pageSize should be a positive value");
		}
		
		TreeSet<Employee> employees = dataSource.getEmployees();
		int totalSize = employees.size();
		if (pageSize > totalSize) {
			throw new IllegalArgumentException("pageSize is out of range");
		}
		
		int availablePages = (int) Math.ceil(totalSize / (pageSize * 1.0));
		if (pageNumber > availablePages) {
			throw new IllegalArgumentException("pageNumber is out of range");
		}			
		
		TreeSet<Employee> collectedElements = new TreeSet<Employee>();		
		Iterator<Employee> iterator = employees.iterator();		
		int skippedElements = (pageNumber - 1) * pageSize;
		int collectedElementsLimit = 1; // max is pageSize
		int cursor = 0;
		while (iterator.hasNext()) {
			
			Employee currentElement = iterator.next();
			cursor++;			
			if (cursor > skippedElements) {
				
				if (collectedElementsLimit <= pageSize) {
					
					collectedElements.add(currentElement);
					collectedElementsLimit++;					
				}				
			}
		}

		return collectedElements;
	}
	
	public int save(Employee employee) {
		
		TreeSet<Employee> employees = dataSource.getEmployees();		
		int idOfCreatedEmployee = employees.last().getId() + 1;		
		employee.setId(idOfCreatedEmployee);		
		employees.add(employee);
		
		return idOfCreatedEmployee;
	}
	
	public boolean delete(int id) {
		
		Employee employee = findById(id);		
		boolean removed = dataSource.getEmployees().remove(employee);
		
		return removed;
	}
	
	public boolean update(Employee newEmployee) {
		
		Employee oldEmployee = findById(newEmployee.getId());
		
		// update using a pointer to the old object in memory
		oldEmployee.setName(newEmployee.getName());
		oldEmployee.setTitle(newEmployee.getTitle());
		
		return true; // update is done
	}
	
}
