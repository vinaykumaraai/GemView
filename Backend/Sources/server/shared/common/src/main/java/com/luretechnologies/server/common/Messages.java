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
package com.luretechnologies.server.common;

/**
 * Messages Class - containing all Messages's constants and messages.
 */
public class Messages {

    /**
     * ********************* MESSAGES SUCCESS ****************
     */
    public static final String SUCCESS = "SUCCESS";
    /**
     * Message Format Version
     */
    public static final String MESSAGE_FORMAT_VERSION = "04A";
    /**
     * ********************* MESSAGES ERROR ****************
     */
    public static final String ERROR_GENERAL = "ERROR";

    /**
     *
     */
    public static final String ERROR_TRANSMITTING_TRANSACTION_HOST = "ERROR TRANSMITTING OR RECEIVING TRANSACTION ";

    /**
     *
     */
    public static final String FAIL_INSERT_SIGNATURE = "FAILED TO INSERT SIGNATURE";

    /**
     *
     */
    public static final String FAIL_GET_TRANSACTION = "FAILED TO GET TRANSACTION";

    /**
     *
     */
    public static final String FAIL_GET_SIGNATURE = "FAILED TO GET SIGNATURE";

    /**
     *
     */
    public static final String FAIL_REFUND_AMOUNT_OVER_SALE_AMOUNT = "THE AMOUNT TO BE REFUND EXCEEDED THE ORIGINAL SALE AMOUNT";

    /**
     * ********************* MESSAGES NOT FOUND ******************************
     */
    public static final String INVALID_GROUP = "INVALID GROUP";

    /**
     *
     */
    public static final String INVALID_USER = "INVALID USER";

    /**
     *
     */
    public static final String USER_NOT_FOUND = "USER NOT FOUND";

    /**
     *
     */
    public static final String JMS_WRONG_TYPE = "JMS OBJECT MESSAGE OF WRONG TYPE ";

    /**
     *
     */
    public static final String JMS_UNREACHEABLE_BROKER_URI = "UNREACHEABLE JMS BROKER URI";

    /**
     *
     */
    public static final String JMS_FRONT_TIMEOUT = "JMS TIMED OUT WAITING FOR CORE RESPONSE";

    /**
     *
     */
    public static final String JMS_CORE_TIMEOUT = "JMS TIMED OUT WAITING FOR HOST RESPONSE";

    /**
     *
     */
    public static final String JMS_HOST_TIMEOUT = "JMS TIMED OUT WAITING FOR PROCESSOR RESPONSE";

    /**
     *
     */
    public static final String PROCESSOR_UNREACHEABLE_URI = "UNREACHEABLE PAYMENT PROCESSOR URI";

    /**
     *
     */
    public static final String EXCEEDED_REVERSAL_ATTEMPTS = "MAXIMUM NUMBER OF REVERSAL ATTEMPTS EXCEEDED";

    /**
     *
     */
    public static final String BEAN_EXCEPTION = "EXCEPTION IN BEAN ";

    /**
     *
     */
    public static final String NOT_TERMINAL_HOST_PARAMETER_FOUND = "FAILED TO GET TERMINAL HOST PARAMETER ";

    /**
     *
     */
    public static final String NOT_MERCHANT_HOST_PARAMETER_FOUND = "FAILED TO GET MERCHANT HOST PARAMETER ";

    /**
     *
     */
    public static final String NOT_HOST_PARAMETER_FOUND = "FAILED TO GET HOST PARAMETER ";

    /**
     *
     */
    public static final String NOT_HOST_RESPONSE = "NO HOST RESPONSE";

    /**
     *
     */
    public static final String FAILED_TO_VERIFY_USER_ACCESS_CORE = "FAILED TO VERIFY USER ACCESS_CORE ";

    /**
     *
     */
    public static final String FAILED_TO_VERIFY_USER_ACCESS_FRONT = "FAILED TO VERIFY USER ACCESS FRONT ";

    /**
     *
     */
    public static final String FAILED_TO_VERIFY_TERMINAL_ACCESS_CORE = "FAILED TO VERIFY TERMINAL ACCESS CORE ";

    /**
     *
     */
    public static final String FAILED_TO_GET_TERMINAL = "FAILED TO GET TERMINAL ";

    /**
     *
     */
    public static final String FAILED_TO_GET_MODE = "FAILED TO GET MODE ";

    /**
     *
     */
    public static final String FAILED_TO_GET_OPERATION = "FAILED TO GET OPERATION ";

    /**
     *
     */
    public static final String FAILED_TO_GET_HOST_BY_MERCHANT_MODE = "FAILED TO GET HOST BY MERCHANT AND MODE ";

    /**
     *
     */
    public static final String FAILED_TO_GET_SENSITIVE_DATA = "FAILED TO GET  SENSITIVE DATA ";

    /**
     *
     */
    public static final String USER_CAN_NOT_PERFORM_TRANSACTION = "USER CAN NOT PERFORM TRANSACTION ";

