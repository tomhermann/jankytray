package com.zombietank.jankytray;

import static com.zombietank.jenkins.model.JobPredicates.building;
import static com.zombietank.jenkins.model.JobPredicates.disabled;
import static com.zombietank.jenkins.model.JobPredicates.failing;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.zombietank.jenkins.model.Job;

public class JobPredicatesTest {
	private Job job;
	
	@Before
	public void setup() {
		job = mock(Job.class);
	}
	
	@Test
	public void buildingPredicateReturnsTrueForBuildingProject() throws Exception {
		when(job.isBuilding()).thenReturn(true);
		
		assertThat(building.apply(job), is(true));
	}

	@Test
	public void buildingPredicateReturnsFalseForProjectThatIsNotBuilding() throws Exception {
		when(job.isBuilding()).thenReturn(false);

		assertThat(building.apply(job), is(false));
	}
	
	@Test
	public void failingPredicateReturnsTrueForFailingProject() throws Exception {
		when(job.isFailing()).thenReturn(true);

		assertThat(failing.apply(job), is(true));
	}
	
	@Test
	public void failingPredicateReturnsFalseForProjectThatIsNotFailing() throws Exception {
		when(job.isFailing()).thenReturn(false);

		assertThat(failing.apply(job), is(false));
	}

	@Test
	public void disabledPredicateReturnsTrueForDisabledProject() throws Exception {
		when(job.isEnabled()).thenReturn(false);
		
		assertThat(disabled.apply(job), is(true));
	}
	
	@Test
	public void disabledPredicateReturnsFalseForProjectThatIsEnabled() throws Exception {
		when(job.isEnabled()).thenReturn(true);

		assertThat(disabled.apply(job), is(false));
	}
}
