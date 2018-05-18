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
package com.luretechnologies.common.enums;

/**
 *
 *
 */
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author developer
 */
public enum SettingEnum {

    /**
     * HSM IP address
     *
     */
    HSM_ADDRESS(1, "hsm_address", "HSM IP address", FormatEnum.ALPHA),
    /**
     * HSM port number
     *
     */
    HSM_PORT(2, "hsm_port", "HSM port number", FormatEnum.NUMERIC),
    /**
     * HSM Base Derivation Key
     *
     */
    HSM_BDK(3, "hsm_bdk", "HSM Base Derivation Key", FormatEnum.ALPHA_NUMERIC);

    private final Integer id;
    private final String name;
    private final String description;
    private final FormatEnum dataType;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, SettingEnum> map = new HashMap<>();

    SettingEnum(Integer id, String name, String description, FormatEnum dataType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dataType = dataType;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public FormatEnum getDataType() {
        return dataType;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (SettingEnum settingEnum : SettingEnum.values()) {
            map.put(settingEnum.getId(), settingEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final SettingEnum getSettingEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }
}
