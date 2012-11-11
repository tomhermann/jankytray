package com.zombietank.jenkins;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.http.client.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombietank.httpclient.MimeType;
import com.zombietank.jenkins.model.JenkinsApi;

public class JenkinsJsonApiService extends JenkinsHttpApiService {
	private final ObjectMapper objectMapper;

	@Inject
	public JenkinsJsonApiService(HttpClient httpClient, ObjectMapper objectMapper) {
		super(httpClient);
		this.objectMapper = objectMapper;
	}

	@Override
	protected URI generateApiUri(URL jenkinsUrl) throws URISyntaxException {
		return new URI(jenkinsUrl.toExternalForm() + "/api/json");
	}

	@Override
	protected JenkinsApi handleReponse(String responseContent) throws Exception {
		return objectMapper.readValue(responseContent, JenkinsApi.class);
	}

	@Override
	protected MimeType supportedMimeType() {
		return MimeType.json;
	}
}
