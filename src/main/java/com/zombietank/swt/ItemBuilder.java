package com.zombietank.swt;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import com.google.common.collect.Lists;
import com.zombietank.support.Builder;

public abstract class ItemBuilder<T extends Item> implements Builder<T> {
	protected final List<EventListener> listeners = Lists.newArrayListWithCapacity(2);
	protected final Widget parent;
	protected int style = SWT.NORMAL;
	protected String text;
	protected Image image;

	public ItemBuilder(final Widget parent) {
		this.parent = parent;
	}

	protected abstract T createItem(final Widget parent, final int style);

	public T build() {
		final T item = createItem(parent, style);

		if (hasText()) {
			item.setText(text);
		}

		if (hasImage()) {
			item.setImage(image);
		}

		if (hasListeners()) {
			for (EventListener eventListener : listeners) {
				item.addListener(eventListener.getEventType(), eventListener.getListener());
				item.addDisposeListener(new OnDisposeRemoveEventListener(eventListener));
			}
		}
		return item;
	}

	public ItemBuilder<T> withStyle(int style) {
		this.style = style;
		return this;
	}

	public ItemBuilder<T> withText(String text) {
		this.text = text;
		return this;
	}

	public ItemBuilder<T> withImage(Image image) {
		this.image = image;
		return this;
	}

	public ItemBuilder<T> withListener(int eventType, Listener listener) {
		listeners.add(new EventListener(eventType, listener));
		return this;
	}

	public ItemBuilder<T> selectionListener(Listener listener) {
		return withListener(SWT.Selection, listener);
	}

	protected boolean hasText() {
		return null != text && !"".equals(text);
	}

	protected boolean hasImage() {
		return this.image != null;
	}

	protected boolean hasListeners() {
		return !listeners.isEmpty();
	}

	protected void clearListeners() {
		listeners.clear();
	}
}
