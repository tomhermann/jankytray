package com.zombietank.support;

import java.net.MalformedURLException;
import java.net.URL;

public class URLBuilder implements Builder<URL>, Validator {
	private final String url;
	
	public static URLBuilder forInput(String url) {
		return new URLBuilder(url);
	}

	public URLBuilder(String url) {
		this.url = url;
	}
	
	@Override
	public boolean isValid() {
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
