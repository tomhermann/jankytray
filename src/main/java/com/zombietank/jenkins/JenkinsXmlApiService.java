package com.zombietank.jenkins;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.http.client.HttpClient;
import org.simpleframework.xml.Serializer;

import com.zombietank.httpclient.MimeType;
import com.zombietank.jenkins.model.JenkinsApi;

public class JenkinsXmlApiService extends JenkinsHttpApiService {
	private final Serializer serializer;
	
	@Inject
	public JenkinsXmlApiService(HttpClient httpClient, Serializer serializer) {
		super(httpClient);
		this.serializer = serializer;
	}

	@Override
	protected URI generateApiUri(URL jenkinsUrl) throws URISyntaxException {
		return new URI(jenkinsUrl.toExternalForm() + "/api/xml");
	}

	@Override
	protected JenkinsApi handleReponse(String responseContent) throws Exception {
		return serializer.read(JenkinsApi.class, responseContent);
	}

	@Override
	protected MimeType supportedMimeType() {
		return MimeType.xml;
	}
}