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
import java.util.Date;
import javax.annotation.Generated;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class Model {

    
    private Long id;
    private String name;
    private String description;
    private String manufacturer;
    private Date updatedAt;
    private Boolean available;
    private Boolean rkiCapable;
    private Boolean osUpdate;

    /**
     * @return the id
     */
    @ApiModelProperty(value = "Id")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @ApiModelProperty(value = "Name")
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    @ApiModelProperty(value = "Description")
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the manufacturer
     */
    @ApiModelProperty(value = "Manufacturer")
    @JsonProperty("manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the updatedAt
     */
    @ApiModelProperty(value = "Updated At")
    @JsonProperty("updatedAt")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the available
     */
    @ApiModelProperty(value = "Available")
    @JsonProperty("available")
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * @return the rkiCapable
     */
    @ApiModelProperty(value = "RKI capable")
    @JsonProperty("rkiCapable")
    public Boolean getRkiCapable() {
        return rkiCapable;
    }

    /**
     * @param rkiCapable the rkiCapable to set
     */
    public void setRkiCapable(Boolean rkiCapable) {
        this.rkiCapable = rkiCapable;
    }

    /**
     * @return the osUpdate
     */
    @ApiModelProperty(value = "OS Update")
    @JsonProperty("osUpdate")
    public Boolean getOsUpdate() {
        return osUpdate;
    }

    /**
     * @param osUpdate the osUpdate to set
     */
    public void setOsUpdate(Boolean osUpdate) {
        this.osUpdate = osUpdate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Model {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    manufacturer: ").append(StringUtil.toIndentedString(getManufacturer())).append("\n");
        sb.append("    updatedAt: ").append(StringUtil.toIndentedString(getUpdatedAt())).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(getDescription())).append("\n");
        sb.append("    available: ").append(StringUtil.toIndentedString(getAvailable())).append("\n");
        sb.append("    rkiCapable: ").append(StringUtil.toIndentedString(getRkiCapable())).append("\n");
        sb.append("    osUpdate: ").append(StringUtil.toIndentedString(getOsUpdate())).append("\n");        
        sb.append("}");
        return sb.toString();
    }
}
