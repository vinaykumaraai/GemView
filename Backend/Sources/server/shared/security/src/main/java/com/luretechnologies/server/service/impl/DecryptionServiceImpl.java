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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.service.DecryptionService;
import com.luretechnologies.server.service.HSMService;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author developer
 */
@Service
public class DecryptionServiceImpl implements DecryptionService {

    @Autowired
    HSMService hsmService;

    @Override
    public List<String> decryptVeriFoneMSR(String data, String ksn, String iv) throws Exception {
        if (iv == null || iv.isEmpty()) {
            iv = "0000000000000000";
        }

        if (iv.length() > 16) {
            iv = iv.substring(0, 16);
        }

        // Initialize HSM Service
        hsmService.init();

        // Use HSM service for decrypting the data
        byte[] decryptedData = trim(hsmService.decrypt(data, ksn, iv));

        List<String> tracks = new LinkedList<>();
        tracks.add(getDelimitedBytes(decryptedData, (byte) 0x1D, 1));
        tracks.add(getDelimitedBytes(decryptedData, (byte) 0x1D, 0));
        tracks.add(getDelimitedBytes(decryptedData, (byte) 0x1D, 2));

        return tracks;
    }

    /**
     *
     * @param array
     * @return
     */
    private byte[] trim(byte[] array) {
        int i = array.length - 1;

        while (i >= 0 && (array[i] == 0x00 || array[i] == 0x20)) {
            --i;
        }

        return Arrays.copyOf(array, i + 1);
    }

    /**
     *
     * @param array
     * @param delim
     * @param index
     * @return
     */
    private String getDelimitedBytes(byte[] array, byte delim, int index) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        for (byte b : array) {

            if (i > index) {
                break;
            }

            if (b == delim) {
                i++;
                continue;
            }

            if (i == index) {
                sb.append(String.format("%c", b));
            }
        }

        return sb.toString();
    }

}
