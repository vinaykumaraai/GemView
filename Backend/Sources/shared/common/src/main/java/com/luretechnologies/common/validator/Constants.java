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
 * The Constants class
 *
 *
 */
public class Constants {

    /*
     * ********************************************************************************************
     * ************************************************* DO NOT CHANGE - CLIENT FIELDS MAX LENGTH *
     * ********************************************************************************************
     */
    /**
     *
     */
    public static final int STANDARD_TEXT_MIN_LENGTH = 3;

    /**
     *
     */
    public static final int STANDARD_TEXT_MAX_LENGTH = 25;

    /**
     *
     */
    public static final int MEDIUM_TEXT_MAX_LENGTH = 50;

    /**
     *
     */
    public static final int HIGH_TEXT_MAX_LENGTH = 500;

    /**
     *
     */
    public static final int STANDARD_PHONE_TEXT_LENGTH = 13;

    /**
     *
     */
    public static final int TID_MAX_LENGTH = 13;

    /**
     *
     */
    public static final int CVV_MAX_LENGTH = 4;

    /**
     *
     */
    public static final int EXPDATE_LENGTH = 4;

    /**
     *
     */
    public static final int LAST_FOUR_MAX_LENGTH = 4;

    /**
     *
     */
    public static final int ZIP_MIN_LENGTH = 5;

    /**
     *
     */
    public static final int ZIP_MAX_LENGTH = 9;

    /**
     *
     */
    public static final int CC_MAX_LENGTH = 19;

    /**
     *
     */
    public static final int ACCOUNT_MAX_LENGTH = 30;

    /**
     *
     */
    public static final int PO_NUMBER_MAX_LENGTH = 20;

    /**
     *
     */
    public static final int PHONE_MAX_LENGTH = 20;

    /**
     *
     */
    public static final int AMOUNT_MAX_LENGTH = 8;

    /**
     *
     */
    public static final int AUTH_NUMBER_LENGTH = 6;

    /*
     * ********************************************************************************************
     * ************************************************** DO NOT CHANGE - CLIENT FIELDS CONSTANTS *
     * ********************************************************************************************
     */
    /**
     *
     */
    public static final int MIN_AMOUNT_ALLOWED = 001;

    /**
     *
     */
    public static final int MAX_AMOUNT_ALLOWED = 99999999;

    /**
     *
     */
    public static final int MIN_NOT_REQUIRED_AMOUNT_ALLOWED = 0;

    /**
     *
     */
    public static final int FORMATID_NOT_ENC_VALUE = 32;

    /**
     *
     */
    public static final int FORMATID_ENC_VALUE = 38;

    private Constants() {
    }

}
