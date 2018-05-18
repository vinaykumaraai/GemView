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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luretechnologies.common.enums.AVSResultEnum;
import com.luretechnologies.common.enums.AccountTypeEnum;
import com.luretechnologies.common.enums.CVMEnum;
import com.luretechnologies.common.enums.CVVResultEnum;
import com.luretechnologies.common.enums.CaptureModeEnum;
import com.luretechnologies.common.enums.CardBrandEnum;
import com.luretechnologies.common.enums.CurrencyEnum;
import com.luretechnologies.common.enums.EntryMethodEnum;
import com.luretechnologies.common.enums.ModeEnum;
import com.luretechnologies.common.enums.MotoIndicatorEnum;
import com.luretechnologies.common.enums.OperationEnum;
import com.luretechnologies.common.enums.StatusEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.StringUtils;
import com.luretechnologies.server.constraints.FieldNotBlank;
import com.luretechnologies.server.data.display.payment.EmvData;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.tms.PaymentProduct;
import com.luretechnologies.server.jpa.converters.AVSResultEnumConverter;
import com.luretechnologies.server.jpa.converters.AccountTypeEnumConverter;
import com.luretechnologies.server.jpa.converters.CVMEnumConverter;
import com.luretechnologies.server.jpa.converters.CVVResultEnumConverter;
import com.luretechnologies.server.jpa.converters.CaptureModeEnumConverter;
import com.luretechnologies.server.jpa.converters.CardBrandEnumConverter;
import com.luretechnologies.server.jpa.converters.CurrencyEnumConverter;
import com.luretechnologies.server.jpa.converters.EmvDataObjectConverter;
import com.luretechnologies.server.jpa.converters.EntryMethodEnumConverter;
import com.luretechnologies.server.jpa.converters.ModeEnumConverter;
import com.luretechnologies.server.jpa.converters.MotoIndicatorEnumConverter;
import com.luretechnologies.server.jpa.converters.OperationEnumConverter;
import com.luretechnologies.server.jpa.converters.StatusEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores transaction information.
 */
