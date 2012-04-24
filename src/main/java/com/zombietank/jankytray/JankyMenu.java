package com.zombietank.jankytray;

import static com.zombietank.support.InvokingListener.invokingListener;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
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
	private final JenkinsApiService jenkinsService;
	private final JankyOptions options;
	private final JankyWidgetContext context;
	private final Menu menu;
	@Inject
	private StatusImages statusImages;
	@Inject
	private ProgramWrapper programWrapper;

	@Autowired
	public JankyMenu(JenkinsApiService jenkinsService, JankyOptions options, JankyWidgetContext context) {
		this.jenkinsService = jenkinsService;
		this.options = options;
		this.context = context;
		this.menu = new Menu(context.getShell());
	}

	public Status refresh() throws JenkinsServiceException {
		List<Job> allJobs = jenkinsService.fetch(options.getJenkinsUrl()).getJobs();
		clear();
		addJobs(allJobs);
		addOtherItems();
		return Status.of(allJobs);
	}

	public void display() {
		menu.setVisible(true);
	}

	public void destroy() throws Exception {
		log.info("Destroying menu.");
		menu.dispose();
	}

	private void clear() {
		for (MenuItem item : menu.getItems()) {
			item.dispose();
		}
	}

	private void addJobs(List<Job> jobs) {
		for (Job job : jobs) {
			new MenuItemBuilder(menu)
				.withText(job.getName())
				.withListener(SWT.Selection, invokingListener(programWrapper, "launch", job.getUrl()))
				.withImage(statusImages.get(Status.of(job))).build();
		}
	}

	private void addOtherItems() {
		new MenuItemBuilder(menu).withStyle(SWT.SEPARATOR).build();
		new MenuItemBuilder(menu)
				.withText("Exit")
				.withListener(SWT.Selection, invokingListener(context.getShell(), "dispose"))
				.build();
	}
}
