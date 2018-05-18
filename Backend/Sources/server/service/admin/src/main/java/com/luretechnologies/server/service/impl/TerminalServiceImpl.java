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

import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.common.enums.TerminalSettingEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.ApplicationDAO;
import com.luretechnologies.server.data.dao.ApplicationPackageDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.HostDAO;
import com.luretechnologies.server.data.dao.ModelDAO;
import com.luretechnologies.server.data.dao.PaymentProfileDAO;
import com.luretechnologies.server.data.dao.ScheduleGroupDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.dao.TerminalHostDAO;
import com.luretechnologies.server.data.dao.TerminalHostSettingDAO;
import com.luretechnologies.server.data.dao.TerminalHostSettingValueDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalHostSetting;
import com.luretechnologies.server.data.model.payment.TerminalHostSettingValue;
import com.luretechnologies.server.data.model.payment.TerminalSettingValue;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.ApplicationPackage;
import com.luretechnologies.server.data.model.tms.KeyBlock;
import com.luretechnologies.server.data.model.tms.Parameter;
import com.luretechnologies.server.data.model.tms.PaymentProfile;
import com.luretechnologies.server.data.model.tms.TerminalApplicationParameter;
import com.luretechnologies.server.service.TerminalService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 */
@Service
@Transactional
public class TerminalServiceImpl implements TerminalService {

    @Autowired
    private HostDAO hostDAO;
    
    @Autowired
    private EntityDAO entityDAO;

    @Autowired
    private TerminalDAO terminalDAO;

    @Autowired
    private ApplicationDAO applicationDAO;

    @Autowired
    private ApplicationPackageDAO applicationPackageDAO;

    @Autowired
    private PaymentProfileDAO paymentProfileDAO;

    @Autowired
    private ScheduleGroupDAO scheduleGroupDAO;

    @Autowired
    private ModelDAO modelDAO;
    
    @Autowired
    private TerminalHostDAO terminalHostDAO;
    
    @Autowired
    private TerminalHostSettingDAO terminalHostSettingDAO;
    
    @Autowired
    private TerminalHostSettingValueDAO terminalHostSettingValueDAO;

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Terminal create(Terminal entity) throws Exception {

        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parent.getEntityId());
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.TERMINAL, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        entity.setParent(parent);
        entity.setType(EntityTypeEnum.TERMINAL);
        entityDAO.updatePath(entity, true);

        if (entity.getModel() != null) {
            entity.setModel(modelDAO.findById(entity.getModel().getId()));
        }

        if (entity.getScheduleGroup() != null) {
            entity.setScheduleGroup(scheduleGroupDAO.findById(entity.getScheduleGroup().getId()));
        } else {
            entity.setScheduleGroup(scheduleGroupDAO.getFirstResult());
        }

        terminalDAO.persist(entity);

