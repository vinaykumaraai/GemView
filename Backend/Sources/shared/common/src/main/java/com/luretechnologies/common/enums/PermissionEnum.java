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

import com.luretechnologies.common.enums.utils.PersistentEnum;

/**
 *
 *
 */
public enum PermissionEnum implements PersistentEnum {

    SUPER(PermissionGroupEnum.ALL, PermissionAccessEnum.ALL, "Allow all actions"),
    /* ********************************************************************** */
    /* USER [100 - 199]                                                          */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_USER(PermissionGroupEnum.USER, PermissionAccessEnum.ALL, "Allow all user actions"),
    /**
     *
     */
    READ_USER(PermissionGroupEnum.USER, PermissionAccessEnum.READ, "Read users"),
    /**
     *
     */
    CREATE_USER(PermissionGroupEnum.USER, PermissionAccessEnum.CREATE, "Create user"),
    /**
     *
     */
    UPDATE_USER(PermissionGroupEnum.USER, PermissionAccessEnum.UPDATE, "Edit user"),
    /**
     *
     */
    DELETE_USER(PermissionGroupEnum.USER, PermissionAccessEnum.DELETE, "Delete user"),
    /* ********************************************************************** */
    /* ROLE [200 - 299]                                                       */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_ROLE(PermissionGroupEnum.ROLE, PermissionAccessEnum.ALL, "Allow all role actions"),
    /**
     *
     */
    READ_ROLE(PermissionGroupEnum.ROLE, PermissionAccessEnum.READ, "Read roles"),
    /**
     *
     */
    CREATE_ROLE(PermissionGroupEnum.ROLE, PermissionAccessEnum.CREATE, "Create role"),
    /**
     *
     */
    UPDATE_ROLE(PermissionGroupEnum.ROLE, PermissionAccessEnum.UPDATE, "Edit role"),
    /**
     *
     */
    DELETE_ROLE(PermissionGroupEnum.ROLE, PermissionAccessEnum.DELETE, "Delete role"),
    /* ********************************************************************** */
    /* CLIENT [300 - 399]                                                     */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_CLIENT(PermissionGroupEnum.CLIENT, PermissionAccessEnum.ALL, "Allow all client actions"),
    /**
     *
     */
    READ_CLIENT(PermissionGroupEnum.CLIENT, PermissionAccessEnum.READ, "Read clients"),
    /**
     *
     */
    CREATE_CLIENT(PermissionGroupEnum.CLIENT, PermissionAccessEnum.CREATE, "Create client"),
    /**
     *
     */
    UPDATE_CLIENT(PermissionGroupEnum.CLIENT, PermissionAccessEnum.UPDATE, "Update client"),
    /**
     *
     */
    DELETE_CLIENT(PermissionGroupEnum.CLIENT, PermissionAccessEnum.DELETE, "Delete client"),
    /* ********************************************************************** */
    /* TRANSACTION [400 - 499]                                              */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_TRANSACTION(PermissionGroupEnum.TRANSACTION, PermissionAccessEnum.ALL, "Allow all transaction actions"),
    /**
     *
     */
    READ_TRANSACTION(PermissionGroupEnum.TRANSACTION, PermissionAccessEnum.READ, "Read transactions"),
    /**
     *
     */
    CREATE_TRANSACTION(PermissionGroupEnum.TRANSACTION, PermissionAccessEnum.CREATE, "Create transaction"),
    /**
     *
     */
    UPDATE_TRANSACTION(PermissionGroupEnum.TRANSACTION, PermissionAccessEnum.UPDATE, "Update transaction"),
    /* ********************************************************************** */
    /*  ENTITY [500 - 599]                                                         */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_ENTITY(PermissionGroupEnum.ENTITY, PermissionAccessEnum.ALL, "Allow all entity actions"),
    /**
     *
     */
    READ_ENTITY(PermissionGroupEnum.ENTITY, PermissionAccessEnum.READ, "Read entity information"),
    /**
     *
     */
    CREATE_ENTITY(PermissionGroupEnum.ENTITY, PermissionAccessEnum.CREATE, "Create entity"),
    /**
     *
     */
    UPDATE_ENTITY(PermissionGroupEnum.ENTITY, PermissionAccessEnum.UPDATE, "Update entity"),
    /**
     *
     */
    DELETE_ENTITY(PermissionGroupEnum.ENTITY, PermissionAccessEnum.DELETE, "Delete entity"),
    
