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
package com.luretechnologies.server.data.model;

import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores session information.
 */
@Entity
@Table(name = "Session")
public class Session implements Serializable {

    /**
     *
     */
    public Session() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "user", nullable = false)})
    private User user;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 250, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "token", nullable = false, length = 250)
    private String token;

    @Column(name = "last_updated", nullable = false)
    private Timestamp lastUpdated;

    @Digits(integer = 11, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "session_info", nullable = true, length = 11)
    private int sessionInfo;

    private void setId(long value) {
        this.id = value;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param value
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param value
     */
    public void setLastUpdated(Timestamp value) {
        this.lastUpdated = value;
    }

    /**
     *
     * @return
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     *
     * @param value
     */
    public void setSessionInfo(int value) {
        this.sessionInfo = value;
    }

    /**
     *
     * @return
     */
    public int getSessionInfo() {
        return sessionInfo;
    }

    /**
     *
     * @param value
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
