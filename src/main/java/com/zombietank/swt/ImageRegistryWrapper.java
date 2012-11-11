package com.zombietank.swt;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class ImageRegistryWrapper implements DisposableBean {
	private final ImageRegistry imageRegistry;
	private final Display display;
	
	public ImageRegistryWrapper(Display display) {
		this.imageRegistry = new ImageRegistry();
		this.display = display;
	}
	
	public void putAll(Map<Object, Resource> keyedImages) throws IOException {
		for (Entry<Object, Resource> entry : keyedImages.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}
	
	public void put(Object key, Resource image) throws IOException {
		Image imageInstance = new Image(display, image.getInputStream());
		imageRegistry.put(key.toString(), imageInstance);
	}
	
	public Image get(Object key) {
		return imageRegistry.get(key.toString());
	}
	
	@Override
	public void destroy() throws Exception {
		imageRegistry.dispose();
	}
}
