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
package com.luretechnologies.server.security.hsm.message.parameter;

/**
 *
 *
 * @author developer
 */
public class Command extends HSMParameter {

    /**
     *
     */
    public Command() {
        parameterDescription = "Command";
    }

    // Diagnostic commands
    /**
     *
     */
    public void performDiagnostics() {
        parameterValue = "NC";
        valueDescription = "Perform Diagnostics";
    }

    /**
     *
     */
    public void returnNetworkInformation() {
        parameterValue = "NI";
        valueDescription = "Return Network Information";
    }

    // RSA Key commands
    /**
     *
     */
    public void generateRSAKeyPair() {
        parameterValue = "EI";
        valueDescription = "Generate an RSA Key Pair";
    }

    /**
     *
     */
    public void importPublicKey() {
        parameterValue = "EO";
        valueDescription = "Import a Public Key";
    }

    /**
     *
     */
    public void checkRSAPublic() {
        parameterValue = "EQ";
        valueDescription = "Check an RSA Public Key Agianst the HSM";
    }

    // RSA Crypto commands
    /**
     *
     */
    public void generateRSASignature() {
        parameterValue = "EW";
        valueDescription = "Generate an RSA Signature";
    }

    /**
     *
     */
    public void validateRSASignature() {
        parameterValue = "EY";
        valueDescription = "Validate an RSA Signature";
    }

    /**
     *
     */
    public void exportUnderRSAPublic() {
        parameterValue = "GK";
        valueDescription = "Export a Key Encrypted Under an RSA Public Key";
    }

    /**
     *
     */
    public void decryptRSA() {
        parameterValue = "GI";
        valueDescription = "Decrypt Data Using RSA Private Key";
    }

    // Symmetric Key commands
    /**
     *
     */
    public void generateSymmetricKey() {
        parameterValue = "A0";
        valueDescription = "Generate a Symmetric Key";
    }

    /**
     *
     */
    public void decryptSymmetric() {
        parameterValue = "M2";
        valueDescription = "Decrypt a Message With a Symetric Key";
    }

    /**
     *
     */
    public void encryptSymmetric() {
        parameterValue = "M0";
        valueDescription = "Encrypt a Message With a Symetric Key";
    }

    /**
     *
     */
    public void VerifyMAC() {
        parameterValue = "M8";
        valueDescription = "Verify a MAC";
    }

    /**
     *
     */
    public void GenerateMAC() {
        parameterValue = "M6";
        valueDescription = "Generate a MAC";
    }

    /**
     *
     */
    public void VerifyMAC_DUKPT() {
        parameterValue = "GW";
        valueDescription = "Verify a MAC DUKPT";
    }

    /**
     *
     */
    public void GenerateMAC_DUKPT() {
        parameterValue = "GW";
        valueDescription = "Generate a MAC DUKPT";
    }

    /**
     *
     */
    public void transalateDUKPTPinToDUKPT() {
        parameterValue = "G0";
        valueDescription = "transalate DUKPT PIN to DUKPT";
    }
}
