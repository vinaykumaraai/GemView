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
package com.luretechnologies.server.data.display.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.performance.PerformanceTiming;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * The Processor Request Class
 *
 *
 */
public class ProcessorRequest implements Serializable {

    // For Processor Purposes
    @FieldNotBlank
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ApiModelProperty(value = "The processor name.")
    private String processorName;

    @FieldNotBlank
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ApiModelProperty(value = "The transaction message.")
    private String transactionMessage;

    // For Debugging Purposes
    @JsonIgnore
    private PerformanceTiming performanceTiming = new PerformanceTiming();

    /**
     *
     */
    public ProcessorRequest() {
    }

    /**
     * @return the processorName
     */
    public String getProcessorName() {
        return processorName;
    }

    /**
     * @param processorName the processorName to set
     */
    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    /**
     * @return the transactionMessage
     */
    public String getTransactionMessage() {
        return transactionMessage;
    }

    /**
     * @param transactionMessage the transactionMessage to set
     */
    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    /**
     *
     * @return
     */
    public PerformanceTiming getPerformanceTiming() {
        return performanceTiming;
    }

    /**
     *
     * @param performanceTiming
     */
    public void setPerformanceTiming(PerformanceTiming performanceTiming) {
        this.performanceTiming = performanceTiming;
    }
}
