package com.zombietank.swt;

import org.eclipse.swt.widgets.Listener;

public class EventListener {
	private final int eventType;
	private final Listener listener;
	
	public EventListener(final int eventType, final Listener listener) {
		this.eventType = eventType;
		this.listener = listener;
	}

	public int getEventType() {
		return eventType;
	}

	public Listener getListener() {
		return listener;
	}
}
