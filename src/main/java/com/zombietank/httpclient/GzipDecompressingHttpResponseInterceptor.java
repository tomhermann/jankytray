package com.zombietank.httpclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

public class GzipDecompressingHttpResponseInterceptor implements HttpResponseInterceptor {

	public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
		Header contentEncoding = response.getEntity().getContentEncoding();
		if (contentEncoding != null) {
			for (HeaderElement element : contentEncoding.getElements()) {
				if (element.getName().equalsIgnoreCase(HttpConstants.GZIP)) {
					response.setEntity(new GzipDecompressingEntity(response.getEntity()));
					return;
				}
			}
		}
	}
}
