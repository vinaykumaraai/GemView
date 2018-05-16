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
package com.luretechnologies.common;

/**
 * Messages Class - containing all Messages constants and messages.
 */
public class Constants {

    /**
     * ******************** CODES **********************
     */
    /**
     * Error Code - CODE_ERROR_GENERAL
     */
    public static final int CODE_ERROR_GENERAL = 500;
    /**
     * Error Code - CODE_ERROR_HSM
     */
    public static final int CODE_ERROR_HSM = 501;
    /**
     * Error Code - CODE_ERROR_CONNECTION_HOST
     */
    public static final int CODE_ERROR_CONNECTION_HOST = 502;
    /**
     * Error Code - CODE_ERROR_VERSION
     */
    public static final int CODE_ERROR_VERSION = 503;
    /**
     * Error Code - CODE_ERROR_BAD_SWIPE
     */
    public static final int CODE_ERROR_BAD_SWIPE = 504;
    /**
     * Error Code - CODE_ERROR_BAD_SWIPE
     */
    public static final int CODE_ERROR_TERMINAL_SN_NOT_REGISTERED = 505;
    /**
     * SUCCESS Code - CODE_ERROR_VERSION
     */
    public static final int CODE_SUCCESS = 100;
    /**
     * Error Code - CODE_SUCCESS
     */
    public static final int CODE_INVALID_GROUP = 400;
    /**
     * Error Code - CODE_INVALID_GROUP
     */
    public static final int CODE_INVALID_USER = 401;
    /**
     * Error Code - CODE_INVALID_USER
     */
    public static final int CODE_USER_NOT_FOUND = 402;
    /**
     * Error Code - CODE_BADLOGIN
     */
    public static final int CODE_BADLOGIN = 403;
    /**
     * Error Code - CODE_WRONG_USER
     */
    public static final int CODE_WRONG_USER = 409;
    /**
     * Error Code - CODE_NOT_PERMISSION
     */
    public static final int CODE_NOT_PERMISSION = 410;
    /**
     * Error Code - CODE_JMS_ERROR
     */
    public static final int CODE_JMS_ERROR = 411;
    /**
     * Error Code - CODE_HOST_NOT_FOUND
     */
    public static final int CODE_HOST_NOT_FOUND = 412;
    /**
     * Error Code - HEADER_TOKEN_MISSING
     */
    public static final int HEADER_TOKEN_MISSING = 413;
    /**
     * Error Code - CODE_INVALID_TOKEN
     */
    public static final int CODE_INVALID_TOKEN = 414;
    /**
     * Error Code - CODE_INVALID_TOKEN_AUDIENCE
     */
    public static final int CODE_INVALID_TOKEN_AUDIENCE = 416;
    /**
     * Error Code - CODE_INVALID_VERIFICATION_CODE
     */
    public static final int CODE_INVALID_VERIFICATION_CODE = 417;

    /**
     * ******************** BUSINESS LOGIC ERROR CODES **********************
     */
    /**
     * The amount to be refunded exceeded the original sale amount.
     */
    public static final int REFUND_AMOUNT_OVER_SALE_AMOUNT = 203;
    /**
     * The amount to be voided exceeded the original sale amount.
     */
    public static final int VOID_AMOUNT_OVER_SALE_AMOUNT = 204;

    /**
     * ******************** SURVEY CODES **********************
     */
    /**
     * Survey - Invalid control parent
     */
    public static final int INVALID_CONTROL_PARENT = 601;

    /**
     *
     */
    public static final int INVALID_CONTENT_DATA = 506;

    /**
     * ******************** DATA ENTRY ERROR CODES **********************
     */
    public static final int CODE_INVALID_ENTRY_DATA = 405;

    /**
     *
     */
    public static final int CODE_SESSION_VALID = 406;

    /**
     *
     */
    public static final int CODE_SESSION_EXPIRED = 407;

    /**
     *
     */
    public static final int CODE_SESSION_LOST = 408;

    /**
     *
     */
    public static final int CODE_MAX_UPLOAD_SIZE_EXCEEDED = 415;

    /**
     * ******************** DATA ACCESS ERROR CODES **********************
     */
    public static final int CANNOT_DELETE_CONTROL = 701;

    /**
     *
     */
    public static final int CANNOT_DELETE_FORM = 702;

    /**
     *
     */
    public static final int CODE_NOT_FOUND = 703;

    /**
     *
     */
    public static final int CODE_CANNOT_BE_DELETED = 704;

    /**
     *
     */
    public static final int CODE_NOTHING_TO_UPDATE = 705;

    /**
     * ******************** JMS ERROR CODES **********************
     */
    public static final int JMS_GENERAL_ERROR_CODE = 800;

    /**
     *
     */
    public static final int JMS_UNREACHEABLE_BROKER_URI_CODE = 801;

    /**
     * ******************** PROCESSOR ERROR CODES **********************
     */
    public static final int PROCESSOR_GENERAL_ERROR_CODE = 900;

    /**
     *
     */
    public static final int PROCESSOR_UNREACHEABLE_URI_CODE = 901;

    /**
     *
     */
    public static final int INVALID_TRANSACTION_CODE = 902;

    private Constants() {
    }

}
