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
import com.luretechnologies.common.enums.MerchantSettingEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.AddressDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.HostDAO;
import com.luretechnologies.server.data.dao.HostModeOperationDAO;
import com.luretechnologies.server.data.dao.MerchantDAO;
import com.luretechnologies.server.data.dao.MerchantHostDAO;
import com.luretechnologies.server.data.dao.MerchantHostModeOperationDAO;
import com.luretechnologies.server.data.dao.MerchantHostSettingDAO;
import com.luretechnologies.server.data.dao.MerchantHostSettingValueDAO;
import com.luretechnologies.server.data.dao.TelephoneDAO;
import com.luretechnologies.server.data.dao.TerminalHostDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Merchant;
import com.luretechnologies.server.data.model.payment.Address;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.HostModeOperation;
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostModeOperation;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.MerchantHostSettingValue;
import com.luretechnologies.server.data.model.payment.MerchantSettingValue;
import com.luretechnologies.server.data.model.payment.Telephone;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.service.MerchantService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MerchantServiceImpl implements MerchantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private EntityDAO entityDAO;

    @Autowired
    private MerchantDAO merchantDAO;

    @Autowired
    private AddressDAO addressDAO;
    
    @Autowired
    private TelephoneDAO telephoneDAO;

    @Autowired
    private HostDAO hostDAO;

    @Autowired
    MerchantHostDAO merchantHostDAO;

    @Autowired
    MerchantHostSettingDAO merchantHostSettingDAO;

    @Autowired
    MerchantHostSettingValueDAO merchantHostSettingValueDAO;

    @Autowired
    HostModeOperationDAO hostModeOperationDAO;

    @Autowired
    MerchantHostModeOperationDAO merchantHostModeOperationDAO;

    @Autowired
    TerminalHostDAO terminalHostDAO;

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Merchant create(Merchant entity) throws Exception {
        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parent.getEntityId());
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.MERCHANT, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        entity.setParent(parent);
        entity.setType(EntityTypeEnum.MERCHANT);
        entityDAO.updatePath(entity, true);
        merchantDAO.persist(entity);

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
    public Merchant update(String entityId, Merchant entity) throws Exception {
        Merchant existentEntity = merchantDAO.findByMerchantId(entityId);

        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, entityId);
        }

        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new PersistenceException("No parent id.");
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.MERCHANT, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        BeanUtils.copyProperties(entity, existentEntity, new String[]{"entityId"});
        existentEntity.setParent(parent);
        existentEntity.setType(EntityTypeEnum.MERCHANT);
        entityDAO.updatePath(existentEntity, false);
        entityDAO.updateActive(existentEntity);

        existentEntity.setAddresses(entity.getAddresses());
        existentEntity.setTelephones(entity.getTelephones());
        existentEntity.setMerchantHosts(entity.getMerchantHosts());

        for (MerchantHost merchantHost : entity.getMerchantHosts()) {
            MerchantHost existentMerchantHost = merchantHostDAO.findById(merchantHost.getId());
            if (existentMerchantHost == null) {
                merchantHost.setMerchant(existentEntity);
                merchantHostDAO.persist(merchantHost);
            } else {
                existentMerchantHost.setMerchantHostSettingValues(merchantHost.getMerchantHostSettingValues());
                for (MerchantHostSettingValue merchantHostSettingValue : existentMerchantHost.getMerchantHostSettingValues()) {
                    merchantHostSettingValue.setMerchantHost(existentMerchantHost);
                }
                merchantHostDAO.merge(existentMerchantHost);
            }
        }

        Merchant updated = merchantDAO.merge(existentEntity);
        
        for (Address address : addressDAO.getAddressNoRelations(updated.getAddresses())) {
            addressDAO.delete(address);
        }
        
        for (Telephone telephone : telephoneDAO.getTelephonesNoRelations(updated.getTelephones())) {
            telephoneDAO.delete(telephone);
        }
        
        return updated;
    }

    /**
     *
     * @param merchantId
     * @throws Exception
     */
    @Override
    public void delete(String merchantId) throws Exception {
        Merchant merchant = merchantDAO.findByMerchantId(merchantId);

        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        merchantDAO.delete(merchant.getId());
    }

    /**
     *
     * @param merchantId
     * @return
     * @throws Exception
     */
    @Override
    public Merchant get(String merchantId) throws Exception {
        // Get merchant basic data and parent info
        return merchantDAO.findByMerchantId(merchantId);
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
    public List<Merchant> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return merchantDAO.list(entity, firstResult, rowsPerPage);

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
    public List<Merchant> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return merchantDAO.search(entity, filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(merchantDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

    /**
     *
     * @param merchantId
     * @return
     * @throws Exception
     */
    @Override
    public List<Host> listAvailableHosts(String merchantId) throws Exception {

        // Throw error if the Merchant does not exists.
        Merchant existentMerchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (existentMerchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        List<MerchantHost> merchantHosts = merchantDAO.listHosts(merchantId);

        List<Host> availableHosts = new ArrayList<>();

        for (MerchantHost merchantHost : merchantHosts) {
            if (!availableHosts.contains(merchantHost.getHost())) {
                availableHosts.add(merchantHost.getHost());
            }
        }
        return availableHosts;
    }

    /**
     *
     * @param merchantId
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public MerchantHost addHost(String merchantId, Long hostId) throws Exception {

        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        MerchantHost merchantHost = merchantHostDAO.findByHostAndMerchant(merchant.getId(), hostId);
        if (merchantHost != null) {
            throw new Exception("Already exists");
        }

        Host host = hostDAO.findById(hostId);
        if (host == null) {
            throw new ObjectRetrievalFailureException(Host.class, hostId);
        }

        merchantHost = new MerchantHost();
        merchantHost.setHost(host);
        merchantHost.setMerchant(merchant);
        merchantHostDAO.persist(merchantHost);

        List<MerchantHostSetting> merchantHostSettings = merchantHostSettingDAO.findByHostId(hostId);
        for (MerchantHostSetting merchantHostSetting : merchantHostSettings) {
            MerchantHostSettingValue merchantHostSettingValue = new MerchantHostSettingValue();
            merchantHostSettingValue.setMerchantHost(merchantHost);
            merchantHostSettingValue.setSetting(merchantHostSetting);
            merchantHostSettingValue.setValue("");
            merchantHostSettingValueDAO.persist(merchantHostSettingValue);
            merchantHost.getMerchantHostSettingValues().add(merchantHostSettingValue);
        }
        return merchantHost;
    }

    /**
     *
     * @param merchantId
     * @param hostId
     * @throws Exception
     */
    @Override
    public void deleteHost(String merchantId, Long hostId) throws Exception {
        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        MerchantHost merchantHost = merchantHostDAO.findByHostAndMerchant(merchant.getId(), hostId);
        if (merchantHost == null) {
            throw new ObjectRetrievalFailureException(MerchantHost.class, hostId);
        }
        merchantHostDAO.delete(merchantHost);

        List<Entity> children = entityDAO.listChildren(merchant);
        for (Entity entity : children) {
            if (entity.getType() == EntityTypeEnum.TERMINAL) {
                TerminalHost terminalHost = terminalHostDAO.findByHostAndTerminal(entity.getId(), hostId);
                if (terminalHost != null) {
                    terminalHostDAO.delete(terminalHost);
                }
            }
        }
    }

    /**
     *
     * @param merchantId
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public List<MerchantHostSettingValue> listHostSettings(String merchantId, Long hostId) throws Exception {
        // Throw error if the Merchant does not exists.
        Merchant existentMerchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (existentMerchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }
        return merchantDAO.listHostSettings(merchantId, hostId);
    }

    /**
     *
     * @param merchantId
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public MerchantHostSettingValue setHostSettingValue(String merchantId, Long hostId, Long merchantHostSettingId, String value) throws Exception {
        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        MerchantHost merchantHost = merchantHostDAO.findByHostAndMerchant(merchant.getId(), hostId);
        if (merchantHost == null) {
            throw new ObjectRetrievalFailureException(MerchantHost.class, hostId);
        }

        MerchantHostSettingValue merchantHostSettingValue = merchantHostSettingValueDAO.getMerchantHostSettingValue(merchantHost.getId(), merchantHostSettingId);
        if (merchantHostSettingValue == null) {
            throw new ObjectRetrievalFailureException(MerchantHostSettingValue.class, merchantHostSettingId);
        }

        merchantHostSettingValue.setValue(value);
        merchantHostSettingValueDAO.merge(merchantHostSettingValue);
        return merchantHostSettingValue;
    }

    /**
     *
     * @param merchantId
     * @param merchantSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public Merchant addSetting(String merchantId, MerchantSettingValue merchantSettingValue) throws Exception {

        // Throw error if the Merchant does not exists.
        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        // If the Setting is already set up for this Merchant --> Do Nothing
        for (MerchantSettingValue existentMerchantSettingValue : merchant.getMerchantSettingValues()) {
            if (existentMerchantSettingValue.getMerchantSetting().equals(merchantSettingValue.getMerchantSetting())) {
                return null;
            }
        }

        merchantSettingValue.setMerchant(merchant);
        merchant.getMerchantSettingValues().add(merchantSettingValue);

        return merchantDAO.merge(merchant);
    }

    /**
     *
     * @param merchantId
     * @param merchantSettingValue
     * @return
     * @throws Exception
     */
    @Override
    public Merchant updateSetting(String merchantId, MerchantSettingValue merchantSettingValue) throws Exception {

        // Throw error if the Merchant does not exists.
        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        // If the Setting is already set up for this Merchant  --> UPDATE
        for (MerchantSettingValue existentMerchantSettingValue : merchant.getMerchantSettingValues()) {
            if (existentMerchantSettingValue.getMerchantSetting().equals(merchantSettingValue.getMerchantSetting())) {

                merchant.getMerchantSettingValues().remove(existentMerchantSettingValue);
                BeanUtils.copyProperties(merchantSettingValue, existentMerchantSettingValue, "id", "merchant");
                merchant.getMerchantSettingValues().add(existentMerchantSettingValue);

                return merchantDAO.merge(merchant);
            }
        }
        // Throw error if the Merchant Setting does not exists.
        throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
    }

    /**
     *
     * @param merchantId
     * @param merchantSetting
     * @throws Exception
     */
    @Override
    public void deleteSetting(String merchantId, MerchantSettingEnum merchantSetting) throws Exception {

        // Throw error if the Merchant does not exists.
        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }
        // If the Setting is already set up for this Merchant  --> DELETE
        for (MerchantSettingValue existentMerchantSettingValue : merchant.getMerchantSettingValues()) {
            if (existentMerchantSettingValue.getMerchantSetting().equals(merchantSetting)) {
                merchant.getMerchantSettingValues().remove(existentMerchantSettingValue);

                merchantDAO.merge(merchant);
                return;
            }
        }
        // Throw error if the Merchant HostEnum does not exists.
        throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
    }

    /**
     *
     * @param merchantId
     * @return
     * @throws Exception
     */
    @Override
    public List<MerchantSettingEnum> listAvailableSettings(String merchantId) throws Exception {

        // Throw error if the Merchant does not exists.
        Merchant existentMerchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (existentMerchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }

        List<MerchantSettingValue> terminalSettingValues = merchantDAO.listMerchantSettings(merchantId);

        List<MerchantSettingEnum> allMerchantSettings = Arrays.asList(MerchantSettingEnum.values());
        List<MerchantSettingEnum> assignedMerchantSettings = new ArrayList<>();
        List<MerchantSettingEnum> availableMerchantSettings = new ArrayList<>();

        for (MerchantSettingValue merchantSettingValue : terminalSettingValues) {
            assignedMerchantSettings.add(merchantSettingValue.getMerchantSetting());
        }

        for (MerchantSettingEnum terminalSetting : allMerchantSettings) {
            if (!assignedMerchantSettings.contains(terminalSetting)) {
                availableMerchantSettings.add(terminalSetting);
            }
        }

        return availableMerchantSettings;
    }

    /**
     *
     * @param merchantId
     * @param mode
     * @param operation
     * @return
     * @throws PersistenceException
     */
    @Override
    public Host getHostByModeOperation(String merchantId, ModeEnum mode, OperationEnum operation) throws PersistenceException {
        return merchantDAO.getHostByModeOperation(merchantId, mode, operation);
    }

    @Override
    public List<MerchantHostModeOperation> getHostModeOperationsByMerchantId(String merchantId) throws PersistenceException {
        return merchantHostModeOperationDAO.findByMerchantId(merchantId);
    }

    @Override
    public MerchantHostModeOperation addHostModeOperation(String merchantId, Long hostModeOperationId) throws Exception {
        MerchantHostModeOperation merchantHostModeOperation = findHostModeOperation(merchantId, hostModeOperationId);
        if (merchantHostModeOperation != null) {
            throw new Exception("Already exists");
        }
        Merchant merchant = (Merchant) merchantDAO.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new ObjectRetrievalFailureException(Merchant.class, merchantId);
        }
        HostModeOperation hostModeOperation = (HostModeOperation) hostModeOperationDAO.findById(hostModeOperationId);
        if (hostModeOperation == null) {
            throw new ObjectRetrievalFailureException(HostModeOperation.class, hostModeOperationId);
        }
        merchantHostModeOperation = new MerchantHostModeOperation();
        merchantHostModeOperation.setMerchant(merchant);
        merchantHostModeOperation.setHostModeOperation(hostModeOperation);
        merchantHostModeOperationDAO.persist(merchantHostModeOperation);
        return merchantHostModeOperation;
    }

    @Override
    public void deleteHostModeOperation(String merchantId, Long hostModeOperationId) throws Exception {
        MerchantHostModeOperation merchantHostModeOperation = findHostModeOperation(merchantId, hostModeOperationId);
        if (merchantHostModeOperation == null) {
            throw new Exception("It does not exist");
        }
        merchantHostModeOperationDAO.delete(merchantHostModeOperation.getId());
    }

    @Override
    public MerchantHostModeOperation findHostModeOperation(String merchantId, Long hostModeOperationId) {
        return merchantHostModeOperationDAO.findByMerchantAndHostModeOperation(merchantId, hostModeOperationId);
    }

//    @Override
//    public List<Merchant> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return merchantDAO.searchTree(filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//
//    @Override
//    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(merchantDAO.searchTree(filter, -1, -1).size(), rowsPerPage);
//    }
//    @Override
//    public List<Terminal> getTerminals(long id) throws Exception {
//        return merchantDAO.getTerminals(id);
//    }
//    @Override
//    public List<EntityRelation> getEntityRelationTerminals(long id) throws Exception {
//        return merchantDAO.getEntityRelationTerminals(id);
//    }
//    @Override
//    public List<Survey> getSurveys(long id) throws Exception {
//        return merchantDAO.getSurveys(id);
//    }
//    @Override
//    public void addTerminal(EntityRelation entityRelation) throws Exception {
//        merchantDAO.addTerminal(updatedTerminalParentInfo(entityRelation));
//    }
//
//    @Override
//    public void updateTerminal(EntityRelation entityRelation) throws Exception {
//        Terminal terminal = updatedTerminalParentInfo(entityRelation);
//        Date date = new java.util.Date();
//        terminal.setUpdatedAt(new Timestamp(date.getTime()));
//
//        merchantDAO.updateTerminal(terminal);
//    }
//
//    @Override
//    public void deleteTerminal(long idEntityRelation) throws Exception {
    // Deleting just the Relation between the Terminal and the merchant
//        EntityRelation entityRelation = entityRelationDAO.findById(idEntityRelation);
//        entityRelationDAO.delete(entityRelation);
//    }
//    @Override
//    public List<EntitySurvey> searchSurveys(long id, String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return merchantDAO.searchSurveys(id, filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//    @Override
//    public int searchTotalPagesSurvey(long idEntity, String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(merchantDAO.searchSurveys(idEntity, filter, -1, -1).size(), rowsPerPage);
//    }
//    @Override
//    public List<EntityUser> searchUsers(long id, String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return entityUserDAO.searchUsersByEntity(id, EntityType.MERCHANT, filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//    @Override
//    public void addUser(EntityUser entityUser) throws Exception {
//        User user = entityUser.getUser();
//        user.setPassword(Utils.encryptPassword(user.getPassword()));
////        user.getEntityUser().add(entityUser);
//
//        merchantDAO.addUser(user);
//    }
//
//    @Override
//    public User updateUser(EntityUser entityUser) throws Exception {
//        User user = entityUser.getUser();
//        if (user.getPassword() != null) {
//            user.setPassword(Utils.encryptPassword(user.getPassword()));
//        }
//
//        return merchantDAO.updateUser(user);
//    }
//    @Override
//    public void deleteUser(long idEntityUser) throws Exception {
//        EntityUser entityUser = entityUserDAO.findById(idEntityUser);
//        entityUser.setEndDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
//
//        User user = entityUser.getUser();
//        user.setActive(false);
////        user.getEntityUser().add(entityUser);
//
//        merchantDAO.deleteUser(user);
//    }
//    @Override
//    public void addSurvey(EntitySurvey entitySurvey) throws Exception {
//
//        Survey survey = entitySurvey.getSurvey();
//        survey.getEntitySurveys().add(entitySurvey);
//
//        merchantDAO.addSurvey(survey);
//
//        // Reset the previous starting survey (if existing) to false
//        if (entitySurvey.getStartingSurvey()) {
//            entitySurveyDAO.resetStartingSurvey(entitySurvey);
//        }
//
//    }
//    @Override
//    public void updateSurvey(EntitySurvey entitySurvey) throws Exception {
//        entitySurveyDAO.update(entitySurvey);
//
//        // Reset the previous starting survey (if existing) to false
//        if (entitySurvey.getStartingSurvey()) {
//            entitySurveyDAO.resetStartingSurvey(entitySurvey);
//        }
//    }
//
//    @Override
//    public void deleteSurvey(long idEntitySurvey) throws Exception {
//        entitySurveyDAO.delete(idEntitySurvey);
//    }
//
//    @Override
//    public List<Region> getPrevRegions(long id) throws Exception {
//        return merchantDAO.getPrevRegions(id);
//    }
//    private Terminal updatedTerminalParentInfo(EntityRelation entityRelation) {
//        Region region = entityRelationDAO.getRegionByMerchant(entityRelation.getMerchant().getId());
//        Organization organization = entityRelationDAO.getOrganizationByRegion(region.getId());
//
//        entityRelation.setRegion(region);
//        entityRelation.setOrganization(organization);
//
//        Terminal terminal = entityRelation.getTerminal();
////        terminal.getEntityRelation().add(entityRelation);
//
//        return terminal;
//
//    }
    @Override
    public Entity copy(String entityId, long parentId) throws Exception {
        Merchant original = merchantDAO.findByMerchantId(entityId);
        if (original == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }

        Entity parent = entityDAO.findById(parentId);
        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parentId);
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.MERCHANT, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        Merchant copy = new Merchant();
        copy.setName(original.getName());
        copy.setDescription(original.getDescription());
        copy.setActive(original.getActive());
        copy.setParent(parent);
        copy.setType(EntityTypeEnum.MERCHANT);

        for (Address address : original.getAddresses()) {            
            Address newAddress = new Address();
            BeanUtils.copyProperties(address, newAddress, new String[]{"id"});
            addressDAO.persist(newAddress);            
            copy.getAddresses().add(newAddress);
        }
        
        for (Telephone telephone : original.getTelephones()) {            
            Telephone newTelephone = new Telephone();
            BeanUtils.copyProperties(telephone, newTelephone, new String[]{"id"});
            telephoneDAO.persist(newTelephone);            
            copy.getTelephones().add(newTelephone);
        }
        
        entityDAO.updatePath(copy, true);
        merchantDAO.persist(copy);

        Set<MerchantHost> merchantHosts = original.getMerchantHosts();
        for (MerchantHost merchantHost : merchantHosts) {

            MerchantHost newMerchantHost = new MerchantHost();
            newMerchantHost.setHost(merchantHost.getHost());
            newMerchantHost.setMerchant(copy);
            merchantHostDAO.persist(newMerchantHost);

            List<MerchantHostSetting> merchantHostSettings = merchantHostSettingDAO.findByHostId(merchantHost.getHost().getId());
            for (MerchantHostSetting merchantHostSetting : merchantHostSettings) {
                MerchantHostSettingValue merchantHostSettingValue = new MerchantHostSettingValue();
                merchantHostSettingValue.setMerchantHost(newMerchantHost);
                merchantHostSettingValue.setSetting(merchantHostSetting);
                
                for (MerchantHostSettingValue originalMerchantHostSettingValue : merchantHost.getMerchantHostSettingValues()) {
                    if(merchantHostSettingValue.getSetting().getKey().equals(originalMerchantHostSettingValue.getSetting().getKey())) {
                        merchantHostSettingValue.setValue(originalMerchantHostSettingValue.getValue());
                        break;
                    }
                }                
                merchantHostSettingValueDAO.persist(merchantHostSettingValue);
                merchantHost.getMerchantHostSettingValues().add(merchantHostSettingValue);
            }

        }

        List<MerchantHostModeOperation> hostModeOperations = getHostModeOperationsByMerchantId(entityId);
        for (MerchantHostModeOperation hostModeOperation : hostModeOperations) {
            addHostModeOperation(copy.getEntityId(), hostModeOperation.getHostModeOperation().getId());
        }

        return copy;
    }
}
