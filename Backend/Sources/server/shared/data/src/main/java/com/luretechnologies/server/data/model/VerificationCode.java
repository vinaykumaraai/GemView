/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@javax.persistence.Entity
@Table(name = "Verification_Code")
public class VerificationCode implements Serializable {

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id; 
    
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 50, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EMAIL_REG_EXP, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "email_id", nullable = false, length = 50)
    @ApiModelProperty(value = "The email.")
    private String emailId;
    
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 50, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_APPROVAL_VERIFICATION_CODE, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "code", nullable = false, length = 50)
    @ApiModelProperty(value = "The verification code.")
    private String code;
    
    @Column(name = "sent_time", nullable = false, length = 20)
    @ApiModelProperty(value = "The sent time.")
    private long sentTime;
    
    @JsonIgnore
    @Column(name = "sent_timestamp", nullable = false, insertable = false, updatable = false)
    private Timestamp sentTimestamp;
    
    public VerificationCode() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the sentTime
     */
    public long getSentTime() {
        return sentTime;
    }

    /**
     * @param sentTime the sentTime to set
     */
    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * @return the sentTimestamp
     */
    public Timestamp getSentTimestamp() {
        return sentTimestamp;
    }

    /**
     * @param sentTimestamp the sentTimestamp to set
     */
    public void setSentTimestamp(Timestamp sentTimestamp) {
        this.sentTimestamp = sentTimestamp;
    }
    
}
