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
 *
 * @author developer
 */
public enum DayEnum {

    /**
     *
     */
    ALL(0),
    /**
     *
     */
    SUNDAY(1),
    /**
     *
     */
    MONDAY(2),
    /**
     *
     */
    TUESDAY(3),
    /**
     *
     */
    WEDNESDAY(4),
    /**
     *
     */
    THURSDAY(5),
    /**
     *
     */
    FRIDAY(6),
    /**
     *
     */
    SATURDAY(7);

    private final Integer id;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, DayEnum> map = new HashMap<>();

    DayEnum(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (DayEnum dayEnum : DayEnum.values()) {
            map.put(dayEnum.getId(), dayEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final DayEnum getDayEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
