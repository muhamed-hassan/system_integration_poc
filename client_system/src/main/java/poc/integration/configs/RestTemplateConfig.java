package poc.integration.configs;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import poc.view.Displayer;

@Configuration
public class RestTemplateConfig {
	
	@Autowired
	private Displayer displayer;
	
	@Value("${backendSystem.baseUrlV2}")
	private String backendSystemBaseUrl;
	
	@Value("${restTemplateConfigs.connectTimeoutMillis}")
	private int connectTimeoutMillis;
	
	@Value("${restTemplateConfigs.readTimeoutMillis}")
	private int readTimeoutMillis;
	
	@Bean
	public RestTemplate restTemplate() {		
		displayer.print(">>> Initiating restTemplate ...");		
		return new RestTemplateBuilder()
					.rootUri(backendSystemBaseUrl)
					.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.setConnectTimeout(Duration.ofMillis(connectTimeoutMillis))
					.setReadTimeout(Duration.ofMillis(readTimeoutMillis))

					// activate `errorHandler` in local-profile only
					.errorHandler(new ResponseErrorHandlerImpl())
					
					// activate `interceptors` in local-profile only
					.interceptors((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
						displayer.print(">>> " + request.getMethod() + ": HTTP Request is initiated ...");				
						displayer.print(request.getHeaders().entrySet());
						var response = execution.execute(request, body);
						if (response.getStatusCode().is2xxSuccessful()) {
							displayer.print("<<< Http Response is received with " + response.getStatusCode());
							displayer.print(response.getHeaders().entrySet());
						}						
						return response;})	
					.build();
	}
	
	class ResponseErrorHandlerImpl implements ResponseErrorHandler {

		@Override
		public boolean hasError(ClientHttpResponse response) 
				throws IOException {			
			return response.getStatusCode().isError();
		}

		@Override
		public void handleError(ClientHttpResponse response) 
				throws IOException {
			displayer.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			displayer.print("ERROR:: Http Response is received with " + response.getStatusCode());
			displayer.print(response.getHeaders().entrySet());
			displayer.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		}
		
	}
	
}
