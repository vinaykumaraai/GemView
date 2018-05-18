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
package com.luretechnologies.server.hibernate.enums;

import com.luretechnologies.common.enums.utils.PersistentEnum;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 *
 *
 * @param <T>
 */
public abstract class PersistentEnumUserType<T extends PersistentEnum> implements UserType {

    /**
     *
     * @param cached
     * @param owner
     * @return
     * @throws HibernateException
     */
    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    /**
     *
     * @param value
     * @return
     * @throws HibernateException
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /**
     *
     * @param value
     * @return
     * @throws HibernateException
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     * @throws HibernateException
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    /**
     *
     * @param x
     * @return
     * @throws HibernateException
     */
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     *
     * @param rs
     * @param aStrings
     * @param si
     * @param o
     * @return
     * @throws HibernateException
     * @throws SQLException
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] aStrings, SessionImplementor si, Object o) throws HibernateException, SQLException {
        int id = rs.getInt(aStrings[0]);
        if (rs.wasNull()) {
            return null;
        }
        for (PersistentEnum value : returnedClass().getEnumConstants()) {
            if (id == value.getId()) {
                return value;
            }
        }
        throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
    }

    /**
     *
     * @param ps
     * @param value
     * @param index
     * @param si
     * @throws HibernateException
     * @throws SQLException
     */
    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int index, SessionImplementor si) throws HibernateException, SQLException {
        if (value == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, ((PersistentEnum) value).getId());
        }
    }

    /**
     *
     * @param original
     * @param target
     * @param owner
     * @return
     * @throws HibernateException
     */
    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    /**
     *
     * @return
     */
    @Override
    public abstract Class<T> returnedClass();

    /**
     *
     * @return
     */
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.INTEGER};
    }

}
