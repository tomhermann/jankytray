package com.zombietank.jankytray;

import static com.zombietank.support.InvokingListener.invokingListener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import com.zombietank.jenkins.JenkinsApiService;
import com.zombietank.jenkins.JenkinsServiceException;
import com.zombietank.jenkins.model.Job;
import com.zombietank.jenkins.model.Status;
import com.zombietank.swt.MenuItemBuilder;
import com.zombietank.swt.ProgramWrapper;

/***
 * The right-click pop-up menu, which has jobs, a separator, and an exit option.
 * 
 * @author Tom Hermann
 */
@Component
public class JankyMenu implements DisposableBean {
	private static final Logger log = LoggerFactory.getLogger(JankyMenu.class);
	@Inject private JankyOptions options;
	@Inject private JenkinsApiService jenkinsService;
	@Inject private ConfigurationDialog configurationDialog;
	@Inject	private StatusImages statusImages;
	@Inject	private ProgramWrapper programWrapper;
	private final JankyWidgetContext context;
	private final Menu menu;
	private final Map<Job, MenuItem> jobMenu = Collections.synchronizedMap(new HashMap<Job, MenuItem>());
	
	@Inject
	public JankyMenu(JankyWidgetContext context) {
		this.context = context;
		this.menu = new Menu(context.getShell());
	}

	public Status refresh() {
		Collection<Job> jobs = Collections.emptyList();
		try {
			jobs = jenkinsService.fetch(options.getJenkinsUrl()).getJobs();
		} catch (JenkinsServiceException e) {
			log.error("Error fetching Jobs", e);
			clear();
			new MenuItemBuilder(menu).withText("Error contacting Jenkins.").build();
			addOtherItems();
			return Status.UNKNOWN;
		}
		return handleJobs(jobs);
	}

	public void display() {
		menu.setVisible(true);
	}

	public void destroy() throws Exception {
		log.info("Destroying menu.");
		menu.dispose();
	}

	private Status handleJobs(Collection<Job> jobs) {
		if(noNewJobs(jobs)) {
			updateJobStatuses(jobs);
		} else {
			redrawMenu(jobs);
		}
		return Status.of(jobs);
	}

	private boolean noNewJobs(Collection<Job> jobs) {
		return jobMenu.size() == jobs.size() && jobMenu.keySet().containsAll(jobs);
	}

	private void updateJobStatuses(Collection<Job> jobs) {
		log.debug("Updating menu, {} jobs.", jobs.size());
		for (Job job : jobs) {
			jobMenu.get(job).setImage(statusImages.get(job.getStatus()));
		}
	}

	private void redrawMenu(Collection<Job> jobs) {
		log.debug("Redrawing menu, {} jobs.", jobs.size());
		clear();
		for (Job job : jobs) {
			MenuItem menuItem = new MenuItemBuilder(menu)
				.withText(job.getName())
				.withListener(SWT.Selection,invokingListener(programWrapper, "launch",job.getUrl()))
				.withImage(statusImages.get(job.getStatus()))
			.build();
			jobMenu.put(job, menuItem);
		}
		addOtherItems();
	}

	private void clear() {
		jobMenu.clear();
		for (MenuItem item : menu.getItems()) {
			item.dispose();
		}
	}

	private void addOtherItems() {
		new MenuItemBuilder(menu).withStyle(SWT.SEPARATOR).build();
		new MenuItemBuilder(menu).withText("Settings").withListener(SWT.Selection, invokingListener(configurationDialog, "open")).build();
		new MenuItemBuilder(menu).withText("Exit").withListener(SWT.Selection, invokingListener(context.getShell(), "dispose")).build();
	}
}
