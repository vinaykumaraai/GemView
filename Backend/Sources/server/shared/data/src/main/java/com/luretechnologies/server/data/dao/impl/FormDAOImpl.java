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

import com.luretechnologies.server.data.dao.FormControlDAO;
import com.luretechnologies.server.data.dao.FormDAO;
import com.luretechnologies.server.data.model.survey.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class FormDAOImpl extends BaseDAOImpl<Form, Long> implements FormDAO {

    @Autowired
    FormControlDAO formControlDAO;

//    @Override
//    public List<Form> search(String search, int firstResult, int maxResult) throws PersistenceException {
//        Criteria criteria = createCriteria();
//
//        if (firstResult >= 0) {
//            criteria.setFirstResult(firstResult);
//            criteria.setMaxResults(maxResult);
//        }
//
//        if (search != null && !search.isEmpty()) {
//            Disjunction disjunction = (Disjunction) Restrictions.disjunction()
//                    .add(Restrictions.like("name", search, MatchMode.ANYWHERE).ignoreCase());
//            criteria.add(disjunction);
//        }
//
//        return criteria.list();
//    }
//    
//    @Override
//    public List<FormControl> getControls(long id) throws PersistenceException {
//        Criteria criteria = createCriteria(FormControl.class)
//                .createAlias("form", "form")
//                .add(Restrictions.eq("form.id", id));
//        return criteria.list();
//    }
//
//    @Override
//    public void addControl(FormControl formControl) throws PersistenceException {
//        formControlDAO.save(formControl);
//    }
//
//    @Override
//    public void updateControl(FormControl formControl) throws PersistenceException {
//        formControlDAO.update(formControl);
//    }
//
//    @Override
//    public void deleteControl(long idFormControl) throws PersistenceException {
//        formControlDAO.delete(idFormControl);
//    }
//
//    @Override
//    public Boolean isNotBeingUsed(long id) throws PersistenceException {
//        Criteria criteria = createCriteria(Page.class)
//                .createAlias("form", "form")
//                .add(Restrictions.eq("form.id", id));
//
//        return criteria.list().isEmpty();
//    }
}
