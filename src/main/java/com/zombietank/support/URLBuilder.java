package com.zombietank.support;

import java.net.MalformedURLException;
import java.net.URL;

public class URLBuilder implements Builder<URL> {
	private String url;
	
	public URLBuilder(String url) {
		this.url = url;
	}
	
	public URLBuilder withUrl(String url) {
		this.url = url;
		return this;
	}

	public boolean isValidUrl() {
		try {
			return url != null && url.length() > 0 && new URL(url) != null;
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	@Override
	public URL build() {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Invalid url: " + url);
		}
	} 
}
