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
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.server.data.dao.AddressDAO;
import com.luretechnologies.server.data.model.payment.Address;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class AddressDAOImpl extends BaseDAOImpl<Address, Long> implements AddressDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDAOImpl.class);

    @Override
    public List<Address> getAddressNoRelations(Collection<Address> excludes) {
        List<Address> addresses = new ArrayList<>();
        CriteriaQuery<Address> query = criteriaQuery();
        Root<Address> root = getRoot(query);
        root.fetch("merchants", JoinType.LEFT);
        
        Predicate prdcts = criteriaBuilder().conjunction();
        for (Address exclude : excludes) {
            prdcts.getExpressions().add(criteriaBuilder().notEqual(root.<Long>get("id"), exclude.getId()));
        }
        
        List<Address> all = query(query.where(prdcts)).getResultList();
        for (Address item : all) {
            if(item.getMerchants().isEmpty()) {
                addresses.add(item);
            }
        }
        return addresses;
    }

    @Override
    public List<Address> getAddressNoRelations() {
        return getAddressNoRelations(new ArrayList<Address>());
    }

}
