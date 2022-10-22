package poc.integration.configs;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import poc.view.Displayer;

import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
	
	@Autowired
	private Displayer displayer;
	
	@Value("${backendSystem.baseUrlV1}")
	private String backendSystemBaseUrl;
	
	@Value("${webClientConfigs.connectTimeoutMillis}")
	private int connectTimeoutMillis;
	
	@Value("${webClientConfigs.readTimeoutMillis}")
	private int readTimeoutMillis;
	
	@Value("${webClientConfigs.writeTimeoutMillis}")
	private int writeTimeoutMillis;
	
	@Value("${webClientConfigs.responseTimeoutMillis}")
	private int responseTimeoutMillis;

	@Bean
	public WebClient webClient() {
		displayer.print(">>> Initiating webClient ...");

		var readTimeoutHandler = new ReadTimeoutHandler(readTimeoutMillis, TimeUnit.MILLISECONDS);
		var writeTimeoutHandler = new WriteTimeoutHandler(writeTimeoutMillis, TimeUnit.MILLISECONDS);		
		Consumer<? super Connection> connectionHandler = connection -> connection.addHandlerLast(readTimeoutHandler)
																			.addHandlerLast(writeTimeoutHandler);
		var httpClient = HttpClient.create()
								.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
								.responseTimeout(Duration.ofMillis(responseTimeoutMillis))
								.doOnConnected(connectionHandler);		
		
		return WebClient.builder()
				.baseUrl(backendSystemBaseUrl)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
					displayer.print(">>> " + clientRequest.method() + ": HTTP Request is initiated ...");
					clientRequest.headers()
								.entrySet()
								.forEach(header -> displayer.print(header.getKey() + " -> " + header.getValue().toString()));
			        return Mono.just(clientRequest);}))
				.filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
					displayer.print("<<< Http Response is coming ...");
					clientResponse.headers()
								.asHttpHeaders()
								.entrySet()
								.forEach(header -> displayer.print(header.getKey() + " -> " + header.getValue().toString()));
					displayer.print("Http Status Code: " + clientResponse.statusCode());	
					return Mono.just(clientResponse);}))
				.build();
	}

}
