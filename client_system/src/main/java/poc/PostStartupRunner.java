package poc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import poc.integration.clients.BackendSystemClientV2;

import poc.view.Displayer;

@Component
public class PostStartupRunner implements ApplicationRunner {

	@Autowired
	private Displayer displayer;
	
	@Autowired
	private BackendSystemClientV2 backendSystemClientV2;

	@Override
	public void run(ApplicationArguments applicationArguments) 
			throws Exception {	
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
							.runAsync(backendSystemClientV2::postWithOtherError, pool)
							
							.whenComplete((voidChain, throwable) -> {
								displayer.print("ForkJoinPool is shutting down ...");
								pool.shutdown();
							});
	}
	
}
