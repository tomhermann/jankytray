package com.zombietank.jenkins.model;

import static com.google.common.base.Preconditions.*;
import java.io.Serializable;
import java.net.URL;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.google.common.base.Objects;

@Root(name="job")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;
	@Element(name="name")
	private final String name;
	@Element(name="color")
	private final String color;
	@Element(name="url")
	private final URL url;
	
	public Job(@Element(name="url") URL url, @Element(name="name") String name, @Element(name="color") String color) {
		checkNotNull(color, "Color may not be null!");
		this.url = url;
		this.name = name;
		this.color = color;
	}

	public URL getUrl() {
		return url;
	}
	
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public boolean isBuilding() {
		return colorContains("anime");
	}

	public boolean isEnabled() {
		return !colorContains("disabled") && !colorContains("grey");
	}

	public boolean isFailing() {
		return colorContains("red") || colorContains("unstable") || colorContains("broken");
	}
	
	private boolean colorContains(String search) {
		return containsIgnoreCase(getColor(), search);
	}
	
	private static final boolean containsIgnoreCase(String source, String search) {
		return source.toUpperCase().contains(search.toUpperCase());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), getUrl());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", name)
				.add("color", color)
				.add("url", getUrl())
				.add("isBuilding", isBuilding())
				.add("isEnabled", isEnabled())
				.add("isFailing", isFailing())
				.toString();
	}
}
