package com.zombietank.httpclient;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

public class GzipAcceptHttpRequestInterceptor implements HttpRequestInterceptor {
	
	public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
		if (!request.containsHeader(HttpConstants.ACCEPT_ENCODING)) {
			request.addHeader(HttpConstants.ACCEPT_ENCODING, HttpConstants.GZIP);
		}
	}
}
