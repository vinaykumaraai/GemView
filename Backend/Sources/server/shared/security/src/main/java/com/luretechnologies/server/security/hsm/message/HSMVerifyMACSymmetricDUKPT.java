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
import com.luretechnologies.server.security.hsm.message.parameter.MiscParameter;

/**
 *
 *
 * @author developer
 */
public class HSMVerifyMACSymmetricDUKPT extends HSMMessageBuilder {

    /**
     *
     */
    public MiscParameter modeFlag;

    /**
     *
     */
    public MiscParameter methodFlag;

    /**
     *
     */
    public MiscParameter bdk;

    /**
     *
     */
    public MiscParameter ksn;

    /**
     *
     */
    public MiscParameter ksnDescriptor;

    /**
     *
     */
    public MiscParameter mac;

    /**
     *
     */
    public DataLength dataBlockLength;

    /**
     *
     */
    public HSMParameter dataBlock;

    /**
     *
     */
    public HSMVerifyMACSymmetricDUKPT() {
        command.VerifyMAC_DUKPT();

        modeFlag = new MiscParameter("MAC Mode");
        methodFlag = new MiscParameter("MAC Method");

        bdk = new MiscParameter("BDK");
        ksnDescriptor = new MiscParameter("KSN Descriptor");
        ksn = new MiscParameter("Key Serial Number");

        mac = new MiscParameter("Message Authentication Code");

        dataBlock = new HSMParameter("Message Data");
        dataBlockLength = new DataLength(dataBlock);

        addParameter(modeFlag);
        addParameter(methodFlag);
        addParameter(bdk);
        addParameter(ksnDescriptor);
        addParameter(ksn);
        addParameter(mac);
        addParameter(dataBlockLength);
        addParameter(dataBlock);

        dataBlockLength.bcd();
    }

    /**
     *
     */
    @Override
    public void defaultSettings() {
        modeFlag.setValue("2");
        methodFlag.setValue("1");
        ksnDescriptor.setValue("906");
    }
}
