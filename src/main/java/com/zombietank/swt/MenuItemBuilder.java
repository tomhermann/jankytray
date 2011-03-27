package com.zombietank.swt;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;

public class MenuItemBuilder extends ItemBuilder<MenuItem> {
	
	public MenuItemBuilder(final Widget parent) {
		super(parent);
	}

	@Override
	protected MenuItem createItem(Widget parent, int style) {
		return new MenuItem((Menu)parent, style);
	}
}
