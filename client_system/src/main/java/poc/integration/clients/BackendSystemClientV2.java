package poc.integration.clients;

import java.util.List;

import org.springframework.stereotype.Component;

import poc.integration.models.NewEmployee;
import poc.integration.models.SavedEmployee;

@Component
public class BackendSystemClientV2 extends BaseClientV2 {
	
	private static final String SERVER_ERROR_REQUEST_PATH = "v2/employees/server_error";
	private static final String OTHER_ERROR_REQUEST_PATH = "v2/employees/other_error";
	
	public SavedEmployee findById() {
		return (SavedEmployee) get("v2/employees/1", SavedEmployee.class);
	}	
	
	public List<SavedEmployee> findByPage() {
		return (List<SavedEmployee>) get("v2/employees?pageNumber=1&pageSize=10", List.class);
	}
	
	public void getWithServerError() {
		get(SERVER_ERROR_REQUEST_PATH, Object.class);
	}
	
	public void getWithOtherError() {
		get(OTHER_ERROR_REQUEST_PATH, Object.class);
	}
	
	/* ******************************************************************************************************** */	
	public void save() {		
		NewEmployee employee = new NewEmployee();
		employee.setName("sample name");
		employee.setTitle("sample title");
		post("v2/employees", employee);
	}
	
	public void saveWithViolatingPayloadValidations() {
		NewEmployee employee = new NewEmployee();
		employee.setName("");
		employee.setTitle("");
		post("v2/employees", employee);
	}
	
	public void postWithServerError() {
		NewEmployee employee = new NewEmployee();
		employee.setName("sample name");
		employee.setTitle("sample title");
		post(SERVER_ERROR_REQUEST_PATH, employee);
	}
	
	public void postWithOtherError() {
		NewEmployee employee = new NewEmployee();
		employee.setName("sample name");
		employee.setTitle("sample title");
		post(OTHER_ERROR_REQUEST_PATH, employee);
	}
	
	/* ******************************************************************************************************** */	
	public void deleteById() {
		delete("v2/employees/51");
	}
	
	public void deleteWithServerError() {
		delete(SERVER_ERROR_REQUEST_PATH);
	}
	
	public void deleteWithOtherError() {
		delete(OTHER_ERROR_REQUEST_PATH);
	}
	
	/* ******************************************************************************************************** */	
	public void updateById() {
		NewEmployee employee = new NewEmployee();
		employee.setName("sample name");
		employee.setTitle("sample title");
		put("v2/employees/91", employee);
	}
	
	public void updateByIdWithViolatingPayloadValidations() {
		NewEmployee employee = new NewEmployee();
		employee.setName("");
		employee.setTitle("");
		put("v2/employees/91", employee);
	}
	
	public void putWithServerError() {
		NewEmployee employee = new NewEmployee();
		employee.setName("sample name");
		employee.setTitle("sample title");
		put(SERVER_ERROR_REQUEST_PATH, employee);
	}
	
	public void putWithOtherError() {
		NewEmployee employee = new NewEmployee();
		employee.setName("sample name");
		employee.setTitle("sample title");
		put(OTHER_ERROR_REQUEST_PATH, employee);
	}

}
