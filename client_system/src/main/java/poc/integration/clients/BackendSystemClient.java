package poc.integration.clients;

import org.springframework.stereotype.Component;

import poc.integration.models.NewEmployee;
import poc.integration.models.SavedEmployee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BackendSystemClient extends BaseClient {
	
	private static final String OTHER_ERROR_REQUEST_PATH = "/employees/other_error";
	private static final String SERVER_ERROR_REQUEST_PATH = "/employees/server_error";
	private static final String CLIENT_ERROR_REQUEST_PATH = "/employees/client_error";

	public Mono<SavedEmployee> findById() {
		return getSingle("/employees/1", SavedEmployee.class);	
	}
	
	public Flux<SavedEmployee> findByPage() {
		return getList("/employees?pageNumber=1&pageSize=10", SavedEmployee.class);
	}
	
	public Mono<Void> getWithClientError() {
		return getWithError(CLIENT_ERROR_REQUEST_PATH);
	}
	
	public Mono<Void> getWithServerError() {
		return getWithError(SERVER_ERROR_REQUEST_PATH);
	}
	
	public Mono<Void> getWithOtherError() {
		return getWithError(OTHER_ERROR_REQUEST_PATH);
	}
	
	/* ******************************************************************************************************** */	
	public Mono<Void> save() {		
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return post("/employees", employee);
	}
	
	public Mono<Void> saveWithViolatingPayloadValidations() {
		var employee = new NewEmployee().withName("").withTitle("");
		return post("/employees", employee);
	}
	
	public Mono<Void> postWithClientError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return post(CLIENT_ERROR_REQUEST_PATH, employee);
	}
	
	public Mono<Void> postWithServerError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return post(SERVER_ERROR_REQUEST_PATH, employee);
	}
	
	public Mono<Void> postWithOtherError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return post(OTHER_ERROR_REQUEST_PATH, employee);
	}
	
	/* ******************************************************************************************************** */	
	public Mono<Void> deleteById() {
		return delete("/employees/50");
	}
	
	public Mono<Void> deleteWithClientError() {
		return delete(CLIENT_ERROR_REQUEST_PATH);
	}
	
	public Mono<Void> deleteWithServerError() {
		return delete(SERVER_ERROR_REQUEST_PATH);
	}
	
	public Mono<Void> deleteWithOtherError() {
		return delete(OTHER_ERROR_REQUEST_PATH);
	}
	
	/* ******************************************************************************************************** */	
	public Mono<Void> updateById() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return patch("/employees/90", employee);
	}
	
	public Mono<Void> updateByIdWithViolatingPayloadValidations() {
		var employee = new NewEmployee().withName("").withTitle("");
		return patch("/employees/90", employee);
	}
	
	public Mono<Void> patchWithClientError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return patch(CLIENT_ERROR_REQUEST_PATH, employee);
	}
	
	public Mono<Void> patchWithServerError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return patch(SERVER_ERROR_REQUEST_PATH, employee);
	}
	
	public Mono<Void> patchWithOtherError() {
		var employee = new NewEmployee().withName("sample name").withTitle("sample title");
		return patch(OTHER_ERROR_REQUEST_PATH, employee);
	}

}
