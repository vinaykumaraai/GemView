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

import java.util.HashMap;

/**
 *
 *
 * @author developer
 */
public class TLVParser {

    /**
     *
     * @param tlvData
     * @return
     * @throws Exception
     */
    public static TLVHandler parse(String tlvData) throws Exception {
        if (tlvData == null || tlvData.length() % 2 != 0) {
            throw new RuntimeException("Invalid tlv, null or odd length");
        }
        HashMap<String, TLV> hashMap = new HashMap<>();
        for (int i = 0; i < tlvData.length();) {
            try {
                String key = tlvData.substring(i, i = i + 2);

                if ((Integer.parseInt(key, 16) & 0x1F) == 0x1F) {
                    // extra byte for TAG field
                    key += tlvData.substring(i, i = i + 2);
                }
                String len = tlvData.substring(i, i = i + 2);
                int length = Integer.parseInt(len, 16);

                if (length > 127) {
                    // more than 1 byte for lenth
                    int bytesLength = length - 128;
                    len = tlvData.substring(i, i = i + (bytesLength * 2));
                    length = Integer.parseInt(len, 16);
                }
                length *= 2;

                String value = tlvData.substring(i, i = i + length);

                hashMap.put(key, new TLV(key, len, value));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error parsing number", e);
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException("Error processing field", e);
            }
        }
        return new TLVHandler(hashMap, tlvData);
    }

}
