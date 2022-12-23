package poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import poc.integration.clients.BackendSystemClientV2;

@Component
public class PostStartupRunner implements ApplicationRunner {

	@Autowired
	private BackendSystemClientV2 backendSystemClientV2;

	@Override
	public void run(ApplicationArguments applicationArguments) {
		
		backendSystemClientV2.findById();
		backendSystemClientV2.findByPage();
		backendSystemClientV2.getWithServerError();
		backendSystemClientV2.getWithOtherError();		

		System.out.println("*************************************************************");
		backendSystemClientV2.save();
		backendSystemClientV2.saveWithViolatingPayloadValidations();
		backendSystemClientV2.postWithServerError();
		backendSystemClientV2.postWithOtherError();
		
		System.out.println("*************************************************************");
		backendSystemClientV2.deleteById();
		backendSystemClientV2.deleteWithServerError();
		backendSystemClientV2.deleteWithOtherError();

		System.out.println("*************************************************************");
		backendSystemClientV2.updateById();
		backendSystemClientV2.updateByIdWithViolatingPayloadValidations();
		backendSystemClientV2.putWithServerError();
		backendSystemClientV2.putWithOtherError();		
	}
	
}
