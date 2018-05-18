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
package com.luretechnologies.server.service;

import com.luretechnologies.server.data.model.tms.TerminalProfile;
import java.util.List;

/**
 *
 *
 * @author developer
 */
public interface TerminalProfileService {

    /**
     *
     * @param terminalProfile
     * @return
     * @throws Exception
     */
    public TerminalProfile save(TerminalProfile terminalProfile) throws Exception;

    /**
     *
     * @param id
     * @throws Exception
     */
    public void delete(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public TerminalProfile get(long id) throws Exception;

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<TerminalProfile> list(int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param id
     * @param idApp
     * @param idPaymentProfile
     * @return
     * @throws Exception
     */
    public TerminalProfile addApplicationPaymentProfile(long id, long idApp, long idPaymentProfile) throws Exception;

    /**
     *
     * @param id
     * @param idApp
     * @param idPaymentProfile
     * @return
     * @throws Exception
     */
    public TerminalProfile deleteApplicationPaymentProfile(long id, long idApp, long idPaymentProfile) throws Exception;

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<TerminalProfile> search(String filter, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param search
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public int getSearchTotalPages(String search, int rowsPerPage) throws Exception;
}
