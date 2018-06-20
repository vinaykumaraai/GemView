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
import com.luretechnologies.server.data.dao.OrganizationDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Organization;
import com.luretechnologies.server.service.OrganizationService;
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
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private EntityDAO entityDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Organization create(Organization entity) throws Exception {

        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parent.getEntityId());
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.ORGANIZATION, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        entity.setParent(parent);
        entity.setType(EntityTypeEnum.ORGANIZATION);
        entityDAO.updatePath(entity, true);
        organizationDAO.persist(entity);

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
    public Organization update(String entityId, Organization entity) throws Exception {

        Organization existentOrganization = organizationDAO.findByOrganizationId(entityId);

        if (existentOrganization == null) {
            throw new ObjectRetrievalFailureException(Organization.class, entityId);
        }

        Entity parent = entityDAO.findById(existentOrganization.getParentId());

        if (parent == null) {
            throw new PersistenceException("No parent id.");
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.ORGANIZATION, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        BeanUtils.copyProperties(entity, existentOrganization, new String[]{"entityId"});
        existentOrganization.setParent(parent);
        existentOrganization.setType(EntityTypeEnum.ORGANIZATION);
        entityDAO.updatePath(existentOrganization, false);
        entityDAO.updateActive(existentOrganization);

        return organizationDAO.merge(existentOrganization);
    }

    /**
     *
     * @param organizationId
     * @throws Exception
     */
    @Override
    public void delete(String organizationId) throws Exception {

        Organization organization = organizationDAO.findByOrganizationId(organizationId);

        if (organization == null) {
            throw new ObjectRetrievalFailureException(Organization.class, organizationId);
        }

        organization.setActive(false);
        entityDAO.updateActive(organization);
        organizationDAO.merge(organization);
    }

    /**
     *
     * @param organizationId
     * @return
     * @throws Exception
     */
    @Override
    public Organization get(String organizationId) throws Exception {
        // Get organization basic data and parent info
        return organizationDAO.findByOrganizationId(organizationId);
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
    public List<Organization> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return organizationDAO.list(entity, firstResult, rowsPerPage);
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
    public List<Organization> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return organizationDAO.search(entity, filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(organizationDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

//    @Override
//    public void save(EntityRelation entityRelation) throws Exception {
//        Organization organization = entityRelation.getOrganization();
//        organization.getEntityRelations().add(entityRelation);
//
//        organizationDAO.save(organization);
//    }
//
//    @Override
//    public void update(EntityRelation entityRelation) throws Exception {
//        Organization organization = entityRelation.getOrganization();
//        Date date = new java.util.Date();
//        organization.setUpdatedAt(new Timestamp(date.getTime()));
//
//        organizationDAO.update(organization);
//    }
//
//    @Override
//    public void delete(long id) throws Exception {
//        EntityRelation entityRelation = entityRelationDAO.findById(id);
//
//        Organization organization = entityRelation.getOrganization();
//        organization.getEntityRelations().add(entityRelation);
//
//        organizationDAO.delete(id);
//    }
//
//    @Override
//    public Organization get(long id) throws Exception {
//        return organizationDAO.findById(id);
//    }
//
//    @Override
//    public List<Organization> list() throws Exception {
//        return organizationDAO.list();
//    }
//    @Override
//    public List<Organization> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception {
//        return organizationDAO.searchTree(filter, pageNumber * rowsPerPage, rowsPerPage);
//    }
//    @Override
//    public List<EntityRelation> getEntityRelationOrganizations(String filter, int pageNumber, int rowsPerPage, User user) throws Exception {
//        return organizationDAO.getEntityRelationOrganizations(filter, pageNumber * rowsPerPage, rowsPerPage, user);
//    }
//
//    @Override
//    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception {
//        return Utils.getTotalPages(organizationDAO.searchTree(filter, -1, -1).size(), rowsPerPage);
//    }
//
//    @Override
//    public List<Region> getRegions(long id) throws Exception {
//        return organizationDAO.getRegions(id);
//    }
//
//    @Override
//    public List<Survey> getSurveys(long id) throws Exception {
//        return organizationDAO.getSurveys(id);
//    }
//    @Override
//    public List<EntityRelation> getEntityRelationRegions(long id) throws Exception {
//        return organizationDAO.getEntityRelationRegions(id);
//    }
//
//    @Override
//    public void addRegion(EntityRelation entityRelation) throws Exception {
//        Region region = entityRelation.getRegion();
//        region.getEntityRelations().add(entityRelation);
//
//        organizationDAO.addRegion(region);
//    }
//
//    @Override
//    public void updateRegion(EntityRelation entityRelation) throws Exception {
//        Region region = entityRelation.getRegion();
//        region.getEntityRelations().add(entityRelation);
//        Date date = new java.util.Date();
//        region.setUpdatedAt(new Timestamp(date.getTime()));
//
//        organizationDAO.updateRegion(region);
//    }
//    @Override
//    public void deleteRegion(long idEntityRelation) throws Exception {
//        EntityRelation entityRelation = entityRelationDAO.findById(idEntityRelation);
//        Region region = entityRelation.getRegion();
//        region.getEntityRelations().add(entityRelation);
//
//        regionDAO.delete(idEntityRelation);
//    }
//    @Override
//    public List<EntitySurvey> searchSurveys(long id, String filter, int firstResult, int maxResult) throws Exception {
//        return organizationDAO.searchSurveys(id, filter, maxResult, maxResult);
//    }
//
//    @Override
//    public int searchTotalPagesSurvey(long idEntity, String filter, int rowsPerPage) throws Exception {
//        // return Utils.getTotalPages(organizationDAO.searchSurveys(idEntity, filter, -1, -1).size(), rowsPerPage);
//        return 0;
//    }
//    @Override
//    public void addSurvey(EntitySurvey entitySurvey) throws Exception {
//        Survey survey = entitySurvey.getSurvey();
//        survey.getEntitySurveys().add(entitySurvey);
//        organizationDAO.addSurvey(survey);
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

    @Override
    public Entity copy(String entityId, long parentId) {
        Organization original = organizationDAO.findByOrganizationId(entityId);
        if (original == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }
        
        Entity parent = entityDAO.findById(parentId);
        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parentId);
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.ORGANIZATION, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        Organization copy = new Organization();
                
        copy.setParent(parent);
        copy.setType(EntityTypeEnum.ORGANIZATION);
        BeanUtils.copyProperties(original, copy, new String[]{"entityId"});        
        entityDAO.updatePath(copy, true);
        organizationDAO.persist(copy);

        return copy;
    }
}