    /**
     *
     */
    public static final String ACCESING_TO_DB = "ERROR ACCESING TO DB ";

    /**
     *
     */
    public static final String VALUE_IS_EMPTY = "VALUE IS NULL OR EMPTY";

    /**
     *
     */
    public static final String VALUE_IS_BLANK = "VALUE IS BLANK";

    /**
     *
     */
    public static final String VALUE_DATA_TYPE_IS_INCORRECT = "THE VALUE'S DATA TYPE IS INCORRECT";

    /**
     *
     */
    public static final String FIELD_VALUE_IS_EMPTY = "THE FIELD VALUE IS EMPTY ";

    /**
     *
     */
    public static final String FILTER_MUST_HAS_VALUE = "THE FILTER MUST HAS VALUE ";

    /**
     *
     */
    public static final String FILTER_IS_NOT_ALLOWED = "THE FILTER IS NOT ALLOWED ";

    /**
     *
     */
    public static final String ORGANIZATION_DOES_NOT_EXIST = "THIS ORGANIZATION DOES NOT EXIST ";

    /**
     *
     */
    public static final String CLIENT_DOES_NOT_EXIST = "THIS CLIENT DOES NOT EXIST ";

    /**
     *
     */
    public static final String MERCHANT_DOES_NOT_EXIST = "THIS MERCHANT DOES NOT EXIST ";

    /**
     *
     */
    public static final String PROPERTY_DOES_NOT_EXIST = "THIS PROPERTY DOES NOT EXIST ";

    /**
     *
     */
    public static final String MERCHANT_PARAMETER_VALUE_DOES_NOT_EXIST = "THIS MERCHANT PARAMETER VALUE DOES NOT EXIST ";

    /**
     *
     */
    public static final String USER_DOES_NOT_EXIST = "THIS USER DOES NOT EXIST ";

    /**
     *
     */
    public static final String USERNAME_IS_NULL = "USERNAME IS NULL";

    /**
     *
     */
    public static final String PASSWORD_IS_NULL = "PASSWORD IS NULL";

    /**
     *
     */
    public static final String CANNOT_ENCRYPT_NULL_PASSWORD = "CANNOT ENCRYPT NULL PASSWORD";

    /**
     *
     */
    public static final String TERMINAL_DOES_NOT_EXIST = "THIS TERMINAL DOES NOT EXIST ";

    /**
     *
     */
    public static final String TERMINAL_PARAMETER_VALUE_DOES_NOT_EXIST = "THIS TERMINAL PARAMETER VALUE DOES NOT EXIST ";

    /**
     *
     */
    public static final String DEVICE_DOES_NOT_EXIST = "THIS DEVICE DOES NOT EXIST ";

    /**
     *
     */
    public static final String CLERK_DOES_NOT_EXIST = "THIS CLERK DOES NOT EXIST ";

    /**
     *
     */
    public static final String TRANSACTION_DOES_NOT_EXIST = "THIS TRANSACTION DOES NOT EXIST ";

    /**
     *
     */
    public static final String PARAMETER_DOES_NOT_EXIST = "THIS PARAMETER DOES NOT EXIST ";

    /**
     *
     */
    public static final String PARAMETER_VALUE_DOES_NOT_EXIST = "THIS PARAMETER VALUE DOES NOT EXIST ";

    /**
     *
     */
    public static final String PARAMETER_READ_ONLY = "THIS PARAMETER IS READ ONLY AND IT CANNOT BE MODIFIED";

    /**
     *
     */
    public static final String APPLICATION_DOES_NOT_EXIST = "THIS APPLICATION DOES NOT EXIST ";

    /**
     *
     */
    public static final String ROLE_DOES_NOT_EXIST = "THIS ROLE DOES NOT EXIST ";

    /**
     *
     */
    public static final String USER_ROLE_DOES_NOT_EXIST = "THIS USER ROLE DOES NOT EXIST ";

    /**
     *
     */
    public static final String USER_PRIVILEGE_DOES_NOT_EXIST = "THIS USER PRIVILEGE DOES NOT EXIST ";

    /**
     *
     */
    public static final String CLERK_ROLE_DOES_NOT_EXIST = "THIS CLERK_ROLE DOES NOT EXIST ";

    /**
     *
     */
    public static final String CLERK_PRIVILEGE_DOES_NOT_EXIST = "THIS CLERK_PRIVILEGE DOES NOT EXIST ";

    /**
     *
     */
    public static final String CLERK_ROLE_PRIVILEGE_DOES_NOT_EXIST = "THIS CLERK_ROLE_PRIVILEGE DOES NOT EXIST ";

    /**
     *
     */
    public static final String USER_ROLE_PRIVILEGE_DOES_NOT_EXIST = "THIS USER_ROLE_PRIVILEGE DOES NOT EXIST ";

    /**
     *
     */
    public static final String ASSOCIATION_ALREADY_EXIST = "THIS ASSOCIATION ALREADY EXIST";

