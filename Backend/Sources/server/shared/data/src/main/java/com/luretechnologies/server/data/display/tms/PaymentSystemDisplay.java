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
package com.luretechnologies.server.data.display.tms;

import com.luretechnologies.server.data.model.tms.CAKey;
import com.luretechnologies.server.data.model.tms.PaymentSystem;
import com.luretechnologies.server.data.model.tms.PaymentSystemProfile;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author developer
 */
public class PaymentSystemDisplay {

    private final String rid;

    private final String tacDefault;

    private final String name;

    private final String description;

    private final String tacDenial;

    private final String tacOnline;

    private final String tcc;

    private final String defaultTDOL;

    private final String defaultDDOL;

    private final Timestamp createdAt;

    private final Timestamp updatedAt;

    private List<CAKey> caKeys = new ArrayList<>();

    private List<PaymentProductDisplay> paymentProducts = new ArrayList<>();

    private final int version;

    /**
     *
     * @param paymentSystemProfile
     */
    public PaymentSystemDisplay(PaymentSystemProfile paymentSystemProfile) {
        PaymentSystem paymentSystem = paymentSystemProfile.getPaymentSystem();

        this.rid = paymentSystem.getRid();
        this.name = paymentSystem.getName();
        this.description = paymentSystem.getDescription();

        this.tacDefault = paymentSystemProfile.getTacDefault();
        this.tacOnline = paymentSystemProfile.getTacOnline();
        this.tacDenial = paymentSystemProfile.getTacDenial();
        this.defaultDDOL = paymentSystemProfile.getDefaultDDOL();
        this.defaultTDOL = paymentSystemProfile.getDefaultTDOL();
        this.tcc = paymentSystemProfile.getTcc();
        this.version = paymentSystemProfile.getVersion();

        this.createdAt = paymentSystemProfile.getCreatedAt();
        this.updatedAt = paymentSystemProfile.getUpdatedAt();
    }

    /**
     * Represents the Registered Identifier unique per Payment System and
     * assigned by ISO.
     *
     * @return
     */
    public String getRid() {
        return rid;
    }

    /**
     *
     * @param value
     */
    public void setCAKeys(List<CAKey> value) {
        this.caKeys = value;
    }

    /**
     *
     * @return
     */
    public List<CAKey> getCAKeys() {
        return caKeys;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @return
     */
    public String getTacDefault() {
        return tacDefault;
    }

    /**
     *
     * @return
     */
    public String getTacDenial() {
        return tacDenial;
    }

    /**
     *
     * @return
     */
    public String getTacOnline() {
        return tacOnline;
    }

    /**
     *
     * @return
     */
    public String getTcc() {
        return tcc;
    }

    /**
     *
     * @return
     */
    public String getDefaultTDOL() {
        return defaultTDOL;
    }

    /**
     *
     * @return
     */
    public String getDefaultDDOL() {
        return defaultDDOL;
    }

    /**
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     *
     * @return
     */
    public List<PaymentProductDisplay> getPaymentProducts() {
        return paymentProducts;
    }

    /**
     *
     * @param paymentProducts
     */
    public void setPaymentProducts(List<PaymentProductDisplay> paymentProducts) {
        this.paymentProducts = paymentProducts;
    }

}
