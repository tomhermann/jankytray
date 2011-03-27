package com.zombietank.jenkins.model;

import java.io.Serializable;
import java.net.URL;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.google.common.base.Objects;

@Root(name="view")
public class View implements Serializable {
	private static final long serialVersionUID = 1L;
	@Element(name="name")
	private final String name;
	@Element(name="url")
	private final URL url;

	public View(@Element(name="name") final String name, @Element(name="url") final URL url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public URL getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", name).add("url", url).toString();
	}
}
