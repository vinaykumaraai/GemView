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
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.annotation.Generated;

@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T15:43:45.337-04:00")
public class EntityAppProfile {
    
    private Long id = null;
    
    private Entity entity = null;
    
    private Long appProfileId = null;
    
    private List<EntityAppProfileParam> entityappprofileparamCollection = null;
    
    public EntityAppProfile() {
    }

    /**
     * @return the id
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the entity
     */
    @ApiModelProperty(value = "")
    @JsonProperty("entity")
    public Entity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * @return the appProfile
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileId")
    public Long getAppProfileId() {
        return appProfileId;
    }

    /**
     * @param appProfileId
     */
    public void setAppProfileId(Long appProfileId) {
        this.appProfileId = appProfileId;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EntityAppProfile {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    entity: ").append(StringUtil.toIndentedString(entity)).append("\n");
        sb.append("    appProfileId: ").append(StringUtil.toIndentedString(getAppProfileId())).append("\n");
        sb.append("    entityappprofileparamCollection: ").append(StringUtil.toIndentedString(getEntityappprofileparamCollection())).append("\n");
        sb.append("}");
        return sb.toString();
    } 

    /**
     * @return the entityappprofileparamCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("entityappprofileparamCollection")
    public List<EntityAppProfileParam> getEntityappprofileparamCollection() {
        return entityappprofileparamCollection;
    }

    /**
     * @param entityappprofileparamCollection the entityappprofileparamCollection to set
     */
    public void setEntityappprofileparamCollection(List<EntityAppProfileParam> entityappprofileparamCollection) {
        this.entityappprofileparamCollection = entityappprofileparamCollection;
    }
    
}
