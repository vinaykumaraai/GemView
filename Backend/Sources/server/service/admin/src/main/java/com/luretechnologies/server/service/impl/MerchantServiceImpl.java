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
import com.luretechnologies.server.data.model.payment.MerchantHost;
import com.luretechnologies.server.data.model.payment.MerchantHostSetting;
import com.luretechnologies.server.data.model.payment.MerchantHostSettingValue;
import com.luretechnologies.server.data.model.payment.Telephone;
import com.luretechnologies.server.service.MerchantService;
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
        merchant.setActive(false);
        entityDAO.updateActive(merchant);
        merchantDAO.merge(merchant);
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
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return merchantDAO.list(entity, firstResult, firstResult + rowsPerPage);

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
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return merchantDAO.search(entity, filter, firstResult, firstResult + rowsPerPage);
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

        return copy;
    }
}
