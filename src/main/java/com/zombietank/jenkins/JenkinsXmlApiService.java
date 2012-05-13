package com.zombietank.jenkins;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zombietank.jenkins.model.JenkinsApi;

/***
 * Populates Jenkins model given Jenkins instance URL.
 * 
 * @author Tom Hermann
 */
@Service
public class JenkinsXmlApiService implements JenkinsApiService {
	private static final Logger logger = LoggerFactory.getLogger(JenkinsXmlApiService.class);
	private final Serializer serializer;
	private final HttpClient httpClient;
	
	@Inject
	public JenkinsXmlApiService(HttpClient httpClient, Serializer serializer) {
		this.httpClient = httpClient;
		this.serializer = serializer;
	}

	public JenkinsApi fetch(final URL jenkinsUrl) throws JenkinsServiceException {
		try {
			final URI apiUrl = generateApiUri(jenkinsUrl);
			logger.info("Fetching xml from: {}", apiUrl);
			HttpEntity responseEntity = httpClient.execute(new HttpPost(apiUrl)).getEntity();
			return serializer.read(JenkinsApi.class, EntityUtils.toString(responseEntity));
		} catch (Exception e) {
			throw new JenkinsServiceException("Unable to fetch xml from " + jenkinsUrl, e);
		}
	}

	private URI generateApiUri(final URL jenkinsUrl) throws URISyntaxException {
		return new URI(jenkinsUrl.toExternalForm() + "/api/xml");
	}
}
