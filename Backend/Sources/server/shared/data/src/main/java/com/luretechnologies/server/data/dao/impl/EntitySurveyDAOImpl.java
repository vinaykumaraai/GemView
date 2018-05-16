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

import com.luretechnologies.server.data.dao.EntitySurveyDAO;
import com.luretechnologies.server.data.model.EntitySurvey;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class EntitySurveyDAOImpl extends BaseDAOImpl<EntitySurvey, Long> implements EntitySurveyDAO {

//    @Override
//    public void resetStartingSurvey(EntitySurvey entitySurvey) throws PersistenceException {
//        Criteria criteria = createCriteria()
//                .createAlias("survey", "survey")
//                .add(Restrictions.eq("startingSurvey", true))
//                .add(Restrictions.ne("survey.id", entitySurvey.getSurvey().getId()));
//
//        if (entitySurvey.getOrganization() != null) {
//            criteria.createAlias("organization", "organization")
//                    .add(Restrictions.eq("organization.id", entitySurvey.getOrganization().getId()));
//        } else if (entitySurvey.getRegion() != null) {
//            criteria.createAlias("merchant", "merchant")
//                    .add(Restrictions.eq("region.id", entitySurvey.getRegion().getId()));
//        } else if (entitySurvey.getMerchant() != null) {
//            criteria.createAlias("region", "region")
//                    .add(Restrictions.eq("merchant.id", entitySurvey.getMerchant().getId()));
//        }
//
//        if (criteria.uniqueResult() != null) {
//            EntitySurvey startingEntitySurvey = (EntitySurvey) criteria.uniqueResult();
//            startingEntitySurvey.setStartingSurvey(false);
//
//            update(startingEntitySurvey);
//        }
//    }
}
