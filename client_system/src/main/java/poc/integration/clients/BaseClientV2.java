package poc.integration.clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Designed to be extended (inherited) to hide the low level complexity a bit and generalize the HTTP verbs usage.
 * 
 * Abstract Example: 
 * - ChildClientClass extends BaseClientV2 => Where `ChildClient` is replaced with a proper name where it follows SRP and KISS 
 * 
 * Real Example: 
 * - BackendSystemClientV2 extends BaseClientV2
 * */
public class BaseClientV2 {
	
	private String backendSystemBaseUrl = "http://localhost:8080/";
	
	protected Object get(String requestPath, Class<? extends Object> responseBodyType) {
		
		HttpURLConnection connection = null;
		Object responseBody = null;		
		try {
			
			URL url = new URL(backendSystemBaseUrl + requestPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			int responseCode = connection.getResponseCode();
			System.out.println("> GET Response Code :: " + responseCode);
			ObjectMapper objectMapper = new ObjectMapper();
			switch (responseCode) {
				case HttpURLConnection.HTTP_OK:					
					responseBody = objectMapper.readValue((InputStream) connection.getContent(), responseBodyType);
					System.out.println("Parsed response: " + responseBody);
					break;
				case HttpURLConnection.HTTP_INTERNAL_ERROR:
					Object errorBodyWithServerError = objectMapper.readValue((InputStream) connection.getErrorStream(), Object.class);
					throw new IOException("Failed to connect with " + url + " due to " + errorBodyWithServerError);	
				default:
					throw new IOException("Failed to connect with " + url);
			}			
			
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (ProtocolException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.disconnect();
	    }
		
		System.out.println();
		return responseBody;
	}
		
	/* ******************************************************************************************************** */	
	protected <T> void post(String requestPath, T payload) {
		
		HttpURLConnection connection = null;
		try {
			
			URL url = new URL(backendSystemBaseUrl + requestPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);			
			
			ObjectMapper objectMapper = new ObjectMapper();			
			OutputStream outputStream = connection.getOutputStream();			
			objectMapper.writeValue(outputStream, payload);
			outputStream.flush();
			
			int responseCode = connection.getResponseCode();
			System.out.println("> POST Response Code :: " + responseCode);
			switch (responseCode) {
				case HttpURLConnection.HTTP_CREATED:
					System.out.println("Payload is sent successfully");
					break;
				case HttpURLConnection.HTTP_BAD_REQUEST:
					Object errorBodyWithBadRequest = objectMapper.readValue((InputStream) connection.getErrorStream(), Object.class);
					throw new IOException("Sent payload is not valid: " + errorBodyWithBadRequest);
				case HttpURLConnection.HTTP_INTERNAL_ERROR:
					Object errorBodyWithServerError = objectMapper.readValue((InputStream) connection.getErrorStream(), Object.class);
					throw new IOException("Failed to connect with " + url + " due to " + errorBodyWithServerError);	
				default:
					throw new IOException("Failed to connect with " + url);
			}
			
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (ProtocolException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.disconnect();
	    }
		
		System.out.println();
	}
	
	/* ******************************************************************************************************** */	
	protected void delete(String requestPath) {
		
		HttpURLConnection connection = null;
		try {
			
			URL url = new URL(backendSystemBaseUrl + requestPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
						
			int responseCode = connection.getResponseCode();
			System.out.println("> DELETE Response Code :: " + responseCode);
			ObjectMapper objectMapper = new ObjectMapper();
			switch (responseCode) {
				case HttpURLConnection.HTTP_NO_CONTENT:
					System.out.println("Delete is done");
					break;
				case HttpURLConnection.HTTP_INTERNAL_ERROR:
					Object errorBodyWithServerError = objectMapper.readValue((InputStream) connection.getErrorStream(), Object.class);
					throw new IOException("Failed to connect with " + url + " due to " + errorBodyWithServerError);
				default:
					throw new IOException("Failed to connect with " + url);
			}
			
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (ProtocolException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.disconnect();
	    }
		
		System.out.println();
	}
	
	/* ******************************************************************************************************** */	
	protected <T> void put(String requestPath, T payload) {
		
		HttpURLConnection connection = null;
		try {
			
			URL url = new URL(backendSystemBaseUrl + requestPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);			
			
			ObjectMapper objectMapper = new ObjectMapper();			
			OutputStream outputStream = connection.getOutputStream();			
			objectMapper.writeValue(outputStream, payload);
			outputStream.flush();
			
			int responseCode = connection.getResponseCode();
			System.out.println("> PUT Response Code :: " + responseCode);
			switch (responseCode) {
				case HttpURLConnection.HTTP_NO_CONTENT:
					System.out.println("Payload is sent successfully");
					break;
				case HttpURLConnection.HTTP_BAD_REQUEST:
					Object errorBodyWithBadRequest = objectMapper.readValue((InputStream) connection.getErrorStream(), Object.class);
					throw new IOException("Sent payload is not valid: " + errorBodyWithBadRequest);
				case HttpURLConnection.HTTP_INTERNAL_ERROR:
					Object errorBodyWithServerError = objectMapper.readValue((InputStream) connection.getErrorStream(), Object.class);
					throw new IOException("Failed to connect with " + url + " due to " + errorBodyWithServerError);	
				default:
					throw new IOException("Failed to connect with " + url);
			}
			
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (ProtocolException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			connection.disconnect();
	    }
	
		System.out.println();
	}
		
}
