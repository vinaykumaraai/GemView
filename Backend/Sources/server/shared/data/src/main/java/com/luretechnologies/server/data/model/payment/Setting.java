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
package com.luretechnologies.server.data.model.payment;

import com.luretechnologies.common.enums.SettingEnum;
import com.luretechnologies.common.enums.SettingGroupEnum;
import com.luretechnologies.server.jpa.converters.SettingEnumConverter;
import com.luretechnologies.server.jpa.converters.SettingGroupEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author developer
 */
@Entity
@Table(name = "Setting")
public class Setting implements Serializable {

    /**
     *
     */
    public Setting() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @Column(name = "setting", nullable = false, length = 2)
    @Convert(converter = SettingEnumConverter.class)
    @ApiModelProperty(value = "The name.")
    private SettingEnum name;

    @Column(name = "value", nullable = false, length = 200)
    @ApiModelProperty(value = "The value.")
    private String value;

    @Column(name = "`group`", nullable = false, length = 2)
    @Convert(converter = SettingGroupEnumConverter.class)
    @ApiModelProperty(value = "The group.")
    private SettingGroupEnum group;

    /**
     * Database identification.
     */
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Database identification.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * SettingEnum identification. Ex: 1-HSM_ADDRESS, 2- HSM_PORT , 3- Host SSL,
     * 4- BDK
     *
     * @param setting
     */
    public void setName(SettingEnum setting) {
        this.name = setting;
    }

    /**
     * SettingEnum identification. Ex: 1-HSM_ADDRESS, 2- HSM_PORT , 3- Host SSL,
     * 4- BDK
     *
     * @return
     */
    public SettingEnum getName() {
        return name;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * SettingEnum' group.
     *
     * @param group
     */
    public void setGroup(SettingGroupEnum group) {
        this.group = group;
    }

    /**
     * SettingEnum' group.
     *
     * @return
     */
    public SettingGroupEnum getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
