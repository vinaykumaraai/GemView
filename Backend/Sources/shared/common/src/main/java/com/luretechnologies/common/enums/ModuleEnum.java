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
public enum ModuleEnum {

    /**
     *
     */
    ADMIN(0, "Admin Module", "Admin Module"),
    /**
     *
     */
    PAYMENT(1, "Payment Module", "Payment Module"),
    /**
     *
     */
    POWAPIN_PAYMENT(2, "PowaPIN Payment Module", "PowaPIN Payment Module"),
    /**
     *
     */
    IT_DASHBOARD(3, "IT Dashboard Module", "IT Dashboard Module"),
    /**
     *
     */
    DRIVING(4, "Driving Module", "Driving Module"),
    /**
     *
     */
    TMS(5, "TMS Module", "TMS Module"),
    /**
     *
     */
    CORE(100, "Core Module", "Core Module"),
    /**
     *
     */
    WORLDPAY_HOST(200, "Worldpay Host Module", "Worldpay Host Module"),
    /**
     *
     */
    FIRSTDATA_HOST(201, "Firstdata Host Module", "Firstdata Host Module"),
    /**
     *
     */
    CHASE_HOST(202, "Chase Host Module", "Chase Host Module"),
    /**
     *
     */
    BLACKSTONE_HOST(203, "Blackstone Host Module", "Blackstone Host Module"),
    /**
     *
     */
    TEST_HOST(300, "Test Host Module", "Test Host Module");

    private final Integer id;
    private final String name;
    private final String description;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, ModuleEnum> map = new HashMap<>();

    ModuleEnum(Integer id, String name, String description) {
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
        for (ModuleEnum queueEnum : ModuleEnum.values()) {
            map.put(queueEnum.getId(), queueEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final ModuleEnum getQueueEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
