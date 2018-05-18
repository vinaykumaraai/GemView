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
import com.luretechnologies.server.common.Messages;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores MCC which represents the Merchant Category Code to be supported by the
 * terminals.
 */
@Entity
@Table(name = "MCC")
public class Mcc implements Serializable {

    /**
     *
     */
    public Mcc() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 4, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "mcc", nullable = false, length = 4)
    private String mcc;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "edited_description", nullable = false, length = 255)
    private String editedDescription;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "combined_description", nullable = false, length = 255)
    private String combinedDescription;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "usda_description", nullable = false, length = 255)
    private String usdaDescription;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "irs_description", nullable = false, length = 255)
    private String irsDescription;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "irs_reportable", nullable = false, length = 255)
    private String irsReportable;

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
     * @return
     */
    public String getMcc() {
        return mcc;
    }

    /**
     *
     * @param mcc
     */
    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    /**
     *
     * @return
     */
    public String getEditedDescription() {
        return editedDescription;
    }

    /**
     *
     * @param editedDescription
     */
    public void setEditedDescription(String editedDescription) {
        this.editedDescription = editedDescription;
    }

    /**
     *
     * @return
     */
    public String getCombinedDescription() {
        return combinedDescription;
    }

    /**
     *
     * @param combinedDescription
     */
    public void setCombinedDescription(String combinedDescription) {
        this.combinedDescription = combinedDescription;
    }

    /**
     *
     * @return
     */
    public String getUsdaDescription() {
        return usdaDescription;
    }

    /**
     *
     * @param usdaDescription
     */
    public void setUsdaDescription(String usdaDescription) {
        this.usdaDescription = usdaDescription;
    }

    /**
     *
     * @return
     */
    public String getIrsDescription() {
        return irsDescription;
    }

    /**
     *
     * @param irsDescription
     */
    public void setIrsDescription(String irsDescription) {
        this.irsDescription = irsDescription;
    }

    /**
     *
     * @return
     */
    public String isIrsReportable() {
        return irsReportable;
    }

    /**
     *
     * @param irsReportable
     */
    public void setIrsReportable(String irsReportable) {
        this.irsReportable = irsReportable;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
