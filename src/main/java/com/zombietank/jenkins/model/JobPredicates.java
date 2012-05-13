package com.zombietank.jenkins.model;

import static com.google.common.base.Predicates.not;

import com.google.common.base.Predicate;

public final class JobPredicates {

	public static Predicate<Job> enabled = new Predicate<Job>() {
		public boolean apply(final Job job) {
			return job.isEnabled();
		}
	};

	public static Predicate<Job> failing = new Predicate<Job>() {
		public boolean apply(final Job job) {
			return job.isFailing();
		}
	};

	public static Predicate<Job> building = new Predicate<Job>() {
		public boolean apply(final Job job) {
			return job.isBuilding();
		}
	};
	
	public static Predicate<Job> are(final Predicate<Job> predicate) {
		return predicate;
	}

	public static Predicate<Job> disabled = not(enabled);

	private JobPredicates() {
	}
}
