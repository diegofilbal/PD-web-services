package br.imd.rest;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import br.imd.rest.expections.RestRequestException;


public class HttpUtils {

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String httpPostRequest(String uri, Map<String, String> headerParams,
			String body, int expectStatus) throws RestRequestException {

		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(uri);

			if (headerParams != null) {

				for (String header : headerParams.keySet()) {
					request.addHeader(header, headerParams.get(header));
				}
			}

			if (body != null) {
				StringEntity bodyEntity = new StringEntity(body, "UTF-8");
				request.setEntity(bodyEntity);
			}
			HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() != expectStatus) {
				throw new RestRequestException(response.getStatusLine().getReasonPhrase());
			}

			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");

			return responseString;

		} catch (IOException e) {
			throw new RestRequestException(e.getMessage());
		}
	}

	public static String httpGetRequest(String uri, Map<String, String> headerParams) throws RestRequestException {

		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(uri);

			if (headerParams != null) {

				for (String header : headerParams.keySet()) {
					request.addHeader(header, headerParams.get(header));
				}
			}

			HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RestRequestException(response.getStatusLine().getReasonPhrase());
			}

			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");

			return responseString;

		} catch (IOException e) {
			throw new RestRequestException(e.getMessage());
		}
	}

}
