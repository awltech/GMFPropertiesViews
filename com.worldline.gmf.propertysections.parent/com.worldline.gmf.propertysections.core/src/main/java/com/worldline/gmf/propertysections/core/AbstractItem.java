package com.worldline.gmf.propertysections.core;

import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This Abstract Class role is to define Items. An AbstractItem is a Composite part
 * containing information, that can be created in Zones. A particularity of this
 * kind of Object, is to allow reloading and visibility setting in a process.
 * 
 * @author mvanbesien
 * @version $Revision: 1.1 $
 * @since $Date: 2007/10/02 11:53:17 $
 * 
 */
public abstract class AbstractItem {

	/**
	 * Composite Background.
	 */
	private Composite backGround = null;

	/**
	 * BackGround parent.
	 */
	private Composite parent = null;

	/**
	 * Tells if BackGround (full AbstractItem) should be visible or not.
	 */
	private boolean isVisible = true;

	/**
	 * FormData used be SubClasses to define this item's Objects layouts
	 */
	protected FormData fData;

	/**
	 * TabbedPropertySheetWidgetFactory Instance, used to create Elements.
	 */
	private TabbedPropertySheetWidgetFactory widgetFactory;

	/**
	 * Constructor
	 * 
	 * @param parent :
	 *            Parent in which this AbstractItem will be drawn
	 * @param widgetFactory :
	 *            Instance used to create Elements.
	 */
	public AbstractItem(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {

		this.parent = parent;
		this.widgetFactory = widgetFactory;

		this.backGround = widgetFactory.createComposite(this.parent);
		this.backGround.setLayout(new FormLayout());

		initElements();
		setLayoutsToElements();
		setListenersToElements();
	}

	/**
	 * Default method used to reload this AbstractItem
	 * 
	 */
	public void load() {
		makeVisible();
		if (isVisible())
			backGround.update();

	}

	/**
	 * Set the property holding the visibility parameter
	 * 
	 * @param visible :
	 *            should be true, to set this AbstractItem Visible, false otherwise
	 */
	public final void setVisible(boolean visible) {
		this.isVisible = visible;
		makeVisible();
	}

	/**
	 * Makes the AbstractItem visible or not, by using the isVisible property.
	 * 
	 */
	public final void makeVisible() {
		backGround.setEnabled(this.isVisible);
		backGround.setVisible(this.isVisible);
	}

	/**
	 * Tells if AbstractItem is visible
	 * 
	 * @return true if visible, false otherwise
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Method which purpose is to create and initialize all the graphical
	 * elements to be displayed in this AbstractItem
	 * 
	 */
	protected abstract void initElements();

	/**
	 * Method which purpose is to create and set the layout between all the
	 * graphical elements to be displayed in this AbstractItem
	 * 
	 */
	protected abstract void setLayoutsToElements();

	/**
	 * Method which purpose is to create and set the listeners to the graphical
	 * elements to be displayed in this AbstractItem
	 * 
	 */
	protected abstract void setListenersToElements();

	/**
	 * Method which purpose is to update the values of the elements to be
	 * displayed in this AbstractItem
	 * 
	 */
	protected abstract void updateValues();

	/**
	 * Gets this AbstractItem's background
	 * 
	 * @return Composite standing for AbstractItem's background
	 */
	public Composite getBackGround() {
		return backGround;
	}

	/**
	 * Gets the Widget Factory Instance
	 * 
	 * @return Widget Factory Instance
	 */
	protected TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}
}
