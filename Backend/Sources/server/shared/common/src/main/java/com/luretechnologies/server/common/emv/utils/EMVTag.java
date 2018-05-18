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
package com.luretechnologies.server.common.emv.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 *
 * @author developer
 */
public class EMVTag {

    /**
     * Static EMVTag values
     */
    public static final String TEMPLATE0 = "E0";

    /**
     *
     */
    public static final String TEMPLATE1 = "C1";

    /**
     *
     */
    public static final String TEMPLATE2 = "C2";

    /**
     *
     */
    public static final String DEVICE_SERIAL_NUMBER = "DF38";

    /**
     *
     */
    public static final String MASKED_APPLICATION_PAN = "DF39";

    /**
     *
     */
    public static final String POS_ENTRY_MODE = "9F39";

    /**
     *
     */
    public static final String TRANSACTION_DATE = "9A";

    /**
     *
     */
    public static final String TRANSACTION_TIME = "9F21";

    /**
     *
     */
    public static final String TRANSACTION_CURRENCY_CODE = "5F2A";

    /**
     *
     */
    public static final String OPERATION_TYPE = "DF18";

    /**
     *
     */
    public static final String ONLINE_PIN_BLOCK = "DF16";

    /**
     *
     */
    public static final String ORIGINAL_SALE_DATE = "DF3A";

    /**
     *
     */
    public static final String ORIGINAL_SALE_TIME = "DF3B";

    /**
     *
     */
    public static final String ORIGINAL_SALE_IDENTIFIER = "DF4A";

    /**
     *
     */
    public static final String TRACK1DATA_MAG_STRIPE = "DF13";
    
    /**
     *
     */
    public static final String TRACK1DATA_MAG_ICC = "56";

    /**
     *
     */
    public static final String TRACK2DATA_MAG_STRIPE = "DF14";

    /**
     *
     */
    public static final String TRACK2DATA_MAG_ICC = "57";

    /**
     *
     */
    public static final String APP_PAN_ICC = "5A";

    /**
     *
     */
    public static final String APP_EXP_DATE = "5F24";

    /**
     *
     */
    public static final String APP_CARDHOLDER_NAME = "5F20";

    /**
     *
     */
    public static final String PAN_SEQUENCE_NUMBER_ICC = "5F34";

    /**
     *
     */
    public static final String KEY_SERIAL_NUMBER = "DF17";

    /**
     *
     */
    public static final String KEY_SERIAL_NUMBER_DEC = "DF44";

    /**
     *
     */
    public static final String APP_INTERCHANGE_PROFILE = "82";

    /**
     *
     */
    public static final String DEDICATED_FILE_NAME = "84";

    /**
     *
     */
    public static final String TERMINAL_VERIFICATION_RESULTS = "95";

    /**
     *
     */
    public static final String TRANSACTION_TYPE = "9C";

    /**
     *
     */
    public static final String AMOUNT_AUTHORISED = "9F02";

    /**
     *
     */
    public static final String APP_VERSION_NUMBER = "9F09";

    /**
     *
     */
    public static final String ISSUER_APP_DATA = "9F10";

    /**
     *
     */
    public static final String TERMINAL_COUNTRY_CODE = "9F1A";

    /**
     *
     */
    public static final String IFD_SERIAL_NUMBER = "9F1E";

    /**
     *
     */
    public static final String APP_NAME = "9F12";
    
    /**
     *
     */
    public static final String APP_CRYPTOGRAM = "9F26";

    /**
     *
     */
    public static final String CRYPTOGRAM_INFORMATION_DATA = "9F27";

    /**
     *
     */
    public static final String TERMINAL_CAPABILITIES = "9F33";

    /**
     *
     */
    public static final String CVM_RESULT = "9F34";

    /**
     *
     */
    public static final String TERMINAL_TYPE = "9F35";

    /**
     *
     */
    public static final String APP_TRANSACTION_COUNTER = "9F36";

    /**
     *
     */
    public static final String UNPREDICTABLE_NUMBER = "9F37";

    /**
     *
     */
    public static final String APP_EFFECTIVE_DATE = "5F25";

    /**
     *
     */
    public static final String APP_USAGE_CONTROL = "9F07";

    /**
     *
     */
    public static final String IAC_DEFAULT = "9F0D";

    /**
     *
     */
    public static final String IAC_DENIAL = "9F0E";

    /**
     *
     */
    public static final String IAC_ONLINE = "9F0F";

    /**
     *
     */
    public static final String ICC_APP_VERSION_NUMBER = "9F08";

    /**
     *
     */
    public static final String AID = "4F";

    /**
     *
     */
    public static final String TRANSACTION_CATEGORY_CODE = "9F53";

    /**
     *
     */
    public static final String TRANSACTION_SEQUENCE_COUNTER = "9F41";

    /**
     *
     */
    public static final String COMPLETION_RESULT = "DF1F";

    /**
     *
     */
    public static final String ISSUER_SCRIPT_RESULTS = "9F5B";

    /**
     *
     */
    public static final String ISSUER_SCRIPT_T1 = "71";

    /**
     *
     */
    public static final String ISSUER_SCRIPT_T2 = "72";

    /**
     *
     */
    public static final String ISSUER_AUTHENTICATION_DATA = "91";

    /**
     *
     */
    public static final String AUTHORIZATION_CODE = "89";

    /**
     *
     */
    public static final String AUTHORIZATION_RESPONSE_CODE = "8A";
    
    /**
     *
     */
    public static final String TRANSACTION_PIN = "99";
     /**
     *
     */
    public static final String APP_CARDHOLDER_NAME_EXT = "9F0B";
    
    /**
     *
     */
    public static final String TRACK1_DISCRETIONARY_DATA = "9F1F";
    
    /**
     *
     */
    public static final String TRACK2_DISCRETIONARY_DATA = "9F20";
    /**
     *
     */
    public static final String MAC = "DF45";

    // TMS Code Start
    /**
     *
     */
    public static final String E1 = "E1";

    /**
     *
     */
    public static final String E2 = "E2";

    /**
     *
     */
    public static final String TABLE_POS = "DF3E";

    /**
     *
     */
    public static final String TABLE_ID = "DF21";

    /**
     *
     */
    public static final String TABLE_VER = "DF22";
    
    /**
     *
     */
    public static final String  KERNAL_VERSION_NUMBER = "DF79";

    /**
     *
     */
    public static final String  FORM_FACTOR_INDICATOR = "9F6E";
    // TMS Code End
    /**
     *
     */
    public final byte[] bytes;

    /**
     *
     * @param bytes
     */
    public EMVTag(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     *
     * @param value
     * @throws DecoderException
     */
    public EMVTag(String value) throws DecoderException {
        this(Hex.decodeHex(value.toCharArray()));
    }

    /**
     *
     * @return
     */
    public boolean isConstructed() {
        return (bytes[0] & 0x20) != 0;
    }

    /**
     *
     * @return
     */
    public String getHexName() {
        return Hex.encodeHexString(bytes);
    }

    @Override
    public String toString() {
        return getHexName();
    }

}