        return entity;
    }

    /**
     *
     * @param entityId
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Terminal update(String entityId, Terminal entity) throws Exception {
        Terminal existentEntity = terminalDAO.findByTerminalId(entityId);

        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, entityId);
        }

        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new PersistenceException("No parent id.");
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.MERCHANT, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        if (existentEntity.getModel() != null) {
            existentEntity.setModel(modelDAO.findById(entity.getModel().getId()));
        }

        if (existentEntity.getScheduleGroup() != null) {
            existentEntity.setScheduleGroup(scheduleGroupDAO.findById(existentEntity.getScheduleGroup().getId()));
        } else {
            existentEntity.setScheduleGroup(scheduleGroupDAO.getFirstResult());
        }

//        if (existentEntity.getKeyBlock() != null) {
//            KeyBlock existentKey = existentEntity.getKeyBlock() == null ? new KeyBlock() : existentEntity.getKeyBlock();
//            BeanUtils.copyProperties(existentEntity.getKeyBlock(), existentKey, new String[]{"version", "createdAt"});
//            updateVersion(existentKey);
//            existentEntity.setKeyBlock(existentKey);
//        }
        
        BeanUtils.copyProperties(entity, existentEntity, new String[]{"entityId"});
        existentEntity.setParent(parent);
        existentEntity.setType(EntityTypeEnum.TERMINAL);
        entityDAO.updatePath(existentEntity, false);
        entityDAO.updateActive(existentEntity);
        
        for (TerminalHost terminalHost : entity.getTerminalHosts()) {
            TerminalHost existentTerminalHost = terminalHostDAO.findById(terminalHost.getId());
            if(existentTerminalHost == null) {
                terminalHost.setTerminal(existentEntity);
                terminalHostDAO.persist(terminalHost);
            } else {
                existentTerminalHost.setTerminalHostSettingValues(terminalHost.getTerminalHostSettingValues());
                for (TerminalHostSettingValue terminalHostSettingValue : existentTerminalHost.getTerminalHostSettingValues()) {
                    terminalHostSettingValue.setTerminalHost(existentTerminalHost);
                }
                terminalHostDAO.merge(existentTerminalHost);
            }
        }
        
        return terminalDAO.merge(existentEntity);
    }

    /**
     *
     * @param terminalId
     * @throws Exception
     */
    @Override
    public void delete(String terminalId) throws Exception {
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);

        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        terminalDAO.delete(terminal.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Terminal findById(long id) throws Exception {
        return terminalDAO.findById(id);
    }

    /**
     *
     * @param terminalId
     * @return
     * @throws Exception
     */
    @Override
    public Terminal get(String terminalId) throws Exception {
        //Get terminal basic data and parent info
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);

        if (terminal != null) {
            return terminal;
        }

        throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
    }

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Terminal> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return terminalDAO.list(entity, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Terminal> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return terminalDAO.search(entity, filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(terminalDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

    /**
     *
     * @param serialNumber
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public Terminal addKey(String serialNumber, KeyBlock key) throws Exception {
        Terminal terminal = (Terminal) terminalDAO.findBySerialNumber(serialNumber);

        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, serialNumber);
        }

//        KeyBlock existentKey = terminal.getKeyBlock() == null ? new KeyBlock() : terminal.getKeyBlock();
//
//        // Copy properties from -> to
//        BeanUtils.copyProperties(key, existentKey, new String[]{"version", "createdAt"});
//
//        // Update Key Block version number
//        updateVersion(existentKey);
//
//        terminal.setKeyBlock(existentKey);

        return terminalDAO.merge(terminal);
    }

    /**
     *
     * @param serialNumber
     * @param idApp
     * @param idProfile
     * @throws Exception
     */
    @Override
    public void addPaymentApplication(String serialNumber, long idApp, long idProfile) throws Exception {
        Terminal terminal = (Terminal) terminalDAO.findBySerialNumber(serialNumber);

        Application application = applicationDAO.findById(idApp);

        PaymentProfile paymentProfile = paymentProfileDAO.findById(idProfile);

        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, serialNumber);
        }

        for (ApplicationPackage applicationPackage : terminal.getApplicationPackages()) {
            if (applicationPackage.getApplication().getId() == application.getId()) {
                return;
            }
        }

        // Create the Application Package
        ApplicationPackage applicationPackage = new ApplicationPackage();
        applicationPackage.setApplication(application);
        applicationPackage.setTerminal(terminal);
        applicationPackage.setPaymentProfile(paymentProfile);

        // Add default terminal application parameters to the Application Package if Parameter isn't ApplicationWide
        for (Parameter parameter : application.getParameters()) {
            if (!parameter.getApplicationWide()) {
                TerminalApplicationParameter terminalApplicationParameter = new TerminalApplicationParameter();
                terminalApplicationParameter.setParameter(parameter);
                terminalApplicationParameter.setApplicationPackage(applicationPackage);
                terminalApplicationParameter.setValue(parameter.getDefaultValue());

                applicationPackage.getParameters().add(terminalApplicationParameter);
            }
        }

        applicationPackageDAO.merge(applicationPackage);
    }

    /**
     *
     * @param entity
     * @param name
     * @param serialNumber
     * @param active
     * @return
     * @throws Exception
     */
    @Override
    public List<Terminal> list(Entity entity, String name, String serialNumber, Boolean active) throws Exception {
        if (name == null && serialNumber == null && active == null) {
            return terminalDAO.list();
        } else {
            return terminalDAO.list(entity, name, serialNumber, active);
        }
    }

    private void updateVersion(KeyBlock keyBlock) {
        int currentVersion = keyBlock.getVersion();

        if (currentVersion == 255) {
            keyBlock.setVersion(0);
        } else {
            keyBlock.setVersion(++currentVersion);
        }
    }

    /**
     *
     * @param terminalId
     * @return
     * @throws Exception
     */
    @Override
    public List<Host> listAvailableHosts(String terminalId) throws Exception {
        // Throw error if the Terminal does not exists.
        Terminal existentTerminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (existentTerminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        List<TerminalHost> terminalHosts = terminalDAO.listHosts(terminalId);

        List<Host> availableHosts = new ArrayList<>();

        for (TerminalHost terminalHost : terminalHosts) {
            if (!availableHosts.contains(terminalHost.getHost())) {
                availableHosts.add(terminalHost.getHost());
            }
        }
        return availableHosts;
    }

    /**
     *
     * @param terminalId
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public TerminalHost addHost(String terminalId, Long hostId) throws Exception {
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }
        
        TerminalHost terminalHost = terminalHostDAO.findByHostAndTerminal(terminal.getId(), hostId);
        if (terminalHost != null) {
            throw new Exception("Already exists");
        }

        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }
        
        terminalHost = new TerminalHost();
        terminalHost.setHost(host);
        terminalHost.setTerminal(terminal);        
        terminalHostDAO.persist(terminalHost);
        
        List<TerminalHostSetting> terminalHostSettings = terminalHostSettingDAO.findByHostId(hostId);
        for (TerminalHostSetting terminalHostSetting : terminalHostSettings) {
            TerminalHostSettingValue terminalHostSettingValue = new TerminalHostSettingValue();
            terminalHostSettingValue.setTerminalHost(terminalHost);
            terminalHostSettingValue.setSetting(terminalHostSetting);
            terminalHostSettingValue.setValue("");
            terminalHostSettingValueDAO.persist(terminalHostSettingValue);
            terminalHost.getTerminalHostSettingValues().add(terminalHostSettingValue);
        }
        return terminalHost;
    }

    /**
     *
     * @param terminalId
     * @param hostId
     * @throws Exception
     */
    @Override
    public void deleteHost(String terminalId, Long hostId) throws Exception {
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }
        
        TerminalHost terminalHost = terminalHostDAO.findByHostAndTerminal(terminal.getId(), hostId);
        if (terminalHost == null) {
            throw new ObjectRetrievalFailureException(TerminalHost.class, hostId);
        }
        terminalHostDAO.delete(terminalHost);
    }

    /**
     *
     * @param terminalId
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public List<TerminalHostSettingValue> listHostSettings(String terminalId, Long hostId) throws Exception {
        // Throw error if the Terminal does not exists.
        Terminal existentTerminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (existentTerminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }
        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }
        return terminalDAO.listHostSettings(terminalId, host);
    }

    /**
     *
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public Terminal addHostSetting(String terminalId, Long hostId, TerminalHostSettingValue terminalHostSettingValue) throws Exception {
        // Throw error if the Terminal does not exists.
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        for (TerminalHost terminalHost : terminal.getTerminalHosts()) {
            if (terminalHost.getHost().getId() == hostId) {

                // If the Setting is already set up for this Terminal HostEnum --> Do Nothing
                for (TerminalHostSettingValue existentTerminalHostSettingValue : terminalHost.getTerminalHostSettingValues()) {
                    if (existentTerminalHostSettingValue.getSetting().equals(terminalHostSettingValue.getSetting())) {
                        return null;
                    }
                }
                terminalHostSettingValue.setTerminalHost(terminalHost);
                terminalHost.getTerminalHostSettingValues().add(terminalHostSettingValue);

                return terminalDAO.merge(terminal);
            }
        }

        // Throw error if the Terminal HostEnum does not exists.
        throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
    }

    /**
     *
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public Terminal updateHostSetting(String terminalId, Long hostId, TerminalHostSettingValue terminalHostSettingValue) throws Exception {
        // Throw error if the Terminal does not exists.
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        for (TerminalHost terminalHost : terminal.getTerminalHosts()) {
            if (terminalHost.getHost().getId() == hostId) {

                // If the Setting is already set up for this Terminal HostEnum --> UPDATE
                for (TerminalHostSettingValue existentTerminalHostSettingValue : terminalHost.getTerminalHostSettingValues()) {
                    if (existentTerminalHostSettingValue.getSetting().equals(terminalHostSettingValue.getSetting())) {

                        terminalHost.getTerminalHostSettingValues().remove(existentTerminalHostSettingValue);
                        BeanUtils.copyProperties(terminalHostSettingValue, existentTerminalHostSettingValue, "id", "terminalHost");
                        terminalHost.getTerminalHostSettingValues().add(existentTerminalHostSettingValue);

                        return terminalDAO.merge(terminal);
                    }
                }
            }
        }

        // Throw error if the Terminal HostEnum does not exists.
        throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
    }

    /**
     *
     * @param terminalId
     * @param hostId
     * @param terminalHostSettingId
     * @throws Exception
     */
    @Override
    public void deleteHostSetting(String terminalId, Long hostId, Long terminalHostSettingId) throws Exception {
        // Throw error if the Terminal does not exists.
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        for (TerminalHost terminalHost : terminal.getTerminalHosts()) {
            if (terminalHost.getHost().getId() == hostId) {

                // If the Setting is already set up for this Terminal HostEnum --> UPDATE
                for (TerminalHostSettingValue existentTerminalHostSettingValue : terminalHost.getTerminalHostSettingValues()) {

                    if (existentTerminalHostSettingValue.getSetting().getId() == terminalHostSettingId) {
                        terminalHost.getTerminalHostSettingValues().remove(existentTerminalHostSettingValue);
                        terminalDAO.merge(terminal);

                        return;
                    }
                }
            }
        }

        // Throw error if the Terminal HostEnum does not exists.
        throw new ObjectRetrievalFailureException(Terminal.class, terminalId);

    }

    /**
     *
     * @param terminalId
     * @param terminalSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public Terminal addSetting(String terminalId, TerminalSettingValue terminalSettingValue) throws Exception {

        // Throw error if the Terminal does not exists.
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        // If the Setting is already set up for this Terminal --> Do Nothing
        for (TerminalSettingValue existentTerminalSettingValue : terminal.getTerminalSettingValues()) {
            if (existentTerminalSettingValue.getTerminalSetting().equals(terminalSettingValue.getTerminalSetting())) {
                return null;
            }
        }

        terminalSettingValue.setTerminal(terminal);
        terminal.getTerminalSettingValues().add(terminalSettingValue);

        return terminalDAO.merge(terminal);
    }

    /**
     *
     * @param terminalId
     * @param terminalSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public Terminal updateSetting(String terminalId, TerminalSettingValue terminalSettingValue) throws Exception {

        // Throw error if the Terminal does not exists.
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        // If the Setting is already set up for this Terminal  --> UPDATE
        for (TerminalSettingValue existentTerminalSettingValue : terminal.getTerminalSettingValues()) {
            if (existentTerminalSettingValue.getTerminalSetting().equals(terminalSettingValue.getTerminalSetting())) {

                terminal.getTerminalSettingValues().remove(existentTerminalSettingValue);
                BeanUtils.copyProperties(terminalSettingValue, existentTerminalSettingValue, "id", "terminal");
                terminal.getTerminalSettingValues().add(existentTerminalSettingValue);

                return terminalDAO.merge(terminal);
            }
        }
        // Throw error if the Terminal Setting does not exists.
        throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
    }

    /**
     *
     * @param terminalId
     * @param terminalSetting
     * @throws Exception
     */
    @Override
    public void deleteSetting(String terminalId, TerminalSettingEnum terminalSetting) throws Exception {

        // Throw error if the Terminal does not exists.
        Terminal terminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }
        // If the Setting is already set up for this Terminal  --> DELETE
        for (TerminalSettingValue existentTerminalSettingValue : terminal.getTerminalSettingValues()) {

            if (existentTerminalSettingValue.getTerminalSetting().equals(terminalSetting)) {
                terminal.getTerminalSettingValues().remove(existentTerminalSettingValue);

                terminalDAO.merge(terminal);
                return;
            }
        }
        // Throw error if the Terminal HostEnum does not exists.
        throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
    }

    /**
     *
     * @param terminalId
     * @return
     * @throws Exception
     */
    @Override
    public List<TerminalSettingEnum> listAvailableSettings(String terminalId) throws Exception {

        // Throw error if the Terminal does not exists.
        Terminal existentTerminal = (Terminal) terminalDAO.findByTerminalId(terminalId);
        if (existentTerminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, terminalId);
        }

        List<TerminalSettingValue> terminalSettingValues = terminalDAO.listTerminalSettings(terminalId);

        List<TerminalSettingEnum> allTerminalSettings = Arrays.asList(TerminalSettingEnum.values());
        List<TerminalSettingEnum> assignedTerminalSettings = new ArrayList<>();
        List<TerminalSettingEnum> availableTerminalSettings = new ArrayList<>();

        for (TerminalSettingValue terminalSettingValue : terminalSettingValues) {
            assignedTerminalSettings.add(terminalSettingValue.getTerminalSetting());
        }

        for (TerminalSettingEnum terminalSetting : allTerminalSettings) {
            if (!assignedTerminalSettings.contains(terminalSetting)) {
                availableTerminalSettings.add(terminalSetting);
            }
        }

        return availableTerminalSettings;
    }

