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

import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.HostModeOperation;
import com.luretechnologies.server.data.model.payment.HostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import java.util.List;

/**
 *
 */
public interface HostService {

    /**
     *
     * @param key
     * @return
     */
    public Host findByKey(String key);
    
    /**
     *
     * @return @throws Exception
     */
    public List<Host> list() throws Exception;

    public Host create(Host host) throws Exception;
    
    public Host update(Long id, Host host) throws Exception;
    
    /**
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    public List<HostSettingValue> findSettingValues(Long hostId) throws Exception;

    /**
     *
     * @param hostId
     * @param hostSettingValue
     * @return
     * @throws Exception
     */
    public HostSettingValue addSetting(Long hostId, HostSettingValue hostSettingValue) throws Exception;

    /**
     *
     * @param host
     * @param hostSettingValue
     * @return
     * @throws Exception
     */
    public HostSettingValue updateSetting(Long hostId, HostSettingValue hostSettingValue) throws Exception;

    /**
     *
     * @param hostId
     * @param hostSettingId
     * @throws Exception
     */
    public void deleteSetting(Long hostId, String key) throws Exception;
    
    /**
     *
     * @param hostId
     * @throws Exception
     */
    public List<MerchantHostSetting> listMerchantSettings(Long hostId) throws Exception;
    
    /**
     *
     * @param hostId
     * @throws Exception
     */
    public List<TerminalHostSetting> listTerminalSettings(Long hostId) throws Exception;
    
    /**
     *
     * @param hostId
     * @param merchantHostSetting
     * @return
     * @throws Exception
     */
    public MerchantHostSetting addMerchantSetting(Long hostId, MerchantHostSetting merchantHostSetting) throws Exception;
    
    public MerchantHostSetting updateMerchantSetting(Long hostId, MerchantHostSetting merchantHostSetting) throws Exception;
    
    public MerchantHostSetting getMerchantSetting(Long hostId, String key) throws Exception;
    
    public void deleteMerchantSetting(Long settingId) throws Exception;
    
    public TerminalHostSetting addTerminalSetting(Long hostId, TerminalHostSetting terminalHostSetting) throws Exception;
    
    public TerminalHostSetting updateTerminalSetting(Long hostId, TerminalHostSetting terminalHostSetting) throws Exception;
    
    public void deleteTerminalSetting(Long settingId) throws Exception;
    
    public TerminalHostSetting getTerminalSetting(Long hostId, String key);
    
    public List<HostModeOperation> getModeOperationsByHostId(Long hostId);
    
    public HostModeOperation addModeOperation(Long hostId, HostModeOperation modeOper) throws Exception;
    
    public HostModeOperation updateModeOperation(Long hostId, HostModeOperation modeOper) throws Exception;
    
    public void deleteModeOperation(Long modeOperationId) throws Exception;

}
