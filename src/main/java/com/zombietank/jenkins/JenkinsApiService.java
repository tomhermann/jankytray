package com.zombietank.jenkins;

import java.net.URL;

import com.zombietank.jenkins.model.JenkinsApi;

public interface JenkinsApiService {
	public JenkinsApi fetch(URL jenkinsUrl) throws JenkinsServiceException;
}
