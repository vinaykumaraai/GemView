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

import com.luretechnologies.server.security.hsm.message.parameter.Command;
import com.luretechnologies.server.security.hsm.message.parameter.HSMParameter;
import com.luretechnologies.server.security.hsm.message.parameter.Header;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author developer
 */
public abstract class HSMMessageBuilder {

    /**
     *
     */
    public static final int MAX_MESSAGE_SIZE = 5000;

    /**
     *
     */
    public static final int MAX_PARAMETER_SIZE = 100;

    /**
     *
     */
    public static final int MESSAGE_SIZE_OFFSET = 2;

    /**
     *
     */
    protected static final String KEY_BLOCK_DELIMITER = "#";

    /**
     *
     */
    protected static final String VARIANT_DELIMITER = "&";

    /**
     *
     */
    protected static final String KEY_BLOCK_VERSION_NUMBER = "01";

    /**
     *
     */
    protected static final String VARIANT_VERSION_NUMBER = "01";

    /**
     *
     */
    protected static final String OPTIONAL_BLOCKS = "00";

    /**
     *
     */
    protected static final String RSA_ALGORITHM_IDENTIFIER = "01";

    /**
     *
     */
    protected static final String PKCS1_PAD_MODE_IDENTIFIER = "01";

    private int messageLength, parameterCount;
    private byte[] message;

    private static final Logger LOGGER = LoggerFactory.getLogger(HSMMessageBuilder.class);

    /**
     *
     */
    public Header header;
    Command command;
    HSMParameter[] hsmParameters;

    HSMMessageBuilder() {
        messageLength = MESSAGE_SIZE_OFFSET;
        parameterCount = 0;

        command = new Command();
        header = new Header();
        message = new byte[MAX_MESSAGE_SIZE];
        hsmParameters = new HSMParameter[MAX_PARAMETER_SIZE];

        addParameter(header);
        addParameter(command);
    }

    /**
     *
     * @param parameter
     */
    protected void addParameter(HSMParameter parameter) {
        hsmParameters[parameterCount] = parameter;
        parameterCount++;
    }

    /**
     *
     * @return
     */
    public byte[] exportMessage() {
        byte[] exportedMessage;

        writeParameters();
        prependMessageSize();

        exportedMessage = Arrays.copyOf(message, messageLength);

        messageLength = MESSAGE_SIZE_OFFSET;

        return exportedMessage;
    }

    void prependMessageSize() {
        int tailLength = messageLength - MESSAGE_SIZE_OFFSET;

        message[0] = (byte) ((short) tailLength >> 8);
        message[1] = (byte) tailLength;
    }

    private void writeParameters() {
        for (int i = 0; i < parameterCount; i++) {
            if (hsmParameters[i].isRaw) {
                appendRaw(hsmParameters[i].getRawParameter());
            } else {
                appendString(hsmParameters[i].getStringParameter());
            }
        }
    }

    private void appendString(String parameter) {
        byte[] data;

        try {
            data = parameter.getBytes("US-ASCII");
            appendRaw(data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void appendRaw(byte[] data) {
        System.arraycopy(data, 0, message, messageLength, data.length);
        messageLength += data.length;
    }

    @Override
    public String toString() {
        String stringMessage = "";

        for (int i = 0; i < parameterCount; i++) {
            stringMessage = stringMessage + hsmParameters[i].toString() + "\n";
        }

        return stringMessage;
    }

    /**
     *
     */
    public abstract void defaultSettings();
}
