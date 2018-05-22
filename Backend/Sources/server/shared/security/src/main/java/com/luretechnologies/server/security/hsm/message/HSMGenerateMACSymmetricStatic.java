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
import com.luretechnologies.server.security.hsm.message.parameter.IOFormatFlag;
import com.luretechnologies.server.security.hsm.message.parameter.MiscParameter;
import com.luretechnologies.server.security.hsm.message.parameter.VariantKeyType;

/**
 *
 *
 * @author developer
 */
public class HSMGenerateMACSymmetricStatic extends HSMMessageBuilder {

    /**
     *
     */
    public MiscParameter modeFlag;

    /**
     *
     */
    public IOFormatFlag inputFormatFlag;

    /**
     *
     */
    public MiscParameter macSize;

    /**
     *
     */
    public MiscParameter macAlgorithm;

    /**
     *
     */
    public MiscParameter paddingMethod;

    /**
     *
     */
    public VariantKeyType keyType;

    /**
     *
     */
    public MiscParameter key;

    /**
     *
     */
    protected DataLength dataBlockLength;

    /**
     *
     */
    public HSMParameter dataBlock;

    /**
     *
     */
    public HSMGenerateMACSymmetricStatic() {
        command.GenerateMAC();

        modeFlag = new MiscParameter("Mode Flag");
        inputFormatFlag = new IOFormatFlag();
        macSize = new MiscParameter("MAC Size");
        macAlgorithm = new MiscParameter("MAC Algorithm");
        paddingMethod = new MiscParameter("Padding Method");

        keyType = new VariantKeyType();
        key = new MiscParameter("Key");
        dataBlock = new HSMParameter("Data Block");
        dataBlockLength = new DataLength(dataBlock);

        addParameter(modeFlag);
        addParameter(inputFormatFlag);
        addParameter(macSize);
        addParameter(macAlgorithm);
        addParameter(paddingMethod);
        addParameter(keyType);
        addParameter(key);
        addParameter(dataBlockLength);
        addParameter(dataBlock);

        dataBlockLength.hex();
    }

    /**
     *
     */
    @Override
    public void defaultSettings() {
        modeFlag.setValue("0");
        inputFormatFlag.binary();
        keyType.terminalAuthenticationKey();
        macSize.setValue("0");
        macAlgorithm.setValue("3");
        paddingMethod.setValue("3");
    }
}