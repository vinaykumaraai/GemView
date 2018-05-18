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
public enum CVVResultEnum {

    /**
     *
     */
    MATCH(1, "Match", "CVV2/CID match"),
    /**
     *
     */
    NO_MATCH(2, "No match", "CVV2/CID no match"),
    /**
     *
     */
    NO_PROCESSED(3, "No processed", "Not processed"),
    /**
     *
     */
    NO_PRESENT(4, "No present", "CVV2/CID is on the card, but the merchant has indicated that CVV2/CID is not present"),
    /**
     *
     */
    NO_CERTIFIED(5, "No certified", "Issuer is not certified for CVV2/CID");

    private final Integer id;
    private final String name;
    private final String description;

    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, CVVResultEnum> map = new HashMap<>();

    CVVResultEnum(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (CVVResultEnum cVVResultEnum : CVVResultEnum.values()) {
            map.put(cVVResultEnum.getId(), cVVResultEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final CVVResultEnum getCVVResultEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }
}
