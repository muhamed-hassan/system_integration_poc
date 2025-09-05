package poc;

import poc.integration.clients.BackendSystemClient;

public class Launcher {

	public static void main(String[] args) {
		
		BackendSystemClient backendSystemClient = new BackendSystemClient();
		
		backendSystemClient.findById();
		backendSystemClient.findByPage();
		backendSystemClient.getWithServerError();	

		System.out.println("*************************************************************");
		backendSystemClient.save();
		backendSystemClient.saveWithViolatingPayloadValidations();
		backendSystemClient.postWithServerError();
		
		System.out.println("*************************************************************");
		backendSystemClient.deleteById();
		backendSystemClient.deleteWithServerError();

		System.out.println("*************************************************************");
		backendSystemClient.updateById();
		backendSystemClient.updateByIdWithViolatingPayloadValidations();
		backendSystemClient.putWithServerError();
	}

}
