package com.zombietank.httpclient;

public enum MimeType {
    xml ("application/xml");
    
	public String contentType;

	MimeType(String contentType) {
		this.contentType = contentType;
	}
}