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

import com.luretechnologies.common.enums.AccountTypeEnum;
import com.luretechnologies.common.enums.AmountFormatEnum;
import com.luretechnologies.common.enums.CVMEnum;
import com.luretechnologies.common.enums.CaptureModeEnum;
import com.luretechnologies.common.enums.CardBrandEnum;
import com.luretechnologies.common.enums.CurrencyEnum;
import com.luretechnologies.common.enums.E2EMethodEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.MotoIndicatorEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.common.performance.PerformanceTiming;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.payment.Transaction;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luretechnologies.server.common.utils.StringUtils;
import com.luretechnologies.server.data.model.payment.Host;

/**
 * The Transaction Request Class
 *
 *
 */
public class TransactionRequest implements Serializable {

    @JsonIgnore
    private long transactionId;

    @FieldNotBlank
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ApiModelProperty(value = "The merchant identifier.", required = true)
    private String merchantId;

    @FieldNotBlank
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ApiModelProperty(value = "The terminal identifier.", required = true)
    private String terminalId;

    @ApiModelProperty(value = "The device serial number.")
    private String serialNumber;

    @ApiModelProperty(value = "The message authentication code.")
    private String mac;

    @JsonIgnore
    @ApiModelProperty(value = "The host used to process the transaction.")
    private Host host;
    
    @ApiModelProperty(value = "The host key used to process the transaction.")
    private String hostKey;

    @ApiModelProperty(value = "The transaction mode.", required = true)
    private ModeEnum mode;

    @ApiModelProperty(value = "The transaction operation.", required = true)
    private OperationEnum operation;

    @ApiModelProperty(value = "The transaction base amount.")
    private String amount;

    @ApiModelProperty(value = "The transaction tip amount.")
    private String tipAmount = "0";

    @ApiModelProperty(value = "The transaction cash back amount.")
    private String cashBackAmount = "0";

    @ApiModelProperty(value = "Surcharge amount that is added to the purchase amount.")
    private String debitSurchargeAmount;

    @ApiModelProperty(value = "The list of transaction tax amounts.")
    private List<Tax> taxAmounts = new ArrayList<>();

    @ApiModelProperty(value = "The transaction approval code.")
    private String approvalCode;

    @ApiModelProperty(value = "The transaction approval number.")
    private String approvalNumber;
    
    @ApiModelProperty(value = "The transaction event data.")
    private String eventData;

    @ApiModelProperty(value = "The transaction MOTO mode.")
    private MotoIndicatorEnum motoMode;

    @ApiModelProperty(value = "The transaction CVM.")
    private CVMEnum cvm;

    @ApiModelProperty(value = "The transaction capture mode.")
    private CaptureModeEnum captureMode;

    @ApiModelProperty(value = "The transaction account type.")
    private AccountTypeEnum accountType;

    @JsonIgnore
    private String totalAmount;

    @JsonIgnore
    private AmountFormatEnum amountFormat;

    @ApiModelProperty(value = "Specifies if partial authorization is allowed.")
    private boolean allowPartialAuth;

    @ApiModelProperty(value = "The currency code of the transaction. Default: USD")
    private CurrencyEnum currencyCode = CurrencyEnum.USD;

    @ApiModelProperty(value = "The entry method used to gather card information.")
    private EntryMethodEnum entryMethod;

    @JsonIgnore
    private Integer sequenceNumber;

    @ApiModelProperty(value = "The transaction batch number.")
    private Integer batchNumber;

    @ApiModelProperty(value = "The transaction invoice number.")
    private String invoiceNumber;

    @Size(max = 6, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_TIME_HHMMSS, message = Messages.INVALID_DATA_ENTRY)
    @ApiModelProperty(value = "The local time in which the transaction was performed, formatted HHMMSS.")
    private String localTime;

    @Size(max = 6, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_DATE_YYMMDD, message = Messages.INVALID_DATA_ENTRY)
    @ApiModelProperty(value = "The local date in which the transaction was performed, formatted YYMMDD.")
    private String localDate;

    // Card group
    @ApiModelProperty(value = "The cardholder name.")
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    private String cardHolderName;

    @ApiModelProperty(value = "The card number.")
    @Pattern(regexp = RegExp.EXP_REG_PAN, message = Messages.INVALID_DATA_ENTRY)
    private String cardPan;

    @ApiModelProperty(value = "The card expiration date, formatted YYMM.")
    @Pattern(regexp = RegExp.EXP_REG_EXPDATE_YYMM, message = Messages.INVALID_DATA_ENTRY)
    private String cardExpDate;

    @ApiModelProperty(value = "The card track 2.")
    private String cardTrack;

