package com.zombietank.jenkins.model;

import static com.google.common.collect.Iterables.all;
import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Iterables.isEmpty;
import static com.zombietank.jenkins.model.JobPredicates.are;
import static com.zombietank.jenkins.model.JobPredicates.building;
import static com.zombietank.jenkins.model.JobPredicates.disabled;
import static com.zombietank.jenkins.model.JobPredicates.failing;

import com.google.common.collect.ImmutableSet;

public enum Status {
	BUILDING("Building"), DISABLED("Disabled"), SUCCESS("Success"), FAILURE("Failure"), UNKNOWN("Unknown");
	
	public static Status of(final Iterable<Job> jobs) {
		if (isEmpty(jobs)) {
			return UNKNOWN;
		} else if (any(jobs, are(failing))) {
			return FAILURE;
		} else if (all(jobs, are(disabled))) {
			return DISABLED;
		}  else if(any(jobs, are(building))) {
			return BUILDING;
		}
		return SUCCESS;
	}
	
	public static Status of(final Job job) {
		return Status.of(ImmutableSet.of(job));
	}
	
	private final String name;

	private Status(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
