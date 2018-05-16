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
import com.luretechnologies.common.enums.AccountTypeEnum;
import com.luretechnologies.common.enums.CVMEnum;
import com.luretechnologies.common.enums.CaptureModeEnum;
import com.luretechnologies.common.enums.CurrencyEnum;
import com.luretechnologies.common.enums.E2EMethodEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.MotoIndicatorEnum;
import com.luretechnologies.common.enums.OperationEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-09T17:35:21.421-05:00")
public class ClientTransactionRequest {

    private Boolean allowPartialAuth = null;
    private EntryMethodEnum entryMethod = null;
    private String amount = null;
    private String tipAmount = "0";
    private String cashBackAmount = "0";
    private String debitSurchargeAmount = "0";
    private List<Tax> taxAmounts = new ArrayList<>();
    private CurrencyEnum currencyCode = CurrencyEnum.USD;
    private String cardCvv = null;
    private String cardExpDate = null;
    private String cardHolderName = null;
    private String cardPan = null;
    private String cardPin = null;
    private String cardPinKSN = null;
    private String cardTrack = null;
    private String e2eIV = null;
    private String e2eKSN = null;
    private E2EMethodEnum e2eMethod = E2EMethodEnum.NONE;
    private String emvDataTLV = null;
    private Integer batchNumber = null;
    private String hostKey = null;
    private String implementationID = null;
    private String invoiceNumber = null;
    private String localDate = null;
    private String localTime = null;
    private String mac = null;
    private String merchantId = null;
    private ModeEnum mode = null;
    private OperationEnum operation = null;
    private String originalTransactionId = null;
    private Integer sequenceNumber = null;
    private String serialNumber = null;
    private String terminalId = null;
    
    private String approvalCode = null;
    private String approvalNumber = null;
    private String eventData = null;
    private MotoIndicatorEnum motoMode = null;
    private CVMEnum cvm = null;
    private CaptureModeEnum captureMode = null;
    private AccountTypeEnum accountType = null;
    private String merchantTransactionId = null;
    private String originalMerchantTransactionId = null;
    private String debugInfo = null;
    
    // ecommerce 
    private String billingStreet1 = null;
    private String billingStreet2 = null;
    private String billingCountry = null;
    private String billingState = null;
    private String billingEmail = null;
    private String billingPhoneNumber = null;
    private String billingCity = null;
    private String billingZip = null;
    private Lodging lodging = null; 
    protected EBT ebt = null; 

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("allowPartialAuth")
    public Boolean getAllowPartialAuth() {
        return allowPartialAuth;
    }

