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
package com.luretechnologies.server.front.tms.conf.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.luretechnologies.common.Constants;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.display.ErrorResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import org.hibernate.NonUniqueObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     */
    public ExceptionHandlerAdvice() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception ex) {
        return logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorResponse(Constants.CODE_ERROR_GENERAL, ex.getMessage()), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleNotAuthorizedException(NotAuthorizedException ex) {
        return logErrorResponse(HttpStatus.UNAUTHORIZED, new ErrorResponse(Constants.CODE_INVALID_TOKEN, ex.getMessage()), ex);
    }
    
    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return logErrorResponse(HttpStatus.FORBIDDEN, new ErrorResponse(Constants.CODE_NOT_PERMISSION, Messages.NO_PERMISSION), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException ex) {
        return logErrorResponse(HttpStatus.FORBIDDEN, new ErrorResponse(Constants.CODE_NOT_PERMISSION, ex.getMessage()), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String field = result.getFieldErrors().get(0).getField().toUpperCase();
        String error = result.getAllErrors().get(0).getDefaultMessage();

        if (field.isEmpty()) {
            return logErrorResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(Constants.CODE_INVALID_ENTRY_DATA, error), ex);
        } else {
            return logErrorResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(Constants.CODE_INVALID_ENTRY_DATA, field + " " + error), ex);
        }
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        String field = ex.getConstraintViolations().iterator().next().getPropertyPath().toString().toUpperCase();
        String error = ex.getConstraintViolations().iterator().next().getMessageTemplate();

        if (field.isEmpty()) {
            return logErrorResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(Constants.CODE_INVALID_ENTRY_DATA, error), ex);
        } else {
            return logErrorResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(Constants.CODE_INVALID_ENTRY_DATA, field + " " + error), ex);
        }
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            return logErrorResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(Constants.CODE_INVALID_ENTRY_DATA, Messages.ENUM_INVALID_DATA_ENTRY), ex);
        }
        return logErrorResponse(HttpStatus.BAD_REQUEST, new ErrorResponse(Constants.CODE_ERROR_GENERAL, ex.getMessage()), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return logErrorResponse(HttpStatus.CONFLICT, new ErrorResponse(Constants.CODE_INVALID_ENTRY_DATA, Messages.DATA_INTEGRITY_VIOLATION), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ObjectRetrievalFailureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleValidationException(ObjectRetrievalFailureException ex) {
        return logErrorResponse(HttpStatus.NOT_FOUND, new ErrorResponse(Constants.CODE_NOT_FOUND, Messages.NOT_FOUND), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(NonUniqueObjectException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNonUniqueObjectException(NonUniqueObjectException ex) {
        return logErrorResponse(HttpStatus.NOT_FOUND, new ErrorResponse(), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDataAccessException(DataAccessException ex) {
        //return logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorResponse(Constants.CODE_ERROR_GENERAL, Messages.DATA_ACCESS_EXCEPTION), ex);
        return logErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorResponse(Constants.CODE_ERROR_GENERAL,ex.getMessage()), ex);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ErrorResponse handleGeneralError(CustomException ex) {
        return logErrorResponse(HttpStatus.ACCEPTED, new ErrorResponse(ex.getCode(), ex.getMessage()), ex);
    }

    private ErrorResponse logErrorResponse(HttpStatus status, ErrorResponse response, Exception exception) {
        LOGGER.debug(exception.getMessage());

        LOGGER.info("RESPONSE: {} {}", status, status.getReasonPhrase());

        try {
            LOGGER.info("RESPONSE DATA: {}", mapper.writeValueAsString(response));
            return response;
        } catch (JsonProcessingException ex) {
            LOGGER.info("RESPONSE DATA: {}", ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);

            return new ErrorResponse(Constants.CODE_ERROR_GENERAL, ex.getMessage());
        }
    }
}
