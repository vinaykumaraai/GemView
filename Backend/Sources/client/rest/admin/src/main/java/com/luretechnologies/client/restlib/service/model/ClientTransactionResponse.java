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
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import com.luretechnologies.common.enums.AVSResultEnum;
import com.luretechnologies.common.enums.CVVResultEnum;
import com.luretechnologies.common.enums.CardBrandEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-09T17:35:21.421-05:00")
public class ClientTransactionResponse {

    private String approvalCode = null;
    private String approvalNumber = null;
    private String authorizedAmount = null;
    private AVSResultEnum avsResult = null;
    private Integer batchNumber = null;
    private CardBrandEnum cardBrand = null;
    private String cardLastFour = null;
    private CVVResultEnum cvvResult = null;
    private String disposition = null;
    private String emvDataTLV = null;
    private EntryMethodEnum entryMethod = null;
    private Boolean partialAuth = null;
    private String retrievalData = null;
    private Integer sequenceNumber = null;
    private StatusEnum status = null;
    private String transactionId = null;
    private String transmissionDate = null;
    private String transmissionTime = null;
    private final EmvData emvData = null;

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
     * @param approvalCode
     */
    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
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
     * @param approvalNumber
     */
    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("authorizedAmount")
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
    @ApiModelProperty(value = "")
    @JsonProperty("avsResult")
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
    @ApiModelProperty(value = "")
    @JsonProperty("batchNumber")
    public Integer getBatchNumber() {
        return batchNumber;
    }

    /**
     *
     * @param batchNumber
     */
    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
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
    @ApiModelProperty(value = "")
    @JsonProperty("cardLastFour")
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
    @ApiModelProperty(value = "")
    @JsonProperty("cvvResult")
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
    @ApiModelProperty(value = "")
    @JsonProperty("disposition")
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
    @ApiModelProperty(value = "")
    @JsonProperty("emvDataTLV")
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
    @ApiModelProperty(value = "")
    @JsonProperty("entryMethod")
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
    @ApiModelProperty(value = "")
    @JsonProperty("partialAuth")
    public Boolean getPartialAuth() {
        return partialAuth;
    }

    /**
     *
     * @param partialAuth
     */
    public void setPartialAuth(Boolean partialAuth) {
        this.partialAuth = partialAuth;
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
     * @param retrievalData
     */
    public void setRetrievalData(String retrievalData) {
        this.retrievalData = retrievalData;
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
     * @param sequenceNumber
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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
     * @param status
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
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
     * @param transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("transmissionDate")
    public String getTransmissionDate() {
        return transmissionDate;
    }

    /**
     *
     * @param transmissionDate
     */
    public void setTransmissionDate(String transmissionDate) {
        this.transmissionDate = transmissionDate;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("transmissionTime")
    public String getTransmissionTime() {
        return transmissionTime;
    }

    /**
     *
     * @param transmissionTime
     */
    public void setTransmissionTime(String transmissionTime) {
        this.transmissionTime = transmissionTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TransactionResponse {\n");

        sb.append("    approvalCode: ").append(StringUtil.toIndentedString(approvalCode)).append("\n");
        sb.append("    approvalNumber: ").append(StringUtil.toIndentedString(approvalNumber)).append("\n");
        sb.append("    authorizedAmount: ").append(StringUtil.toIndentedString(authorizedAmount)).append("\n");
        sb.append("    avsResult: ").append(StringUtil.toIndentedString(avsResult)).append("\n");
        sb.append("    batchNumber: ").append(StringUtil.toIndentedString(batchNumber)).append("\n");
        sb.append("    cardBrand: ").append(StringUtil.toIndentedString(cardBrand)).append("\n");
        sb.append("    cardLastFour: ").append(StringUtil.toIndentedString(cardLastFour)).append("\n");
        sb.append("    cvvResult: ").append(StringUtil.toIndentedString(cvvResult)).append("\n");
        sb.append("    disposition: ").append(StringUtil.toIndentedString(disposition)).append("\n");
        sb.append("    emvDataTLV: ").append(StringUtil.toIndentedString(emvDataTLV)).append("\n");
        sb.append("    entryMethod: ").append(StringUtil.toIndentedString(entryMethod)).append("\n");
        sb.append("    partialAuth: ").append(StringUtil.toIndentedString(partialAuth)).append("\n");
        sb.append("    retrievalData: ").append(StringUtil.toIndentedString(retrievalData)).append("\n");
        sb.append("    sequenceNumber: ").append(StringUtil.toIndentedString(sequenceNumber)).append("\n");
        sb.append("    status: ").append(StringUtil.toIndentedString(status)).append("\n");
        sb.append("    transactionId: ").append(StringUtil.toIndentedString(transactionId)).append("\n");
        sb.append("    transmissionDate: ").append(StringUtil.toIndentedString(transmissionDate)).append("\n");
        sb.append("    transmissionTime: ").append(StringUtil.toIndentedString(transmissionTime)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
