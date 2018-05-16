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

import com.luretechnologies.server.data.dao.HostDAO;
import com.luretechnologies.server.data.dao.HostModeOperationDAO;
import com.luretechnologies.server.data.dao.HostSettingValueDAO;
import com.luretechnologies.server.data.dao.MerchantHostDAO;
import com.luretechnologies.server.data.dao.MerchantHostSettingDAO;
import com.luretechnologies.server.data.dao.MerchantHostSettingValueDAO;
import com.luretechnologies.server.data.dao.TerminalHostDAO;
import com.luretechnologies.server.data.dao.TerminalHostSettingDAO;
import com.luretechnologies.server.data.dao.TerminalHostSettingValueDAO;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.HostModeOperation;
import com.luretechnologies.server.data.model.payment.HostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.MerchantHostSettingValue;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import com.luretechnologies.server.service.HostService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class HostServiceImpl implements HostService {

    @Autowired
    HostDAO hostDAO;

    @Autowired
    MerchantHostSettingDAO merchantHostSettingDAO;

    @Autowired
    HostSettingValueDAO hostSettingValueDAO;

    @Autowired
    MerchantHostDAO merchantHostDAO;

    @Autowired
    MerchantHostSettingValueDAO merchantHostSettingValueDAO;

    @Autowired
    HostModeOperationDAO hostModeOperationDAO;

    @Autowired
    TerminalHostSettingDAO terminalHostSettingDAO;

    @Autowired
    TerminalHostDAO terminalHostDAO;

    @Autowired
    TerminalHostSettingValueDAO terminalHostSettingValueDAO;

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Host findByKey(String key) {
        return hostDAO.findByKey(key);
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    public List<Host> list() throws Exception {
        return hostDAO.listOrdered();
    }

    /**
     *
     * @param host
     * @return
     * @throws Exception
     */
    @Override
    public Host create(Host host) throws Exception {
        hostDAO.persist(host);
        return host;
    }

    /**
     *
     * @param id
     * @param host
     * @return
     * @throws Exception
     */
    @Override
    public Host update(Long id, Host host) throws Exception {
        Host existentHost = hostDAO.findById(id);

        if (existentHost == null) {
            throw new ObjectRetrievalFailureException(Host.class, id);
        }

        BeanUtils.copyProperties(host, existentHost);
        return hostDAO.merge(existentHost);
    }

    /**
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public List<HostSettingValue> findSettingValues(Long hostId) throws Exception {
        return hostSettingValueDAO.findSettingValues(hostId);
    }

    /**
     *
     * @param hostId
     * @param hostSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public HostSettingValue addSetting(Long hostId, HostSettingValue hostSettingValue) throws Exception {
        Host host = hostDAO.findById(hostId);
        hostSettingValue.setHost(host);
        return hostSettingValueDAO.merge(hostSettingValue);
    }

    /**
     *
     * @param hostId
     * @param hostSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public HostSettingValue updateSetting(Long hostId, HostSettingValue hostSettingValue) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }
        HostSettingValue existentHostSettingValue = hostSettingValueDAO.findById(hostSettingValue.getId());
        if (existentHostSettingValue == null) {
            throw new ObjectRetrievalFailureException(HostSettingValue.class, hostId);
        }
        
        BeanUtils.copyProperties(hostSettingValue, existentHostSettingValue, new String[]{"id"});
        existentHostSettingValue.setHost(host);

        return hostSettingValueDAO.merge(existentHostSettingValue);
    }

    /**
     *
     * @param hostId
     * @param key
     * @throws Exception
     */
    @Override
    public void deleteSetting(Long hostId, String key) throws Exception {
        HostSettingValue existentHostSettingValue = hostSettingValueDAO.findHostSettingValue(hostId, key);
        if (existentHostSettingValue == null) {
            throw new ObjectRetrievalFailureException(HostSettingValue.class, hostId);
        }
        hostSettingValueDAO.delete(existentHostSettingValue.getId());
    }

    /**
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public List<MerchantHostSetting> listMerchantSettings(Long hostId) throws Exception {
        return merchantHostSettingDAO.findByHostId(hostId);
    }

    /**
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public List<TerminalHostSetting> listTerminalSettings(Long hostId) throws Exception {
        return terminalHostSettingDAO.findByHostId(hostId);
    }

    @Override
    public MerchantHostSetting getMerchantSetting(Long hostId, String key) throws Exception {
        return (MerchantHostSetting) merchantHostSettingDAO.findByHostAndKey(hostId, key);
    }

    @Override
    public MerchantHostSetting addMerchantSetting(Long hostId, MerchantHostSetting merchantHostSetting) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }

        merchantHostSetting.setHost(host);
        merchantHostSettingDAO.persist(merchantHostSetting);

        List<MerchantHost> merchantHosts = merchantHostDAO.findByHostId(hostId);
        for (MerchantHost merchantHost : merchantHosts) {
            MerchantHostSettingValue merchantHostSettingValue = new MerchantHostSettingValue();
            merchantHostSettingValue.setMerchantHost(merchantHost);
            merchantHostSettingValue.setSetting(merchantHostSetting);
            merchantHostSettingValue.setValue("");
            merchantHostSettingValueDAO.persist(merchantHostSettingValue);
        }

        return merchantHostSetting;
    }

    @Override
    public MerchantHostSetting updateMerchantSetting(Long hostId, MerchantHostSetting merchantHostSetting) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }

        MerchantHostSetting existentEntity = merchantHostSettingDAO.findById(merchantHostSetting.getId());
        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(MerchantHostSetting.class, hostId);
        }

        BeanUtils.copyProperties(merchantHostSetting, existentEntity, new String[]{"id"});
        existentEntity.setHost(host);

        return merchantHostSettingDAO.merge(existentEntity);
    }

    @Override
    public void deleteMerchantSetting(Long settingId) throws Exception {
        merchantHostSettingDAO.delete(settingId);
    }

    @Override
    public TerminalHostSetting getTerminalSetting(Long hostId, String key) {
        return (TerminalHostSetting) terminalHostSettingDAO.findByHostAndKey(hostId, key);
    }

    @Override
    public TerminalHostSetting addTerminalSetting(Long hostId, TerminalHostSetting terminalHostSetting) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }

        terminalHostSetting.setHost(host);
        terminalHostSettingDAO.persist(terminalHostSetting);

        List<TerminalHost> terminalHosts = terminalHostDAO.findByHostId(hostId);
        for (TerminalHost terminalHost : terminalHosts) {
            TerminalHostSettingValue terminalHostSettingValue = new TerminalHostSettingValue();
            terminalHostSettingValue.setTerminalHost(terminalHost);
            terminalHostSettingValue.setSetting(terminalHostSetting);
            terminalHostSettingValue.setValue("");
            terminalHostSettingValueDAO.persist(terminalHostSettingValue);
        }

        return terminalHostSetting;
    }

    @Override
    public TerminalHostSetting updateTerminalSetting(Long hostId, TerminalHostSetting terminalHostSetting) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }

        TerminalHostSetting existentEntity = terminalHostSettingDAO.findById(terminalHostSetting.getId());
        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(TerminalHostSetting.class, hostId);
        }

        BeanUtils.copyProperties(terminalHostSetting, existentEntity, new String[]{"id"});
        terminalHostSetting.setHost(host);

        return terminalHostSettingDAO.merge(existentEntity);
    }

    @Override
    public void deleteTerminalSetting(Long settingId) throws Exception {
        terminalHostSettingDAO.delete(settingId);
    }
    
    @Override
    public HostModeOperation addModeOperation(Long hostId, HostModeOperation modeOper) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }
        modeOper.setHost(host);
        hostModeOperationDAO.persist(modeOper);

        return modeOper;
    }
    
    @Override
    public HostModeOperation updateModeOperation(Long hostId, HostModeOperation modeOper) throws Exception {
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }

        HostModeOperation existentEntity = hostModeOperationDAO.findById(modeOper.getId());
        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(HostModeOperation.class, hostId);
        }

        BeanUtils.copyProperties(modeOper, existentEntity, new String[]{"id"});
        modeOper.setHost(host);

        return hostModeOperationDAO.merge(existentEntity);
    }

    @Override
    public List<HostModeOperation> getModeOperationsByHostId(Long hostId) {
        return hostModeOperationDAO.findByHostId(hostId);
    }

    @Override
    public void deleteModeOperation(Long modeOperationId) throws Exception {
        hostModeOperationDAO.delete(modeOperationId);
    }

}
