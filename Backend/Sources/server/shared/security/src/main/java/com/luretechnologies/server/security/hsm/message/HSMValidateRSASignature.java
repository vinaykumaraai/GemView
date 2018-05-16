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

import com.luretechnologies.server.security.hsm.message.parameter.Algorithm;
import com.luretechnologies.server.security.hsm.message.parameter.DataLength;
import com.luretechnologies.server.security.hsm.message.parameter.HSMParameter;
import com.luretechnologies.server.security.hsm.message.parameter.HashIdentifier;
import com.luretechnologies.server.security.hsm.message.parameter.KeyModeOfUse;
import com.luretechnologies.server.security.hsm.message.parameter.KeyUsage;
import com.luretechnologies.server.security.hsm.message.parameter.MiscParameter;

/**
 *
 *
 * @author developer
 */
public class HSMValidateRSASignature extends HSMMessageBuilder {

    /**
     *
     */
    public HashIdentifier hashIdentifier;
    protected DataLength signatureLength,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            dataLength;
    public HSMParameter signature,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            dataToVerify,
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
    protected KeyUsage publicKeyUsage;

    /**
     *
     */
    protected Algorithm publicKeyAlgorithm;

    /**
     *
     */
    public KeyModeOfUse publicKeyModeOfUse;

    /**
     *
     */
    public HSMValidateRSASignature() {
        command.validateRSASignature();

        hashIdentifier = new HashIdentifier();
        signature = new HSMParameter("Signature");
        signatureLength = new DataLength(signature);
        dataToVerify = new HSMParameter("Data to Verify");
        dataLength = new DataLength(dataToVerify);
        publicKeyUsage = new KeyUsage();
        publicKeyAlgorithm = new Algorithm();
        publicKeyModeOfUse = new KeyModeOfUse();
        publicKey = new HSMParameter("Public Key");

        addParameter(hashIdentifier);
        addParameter(new MiscParameter(RSA_ALGORITHM_IDENTIFIER, "Signature Identifier (RSA)"));
        addParameter(new MiscParameter(PKCS1_PAD_MODE_IDENTIFIER, "Pad Mode Identifier (PKCS#1 v1.5)"));
        addParameter(signatureLength);
        addParameter(signature);
        addParameter(new MiscParameter(";", "Delimiter"));
        addParameter(dataLength);
        addParameter(dataToVerify);
        addParameter(new MiscParameter(";", "Delimiter"));
        addParameter(publicKey);

        signatureLength.decimal();
        dataLength.decimal();
        publicKeyUsage.RSAPublicKey();
        publicKeyAlgorithm.RSA();
    }

    /**
     *
     */
    @Override
    public void defaultSettings() {
        hashIdentifier.md5();
        publicKeyModeOfUse.noRestrictions();
    }
}
