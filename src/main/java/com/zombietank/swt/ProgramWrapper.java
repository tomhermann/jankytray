package com.zombietank.swt;

import org.eclipse.swt.program.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProgramWrapper {
	private static final Logger log = LoggerFactory.getLogger(ProgramWrapper.class);
	
	public boolean launch(final Object command) {
		log.debug("Launching: " + command);
		return Program.launch(command.toString());
	}
}
