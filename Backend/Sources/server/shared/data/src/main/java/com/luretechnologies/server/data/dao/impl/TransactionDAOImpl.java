/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.common.enums.CardBrandEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.common.enums.StatusEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.TransactionDAO;
import com.luretechnologies.server.data.display.payment.dashboard.TransactionData;
import com.luretechnologies.server.data.display.payment.dashboard.TransactionSpeed;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.payment.Account;
import com.luretechnologies.server.data.model.payment.Host;
import com.luretechnologies.server.data.model.payment.TimePerformance;
import com.luretechnologies.server.data.model.payment.Transaction;
import com.luretechnologies.server.data.model.payment.TransactionTax;
import com.luretechnologies.server.filters.FilterItem;
import com.luretechnologies.server.filters.FiltersDecoder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class TransactionDAOImpl extends BaseDAOImpl<Transaction, Long> implements TransactionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDAO.class);

    @Autowired
    EntityDAO entityDAO;

    @Override
    protected Root getRoot(CriteriaQuery cq) {

        Root<Transaction> root = cq.from(Transaction.class);
        root.fetch("terminal", JoinType.LEFT);
        root.fetch("host", JoinType.LEFT);
        root.fetch("originalTransaction", JoinType.LEFT);

        cq.select(root);

        return root;
    }

    @Override
    public Transaction get(UUID id) throws PersistenceException {
        try {

            CriteriaQuery<Transaction> cq = criteriaQuery();
            Root<Transaction> root = getRoot(cq);
            return (Transaction) query(cq.where(criteriaBuilder().equal(root.get("transactionId"), id))).getSingleResult();

        } catch (NoResultException e) {
            LOGGER.info("Transaction not found. ", e);
            return null;
        }
    }

    @Override
    public List<Transaction> list(Entity entity, int firstResult, int lastResult) throws PersistenceException {
        CriteriaQuery<Transaction> cq = criteriaQuery();
        Root<Transaction> root = cq.from(Transaction.class);
        root.fetch("terminal", JoinType.LEFT);
        root.fetch("originalTransaction", JoinType.LEFT);
        List<Predicate> wherePredicate = wherePredicate(root, entity);
        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));
        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public List<Transaction> list(Entity entity, long id, int firstResult, int lastResult) throws PersistenceException {
        if (!entityDAO.isDescendant(entity, id)) {
            throw new PersistenceException("Not found");
        }
        Entity givenEntity = entityDAO.findById(id);
        return list(givenEntity, firstResult, lastResult);
    }

    @Override
    public List<Transaction> search(Entity entity, long id, String filters, int firstResult, int lastResult) throws PersistenceException {
        if (!entityDAO.isDescendant(entity, id)) {
            throw new PersistenceException("Not found");
        }
        Entity givenEntity = entityDAO.findById(id);
        return search(givenEntity, filters, firstResult, lastResult);
    }

    @Override
    public List<Transaction> search(Entity entity, String filters, int firstResult, int lastResult) throws PersistenceException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        CriteriaQuery<Transaction> cq = criteriaQuery();
        Root<Transaction> root = getRoot(cq);

        FiltersDecoder filtersDecoder = new FiltersDecoder(filters);

        List<Predicate> wherePredicate = wherePredicate(root, entity);

        //entityDAO.wherePredicate(root.get("terminal").get("path"), entity);
        for (FilterItem filterItem : filtersDecoder.getFilters()) {

            Predicate xPredicate;
            if (filterItem.isKeyEmpty()) {
                xPredicate = criteriaBuilder().disjunction();
            } else {
                xPredicate = criteriaBuilder().conjunction();
            }

            if (filterItem.hasKey("fromDate")) {
                try {
                    Date from = format.parse(filterItem.getValue());
                    xPredicate.getExpressions().add(criteriaBuilder().greaterThanOrEqualTo(root.<Date>get("localCreationTime"), from));
                    wherePredicate.add(xPredicate);
                    continue;
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (filterItem.hasKey("toDate")) {
                try {
                    Date to = format.parse(filterItem.getValue());
                    xPredicate.getExpressions().add(criteriaBuilder().lessThanOrEqualTo(root.<Date>get("localCreationTime"), to));
                    wherePredicate.add(xPredicate);
                    continue;
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(TransactionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (filterItem.isKeyEmpty() || filterItem.hasKey("disposition")) {
                xPredicate.getExpressions().add(criteriaBuilder().like(criteriaBuilder().
                        upper((Expression) root.get("disposition")), "%" + filterItem.getValue().toUpperCase() + "%"));
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("approvalCode")) {
                xPredicate.getExpressions().add(criteriaBuilder().like(criteriaBuilder().
                        upper((Expression) root.get("approvalCode")), "%" + filterItem.getValue().toUpperCase() + "%"));
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("approvalNumber")) {
                xPredicate.getExpressions().add(criteriaBuilder().like(criteriaBuilder().
                        upper((Expression) root.get("approvalNumber")), "%" + filterItem.getValue().toUpperCase() + "%"));
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("invoiceNumber")) {
                xPredicate.getExpressions().add(criteriaBuilder().like(criteriaBuilder().
                        upper((Expression) root.get("invoiceNumber")), "%" + filterItem.getValue().toUpperCase() + "%"));
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("host")) {
                xPredicate.getExpressions().add(criteriaBuilder().like(criteriaBuilder().
                        upper((Expression) root.get("host").get("name")), "%" + filterItem.getValue().toUpperCase() + "%"));
            }

            if (filterItem.isKeyEmpty() || filterItem.hasKey("status")) {
                int enumId = StatusEnum.getIdFromName(filterItem.getValue().toUpperCase());
                if (enumId != 0) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(criteriaBuilder().
                            upper((Expression) root.get("status")), enumId));
                }
            }
            if (filterItem.isKeyEmpty() || filterItem.getKey().equalsIgnoreCase("cardBrand")) {
                int enumId = CardBrandEnum.getIdFromName(filterItem.getValue().toUpperCase());
                if (enumId != 0) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(criteriaBuilder().
                            upper((Expression) root.get("cardBrand")), enumId));
                }
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("mode")) {
                int enumId = ModeEnum.getIdFromName(filterItem.getValue().toUpperCase());
                if (enumId != 0) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(criteriaBuilder().
                            upper((Expression) root.get("mode")), enumId));
                }
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("operation")) {
                int enumId = OperationEnum.getIdFromName(filterItem.getValue().toUpperCase());
                if (enumId != 0) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(criteriaBuilder().
                            upper((Expression) root.get("operation")), enumId));
                }
            }
            if (filterItem.isKeyEmpty() || filterItem.hasKey("entryMethod")) {
                int enumId = EntryMethodEnum.getIdFromName(filterItem.getValue().toUpperCase());
                if (enumId != 0) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(criteriaBuilder().
                            upper((Expression) root.get("entryMethod")), enumId));
                }
            }

            if (Utils.isNumeric(filterItem.getValue())) {
                BigDecimal filterAsNumber = new BigDecimal(filterItem.getValue());

                if (filterItem.isKeyEmpty() || filterItem.hasKey("amount")) {
                    Join<Transaction, TransactionTax> taxes = root.join("taxAmounts", JoinType.LEFT);
                    xPredicate.getExpressions().add(criteriaBuilder().equal(taxes.get("amount"), filterAsNumber));
                }
                if (filterItem.isKeyEmpty() || filterItem.hasKey("tipAmount")) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(root.get("tipAmount"), filterAsNumber));
                }
                if (filterItem.isKeyEmpty() || filterItem.hasKey("authorizedAmount")) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(root.get("authorizedAmount"),
                            filterAsNumber));
                }
                if (filterItem.isKeyEmpty() || filterItem.hasKey("totalAmount")) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(root.get("totalAmount"), filterAsNumber));
                }
                if (filterItem.isKeyEmpty() || filterItem.hasKey("sequenceNumber")) {
                    xPredicate.getExpressions().add(criteriaBuilder().equal(root.get("sequenceNumber").as(String.class),
                            filterAsNumber));
                }
            }
            wherePredicate.add(xPredicate);
        }

        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()]))).
                orderBy(criteriaBuilder().desc(root.get("localCreationTime")));

        return query(cq).setFirstResult(firstResult).setMaxResults(lastResult).getResultList();
    }

    @Override
    public Transaction findByTransactionId(UUID transactionId) throws PersistenceException {
        try {
            CriteriaQuery<Transaction> cq = criteriaQuery();
            Root<Transaction> root = cq.from(Transaction.class);
            root.fetch("terminal", JoinType.LEFT);
            root.fetch("client", JoinType.LEFT);
            root.fetch("device", JoinType.LEFT);
            root.fetch("originalTransaction", JoinType.LEFT);
            root.fetch("taxAmounts", JoinType.LEFT);
            root.fetch("account", JoinType.LEFT);
            cq.select(root);

            return (Transaction) query(cq.where(criteriaBuilder().equal(root.get("transactionId"), transactionId))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("findByTransactionId -> Transaction not found. ", e);
            return null;
        }
    }

    @Override
    public Transaction findByDateAndDevice(Timestamp time, Device device) throws PersistenceException {
        //TODO: ADD restriction  that the transaction can not be a reversal nor an advice
        try {

            CriteriaQuery<Transaction> cq = criteriaQuery();
            Root<Transaction> root = cq.from(Transaction.class);
            root.fetch("terminal", JoinType.LEFT);
            root.fetch("client", JoinType.LEFT);
            root.fetch("device", JoinType.LEFT);
            root.fetch("originalTransaction", JoinType.LEFT);
            root.fetch("taxAmounts", JoinType.LEFT);
            root.fetch("account", JoinType.LEFT);
            cq.select(root);

            Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("device"), device));
            predicate.getExpressions().add(criteriaBuilder().equal(root.get("localCreationTime"), time));
            cq.where(predicate);

            return (Transaction) query(cq).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("findByDateAndDevice -> Transaction not found. ", e);
            return null;
        }
    }

    @Override
    public List<Transaction> listLatestTransactions(Entity entity, int count) throws PersistenceException {

        CriteriaQuery<Transaction> cq = criteriaQuery();
        Root<Transaction> root = getRoot(cq);
        cq.where(criteriaBuilder().and(wherePredicate(root, entity).toArray(new Predicate[wherePredicate(root, entity).size()]))).orderBy(criteriaBuilder().desc(root.get("id")));

        return query(cq).setMaxResults(count).getResultList();
    }

    @Override
    public List<Transaction> listLatestTransactionsHost(Entity entity, Host host, int count) throws PersistenceException {

        CriteriaQuery<Transaction> cq = criteriaQuery();
        Root<Transaction> root = getRoot(cq);

        List<Predicate> wherePredicate = wherePredicate(root, entity);
        wherePredicate.add(criteriaBuilder().equal(root.get("host"), host));
        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()]))).orderBy(criteriaBuilder().desc(root.get("id")));

        return query(cq).setMaxResults(count).getResultList();
    }

    @Override
    public Long getTotalTransactions(Entity entity) throws PersistenceException {

        CriteriaQuery<Long> cq = criteriaBuilder().createQuery(Long.class);
        Root<Transaction> root = cq.from(Transaction.class);
        cq.select(criteriaBuilder().count(root));
        cq.where(criteriaBuilder().and(wherePredicate(root, entity).toArray(new Predicate[wherePredicate(root, entity).size()])));

        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public TransactionData getInfoByOperation(Entity entity, OperationEnum operation, Date date) throws PersistenceException {

        CriteriaQuery<TransactionData> cq = criteriaBuilder().createQuery(TransactionData.class);
        Root root = cq.from(Transaction.class);
        Expression count = criteriaBuilder().count(root);
        Expression sum = criteriaBuilder().sum(root.get("authorizedAmount"));
        cq.multiselect(count, sum);

        List<Predicate> wherePredicate = wherePredicate(root, entity);
        wherePredicate.add(criteriaBuilder().equal(root.get("operation"), operation));

        if (date != null) {
            wherePredicate.add(criteriaBuilder().equal(criteriaBuilder().function("date", java.sql.Date.class, root.get("localCreationTime")), date));
        }
        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public TransactionData getInfoByOperation(Entity entity, OperationEnum operation) throws PersistenceException {
        return getInfoByOperation(entity, operation, null);
    }

    @Override
    public TransactionData getMonthlyInfoByOperation(Entity entity, OperationEnum operation, Date date) throws PersistenceException {

        CriteriaQuery<TransactionData> cq = criteriaBuilder().createQuery(TransactionData.class);
        Root root = cq.from(Transaction.class);
        Expression<Date> date_compare = criteriaBuilder().literal(date);
        cq.multiselect(criteriaBuilder().count(root), criteriaBuilder().sum(root.get("authorizedAmount")));

        List<Predicate> wherePredicate = wherePredicate(root, entity);
        wherePredicate.add(criteriaBuilder().equal(root.get("operation"), operation));
        wherePredicate.add(criteriaBuilder().and(criteriaBuilder().equal(criteriaBuilder().function("year", java.sql.Date.class, root.get("localCreationTime")), criteriaBuilder().function("year", java.sql.Date.class, date_compare)), criteriaBuilder().equal(criteriaBuilder().function("month", java.sql.Date.class, root.get("localCreationTime")), criteriaBuilder().function("month", java.sql.Date.class, date_compare))));
        cq.where(criteriaBuilder().and(wherePredicate.toArray(new Predicate[wherePredicate.size()])));

        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public TransactionSpeed getTransactionSpeed() throws PersistenceException {

        CriteriaQuery<TransactionSpeed> cq = criteriaBuilder().createQuery(TransactionSpeed.class);
        Root root = cq.from(TimePerformance.class);

        Expression sumTemp = criteriaBuilder().sum(root.get("rqstInFront"), root.get("rqstInCoreQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rqstInCore"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rqstInHostQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rqstInHost"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rqstInProcessor"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rspsInHost"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rspsInHostQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rspsInCore"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rspsInCoreQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, root.get("rspsInFront"));

        Expression sum = criteriaBuilder().avg(sumTemp);
        Expression max = criteriaBuilder().max(sumTemp);
        Expression min = criteriaBuilder().min(sumTemp);

        cq.multiselect(sum.alias("average"), min.alias("min"), max.alias("max"));
        TransactionSpeed result = getEntityManager().createQuery(cq).getSingleResult();

        return result;
    }

    @Override
    public TransactionSpeed getTransactionSpeed(Host host) throws PersistenceException {

        CriteriaQuery<TransactionSpeed> cq = criteriaBuilder().createQuery(TransactionSpeed.class);
        Root<Transaction> transaction = cq.from(Transaction.class);
        Join<Transaction, TimePerformance> join = transaction.join("timePerformance");

        Expression sumTemp = criteriaBuilder().sum((Expression) join.get("rqstInFront"), (Expression) join.get("rqstInCoreQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rqstInCore"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rqstInHostQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rqstInHost"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rqstInProcessor"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rspsInHost"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rspsInHostQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rspsInCore"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rspsInCoreQueue"));
        sumTemp = criteriaBuilder().sum(sumTemp, (Expression) join.get("rspsInFront"));

        Expression avg = criteriaBuilder().avg(sumTemp);
        Expression max = criteriaBuilder().max(sumTemp);
        Expression min = criteriaBuilder().min(sumTemp);

        Predicate predicate = criteriaBuilder().equal(transaction.get("host"), host);
        cq.multiselect(avg.alias("average"), min.alias("min"), max.alias("max")).where(predicate);

        TransactionSpeed result = getEntityManager().createQuery(cq).getSingleResult();

        return result;
    }

    @Override
    public Transaction findWithAccountInfo(UUID transactionId) throws PersistenceException {
        Transaction transaction = findByTransactionId(transactionId);

        // Initializing Account
        transaction.getAccount().getData();
        //Getting Account object from Account proxy
        Account account = (Account) getEntityManager().unwrap(SessionImplementor.class).
                getPersistenceContext().unproxy(transaction.getAccount());
        // Attach  Account object
        transaction.setAccount(account);

        return transaction;
    }
    
    @Override
    public Transaction findWithAccountInfo(String merchantTransactionId) throws PersistenceException {
        Transaction transaction = findByMerchantTransactionId(merchantTransactionId);

        // Initializing Account
        transaction.getAccount().getData();
        //Getting Account object from Account proxy
        Account account = (Account) getEntityManager().unwrap(SessionImplementor.class).
                getPersistenceContext().unproxy(transaction.getAccount());
        // Attach  Account object
        transaction.setAccount(account);

        return transaction;
    }

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    @Override
    public BigDecimal getRemainingRefundableAmount(UUID id) {

        // TODO Improve with JPA once subqueries in the SELECT clause is supported.
        Transaction transaction = findByTransactionId(id);

        CriteriaQuery<BigDecimal> cq = criteriaBuilder().createQuery(BigDecimal.class);
        Root root = cq.from(Transaction.class);

        Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("operation"), 
                OperationEnum.REFUND), criteriaBuilder().equal(root.get("originalTransaction"), transaction.getId()));

        Expression<BigDecimal> sum = criteriaBuilder().sum(root.get("authorizedAmount"));
        cq.select(sum);
        cq.where(predicate);

        BigDecimal totalRefunded = getEntityManager().createQuery(cq).getSingleResult();

        return transaction.getAuthorizedAmount().subtract(totalRefunded != null ? totalRefunded : new BigDecimal(BigInteger.ZERO));
    }

    /**
     *
     * @param id
     * @return
     * @throws PersistenceException
     */
    @Override
    public BigDecimal getRemainingVoidableAmount(UUID id) {

        // TODO Improve with JPA once subqueries in the SELECT clause is supported.
        Transaction transaction = findByTransactionId(id);

        CriteriaQuery<BigDecimal> cq = criteriaBuilder().createQuery(BigDecimal.class);
        Root root = cq.from(Transaction.class);

        Predicate predicate = criteriaBuilder().and(criteriaBuilder().equal(root.get("operation"), 
                OperationEnum.VOID), criteriaBuilder().equal(root.get("originalTransaction"), transaction.getId()));

        Expression<BigDecimal> sum = criteriaBuilder().sum(root.get("authorizedAmount"));
        cq.select(sum);
        cq.where(predicate);

        BigDecimal totalVoided = getEntityManager().createQuery(cq).getSingleResult();

        return transaction.getAuthorizedAmount().subtract(totalVoided != null ? totalVoided : new BigDecimal(BigInteger.ZERO));
    }

    private List<Predicate> wherePredicate(Root root, Entity entity) {
        List<Predicate> wherePredicate = new ArrayList<>();
        Predicate entPredicate = criteriaBuilder().disjunction();
        entPredicate.getExpressions().add(criteriaBuilder().equal(root.get("terminal").<Long>get("id"), entity.getId()));
        entPredicate.getExpressions().add(criteriaBuilder().like(root.get("terminal").<String>get("path"), 
                "%-" + String.valueOf(entity.getId()) + "-%"));
        wherePredicate.add(entPredicate);
        return wherePredicate;
    }

    @Override
    public Transaction findByMerchantTransactionId(String merchantTransactionId) throws PersistenceException {
        try {
            CriteriaQuery<Transaction> cq = criteriaQuery();
            Root<Transaction> root = cq.from(Transaction.class);
            root.fetch("terminal", JoinType.LEFT);
            root.fetch("client", JoinType.LEFT);
            root.fetch("device", JoinType.LEFT);
            root.fetch("originalTransaction", JoinType.LEFT);
            root.fetch("taxAmounts", JoinType.LEFT);
            root.fetch("account", JoinType.LEFT);
            cq.select(root);

            return (Transaction) query(cq.where(criteriaBuilder().equal(root.get("merchantTransactionId"), merchantTransactionId))).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("findByTransactionId -> Transaction not found. ", e);
            return null;
        }
    }

}
