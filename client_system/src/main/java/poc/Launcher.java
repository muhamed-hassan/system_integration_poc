package poc;

import poc.integration.clients.BackendSystemClientV2;

public class Launcher {

	public static void main(String[] args) {
		
		BackendSystemClientV2 backendSystemClientV2 = new BackendSystemClientV2();
		
		backendSystemClientV2.findById();
		backendSystemClientV2.findByPage();
		backendSystemClientV2.getWithServerError();	

		System.out.println("*************************************************************");
		backendSystemClientV2.save();
		backendSystemClientV2.saveWithViolatingPayloadValidations();
		backendSystemClientV2.postWithServerError();
		
		System.out.println("*************************************************************");
		backendSystemClientV2.deleteById();
		backendSystemClientV2.deleteWithServerError();

		System.out.println("*************************************************************");
		backendSystemClientV2.updateById();
		backendSystemClientV2.updateByIdWithViolatingPayloadValidations();
		backendSystemClientV2.putWithServerError();
	}

}
