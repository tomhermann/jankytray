package com.zombietank.jenkins;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zombietank.jenkins.model.JenkinsApi;

/***
 * Populates Jenkins model given Jenkins instance URL.
 * 
 * @author Tom Hermann
 */
@Service
public class JenkinsXmlApiService implements JenkinsApiService {
	private static final Logger logger = LoggerFactory.getLogger(JenkinsXmlApiService.class);
	private final RestTemplate restTemplate;
	private final Serializer serializer;
	
	@Autowired
	public JenkinsXmlApiService(RestTemplate restTemplate, Serializer serializer) {
		this.restTemplate = restTemplate;
		this.serializer = serializer;
	}

	public JenkinsApi fetch(final URL jenkinsUrl) throws JenkinsServiceException {
		try {
			final URI apiUrl = generateApiUri(jenkinsUrl);
			logger.info("Fetching xml from: {}", apiUrl);
			return serializer.read(JenkinsApi.class, restTemplate.getForObject(apiUrl, String.class));
		} catch (Exception e) {
			throw new JenkinsServiceException("Unable to fetch xml from " + jenkinsUrl, e);
		}
	}

	private URI generateApiUri(final URL jenkinsUrl) throws URISyntaxException {
		return new URI(jenkinsUrl.toExternalForm() + "/api/xml");
	}
}
