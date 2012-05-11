package com.zombietank.jankytray.configuration;

import static com.zombietank.jenkins.model.Status.BUILDING;
import static com.zombietank.jenkins.model.Status.FAILURE;
import static com.zombietank.jenkins.model.Status.SUCCESS;
import static com.zombietank.jenkins.model.Status.UNKNOWN;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.ImmutableMap;
import com.zombietank.httpclient.GzipAcceptHttpRequestInterceptor;
import com.zombietank.httpclient.GzipDecompressingHttpResponseInterceptor;
import com.zombietank.jankytray.StatusImages;
import com.zombietank.jenkins.model.Status;

@Configuration
@ComponentScan(basePackages = "com.zombietank", excludeFilters= {@Filter(Configuration.class)})
public class JankyConfiguration {

	@Bean
	public Serializer serializer() {
		return new Persister();
	}
	
	@Bean
	public HttpClient httpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.addResponseInterceptor(new GzipDecompressingHttpResponseInterceptor());
		httpClient.addRequestInterceptor(new GzipAcceptHttpRequestInterceptor());
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
	public StatusImages statusImages() throws IOException {
		ImmutableMap<Status, Resource> images = ImmutableMap.<Status,Resource>builder()
			.put(SUCCESS,  new ClassPathResource("success.png"))
			.put(BUILDING, new ClassPathResource("building.png"))
			.put(FAILURE,  new ClassPathResource("failure.png"))
			.put(UNKNOWN,  new ClassPathResource("unknown.png"))
		.build();
		return new StatusImages(display(), images);
	}
}
