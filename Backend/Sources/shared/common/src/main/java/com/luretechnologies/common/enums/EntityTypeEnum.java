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

/**
 * The EntityTypeEnum class
 *
 *
 */
public enum EntityTypeEnum {

    /**
     * Enterprise entity identifier, the whole tree
     */
    ENTERPRISE(0),
    /**
     * AMS entity identifier
     */
    //    AMS,
    /**
     * Organization entity identifier
     */
    ORGANIZATION(1),
    /**
     * Region entity identifier
     */
    REGION(2),
    /**
     * Merchant entity identifier
     */
    MERCHANT(3),
    /**
     * Terminal entity identifier
     */
    TERMINAL(4),
    /**
     * Device entity identifier
     */
    DEVICE(5);
    /**
     * Clerk entity identifier
     */
    //CLERK,
    /**
     * User entity identifier
     */
    //USER,
    /**
     * Application Parameters Value identifier
     */
    //APPLICATION_PARAMETER_VALUE,
    /**
     * Application Parameters identifier
     */
    //APPLICATION_PARAMETER;

    private final int id;

    private EntityTypeEnum(int value) {
        this.id = value;
    }

    public final int getId() {
        return id;
    }

    public static EntityTypeEnum getById(int value) {
        for (EntityTypeEnum item : EntityTypeEnum.values()) {
            if (item.getId() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Value does not exist.");
    }

    public boolean isParentCorrect(EntityTypeEnum parentType) {
        if (this.equals(EntityTypeEnum.ENTERPRISE)) {
            return false;
        }
        if (this.equals(EntityTypeEnum.ENTERPRISE)
                || this.equals(EntityTypeEnum.ORGANIZATION)
                || this.equals(EntityTypeEnum.REGION)
                || this.equals(EntityTypeEnum.ENTERPRISE)) {
            if (parentType.equals(EntityTypeEnum.MERCHANT)
                    || parentType.equals(EntityTypeEnum.TERMINAL)
                    || parentType.equals(EntityTypeEnum.DEVICE)) {
                return false;
            }
        }
        if (this.equals(EntityTypeEnum.TERMINAL)) {
            if (!parentType.equals(EntityTypeEnum.MERCHANT)) {
                return false;
            }
        }
        if (this.equals(EntityTypeEnum.DEVICE)) {
            if (!parentType.equals(EntityTypeEnum.TERMINAL)) {
                return false;
            }
        }
        return true;
    }

    public String getAsAttributeName() {
        return this.toString().toLowerCase();
    }

}
