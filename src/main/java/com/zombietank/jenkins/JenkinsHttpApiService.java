package com.zombietank.jenkins;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zombietank.httpclient.MimeType;
import com.zombietank.jenkins.model.JenkinsApi;

public abstract class JenkinsHttpApiService implements JenkinsApiService {
	private final HttpClient httpClient;
	
	public JenkinsHttpApiService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public JenkinsApi fetch(final URL jenkinsUrl) throws JenkinsServiceException {
		try {
			URI apiUrl = generateApiUri(jenkinsUrl);
			getLogger().info("Fetching data from: {}", apiUrl);
			HttpResponse response = httpClient.execute(new HttpPost(apiUrl));
			
			if(supportedMimeType().contentType.equals(EntityUtils.getContentMimeType(response.getEntity()))) {
				JenkinsApi jenkinsApi = handleReponse(EntityUtils.toString(response.getEntity()));
				getLogger().debug("API response: {}", jenkinsApi);
				return jenkinsApi;
			}
			throw new JenkinsServiceException("Invalid content mime type.: " + EntityUtils.getContentMimeType(response.getEntity()));
		} catch (Exception e) {
			throw new JenkinsServiceException("Unable to fetch xml from " + jenkinsUrl, e);
		}
	}

	protected abstract URI generateApiUri(final URL jenkinsUrl) throws URISyntaxException;
	
	protected abstract JenkinsApi handleReponse(String responseContent) throws Exception;

	protected abstract MimeType supportedMimeType();
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
}
