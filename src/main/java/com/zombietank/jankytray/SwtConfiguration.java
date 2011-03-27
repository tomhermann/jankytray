package com.zombietank.jankytray;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwtConfiguration {
	
	@Bean(destroyMethod="dispose")
	public Display display() {
		return new Display();
	}
	
	@Bean(destroyMethod="dispose")
	public Shell shell() {
		return new Shell(display());
	}
	
	@Bean
	public Tray tray() {
		return display().getSystemTray();
	}
}
