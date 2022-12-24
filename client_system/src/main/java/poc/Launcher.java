package poc;

import poc.integration.clients.BackendSystemClientV2;

public class Launcher {

	public static void main(String[] args) {
		
		BackendSystemClientV2 backendSystemClientV2 = new BackendSystemClientV2();
		
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
