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
package com.luretechnologies.server.security.hsm.message;

import com.luretechnologies.server.security.hsm.message.parameter.DUKPTMasterKeyType;
import com.luretechnologies.server.security.hsm.message.parameter.KeyExportability;
import com.luretechnologies.server.security.hsm.message.parameter.KeyModeOfUse;
import com.luretechnologies.server.security.hsm.message.parameter.KeyUsage;
import com.luretechnologies.server.security.hsm.message.parameter.LMKKeyScheme;
import com.luretechnologies.server.security.hsm.message.parameter.MiscParameter;
import com.luretechnologies.server.security.hsm.message.parameter.VariantKeyType;

/**
 *
 *
 * @author developer
 */
public class HSMDerriveSymmetricKey extends HSMMessageBuilder {

    private final String DERRIVE_KEY_MODE = "A";

    /**
     *
     */
    protected VariantKeyType keyType;

    /**
     *
     */
    public LMKKeyScheme lmkKeyScheme;

    /**
     *
     */
    public DUKPTMasterKeyType masterKeyType;
    public MiscParameter masterKey,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            serialNumber;

    /**
     *
     */
    public KeyExportability keyExportability;

    /**
     *
     */
    public KeyUsage keyUsage;

    /**
     *
     */
    public KeyModeOfUse keyModeOfUse;

    /**
     *
     */
    public HSMDerriveSymmetricKey() {
        command.generateSymmetricKey();

        keyType = new VariantKeyType();
        lmkKeyScheme = new LMKKeyScheme();
        masterKeyType = new DUKPTMasterKeyType();
        masterKey = new MiscParameter("BDK Master Key");
        serialNumber = new MiscParameter("Terminal Serial Number");
        keyExportability = new KeyExportability();
        keyUsage = new KeyUsage();
        keyModeOfUse = new KeyModeOfUse();

        addParameter(new MiscParameter(DERRIVE_KEY_MODE, "Mode (Derrive Key)"));
        addParameter(keyType);
        addParameter(lmkKeyScheme);
        addParameter(new MiscParameter("0", "Derrive Key Mode (Derive IPEK)"));
        addParameter(masterKeyType);
        addParameter(masterKey);
        addParameter(serialNumber); // Pad this with 'F' (right justified)
        keyType.IPEK();
    }

    /**
     *
     */
    @Override
    public void defaultSettings() {
        lmkKeyScheme.thalesVariantDoubleLengthLMKScheme();
        masterKeyType.bidirectionalBDK();
        keyUsage.bidirectionalBDK();
        keyExportability.exportIfEnabled();
        keyModeOfUse.noRestrictions();
    }
}
