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
package com.luretechnologies.common.validator;

/**
 * The RegExp Class
 *
 */
public class RegExp {

    /*
     * ********************************************************************************************
     * *************************************************************************** SERVER REG EXP *
     * ********************************************************************************************
     */
    /**
     * DO NOT CHANGE - Regular Expression to validate an amount - commas and
     * decimals optional - two decimal places
     */
    public static final String EXP_REG_AMOUNT = "(?=.)^\\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\\.[0-9]{1,2})?$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a currency - three letters
     * uppercase
     */
    public static final String EXP_REG_CURRENCY = "^[A-Z]{3}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a PIN - 8 bytes as 16 hex
     * characters
     */
    public static final String EXP_REG_PIN = "^[0-9A-Fa-f]{16}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a KSN - 10 bytes as 20 hex
     * characters
     */
    public static final String EXP_REG_KSN = "^[0-9A-Fa-f]{20}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a serial numbers - 255
     * alphanumeric, including periods and dashes
     */
    public static final String EXP_REG_SERIAL_NUM = "^[0-9A-Za-z.-]{1,255}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a MAC - 2 bytes as 4 hex
     * characters
     */
    public static final String EXP_REG_MAC = "^[0-9A-Fa-f]{4}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate TLV - bytes as hex
     * characters
     */
    public static final String EXP_REG_TLV = "^[0-9A-Fa-f]{2,}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate UUID - standard format
     * with or without opening and closing braces {}
     */
    public static final String EXP_REG_UUID = "^{?[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}}?$";
    /**
     * DO NOT CHANGE - Regular Expression to validate an IV - 9 bytes as 16 hex
     * characters
     */
    public static final String EXP_REG_INIT_VECTOR = "^[0-9A-Fa-f]{16}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate an approval code - up to
     * 10 alphanumeric
     */
    public static final String EXP_REG_APPROVAL_CODE = "^[0-9A-Za-z]{1,10}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate an approval verification 
     * code - up to 6 numeric
     */
    public static final String EXP_REG_APPROVAL_VERIFICATION_CODE = "^[0-9]{1,6}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate an approval number - up to
     * 25 alphanumeric
     */
    public static final String EXP_REG_APPROVAL_NUMBER = "^[0-9A-Za-z]{1,25}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate an event data - up to 255
     * alphanumeric
     */
    public static final String EXP_REG_EVENT_DATA = "^[0-9A-Za-z-.]{1,255}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a batch number - up to 9
     * digits
     */
    public static final String EXP_REG_BATCH_NUMBER = "^[0-9]{1,9}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a batch number - up to 20
     * alphanumeric
     */
    public static final String EXP_REG_INVOICE_NUMBER = "^[0-9A-Za-z]{1,20}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate a batch number - up to 20
     * alphanumeric
     */
    public static final String EXP_REG_CARDHOLDER_NAME = "^[0-9A-Za-z .-\\/]{1,}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate social security number
     */
    public static final String EXP_REG_SSN = "^([0-9]{9})$";
    /**
     * DO NOT CHANGE - Regular Expression to validate Terminal identification -
     * TER + 10 characters
     */
    public static final String EXP_REG_TERMINAL = "^(TER)[0-9A-Za-z]{10}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate Merchant Number - MER + 10
     * characters
     */
    public static final String EXP_REG_MERCHANT = "^(MER)[0-9A-Za-z]{10}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate PAN - Primary Account
     * Number.
     */
    public static final String EXP_REG_PAN = "^(?:2[0-9]{15}|3[0-9]{12}|3[0-9]{13}|3[0-9]{14}|3[0-9]{15}|4[0-9]{15}|4[0-9]{18}|5[0-9]{15}|5[0-9]{18}|6[0-9]{15}|6[0-9]{18}|)$";
    /**
     * DO NOT CHANGE - Regular Expression to validate cvv number - 3 or 4 digits
     */
    public static final String EXP_REG_CVV = "^([0-9]{3,4})$|^$";
    /**
     * DO NOT CHANGE - Regular Expression to zip code Matches US or Canadian zip
     * codes in above formats. (e.g., "T2X 1V4" or "T2X1V4"), (e.g.,
     * "94105-0011" or "94105") f
     */
    public static final String EXP_REG_VALID_ZIP_CODE = "(^\\d{5}(-\\d{4})?$)|(^[ABCEGHJKLMNPRSTVXY]{1}\\d{1}[A-Z]{1} *\\d{1}[A-Z]{1}\\d{1}$)";

