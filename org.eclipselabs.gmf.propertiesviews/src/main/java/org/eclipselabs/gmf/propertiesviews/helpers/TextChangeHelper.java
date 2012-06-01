package org.eclipselabs.gmf.propertiesviews.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

/**
 * TextChangeHelper notifies the listener of Text lifecycle events on behalf of
 * the widget(s) it listens to.
 * 
 * @author mvanbesien
 * @version $Revision: 1.1 $
 * @since $Date: 2007/10/02 11:53:17 $
 */
public abstract class TextChangeHelper extends AbstractControlChangeHelper {

	/**
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	public void handleEvent(Event event) {
		switch (event.type) {
		case SWT.KeyDown:
			if (event.character == SWT.CR)
				textChanged((Control) event.widget);
			break;
		case SWT.FocusOut:
			textChanged((Control) event.widget);
			break;
		}
	}

	/**
	 * Abstract method notified when a text field has been changed.
	 * 
	 * @param control
	 *            the given control.
	 */
	public abstract void textChanged(Control control);

	/**
	 * Registers this helper with the given control to listen for events which
	 * indicate that a change is in progress (or done).
	 * 
	 * @param control
	 *            the given control.
	 */
	public void startListeningTo(Control control) {
		control.addListener(SWT.FocusOut, this);
		control.addListener(SWT.Modify, this);
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
		// NOTE: KeyDown rather than KeyUp, because of similar usage in CCombo.
		control.addListener(SWT.KeyDown, this);
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
			control.removeListener(SWT.FocusOut, this);
			control.removeListener(SWT.Modify, this);
			control.removeListener(SWT.KeyDown, this);
		}
	}

	public void controlChanged(Control control) {
		textChanged(control);
	}
}