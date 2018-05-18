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

import com.luretechnologies.server.security.hsm.message.parameter.DESKeyFlag;
import com.luretechnologies.server.security.hsm.message.parameter.DataLength;
import com.luretechnologies.server.security.hsm.message.parameter.HSMParameter;
import com.luretechnologies.server.security.hsm.message.parameter.HashIdentifier;
import com.luretechnologies.server.security.hsm.message.parameter.MiscParameter;
import com.luretechnologies.server.security.hsm.message.parameter.VariantLMKKeyType;

/**
 *
 *
 * @author developer
 */
public class HSMExportKeyUnderRSAPublicKey extends HSMMessageBuilder {

    /**
     *
     */
    public HashIdentifier hashIdentifier;
    public HSMParameter privateKey,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            keyToExport,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            keyCheckValue,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            publicKey;

    /**
     *
     */
    protected DataLength privateKeyLength;

    /**
     *
     */
    public DESKeyFlag desKeyFlag;

    /**
     *
     */
    public VariantLMKKeyType keyType;

    /**
     *
     */
    public HSMExportKeyUnderRSAPublicKey() {
        command.exportUnderRSAPublic();

        hashIdentifier = new HashIdentifier();
        privateKey = new HSMParameter("Private Key");
        keyType = new VariantLMKKeyType();
        privateKeyLength = new DataLength(privateKey);
        keyToExport = new HSMParameter("Key To Export");
        keyCheckValue = new HSMParameter("Key Check Value");
        desKeyFlag = new DESKeyFlag();
        publicKey = new HSMParameter("Public Key");

        addParameter(new MiscParameter(RSA_ALGORITHM_IDENTIFIER, "Encryption Identifier (RSA)"));
        addParameter(new MiscParameter(PKCS1_PAD_MODE_IDENTIFIER, "Pad Mode Identifier (PKCS#1 v1.5)"));
        addParameter(keyType);
        addParameter(new MiscParameter("=", "Generate Hash of Key"));
        addParameter(hashIdentifier);
        addParameter(new MiscParameter(RSA_ALGORITHM_IDENTIFIER, "Signature Identifier (RSA)"));
        addParameter(new MiscParameter(PKCS1_PAD_MODE_IDENTIFIER, "Pad Mode Identifier (PKCS#1 v1.5)"));
        addParameter(new MiscParameter("0000", "Header Size"));
        addParameter(new MiscParameter(";", "Delimiter"));
        addParameter(new MiscParameter("0000", "Footer Size"));
        addParameter(new MiscParameter(";", "Delimiter"));
        addParameter(new MiscParameter("99", "Private Key Provided"));
        addParameter(privateKeyLength);
        addParameter(privateKey);
        addParameter(new MiscParameter(";", "Delimiter"));
        addParameter(desKeyFlag);
        addParameter(keyToExport);
        addParameter(keyCheckValue);
        addParameter(publicKey);

        privateKeyLength.decimal();
    }

    /**
     *
     */
    @Override
    public void defaultSettings() {
        hashIdentifier.md5();
        keyType.bidirectionalBDK();
        desKeyFlag.doubleLengthDES();
    }

}
