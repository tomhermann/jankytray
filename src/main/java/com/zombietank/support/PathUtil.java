package com.zombietank.support;

import java.io.File;

import com.zombietank.jankytray.Boot;

public class PathUtil {

	public static File getApplicationPath() {
		return new File(Boot.class.getProtectionDomain().getCodeSource().getLocation().getFile());
	}
	
	private PathUtil() {
	}
}
