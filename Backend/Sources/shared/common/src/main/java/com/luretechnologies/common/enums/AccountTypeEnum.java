/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
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
public enum AccountTypeEnum {

    /**
     * None
     *
     */
    NONE(1, "None", "None Specified"),
    /**
     * Checking Account
     *
     */
    CHECKING(2, "Checking", "Checking"),
    /**
     * Savings Account
     *
     */
    SAVINGS(3, "Savings", "Savings");

    private final Integer id;
    private final String name;
    private final String description;

    // This is for JPA implementation
    // This is look up map to get Enum value from int value.
    private static final Map<Integer, AccountTypeEnum> map = new HashMap<>();

    AccountTypeEnum(Integer id, String name, String description) {
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
        for (AccountTypeEnum item : AccountTypeEnum.values()) {
            if(item.name.contains(name)) {
                return item.id;
            }
        }
        return 0;
    }

    static {
        for (AccountTypeEnum item : AccountTypeEnum.values()) {
            map.put(item.getId(), item);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final AccountTypeEnum getAccountTypeEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        }
        return null;
    }

}
