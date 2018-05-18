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
import com.luretechnologies.common.validator.RegExp;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.constraints.FieldNotBlank;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
//import org.hibernate.annotations.BatchSize;

/**
 * Stores the merchant's settings.
 */
@Entity
@Table(name = "Terminal_Host_Setting")
public class TerminalHostSetting implements Serializable {

    /**
     *
     */
    public TerminalHostSetting() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identifier.")
    private long id;
    
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "name", nullable = false, length = 100)
    @ApiModelProperty(value = "The name.")
    protected String name;
    
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @FieldNotBlank
    @Size(max = 100, message = Messages.INVALID_DATA_LENGHT)
    @Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL_NAME, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "enum_key", nullable = false, length = 100, unique = true)
    @ApiModelProperty(value = "The key.")
    protected String key;

    @ManyToOne(targetEntity = Host.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "host", referencedColumnName = "id")})
    @ApiModelProperty(value = "The host.")
    private Host host;

    //@BatchSize(size = 100)
//    @OneToMany(mappedBy = "terminalHostSetting", targetEntity = TerminalHostSettingValue.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @ApiModelProperty(value = "The terminal host settings.")
//    private Set<TerminalHostSettingValue> terminalHostSettingValues = new HashSet<>();
    
//    @JsonIgnore
//    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
//    private Timestamp createdAt;
//
//    @JsonIgnore
//    @Column(name = "updated_at", nullable = false)
//    private Timestamp updatedAt;

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
     * HostEnum identification.
     *
     * @param value
     */
    public void setHost(Host value) {
        this.host = value;
    }

    /**
     * HostEnum identification.
     *
     * @return
     */
    public Host getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     *
     * @return
     */
//    public Set<TerminalHostSettingValue> getTerminalHostSettingValues() {
//        return terminalHostSettingValues;
//    }

    /**
     *
     * @param terminalHostSettingValues
     */
//    public void setTerminalHostSettingValues(Set<TerminalHostSettingValue> terminalHostSettingValues) {
//        this.terminalHostSettingValues = terminalHostSettingValues;
//    }
    
    /**
     *
     * @return
     */
//    public Timestamp getUpdatedAt() {
//        return updatedAt;
//    }

    /**
     *
     * @param updatedAt
     */
//    public void setUpdatedAt(Timestamp updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
