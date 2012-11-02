package com.zombietank.swt;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OnDisposeRemoveEventListenerTest {
	private DisposeListener disposeListener;
	@Mock
	private EventListener eventListenerToRemove;
	@Mock
	private Listener listener;
	@Mock
	private Widget widget;
	private Event event;
	private DisposeEvent disposeEvent;
	
	@Before
	public void setup() {
		this.event = new Event();
		event.widget = widget;
		this.disposeEvent = new DisposeEvent(event);
		this.disposeListener = new OnDisposeRemoveEventListener(eventListenerToRemove);
	}
	
	@Test
	public void onDisposeRemoveEventListener() {
		when(eventListenerToRemove.getEventType()).thenReturn(42);
		when(eventListenerToRemove.getListener()).thenReturn(listener);
		
		disposeListener.widgetDisposed(disposeEvent);
		
		verify(widget).removeListener(42, listener);
	}
	
	@Test
	public void onDisposeRemoveDisposeListener() {
		when(eventListenerToRemove.getEventType()).thenReturn(42);
		when(eventListenerToRemove.getListener()).thenReturn(listener);
		
		disposeListener.widgetDisposed(disposeEvent);
		
		verify(widget).removeDisposeListener(disposeListener);
	}

	@Test
	public void doNothingWhenWidgetIsDisposed() {
		when(widget.isDisposed()).thenReturn(true);
		
		disposeListener.widgetDisposed(disposeEvent);
		
		verify(widget).isDisposed();
		verifyNoMoreInteractions(widget);
	}
}
