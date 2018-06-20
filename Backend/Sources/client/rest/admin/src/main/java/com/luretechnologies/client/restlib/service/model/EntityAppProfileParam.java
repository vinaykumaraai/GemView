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
import javax.annotation.Generated;

@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T11:49:45.337-04:00")
public class EntityAppProfileParam {

    private Long id = null;
    
    private Long entityAppProfileId = null;
    
    private Long appProfileParamValueId = null;
    
    private String value = null;
        
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
     * @return the entityAppProfile
     */
    @ApiModelProperty(value = "")
    @JsonProperty("entityAppProfileId")
    public Long getEntityAppProfileId() {
        return entityAppProfileId;
    }

    /**
     * @param entityAppProfileId
     */
    public void setEntityAppProfileId(Long entityAppProfileId) {
        this.entityAppProfileId = entityAppProfileId;
    }

    /**
     * @return the appProfileParamValue
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileParamValueId")
    public Long getAppProfileParamValueId() {
        return appProfileParamValueId;
    }

    /**
     * @param appProfileParamValueId
     */
    public void setAppProfileParamValueId(Long appProfileParamValueId) {
        this.appProfileParamValueId = appProfileParamValueId;
    }

    /**
     * @return the value
     */
    @ApiModelProperty(value = "")
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    public EntityAppProfileParam() {
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EntityAppProfileParam {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    entityAppProfileId: ").append(StringUtil.toIndentedString(getEntityAppProfileId())).append("\n");
        sb.append("    appProfileParamValueId: ").append(StringUtil.toIndentedString(getAppProfileParamValueId())).append("\n");
        sb.append("    value: ").append(StringUtil.toIndentedString(getValue())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
