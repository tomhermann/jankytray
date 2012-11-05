package com.zombietank.jankytray;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/***
 * Initializes Spring Context, which ultimately runs JankyTray.
 * 
 * @author Tom Hermann
 */
public final class Boot {
	
	public static void main(String... args) throws Exception {
		new Boot().start();
	}

	private void start() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(JankyConfiguration.class);
		context.refresh();
		context.registerShutdownHook();
		context.start();
	}
}
