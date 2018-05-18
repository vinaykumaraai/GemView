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
package com.luretechnologies.server.data.display.payment.dashboard;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 *
 * @author developer
 */
public class SalesData implements Serializable {

    @ApiModelProperty(value = "The total of sales.")
    private Long saleCount = 0l;

    @ApiModelProperty(value = "The total of voids.")
    private Long voidCount = 0l;

    @ApiModelProperty(value = "The total amount of sales.")
    private BigDecimal saleAmount = BigDecimal.ZERO;

    /**
     *
     * @param saleCount
     * @param voidCount
     * @param amount
     */
    public SalesData(Long saleCount, Long voidCount, BigDecimal amount) {
        if (saleCount != null) {
            this.saleCount = saleCount;
        }
        if (voidCount != null) {
            this.voidCount = voidCount;
        }
        if (amount != null) {
            this.saleAmount = amount;
        }
    }

    /**
     *
     */
    public SalesData() {
        this.saleCount = 0l;
        this.voidCount = 0l;
        this.saleAmount = BigDecimal.ZERO;
    }

    /**
     * @return the amount
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * @param amount the amount to set
     */
    public void setSaleAmount(BigDecimal amount) {
        this.saleAmount = amount;
    }

    /**
     * @return the saleCount
     */
    public Long getSaleCount() {
        return saleCount;
    }

    /**
     * @param saleCount the saleCount to set
     */
    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * @return the voidCount
     */
    public Long getVoidCount() {
        return voidCount;
    }

    /**
     * @param voidCount the voidCount to set
     */
    public void setVoidCount(Long voidCount) {
        this.voidCount = voidCount;
    }

}