    @ApiModelProperty(value = "The card billing zip code.")
    @Pattern(regexp = RegExp.EXP_REG_VALID_ZIP_CODE, message = Messages.INVALID_DATA_ENTRY)
    private String billingZip;

    @ApiModelProperty(value = "The card verification value, 3 or 4 digits.")
    @Pattern(regexp = RegExp.EXP_REG_CVV, message = Messages.INVALID_DATA_ENTRY)
    private String cardCvv;

    @ApiModelProperty(value = "The encrypted PIN block.")
    private String cardPin;

    @ApiModelProperty(value = "The key serial number of the encrypted PIN block.")
    private String cardPinKSN;

    @JsonIgnore
    private CardBrandEnum cardBrand;

    @JsonIgnore
    private String transmissionDate;

    @JsonIgnore
    private String transmissionTime;

    // Encryption group
    @ApiModelProperty(value = "The E2E encryption method used to encrypt the card information.")
    private E2EMethodEnum e2eMethod = E2EMethodEnum.NONE;

    @ApiModelProperty(value = "The E2E encryption key serial number.")
    private String e2eKSN;

    @ApiModelProperty(value = "The E2E encryption initialization vector.")
    private String e2eIV;

    @ApiModelProperty(value = "The TLV encoded EMV request data.")
    private String emvDataTLV;

    @ApiModelProperty(value = "The specific program implementation identifier.")
    private String implementationID;

    @ApiModelProperty(value = "The original transaction identifier.")
    private UUID originalTransactionId;

    @JsonIgnore
    private Transaction originalTransaction;

    @JsonIgnore
    private Device device;

    @JsonIgnore
    List<HostSettingValue> hostSettings;

    @JsonIgnore
    List<MerchantHostSettingValue> merchantHostSettings;

    @JsonIgnore
    List<TerminalHostSettingValue> terminalHostSettings;

    @JsonIgnore
    private String hostRequest;

    @JsonIgnore
    private int reversalAttemptsCount;

    // For Debugging Purposes
    @JsonIgnore
    private PerformanceTiming performanceTiming = new PerformanceTiming();
    
    @FieldNotBlank
    @NotNull(message = Messages.VALUE_IS_EMPTY)    
    @ApiModelProperty(value = "Merchant Transaction Id.")
    private String merchantTransactionId;

    @ApiModelProperty(value = "Original Merchant Transaction Id.")
    private String originalMerchantTransactionId;
    
    @ApiModelProperty(value = "Debug information.")
    private String debugInfo;
    
    @ApiModelProperty(value = "First line of the billing street address as it appears on the credit card issuer’s records.")
    private String billingStreet1;
    @ApiModelProperty(value = "Second line of the billing street address.")
    private String billingStreet2;
    @ApiModelProperty(value = "Country to ship the product to. Use the two-character country codes")
    private String billingCountry;
    @ApiModelProperty(value = "State or province of the billing address")
    private String billingState;
    @ApiModelProperty(value = "Customer’s email address, including the full domain name.")
    private String billingEmail;
    @ApiModelProperty(value = "Customer’s phone number.")
    private String billingPhoneNumber;
    @ApiModelProperty(value = "City to ship the product to.")
    private String billingCity;
    @ApiModelProperty(value = "Lodging")
    private Lodging lodging;
    @ApiModelProperty(value = "EBT")
    protected EBTData ebt = null; 
    
    /**
     *
     */
    public TransactionRequest() {
    }

