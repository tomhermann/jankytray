package com.zombietank.jenkins;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
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
			final URI apiUrl = generateApiUri(jenkinsUrl);
			getLogger().info("Fetching data from: {}", apiUrl);
			HttpEntity responseEntity = httpClient.execute(new HttpPost(apiUrl)).getEntity();
			String responseContent = EntityUtils.toString(responseEntity);
			if(supportedMimeType().contentType.equals(EntityUtils.getContentMimeType(responseEntity))) {
				JenkinsApi jenkinsApi = handleReponse(responseContent);
				getLogger().debug("API response: {}", jenkinsApi);
				return jenkinsApi;
			}
			throw new JenkinsServiceException("Invalid content mime type.: " + EntityUtils.getContentMimeType(responseEntity));
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
