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
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.server.data.dao.BaseDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import static java.util.regex.Pattern.quote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 *
 * @param <T>
 * @param <I>
 */
public abstract class BaseDAOImpl<T, I extends Serializable> implements BaseDAO<T, I> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> type;
    private Root<T> root;

    /**
     *
     */
    protected BaseDAOImpl() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     *
     * @return
     */
    public Class<T> getType() {
        return type;
    }

    /**
     *
     * @return
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *
     * @return
     */
    protected CriteriaBuilder criteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    /**
     *
     * @return
     */
    protected CriteriaQuery criteriaQuery() {
        return criteriaBuilder().createQuery(type);
    }

    /**
     *
     * @param criteriaQuery
     * @return
     */
    protected Root<T> getRoot(CriteriaQuery criteriaQuery) {
        root = criteriaQuery.from(type);
        return root;
    }

    /**
     *
     * @return
     */
    protected CriteriaQuery criteriaQueryComplex() {
        CriteriaQuery criteriaQuery = criteriaQuery();
        root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    /**
     *
     * @param alias
     *
     * @return
     */
    protected CriteriaQuery criteriaQueryComplex(String alias) {
        CriteriaQuery criteriaQuery = criteriaQuery();
        root = criteriaQuery.from(type);
        root.fetch(alias, JoinType.INNER);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    /**
     *
     * @param alias
     * @param joinType
     *
     * @return
     */
    protected CriteriaQuery criteriaQueryComplex(String alias, JoinType joinType) {
        CriteriaQuery criteriaQuery = criteriaQuery();
        root = criteriaQuery.from(type);
        root.fetch(alias, joinType);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    /**
     *
     * @param clazz
     * @return
     */
    protected CriteriaQuery criteriaQueryComplex(Class clazz) {
        CriteriaQuery criteriaQuery = criteriaBuilder().createQuery(clazz);
        root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    /**
     *
     * @param clazz
     * @param alias
     *
     * @return
     */
    protected CriteriaQuery criteriaQueryComplex(Class clazz, String alias) {
        CriteriaQuery criteriaQuery = criteriaBuilder().createQuery(clazz);
        root = criteriaQuery.from(clazz);
        root.fetch(alias, JoinType.INNER);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    /**
     *
     * @param clazz
     * @param alias
     * @param joinType
     *
     * @return
     */
    protected CriteriaQuery criteriaQueryComplex(Class clazz, String alias, JoinType joinType) {
        CriteriaQuery criteriaQuery = criteriaBuilder().createQuery(clazz);
        root = criteriaQuery.from(clazz);
        root.fetch(alias, joinType);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    /**
     *
     * @param criteriaQuery
     * @return
     */
    protected Query query(CriteriaQuery criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

    /**
     *
     * @param query
     * @return
     */
    protected Query query(String query) {
        return entityManager.createQuery(query);
    }

    @Override
    public void persist(T obj) {
        entityManager.persist(obj);
    }

    @Override
    public T merge(T obj) {
        return (T) entityManager.merge(obj);
    }

    @Override
    public void delete(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public void refresh(T object) {
        entityManager.refresh(object);
    }

    @Override
    public void delete(I id) {
        T entity = findById(id);
        delete(entity);
    }

    @Override
    public List<T> list() {
        return query(criteriaQueryComplex().orderBy(criteriaBuilder().asc(root.get("id")))).getResultList();
    }

    @Override
    public List<T> list(String alias) {
        return query(criteriaQueryComplex(alias).orderBy(criteriaBuilder().asc(root.get("id")))).getResultList();
    }

    @Override
    public T findById(I id) {
        T object = (T) entityManager.find(type, id);

        if (object == null) {
            throw new ObjectRetrievalFailureException(type.getName(), id);
        }

        return object;
    }

    @Override
    public Query findByProperty(String property, Object value) {
        return query(criteriaQueryComplex().where(criteriaBuilder().equal(root.get(property), value)));
    }

    @Override
    public Query findByProperty(String property, Object value, String alias) {
        return query(criteriaQueryComplex(alias).where(criteriaBuilder().equal(root.get(property), value)));
    }

    @Override
    public Query findByProperty(String property, Object value, String alias, JoinType joinType) {
        return query(criteriaQueryComplex(alias, joinType).where(criteriaBuilder().equal(root.get(property), value)));
    }

    @Override
    public T getFirstResult() {

        List<T> list = list();

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @param type
     * @param id
     * @param fetchRelations
     * @return
     */
    public T findWithDepth(Class<T> type, Object id, String... fetchRelations) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);

        for (String relation : fetchRelations) {
            FetchParent<T, T> fetch = root;
            for (String pathSegment : relation.split(quote("."))) {
                fetch = fetch.fetch(pathSegment, JoinType.LEFT);
            }
        }

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        return getSingleOrNoneResult(entityManager.createQuery(criteriaQuery));
    }

    private <T> T getSingleOrNoneResult(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
}
