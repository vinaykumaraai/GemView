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
 * Messages class containing all Messages constants and messages.
 *
 */
public class Messages {

    /**
     * ******************** Info
     */
    public static final String API_TERMS_OF_SERVICE = "http://www.luretechnologies.com";

    /**
     *
     */
    public static final String API_CONTACT_NAME = "sales@luretechnologies.com";

    /**
     *
     */
    public static final String API_CONTACT_URL = "http://www.luretechnologies.com";

    /**
     *
     */
    public static final String API_CONTACT_EMAIL = "sales@luretechnologies.com";

    /**
     *
     */
    public static final String API_LICENSE_NAME = "Proprietary, Customer Specific";

    /**
     *
     */
    public static final String API_LICENSE_URL = "http://www.luretechnologies.com";

    /**
     * Survey - Invalid control parent
     */
    public static final String INVALID_CONTROL_PARENT = "This control does not accept a parent.";

    /**
     *
     */
    public static final String ERROR_GENERAL = "ERROR";

    /**
     *
     */
    public static final String ERROR_TRANSMITTING_TRANSACTION_HOST = "ERROR TRANSMITTING OR RECEIVING TRANSACTION";

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
    public static final String REJECTED_RESPONSE = "REQUEST REJECTED BY HOST";

    /**
     *
     */
    public static final String INVALID_TRANSACTION = "INVALID TRANSACTION";
    /**
     *
     */
    public static final String CARD_NOT_SUPPORTED = "CARD NOT SUPPORTED";
    /**
     *
     */
    public static final String CASHBACK_NOT_ALLOWED = "CASH BACK NOT ALLOWED";
    /**
     *
     */
    public static final String VOID_AMOUNT_OVER_SALE_AMOUNT = "THE AMOUNT TO BE VOIDED EXCEEDED THE ORIGINAL SALE AMOUNT";
    /**
     *
     */
    public static final String REFUND_AMOUNT_OVER_SALE_AMOUNT = "THE AMOUNT TO BE REFUNDED EXCEEDED THE ORIGINAL SALE AMOUNT";
    /**
     *
    */
    public static final String REFER_TO_CARD_ISSUER = "Refer to card issuer";
    /**
     *
    */
    public static final String INVALID_MERCHANT = "Invalid merchant or service provider";
    /**
     *
    */
    public static final String PICKUP_CARD = "Pickup card";
    /**
     *
    */
    public static final String DO_NOT_HONOR = "Do not honor";
    /**
     *
    */
    public static final String APPROVED_LESSER = "Approved Lesser";
    /**
     *
    */
    public static final String DECLINED = "Declined";
    /**
     *
    */
    public static final String DATA_ERROR = "Data Error";
    /**
     *
    */
    public static final String APPROVED = "Approved";
    /**
     *
    */
    public static final String TEST = "Test";
    /**
     *
    */
    public static final String INVALID_AMOUNT = "Invalid Amount";
    /**
     *
    */
    public static final String INVALID_ACCOUNT = "Invalid account number (no such number)";
    /**
     *
    */
    public static final String NO_SUCH_ISSUER = "No such issuer";
     /**
     *
    */
    public static final String RE_ENTER_TRANS = "Re-enter transaction";
    /**
     *
    */
    public static final String UNABLE_TO_LOCATE_RECORD = "Unable to locate record in file";
    /**
     *
    */
    public static final String FORMAT_ERROR = "Format Error";
    /**
     *
    */
    public static final String LOST_CARD = "Lost Card";
    /**
     *
    */
    public static final String STOLEN_CARD = "Stolen Card";
    /**
     *
    */
    public static final String EXPIRED_CARD = "Expired Card";
    /**
     *
    */
    public static final String INCORRECT_PIN = "Incorrect PIN";
    /**
     *
    */
    public static final String TRANSACTION_NOT_ALLOWED = "Transaction not allowed at terminal";
    /**
     *
    */
    public static final String ISSUER_OR_SWITCH_INOPERATIVE = "Issuer or switch is inoperative";
    /**
     *
    */
    public static final String DUPLICATE_TRANSMISSION = "Duplicate Transmission";
    /**
     *
    */
    public static final String RECONCILE_ERROR = "Reconcile error";
    /**
     *
    */
    public static final String SYSTEM_ERROR = "System error";


    private Messages() {
    }

}
