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
package com.luretechnologies.tms.backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.luretechnologies.tms.backend.data.entity.AbstractEntity;
import com.vaadin.data.provider.Query;

/**
 * 
 * @author Vinay
 *
 */

public abstract class CrudService<T extends AbstractEntity> {

	protected abstract Map<Long, T> getRepository();
	protected abstract List<T> getSavedList();

	public T save(T entity) {
		if(entity.getId()== null) {
			entity.setId(System.currentTimeMillis());
			getSavedList().add(entity);
		}
		getRepository().put(entity.getId(),entity);
		return entity;
	}

	public void delete(Long id) {
		getRepository().remove(id);
		
	}

	public T load(Long id) {
		return getRepository().get(id);
	}

	public abstract Stream<T> fetchFromBackEnd(Query<T, String> query);
	
	protected abstract int sizeInBackEnd(Query<T, String> query);
	
}


