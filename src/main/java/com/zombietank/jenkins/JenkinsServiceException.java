package com.zombietank.jenkins;

public class JenkinsServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JenkinsServiceException(String message) {
		super(message);
	}

	public JenkinsServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
