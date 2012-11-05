package com.zombietank.jenkins;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.simpleframework.xml.Serializer;

import com.zombietank.jenkins.model.JenkinsApi;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsXmlApiServiceTest {
	private final String baseUrl = "http://example.com/jenkins";
	private JenkinsXmlApiService xmlService;
	@Mock private Serializer xmlDeserializer;
	@Mock private HttpClient httpClient;
	
	@Before
	public void setup() {
		xmlService = new JenkinsXmlApiService(httpClient, xmlDeserializer);
	}
	
	@Test
	public void fetchApiUrlAndUnmarsal() throws Exception {
		JenkinsApi api = new JenkinsApi();
		String xml = "<some><xml/></some>";
		HttpResponse httpResponse = mock(HttpResponse.class, RETURNS_DEEP_STUBS);
		StringEntity entity = new StringEntity(xml, "application/xml", "utf-8");
		when(httpResponse.getEntity()).thenReturn(entity);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);

		when(xmlDeserializer.read(JenkinsApi.class, xml)).thenReturn(api);
		
		JenkinsApi actualApi = xmlService.fetch(new URL("http://example.com"));
	
		assertThat(actualApi, is(sameInstance(api)));
	}


	@Test(expected=JenkinsServiceException.class)
	public void whenUnableToReadThrowUp() throws Exception {
		when(httpClient.execute(any(HttpUriRequest.class))).thenThrow(new RuntimeException("Oh dear!@"));
		xmlService.fetch(new URL(baseUrl));
	}

	@Test(expected=JenkinsServiceException.class)
	public void whenUnableToParseThrowUp() throws Exception {
		when(xmlDeserializer.read(eq(JenkinsApi.class), anyString())).thenThrow(new RuntimeException("Boo"));
		xmlService.fetch(new URL(baseUrl));
	}
}
