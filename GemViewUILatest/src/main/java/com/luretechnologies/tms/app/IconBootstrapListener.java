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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;

public class IconBootstrapListener implements BootstrapListener {

	private static final long serialVersionUID = 1L;
	
	protected String baseUri = "theme://icon-";
	protected String extension = ".png";
	protected String[] rels = { "icon", "apple-touch-icon" };
	protected int[] sizes = { 192, 96 };

	@Override
	public void modifyBootstrapFragment(BootstrapFragmentResponse response) {
		// NOP
	}

	@Override
	public void modifyBootstrapPage(BootstrapPageResponse response) {
		// Generate link-tags for "add to homescreen" icons
		final Document document = response.getDocument();
		final Element head = document.getElementsByTag("head").get(0);
		for (String rel : rels) {
			for (int size : sizes) {
				String iconUri = baseUri + size + extension;
				String href = response.getUriResolver().resolveVaadinUri(iconUri);
				String s = size + "x" + size;
				Element element = document.createElement("link");
				element.attr("rel", rel);
				element.attr("sizes", s);
				element.attr("href", href);
				head.appendChild(element);
			}
		}

		//- Enable these to hide browser controls when app is started from homescreen:
		Element element = document.createElement("meta");
		element.attr("name", "mobile-web-app-capable");
		element.attr("content", "yes");
		head.appendChild(element);

		element = document.createElement("meta");
		element.attr("name", "apple-mobile-web-app-capable");
		element.attr("content", "yes");
		head.appendChild(element);
		
	}

}