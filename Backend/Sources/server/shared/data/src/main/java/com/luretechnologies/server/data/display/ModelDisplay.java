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
package com.luretechnologies.server.data.display;

/**
 *
 *
 */
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * Stores terminal's model.
 */
public class ModelDisplay {

    /**
     *
     */
    public ModelDisplay() {
    }

    @ApiModelProperty(value = "The database identification.")
    private Long id;

    @ApiModelProperty(value = "The name.", required = true)
    private String name;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The manufacturer.")
    private String manufacturer;

    @ApiModelProperty(value = "Last update")
    private Date updatedAt;

    @ApiModelProperty(value = "If is available or not")
    private Boolean available;

    @ApiModelProperty(value = "Device is RKI capable (Remote key injection)")
    private Boolean rkiCapable;

    @ApiModelProperty(value = "Device can accept O/S update")
    private Boolean osUpdate;

    /**
     * @return the id
     */
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
     * @return the name
     */
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
     * @return the updatedAt
     */
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
    public Boolean getOsUpdate() {
        return osUpdate;
    }

    /**
     * @param osUpdate the osUpdate to set
     */
    public void setOsUpdate(Boolean osUpdate) {
        this.osUpdate = osUpdate;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
