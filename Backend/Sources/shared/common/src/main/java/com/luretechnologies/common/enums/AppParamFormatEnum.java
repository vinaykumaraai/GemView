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

public enum AppParamFormatEnum {

    /**
     * ALPHANUMERIC
     */
    ALPHANUMERIC(1, "Alphanumeric", "An alphanumeric string value."),
    /**
     * ALPHA
     */
    ALPHA(2, "Alpha", "An alphabetic string value."),
    /**
     * SPECIAL
     */
    SPECIAL(3, "Special", "An alphanumeric with printable specials string value."),
    /**
     * INTEGER
     */
    INTEGER(4, "Integer", "An integer value."),
    /**
     * DECIMAL
     */
    DECIMAL(5, "Decimal", "A decimal value."),
    /**
     * BOOLEAN
     */
    BOOLEAN(6, "Boolean", "A boolean value."),
    /**
     * JSON
     */
    JSON(7, "JSON", "A JSON value."),
    /**
     * XML
     */
    XML(8, "XML", "An XML value."),
    /**
     * HEX
     */
    HEX(9, "Hexadecimal", "A hexadecimal value."),
    /**
     * BINARY
     */
    BINARY(10, "Binary", "A binary value."),
    /**
     * TELEPHONE
     */
    TELEPHONE(11, "Telephone", "A telephone number value."),
    /**
     * EMAIL
     */
    EMAIL(12, "Email", "An email address value."),
    /**
     * US ZIP CODE
     */
    ZIPCODE(13, "Zip Code", "A US Zip Code value."),
    /**
     * CANADA POSTAL CODE
     */
    POSTALCODE(14, "Postal Code", "A Canadian Postal Code value."),
    /**
     * OTHER
     */
    OTHER(99, "Other", "An other value.");

    private final Integer id;
    private final String name;
    private final String description;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, AppParamFormatEnum> map = new HashMap<>();

    AppParamFormatEnum(Integer id, String name, String description) {
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
        for (AppParamFormatEnum formatEnum : AppParamFormatEnum.values()) {
            if (formatEnum.getName().toUpperCase().contains(name.toUpperCase())) {
                return formatEnum.getId();
            }
        }
        return 0;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (AppParamFormatEnum formatEnum : AppParamFormatEnum.values()) {
            map.put(formatEnum.getId(), formatEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final AppParamFormatEnum getFormatEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }
}
