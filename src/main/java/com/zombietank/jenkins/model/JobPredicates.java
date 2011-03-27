package com.zombietank.jenkins.model;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

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
	
	public static Predicate<Job> are(final Predicate<Job> predicate) { return predicate; }
	
	public static Predicate<Job> disabled = Predicates.not(enabled);

	private JobPredicates() {
	}
}
