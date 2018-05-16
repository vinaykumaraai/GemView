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
package com.luretechnologies.server.common.emv.utils;

import com.luretechnologies.server.common.utils.Utils;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author developer
 */
public class TLVHandler {

    private final Map<String, TLV> tlvs;
    private final String valueRaw;

    /**
     *
     * @param tlv
     */
    public TLVHandler(TLV tlv) {
        this.tlvs = new HashMap<>();
        this.tlvs.put(tlv.getTag().getHexName(), tlv);

        this.valueRaw = tlv.getHexTLV();
    }

    /**
     *
     * @param tlvs
     * @param valueRaw
     */
    public TLVHandler(Map<String, TLV> tlvs, String valueRaw) {
        this.tlvs = tlvs;
        this.valueRaw = valueRaw;
    }

    /**
     *
     * @param tag
     * @return
     */
    public String get(String tag) {
        TLV tlv = tlvs.get(tag);
        return tlv == null ? null : tlv.getHexValue();
    }

    /**
     *
     * @param tag
     * @return
     */
    public String getLength(String tag) {
        TLV tlv = tlvs.get(tag);
        return tlv == null ? null : tlv.getHexLength();
    }

    /**
     *
     * @param tag
     * @return
     */
    public TLV getTLV(String tag) {
        return tlvs.get(tag);
    }

    /**
     *
     * @param tag
     * @return
     */
    public String getHexTLV(String tag) {
        TLV tlv = tlvs.get(tag);
        return tlv == null ? "" : tlv.getHexTLV();
    }

    /**
     *
     * @param tag
     * @return
     */
    public String getStringTLV(String tag) {
        TLV tlv = tlvs.get(tag);
        return tlv == null ? "" : Utils.hexToString(tlv.getHexValue());
    }

    /**
     *
     * @return
     */
    public String getTrack2FromTag57() {

        TLV tlv = tlvs.get(EMVTag.TRACK2DATA_MAG_ICC);
        if (tlv == null) {
            return "";
        }

        String tag57 = tlv.getHexTLV();

        String tk2 = tag57.substring(4);
        tk2 = tk2.replace("D", "=");
        tk2 = tk2.replace("F", "");

        return tk2;
    }

    /**
     *
     * @return
     */
    public Map<String, TLV> getTlvs() {
        return tlvs;
    }

    /**
     *
     * @return
     */
    public String getValueRaw() {
        return valueRaw;
    }

    @Override
    public String toString() {
        return tlvs.toString();
    }
}