//
//    @Override
//    public List<Terminal> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return terminalDAO.searchTree(filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//
//    @Override
//    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(terminalDAO.searchTree(filter, -1, -1).size(), rowsPerPage);
//    }
//    @Override
//    public List<Devices> getDevices(long id) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    @Override
//    public void addDevice(EntityRelation entityRelation) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void updateDevice(EntityRelation entityRelation) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void deleteDevice(long idEntityRelation) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public List<Merchant> getPrevMerchants(long id) throws Exception {
//        return terminalDAO.getPrevMerchants(id);
//    }
//
//    @Override
//    public List<Statistic> getStatistics(long id, String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return terminalDAO.getStatistics(id, filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//
//    @Override
//    public int getStatisticsTotalPages(Long id, String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(terminalDAO.getStatistics(id, filter, -1, -1).size(), rowsPerPage);
//    }
//
//    @Override
//    public List<Diagnostic> getDiagnostics(long id, String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return terminalDAO.getDiagnostics(id, filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//
//    @Override
//    public int getDiagnosticsTotalPages(Long id, String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(terminalDAO.getDiagnostics(id, filter, -1, -1).size(), rowsPerPage);
//    }

    @Override
    public Entity copy(String entityId, long parentId) {
        Terminal original = terminalDAO.findByTerminalId(entityId);
        if (original == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }

        Entity parent = entityDAO.findById(parentId);
        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parentId);
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.TERMINAL, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        Terminal copy = new Terminal();
        copy.setName(original.getName());
        copy.setSerialNumber(null);
        copy.setDescription(original.getDescription());
        copy.setParent(parent);
        copy.setActive(original.getActive());
        copy.setType(EntityTypeEnum.TERMINAL);
        
        entityDAO.updatePath(copy, true);
                
        if (original.getModel() != null) {
            copy.setModel(modelDAO.findById(original.getModel().getId()));
        }

        if (original.getScheduleGroup() != null) {
            copy.setScheduleGroup(scheduleGroupDAO.findById(original.getScheduleGroup().getId()));
        } else {
            copy.setScheduleGroup(scheduleGroupDAO.getFirstResult());
        }
        
        terminalDAO.persist(copy);
        
        Set<TerminalHost> terminalHosts = original.getTerminalHosts();
        for (TerminalHost terminalHost : terminalHosts) {

            TerminalHost newTerminalHost = new TerminalHost();
            newTerminalHost.setHost(terminalHost.getHost());
            newTerminalHost.setTerminal(copy);
            terminalHostDAO.persist(newTerminalHost);

            List<TerminalHostSetting> terminalHostSettings = terminalHostSettingDAO.findByHostId(terminalHost.getHost().getId());
            for (TerminalHostSetting terminalHostSetting : terminalHostSettings) {
                TerminalHostSettingValue terminalHostSettingValue = new TerminalHostSettingValue();
                terminalHostSettingValue.setTerminalHost(newTerminalHost);
                terminalHostSettingValue.setSetting(terminalHostSetting);
                
                for (TerminalHostSettingValue originalMerchantHostSettingValue : terminalHost.getTerminalHostSettingValues()) {
                    if(terminalHostSettingValue.getSetting().getKey().equals(originalMerchantHostSettingValue.getSetting().getKey())) {
                        terminalHostSettingValue.setValue(originalMerchantHostSettingValue.getValue());
                        break;
                    }
                }                
                terminalHostSettingValueDAO.persist(terminalHostSettingValue);
                terminalHost.getTerminalHostSettingValues().add(terminalHostSettingValue);
            }
        }

        return copy;
    }
}
