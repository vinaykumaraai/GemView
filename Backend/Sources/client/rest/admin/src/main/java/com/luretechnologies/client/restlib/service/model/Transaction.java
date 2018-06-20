/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import com.luretechnologies.common.enums.CardBrandEnum;
import com.luretechnologies.common.enums.CurrencyEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.common.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class Transaction {

    private final Long id = null;
    private final String transactionId = null;

    private final Host host = null;
    private final ModeEnum mode = null;
    private final OperationEnum operation = null;
    private final BigDecimal amount = null;
    private final BigDecimal tipAmount = null;
    private final BigDecimal cashBackAmount = null;
    private final List<TransactionTax> taxAmounts = new ArrayList<>();
    private final BigDecimal totalAmount = null;
    private final BigDecimal authorizedAmount = null;
    private final EntryMethodEnum entryMethod = null;
    private final CurrencyEnum currency = null;
    private final StatusEnum status = null;
    private final String disposition = null;
    private final String approvalCode = null;
    private final String approvalNumber = null;
    private final String accountSuffix = null;
    private final String invoiceNumber = null;
    private final CardBrandEnum cardBrand = null;
    private final Integer batchNumber = null;
    private final Integer sequenceNumber = null;
    private final String retrievalData = null;
    private final Boolean finalized = null;
    private final Boolean voided = null;
    private final Timestamp localCreationTime = null;
    private final Terminal terminal = null;
    private final Device device = null;
    private final Transaction originalTransaction = null;
    private final EmvData emvData = null;
    private final Timestamp createdAt = null;

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("transactionId")
    public String getTransactionId() {
        return transactionId;
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
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("taxAmounts")
    public List<TransactionTax> getTaxAmounts() {
        return taxAmounts;
    }

    /**
     *
     * @return
     */
    public BigDecimal getCashBackAmount() {
        return cashBackAmount;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("tipAmount")
    public BigDecimal getTipAmount() {
        return tipAmount;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("totalAmount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("authorizedAmount")
    public BigDecimal getAuthorizedAmount() {
        return authorizedAmount;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("entryMethod")
    public EntryMethodEnum getEntryMethod() {
        return entryMethod;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("currency")
    public CurrencyEnum getCurrency() {
        return currency;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("disposition")
    public String getDisposition() {
        return disposition;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("approvalCode")
    public String getApprovalCode() {
        return approvalCode;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("approvalNumber")
    public String getApprovalNumber() {
        return approvalNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("retrievalData")
    public String getRetrievalData() {
        return retrievalData;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("sequenceNumber")
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("status")
    public StatusEnum getStatus() {
        return status;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("finalized")
    public Boolean getFinalized() {
        return finalized;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("voided")
    public Boolean getVoided() {
        return voided;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("accountSuffix")
    public String getAccountSuffix() {
        return accountSuffix;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("batchNumber")
    public Integer getBatchNumber() {
        return batchNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardBrand")
    public CardBrandEnum getCardBrand() {
        return cardBrand;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("terminal")
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     *
     * @return
     */
    public String getTerminalName() {
        return terminal.getName();
    }

    /**
     *
     * @return
     */
    public String getTerminalDescription() {
        return terminal.getDescription();
    }

    /**
     *
     * @return
     */
    public String getTerminalSerialNumber() {
        return terminal.getSerialNumber();
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("host")
    public Host getHost() {
        return host;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("mode")
    public ModeEnum getMode() {
        return mode;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("operation")
    public OperationEnum getOperation() {
        return operation;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("originalTransaction")
    public Transaction getOriginalTransaction() {
        return originalTransaction;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("localCreationTime")
    public Timestamp getLocalCreationTime() {
        return localCreationTime;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("emvData")
    public EmvData getEmvData() {
        return emvData;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("device")
    public Device getDevice() {
        return device;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("invoiceNumber")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("createdAt")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Transaction {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    transactionId: ").append(StringUtil.toIndentedString(transactionId)).append("\n");
        sb.append("    taxAmounts: ").append(StringUtil.toIndentedString(taxAmounts)).append("\n");
        sb.append("    tipAmount: ").append(StringUtil.toIndentedString(tipAmount)).append("\n");
        sb.append("    totalAmount: ").append(StringUtil.toIndentedString(totalAmount)).append("\n");
        sb.append("    authorizedAmount: ").append(StringUtil.toIndentedString(authorizedAmount)).append("\n");
        sb.append("    entryMethod: ").append(StringUtil.toIndentedString(entryMethod)).append("\n");
        sb.append("    currency: ").append(StringUtil.toIndentedString(currency)).append("\n");
        sb.append("    disposition: ").append(StringUtil.toIndentedString(disposition)).append("\n");
        sb.append("    approvalCode: ").append(StringUtil.toIndentedString(approvalCode)).append("\n");
        sb.append("    approvalNumber: ").append(StringUtil.toIndentedString(approvalNumber)).append("\n");
        sb.append("    retrievalData: ").append(StringUtil.toIndentedString(retrievalData)).append("\n");
        sb.append("    sequenceNumber: ").append(StringUtil.toIndentedString(sequenceNumber)).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
        sb.append("    finalized: ").append(StringUtil.toIndentedString(finalized)).append("\n");
        sb.append("    voided: ").append(StringUtil.toIndentedString(voided)).append("\n");
        sb.append("    accountSuffix: ").append(StringUtil.toIndentedString(accountSuffix)).append("\n");
        sb.append("    batchNumber: ").append(StringUtil.toIndentedString(batchNumber)).append("\n");
        sb.append("    cardBrand: ").append(StringUtil.toIndentedString(cardBrand)).append("\n");
        sb.append("    terminal: ").append(StringUtil.toIndentedString(terminal)).append("\n");
        sb.append("    host: ").append(StringUtil.toIndentedString(host)).append("\n");
        sb.append("    mode: ").append(StringUtil.toIndentedString(mode)).append("\n");
        sb.append("    operation: ").append(StringUtil.toIndentedString(operation)).append("\n");
        sb.append("    originalTransaction: ").append(StringUtil.toIndentedString(originalTransaction)).append("\n");
        sb.append("    localCreationTime: ").append(StringUtil.toIndentedString(localCreationTime)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
