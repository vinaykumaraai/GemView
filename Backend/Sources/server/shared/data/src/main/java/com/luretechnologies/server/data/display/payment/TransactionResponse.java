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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luretechnologies.common.enums.AVSResultEnum;
import com.luretechnologies.common.enums.CVVResultEnum;
import com.luretechnologies.common.enums.CardBrandEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.StatusEnum;
import com.luretechnologies.server.common.utils.StringUtils;
import com.luretechnologies.server.data.model.payment.Transaction;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Transaction Response Class
 *
 *
 */
public class TransactionResponse implements Serializable {

    @ApiModelProperty(value = "The transaction identifier.")
    private UUID transactionId;

    @ApiModelProperty(value = "The transaction status.")
    private StatusEnum status;

    @ApiModelProperty(value = "The transaction disposition.")
    private String disposition;

    @ApiModelProperty(value = "The transaction approval code.")
    private String approvalCode;

    @ApiModelProperty(value = "The transaction approval number.")
    private String approvalNumber;

    @ApiModelProperty(value = "The total authorized amount.")
    private String authorizedAmount;

    @ApiModelProperty(value = "The transaction batch number.")
    private int batchNumber;

    @ApiModelProperty(value = "The transaction sequence number.")
    private int sequenceNumber;

    @ApiModelProperty(value = "The entry method used to gather card information.")
    private EntryMethodEnum entryMethod;

    @ApiModelProperty(value = "The processor's response to the card verification value (CVV) provided.")
    private CVVResultEnum cvvResult;

    @ApiModelProperty(value = "The processor's response to the address verification system (AVS) provided.")
    private AVSResultEnum avsResult;

    @ApiModelProperty(value = "The processor's response data.")
    private String retrievalData;

    @ApiModelProperty(value = "The TLV encoded EMV response data.")
    private String emvDataTLV;

    @ApiModelProperty(value = "EMV data.")
    private EmvData emvData;

    @ApiModelProperty(value = "The last four digits of the card used for the transaction.")
    private String cardLastFour;

    @ApiModelProperty(value = "The brand of the card used for the transaction.")
    private CardBrandEnum cardBrand;

    @ApiModelProperty(value = "Specifies if the transaction was partially authorized or not.")
    private boolean partialAuth;

    @JsonIgnore
    private String hostAdditionalInfo;

    @JsonIgnore
    TransactionRequest transactionRequest;

    @JsonIgnore
    Transaction transaction;

    @JsonIgnore
    private String hostResponse;
    
    @JsonIgnore
    List<HostSettingValue> hostSettings;

    /**
     *
     */
    public TransactionResponse() {
    }

    /**
     *
     * @return
     */
    public UUID getTransactionId() {
        return transactionId;
    }

    /**
     *
     * @param transactionId
     */
    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * @return
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getDisposition() {
        return disposition;
    }

    /**
     *
     * @param disposition
     */
    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    /**
     *
     * @return
     */
    public String getApprovalCode() {
        return approvalCode;
    }

    /**
     *
     * @param approvalCode
     */
    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    /**
     *
     * @return
     */
    public String getApprovalNumber() {
        return approvalNumber;
    }

    /**
     *
     * @param approvalNumber
     */
    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    /**
     *
     * @return
     */
    public String getAuthorizedAmount() {
        return authorizedAmount;
    }

    /**
     *
     * @param authorizedAmount
     */
    public void setAuthorizedAmount(String authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    /**
     *
     * @return
     */
    public int getBatchNumber() {
        return batchNumber;
    }

    /**
     *
     * @param batchNumber
     */
    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     *
     * @return
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     *
     * @param sequenceNumber
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     *
     * @return
     */
    public EntryMethodEnum getEntryMethod() {
        return entryMethod;
    }

    /**
     *
     * @param entryMethod
     */
    public void setEntryMethod(EntryMethodEnum entryMethod) {
        this.entryMethod = entryMethod;
    }

    /**
     *
     * @return
     */
    public CVVResultEnum getCvvResult() {
        return cvvResult;
    }

    /**
     *
     * @param cvvResult
     */
    public void setCvvResult(CVVResultEnum cvvResult) {
        this.cvvResult = cvvResult;
    }

    /**
     *
     * @return
     */
    public AVSResultEnum getAvsResult() {
        return avsResult;
    }

    /**
     *
     * @param avsResult
     */
    public void setAvsResult(AVSResultEnum avsResult) {
        this.avsResult = avsResult;
    }

    /**
     *
     * @return
     */
    public String getCardLastFour() {
        return cardLastFour;
    }

    /**
     *
     * @param cardLastFour
     */
    public void setCardLastFour(String cardLastFour) {
        this.cardLastFour = cardLastFour;
    }

    /**
     *
     * @return
     */
    public CardBrandEnum getCardBrand() {
        return cardBrand;
    }

    /**
     *
     * @param cardBrand
     */
    public void setCardBrand(CardBrandEnum cardBrand) {
        this.cardBrand = cardBrand;
    }

    /**
     *
     * @return
     */
    public boolean isPartialAuth() {
        return partialAuth;
    }

    /**
     *
     * @param partialAuth
     */
    public void setPartialAuth(boolean partialAuth) {
        this.partialAuth = partialAuth;
    }

    /**
     *
     * @return
     */
    public String getEmvDataTLV() {
        return emvDataTLV;
    }

    /**
     *
     * @param emvDataTLV
     */
    public void setEmvDataTLV(String emvDataTLV) {
        this.emvDataTLV = emvDataTLV;
    }

    /**
     *
     * @return
     */
    public TransactionRequest getTransactionRequest() {
        return transactionRequest;
    }

    /**
     *
     * @param transactionRequest
     */
    public void setTransactionRequest(TransactionRequest transactionRequest) {
        this.transactionRequest = transactionRequest;
    }

    /**
     *
     * @return
     */
    public String getHostAdditionalInfo() {
        return hostAdditionalInfo;
    }

    /**
     *
     * @param hostAdditionalInfo
     */
    public void setHostAdditionalInfo(String hostAdditionalInfo) {
        this.hostAdditionalInfo = hostAdditionalInfo;
    }

    /**
     *
     * @return
     */
    public String getHostResponse() {
        return hostResponse;
    }

    /**
     *
     * @param hostResponse
     */
    public void setHostResponse(String hostResponse) {
        this.hostResponse = hostResponse;
    }

    /**
     *
     * @return
     */
    public String getRetrievalData() {
        return retrievalData;
    }

    /**
     *
     * @param retrievalData
     */
    public void setRetrievalData(String retrievalData) {
        this.retrievalData = retrievalData;
    }

    /**
     *
     * @return
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     *
     * @param transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public EmvData getEmvData() {
        return emvData;
    }

    public void setEmvData(EmvData value) {
        emvData = value;
    }
    
    /**
     *
     * @return
     */
    public List<HostSettingValue> getHostSettings() {
        if(hostSettings == null) {
            hostSettings = new ArrayList<>();
        }
        return hostSettings;
    }

    /**
     *
     * @param hostSettings
     */
    public void setHostSettings(List<HostSettingValue> hostSettings) {
        this.hostSettings = hostSettings;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception ex) {
        }

        return "";
    }
    
    public String toMaskedString() {
        try {
            return StringUtils.maskJsonTransaction(this.toString());
        } catch (Exception ex) {
            return "";
        }
    }
}
