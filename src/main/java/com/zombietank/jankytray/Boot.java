package com.zombietank.jankytray;

import org.kohsuke.args4j.CmdLineParser;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/***
 * Initializes Spring Context, which ultimately runs JankyTray.
 * 
 * @author Tom Hermann
 */
public final class Boot {
	private static final String[] CONFIG_LOCATIONS = new String[] { "META-INF/spring/root-context.xml" };

	public static void main(String[] args) throws Exception {
		// Handle command line options
		JankyOptions options = new JankyOptions();
		new CmdLineParser(options).parseArgument(args);

		// Set up new application context containing options.
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerSingleton("jankyOptions", options);
		GenericApplicationContext parentContext = new GenericApplicationContext(
				beanFactory);
		parentContext.refresh();

		// Augment XML based context
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				CONFIG_LOCATIONS, parentContext);
		context.registerShutdownHook();
	}
}
