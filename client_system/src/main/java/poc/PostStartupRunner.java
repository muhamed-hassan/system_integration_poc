package poc;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import poc.integration.clients.BackendSystemClientV1;
import poc.view.Displayer;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Component
public class PostStartupRunner implements ApplicationRunner {

	@Autowired
	private Displayer displayer;
	
	@Autowired
	private BackendSystemClientV1 backendSystemClient;
	
	@Override
	public void run(ApplicationArguments applicationArguments) 
			throws Exception {		
		var countDownLatch = new CountDownLatch(1);
		
		Flux.merge(backendSystemClient.findById(), 
				backendSystemClient.findByPage(),
				backendSystemClient.getWithClientError(),
				backendSystemClient.getWithServerError(),
				backendSystemClient.getWithOtherError(),
				
				backendSystemClient.save(),
				backendSystemClient.saveWithViolatingPayloadValidations(),
				backendSystemClient.postWithClientError(),
				backendSystemClient.postWithServerError(),
				backendSystemClient.postWithOtherError(),
				
				backendSystemClient.deleteById(),
				backendSystemClient.deleteWithClientError(),
				backendSystemClient.deleteWithServerError(),
				backendSystemClient.deleteWithOtherError(),
				
				backendSystemClient.updateById(),
				backendSystemClient.updateByIdWithViolatingPayloadValidations(),
				backendSystemClient.patchWithClientError(),
				backendSystemClient.patchWithServerError(),
				backendSystemClient.patchWithOtherError())
		.doFinally(signal -> countDownLatch.countDown())
		.subscribeOn(Schedulers.boundedElastic())
        .doOnError(displayer::print)	
		.subscribe();
		
		countDownLatch.await();
	}

}