    @Override
    public String toString() {
        try {
            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(Include.NON_NULL);
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

    /**
     *
     * @return
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     *
     * @param transactionId
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     *
     * @return
     */
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
    public Host getHost() {
        return host;
    }

    /**
     *
     * @param host
     */
    public void setHost(Host host) {
        this.host = host;
    }

    /**
     *
     * @return
     */
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
     *
     * @return
     */
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
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     *
     * @param totalAmount
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     *
     * @return
     */
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
    public AmountFormatEnum getAmountFormat() {
        return amountFormat;
    }

    /**
     *
     * @param amountFormat
     */
    public void setAmountFormat(AmountFormatEnum amountFormat) {
        this.amountFormat = amountFormat;
    }

    /**
     *
     * @return
     */
    public boolean isAllowPartialAuth() {
        return allowPartialAuth;
    }

    /**
     *
     * @param allowPartialAuth
     */
    public void setAllowPartialAuth(boolean allowPartialAuth) {
        this.allowPartialAuth = allowPartialAuth;
    }

    /**
     *
     * @return
     */
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
    public String getCardPan() {

        if (cardPan == null || cardPan.isEmpty()) {

            if (cardTrack == null || cardTrack.isEmpty()) {
                return cardPan;
            }

            return cardTrack.split("\\=")[0];
        }

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
    public String getCardExpDate() {
        return cardExpDate;
    }

    /**
     *
     * @return
     */
    public String getCardExpDateMonth() {
        if( cardExpDate != null && !cardExpDate.isEmpty() && cardExpDate.length()>=4 ){
            return cardExpDate.substring(2, 4);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getCardExpDateYear2() {
        if( cardExpDate != null && !cardExpDate.isEmpty() && cardExpDate.length() >= 2 ){
            return cardExpDate.substring(0, 2);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getCardExpDateYear4() {
        if( cardExpDate != null && !cardExpDate.isEmpty() && cardExpDate.length() >= 2 ){
            return "20" + cardExpDate.substring(0, 2);
        }
        return null;
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
    public List<HostSettingValue> getHostSettings() {
        return hostSettings;
    }

    /**
     *
     * @param hostSettings
     */
    public void setHostSettings(List<HostSettingValue> hostSettings) {
        this.hostSettings = hostSettings;
    }

    /**
     *
     * @return
     */
    public List<TerminalHostSettingValue> getTerminalHostSettings() {
        return terminalHostSettings;
    }

    /**
     *
     * @param terminalHostSettings
     */
    public void setTerminalHostSettings(List<TerminalHostSettingValue> terminalHostSettings) {
        this.terminalHostSettings = terminalHostSettings;
    }

    /**
     *
     * @return
     */
    public List<MerchantHostSettingValue> getMerchantHostSettings() {
        return merchantHostSettings;
    }

    /**
     *
     * @param merchantHostSettings
     */
    public void setMerchantHostSettings(List<MerchantHostSettingValue> merchantHostSettings) {
        this.merchantHostSettings = merchantHostSettings;
    }

    /**
     *
     * @return
     */
    public Transaction getOriginalTransaction() {
        return originalTransaction;
    }

    /**
     *
     * @param originalTransaction
     */
    public void setOriginalTransaction(Transaction originalTransaction) {
        this.originalTransaction = originalTransaction;
    }

    /**
     *
     * @return
     */
    public UUID getOriginalTransactionId() {
        return originalTransactionId;
    }

    /**
     *
     * @param originalTransactionId
     */
    public void setOriginalTransactionId(UUID originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }

    /**
     *
     * @return
     */
    public String getHostRequest() {
        return hostRequest;
    }

    /**
     *
     * @param hostRequest
     */
    public void setHostRequest(String hostRequest) {
        this.hostRequest = hostRequest;
    }

    /**
     *
     * @return
     */
    public Device getDevice() {
        return device;
    }

    /**
     *
     * @param device
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     *
     * @return
     */
    public int getReversalAttemptsCount() {
        return reversalAttemptsCount;
    }

    /**
     *
     * @param reversalAttemptsCount
     */
    public void setReversalAttemptsCount(int reversalAttemptsCount) {
        this.reversalAttemptsCount = reversalAttemptsCount;
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

    /**
     * @return the approvalCode
     */
    public String getApprovalCode() {
        return approvalCode;
    }

    /**
     * @param approvalCode the approvalCode to set
     */
    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
    
        /**
     * @return the approvalNumber
     */
    public String getApprovalNumber() {
        return approvalNumber;
    }

    /**
     * @param approvalNumber the approvalNumber to set
     */
    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    /**
     * @return the eventData
     */
    public String getEventData() {
        return eventData;
    }

    /**
     * @param eventData the eventData to set
     */
    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    /**
     * @return the cvm
     */
    public CVMEnum getCvm() {
        return cvm;
    }

    /**
     * @param cvm the cvm to set
     */
    public void setCvm(CVMEnum cvm) {
        this.cvm = cvm;
    }

    /**
     * @return the motoMode
     */
    public MotoIndicatorEnum getMotoMode() {
        return motoMode;
    }

    /**
     * @param motoMode the motoMode to set
     */
    public void setMotoMode(MotoIndicatorEnum motoMode) {
        this.motoMode = motoMode;
    }

    /**
     * @return the captureMode
     */
    public CaptureModeEnum getCaptureMode() {
        return captureMode;
    }

    /**
     * @param captureMode the captureMode to set
     */
    public void setCaptureMode(CaptureModeEnum captureMode) {
        this.captureMode = captureMode;
    }

    /**
     *
     * @return
     */
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

    public String getHostKey() {
        return hostKey;
    }

    public void setHostKey(String hostKey) {
        this.hostKey = hostKey;
    }    
    /**
     * @return the merchantTransactionId
     */
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
     * @return the debitSurchargeAmount
     */
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
     * @return the lodging
     */
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
    public EBTData getEbt() {
        return ebt;
    }

    /**
     * @param ebt the ebt to set
     */
    public void setEbt(EBTData ebt) {
        this.ebt = ebt;
    }
}
