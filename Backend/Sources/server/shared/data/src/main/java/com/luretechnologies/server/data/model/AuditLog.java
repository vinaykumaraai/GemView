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

import com.luretechnologies.common.enums.ActionEnum;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.jpa.converters.ActionEnumConverter;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Store system logs.
 */
@Entity
@Table(name = "Audit_Log")
public class AuditLog implements Serializable {

    /**
     *
     */
    public AuditLog() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Terminal.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal", referencedColumnName = "id")})
    private Terminal terminal;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "user", referencedColumnName = "id")})
    private User user;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "action", nullable = false, length = 2)
    @Convert(converter = ActionEnumConverter.class)
    private ActionEnum action;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_DESC, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "details", nullable = false, length = 255)
    private String details;

    @Column(name = "occurred_at", nullable = false, insertable = false, updatable = false)
    private Timestamp occurredAt;

    /**
     * Database identification.
     */
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Database identification.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Log details.
     *
     * @param value
     */
    public void setDetails(String value) {
        this.details = value;
    }

    /**
     * Log details.
     *
     * @return
     */
    public String getDetails() {
        return details;
    }

    /**
     * Log's creation date.
     *
     * @param value
     */
    public void setOccurredAt(Timestamp value) {
        this.occurredAt = value;
    }

    /**
     * Log's creation date.
     *
     * @return
     */
    public Timestamp getOccurredAt() {
        return occurredAt;
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

    /**
     *
     * @param value
     */
    public void setTerminal(Terminal value) {
        this.terminal = value;
    }

    /**
     *
     * @return
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     * ActionEnum's category.
     *
     * @param value
     */
    public void setAction(ActionEnum value) {
        this.action = value;
    }

    /**
     * ActionEnum's category.
     *
     * @return
     */
    public ActionEnum getAction() {
        return action;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
