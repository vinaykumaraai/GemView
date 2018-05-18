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
package com.luretechnologies.common.enums;

import com.luretechnologies.common.Messages;
import java.util.HashMap;
import java.util.Map;

public enum StatusEnum {

    /* ********************************************************************** */
    /* TRANSACTION STATUS CODES                                               */
    /* ********************************************************************** */

    /**
     *
     */
    TEST(100, Messages.TEST),
    /**
     *
     */
    APPROVED(0, Messages.APPROVED),
    /**
     *
     */
    REFER2CARD_ISSUER(1, Messages.REFER_TO_CARD_ISSUER),
    /**
     *
     */
    REFER2CARD_ISSUER2(2, Messages.REFER_TO_CARD_ISSUER),
    /**
     *
     */
    INVALID_MERCHANT(3, Messages.INVALID_MERCHANT),
    /**
     *
     */
    PICKUP_CARD(4, Messages.PICKUP_CARD),
    /**
     *
     */
    DO_NOT_HONOR(5, Messages.DO_NOT_HONOR),
    /**
     *
     */
    APPROVED_LESSER(10, Messages.APPROVED_LESSER),
    /**
     *
     */
    INVALID_TRANSACTION(12, Messages.INVALID_TRANSACTION),
    /**
     *
     */
    INVALID_AMOUNT(13, Messages.INVALID_AMOUNT),
    /**
     *
     */
    INVALID_ACCOUNT(14, Messages.INVALID_ACCOUNT),
    /**
     *
     */
    NO_SUCH_ISSUER(15, Messages.NO_SUCH_ISSUER),
    /**
     *
     */
    RE_ENTER_TRANSACTION(19, Messages.RE_ENTER_TRANS),
    /**
     *
     */
    UNABLE_TO_LOCATE_RECORD(25, Messages.UNABLE_TO_LOCATE_RECORD),
    /**
     *
     */
    FORMAT_ERROR(30, Messages.FORMAT_ERROR),
    /**
     *
     */
    LOST_CARD(41, Messages.LOST_CARD),
    /**
     *
     */
    STOLEN_CARD(42, Messages.STOLEN_CARD),
    /**
     *
     */
    DECLINED(51, Messages.DECLINED),
    /**
     *
     */
    EXPIRED_CARD(54, Messages.EXPIRED_CARD),
    /**
     *
     */
    INCORRECT_PIN(55, Messages.INCORRECT_PIN),
    /**
     *
     */
    TRANSACTION_NOT_ALLOWED(58, Messages.TRANSACTION_NOT_ALLOWED),
    /**
     *
     */
    ISSUER_OR_SWITCH_INOPERATIVE(91, Messages.ISSUER_OR_SWITCH_INOPERATIVE),
    /**
     *
     */
    DUPLICATED_TRANSMISSION(94, Messages.DUPLICATE_TRANSMISSION),
    /**
     *
     */
    RECONCILE_ERROR(95, Messages.RECONCILE_ERROR),
    /**
     *
     */
    SYSTEM_ERROR(96, Messages.SYSTEM_ERROR),
    
    /* ********************************************************************** */
    /* HOST ERROR STATUS [1000 - 1999]                                        */
    /* ********************************************************************** */
    
    /**
     *
     */
    DATA_ERROR(1000, Messages.DATA_ERROR),
    
    /* ********************************************************************** */
    /* SYSTEM ERROR STATUS[2000 - 2999]                                       */
    /* ********************************************************************** */
    
    /**
     * StatusEnum used to identify general error
     *
     */
    ERROR(2000, Messages.ERROR_GENERAL),
    /**
     * StatusEnum used to identify when the JMS timed out waiting for Core
     * response
     *
     */
    ERROR_TRANSMITING_TO_HOST(2001, Messages.ERROR_TRANSMITTING_TRANSACTION_HOST),
    /**
     * StatusEnum used to identify when the JMS timed out waiting for a Core
     * response
     *
     */
    ERROR_JMS_FRONT_TIMEOUT(2002, Messages.JMS_FRONT_TIMEOUT),
    /**
     * StatusEnum used to identify when the JMS timed out waiting for a Host
     * response
     *
     */
    ERROR_JMS_CORE_TIMEOUT(2003, Messages.JMS_CORE_TIMEOUT),
    /**
     * StatusEnum used to identify when the JMS timed out waiting for a
     * Processor response
     *
     */
    ERROR_JMS_HOST_TIMEOUT(2004, Messages.JMS_HOST_TIMEOUT),
    /**
     * StatusEnum used to identify when the connection to the Processor failed
     *
     */
    ERROR_PROCESSOR_UNREACHEABLE_URI(2005, Messages.PROCESSOR_UNREACHEABLE_URI),
    /**
     * StatusEnum used to identify when there was an error transmitting the
     * transaction to the Processor
     *
     */
    ERROR_TRANSMITTING_TRANSACTION_HOST(2006, Messages.ERROR_TRANSMITTING_TRANSACTION_HOST),
    /**
     * StatusEnum used to identify when the maximum number of reversal attempts
     * is exceeded
     *
     */
    ERROR_EXCEEDED_REVERSAL_ATTEMPTS(2007, Messages.EXCEEDED_REVERSAL_ATTEMPTS),
    /**
     * StatusEnum used to identify when the request was rejected by the host
     *
     */
    ERROR_REJECTED_RESPONSE(2008, Messages.REJECTED_RESPONSE),
    /**
     * StatusEnum used to identify when the card is not supported
     *
     */
    ERROR_CARD_NOT_SUPPORTED(2009, Messages.CARD_NOT_SUPPORTED),
    /**
     * StatusEnum used to identify when the cash back is added on a void
     * transaction
     *
     */
    ERROR_CASHBACK_NOT_ALLOWED(2010, Messages.CASHBACK_NOT_ALLOWED);

    private final Integer id;
    private final String defaultMessage;
    private final String statusCode;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, StatusEnum> map = new HashMap<>();

    StatusEnum(Integer id, String defaultMessage) {
        this.id = id;
        this.defaultMessage = defaultMessage;
        this.statusCode = String.format("%02d", id);
    }

    StatusEnum(Integer id, String defaultMessage, String statusCode) {
        this.id = id;
        this.defaultMessage = defaultMessage;
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @return
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     *
     * @param name
     * @return
     */
    public static Integer getIdFromName(String name) {
        for (StatusEnum item : StatusEnum.values()) {
            if (item.toString().equals(name)) {
                return item.getId();
            }
        }
        return 0;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            map.put(statusEnum.getId(), statusEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final StatusEnum getStatusEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }
}
