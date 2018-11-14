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

package com.luretechnologies.tms.app;

import org.springframework.stereotype.Component;

import com.vaadin.server.ServiceInitEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServiceInitListener;

/**
 * Configures the VaadinService instance that serves the app through a servlet.
 * <p>
 * Uses a bootstrap listener to modify the bootstrap HTML page and include icons
 * for home screen for mobile devices.
 */

/**
 * 
 * @author Vinay
 *
 */

@Component
public class ApplicationInitListener implements VaadinServiceInitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serviceInit(ServiceInitEvent serviceInitEvent) {
		VaadinService service = serviceInitEvent.getSource();

		service.addSessionInitListener(event -> event.getSession().addBootstrapListener(new IconBootstrapListener()));
	}
}
