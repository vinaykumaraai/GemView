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
package com.luretechnologies.server.data.model;

//import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.data.model.payment.TerminalHost;
import com.luretechnologies.server.data.model.payment.TerminalSettingValue;
import com.luretechnologies.server.data.model.tms.ScheduleGroup;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Stores all Terminals.
 */
@Entity
@Table(name = "Terminal")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
//@JsonFilter("terminalFilter")
//@JsonIgnoreProperties(ignoreUnknown=true)
public class Terminal extends com.luretechnologies.server.data.model.Entity implements Serializable {

    /**
     *
     */
    public Terminal() {
    }

    @Size(max = 50, message = Messages.INVALID_DATA_LENGHT)
    @Column(name = "serial_number", nullable = true, unique = true, length = 50)
    @ApiModelProperty(value = "The serial number.")
    private String serialNumber;

    @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "model", referencedColumnName = "id")})
    @ApiModelProperty(value = "The model.", required = true)
    private Model model;

//    @OneToOne(targetEntity = KeyBlock.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumns({
//        @JoinColumn(name = "key_block", referencedColumnName = "id")})
//    @ApiModelProperty(value = "The key block.")
//    private KeyBlock keyBlock;
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = ScheduleGroup.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "schedule_group", referencedColumnName = "id", nullable = false)})
    @ApiModelProperty(value = "The schedule group to which it belongs.", required = true)
    private ScheduleGroup scheduleGroup;

//    @OneToMany(mappedBy = "terminal", targetEntity = Result.class)
//    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
//    @LazyCollection(LazyCollectionOption.TRUE)
//    private Set<Result> results = new HashSet<>();
//
    @JsonIgnore
    @OneToMany(mappedBy = "terminal", targetEntity = AuditLog.class, fetch = FetchType.LAZY)
    private Set<AuditLog> auditLogs = new HashSet<>();

//    @OneToMany(mappedBy = "terminal", targetEntity = Diagnostic.class)
//    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
//    @LazyCollection(LazyCollectionOption.TRUE)
//    private Set<Diagnostic> diagnostic = new HashSet<>();
//    
    @OneToMany(mappedBy = "terminal", targetEntity = TerminalHost.class, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "The hosts.")
    private Set<TerminalHost> terminalHosts = new HashSet<>();

    @OneToMany(mappedBy = "terminal", targetEntity = TerminalSettingValue.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "The settings.")
    private Set<TerminalSettingValue> terminalSettingValues = new HashSet<>();

    @Column(name = "last_contact", nullable = false, length = 50)
    @ApiModelProperty(value = "The last contact.", required = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastContact;

    @Column(name = "last_download", nullable = false, length = 50)
    @ApiModelProperty(value = "The last  download.", required = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastDownload;

    @Column(name = "active", nullable = false, length = 1)
    @ApiModelProperty(value = "The active.")
    private boolean active = true;

    @Column(name = "heartbeat", nullable = false, length = 1)
    @ApiModelProperty(value = "The Heartbeat.")
    private boolean heartbeat = true;

    @Column(name = "frequency", nullable = false, length = 20)
    @ApiModelProperty(value = "The frequency heartbeat request.")
    private long frequency;

    @Column(name = "debug_expiration_date", nullable = false, length = 50)
    @ApiModelProperty(value = "Debug expiration date.", required = true)
    private Timestamp debugExpirationDate;

    @Column(name = "debug_active", nullable = false)
    @ApiModelProperty(value = "if is debuggable or not")
    private Boolean debugActive;

    public Date getLastContact() {
        return lastContact;
    }

    public void setLastContact(Date lastContact) {
        this.lastContact = lastContact;
    }

    public Date getLastDownload() {
        return lastDownload;
    }

    public void setLastDownload(Date lastDownload) {
        this.lastDownload = lastDownload;
    }

    /**
     * Terminal serial number.
     *
     * @param value
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Terminal serial number.
     *
     * @return
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     *
     * @return
     */
    public Set<TerminalSettingValue> getTerminalSettingValues() {
        return terminalSettingValues;
    }

    /**
     *
     * @param terminalSettingValues
     */
    public void setTerminalSettingValues(Set<TerminalSettingValue> terminalSettingValues) {
        this.terminalSettingValues = terminalSettingValues;
    }

//    /**
//     *
//     * @param value
//     */
//    public void setResults(Set<Result> value) {
//        this.results = value;
//    }
//
//    /**
//     *
//     * @return
//     */
//    @JsonIgnore
//    public Set<Result> getResults() {
//        return results;
//    }
//
    /**
     *
     * @return
     */
    @JsonIgnore
    public Set<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    /**
     *
     * @param auditLogs
     */
    @JsonIgnore
    public void setAuditLog(Set<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }

//    public void setDiagnostic(Set<Diagnostic> value) {
//        this.diagnostic = value;
//    }
//
//    @JsonIgnore
//    public Set<Diagnostic> getDiagnostic() {
//        return diagnostic;
//    }
//    /**
//     *
//     * @param value
//     */
//    public void setKeyBlock(KeyBlock value) {
//        this.keyBlock = value;
//    }
//    /**
//     *
//     * @return
//     */
//    public KeyBlock getKeyBlock() {
//        return keyBlock;
//    }
    /**
     *
     * @param value
     */
    public void setModel(Model value) {
        this.model = value;
    }

    /**
     *
     * @return
     */
    public Model getModel() {
        return model;
    }

    /**
     *
     * @param value
     */
    public void setScheduleGroup(ScheduleGroup value) {
        this.scheduleGroup = value;
    }

    /**
     *
     * @return
     */
    public ScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    /**
     *
     * @return
     */
    public Set<TerminalHost> getTerminalHosts() {
        return terminalHosts;
    }

    /**
     *
     * @param terminalHosts
     */
    public void setTerminalHosts(Set<TerminalHost> terminalHosts) {
        this.terminalHosts = terminalHosts;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    /**
     * @return the frequency
     */
    public long getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
 /**
     * @return the debugExpirationDate
     */
    public Timestamp getDebugExpirationDate() {
        return debugExpirationDate;
    }

    /**
     * @param debugExpirationDate the debugExpirationDate to set
     */
    public void setDebugExpirationDate(Timestamp debugExpirationDate) {
        this.debugExpirationDate = debugExpirationDate;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the heartbeat
     */
    public boolean isHeartbeat() {
        return heartbeat;
    }

    /**
     * @param heartbeat the heartbeat to set
     */
    public void setHeartbeat(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }
    /**
     * @return the debugActive
     */
    public Boolean getDebugActive() {
        return debugActive;
    }

    /**
     * @param debugActive the debugActive to set
     */
    public void setDebugActive(Boolean debugActive) {
        this.debugActive = debugActive;
    }    
}