@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable {


    /**
     *
     */
    public Transaction() {
    }

    @JsonIgnore
    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identifier.")
    private long id;

    @Column(name = "transaction_id", nullable = false, length = 16)
    @ApiModelProperty(value = "The transaction identifier.")
    private UUID transactionId;
    
    @Column(name = "merchant_transaction_id", nullable = true, length = 50)
    @ApiModelProperty(value = "The merchant transaction identifier.")
    private String merchantTransactionId;
    
    @Column(name = "billing_city", nullable = true, length = 255)
    @ApiModelProperty(value = "City to ship the product to.")
    private String billingCity;
    
    @Column(name = "billing_country", nullable = true, length = 10)
    @ApiModelProperty(value = "Country to ship the product to. Use the two-character country codes.")
    private String billingCountry;

    @Column(name = "billing_email", nullable = true, length = 255)
    @ApiModelProperty(value = "Customer’s email address, including the full domain name.")
    private String billingEmail;
    
    @Column(name = "billing_phone_number", nullable = true, length = 20)
    @ApiModelProperty(value = "Customer’s phone number.")
    private String billingPhoneNumber;
    
    @Column(name = "billing_tate", nullable = true, length = 10)
    @ApiModelProperty(value = "State or province of the billing address.")
    private String billingState;

    @Column(name = "billing_street1", nullable = true, length = 255)
    @ApiModelProperty(value = "First line of the billing street address as it appears on the credit card issuer’s records.")
    private String billingStreet1;
    
    @Column(name = "billing_street2", nullable = true, length = 255)
    @ApiModelProperty(value = "Second line of the billing street address.")
    private String billingStreet2;

    @Column(name = "billing_zip", nullable = true, length = 20)
    @ApiModelProperty(value = "The card billing zip code.")
    private String billingZip;
    
    @ManyToOne(targetEntity = Host.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "host", referencedColumnName = "id")})
    @ApiModelProperty(value = "The host.")
    private Host host;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "mode", nullable = false, length = 2)
    @Convert(converter = ModeEnumConverter.class)
    @ApiModelProperty(value = "The mode.", required = true)
    private ModeEnum mode;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "operation", nullable = false, length = 2)
    @Convert(converter = OperationEnumConverter.class)
    @ApiModelProperty(value = "The operation.", required = true)
    private OperationEnum operation;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @DecimalMin(value = "0", message = Messages.INVALID_DATA_ENTRY)
    @DecimalMax(value = "999999.99", message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @ApiModelProperty(value = "The amount.", required = true)
    private BigDecimal amount = BigDecimal.ZERO;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @DecimalMin(value = "0", message = Messages.INVALID_DATA_ENTRY)
    @DecimalMax(value = "999999.99", message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "tip_amount", nullable = false, precision = 19, scale = 4)
    @ApiModelProperty(value = "The tip amount.", required = true)
    private BigDecimal tipAmount = BigDecimal.ZERO;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @DecimalMin(value = "0", message = Messages.INVALID_DATA_ENTRY)
    @DecimalMax(value = "999999.99", message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "cash_back_amount", nullable = false, precision = 19, scale = 4)
    @ApiModelProperty(value = "The cash back amount.", required = true)
    private BigDecimal cashBackAmount;

    @OneToMany(targetEntity = TransactionTax.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "`transaction`", nullable = false)})
    @ApiModelProperty(value = "The tax amount.")
    private Set<TransactionTax> taxAmounts = new HashSet<>();

    // TODO: Validate that the total amount is the SUM of the other amounts
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @DecimalMin(value = "0", message = Messages.INVALID_DATA_ENTRY)
    @DecimalMax(value = "999999.99", message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "total_amount", nullable = false, precision = 19, scale = 4)
    @ApiModelProperty(value = "The total amount.", required = true)
    private BigDecimal totalAmount;

    @DecimalMin(value = "0", message = Messages.INVALID_DATA_ENTRY)
    @DecimalMax(value = "999999.99", message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "authorized_amount", nullable = true, precision = 19, scale = 4)
    @ApiModelProperty(value = "The authorized amount.")
    private BigDecimal authorizedAmount = BigDecimal.ZERO;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "currency", nullable = false, length = 3)
    @Convert(converter = CurrencyEnumConverter.class)
    @ApiModelProperty(value = "The currency code of the transaction. Default: USD.", required = true)
    private CurrencyEnum currency;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "entry_method", nullable = true, length = 2)
    @Convert(converter = EntryMethodEnumConverter.class)
    @ApiModelProperty(value = "The entry method used to gather card information.", required = true)
    private EntryMethodEnum entryMethod;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "status", nullable = false, length = 2)
    @Convert(converter = StatusEnumConverter.class)
    @ApiModelProperty(value = "The status.", required = true)
    private StatusEnum status;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "disposition", nullable = false, length = 255)
    @ApiModelProperty(value = "The disposition.", required = true)
    private String disposition;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 10, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "approval_code", nullable = false, length = 10)
    @ApiModelProperty(value = "The approval code.", required = true)
    private String approvalCode;
    
    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "event_data", nullable = true, length = 255)
    @ApiModelProperty(value = "The transaction event data.")
    private String eventData;

    @Column(name = "moto_mode", nullable = true, length = 2)
    @Convert(converter = MotoIndicatorEnumConverter.class)
    @ApiModelProperty(value = "The transaction MOTO mode.")
    private MotoIndicatorEnum motoMode;

    @Column(name = "cvm", nullable = true, length = 2)
    @Convert(converter = CVMEnumConverter.class)
    @ApiModelProperty(value = "The transaction CVM.")
    private CVMEnum cvm;

    @Column(name = "capture_mode", nullable = true, length = 2)
    @Convert(converter = CaptureModeEnumConverter.class)
    @ApiModelProperty(value = "The transaction capture mode.")
    private CaptureModeEnum captureMode;

    @Column(name = "account_type", nullable = true, length = 2)
    @Convert(converter = AccountTypeEnumConverter.class)
    @ApiModelProperty(value = "The transaction account type.")
    private AccountTypeEnum accountType;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 25, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "approval_number", nullable = false, length = 25)
    @ApiModelProperty(value = "The approval number.", required = true)
    private String approvalNumber;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Column(name = "account_suffix", nullable = false, length = 4)
    @ApiModelProperty(value = "The transaction account suffix.")
    private String accountSuffix;

    @Column(name = "card_brand", nullable = true, length = 2)
    @Convert(converter = CardBrandEnumConverter.class)
    @ApiModelProperty(value = "The card brand.", required = true)
    private CardBrandEnum cardBrand;

    @Digits(integer = 9, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "batch_number", nullable = true, length = 9)
    @ApiModelProperty(value = "The batch number.")
    private int batchNumber;

    @Size(max = 20, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "invoice_number", nullable = true, length = 20)
    @ApiModelProperty(value = "The invoice number.")
    private String invoiceNumber;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Digits(integer = 10, fraction = 0, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "sequence_number", nullable = false, length = 10)
    @ApiModelProperty(value = "The sequence number.", required = true)
    private int sequenceNumber;

    @Size(max = 255, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "retrieval_data", nullable = true, length = 255)
    @ApiModelProperty(value = "The processor's response data.")
    private String retrievalData;

    @Column(name = "cvv_result", nullable = true, length = 2)
    @Convert(converter = CVVResultEnumConverter.class)
    @ApiModelProperty(value = "The processor's response to the card verification value (CVV) provided.")
    private CVVResultEnum cvvResult;

    @Column(name = "avs_result", nullable = true, length = 2)
    @Convert(converter = AVSResultEnumConverter.class)
    @ApiModelProperty(value = "The processor's response to the address verification system (AVS) provided.")
    private AVSResultEnum avsResult;

    @Column(name = "finalized", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if the transaction is finalized.")
    private boolean finalized;

    @Column(name = "voided", nullable = false, length = 1)
    @ApiModelProperty(value = "Specifies if the transaction is voided.")
    private boolean voided;

    @ManyToOne(targetEntity = Transaction.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumns({
        @JoinColumn(name = "original_transaction", referencedColumnName = "id")})
    @ApiModelProperty(value = "The original transaction object.")
    private Transaction originalTransaction;

    @JsonIgnore
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumns({
        @JoinColumn(name = "a_data", referencedColumnName = "data", nullable = true)})
    @ApiModelProperty(value = "The account used to process the transaction.")
    private Account account;

    @Size(max = 60, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "geo_location", nullable = true, length = 60)
    @ApiModelProperty(value = "The location where the transaction occurred.")
    private String geoLocation;
        
    @Column(name = "emv_data", nullable = true, columnDefinition="TEXT")
    @Convert(converter = EmvDataObjectConverter.class)
    @ApiModelProperty(value = "The EMV data object.")
    private EmvData emvData = null;

    @Column(name = "local_creation_time", nullable = false)
    @ApiModelProperty(value = "The local time in which the transaction was performed.")
    private Timestamp localCreationTime;

    @Column(name = "transmission_time", nullable = false)
    @ApiModelProperty(value = "The transmission time.")
    private Timestamp transmissionTime;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Terminal.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The terminal that processed the transaction.", required = true)
    private Terminal terminal;

    @ManyToOne(targetEntity = PaymentProduct.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "payment_product", referencedColumnName = "id")})
    @ApiModelProperty(value = "The transaction payment product.")
    private PaymentProduct paymentProduct;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "client", referencedColumnName = "id")})
    @ApiModelProperty(value = "The client that made the transaction.")
    private Client client;

    @OneToOne(targetEntity = Signature.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "signature", referencedColumnName = "id", nullable = true)})
    @ApiModelProperty(value = "The signature.")
    private Signature signature;

    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "device", referencedColumnName = "id")})
    @ApiModelProperty(value = "The device that processed the transaction.")
    private Device device;

    @JsonIgnore
    @Column(name = "host_request", columnDefinition = "BLOB", nullable = true)
    @ApiModelProperty(value = "The host request.")
    private String hostRequest;

    @JsonIgnore
    @Column(name = "host_response", columnDefinition = "BLOB", nullable = true)
    @ApiModelProperty(value = "The host response.")
    private String hostResponse;

    @JsonIgnore
    @Column(name = "host_additional_info", columnDefinition = "BLOB", nullable = true)
    @ApiModelProperty(value = "The additional info from host.")
    private String hostAdditionalInfo;

    @JsonIgnore
    @ManyToOne(targetEntity = TimePerformance.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumns({
        @JoinColumn(name = "time_performance", referencedColumnName = "id")})
    @ApiModelProperty(value = "The time performance.")
    private TimePerformance timePerformance;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

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

    /**
     *
     * @return
     */
    public BigDecimal getCashBackAmount() {
        return cashBackAmount;
    }

    /**
     *
     * @param cashBackAmount
     */
    public void setCashBackAmount(BigDecimal cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

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
    public Set<TransactionTax> getTaxAmounts() {
        return taxAmounts;
    }

    /**
     *
     * @param taxAmounts
     */
    public void setTaxAmounts(Set<TransactionTax> taxAmounts) {
        this.taxAmounts = taxAmounts;
    }

    /**
     *
     * @return
     */
    public TimePerformance getTimePerformance() {
        return timePerformance;
    }

    /**
     *
     * @param timePerformance
     */
    public void setTimePerformance(TimePerformance timePerformance) {
        this.timePerformance = timePerformance;
    }

    /**
     * Transaction's tip amount.
     *
     * @param value
     */
    public void setTipAmount(BigDecimal value) {
        this.tipAmount = value;
    }

    /**
     * Transaction's tip amount.
     *
     * @return
     */
    public BigDecimal getTipAmount() {
        return tipAmount;
    }

    /**
     * Transaction's total amount.
     *
     * @param value
     */
    public void setTotalAmount(BigDecimal value) {
        this.totalAmount = value;
    }

    /**
     * Transaction's total amount.
     *
     * @return
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Amount tha was approved by the host.
     *
     * @param value
     */
    public void setAuthorizedAmount(BigDecimal value) {
        this.authorizedAmount = value;
    }

    /**
     * Amount tha was approved by the host.
     *
     * @return
     */
    public BigDecimal getAuthorizedAmount() {
        return authorizedAmount;
    }

    /**
     *
     * @return
     */
    public CurrencyEnum getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    /**
     * Transaction's entry method.
     *
     * @param value
     */
    public void setEntryMethod(EntryMethodEnum value) {
        this.entryMethod = value;
    }

    /**
     * Transaction's entry method.
     *
     * @return
     */
    public EntryMethodEnum getEntryMethod() {
        return entryMethod;
    }

    /**
     * Transaction's disposition.
     *
     * @param value
     */
    @JsonIgnore
    public void setDisposition(String value) {
        this.disposition = value;
    }

    /**
     * Transaction's disposition.
     *
     * @return
     */
    @JsonProperty
    public String getDisposition() {
        return disposition;
    }

    /**
     * Transaction's approval code.
     *
     * @param value
     */
    @JsonIgnore
    public void setApprovalCode(String value) {
        this.approvalCode = value;
    }

    /**
     * Transaction's approval code.
     *
     * @return
     */
    @JsonProperty
    public String getApprovalCode() {
        return approvalCode;
    }

    /**
     * Transaction's approval number.
     *
     * @param value
     */
    @JsonIgnore
    public void setApprovalNumber(String value) {
        this.approvalNumber = value;
    }
    
    /**
     * Transaction's approval number.
     *
     * @return
     */
    @JsonProperty
    public String getApprovalNumber() {
        return approvalNumber;
    }

    /**
     * Transaction's event data.
     *
     * @return
     */
    @JsonProperty
    public String getEventData() {
        return eventData;
    }

    /**
     * Transaction's event data.
     *
     * @param value
     */
    @JsonIgnore
    public void setEventData(String value) {
        this.eventData = value;
    }

    /**
     * Transaction's moto mode.
     *
     * @return 
     */
    @JsonProperty
    public MotoIndicatorEnum getMotoMode() {
        return motoMode;
    }

    /**
     * Transaction's moto mode.
     *
     * @param value
     */
    @JsonIgnore
    public void setMotoMode(MotoIndicatorEnum value) {
        this.motoMode = value;
    }

    /**
     * Transaction's cvm.
     *
     * @return
     */
    @JsonProperty
    public CVMEnum getCvm() {
        return cvm;
    }

    /**
     * Transaction's cvm.
     *
     * @param value
     */
    @JsonIgnore
    public void setCvm(CVMEnum value) {
        this.cvm = value;
    }

    /**
     * Transaction's capture mode.
     *
     * @return
     */
    @JsonProperty
    public CaptureModeEnum getCaptureMode() {
        return captureMode;
    }

    /**
     * Transaction's capture mode.
     *
     * @param value
     */
    @JsonIgnore
    public void setCaptureMode(CaptureModeEnum value) {
        this.captureMode = value;
    }

    /**
     * Transaction's account type.
     *
     * @return 
     */
    @JsonProperty
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    /**
     * Transaction's account type.
     *
     * @param value
     */
    @JsonIgnore
    public void setAccountType(AccountTypeEnum value) {
        this.accountType = value;
    }  

    /**
     * Transaction's retrieval data.
     *
     * @param value
     */
    @JsonIgnore
    public void setRetrievalData(String value) {
        this.retrievalData = value;
    }

    /**
     * Transaction's retrieval data.
     *
     * @return
     */
    @JsonProperty
    public String getRetrievalData() {
        return retrievalData;
    }

    /**
     * Transaction's cvv result.
     *
     * @param value
     */
    @JsonIgnore
    public void setCvvResult(CVVResultEnum value) {
        this.cvvResult = value;
    }

    /**
     * Transaction's cvv result.
     *
     * @return
     */
    @JsonProperty
    public CVVResultEnum getCvvResult() {
        return cvvResult;
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
     * Transaction's status. Ex: approved, denied.
     *
     * @param value
     */
    @JsonIgnore
    public void setStatus(StatusEnum value) {
        this.status = value;
    }

    /**
     * Transaction's status. Ex: approved, denied.
     *
     * @return
     */
    @JsonProperty
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * Transaction's finalized status.
     *
     * @param value
     */
    @JsonIgnore
    public void setFinalized(boolean value) {
        this.finalized = value;
    }

    /**
     * Transaction's finalized status.
     *
     * @return
     */
    @JsonProperty
    public boolean getFinalized() {
        return finalized;
    }

    /**
     * Flag which says if the transaction was voided.
     *
     * @param value
     */
    @JsonIgnore
    public void setVoided(boolean value) {
        this.voided = value;
    }

    /**
     * Flag which says if the transaction was voided.
     *
     * @return
     */
    @JsonProperty
    public boolean getVoided() {
        return voided;
    }

    /**
     * Original transaction identification if this is a void or refund.
     *
     * @param value
     */
    @JsonIgnore
    public void setOriginalTransaction(Transaction value) {
        this.originalTransaction = value;
    }

    /**
     * Original transaction identification if this is a void or refund.
     *
     * @return
     */
    @JsonProperty
    public Transaction getOriginalTransaction() {
        return originalTransaction;
    }

    /**
     * Transaction's card suffix. First 4 digits and/or Last 4 digits.
     *
     * @param value
     */
    public void setAccountSuffix(String value) {
        this.accountSuffix = value;
    }

    /**
     * Transaction's card suffix. First 4 digits and/or Last 4 digits.
     *
     * @return
     */
    public String getAccountSuffix() {
        return accountSuffix;
    }

    /**
     * Transaction's batch number.
     *
     * @param value
     */
    public void setBatchNumber(int value) {
        this.batchNumber = value;
    }

    /**
     * Transaction's batch number.
     *
     * @return
     */
    public int getBatchNumber() {
        return batchNumber;
    }

    /**
     * Transaction's invoice number.
     *
     * @param value
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    /**
     * Transaction's invoice number.
     *
     * @return
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Location where the transaction occurred at.
     *
     * @param value
     */
    public void setGeoLocation(String value) {
        this.geoLocation = value;
    }

    /**
     * Location where the transaction occurred at.
     *
     * @return
     */
    public String getGeoLocation() {
        return geoLocation;
    }

    /**
     * Transaction's card brand
     *
     * @param value
     */
    public void setCardBrand(CardBrandEnum value) {
        this.cardBrand = value;
    }

    /**
     * Transaction's card brand
     *
     * @return
     */
    public CardBrandEnum getCardBrand() {
        return cardBrand;
    }

    /**
     *
     * @param value
     */
    public void setTerminal(Terminal value) {
        this.terminal = value;
    }

    /**
     *
     * @return
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     *
     * @param value
     */
    @JsonIgnore
    public void setPaymentProduct(PaymentProduct value) {
        this.paymentProduct = value;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public PaymentProduct getPaymentProduct() {
        return paymentProduct;
    }

    /**
     *
     * @param value
     */
    public void setClient(Client value) {
        this.client = value;
    }

    /**
     *
     * @return
     */
    public Client getClient() {
        return client;
    }

    /**
     *
     * @param value
     */
    public void setSignature(Signature value) {
        this.signature = value;
    }

    /**
     *
     * @return
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     *
     * @param value
     */
    public void setDevice(Device value) {
        this.device = value;
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
     * @param value
     */
    public void setAccount(Account value) {
        this.account = value;
    }

    /**
     *
     * @return
     */
    public Account getAccount() {
        return account;
    }

    /**
     * HostEnum's identification.
     *
     * @param value
     */
    public void setHost(Host value) {
        this.host = value;
    }

    /**
     * HostEnum's identification.
     *
     * @return
     */
    public Host getHost() {
        return host;
    }

    /**
     * ModeEnum's identification.
     *
     * @param value
     */
    public void setMode(ModeEnum value) {
        this.mode = value;
    }

    /**
     * ModeEnum's identification.
     *
     * @return
     */
    public ModeEnum getMode() {
        return mode;
    }

    /**
     * OperationEnum's identification.
     *
     * @param value
     */
    public void setOperation(OperationEnum value) {
        this.operation = value;
    }

    /**
     * OperationEnum's identification.
     *
     * @return
     */
    public OperationEnum getOperation() {
        return operation;
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
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Transaction's sequence number.
     *
     * @param value
     */
    public void setSequenceNumber(int value) {
        this.sequenceNumber = value;
    }

    /**
     * Transaction's sequence number.
     *
     * @return
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     *
     * @return
     */
    public Timestamp getLocalCreationTime() {
        return localCreationTime;
    }

    /**
     *
     * @param localCreationTime
     */
    public void setLocalCreationTime(Timestamp localCreationTime) {
        this.localCreationTime = localCreationTime;
    }

    /**
     *
     * @return
     */
    public Timestamp getTransmissionTime() {
        return transmissionTime;
    }

    /**
     *
     * @param transmissionTime
     */
    public void setTransmissionTime(Timestamp transmissionTime) {
        this.transmissionTime = transmissionTime;
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

    /**
     *
     */
    @PrePersist
    public void generateUUID() {
        if (transactionId == null) {
            transactionId = UUID.randomUUID();
        }
    }
    
    /**
     *
     * @return
     */
    public EmvData getEmvData() {
            return emvData;
    }
    
    /**
     *
     * @param emvData
     */
    public void setEmvData(EmvData emvData) {
        this.emvData = emvData;
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
}
