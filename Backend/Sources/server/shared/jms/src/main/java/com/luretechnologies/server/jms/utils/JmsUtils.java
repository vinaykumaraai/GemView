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
package com.luretechnologies.server.jms.utils;

import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.StatusEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.display.payment.TransactionRequest;
import com.luretechnologies.server.data.display.payment.TransactionResponse;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jms.JMSException;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 *
 * @author developer
 */
public class JmsUtils {

    private static final Logger logger = LoggerFactory.getLogger(JmsUtils.class);

    /**
     * Build Transaction Error response
     *
     * @param transactionRequest
     * @param exception
     * @return
     */
    public static TransactionResponse buildTransactionErrorResponse(TransactionRequest transactionRequest, Exception exception) {
        StatusEnum errorStatus = StatusEnum.ERROR;
        String errorMessage = null;

        // Handle exceptions
        if (exception instanceof JMSException) {
            errorStatus = StatusEnum.ERROR_TRANSMITTING_TRANSACTION_HOST;
        } else if (exception instanceof NoResultException) {
            errorMessage = Messages.NOT_FOUND;
        } else if (exception instanceof HttpClientErrorException) {
            errorStatus = StatusEnum.ERROR_PROCESSOR_UNREACHEABLE_URI;
        } else if (exception.getCause() instanceof SocketTimeoutException) {
            errorStatus = StatusEnum.ERROR_JMS_HOST_TIMEOUT;
        } else if (exception instanceof CustomException) {
            errorMessage = ((CustomException) exception).getMessage();
        }

        logger.info("ERROR: ", exception);

        TransactionResponse transactionResponse = new TransactionResponse();

        transactionRequest.getPerformanceTiming().setPROCESSOR_CONNECT_START();

        // Set TransactionRequest transmission time
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        transactionRequest.setTransmissionDate(dateFormat.format(date));
        transactionRequest.setTransmissionTime(timeFormat.format(date));

        transactionRequest.getPerformanceTiming().setPROCESSOR_SEND_REQUEST();
        transactionRequest.getPerformanceTiming().setPROCESSOR_RECEIVED_RESPONSE();

        transactionResponse.setStatus(errorStatus);
        transactionResponse.setDisposition(errorMessage == null ? errorStatus.getDefaultMessage() : errorMessage);
        transactionResponse.setTransactionRequest(transactionRequest);
        transactionResponse.setBatchNumber(0);
        transactionResponse.setSequenceNumber(transactionRequest.getSequenceNumber() != null ? transactionRequest.getSequenceNumber() : 0);
        transactionResponse.setEntryMethod(EntryMethodEnum.MANUAL);
        transactionResponse.setApprovalCode("999");
        transactionResponse.setAuthorizedAmount("0");
        transactionResponse.setApprovalNumber("");

        return transactionResponse;
    }

    /**
     *
     * @param transactionRequest
     * @param errorStatus
     * @return
     */
    public static TransactionResponse buildTransactionErrorResponse(TransactionRequest transactionRequest, StatusEnum errorStatus) {

        TransactionResponse transactionResponse = new TransactionResponse();

        transactionRequest.getPerformanceTiming().setPROCESSOR_CONNECT_START();

        // Set TransactionRequest transmission time
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        transactionRequest.setTransmissionDate(dateFormat.format(date));
        transactionRequest.setTransmissionTime(timeFormat.format(date));

        transactionRequest.getPerformanceTiming().setPROCESSOR_SEND_REQUEST();
        transactionRequest.getPerformanceTiming().setPROCESSOR_RECEIVED_RESPONSE();

        transactionResponse.setStatus(errorStatus);
        transactionResponse.setDisposition(errorStatus.getDefaultMessage());
        transactionResponse.setTransactionRequest(transactionRequest);
        transactionResponse.setBatchNumber(0);
        transactionResponse.setSequenceNumber(transactionRequest.getSequenceNumber() != null ? transactionRequest.getSequenceNumber() : 0);
        transactionResponse.setEntryMethod(EntryMethodEnum.MANUAL);
        transactionResponse.setApprovalCode("999");
        transactionResponse.setAuthorizedAmount("0");
        transactionResponse.setApprovalNumber("");

        return transactionResponse;

    }

    /**
     *
     * @param transactionRequest
     * @return
     */
    public static TransactionResponse buildTestTransactionResponse(TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = new TransactionResponse();

        transactionRequest.getPerformanceTiming().setPROCESSOR_CONNECT_START();

        // Set TransactionRequest transmission time
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        transactionRequest.setTransmissionDate(dateFormat.format(date));
        transactionRequest.setTransmissionTime(timeFormat.format(date));

        transactionRequest.getPerformanceTiming().setPROCESSOR_SEND_REQUEST();
        transactionRequest.getPerformanceTiming().setPROCESSOR_RECEIVED_RESPONSE();

        transactionResponse.setStatus(StatusEnum.TEST);
        transactionResponse.setDisposition(StatusEnum.TEST.getDefaultMessage());
        transactionResponse.setTransactionRequest(transactionRequest);
        transactionResponse.setBatchNumber(0);
        transactionResponse.setSequenceNumber(transactionRequest.getSequenceNumber());
        transactionResponse.setEntryMethod(EntryMethodEnum.MANUAL);
        transactionResponse.setApprovalCode("TEST");
        transactionResponse.setAuthorizedAmount("0");
        transactionResponse.setApprovalNumber("TEST");

        return transactionResponse;
    }
}
