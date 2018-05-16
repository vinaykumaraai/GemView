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

import com.luretechnologies.server.data.model.tms.PaymentProduct;
import com.luretechnologies.server.data.model.tms.PaymentProductProfile;
import java.sql.Timestamp;

/**
 *
 *
 * @author developer
 */
public class PaymentProductDisplay {

    private final int version;

    private final String aid;

    private final String name;

    private final String description;

    private final String appVersion;

    private final String floorLimit;

    private final String tprs;

    private final String tvbrs;

    private final String mtpbrs;

    private final String partialSelection;

    private final String terminalAppLabel;

    private final String fallbackControl;

    private final Timestamp createdAt;

    private final Timestamp updatedAt;

    /**
     *
     * @param paymentProductProfile
     */
    public PaymentProductDisplay(PaymentProductProfile paymentProductProfile) {
        PaymentProduct paymentProduct = paymentProductProfile.getPaymentProduct();

        this.aid = paymentProduct.getAid();
        this.name = paymentProduct.getName();
        this.description = paymentProduct.getDescription();

        this.appVersion = paymentProductProfile.getAppVersion();
        this.fallbackControl = paymentProductProfile.getFallbackControl();
        this.floorLimit = paymentProductProfile.getFloorLimit();
        this.mtpbrs = paymentProductProfile.getMtpbrs();
        this.tprs = paymentProductProfile.getTprs();
        this.tvbrs = paymentProductProfile.getTvbrs();
        this.partialSelection = paymentProductProfile.getPartialSelection();
        this.terminalAppLabel = paymentProductProfile.getTerminalAppLabel();
        this.version = paymentProductProfile.getVersion();

        this.createdAt = null; // TODO paymentProductProfile.getCreatedAt();
        this.updatedAt = null; // TODO paymentProductProfile.getUpdatedAt();
    }

    /**
     * Represents the Application Identifier as defined by EMV and assigned by
     * ISO to identify EMV card applications.
     *
     * @return
     */
    public String getAid() {
        return aid;
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
    public String getAppVersion() {
        return appVersion;
    }

    /**
     *
     * @return
     */
    public String getFloorLimit() {
        return floorLimit;
    }

    /**
     *
     * @return
     */
    public String getTprs() {
        return tprs;
    }

    /**
     *
     * @return
     */
    public String getTvbrs() {
        return tvbrs;
    }

    /**
     *
     * @return
     */
    public String getMtpbrs() {
        return mtpbrs;
    }

    /**
     *
     * @return
     */
    public String getPartialSelection() {
        return partialSelection;
    }

    /**
     *
     * @return
     */
    public String getTerminalAppLabel() {
        return terminalAppLabel;
    }

    /**
     *
     * @return
     */
    public String getFallbackControl() {
        return fallbackControl;
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

}
