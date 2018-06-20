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
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.RegionDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Region;
import com.luretechnologies.server.service.RegionService;
import java.util.List;
import javax.persistence.PersistenceException;
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
public class RegionServiceImpl implements RegionService {

    @Autowired
    private EntityDAO entityDAO;

    @Autowired
    private RegionDAO regionDAO;

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Region create(Region entity) throws Exception {
        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parent.getEntityId());
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.REGION, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        entity.setParent(parent);
        entity.setType(EntityTypeEnum.REGION);
        entityDAO.updatePath(entity, true);
        regionDAO.persist(entity);

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
    public Region update(String entityId, Region entity) throws Exception {
        Region existentEntity = regionDAO.findByRegionId(entityId);

        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(Region.class, entityId);
        }

        Entity parent = entityDAO.findById(existentEntity.getParentId());

        if (parent == null) {
            throw new PersistenceException("No parent id.");
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.REGION, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        BeanUtils.copyProperties(entity, existentEntity, new String[]{"entityId"});
        existentEntity.setParent(parent);
        existentEntity.setType(EntityTypeEnum.REGION);
        entityDAO.updatePath(existentEntity, false);
        entityDAO.updateActive(existentEntity);

        return regionDAO.merge(existentEntity);
    }

    /**
     *
     * @param regionId
     * @throws Exception
     */
    @Override
    public void delete(String regionId) throws Exception {
        Region region = regionDAO.findByRegionId(regionId);

        if (region == null) {
            throw new ObjectRetrievalFailureException(Region.class, regionId);
        }
        region.setActive(false);
        entityDAO.updateActive(region);
        regionDAO.merge(region);
    }

    /**
     *
     * @param regionId
     * @return
     * @throws Exception
     */
    @Override
    public Region get(String regionId) throws Exception {
        // Get region basic data and parent info
        return regionDAO.findByRegionId(regionId);
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
    public List<Region> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return regionDAO.list(entity, firstResult, firstResult + rowsPerPage);
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
    public List<Region> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        return regionDAO.search(entity, filter, firstResult, firstResult + rowsPerPage);
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
        return Utils.getTotalPages(regionDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

//    @Override
//    public List<Region> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return regionDAO.searchTree(filter, pageNumber, pageNumber);
//    }
//
//    @Override
//    public int getSearchTotalPages(String search, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(regionDAO.searchTree(search, -1, -1).size(), rowsPerPage);
//    }
//
//    @Override
//    public List<Merchant> getMerchants(long id) throws Exception {
//        return regionDAO.getMerchants(id);
//    }
//    @Override
//    public List<EntityRelation> getEntityRelationMerchants(long id) throws Exception {
//        return regionDAO.getEntityRelationMerchants(id);
//    }
//    @Override
//    public List<Survey> getSurveys(long id) throws Exception {
//        return regionDAO.getSurveys(id);
//    }
//    @Override
//    public void addMerchant(EntityRelation entityRelation) throws Exception {
//        regionDAO.addMerchant(updatedMerchantParentInfo(entityRelation));
//    }
//
//    @Override
//    public void updateMerchant(EntityRelation entityRelation) throws Exception {
//        Merchant merchant = updatedMerchantParentInfo(entityRelation);
//        Date date = new java.util.Date();
//        merchant.setUpdatedAt(new Timestamp(date.getTime()));
//
//        regionDAO.updateMerchant(merchant);
//    }
//    @Override
//    public void deleteMerchant(long idEntityRelation) throws Exception {
    // Deleting just the Relation between the merchant and the Region
//        EntityRelation entityRelation = entityRelationDAO.findById(idEntityRelation);
//        entityRelationDAO.delete(entityRelation);
//    }
//    @Override
//    public List<EntitySurvey> searchSurveys(long id, String filter, int firstResult, int maxResult) throws Exception {
//        return regionDAO.searchSurveys(id, filter, firstResult, maxResult);
//    }
//
//    @Override
//    public int searchTotalPagesSurvey(long idEntity, String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(regionDAO.searchSurveys(idEntity, filter, -1, -1).size(), rowsPerPage);
//    }
//
//  @Override
//    public void addSurvey(EntitySurvey entitySurvey) throws Exception {
//        Survey survey = entitySurvey.getSurvey();
//        survey.getEntitySurveys().add(entitySurvey);
//        regionDAO.addSurvey(survey);
//
//        // Reset the previous starting survey (if existing) to false
//        if (entitySurvey.getStartingSurvey()) {
//            entitySurveyDAO.resetStartingSurvey(entitySurvey);
//        }
//    }
//
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
//    public List<Organization> getPrevOrganizations(long id) throws Exception {
//        return regionDAO.getPrevOrganizations(id);
//    }
//    private Merchant updatedMerchantParentInfo(EntityRelation entityRelation) {
//
//        Organization organization = entityRelationDAO.getOrganizationByRegion(entityRelation.getRegion().getId());
//        entityRelation.setOrganization(organization);
//
//        Merchant merchant = entityRelation.getMerchant();
//        merchant.getEntityRelations().add(entityRelation);
//
//        return merchant;
//    }
    @Override
    public Entity copy(String entityId, long parentId) {
        Region original = regionDAO.findByRegionId(entityId);
        if (original == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }

        Entity parent = entityDAO.findById(parentId);
        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parentId);
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.REGION, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        Region copy = new Region();

        copy.setParent(parent);
        copy.setType(EntityTypeEnum.REGION);
        BeanUtils.copyProperties(original, copy, new String[]{"entityId"});
        entityDAO.updatePath(copy, true);
        regionDAO.persist(copy);

        return copy;
    }
}