    /**
     *
     */
    public static final String INVALID_LOGIN_CREDENTIALS = "INVALID LOGIN CREDENTIALS";
    
    /**
     *
     */
    public static final String INVALID_VERIFICATION_CODE = "INVALID VERIFICATION CODE";

    /**
     *
     */
    public static final String NO_HOST_FOUND = "NO HOST {} FOUND";

    /**
     *
     */
    public static final String UNABLE_VOID = "UNABLE TO VOID  TRANSACTION ";

    /**
     *
     */
    public static final String UNABLE_FINALIZE = "UNABLE TO MODIFY A FINALIZED TRANSACTION ";

    /**
     *
     */
    public static final String ERROR_HSM = "ERROR WITH HSM ";

    /**
     *
     */
    public static final String ERROR_LENGTH = "The length of the field is not allowed.";

    /**
     *
     */
    public static final String ERROR_INVALID_CHARACTERS = "Invalid characters entered.";

    /**
     *
     */
    public static final String TERMINAL_NOT_FOUND_BY_DEVICE_SN = "There is not terminal with serial number equals to ";

    /**
     *
     */
    public static final String INVALID_TOKEN = "INVALID HEADER TOKEN RECEIVED";

    /**
     *
     */
    public static final String EXPIRED_TOKEN = "EXPIRED HEADER TOKEN RECEIVED";
    
    /**
     *
     */
    public static final String EXPIRED_VERIFICATION_CODE = "EXPIRED VERIFICATION CODE RECEIVED";

    /**
     *
     */
    public static final String HEADER_TOKEN_MISSING = "MISSING HEADER TOKEN";

    /**
     *
     */
    public static final String INVALID_TOKEN_AUDIENCE = "INVALID TOKEN AUDIENCE";

    /**
     *
     */
    public static final String INVALID_CONTENT_DATA = "INVALID TOKEN FILE";

    /**
     *
     */
    public static final String NO_PERMISSION = "USER DOES NOT HAVE SUFFICIENT PERMISSION TO PERFORM THIS ACTION";

    /**
     * ******************** DATA ENTRY ERROR MESSAGES **********************
     */
    public static final String INVALID_DATA_ENTRY = "INVALID DATA ENTRY";

    /**
     *
     */
    public static final String INVALID_DATA_LENGHT = "INVALID DATA LENGTH";

    /**
     *
     */
    public static final String INVALID_DATA_FORMAT = "INVALID DATA FORMAT";

    /**
     *
     */
    public static final String DEFAULTVALUE_INVALID_DATA_ENTRY = "DEFAULTVALUE INVALID DATA ENTRY";

    /**
     *
     */
    public static final String DEFAULTVALUE_INVALID_DATA_LENGHT = "DEFAULTVALUE INVALID DATA LENGTH";

    /**
     *
     */
    public static final String CONSTRAINT_VIOLATION = "CONSTRAINT VIOLATION";

    /**
     *
     */
    public static final String DATA_INTEGRITY_VIOLATION = "DATA INTEGRITY VIOLATION";

    /**
     *
     */
    public static final String DATA_ACCESS_EXCEPTION = "DATA ACCESS EXCEPTION";

    /**
     *
     */
    public static final String ENUM_INVALID_DATA_ENTRY = "ENUM INVALID DATA ENTRY";

    /**
     *
     */
    public static final String INVALID_PASSWORD_FORMAT = "PASSWORD INVALID DATA ENTRY. MUST CONTAIN AT LEAST 8 CHARACTERS, ONE UPPERCASE, ONE LOWERCASE AND ONE NUMERIC VALUE.";

    /**
     *
     */
    public static final String INVALID_LAST_PASSWORD = "LAST PASSWORD ERROR.";

    /**
     *
     */
    public static final String INCORRECT_DATA_VALUE = "INCORRECT DATA VALUE";

    /**
     * ******************** DATA ACCESS ERROR MESSAGES **********************
     */
    public static final String CANNOT_DELETE_CONTROL_MSG = "This control cannot be deleted. The form is already being used.";

    /**
     *
     */
    public static final String CANNOT_DELETE_FORM_MSG = "This form cannot be deleted. It is already being used.";

    /**
     *
     */
    public static final String NOT_FOUND = "ENTITY NOT FOUND";

    /**
     *
     */
    public static final String CANNOT_BE_DELETED = "ENTITY CANNOT BE DELETED";

    /**
     *
     */
    public static final String NOTHING_TO_UPDATE = "NOTHING TO UPDATE";

    /**
     *
     */
    public static final String ERROR_UNEXPECTED_STATUS_CODE_HOST = "ERROR UNEXPECTED STATUS CODE HOST";

    private Messages() {
    }

    /**
     * Get Default Error Message
     *
     * @param ex Exception
     * @return Error message string
     */
    public static final String getErrorMsg(Exception ex) {
        return ERROR_GENERAL;

    }
}
