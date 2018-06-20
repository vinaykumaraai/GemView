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

import com.luretechnologies.common.enums.TerminalSettingEnum;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import com.luretechnologies.server.data.model.payment.TerminalSettingValue;
import com.luretechnologies.server.data.model.tms.KeyBlock;
import java.util.List;

/**
 *
 */
public interface TerminalService {

    /**
     *
     * @param terminal
     * @return
     * @throws Exception
     */
    public Terminal create(Terminal terminal) throws Exception;

    /**
     *
     * @param terminalId
     * @return
     * @throws Exception
     */
    public Terminal get(String terminalId) throws Exception;

    /**
     *
     * @param terminalId
     * @param terminal
     * @return
     * @throws Exception
     */
    public Terminal update(String terminalId, Terminal terminal) throws Exception;

    /**
     *
     * @param terminalId
     * @throws Exception
     */
    public void delete(String terminalId) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Terminal findById(long id) throws Exception;

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Terminal> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param serialNumber
     * @param key
     * @return
     * @throws Exception
     */
    public Terminal addKey(String serialNumber, KeyBlock key) throws Exception;

    /**
     *
     * @param serialNumber
     * @param idApp
     * @param idProfile
     * @throws Exception
     */
    public void addPaymentApplication(String serialNumber, long idApp, long idProfile) throws Exception;

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Terminal> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception;

    /**
     *
     * @param entity
     * @param name
     * @param serialNumber
     * @param active
     * @return
     * @throws Exception
     */
    public List<Terminal> list(Entity entity, String name, String serialNumber, Boolean active) throws Exception;

    /**
     *
     * @param terminalId
     * @return
     * @throws Exception
     */
    public List<Host> listAvailableHosts(String terminalId) throws Exception;

    /**
     *
     * @param terminalId
     * @param hostId
     * @return
     * @throws Exception
     */
    public TerminalHost addHost(String terminalId, Long hostId) throws Exception;

    /**
     *
     * @param terminalId
     * @param hostId
     * @throws Exception
     */
    public void deleteHost(String terminalId, Long hostId) throws Exception;

    /**
     *
     * @param terminalId
     * @param hostId
     * @return
     * @throws Exception
     */
    public List<TerminalHostSettingValue> listHostSettings(String terminalId, Long hostId) throws Exception;

    /**
     *
     * @param terminalId
     * @param host
     * @param terminalHostSettingValue
     * @return
     * @throws Exception
     */
    public Terminal addHostSetting(String terminalId, Long hostId, TerminalHostSettingValue terminalHostSettingValue) throws Exception;

    /**
     *
     * @param terminalId
     * @param host
     * @param terminalHostSettingValue
     * @return
     * @throws Exception
     */
    public Terminal updateHostSetting(String terminalId, Long hostId, TerminalHostSettingValue terminalHostSettingValue) throws Exception;

    /**
     *
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingId
     * @throws Exception
     */
    public void deleteHostSetting(String terminalId, Long hostId, Long terminalHostSettingId) throws Exception;

    /**
     *
     * @param terminalId
     * @param terminalSettingValue
     * @return
     * @throws Exception
     */
    public Terminal addSetting(String terminalId, TerminalSettingValue terminalSettingValue) throws Exception;

    /**
     *
     * @param terminalId
     * @param terminalSettingValue
     * @return
     * @throws Exception
     */
    public Terminal updateSetting(String terminalId, TerminalSettingValue terminalSettingValue) throws Exception;

    /**
     *
     * @param terminalId
     * @param terminalSetting
     * @throws Exception
     */
    public void deleteSetting(String terminalId, TerminalSettingEnum terminalSetting) throws Exception;

    /**
     *
     * @param terminalId
     * @return
     * @throws Exception
     */
    public List<TerminalSettingEnum> listAvailableSettings(String terminalId) throws Exception;

//
//    public List<Terminal> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception;
//
//    public List<Devices> getDevices(long id) throws Exception;
//    
//    public void addDevice(EntityRelation entityRelation) throws Exception;
//    
//    public void updateDevice(EntityRelation entityRelation) throws Exception;
//    
//    public void deleteDevice(long idEntityRelation) throws Exception;
//    public List<Merchant> getPrevMerchants(long id) throws Exception;
//
//    public List<Statistic> getStatistics(long id, String filter, int pageNumber, int rowsPerPage) throws Exception;
//
//    public int getStatisticsTotalPages(Long id, String filter, int rowsPerPage) throws Exception;
//
//    public List<Diagnostic> getDiagnostics(long id, String filter, int pageNumber, int rowsPerPage) throws Exception;
//
//    public int getDiagnosticsTotalPages(Long id, String filter, int rowsPerPage) throws Exception;

    public Entity copy(String entityId, long parentId);
    /**
     * Get the terminal info by serial number
     * @param terminalSerialNumber The serial number
     * @return
     * @throws Exception 
     */
    public Terminal getBySerialNumber(String terminalSerialNumber) throws Exception;
}
