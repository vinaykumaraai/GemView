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
public enum EntryMethodEnum {

    /**
     * Manual
     *
     */
    MANUAL(1, "Manual", "Manual"),
    /**
     * Swipe
     *
     */
    SWIPE(2, "Swipe", "Swipe"),
    /**
     * EMV Contact
     *
     */
    EMV_CONTACT(3, "EMV Contact", "Emv Contact"),
    /**
     * EMV Contact less
     *
     */
    EMV_CONTACTLESS(4, "Emv Contactless", "Emv Contactless"),
    /**
     * EMV Technical Fallback
     *
     */
    EMV_TECHNICAL_FB(5, "Emv Technical Fallback", "Emv Technical Fallback"),
    /**
     * EMV XXXX Fallback
     *
     */
    EMV_XXXX_FB(6, "EMV_XXXX_FB", "TBD"),
    /**
     * RFID
     *
     */
    RFID(7, "RFID", "Radio-Frequency IDentification"),
    /**
     * NFC
     *
     */
    NFC(8, "NFC", "Near Field Communication"),
    /**
     * CONTACTLESS
     *
     */
    CONTACTLESS(9, "Contactless", "Contactless"),
    /**
     * fallback swipe
     *
     */
    FALLBACK_SWIPE(10, "fallback swipe", "fallback swipe"),
    
    /**
     * NONE
     *
     */
    NONE(999, "None", "None");
    
    private final Integer id;
    private final String name;
    private final String description;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, EntryMethodEnum> map = new HashMap<>();

    EntryMethodEnum(Integer id, String name, String description) {
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

        if ("MANUAL".contains(name)) {
            return 0;
        }

        if ("ICC".contains(name)) {
            return 1;
        }

        if ("MSR".contains(name)) {
            return 2;
        }
        
        if ("NONE".contains(name)) {
            return 999;
        }
        return 0;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (EntryMethodEnum entryMethodEnum : EntryMethodEnum.values()) {
            map.put(entryMethodEnum.getId(), entryMethodEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final EntryMethodEnum getEntryMethodEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
