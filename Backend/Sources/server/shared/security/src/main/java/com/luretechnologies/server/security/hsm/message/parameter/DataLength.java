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
public class DataLength extends HSMParameter {

    /**
     *
     */
    public static final int NUM_OF_LENGTH_DIGITS = 4;

    String base;
    HSMParameter parameter;

    /**
     *
     * @param parameter
     */
    public DataLength(HSMParameter parameter) {
        this.parameter = parameter;
    }

    /**
     *
     */
    public void decimal() {
        base = "decimal";
    }

    /**
     *
     */
    public void bcd() {
        base = "bcd";
    }

    /**
     *
     */
    public void hex() {
        base = "hex";
    }

    /**
     *
     * @return
     */
    @Override
    public String getStringParameter() {
        parameterDescription = "Length of Data (in " + base + ")";
        valueDescription = parameter.valueDescription;

        if (parameter.isRaw) {
            parameterValue = getLengthString(parameter.getRawParameter().length);
        } else {
            parameterValue = getLengthString(parameter.getStringParameter().length());
        }

        while (parameterValue.length() < NUM_OF_LENGTH_DIGITS) {
            parameterValue = "0" + parameterValue;
        }

        return parameterValue;
    }

    private String getLengthString(int length) {
        switch (base) {
            case "decimal":
                return Integer.toString(length);
            case "hex":
                return Integer.toHexString(length).toUpperCase();
            case "bcd":
                return String.format("%04d", length);
        }

        return "Error, no base set";
    }
}
