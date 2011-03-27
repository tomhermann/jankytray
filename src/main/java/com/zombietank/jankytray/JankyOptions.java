package com.zombietank.jankytray;

import java.net.URL;

import org.kohsuke.args4j.Option;

import com.google.common.base.Objects;

/***
 * Command line options for the program.
 * 
 * @author Tom Hermann
 */
public final class JankyOptions {
	@Option(name = "-url", required = true, usage = "Root Jenkins url")
	private URL jenkinsUrl;

	@Option(name = "-pollingInterval", usage = "polling interval in seconds")
	private int pollingInterval = 30;

	public URL getJenkinsUrl() {
		return jenkinsUrl;
	}

	public void setJenkinsUrl(URL jenkinsUrl) {
		this.jenkinsUrl = jenkinsUrl;
	}

	public int getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("jenkinsUrl", jenkinsUrl).add("pollingInterval", pollingInterval).toString();
	}
}
