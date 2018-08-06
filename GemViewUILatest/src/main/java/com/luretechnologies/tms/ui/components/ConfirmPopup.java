/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.tms.ui.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ConfirmPopup {

	@Autowired
	public ConfirmDialogFactory confirmDialogFactory;

	/**
	 * Shows the standard before leave confirm dialog on given ui. If the user
	 * confirms the the navigation, the given {@literal runOnConfirm} will be
	 * executed. Otherwise, nothing will be done.
	 *
	 * @param view
	 *            the view in which to show the dialog
	 * @param runOnConfirm
	 *            the runnable to execute if the user presses {@literal confirm}
	 *            in the dialog
	 */
	public void showLeaveViewConfirmDialog(View view, Runnable runOnConfirm) {
		showLeaveViewConfirmDialog(view, runOnConfirm, () -> {
			// Do nothing on cancel
		});
	}

	/**
	 * Shows the standard before leave confirm dialog on given ui. If the user
	 * confirms the the navigation, the given {@literal runOnConfirm} will be
	 * executed. Otherwise, the given {@literal runOnCancel} runnable will be
	 * executed.
	 *
	 * @param view
	 *            the view in which to show the dialog
	 * @param runOnConfirm
	 *            the runnable to execute if the user presses {@literal confirm}
	 *            in the dialog
	 * @param runOnCancel
	 *            the runnable to execute if the user presses {@literal cancel}
	 *            in the dialog
	 */
	public void showLeaveViewConfirmDialog(View view, Runnable runOnConfirm, Runnable runOnCancel) {
		ConfirmDialog dialog = confirmDialogFactory.create("Please confirm",
				"You have unsaved changes that will be discarded if you navigate away.", "Discard Changes", "Cancel",
				null);
		dialog.show(view.getViewComponent().getUI(), event -> {
			if (event.isConfirmed()) {
				runOnConfirm.run();
			} else {
				runOnCancel.run();
			}
		}, true);
	}

}