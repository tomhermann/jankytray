package com.zombietank.jankytray;

import org.kohsuke.args4j.CmdLineParser;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.zombietank.jankytray.configuration.JankyConfiguration;

/***
 * Initializes Spring Context, which ultimately runs JankyTray.
 * 
 * @author Tom Hermann
 */
public final class Boot {
	
	public static void main(String... args) throws Exception {
		JankyOptions options = new JankyOptions();
		new CmdLineParser(options).parseArgument(args);
		new Boot().start(options);
	}

	private void start(JankyOptions options) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerSingleton("jankyOptions", options);
		GenericApplicationContext parentContext = new GenericApplicationContext(beanFactory);
		parentContext.refresh();

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.setParent(parentContext);
		context.register(JankyConfiguration.class);
		context.registerShutdownHook();
		context.refresh();
		context.start();		
	}
}
