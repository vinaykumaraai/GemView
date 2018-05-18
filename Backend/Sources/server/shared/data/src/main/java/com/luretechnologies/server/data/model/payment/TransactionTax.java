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
package com.luretechnologies.server.data.model.payment;

/**
 *
 *
 */
import com.luretechnologies.common.enums.TaxTypeEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.jpa.converters.TaxTypeEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author developer
 */
@Entity
@Table(name = "Transaction_Tax")
public class TransactionTax implements Serializable {

    /**
     *
     */
    public TransactionTax() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @Column(name = "tax", nullable = true, unique = true, length = 2)
    @Convert(converter = TaxTypeEnumConverter.class)
    @ApiModelProperty(value = "The tax type.")
    private TaxTypeEnum type;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @DecimalMin(value = "0", message = Messages.INVALID_DATA_ENTRY)
    @DecimalMax(value = "999999.99", message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @ApiModelProperty(value = "The tax amount.", required = true)
    private BigDecimal amount = BigDecimal.ZERO;

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
    public void setType(TaxTypeEnum value) {
        this.type = value;
    }

    /**
     *
     * @return
     */
    public TaxTypeEnum getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
