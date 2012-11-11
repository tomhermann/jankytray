package com.zombietank.jenkins.model;

import java.io.Serializable;
import java.net.URL;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.google.common.base.Objects;

@Root(name = "view")
public class View implements Serializable {
	private static final long serialVersionUID = 1L;
	@Element(name = "name")
	private String name;
	@Element(name = "url")
	private URL url;

	public View() {
	}

	public View(@Element(name = "name") final String name, @Element(name = "url") final URL url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", getName())
				.add("url", getUrl()).toString();
	}

}
