package com.worldline.gmf.propertysections.core;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * 
 * This Class defines a generic AbstractZone. the AbstractZone is a subdivision of a AbstractSection
 * 
 * @author mvanbesien
 * @version $Revision: 1.2 $
 * @since $Date: 2008/12/02 14:52:27 $
 * 
 */
public abstract class AbstractZone {

	/**
	 * FormData, used to define the Zones position among others.
	 */
	protected FormData fData;

	/**
	 * Selected EObject
	 */
	private EObject eObject;

	/**
	 * Selected Graphical Edit Part
	 */
	private AbstractGraphicalEditPart editPart;

	/**
	 * TransactionalEditingDomain retrieved from current Opened Editor
	 */
	private TransactionalEditingDomain editingDomain;

	/**
	 * Composite standing for the AbstractZone
	 */
	private Composite zone;

	/**
	 * WidgetFactory to create Graphical Elements in properties views
	 */
	private TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();

	/**
	 * Constructor
	 * 
	 * @param parent :
	 *            parent composite
	 * @param isGroup :
	 *            true creates a Group, false creates a standard Composite.
	 */
	public AbstractZone(Composite parent, boolean isGroup) {

		zone = (isGroup) ? widgetFactory.createGroup(parent, "") : widgetFactory
				.createComposite(parent);
		zone.setLayout(new FormLayout());
	}

	/**
	 * @return AbstractZone in which graphical elements are created ("Background")
	 */
	public final Composite getZone() {
		return zone;
	}

	/**
	 * Initializes the zone by binding Graphical and Semantic objects to it.
	 * 
	 * @param eObject :
	 *            Selected EObject
	 * @param editPart :
	 *            Selected AbstractGraphicalEditPart
	 * @param editingDomain :
	 *            This Editor's TransactionalEditingDomain
	 */
	public final void init(EObject eObject, AbstractGraphicalEditPart editPart,
			TransactionalEditingDomain editingDomain) {
		this.eObject = eObject;
		this.editPart = editPart;
		this.editingDomain = editingDomain;
	}

	/**
	 * Method which purpose is to define and create all graphical Elements for a
	 * AbstractZone.
	 */
	public abstract void addItemsToZone();

	/**
	 * Method which purpose is to define and apply layouts to the graphical
	 * Elements for a AbstractZone
	 */
	public abstract void addLayoutsToItems();

	/**
	 * Method which purpose is to define and apply listeners to the graphical
	 * Elements for a AbstractZone
	 */
	public abstract void addListenersToItems();

	/**
	 * Methodwhich purpose is to update the values of the graphical Elements for
	 * a AbstractZone
	 */
	public abstract void updateItemsValues();

	/**
	 * @return TransactionalEditingDomain, retrieved from Opened Diagram
	 */
	protected final TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * @return current selected Edit Part in Diagram
	 */
	protected final AbstractGraphicalEditPart getEditPart() {
		return editPart;
	}

	/**
	 * @return selected EObject beyond the selected EditPart in the Diagram
	 */
	protected final EObject getEObject() {
		return eObject;
	}

	/**
	 * If this AbstractZone is a Group, applies a Title to it.
	 * 
	 * @param title :
	 *            Group Title
	 */
	protected final void setText(String title) {
		if (zone instanceof Group)
			((Group) zone).setText(title);
	}

	/**
	 * @return The Widget Factory
	 */
	protected final TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	/**
	 * Refreshes the current AbstractZone, such as the EditPart bound to this AbstractZone.
	 * 
	 */
	protected void refreshZoneAndDiagram() {
		updateItemsValues();
		refreshEditPart();
	}

	/**
	 * Refreshes the current EditPart Also refreshes direct children of this
	 * EditPart
	 */
	protected final void refreshEditPart() {
		if (getEditPart() != null && getEditPart().getParent() != null
				&& getEditPart().getParent().getRoot() != null && getEditPart().getViewer() != null) {
			getEditPart().refresh();
			List<?> children = getEditPart().getChildren();
			for (Object child : children) {
				if (child != null && child instanceof AbstractGraphicalEditPart) {
					AbstractGraphicalEditPart agep = (AbstractGraphicalEditPart) child;
					if (agep.getRoot() != null && agep.getViewer() != null) {
						agep.refresh();
					}
				}
			}
		}
	}
}
