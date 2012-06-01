package org.eclipselabs.gmf.propertiesviews.tools;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

/**
 * Lite Label Provider, for XA Common properties views
 * 
 * @author mvanbesien
 * @version $Revision: 1.4 $
 * @since $Date: 2008/02/07 14:29:11 $
 * 
 */
public abstract class LiteLabelProvider extends org.eclipse.jface.viewers.LabelProvider {

	/**
	 * Map containing bindings between ESF and Classes
	 */
	private Map<Class<?>, EStructuralFeature> bindings;

	/**
	 * Retrieves the Image associated To EClass passed as parameter.
	 * 
	 * @param eClass :
	 *            EClass
	 * @return Image
	 */
	public abstract Image getImageFromEClass(EClass eClass);

	/**
	 * Constructor
	 * 
	 */
	public LiteLabelProvider() {
		super();
		this.bindings = new LinkedHashMap<Class<?>, EStructuralFeature>();
		this.bindObjectsAndLabels();
	}

	/**
	 * Defines the mappings between an object and its label.
	 * 
	 * @see bind method
	 * 
	 */
	public abstract void bindObjectsAndLabels();

	/**
	 * Retrieves the EPackage in which the Features are contained
	 * 
	 * @return EPackage
	 */
	public abstract EPackage getEPackage();

	/**
	 * Retrieves the Image associated with Object passed as parameter.
	 */
	@Override
	public final Image getImage(Object element) {
		if (element == null)
			return null;

		EObject eObject = this.convertToEMF(element);
		if (eObject == null)
			return null;
		EClass eObjectClass = eObject.eClass();
		if (eObjectClass == null)
			return null;
		return this.getImageFromEClass(eObjectClass);
	}

	/**
	 * Retrieves the label associated with Object passed as parameter
	 */
	@Override
	public final String getText(Object element) {
		if (element instanceof IStructuredSelection)
			element = ((IStructuredSelection) element).getFirstElement();

		EObject eObject = this.convertToEMF(element);
		if (eObject == null)
			return "";
		String className = "<" + eObject.eClass().getName() + ">";

		EStructuralFeature feature = null;
		for (Iterator<Class<?>> i = this.bindings.keySet().iterator(); i.hasNext() && feature == null;) {
			Class<?> clazz = i.next();
			if (clazz.isInstance(eObject))
				feature = this.bindings.get(clazz);
		}
		if (feature == null)
			return className;
		else {
			this.addModifyAdapter(eObject, feature);
			Object elementName = eObject.eGet(feature);
			return className + " " + (elementName == null ? "" : elementName.toString());
		}

	}

	/**
	 * Returns whether the label would be affected by a change to the given
	 * property of the given element. This can be used to optimize a
	 * non-structural viewer update. If the property mentioned in the update
	 * does not affect the label, then the viewer need not update the label.
	 * 
	 * This methods returns true by default.
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return <code>true</code> if the label would be affected, and
	 *         <code>false</code> if it would be unaffected
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	/**
	 * Event object instance describing a label provider state change.
	 * 
	 * @see ILabelProviderListener
	 */
	private LabelProviderChangedEvent event = new LabelProviderChangedEvent(this);

	/**
	 * Adapter used to update text values if changed in Editor.
	 * 
	 * @param eObject :
	 *            EObject containing the value
	 * @param feature :
	 *            EStructuralFeature defining the relationship between the
	 *            EObject and the Value to update.
	 */
	private final void addModifyAdapter(EObject eObject, final EStructuralFeature feature) {

		Adapter modifyAdapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (msg != null && msg.getFeature() != null && feature != null
						&& msg.getFeature().equals(feature))
					LiteLabelProvider.this.fireLabelProviderChanged(LiteLabelProvider.this.event);
			}
		};

		if (eObject.eAdapters().indexOf(modifyAdapter) == -1)
			eObject.eAdapters().add(modifyAdapter);
	}

	/**
	 * Method used to retrieve the EObject linked with the Object passed as
	 * parameter
	 * 
	 * @param object :
	 *            Object from which EObject has to be revealed
	 * @return EObject
	 */
	private final EObject convertToEMF(Object object) {
		EObject eObject = null;
		Object element = null;
		if (object instanceof ISelection)
			element = ((IStructuredSelection) object).getFirstElement();
		else
			element = object;
		if (element instanceof GraphicalEditPart) {
			AbstractGraphicalEditPart myGEP = (AbstractGraphicalEditPart) element;
			Object model = myGEP.getModel();
			if (model instanceof View)
				eObject = ((View) model).getElement();
			return eObject;
		} else if (element instanceof EObject)
			return (EObject) element;
		else
			return null;
	}

	public final void bind(Class<?> clazz, EStructuralFeature feature) {
		this.bindings.put(clazz, feature);
	}
}
