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

import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.Organization;
import java.util.List;

/**
 *
 */
public interface OrganizationService {

    /**
     *
     * @param organization
     * @return
     * @throws Exception
     */
    public Organization create(Organization organization) throws Exception;

    /**
     *
     * @param organizationId
     * @param organization
     * @return
     * @throws Exception
     */
    public Organization update(String organizationId, Organization organization) throws Exception;

    /**
     *
     * @param organizationId
     * @return
     * @throws Exception
     */
    public Organization get(String organizationId) throws Exception;

    /**
     *
     * @param organizationId
     * @throws Exception
     */
    public void delete(String organizationId) throws Exception;

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Organization> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Organization> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception;

//    public List<Organization> searchTree(String filter, int pageNumber, int rowsPerPage) throws Exception;
//
//    
//    public int getSearchTotalPages(String search, int rowsPerPage) throws Exception;
//
//    public List<Region> getRegions(long id) throws Exception;
//
//    public List<Survey> getSurveys(long id) throws Exception;
//    public List<EntityRelation> getEntityRelationRegions(long id) throws Exception;
//
//    public void addRegion(EntityRelation entityRelation) throws Exception;
//
//    public void updateRegion(EntityRelation entityRelation) throws Exception;
//    public void deleteRegion(long idEntityRelation) throws Exception;
//
//    public List<EntitySurvey> searchSurveys(long id, String filter, int firstResult, int maxResult) throws Exception;
//
//    public int searchTotalPagesSurvey(long idEntity, String search, int rowsPerPage) throws Exception;
//
//     public void addSurvey(EntitySurvey entitySurvey) throws Exception;
//
//    public void updateSurvey(EntitySurvey entitySurvey) throws Exception;
//
//    public void deleteSurvey(long idEntitySurvey) throws Exception;

    public Entity copy(String entityId, long parentId);
}
