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
public enum BenefitTypeEnum {

    /**
     * FoodStamp type
     *
     */
    FOOD_STAMP(1, "FoodStamp", "Food Stamp"),
    /**
     * Cash type
     *
     */
    CASH(2, "Cash", "Cash Benefit"),
    /**
     * Cash Withdrawal type
     *
     */
    CASH_WITHDRAWAL(3, "Cash Withdrawal", "Cash Withdrawal"),
    /**
     *  Voucher type
     *
     */
    VOUCHER(4, "Voucher", "Electronic Voucher"),
    /**
     * Test mode, it will be used to send a test message that will check the
     * status of the whole System.
     *
     */
    NONE(100, "None", "None");

    private final Integer id;
    private final String name;
    private final String description;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, BenefitTypeEnum> map = new HashMap<>();

    BenefitTypeEnum(Integer id, String name, String description) {
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

    /**
     *
     * @param name
     * @return
     */
    public static Integer getIdFromName(String name) {
        for (BenefitTypeEnum enumItem : BenefitTypeEnum.values()) {
            if (enumItem.getName().toUpperCase().contains(name.toUpperCase())) {
                return enumItem.getId();
            }
        }
        return 0;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (BenefitTypeEnum modeEnum : BenefitTypeEnum.values()) {
            map.put(modeEnum.getId(), modeEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final BenefitTypeEnum getModeEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
