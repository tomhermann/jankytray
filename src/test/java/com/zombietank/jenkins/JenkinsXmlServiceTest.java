package com.zombietank.jenkins;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.springframework.web.client.RestTemplate;

import com.zombietank.jenkins.model.JenkinsApi;

public class JenkinsXmlServiceTest {
	private final String baseUrl = "http://example.com/jenkins";
	private final String apiUrl = baseUrl + "/api/xml";
	private JenkinsXmlApiService xmlService;
	private Serializer xmlDeserializer;
	private RestTemplate restTemplate;

	@Before
	public void setup() {
		xmlDeserializer = mock(Serializer.class);
		restTemplate = mock(RestTemplate.class);
		xmlService = new JenkinsXmlApiService(restTemplate, xmlDeserializer);
	}
	
	@Test
	public void fetchApiUrlAndUnmarsal() throws Exception {
		JenkinsApi api = new JenkinsApi();
		String xml = "<some><xml/></some>";
		when(restTemplate.getForObject(new URI(apiUrl), String.class)).thenReturn(xml);
		when(xmlDeserializer.read(JenkinsApi.class, xml)).thenReturn(api);

		JenkinsApi actualApi = xmlService.fetch(new URL(baseUrl));
	
		assertThat(actualApi, is(sameInstance(api)));
	}


	@Test(expected=JenkinsServiceException.class)
	public void whenUnableToReadThrowUp() throws Exception {
		when(restTemplate.getForObject(new URI(apiUrl), String.class)).thenThrow(new RuntimeException("Oh dear!@"));
		xmlService.fetch(new URL(baseUrl));
	}

	@Test(expected=JenkinsServiceException.class)
	public void whenUnableToParseThrowUp() throws Exception {
		when(xmlDeserializer.read(eq(JenkinsApi.class), anyString())).thenThrow(new RuntimeException("Boo"));
		xmlService.fetch(new URL(baseUrl));
	}
}
