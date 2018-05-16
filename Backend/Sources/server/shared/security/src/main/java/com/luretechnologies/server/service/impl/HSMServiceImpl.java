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

import com.luretechnologies.common.Constants;
import com.luretechnologies.common.enums.SettingGroupEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.dao.SettingDAO;
import com.luretechnologies.server.data.model.payment.Setting;
import com.luretechnologies.server.security.hsm.HSMCaller;
import com.luretechnologies.server.security.hsm.HSMUnit;
import com.luretechnologies.server.security.util.Utils;
import com.luretechnologies.server.service.HSMService;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author developer
 */
@Service
public class HSMServiceImpl implements HSMService {

    private static HSMCaller hsmCaller;

    private static String HSM_ADDRESS;
    private static String HSM_PORT;
    private static String HSM_BDK;

    @Autowired
    SettingDAO settingDAO;

    @Override
    public void init() throws Exception {
        // Load HSM settings from DB
        List<Setting> hsmSettings = settingDAO.list(SettingGroupEnum.HSM, 0, 50);
        for (Setting hsmSetting : hsmSettings) {
            switch (hsmSetting.getName()) {
                case HSM_ADDRESS: {
                    HSM_ADDRESS = hsmSetting.getValue();

                    if (HSM_ADDRESS == null || HSM_ADDRESS.isEmpty()) {
                        throw new CustomException(Constants.CODE_INVALID_ENTRY_DATA, Messages.INVALID_DATA_ENTRY);
                    }
                    break;
                }
                case HSM_PORT: {
                    HSM_PORT = hsmSetting.getValue();

                    if (HSM_PORT == null || HSM_PORT.isEmpty()) {
                        throw new CustomException(Constants.CODE_INVALID_ENTRY_DATA, Messages.INVALID_DATA_ENTRY);
                    }
                    break;
                }
                case HSM_BDK: {
                    HSM_BDK = hsmSetting.getValue();

                    if (HSM_BDK == null || HSM_BDK.isEmpty()) {
                        throw new CustomException(Constants.CODE_INVALID_ENTRY_DATA, Messages.INVALID_DATA_ENTRY);
                    }
                    break;
                }
            }
        }

        List<HSMUnit> hsmUnits = new LinkedList<>();
        hsmUnits.add(new HSMUnit("HSM1", HSM_ADDRESS, Integer.valueOf(HSM_PORT)));

        hsmCaller = new HSMCaller(hsmUnits);
    }

    @Override
    public byte[] decrypt(String encryptedData, String ksn, String iv) throws Exception {
        if (!validConfig()) {
            throw new CustomException(10000, "INVALID HSM CONFIG");
        }

        byte[] sd1 = null;
        String tempCipher = encryptedData;

        if (encryptedData != null) {
            if (iv == null) {
                iv = encryptedData.substring(encryptedData.length() - 16);
                tempCipher = encryptedData.substring(0, encryptedData.length() - 16);
            }

            sd1 = hsmCaller.decryptSymmetricDUKPT(HSM_BDK, Utils.toByteArray(tempCipher), ksn, iv);

            hsmCaller.endHSMTransmission();
        }

        return sd1;
    }

    @Override
    public String decrypt(String encryptedData, String ksn) throws Exception {
        return Hex.encodeHexString(this.decrypt(encryptedData, ksn, null)).toUpperCase();
    }

    private boolean validConfig() throws Exception {
        return HSM_BDK != null && hsmCaller != null;
    }

}