    ///
    ///
    ///
    ///
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field in hexadecimal format.
     */
    public static final String EXP_REG_VALID_HEXADECIMAL = "^[0-9A-Fa-f]+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9]
     */
    public static final String EXP_REG_VALID_ALPHANUMERIC = "^[A-Za-z0-9]+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9] [_] [-] [.]
     */
    public static final String EXP_REG_VALID_ALPHANUMERIC_PARTIAL_SPECIAL = "^[A-Za-z0-9_.-]+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9] [_] [ ] [\] [-] [']
     */
    public static final String EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME = "^[A-Za-z0-9_ \\-']+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9] [_] [ ] [-] [.] [,] [?] [!]
     * [']
     */
    public static final String EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC = "^[A-Za-z0-9_ \\-.,?!']+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed [a-z] [A-Z] [0-9] and all Special Characters.
     */
    public static final String EXP_REG_VALID_ALPHANUMERIC_SPECIAL = "^[A-Za-z0-9 !#$%&'()*+,.:;<=>?@\\[\\]\\\\\\/\\\"^_`{|}~]+$";

    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field in binary format.
     */
    public static final String EXP_REG_VALID_BINARY = "^[0-9A-F]+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate passwords - The password
     * length must be greater than or equal to 8 - The password must contain one
     * or more uppercase characters - The password must contain one or more
     * lowercase characters - The password must contain one or more numeric
     * values
     */
    public static final String EXP_REG_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any number
     * field. Match a single character. Allowed Characters: [0-9]
     */
    public static final String EXP_REG_KEY_INTEGER = "[0-9]";

    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any number
     * field. Between zero and unlimited time. Allowed Characters: [0-9]
     */
    public static final String VALID_NUMBER_I_REG_EXP = "^[0-9]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any number
     * field, between one and unlimited times. Allowed Characters: [0-9]
     */
    public static final String EXP_REG_INTEGER = "[0-9]+";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z]
     */
    public static final String EXP_REG_VALID_ALPHABETIC = "^[A-Za-z]+$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9]
     */
    public static final String VALID_TEXT_REG_EXP = "^[a-zA-Z0-9]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9] [ ]
     */
    public static final String VALID_TEXT_I_REG_EXP = "^[a-zA-Z0-9 ]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [ ]
     */
    public static final String VALID_TEXT_II_REG_EXP = "^[a-zA-Z ]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [ ] ['] [-]
     */
    public static final String VALID_TEXT_III_REG_EXP = "^[a-zA-Z '-]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * field. Allowed Characters: [a-z] [A-Z] [0-9] [ ] ['] [-]
     */
    public static final String VALID_TEXT_IV_REG_EXP = "^[a-zA-Z0-9 '-]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * text field. Allowed Characters: [a-z] [A-Z] [0-9] [ ] ['] [.] [-]
     */
    public static final String VALID_TEXT_V_REG_EXP = "^[a-zA-Z0-9 '.-]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * text field. Allowed Characters: All including all symbols and
     * non-alphanumeric characters
     */
    public static final String VALID_TEXT_VI_REG_EXP = "^[\\w\\W]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any text
     * text field. Allowed Characters: [a-z] [A-Z] [0-9] [ ] ['] [.] [-]
     */
    public static final String VALID_TEXT_VII_REG_EXP = "^[a-zA-Z0-9 ',.-]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any search
     * text field. Allowed Characters: [a-z] [A-Z] [0-9] [ ] ['] [@] [.] [-] [,]
     */
    public static final String VALID_SEARCH_I_REG_EXP = "^[\\w\\W]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry on any search
     * text field. Allowed Characters: [a-z] [A-Z] [0-9] [ ] ['] [@] [.] [-] [_]
     * [?] [/] [!] [.] [:]
     */
    public static final String VALID_SEARCH_II_REG_EXP = "^[\\w\\W]*$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry in the Credit
     * Card Number field.
     */
    public static final String CREDIT_CARD_REG_EXP = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry in the Email
     * field.
     */
    public static final String EMAIL_REG_EXP = "^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry in the
     * Expiration Date field. Format: YYMM
     */
    public static final String EXP_REG_EXPDATE_YYMM = "^(\\d{2})((0[1-9])|(1[0-2]))$";
    /**
     * DO NOT CHANGE - Regular Expression to validate date entry. Format: YYMMDD
     */
    public static final String EXP_REG_DATE_YYMMDD = "^((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)$";
    /**
     * DO NOT CHANGE - Regular Expression to validate time entry. Format: HHMMSS
     */
    public static final String EXP_REG_TIME_HHMMSS = "^(?:2[0-3]|[01][0-9])[0-5][0-9][0-5][0-9]$";
    /**
     * DO NOT CHANGE - Regular Expression to validate time zone offset GMT
     * entry. Format: [+/-]##:##
     */
    public static final String EXP_REG_TZ_OFFSET = "^(?:Z|[+-](?:2[0-3]|[01][0-9]):[0-5][0-9])$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry in the
     * Expiration Month field. Format: MM
     */
    public static final String MONTH_REG_EXP = "^(0?[1-9]|1[012])$";
    /**
     * DO NOT CHANGE - Regular Expression to validate user entry in the
     * Telephone field. Format: #####(###) ### - ####
     */
    public static final String PHONE_REG_EXP = "^((\\d{2,3})) ?-? ?(\\d{3,4}) ?-? ?(\\d{4})$";
    /**
     * DO NOT CHANGE - Regular Expression to validate password entry on any text
     * field. Allowed [a-z] [A-Z] [0-9] and all Special Characters.
     */
    public static final String EXP_REG_PASSWORD_VALID_ALPHANUMERIC_SPECIAL = "^[A-Za-z0-9 !#$%&'()*+,.:;<=>?@\\[\\]\\\\\\/\\\"^_`{|}~]+$";
    /*
     * ********************************************************************************************
     * *************************************************************** CLIENT FIELDS TRANSFORM TO *
     * ********************************************************************************************
     */
    /**
     * DO NOT CHANGE - Transform format used for phone entry. Format:
     * (#####){0,5}(###) ### - ####
     */
    public static final String PHONE_TRANSFORM_TO = "$1 - $3 - $4";

    /**
     *
     */
    public static final String EXP_REG_FLOAT = "^([0-9]*(\\.[0-9]{2})?){3,20}$";

    /**
     *
     */
    public static final String EXP_REG_KEY_FLOAT = "[0-9.]";

    /**
     *
     */
    public static final String EXP_REG_KEY_EMAIL = "[a-zA-Z0-9.@\\-_]";

    /**
     *
     */
    public static final String EXP_REG_TEXT = "^[_a-zA-Z0-9-,@./!: ]{0,25}$";

    /**
     *
     */
    public static final String EXP_REG_FULL_DATE = "^(0[1-9]|1[0-2])/([012][0-9]|3[01])/(([0-9]{4})|([0-9]{2}))\\s+([01]?[0-9]|2[0-3]):[0-5][0-9]";

    /**
     *
     */
    public static final String EXP_REG_DATE = "^(0[1-9]|1[0-2])/([012][0-9]|3[01])/(([0-9]{4})|([0-9]{2}))";

    /**
     *
     */
    public static final String EXP_REG_TIME = "^([01]?[0-9]|2[0-3]):[0-5][0-9]";

    /**
     *
     */
    public static final String EXP_REG_VALID_ENCRYPTION_FORMATID = "32|37|38|45";

    /**
     *
     */
    public static final String EXP_REG_VALID_NUMERIC = "^[0-9]+$";

    private RegExp() {
    }
}
