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
package com.luretechnologies.server.audit.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luretechnologies.common.conf.provider.HibernateAwareObjectMapper;
import com.luretechnologies.server.audit.aop.util.AuditService;
import static com.luretechnologies.server.common.utils.StringUtils.maskAll;
import static com.luretechnologies.server.common.utils.StringUtils.maskCardData;
import static com.luretechnologies.server.common.utils.StringUtils.maskJsonTransaction;
import com.luretechnologies.server.data.display.payment.TransactionRequest;
import com.luretechnologies.server.data.display.payment.TransactionResponse;
import com.luretechnologies.server.data.display.tms.DataPackage;
import com.luretechnologies.server.data.model.Terminal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * @author developer
 */
@Aspect
public class Audit {

    @Autowired
    private AuditService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(Audit.class);
    private final HibernateAwareObjectMapper mapper = new HibernateAwareObjectMapper();

    /**
     *
     */
    public Audit() {
    }

    /**
     *
     */
    @Pointcut("within(com.luretechnologies.server.front.*.controller.*)")
    public void controller() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.front.*.controller.ApplicationController*.getUpdate(*,*))")
    public void applicationController() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.front.*.controller.TransactionController*.doTransaction(..)))")
    public void doTransactionController() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.service.*.send(..)))")
    public void frontSendRequestToCoreService() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.service.PaymentCoreService*.sendToHost(..)))")
    public void coreSendRequestToHostService() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.service.payment.core.*Core.process(..)))")
    public void coreProcessRequestService() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.service.*.*.PaymentHostService*.processTransaction(..)))")
    public void hostSendRequestToProcessorService() {
    }

    /**
     *
     */
    @Pointcut("execution(* com.luretechnologies.server.service.PaymentCoreService*.receiveFromHost(..)))")
    public void coreReceivedResponseService() {
    }

    /**
     *
     * @param thisJoinPoint
     */
    @Before("controller()")
    public void doBeforeControllerAdvice(JoinPoint thisJoinPoint) {
        try {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
            String method = signature.getMethod().getAnnotation(RequestMapping.class).method()[0].toString();

            RequestMapping parentMapping = (RequestMapping) signature.getDeclaringType().getAnnotation(RequestMapping.class);
            String base = parentMapping == null ? "" : (parentMapping.value().length == 0 ? "" : parentMapping.value()[0]);

            RequestMapping methodMapping = (RequestMapping) signature.getMethod().getAnnotation(RequestMapping.class);
            String path = methodMapping == null ? "" : (methodMapping.value().length == 0 ? "" : methodMapping.value()[0]);

            String[] paramName = methodMapping == null ? null : (methodMapping.params().length == 0 ? null : methodMapping.params());
            Object[] paramValue = thisJoinPoint.getArgs();

            LOGGER.info("--------------------------------------------------------");
            LOGGER.info("REQUEST 0: {} {}", method, base + path);

            if (paramName != null) {
                for (int i = 0; i < paramName.length; i++) {
                    String name = paramName[i];
                    String value = (String) paramValue[i];

                    switch (name.toLowerCase()) {

                        case "cardexpdate":
                        case "cardexpdatemonth":
                        case "cardexpdateyear2":
                        case "cardexpdateyear4":
                        case "cardholdername":
                        case "cardzip":
                        case "cvv":
                        case "password":
                        case "ssn":
                            value = maskAll(value);
                            break;

                        case "cardpan":
                        case "cardtrack":
                            value = maskCardData(value);
                            break;
                    }

                    LOGGER.info("REQUEST PARAM: {} = {}", name, value);
                }
            } else if (paramValue != null) {
                for (Object value : paramValue) {
                    LOGGER.info("REQUEST PARAM: {}", maskJsonTransaction(mapper.writeValueAsString(value)));
                }
            }
        } catch (JsonProcessingException ex) {
            LOGGER.error("JsonProcessingException", ex);
        }
    }

    /**
     *
     * @param thisJoinPoint
     * @param result
     */
    @AfterReturning(pointcut = "controller()", returning = "result")
    public void doAfterReturnningControllerAdvice(JoinPoint thisJoinPoint, Object result) {
        try {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
            String method = signature.getMethod().getAnnotation(RequestMapping.class).method()[0].toString();
            HttpStatus status = "POST".equals(method) ? HttpStatus.CREATED : ("DELETE".equals(method) ? HttpStatus.NO_CONTENT : HttpStatus.OK);

            String data = mapper.writeValueAsString(result);

            LOGGER.info("RESPONSE: {} {}", status, status.getReasonPhrase());
            LOGGER.info("RESPONSE DATA: {}", data);
        } catch (JsonProcessingException ex) {
            LOGGER.error("JsonProcessingException", ex);
        }
    }

    /**
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("applicationController()")
    public Object doAroundApplicationControllerAdvice(ProceedingJoinPoint jp) throws Throwable {
        DataPackage resultPackage = null;

        try {
            Object[] args = jp.getArgs();
            if (args.length > 0) {
                DataPackage dataPackage = (DataPackage) args[1];

                // Proceed with method invocation
                resultPackage = (DataPackage) jp.proceed();
                String serialNumber = dataPackage.getSerialNumber();

                Terminal terminal = service.getTerminal(serialNumber);

                // Save Request AuditLog to Database
                service.saveAuditRequest(terminal, dataPackage);

                // Save Response AuditLog to Database
                service.saveAuditRequest(terminal, resultPackage);
            }
        } catch (Exception ex) {
            LOGGER.error("AuditError", ex);
        }

        return resultPackage == null ? jp.proceed() : resultPackage;
    }

    /**
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("doTransactionController()")
    public TransactionResponse doAroundDoTransactionControllerAdvice(ProceedingJoinPoint jp) throws Throwable {
        TransactionResponse transactionResponse = null;

        try {
            Object[] args = jp.getArgs();
            if (args.length > 0) {
                TransactionRequest transactionRequest = (TransactionRequest) args[0];

                // Front received request
                transactionRequest.getPerformanceTiming().setFRONT_RECEIVED_REQUEST();

                // Proceed with method invocation
                transactionResponse = (TransactionResponse) jp.proceed();

                transactionRequest = transactionResponse.getTransactionRequest();

                // Front send response
                transactionRequest.getPerformanceTiming().setFRONT_SEND_RESPONSE();

                // Save and display time performace info for debugging purposes
                if (System.getProperty("DEBUG_MODE") == null ? false : Boolean.valueOf(System.getProperty("DEBUG_MODE"))) {
                    service.saveTimePerformance(transactionResponse, transactionRequest);
                    LOGGER.info("--------------------------------------------------------");
                    LOGGER.debug(transactionRequest.getPerformanceTiming().toString());
                }
            }
        } catch (Exception ex) {
            // Validation error printed and thrown out on purpose.
            LOGGER.error("ValidationError", ex);
            throw ex;
        }

        return transactionResponse;
    }

    /**
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("frontSendRequestToCoreService()")
    public TransactionResponse doAroundFrontSendRequestToCoreServiceAdvice(ProceedingJoinPoint jp) throws Throwable {
        TransactionResponse transactionResponse = null;

        try {
            Object[] args = jp.getArgs();
            if (args.length > 0) {
                TransactionRequest transactionRequest = (TransactionRequest) args[0];

                // Front send request
                transactionRequest.getPerformanceTiming().setFRONT_SEND_REQUEST();

                // Proceed with method invocation
                transactionResponse = (TransactionResponse) jp.proceed();

                transactionRequest = transactionResponse.getTransactionRequest();

                // Front receive response
                transactionRequest.getPerformanceTiming().setFRONT_RECEIVED_RESPONSE();
            }
        } catch (Exception ex) {
            LOGGER.error("AuditError", ex);
        }

        return transactionResponse;
    }

    /**
     *
     * @param jp
     * @throws Throwable
     */
    @Around("coreSendRequestToHostService()")
    public void doAroundCoreSendRequestToHostServiceAdvice(ProceedingJoinPoint jp) throws Throwable {
        try {
            Object[] args = jp.getArgs();
            if (args.length > 0) {
                TransactionRequest transactionRequest = (TransactionRequest) args[0];

                // Front received request
                transactionRequest.getPerformanceTiming().setCORE_RECEIVED_REQUEST();

                // Proceed with method invocation
                jp.proceed();
            }
        } catch (Exception ex) {
            LOGGER.error("AuditError", ex);
        }
    }

    /**
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("coreProcessRequestService()")
    public Object doAroundCoreProcessRequestServiceAdvice(ProceedingJoinPoint jp) throws Throwable {

        // Proceed with method invocation
        TransactionRequest transactionRequest = (TransactionRequest) jp.proceed();

        // Front send request
        transactionRequest.getPerformanceTiming().setCORE_SEND_REQUEST();

        return transactionRequest;
    }

    /**
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("coreReceivedResponseService()")
    public Object doAroundCoreReceivedResponseServiceAdvice(ProceedingJoinPoint jp) throws Throwable {
        Message<TransactionResponse> message = null;

        try {
            Object[] args = jp.getArgs();
            if (args.length > 0) {
                TransactionResponse transactionResponse = (TransactionResponse) args[0];

                TransactionRequest transactionRequest = transactionResponse.getTransactionRequest();

                // Core received response
                transactionRequest.getPerformanceTiming().setCORE_RECEIVED_RESPONSE();

                // Proceed with method invocation
                message = (Message<TransactionResponse>) jp.proceed();

                transactionResponse = message.getPayload();

                transactionRequest = transactionResponse.getTransactionRequest();

                // Front send response
                transactionRequest.getPerformanceTiming().setCORE_SEND_RESPONSE();
            }
        } catch (Exception ex) {
            LOGGER.error("AuditError", ex);
        }

        return message == null ? jp.proceed() : message;
    }

    /**
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("hostSendRequestToProcessorService()")
    public Object doAroundHostSendToProcessorServiceAdvice(ProceedingJoinPoint jp) throws Throwable {
        Message<TransactionResponse> message = null;

        try {
            Object[] args = jp.getArgs();
            if (args.length > 0) {
                TransactionRequest transactionRequest = (TransactionRequest) args[0];

                // Front received request
                transactionRequest.getPerformanceTiming().setHOST_RECEIVED_REQUEST();

                // Proceed with method invocation
                message = (Message<TransactionResponse>) jp.proceed();

                TransactionResponse transactionResponse = message.getPayload();

                transactionRequest = transactionResponse.getTransactionRequest();

                // Front send response
                transactionRequest.getPerformanceTiming().setHOST_SEND_RESPONSE();
            }
        } catch (Exception ex) {
            LOGGER.error("AuditError", ex);
        }

        return message == null ? jp.proceed() : message;
    }
}
