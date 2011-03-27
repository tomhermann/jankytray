package com.zombietank.jankytray;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JankyWidgetContext {
	private final Display display;
	private final Shell shell;
	private final Tray tray;
	
	@Autowired 
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
