package poc.integration.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import poc.view.Displayer;

public class BaseClientV2 {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Displayer displayer;
	
	protected <T> T getSingle(String requestPath, Class<T> responseBodyType) {
		displayer.print("GET BaseClientV2::getSingle()");		
		return restTemplate.exchange(requestPath, HttpMethod.GET, HttpEntity.EMPTY, responseBodyType).getBody();
	}
	
	protected <T> List<T> getList(String requestPath, ParameterizedTypeReference<List<T>> responseBodyType) {
		displayer.print("GET BaseClientV2::getList()");		
		return restTemplate.exchange(requestPath, HttpMethod.GET, HttpEntity.EMPTY, responseBodyType).getBody();
	}
	
	protected void getWithError(String requestPath) {
		displayer.print("GET BaseClientV2::getWithError()");		
		restTemplate.exchange(requestPath, HttpMethod.GET, HttpEntity.EMPTY, Void.class);
	}
	
	/* ******************************************************************************************************** */	
	protected <T> void post(String requestPath, T payload) {
		displayer.print("POST BaseClientV2::post()");
		var httpEntity = new HttpEntity<>(payload);
		restTemplate.exchange(requestPath, HttpMethod.POST, httpEntity, Void.class);
	}
	
	/* ******************************************************************************************************** */	
	protected void delete(String requestPath) {
		displayer.print("DELETE BaseClientV2::delete()");
		restTemplate.exchange(requestPath, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
	}
	
	/* ******************************************************************************************************** */	
	protected <T> void patch(String requestPath, T payload) {
		displayer.print("PATCH BaseClientV2::patch()");
		var httpEntity = new HttpEntity<>(payload);
		restTemplate.exchange(requestPath, HttpMethod.PATCH, httpEntity, Void.class);
	}
		
}
