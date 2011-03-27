package com.zombietank.jankytray;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

import com.zombietank.jenkins.model.Status;

/***
 * Maintains mapping between status and the associated image. See xml configuration.
 * 
 * @author Tom Hermann
 */
public final class StatusImages implements DisposableBean {
	private static final Logger log = LoggerFactory.getLogger(StatusImages.class);
	private final ConcurrentHashMap<Status, Image> repository;
	
	public StatusImages(final Device display, final Map<Status, Resource> images) throws IOException {
		repository = new ConcurrentHashMap<Status, Image>(images.size());
		for (Entry<Status, Resource> entry : images.entrySet()) {
			repository.put(entry.getKey(), new Image(display, entry.getValue().getInputStream()));
		}
	}
	
	public Image get(Status status) {
		return repository.get(status);
	}
	
	public void destroy() throws Exception {
		log.info("Destroying status images.");
		for (Entry<Status, Image> entry : repository.entrySet()) {
			entry.getValue().dispose();
		}
		repository.clear();
	}
}
