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

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author developer
 */
public enum HostSettingEnum {

    /**
     *
     */
    HOST_IP(1, "Host ip", "host ip address", FormatEnum.ALPHA, 2),
    /**
     *
     */
    HOST_PORT(2, "Host port", "host port number", FormatEnum.NUMERIC, 2),
    /**
     *
     */
    HOST_SSL(3, "Host ssl", "host ssl mode", FormatEnum.BOOLEAN, 2),
    /**
     *
     */
    HOST_TIME_CHECK(4, "Host time check", "host time check", FormatEnum.DATE, 2),
    /**
     *
     */
    HOST_REVERSAL_ATTEMPTS(5, "Host reversal attempts", "host reversal attempts", FormatEnum.NUMERIC, 2),
    /**
     *
     */
    HOST_MAX_SEQUENCE_NUMBER(6, "Host maximum sequence number", "Host maximum sequence number", FormatEnum.NUMERIC, 2),
    /**
     *
     */
    HOST_URL(7, "Host url", "Host url", FormatEnum.ALPHANUMERIC_SPECIAL, 2),
    /**
     *
     */
    HOST_USERNAME(8, "Host username", "Host Username", FormatEnum.ALPHANUMERIC_SPECIAL, 2),
    /**
     *
     */
    HOST_PASSWORD(9, "Host password", "Host password", FormatEnum.ALPHANUMERIC_SPECIAL, 2),
    /**
     *
     */
    HOST_REVERSAL_DELAY(10, "Host delay before creating the reversal (seconds)", "Host delay before creating the reversal (seconds)", FormatEnum.ALPHANUMERIC_SPECIAL, 2),
    /**
     *
     */
    HOST_REVERSAL_WAIT(11, "Host delay before transmitting the reversal (seconds)", "Host delay before transmitting the reversal (seconds)", FormatEnum.ALPHANUMERIC_SPECIAL, 2);

    private final Integer id;
    private final String name;
    private final String description;
    private final FormatEnum dataType;
    private final int group;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, HostSettingEnum> map = new HashMap<>();

    HostSettingEnum(Integer id, String name, String description, FormatEnum dataType, int group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dataType = dataType;
        this.group = group;
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

    /**
     *
     * @return
     */
    public int getGroup() {
        return group;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (HostSettingEnum hostSettingEnum : HostSettingEnum.values()) {
            map.put(hostSettingEnum.getId(), hostSettingEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final HostSettingEnum getHostSettingEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
