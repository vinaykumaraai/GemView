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

import com.luretechnologies.server.data.dao.HostDAO;
import com.luretechnologies.server.data.model.payment.Host;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
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
public class HostDAOImpl extends BaseDAOImpl<Host, Long> implements HostDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HostDAOImpl.class);

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Host findByKey(String key) {
        return (Host) findByProperty("key", key).getSingleResult();
    }

    @Override
    public List<Host> listOrdered() {
        CriteriaQuery<Host> q = criteriaQuery();
        Root root = q.from(Host.class);
        q.select(root);
        return query(q.orderBy(criteriaBuilder().asc(root.get("name")))).getResultList();
    }

    //temporal
    /*@Override
    public Host merge(Host host) throws PersistenceException {
        super.merge(host);

        List<MerchantHost> merchantHosts = merchantHostDAO.list();
        for (MerchantHost merchantHost : merchantHosts) {
            List<MerchantHostSetting> merchantHostSettings = merchantHostSettingDAO.findByProperty("host", merchantHost.getHost()).getResultList();
            for (MerchantHostSetting merchantHostSetting : merchantHostSettings) {
                MerchantHostSettingValue merchantHostSettingValue = merchantHostSettingValueDAO.getMerchantHostSettingValue(merchantHost.getId(), merchantHostSetting.getId());
                if (merchantHostSettingValue == null) {
                    merchantHostSettingValue = new MerchantHostSettingValue();
                    merchantHostSettingValue.setMerchantHost(merchantHost);
                    merchantHostSettingValue.setSetting(merchantHostSetting);
                    merchantHostSettingValue.setValue("");
                    merchantHostSettingValueDAO.persist(merchantHostSettingValue);
                }
            }
        }

        return host;
    }*/
}
