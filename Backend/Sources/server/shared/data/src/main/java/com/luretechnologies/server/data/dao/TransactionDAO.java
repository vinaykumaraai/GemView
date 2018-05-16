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
package com.luretechnologies.server.data.dao;

import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.data.display.payment.dashboard.TransactionData;
import com.luretechnologies.server.data.display.payment.dashboard.TransactionSpeed;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.Transaction;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.PersistenceException;

/**
 *
 *
 * @author developer
 */
public interface TransactionDAO extends BaseDAO<Transaction, Long> {

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public Transaction get(UUID id) throws PersistenceException;

    /**
     *
     * @param entity
     * @param id
     * @param filters
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Transaction> search(Entity entity, long id, String filters, int firstResult, int lastResult) throws PersistenceException;
    
    /**
     *
     * @param entity
     * @param filters
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Transaction> search(Entity entity, String filters, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param entity
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Transaction> list(Entity entity, int firstResult, int lastResult) throws PersistenceException;
    
    /**
     *
     * @param entity
     * @param id The entity id
     * @param firstResult
     * @param lastResult
     * @return
     * @throws PersistenceException
     */
    public List<Transaction> list(Entity entity, long id, int firstResult, int lastResult) throws PersistenceException;

    /**
     *
     * @param transactionId
     * @return
     * @throws PersistenceException
     */
    public Transaction findByTransactionId(UUID transactionId) throws PersistenceException;
    
    /**
     *
     * @param merchantTransactionId
     * @return
     * @throws PersistenceException
     */
    public Transaction findByMerchantTransactionId(String merchantTransactionId) throws PersistenceException;

    /**
     * @param time
     * @param device
     * @return
     * @throws PersistenceException
     */
    public Transaction findByDateAndDevice(Timestamp time, Device device) throws PersistenceException;

    /**
     *
     * @param entity
     * @param count
     * @return
     * @throws PersistenceException
     */
    public List<Transaction> listLatestTransactions(Entity entity, int count) throws PersistenceException;

    /**
     *
     * @param entity
     * @return
     * @throws PersistenceException
     */
    public Long getTotalTransactions(Entity entity) throws PersistenceException;

    /**
     *
     * @param entity
     * @param operation
     * @param date
     * @return
     * @throws PersistenceException
     */
    public TransactionData getInfoByOperation(Entity entity, OperationEnum operation, Date date) throws PersistenceException;

    /**
     *
     * @param entity
     * @param operation
     * @return
     * @throws PersistenceException
     */
    public TransactionData getInfoByOperation(Entity entity, OperationEnum operation) throws PersistenceException;

    /**
     *
     * @param entity
     * @param operation
     * @param date
     * @return
     * @throws PersistenceException
     */
    public TransactionData getMonthlyInfoByOperation(Entity entity, OperationEnum operation, Date date) throws PersistenceException;

    /**
     *
     * @return @throws PersistenceException
     */
    public TransactionSpeed getTransactionSpeed() throws PersistenceException;

    /**
     *
     * @param host
     * @return
     * @throws PersistenceException
     */
    public TransactionSpeed getTransactionSpeed(Host host) throws PersistenceException;

    /**
     *
     * @param entity
     * @param host
     * @param count
     * @return
     * @throws PersistenceException
     */
    public List<Transaction> listLatestTransactionsHost(Entity entity, Host host, int count) throws PersistenceException;

    /**
     *
     * @param transactionId
     * @return
     * @throws PersistenceException
     */
    public Transaction findWithAccountInfo(UUID transactionId) throws PersistenceException;
    
        /**
     *
     * @param merchantTransactionId
     * @return
     * @throws PersistenceException
     */
    public Transaction findWithAccountInfo(String merchantTransactionId) throws PersistenceException;

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public BigDecimal getRemainingRefundableAmount(UUID id) throws PersistenceException;

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    public BigDecimal getRemainingVoidableAmount(UUID id) throws PersistenceException;
}
