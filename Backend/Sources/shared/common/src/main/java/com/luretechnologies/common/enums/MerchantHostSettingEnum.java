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
public enum MerchantHostSettingEnum {

    /**
     *
     */
    HOST_MERCHANT_NUMBER(1, "hostMerchantNumber", "Merchant Number by Host", FormatEnum.ALPHA, 1),
    /**
     *
     */
    HOST_GROUP_ID(2, "hostGroupId", "Group Id", FormatEnum.NUMERIC, 1),
    /**
     *
     */
    HOST_PROJECT_ID(3, "hostProjectId", "Project Id", FormatEnum.ALPHA_NUMERIC, 1),
    /**
     *
     */
    HOST_DATAWIRE_ID(4, "hostDatawireId", "Datawire Id", FormatEnum.NUMERIC, 1),
    /**
     *
     */
    HOST_CLIENT_NUMBER(5, "hostClientNumber", "Host Client Number", FormatEnum.ALPHA_NUMERIC, 1),
    /**
     *
     */
    HOST_MERCHANT_URL(6, "hostMerchantUrl", "Host Merchant Url", FormatEnum.ALPHA_NUMERIC, 1),
    
    HOST_TSYS_TRANS_KEY(7, "transactionKey", "The TSYS transaction key", FormatEnum.ALPHA_NUMERIC, 2),
    
    HOST_TSYS_DEVICE_ID(8, "deviceID", "The TSYS device id", FormatEnum.NUMERIC, 2),
    
    HOST_TSYS_MID(9, "MID", "The TSYS merchant id", FormatEnum.NUMERIC, 2),
    
    HOST_VCA_PASSWORD(101, "hostPassword", "The VCA host password", FormatEnum.ALPHA_NUMERIC, 3),
    
    HOST_VCA_THIRD_PARTY_CERTIFICATION_NUMBER(102, "thirdPartyCertificationNumber", "The VCA third Party Certification Number", FormatEnum.ALPHA_NUMERIC, 3),
    
    HOST_VCA_INDUSTRY_TYPE(103, "industryType", "industryType", FormatEnum.ALPHA_NUMERIC, 3);
    

    private final Integer id;
    private final String name;
    private final String description;
    private final FormatEnum dataType;
    private final int group;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, MerchantHostSettingEnum> map = new HashMap<>();

    MerchantHostSettingEnum(Integer id, String name, String description, FormatEnum dataType, int group) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dataType = dataType;
        this.group = group;
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

    /**
     *
     * @return
     */
    public int getGroup() {
        return group;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (MerchantHostSettingEnum merchantHostSettingEnum : MerchantHostSettingEnum.values()) {
            map.put(merchantHostSettingEnum.getId(), merchantHostSettingEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final MerchantHostSettingEnum getMerchantHostSettingEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } 
        return null;
    }

}
