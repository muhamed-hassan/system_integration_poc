package poc.integration.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import poc.view.Displayer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BaseClientV1 {

	@Autowired
	private WebClient webClient;
	
	@Autowired
	private Displayer displayer;
	
	protected <T> Mono<T> getSingle(String requestPath, Class<T> responseBodyType) {
		displayer.print("GET BaseClient::getSingle()");
		return webClient.get()
						.uri(requestPath)
						.retrieve()
				        .bodyToMono(responseBodyType);
	}
	
	protected <T> Flux<T> getList(String requestPath, Class<T> responseBodyType) {
		displayer.print("GET BaseClient::getList()");
		return webClient.get()
						.uri(requestPath)
						.retrieve()
						.bodyToFlux(responseBodyType);
	}
	
	protected Mono<Void> getWithError(String requestPath) {
		displayer.print("GET BaseClient::getWithError()");
		return webClient.get()
						.uri(requestPath)
						.retrieve()
				        .bodyToMono(Void.class);
	}
	
	/* ******************************************************************************************************** */	
	protected <T> Mono<Void> post(String requestPath, T payload) {
		displayer.print("POST BaseClient::post()");
		return webClient.post()
						.uri(requestPath)
						.body(Mono.just(payload), payload.getClass())
						.retrieve()
				        .bodyToMono(Void.class);
	}
	
	/* ******************************************************************************************************** */	
	protected Mono<Void> delete(String requestPath) {
		displayer.print("DELETE BaseClient::delete()");
		return webClient.delete()
						.uri(requestPath)
						.retrieve()
				        .bodyToMono(Void.class);
	}
	
	/* ******************************************************************************************************** */	
	protected <T> Mono<Void> patch(String requestPath, T payload) {
		displayer.print("PATCH BaseClient::patch()");
		return webClient.patch()
						.uri(requestPath)
						.body(Mono.just(payload), payload.getClass())
						.retrieve()
				        .bodyToMono(Void.class);
	}
		
}
