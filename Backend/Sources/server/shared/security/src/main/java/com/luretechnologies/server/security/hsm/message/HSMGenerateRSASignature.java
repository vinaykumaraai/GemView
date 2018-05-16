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

import com.luretechnologies.server.security.hsm.message.parameter.DataLength;
import com.luretechnologies.server.security.hsm.message.parameter.HSMParameter;
import com.luretechnologies.server.security.hsm.message.parameter.HashIdentifier;
import com.luretechnologies.server.security.hsm.message.parameter.MiscParameter;

/**
 *
 *
 * @author developer
 */
public class HSMGenerateRSASignature extends HSMMessageBuilder {

    /**
     *
     */
    public HashIdentifier hashIdentifier;
    public HSMParameter dataToSign,
            /**
             *
             */
            privateKey;
    protected DataLength dataToSignLength,
            /**
             *
             */
            /**
             *
             */
            /**
             *
             */
            privateKeyLength;

    /**
     *
     */
    public HSMGenerateRSASignature() {
        command.generateRSASignature();

        hashIdentifier = new HashIdentifier();
        dataToSign = new HSMParameter("Data to Sign");
        privateKey = new HSMParameter("HSM Private Key");
        dataToSignLength = new DataLength(dataToSign);
        privateKeyLength = new DataLength(privateKey);

        addParameter(hashIdentifier);
        addParameter(new MiscParameter(RSA_ALGORITHM_IDENTIFIER, "Signature Identifier (RSA)"));
        addParameter(new MiscParameter(PKCS1_PAD_MODE_IDENTIFIER, "Pad Mode Identifier (PKCS#1 v1.5)"));
        addParameter(dataToSignLength);
        addParameter(dataToSign);
        addParameter(new MiscParameter(";", "Delimiter"));
        addParameter(new MiscParameter("99", "Private Key Provided"));
        addParameter(privateKeyLength);
        addParameter(privateKey);

        dataToSignLength.decimal();
        privateKeyLength.decimal();
    }

    /**
     *
     */
    @Override
    public void defaultSettings() {
        hashIdentifier.md5();
    }
}
