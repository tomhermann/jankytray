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
		if(url == null || url.length() == 0) {
			System.err.println("Sucks here");
			return false;
		}
		try {
			new URL(url);
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
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
