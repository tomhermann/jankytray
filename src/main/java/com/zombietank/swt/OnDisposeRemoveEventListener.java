package com.zombietank.swt;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnDisposeRemoveEventListener implements DisposeListener {
	private static final Logger logger = LoggerFactory.getLogger(OnDisposeRemoveEventListener.class);
	private final EventListener eventListener;

	public OnDisposeRemoveEventListener(EventListener eventListenerToRemove) {
		this.eventListener = eventListenerToRemove;
	}

	@Override
	public void widgetDisposed(DisposeEvent event) {
		Widget widget = event.widget;
		if (!widget.isDisposed()) {
			widget.removeListener(eventListener.getEventType(), eventListener.getListener());
			widget.removeDisposeListener(this);
			logger.debug("Removed listeners for {}", widget.toString());
		}
	}
}