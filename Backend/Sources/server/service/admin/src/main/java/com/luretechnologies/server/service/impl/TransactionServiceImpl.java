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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.TransactionDAO;
import com.luretechnologies.server.data.display.payment.dashboard.SalesData;
import com.luretechnologies.server.data.display.payment.dashboard.TransactionData;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.payment.Signature;
import com.luretechnologies.server.data.model.payment.Transaction;
import com.luretechnologies.server.service.TransactionService;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDAO transactionDAO;

    /**
     *
     * @param transaction
     * @return
     * @throws Exception
     */
    @Override
    public Transaction create(Transaction transaction) throws Exception {
        Transaction newTransaction = new Transaction();

        // TODO
        // Copy properties from -> to
        BeanUtils.copyProperties(transaction, newTransaction);

        return transactionDAO.merge(newTransaction);
    }

    /**
     *
     * @param id
     * @param transaction
     * @return
     * @throws Exception
     */
    @Override
    public Transaction update(long id, Transaction transaction) throws Exception {
        Transaction existentTransaction = transactionDAO.findById(id);

        // TODO
        // Copy properties from -> to
        BeanUtils.copyProperties(transaction, existentTransaction, new String[]{"applicationPackages", "auditLogs"});

        return transactionDAO.merge(existentTransaction);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        Transaction transaction = transactionDAO.findById(id);

        transactionDAO.delete(transaction.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Transaction get(UUID id) throws Exception {

        return transactionDAO.get(id);
    }

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Transaction> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return transactionDAO.list(entity, firstResult, rowsPerPage);
    }
    
    /**
     *
     * @param entity
     * @param id The entity id
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Transaction> list(Entity entity, long id, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return transactionDAO.list(entity, id, firstResult, rowsPerPage);
    }

    /**
     *
     * @param id
     * @param imageFile
     * @return
     * @throws Exception
     */
    @Override
    public Transaction addSignature(UUID id, MultipartFile imageFile) throws Exception {
        Transaction transaction = transactionDAO.findByTransactionId(id);
        Signature signature = transaction.getSignature();

        if (signature == null) {
            signature = new Signature();
            transaction.setSignature(signature);
        }

        // Convert image file to base64 String
        String base64String = Utils.encodeToBase64String(imageFile);
        signature.setImage(new SerialBlob(base64String.getBytes()));
        return transactionDAO.merge(transaction);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String getSignature(UUID id) throws Exception {
        Transaction transaction = transactionDAO.findByTransactionId(id);
        Signature signature = transaction.getSignature();

        if (signature != null) {
            InputStream inputStream = signature.getImage().getBinaryStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new String(bytes);
        }
        return null;
    }

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Transaction> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return transactionDAO.search(entity, filter, firstResult, rowsPerPage);
    }
    
    /**
     *
     * @param entity
     * @param id
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Transaction> search(Entity entity, long id, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return transactionDAO.search(entity, id, filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(transactionDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param count
     * @return
     * @throws Exception
     */
    @Override
    public List<Transaction> listLatestTransactions(Entity entity, int count) throws Exception {
        return transactionDAO.listLatestTransactions(entity, count);
    }

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Long getTotalTransactions(Entity entity) throws Exception {
        return transactionDAO.getTotalTransactions(entity);
    }

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public TransactionData getVoidInformation(Entity entity) throws Exception {
        return transactionDAO.getInfoByOperation(entity, OperationEnum.VOID);
    }

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public TransactionData getRefundInformation(Entity entity) throws Exception {
        return transactionDAO.getInfoByOperation(entity, OperationEnum.REFUND);
    }

    /**
     *
     * @param entity
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public SalesData getTodaySales(Entity entity, Date date) throws Exception {

        TransactionData saleData = transactionDAO.getInfoByOperation(entity, OperationEnum.SALE, date);
        TransactionData voidData = transactionDAO.getInfoByOperation(entity, OperationEnum.VOID, date);
        BigDecimal salesTotal = BigDecimal.ZERO;
        if (saleData.getAmount() != null && voidData.getAmount() != null) {
            salesTotal = saleData.getAmount().subtract(voidData.getAmount());
        } else if (saleData.getAmount() != null) {
            salesTotal = saleData.getAmount();
        } else if (voidData.getAmount() != null) {
            salesTotal = voidData.getAmount().multiply(BigDecimal.valueOf(-1)); //BigDecimal.ZERO.subtract(voidData.getAmount());
        }
        return new SalesData(saleData.getCount(), voidData.getCount(), salesTotal);

    }

    /**
     *
     * @param entity
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public SalesData getMonthlySales(Entity entity, Date date) throws Exception {

        TransactionData saleData = transactionDAO.getMonthlyInfoByOperation(entity, OperationEnum.SALE, date);
        TransactionData voidData = transactionDAO.getMonthlyInfoByOperation(entity, OperationEnum.VOID, date);
        BigDecimal salesTotal = BigDecimal.ZERO;
        if (saleData.getAmount() != null && voidData.getAmount() != null) {
            salesTotal = saleData.getAmount().subtract(voidData.getAmount());
        } else if (saleData.getAmount() != null) {
            salesTotal = saleData.getAmount();
        } else if (voidData.getAmount() != null) {
            salesTotal = voidData.getAmount().multiply(BigDecimal.valueOf(-1)); //BigDecimal.ZERO.subtract(voidData.getAmount());
        }
        return new SalesData(saleData.getCount(), voidData.getCount(), salesTotal);

    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal getRemainingRefundableAmount(UUID id) throws Exception {
        return transactionDAO.getRemainingRefundableAmount(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal getRemainingVoidableAmount(UUID id) throws Exception {
        return transactionDAO.getRemainingVoidableAmount(id);
    }
}
