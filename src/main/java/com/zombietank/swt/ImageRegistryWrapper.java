package com.zombietank.swt;

import java.io.IOException;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

import com.google.common.base.Preconditions;

public class ImageRegistryWrapper implements DisposableBean {
	private final ImageRegistry imageRegistry;
	private final Display display;
	
	public ImageRegistryWrapper(Display display) {
		this.imageRegistry = new ImageRegistry();
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
	
	@Override
	public void destroy() throws Exception {
		imageRegistry.dispose();
	}
}
