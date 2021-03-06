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
package com.luretechnologies.server.data.model.tms;

/**
 *
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.server.data.model.Terminal;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * /**
 * Stores relation between terminals and applications.
 */
@Entity
@Table(name = "Application_Package", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"terminal", "application"})})
public class ApplicationPackage implements Serializable {

    /**
     *
     */
    public ApplicationPackage() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne(targetEntity = Terminal.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal", referencedColumnName = "id", nullable = false)})
    private Terminal terminal;

    @ManyToOne(targetEntity = Application.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "application", referencedColumnName = "id", nullable = false)})
    private Application application;

    @ManyToOne(targetEntity = PaymentProfile.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_profile", referencedColumnName = "id", nullable = false)})
    private PaymentProfile paymentProfile;

    @OneToMany(mappedBy = "applicationPackage", targetEntity = TerminalApplicationParameter.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy
    private Set<TerminalApplicationParameter> parameters = new HashSet<>();

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
     *
     * @param value
     */
    public void setPaymentProfile(PaymentProfile value) {
        this.paymentProfile = value;
    }

    /**
     *
     * @return
     */
    public PaymentProfile getPaymentProfile() {
        return paymentProfile;
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
     *
     * @param value
     */
    public void setApplication(Application value) {
        this.application = value;
    }

    /**
     *
     * @return
     */
    public Application getApplication() {
        return application;
    }

    /**
     *
     * @param value
     */
    public void setParameters(Set<TerminalApplicationParameter> value) {
        this.parameters = value;
    }

    /**
     *
     * @return
     */
    public Set<TerminalApplicationParameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
