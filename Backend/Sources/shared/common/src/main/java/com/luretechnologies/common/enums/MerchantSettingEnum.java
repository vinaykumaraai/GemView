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
public enum MerchantSettingEnum {

    /**
     *
     */
    GRATUITY_RATE_1(1, "gratuityRate1", "Gratuity Rate 1", FormatEnum.DECIMAL),
    /**
     *
     */
    GRATUITY_RATE_2(2, "gratuityRate2", "Gratuity Rate 2", FormatEnum.DECIMAL),
    /**
     *
     */
    GRATUITY_RATE_3(3, "gratuityRate3", "Gratuity Rate 3", FormatEnum.DECIMAL),
    /**
     *
     */
    HEADER_LINE_1(4, "headerLine1", "Header Line 1", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    HEADER_LINE_2(5, "headerLine2", "Header Line 2", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    HEADER_LINE_3(6, "headerLine3", "Header Line 3", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    HEADER_LINE_4(7, "headerLine4", "Header Line 4", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    HEADER_LINE_5(8, "headerLine5", "Header Line 5", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    PROMISSORY_VERBIAGE_1(9, "promissoryVerbiage1", "Promissory Verbiage 1", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    PROMISSORY_VERBIAGE_2(10, "promissoryVerbiage2", "Promissory Verbiage 1", FormatEnum.ALPHA_NUMERIC),
    /**
     *
     */
    PROMISSORY_VERBIAGE_3(11, "promissoryVerbiage3", "Promissory Verbiage 1", FormatEnum.ALPHA_NUMERIC),
    /**
     * Public Cipher Key
     *
     */
    PUBLIC_CIPHER_KEY(12, "publicCipherKey", "Public Cipher Key", FormatEnum.ALPHA_NUMERIC),
    /**
     * Private Cipher Key
     *
     */
    PRIVATE_CIPHER_KEY(13, "privateCipherKey", "Private Cipher Key", FormatEnum.ALPHA_NUMERIC),
    /**
     * Id Cipher Key
     *
     */
    ID_CIPHER_KEY(14, "idCipherKey", "Id Cipher Key", FormatEnum.NUMERIC),
    /**
     * Public Signing Key
     *
     */
    PUBLIC_SIGNING_KEY(15, "publicSigningKey", "Public Signing Key", FormatEnum.ALPHA_NUMERIC),
    /**
     * Private Signing Key
     *
     */
    PRIVATE_SIGNING_KEY(16, "privateSigningKey", "Private Signing Key", FormatEnum.ALPHA_NUMERIC),
    /**
     * Id Signing Key
     *
     */
    ID_SIGNING_KEY(17, "idSigningKey", "Id Signing Key", FormatEnum.NUMERIC);

    private final Integer id;
    private final String name;
    private final String description;
    private final FormatEnum dataType;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, MerchantSettingEnum> map = new HashMap<>();

    MerchantSettingEnum(Integer id, String name, String description, FormatEnum dataType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dataType = dataType;

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
     * @return
     */
    public FormatEnum getDataType() {
        return dataType;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (MerchantSettingEnum merchantSettingEnum : MerchantSettingEnum.values()) {
            map.put(merchantSettingEnum.getId(), merchantSettingEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final MerchantSettingEnum getMerchantSettingEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
