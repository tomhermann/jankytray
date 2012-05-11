package com.zombietank.jankytray;

import javax.inject.Inject;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.springframework.stereotype.Component;

@Component
public class JankyWidgetContext {
	private final Display display;
	private final Shell shell;
	private final Tray tray;
	
	@Inject 
	public JankyWidgetContext(Display display, Shell shell, Tray tray) {
		this.display = display;
		this.shell = shell;
		this.tray = tray;
	}

	public Display getDisplay() {
		return display;
	}

	public Shell getShell() {
		return shell;
	}

	public Tray getTray() {
		return tray;
	}
}
