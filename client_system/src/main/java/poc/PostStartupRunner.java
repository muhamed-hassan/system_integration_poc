package poc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import poc.integration.clients.BackendSystemClientV1;
import poc.integration.clients.BackendSystemClientV2;
import poc.view.Displayer;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Component
public class PostStartupRunner implements ApplicationRunner {

	@Autowired
	private Displayer displayer;

	@Autowired
	private BackendSystemClientV1 backendSystemClientV1;
	
	@Autowired
	private BackendSystemClientV2 backendSystemClientV2;

	@Override
	public void run(ApplicationArguments applicationArguments) 
			throws Exception {		
		doWebClientPoc();		
		doRestTemplatePoc();
	}

	private void doRestTemplatePoc() {
		var pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		
		CompletableFuture.runAsync(backendSystemClientV2::findById, pool)
							.runAsync(backendSystemClientV2::findByPage, pool)
							.runAsync(backendSystemClientV2::getWithClientError, pool)
							.runAsync(backendSystemClientV2::getWithServerError, pool)
							.runAsync(backendSystemClientV2::getWithOtherError, pool)
							
							.runAsync(backendSystemClientV2::deleteById, pool)
							.runAsync(backendSystemClientV2::deleteWithClientError, pool)
							.runAsync(backendSystemClientV2::deleteWithServerError, pool)
							.runAsync(backendSystemClientV2::deleteWithOtherError, pool)
							
							.runAsync(backendSystemClientV2::updateById, pool)
							.runAsync(backendSystemClientV2::updateByIdWithViolatingPayloadValidations, pool)
							.runAsync(backendSystemClientV2::patchWithClientError, pool)
							.runAsync(backendSystemClientV2::patchWithServerError, pool)
							.runAsync(backendSystemClientV2::patchWithOtherError, pool)
							
							.runAsync(backendSystemClientV2::save, pool)
							.runAsync(backendSystemClientV2::saveWithViolatingPayloadValidations, pool)
							.runAsync(backendSystemClientV2::postWithClientError, pool)
							.runAsync(backendSystemClientV2::postWithServerError, pool)
							.runAsync(backendSystemClientV2::postWithOtherError, pool);
	}
	
	private void doWebClientPoc() 
			throws InterruptedException {
		var countDownLatch = new CountDownLatch(1);
		
		Flux.merge(backendSystemClientV1.findById(), 
				backendSystemClientV1.findByPage(),
				backendSystemClientV1.getWithClientError(), 
				backendSystemClientV1.getWithServerError(),
				backendSystemClientV1.getWithOtherError(),

				backendSystemClientV1.save(), 
				backendSystemClientV1.saveWithViolatingPayloadValidations(),
				backendSystemClientV1.postWithClientError(), 
				backendSystemClientV1.postWithServerError(),
				backendSystemClientV1.postWithOtherError(),

				backendSystemClientV1.deleteById(), 
				backendSystemClientV1.deleteWithClientError(),
				backendSystemClientV1.deleteWithServerError(), 
				backendSystemClientV1.deleteWithOtherError(),

				backendSystemClientV1.updateById(), 
				backendSystemClientV1.updateByIdWithViolatingPayloadValidations(),
				backendSystemClientV1.patchWithClientError(), 
				backendSystemClientV1.patchWithServerError(),
				backendSystemClientV1.patchWithOtherError())
			.doFinally(signal -> countDownLatch.countDown())
			.subscribeOn(Schedulers.boundedElastic())
			.doOnError(displayer::print)
			.subscribe();

		countDownLatch.await();
	}

}
