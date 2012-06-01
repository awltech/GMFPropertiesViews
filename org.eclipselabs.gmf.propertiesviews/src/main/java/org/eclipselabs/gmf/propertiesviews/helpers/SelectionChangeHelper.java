package org.eclipselabs.gmf.propertiesviews.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

/**
 * TextChangeHelper notifies the listener of Selectionable widgets lifecycle
 * events on behalf of the widget(s) it listens to.
 * 
 * @author mvanbesien
 * @version $Revision: 1.1 $
 * @since $Date: 2007/10/02 11:53:17 $
 */
public abstract class SelectionChangeHelper extends AbstractControlChangeHelper {

	/**
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	public void handleEvent(Event event) {
		switch (event.type) {
		case SWT.Selection:
			buttonSelected((Control) event.widget);
			break;
		case SWT.DefaultSelection:
			buttonSelected((Control) event.widget);
			break;
		}
	}

	/**
	 * Abstract method notified when a text field has been changed.
	 * 
	 * @param control
	 *            the given control.
	 */
	public abstract void buttonSelected(Control control);

	/**
	 * Registers this helper with the given control to listen for events which
	 * indicate that a change is in progress (or done).
	 * 
	 * @param control
	 *            the given control.
	 */
	public void startListeningTo(Control control) {
		control.addListener(SWT.Selection, this);
		control.addListener(SWT.DefaultSelection, this);
	}

	/**
	 * Registers this helper with the given control to listen for the Enter key.
	 * When Enter is pressed, the change is considered done (this is only
	 * appropriate for single-line Text widgets).
	 * 
	 * @param control
	 *            the given control.
	 */
	public void startListeningForEnter(Control control) {
	}

	/**
	 * Unregisters this helper from a control previously passed to
	 * startListeningTo() and/or startListeningForEnter().
	 * 
	 * @param control
	 *            the given control.
	 */
	public void stopListeningTo(Control control) {
		if ((control != null) && !control.isDisposed()) {
			control.removeListener(SWT.Selection, this);
			control.removeListener(SWT.DefaultSelection, this);
		}
	}

	public void controlChanged(Control control) {
		buttonSelected(control);
	}
}