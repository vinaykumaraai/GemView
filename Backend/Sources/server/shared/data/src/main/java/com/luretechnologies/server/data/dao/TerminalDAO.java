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
package com.luretechnologies.server.data.dao;

import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import com.luretechnologies.server.data.model.payment.TerminalSettingValue;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 *
 * @author developer
 */
public interface TerminalDAO extends BaseDAO<Terminal, Long> {

    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public Criteria search(String filter, int firstResult, int maxResult, long id) throws PersistenceException;
    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
//    public List<Terminal> searchTree(String filter, int firstResult, int maxResult) throws PersistenceException;
    /**
     *
     * @param filter
     * @param firstResult
     * @param maxResult
     * @param parentEntity
     * @param idEntity
     * @return
     * @throws PersistenceException
     */
//    public Criteria searchChild(String filter, int firstResult, int maxResult, Class parentEntity, long idEntity) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public Terminal get(long id) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Terminal> list(Entity entity, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param serialNumber
     * @return
     * @throws PersistenceException
     */
    public Terminal findBySerialNumber(String serialNumber) throws PersistenceException;

    /**
     *
     * @param terminalId
     * @return
     * @throws PersistenceException
     */
    public Terminal findByTerminalId(String terminalId) throws PersistenceException;

    /**
     *
     * @param entity
     * @param name
     * @param serialNumber
     * @param active
     * @return
     * @throws PersistenceException
     */
    public List<Terminal> list(Entity entity, String name, String serialNumber, Boolean active) throws PersistenceException;

    /**
     *
     * @param entity
     * @param filter
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Terminal> search(Entity entity, String filter, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param terminalId
     * @return
     * @throws PersistenceException
     */
    public List<TerminalHost> listHosts(String terminalId) throws PersistenceException;

    /**
     *
     * @param terminalId
     * @param host
     * @return
     * @throws PersistenceException
     */
    public List<TerminalHostSettingValue> listHostSettings(String terminalId, Host host) throws PersistenceException;

    /**
     *
     * @param terminalId
     * @param host
     * @return
     * @throws PersistenceException
     */
    public TerminalHost findByTerminalHost(String terminalId, Host host) throws PersistenceException;

    /**
     *
     * @param terminalId
     * @param host
     * @param terminalHostSetting
     * @return
     * @throws PersistenceException
     */
    public TerminalHostSettingValue findByTerminalHostAndSetting(String terminalId, Host host, TerminalHostSetting terminalHostSetting) throws PersistenceException;

    /**
     *
     * @param terminalId
     * @return
     * @throws PersistenceException
     */
    public List<TerminalSettingValue> listTerminalSettings(String terminalId) throws PersistenceException;

    /**
     *
     * @param serialNumber
     * @return
     * @throws PersistenceException
     */
//    public EntityRelation getEntityRelation(String serialNumber) throws PersistenceException;
    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
//    public List<Merchant> getPrevMerchants(long id) throws PersistenceException;
    /**
     *
     * @param id
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
//    public List<Statistic> getStatistics(long id, String filter, int firstResult, int maxResult) throws PersistenceException;
    /**
     *
     * @param id
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
//    public List<Diagnostic> getDiagnostics(long id, String filter, int firstResult, int maxResult) throws PersistenceException;
    /**
     *
     * @param terminalSerialNumber The terminal serial number
     * @return
     * @throws PersistenceException
     */
    Terminal getBySerialNumber(String terminalSerialNumber) throws PersistenceException;
}
