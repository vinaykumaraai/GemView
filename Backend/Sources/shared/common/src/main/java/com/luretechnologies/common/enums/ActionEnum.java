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
 * Suarez
 */
public enum ActionEnum {

    /**
     * Service requested
     *
     */
    REQUEST(0),
    /**
     * Server response
     *
     */
    RESPONSE(1),
    /**
     * Saving into database
     *
     */
    SAVE(2),
    /**
     * A server process info
     *
     */
    INFO(3),
    /**
     * ERROR
     *
     */
    ERROR(4);

    private final Integer id;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, ActionEnum> map = new HashMap<>();

    ActionEnum(Integer id) {
        this.id = id;
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
     * @return the name Control
     */
    public String getName() {
        switch (this) {
            case REQUEST:
                return "Service requested.";
            case RESPONSE:
                return "Server response.";
            case SAVE:
                return "Saving into database.";
            case INFO:
                return "A server process info.";
            case ERROR:
                return "Error in Server.";
            default:
                return "";
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public static Integer getIdFromName(String name) {

        if ("REQUEST".contains(name)) {
            return 1;
        }

        if ("RESPONSE".contains(name)) {
            return 2;
        }

        if ("SAVE".contains(name)) {
            return 3;
        }

        if ("INFO".contains(name)) {
            return 4;
        }
        if ("ERROR".contains(name)) {
            return 5;
        }

        return 0;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            map.put(actionEnum.getId(), actionEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final ActionEnum getActionEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }
}
