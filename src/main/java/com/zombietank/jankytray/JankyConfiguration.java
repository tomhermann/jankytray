package com.zombietank.jankytray;

import static com.zombietank.jenkins.model.Status.BUILDING;
import static com.zombietank.jenkins.model.Status.DISABLED;
import static com.zombietank.jenkins.model.Status.FAILURE;
import static com.zombietank.jenkins.model.Status.SUCCESS;
import static com.zombietank.jenkins.model.Status.UNKNOWN;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombietank.httpclient.GzipAcceptHttpRequestInterceptor;
import com.zombietank.httpclient.GzipDecompressingHttpResponseInterceptor;
import com.zombietank.jenkins.JenkinsApiService;
import com.zombietank.jenkins.JenkinsJsonApiService;
import com.zombietank.jenkins.JenkinsXmlApiService;
import com.zombietank.swt.ImageRegistryWrapper;

@Configuration
@ComponentScan(basePackages = "com.zombietank", excludeFilters= {@Filter(Configuration.class)})
public class JankyConfiguration {
	public static final String JSON_PROFILE = "json", XML_PROFILE = "xml";
	public static final String SETTINGS_ICON = "com.zombietank.settings.icon";
	public static final String PREFERENCES_FILE_NAME = "preferences.ini";

	@Bean
	public IPersistentPreferenceStore preferenceStore() throws IOException {
		PreferenceStore preferenceStore = new PreferenceStore(PREFERENCES_FILE_NAME);
		if(new File(PREFERENCES_FILE_NAME).exists()) {
			preferenceStore.load();
		}
		return preferenceStore;
	}
	
	@Bean
	public HttpClient httpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.addResponseInterceptor(new GzipDecompressingHttpResponseInterceptor());
		httpClient.addRequestInterceptor(new GzipAcceptHttpRequestInterceptor());
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		return httpClient;
	}
	
	@Bean(destroyMethod="dispose")
	public Display display() {
		return new Display();
	}
	
	@Bean(destroyMethod="dispose")
	public Shell shell() {
		return new Shell(display());
	}
	
	@Bean(destroyMethod="dispose")
	public Tray tray() {
		return display().getSystemTray();
	}
	
	@Bean
	public ImageRegistryWrapper imageRegistry() throws IOException {
		ImageRegistryWrapper registry = new ImageRegistryWrapper(display());
		registry.put(SETTINGS_ICON, new ClassPathResource("settings.png"));
		registry.put(SUCCESS,  new ClassPathResource("success.png"));
		registry.put(BUILDING, new ClassPathResource("building.png"));
		registry.put(DISABLED, new ClassPathResource("disabled.png"));
		registry.put(FAILURE,  new ClassPathResource("failure.png"));
		registry.put(UNKNOWN,  new ClassPathResource("unknown.png"));
		return registry;
	}
	
	@Configuration @Profile(value = JSON_PROFILE) 
	static class Json {
		
		@Bean @Inject
		public JenkinsApiService jenkinsApiService(HttpClient httpClient) {
			return new JenkinsJsonApiService(httpClient, objectMapper());
		}

		@Bean
		public ObjectMapper objectMapper() {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			return objectMapper;
		}
	}
	
	@Configuration @Profile(value = XML_PROFILE) 
	static class Xml {
		
		@Bean @Inject
		public JenkinsApiService jenkinsApiService(HttpClient httpClient) {
			return new JenkinsXmlApiService(httpClient, serializer());
		}

		@Bean
		public Serializer serializer() {
			return new Persister();
		}
	}
}