    /* ********************************************************************** */
    /* HOST [600 - 699]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_HOST(PermissionGroupEnum.HOST, PermissionAccessEnum.ALL, "Allow all host actions"),
    /**
     *
     */
    READ_HOST(PermissionGroupEnum.HOST, PermissionAccessEnum.READ, "Read host information"),
    /**
     *
     */
    CREATE_HOST(PermissionGroupEnum.HOST, PermissionAccessEnum.CREATE, "Create host"),
    /**
     *
     */
    UPDATE_HOST(PermissionGroupEnum.HOST, PermissionAccessEnum.UPDATE, "Update host"),
    /* ********************************************************************** */
    /* SETTING [700 - 799]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_SETTING(PermissionGroupEnum.SETTING, PermissionAccessEnum.ALL, "Allow all setting actions"),
    /**
     *
     */
    READ_SETTING(PermissionGroupEnum.SETTING, PermissionAccessEnum.READ, "Read settings"),
    /**
     *
     */
    CREATE_SETTING(PermissionGroupEnum.SETTING, PermissionAccessEnum.CREATE, "Create setting"),
    /**
     *
     */
    UPDATE_SETTING(PermissionGroupEnum.SETTING, PermissionAccessEnum.UPDATE, "Update setting"),
    /**
     *
     */
    DELETE_SETTING(PermissionGroupEnum.SETTING, PermissionAccessEnum.DELETE, "Delete setting"),
    /* ********************************************************************** */
    /* DASHBOARD [800 - 899]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    READ_DASHBOARD(PermissionGroupEnum.DASHBOARD, PermissionAccessEnum.READ, "Read dashboard"),
    /* ********************************************************************** */
    /* AUDIT [900 - 999]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_AUDIT(PermissionGroupEnum.AUDIT, PermissionAccessEnum.ALL, "Allow all audit actions"),
    /**
     *
     */
    READ_AUDIT(PermissionGroupEnum.AUDIT, PermissionAccessEnum.READ, "Read audit"),
    /**
     *
     */
    CREATE_AUDIT(PermissionGroupEnum.AUDIT, PermissionAccessEnum.CREATE, "Create audit"),
    /**
     *
     */
    UPDATE_AUDIT(PermissionGroupEnum.AUDIT, PermissionAccessEnum.UPDATE, "Update audit"),
    /**
     *
     */
    DELETE_AUDIT(PermissionGroupEnum.AUDIT, PermissionAccessEnum.DELETE, "Delete audit"),
    /* ********************************************************************** */
    /* APPSTORE [1000 - 1099]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_APPSTORE(PermissionGroupEnum.APPSTORE, PermissionAccessEnum.ALL, "Allow all app store actions"),
    /**
     *
     */
    READ_APPSTORE(PermissionGroupEnum.APPSTORE, PermissionAccessEnum.READ, "Read app store"),
    /**
     *
     */
    CREATE_APPSTORE(PermissionGroupEnum.APPSTORE, PermissionAccessEnum.CREATE, "Create app store"),
    /**
     *
     */
    UPDATE_APPSTORE(PermissionGroupEnum.APPSTORE, PermissionAccessEnum.UPDATE, "Update app store"),
    /**
     *
     */
    DELETE_APPSTORE(PermissionGroupEnum.APPSTORE, PermissionAccessEnum.DELETE, "Delete app store"),
    /* ********************************************************************** */
    /* SYSTEM [1100 - 1199]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_SYSTEM(PermissionGroupEnum.SYSTEM, PermissionAccessEnum.ALL, "Allow all system actions"),
    /**
     *
     */
    READ_SYSTEM(PermissionGroupEnum.SYSTEM, PermissionAccessEnum.READ, "Read system"),
    /**
     *
     */
    CREATE_SYSTEM(PermissionGroupEnum.SYSTEM, PermissionAccessEnum.CREATE, "Create system"),
    /**
     *
     */
    UPDATE_SYSTEM(PermissionGroupEnum.SYSTEM, PermissionAccessEnum.UPDATE, "Update system"),
    /**
     *
     */
    DELETE_SYSTEM(PermissionGroupEnum.SYSTEM, PermissionAccessEnum.DELETE, "Delete system"),
    /* ********************************************************************** */
    /* ODOMETER [1200 - 1299]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_ODOMETER(PermissionGroupEnum.ODOMETER, PermissionAccessEnum.ALL, "Allow all odometer actions"),
    /**
     *
     */
    READ_ODOMETER(PermissionGroupEnum.ODOMETER, PermissionAccessEnum.READ, "Read odometer"),
    /**
     *
     */
    CREATE_ODOMETER(PermissionGroupEnum.ODOMETER, PermissionAccessEnum.CREATE, "Create odometer"),
    /**
     *
     */
    UPDATE_ODOMETER(PermissionGroupEnum.ODOMETER, PermissionAccessEnum.UPDATE, "Update odometer"),
    /**
     *
     */
    DELETE_ODOMETER(PermissionGroupEnum.ODOMETER, PermissionAccessEnum.DELETE, "Delete odometer"),
    /* ********************************************************************** */
    /* ASSET [1300 - 1399]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_ASSET(PermissionGroupEnum.ASSET, PermissionAccessEnum.ALL, "Allow all asset actions"),
    /**
     *
     */
    READ_ASSET(PermissionGroupEnum.ASSET, PermissionAccessEnum.READ, "Read asset"),
    /**
     *
     */
    CREATE_ASSET(PermissionGroupEnum.ASSET, PermissionAccessEnum.CREATE, "Create asset"),
    /**
     *
     */
    UPDATE_ASSET(PermissionGroupEnum.ASSET, PermissionAccessEnum.UPDATE, "Update asset"),
    /**
     *
     */
    DELETE_ASSET(PermissionGroupEnum.ASSET, PermissionAccessEnum.DELETE, "Delete asset"),
    /* ********************************************************************** */
    /* HEARTBEAT [1400 - 1499]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_HEARTBEAT(PermissionGroupEnum.HEARTBEAT, PermissionAccessEnum.ALL, "Allow all hearbeat actions"),
    /**
     *
     */
    READ_HEARTBEAT(PermissionGroupEnum.HEARTBEAT, PermissionAccessEnum.READ, "Read hearbeat"),
    /**
     *
     */
    CREATE_HEARTBEAT(PermissionGroupEnum.HEARTBEAT, PermissionAccessEnum.CREATE, "Create hearbeat"),
    /**
     *
     */
    UPDATE_HEARTBEAT(PermissionGroupEnum.HEARTBEAT, PermissionAccessEnum.UPDATE, "Update hearbeat"),
    /**
     *
     */
    DELETE_HEARTBEAT(PermissionGroupEnum.HEARTBEAT, PermissionAccessEnum.DELETE, "Delete hearbeat"),
    /* ********************************************************************** */
    /* PERSONALIZATION [1400 - 1499]                                               */
    /* ********************************************************************** */
    /**
     *
     */
    ALL_PERSONALIZATION(PermissionGroupEnum.PERSONALIZATION, PermissionAccessEnum.ALL, "Allow all personalization actions"),
    /**
     *
     */
    READ_PERSONALIZATION(PermissionGroupEnum.PERSONALIZATION, PermissionAccessEnum.READ, "Read personalization"),
    /**
     *
     */
    CREATE_PERSONALIZATION(PermissionGroupEnum.PERSONALIZATION, PermissionAccessEnum.CREATE, "Create personalization"),
    /**
     *
     */
    UPDATE_PERSONALIZATION(PermissionGroupEnum.PERSONALIZATION, PermissionAccessEnum.UPDATE, "Update personalization"),
    /**
     *
     */
    DELETE_PERSONALIZATION(PermissionGroupEnum.PERSONALIZATION, PermissionAccessEnum.DELETE, "Delete personalization");

    private final int id;
    private final PermissionGroupEnum group;
    private final PermissionAccessEnum access;
    private final String description;

    PermissionEnum(int id, String description) {
        this.group = PermissionGroupEnum.NONE;
        this.access = PermissionAccessEnum.ALL;
        this.id = id;
        this.description = description;
    }

    PermissionEnum(PermissionGroupEnum group, PermissionAccessEnum access, String description) {
        this.group = group;
        this.access = access;
        this.id = group.getId() + access.getId();
        this.description = description; 
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return
     */
    public PermissionGroupEnum getGroup() {
        return group;
    }

    /**
     * @return
     */
    public PermissionAccessEnum getAccess() {
        return access;
    }
}
