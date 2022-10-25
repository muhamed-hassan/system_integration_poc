package poc.integration.clients;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import poc.integration.models.NewEmployee;
import poc.integration.models.SavedEmployee;

@Component
public class BackendSystemClientV2 extends BaseClientV2 {
	
	private static final String CLIENT_ERROR_REQUEST_PATH = "/employees/client_error";
	private static final String SERVER_ERROR_REQUEST_PATH = "/employees/server_error";
	private static final String OTHER_ERROR_REQUEST_PATH = "/employees/other_error";

	public SavedEmployee findById() {
		return getSingle("/employees/1", SavedEmployee.class);	
	}
	
	
	public List<SavedEmployee> findByPage() {
		return getList("/employees?pageNumber=1&pageSize=10", new ParameterizedTypeReference<List<SavedEmployee>>() {});
	}
	
	public void getWithClientError() {
		getWithError(CLIENT_ERROR_REQUEST_PATH);
	}
	
	public void getWithServerError() {
		getWithError(SERVER_ERROR_REQUEST_PATH);
	}
	
	public void getWithOtherError() {
		getWithError(OTHER_ERROR_REQUEST_PATH);
	}
	
	/* ******************************************************************************************************** */	
	public void save() {		
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		post("/employees", employee);
	}
	
	public void saveWithViolatingPayloadValidations() {
		var employee = new NewEmployee().withName("").withTitle("");
		post("/employees", employee);
	}
	
	public void postWithClientError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		post(CLIENT_ERROR_REQUEST_PATH, employee);
	}
	
	public void postWithServerError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		post(SERVER_ERROR_REQUEST_PATH, employee);
	}
	
	public void postWithOtherError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		post(OTHER_ERROR_REQUEST_PATH, employee);
	}
	
	/* ******************************************************************************************************** */	
	public void deleteById() {
		delete("/employees/51");
	}
	
	public void deleteWithClientError() {
		delete(CLIENT_ERROR_REQUEST_PATH);
	}
	
	public void deleteWithServerError() {
		delete(SERVER_ERROR_REQUEST_PATH);
	}
	
	public void deleteWithOtherError() {
		delete(OTHER_ERROR_REQUEST_PATH);
	}
	
	/* ******************************************************************************************************** */	
	public void updateById() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		patch("/employees/91", employee);
	}
	
	public void updateByIdWithViolatingPayloadValidations() {
		var employee = new NewEmployee().withName("").withTitle("");
		patch("/employees/91", employee);
	}
	
	public void patchWithClientError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		patch(CLIENT_ERROR_REQUEST_PATH, employee);
	}
	
	public void patchWithServerError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		patch(SERVER_ERROR_REQUEST_PATH, employee);
	}
	
	public void patchWithOtherError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		patch(OTHER_ERROR_REQUEST_PATH, employee);
	}

}
