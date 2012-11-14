package com.zombietank.swt;

import java.io.IOException;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.springframework.core.io.Resource;

import com.google.common.base.Preconditions;

public class ImageRegistryWrapper {
	private final ImageRegistry imageRegistry;
	private final Display display;
	
	public ImageRegistryWrapper(Display display) {
		this.imageRegistry = new ImageRegistry(display);
		this.display = display;
	}
	
	public void put(Object key, Resource image) throws IOException {
		Preconditions.checkNotNull(key, "Keys may not be null.");
		Image imageInstance = new Image(display, image.getInputStream());
		imageRegistry.put(key.toString(), imageInstance);
	}
	
	public Image get(Object key) {
		Preconditions.checkNotNull(key, "Keys may not be null.");
		return imageRegistry.get(key.toString());
	}
}