    /**
     *
     * @param allowPartialAuth
     */
    public void setAllowPartialAuth(Boolean allowPartialAuth) {
        this.allowPartialAuth = allowPartialAuth;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    /**
     * @return the debitSurchargeAmount
     */
    @ApiModelProperty(value = "")
    @JsonProperty("debitSurchargeAmount")
    public String getDebitSurchargeAmount() {
        return debitSurchargeAmount;
    }

    /**
     * @param debitSurchargeAmount the debitSurchargeAmount to set
     */
    public void setDebitSurchargeAmount(String debitSurchargeAmount) {
        this.debitSurchargeAmount = debitSurchargeAmount;
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
    @JsonProperty("cardCvv")
    public String getCardCvv() {
        return cardCvv;
    }

    /**
     *
     * @param cardCvv
     */
    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardExpDate")
    public String getCardExpDate() {
        return cardExpDate;
    }

    /**
     *
     * @param cardExpDate
     */
    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardHolderName")
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     *
     * @param cardHolderName
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardPan")
    public String getCardPan() {
        return cardPan;
    }

    /**
     *
     * @param cardPan
     */
    public void setCardPan(String cardPan) {
        this.cardPan = cardPan;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardPin")
    public String getCardPin() {
        return cardPin;
    }

    /**
     *
     * @param cardPin
     */
    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardPinKSN")
    public String getCardPinKSN() {
        return cardPinKSN;
    }

    /**
     *
     * @param cardPinKSN
     */
    public void setCardPinKSN(String cardPinKSN) {
        this.cardPinKSN = cardPinKSN;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cardTrack")
    public String getCardTrack() {
        return cardTrack;
    }

    /**
     *
     * @param cardTrack
     */
    public void setCardTrack(String cardTrack) {
        this.cardTrack = cardTrack;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cashBackAmount")
    public String getCashBackAmount() {
        return cashBackAmount;
    }

    /**
     *
     * @param cashBackAmount
     */
    public void setCashBackAmount(String cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("currencyCode")
    public CurrencyEnum getCurrencyCode() {
        return currencyCode;
    }

    /**
     *
     * @param currencyCode
     */
    public void setCurrencyCode(CurrencyEnum currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("e2eIV")
    public String getE2eIV() {
        return e2eIV;
    }

    /**
     *
     * @param e2eIV
     */
    public void setE2eIV(String e2eIV) {
        this.e2eIV = e2eIV;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("e2eKSN")
    public String getE2eKSN() {
        return e2eKSN;
    }

    /**
     *
     * @param e2eKSN
     */
    public void setE2eKSN(String e2eKSN) {
        this.e2eKSN = e2eKSN;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("e2eMethod")
    public E2EMethodEnum getE2eMethod() {
        return e2eMethod;
    }

    /**
     *
     * @param e2eMethod
     */
    public void setE2eMethod(E2EMethodEnum e2eMethod) {
        this.e2eMethod = e2eMethod;
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
    @JsonProperty("hostKey")
    public String getHostKey() {
        return hostKey;
    }

    /**
     *
     * @param hostKey
     */
    public void setHostKey(String hostKey) {
        this.hostKey = hostKey;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("implementationID")
    public String getImplementationID() {
        return implementationID;
    }

    /**
     *
     * @param implementationID
     */
    public void setImplementationID(String implementationID) {
        this.implementationID = implementationID;
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
     * @param invoiceNumber
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("localDate")
    public String getLocalDate() {
        return localDate;
    }

    /**
     *
     * @param localDate
     */
    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("localTime")
    public String getLocalTime() {
        return localTime;
    }

    /**
     *
     * @param localTime
     */
    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("mac")
    public String getMac() {
        return mac;
    }

    /**
     *
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchantId")
    public String getMerchantId() {
        return merchantId;
    }

    /**
     *
     * @param merchantId
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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
     * @param mode
     */
    public void setMode(ModeEnum mode) {
        this.mode = mode;
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
     * @param operation
     */
    public void setOperation(OperationEnum operation) {
        this.operation = operation;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("originalTransactionId")
    public String getOriginalTransactionId() {
        return originalTransactionId;
    }

    /**
     *
     * @param originalTransactionId
     */
    public void setOriginalTransactionId(String originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
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
    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     *
     * @param serialNumber
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("taxAmounts")
    public List<Tax> getTaxAmounts() {
        return taxAmounts;
    }

    /**
     *
     * @param taxAmounts
     */
    public void setTaxAmounts(List<Tax> taxAmounts) {
        this.taxAmounts = taxAmounts;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("terminalId")
    public String getTerminalId() {
        return terminalId;
    }

    /**
     *
     * @param terminalId
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("tipAmount")
    public String getTipAmount() {
        return tipAmount;
    }

    /**
     *
     * @param tipAmount
     */
    public void setTipAmount(String tipAmount) {
        this.tipAmount = tipAmount;
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
    @JsonProperty("eventData")
    public String getEventData() {
        return eventData;
    }

    /**
     *
     * @param eventData
     */
    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("motoMode")
    public MotoIndicatorEnum getMotoMode() {
        return motoMode;
    }

    /**
     *
     * @param motoMode
     */
    public void setMotoMode(MotoIndicatorEnum motoMode) {
        this.motoMode = motoMode;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("cvm")
    public CVMEnum getCvm() {
        return cvm;
    }

    /**
     *
     * @param cvm
     */
    public void setCvm(CVMEnum cvm) {
        this.cvm = cvm;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("captureMode")
    public CaptureModeEnum getCaptureMode() {
        return captureMode;
    }

    /**
     *
     * @param captureMode
     */
    public void setCaptureMode(CaptureModeEnum captureMode) {
        this.captureMode = captureMode;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("accountType")
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    /**
     *
     * @param accountType
     */
    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TransactionRequest {\n");

        sb.append("    allowPartialAuth: ").append(StringUtil.toIndentedString(allowPartialAuth)).append("\n");
        sb.append("    amount: ").append(StringUtil.toIndentedString(amount)).append("\n");
        sb.append("    batchNumber: ").append(StringUtil.toIndentedString(batchNumber)).append("\n");
        sb.append("    cardCvv: ").append(StringUtil.toIndentedString(cardCvv)).append("\n");
        sb.append("    cardExpDate: ").append(StringUtil.toIndentedString(cardExpDate)).append("\n");
        sb.append("    cardHolderName: ").append(StringUtil.toIndentedString(cardHolderName)).append("\n");
        sb.append("    cardPan: ").append(StringUtil.toIndentedString(cardPan)).append("\n");
        sb.append("    cardPin: ").append(StringUtil.toIndentedString(cardPin)).append("\n");
        sb.append("    cardPinKSN: ").append(StringUtil.toIndentedString(cardPinKSN)).append("\n");
        sb.append("    cardTrack: ").append(StringUtil.toIndentedString(cardTrack)).append("\n");
        sb.append("    billingZip: ").append(StringUtil.toIndentedString(getBillingZip())).append("\n");
        sb.append("    cashBackAmount: ").append(StringUtil.toIndentedString(cashBackAmount)).append("\n");
        sb.append("    debitSurchargeAmount: ").append(StringUtil.toIndentedString(debitSurchargeAmount)).append("\n");
        sb.append("    currencyCode: ").append(StringUtil.toIndentedString(currencyCode)).append("\n");
        sb.append("    e2eIV: ").append(StringUtil.toIndentedString(e2eIV)).append("\n");
        sb.append("    e2eKSN: ").append(StringUtil.toIndentedString(e2eKSN)).append("\n");
        sb.append("    e2eMethod: ").append(StringUtil.toIndentedString(e2eMethod)).append("\n");
        sb.append("    emvDataTLV: ").append(StringUtil.toIndentedString(emvDataTLV)).append("\n");
        sb.append("    entryMethod: ").append(StringUtil.toIndentedString(entryMethod)).append("\n");
        sb.append("    hostKey: ").append(StringUtil.toIndentedString(hostKey)).append("\n");
        sb.append("    implementationID: ").append(StringUtil.toIndentedString(implementationID)).append("\n");
        sb.append("    invoiceNumber: ").append(StringUtil.toIndentedString(invoiceNumber)).append("\n");
        sb.append("    localDate: ").append(StringUtil.toIndentedString(localDate)).append("\n");
        sb.append("    localTime: ").append(StringUtil.toIndentedString(localTime)).append("\n");
        sb.append("    mac: ").append(StringUtil.toIndentedString(mac)).append("\n");
        sb.append("    merchantId: ").append(StringUtil.toIndentedString(merchantId)).append("\n");
        sb.append("    mode: ").append(StringUtil.toIndentedString(mode)).append("\n");
        sb.append("    operation: ").append(StringUtil.toIndentedString(operation)).append("\n");
        sb.append("    originalTransactionId: ").append(StringUtil.toIndentedString(originalTransactionId)).append("\n");
        sb.append("    merchantTransactionId: ").append(StringUtil.toIndentedString(merchantTransactionId)).append("\n");
        sb.append("    originalMerchantTransactionId: ").append(StringUtil.toIndentedString(originalMerchantTransactionId)).append("\n");
        sb.append("    sequenceNumber: ").append(StringUtil.toIndentedString(sequenceNumber)).append("\n");
        sb.append("    serialNumber: ").append(StringUtil.toIndentedString(serialNumber)).append("\n");
        sb.append("    taxAmounts: ").append(StringUtil.toIndentedString(taxAmounts)).append("\n");
        sb.append("    terminalId: ").append(StringUtil.toIndentedString(terminalId)).append("\n");
        sb.append("    tipAmount: ").append(StringUtil.toIndentedString(tipAmount)).append("\n");
        sb.append("    billingStreet1: ").append(StringUtil.toIndentedString(billingStreet1)).append("\n");
        sb.append("    billingStreet2: ").append(StringUtil.toIndentedString(billingStreet2)).append("\n");
        sb.append("    billingCountry: ").append(StringUtil.toIndentedString(billingCountry)).append("\n");
        sb.append("    billingState: ").append(StringUtil.toIndentedString(billingState)).append("\n");
        sb.append("    billingEmail: ").append(StringUtil.toIndentedString(billingEmail)).append("\n");
        sb.append("    billingPhoneNumber: ").append(StringUtil.toIndentedString(billingPhoneNumber)).append("\n");
        sb.append("    billingCity: ").append(StringUtil.toIndentedString(billingCity)).append("\n");
        sb.append("    billingZip: ").append(StringUtil.toIndentedString(billingZip)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * @return the merchantTransactionId
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchantTransactionId")    
    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    /**
     * @param merchantTransactionId the merchantTransactionId to set
     */
    public void setMerchantTransactionId(String merchantTransactionId) {
        this.merchantTransactionId = merchantTransactionId;
    }

    /**
     * @return the originalMerchantTransactionId
     */
    @ApiModelProperty(value = "")
    @JsonProperty("originalMerchantTransactionId")    
    public String getOriginalMerchantTransactionId() {
        return originalMerchantTransactionId;
    }

    /**
     * @param originalMerchantTransactionId the originalMerchantTransactionId to set
     */
    public void setOriginalMerchantTransactionId(String originalMerchantTransactionId) {
        this.originalMerchantTransactionId = originalMerchantTransactionId;
    }
    /**
     * @return the debugInfo
     */
    public String getDebugInfo() {
        return debugInfo;
    }

    /**
     * @param debugInfo the debugInfo to set
     */
    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }    

    /**
     * @return the billingStreet1
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingStreet1")    
    public String getBillingStreet1() {
        return billingStreet1;
    }

    /**
     * @param billingStreet1 the billingStreet1 to set
     */
    public void setBillingStreet1(String billingStreet1) {
        this.billingStreet1 = billingStreet1;
    }

    /**
     * @return the billingStreet2
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingStreet2")        
    public String getBillingStreet2() {
        return billingStreet2;
    }

    /**
     * @param billingStreet2 the billingStreet2 to set
     */
    public void setBillingStreet2(String billingStreet2) {
        this.billingStreet2 = billingStreet2;
    }

    /**
     * @return the billingCountry
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingCountry")    
    public String getBillingCountry() {
        return billingCountry;
    }

    /**
     * @param billingCountry the billingCountry to set
     */
    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    /**
     * @return the billingState
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingState")
    public String getBillingState() {
        return billingState;
    }

    /**
     * @param billingState the billingState to set
     */
    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    /**
     * @return the billingEmail
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingEmail")
    public String getBillingEmail() {
        return billingEmail;
    }

    /**
     * @param billingEmail the billingEmail to set
     */
    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    /**
     * @return the billingPhoneNumber
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingPhoneNumber")
    public String getBillingPhoneNumber() {
        return billingPhoneNumber;
    }

    /**
     * @param billingPhoneNumber the billingPhoneNumber to set
     */
    public void setBillingPhoneNumber(String billingPhoneNumber) {
        this.billingPhoneNumber = billingPhoneNumber;
    }

    /**
     * @return the billingCity
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingCity")
    public String getBillingCity() {
        return billingCity;
    }

    /**
     * @param billingCity the billingCity to set
     */
    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    /**
     * @return the billingZip
     */
    @ApiModelProperty(value = "")
    @JsonProperty("billingZip")    
    public String getBillingZip() {
        return billingZip;
    }

    /**
     * @param billingZip the billingZip to set
     */
    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }
    /**
     * @return the lodging
     */
    @ApiModelProperty(value = "")
    @JsonProperty("lodging")     
    public Lodging getLodging() {
        return lodging;
    }

    /**
     * @param lodging the lodging to set
     */
    public void setLodging(Lodging lodging) {
        this.lodging = lodging;
    }
    
    /**
     * @return the ebt
     */
    public EBT getEbt() {
        return ebt;
    }

    /**
     * @param ebt the ebt to set
     */
    public void setEbt(EBT ebt) {
        this.ebt = ebt;
    }
}
