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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.PaymentProductDAO;
import com.luretechnologies.server.data.dao.TerminalProfileDAO;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.TerminalProfile;
import com.luretechnologies.server.service.TerminalProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author developer
 */
@Service
@Transactional
public class TerminalProfileServiceImpl implements TerminalProfileService {

    @Autowired
    private TerminalProfileDAO terminalProfileDAO;

    @Autowired
    private PaymentProductDAO productDAO;

    /**
     *
     * @param terminalProfile
     * @return
     * @throws Exception
     */
    @Override
    public TerminalProfile save(TerminalProfile terminalProfile) throws Exception {
        // If Update
        if (terminalProfile.getId() != 0) {
            TerminalProfile existentTerminalProfile = get(terminalProfile.getId());

            terminalProfile.setTerminalProfileApplications(existentTerminalProfile.getTerminalProfileApplications());
        }

        return terminalProfileDAO.merge(terminalProfile);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        terminalProfileDAO.delete(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public TerminalProfile get(long id) throws Exception {
        return terminalProfileDAO.findById(id);
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<TerminalProfile> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return terminalProfileDAO.list(firstResult, rowsPerPage);
    }

    /**
     *
     * @param id
     * @param idApp
     * @param idPaymentProfile
     * @return
     * @throws Exception
     */
    @Override
    public TerminalProfile addApplicationPaymentProfile(long id, long idApp, long idPaymentProfile) throws Exception {
        TerminalProfile terminalProfile = terminalProfileDAO.findById(id);
//        PaymentProduct product = productDAO.findById(idProduct);
//
//        // Check if exist a product with same id already registered for the terminal profile
//        for (PaymentProduct prod : terminalProfile.getPaymentProducts()) {
//            if (prod.getId() == idProduct) {
//                return terminalProfile;
//            }
//        }
//
//        terminalProfile.getPaymentProducts().add(product);

        return terminalProfileDAO.merge(terminalProfile);
    }

    /**
     *
     * @param id
     * @param idApp
     * @param idPaymentProfile
     * @return
     * @throws Exception
     */
    @Override
    public TerminalProfile deleteApplicationPaymentProfile(long id, long idApp, long idPaymentProfile) throws Exception {
        TerminalProfile terminalProfile = terminalProfileDAO.findById(id);

//        for (PaymentProduct prod : terminalProfile.getPaymentProducts()) {
//            if (prod.getId() == idProduct) {
//                terminalProfile.getPaymentProducts().remove(prod);
//
//                return terminalProfileDAO.merge(terminalProfile);
//            }
//        }
        throw new ObjectRetrievalFailureException(Application.class, idApp);
    }

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<TerminalProfile> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return terminalProfileDAO.search(filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(terminalProfileDAO.search(filter, -1, -1).size(), rowsPerPage);
    }
}