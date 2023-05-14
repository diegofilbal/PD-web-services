package br.imd.rest;

import java.util.HashMap;
import java.util.Map;

import br.imd.rest.expections.RestRequestException;

public class Client {

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public void helloWorld() throws RestRequestException {

		String uri = "http://localhost:8080/RestServer/restapi/hello";
		Map<String, String> headerParams = new HashMap<String, String>();

		headerParams.put("accept", "application/json");

		String response = HttpUtils.httpGetRequest(uri, headerParams);

		System.out.println(response);
	}

	public void helloPeople() throws RestRequestException {

		String name = "Jorge";
		String uri = "http://localhost:8080/RestServer/restapi/hello/hello-people/"+name;
		Map<String, String> headerParams = new HashMap<String, String>();

		headerParams.put("accept", "application/json");

		String response = HttpUtils.httpGetRequest(uri, headerParams);

		System.out.println(response);
	}
	
	public void helloPeople2() throws RestRequestException {

		String name = "Jorge";
		String sobrenome = "Silva";
		String uri = "http://localhost:8080/RestServer/restapi/hello/hello-people2"
				+ "?nome="+name+"&sobrenome="+sobrenome;
		Map<String, String> headerParams = new HashMap<String, String>();

		headerParams.put("accept", "application/json");

		String response = HttpUtils.httpGetRequest(uri, headerParams);

		System.out.println(response);
	}
	
	
	public void helloPOST() throws RestRequestException {

		String uri = "http://localhost:8080/RestServer/restapi/hello";
		Map<String, String> headerParams = new HashMap<String, String>();

		headerParams.put("accept", "application/json");
		headerParams.put("content-type", "text/plain");
		
		String response = HttpUtils.httpPostRequest(uri, headerParams, "Jorge", 200);

		System.out.println(response);
	}
	
	

	public static void main(String[] args) throws RestRequestException {
		
		Client restClient = new Client();
		
		restClient.helloWorld();
		
		restClient.helloPeople();
		
		restClient.helloPeople2();
		
		restClient.helloPOST();

	}

}
