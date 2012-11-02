package com.zombietank.swt;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnDisposeRemoveEventListener implements DisposeListener {
	private static final Logger logger = LoggerFactory.getLogger(OnDisposeRemoveEventListener.class);
	private final EventListener eventListenerToUnhook;

	public OnDisposeRemoveEventListener(EventListener eventListenerToRemove) {
		this.eventListenerToUnhook = eventListenerToRemove;
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		Widget widget = e.widget;
		if (!widget.isDisposed()) {
			widget.removeListener(eventListenerToUnhook.getEventType(), eventListenerToUnhook.getListener());
			widget.removeDisposeListener(this);
			logger.info("Removed listeners for {}", widget.toString());
		}
	}
}