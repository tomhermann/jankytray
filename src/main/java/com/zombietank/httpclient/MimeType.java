package com.zombietank.httpclient;

public enum MimeType {
    xml ("application/xml"),
    json ("application/json");
    
	public String contentType;

	MimeType(String contentType) {
		this.contentType = contentType;
	}
}