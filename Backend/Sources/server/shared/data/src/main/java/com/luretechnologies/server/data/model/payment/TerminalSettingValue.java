/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time you
 * generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */
/**
 * Licensee: LURE TECHNOLOGIES License Type: Purchased
 */
package com.luretechnologies.server.data.model.payment;

/**
 *
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luretechnologies.common.enums.TerminalSettingEnum;
import com.luretechnologies.common.enums.TerminalSettingGroupEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.jpa.converters.TerminalSettingEnumConverter;
import com.luretechnologies.server.jpa.converters.TerminalSettingGroupEnumConverter;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 *
 * @author developer
 */
@Entity
@Table(name = "Terminal_Setting_Value")
public class TerminalSettingValue implements Serializable {

    /**
     *
     */
    public TerminalSettingValue() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private long id;

    @Column(name = "terminal_setting", nullable = false, length = 2)
    @Convert(converter = TerminalSettingEnumConverter.class)
    @ApiModelProperty(value = "The setting.")
    private TerminalSettingEnum terminalSetting;

    @Column(name = "group", nullable = false, length = 2)
    @Convert(converter = TerminalSettingGroupEnumConverter.class)
    @ApiModelProperty(value = "The setting group.")
    private TerminalSettingGroupEnum terminalSettingGroup;

    @JsonIgnore
    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = Terminal.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal", referencedColumnName = "id", nullable = false)})
    private Terminal terminal;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 200, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)
    @Column(name = "value", nullable = false, length = 200)
    @ApiModelProperty(value = "The setting value.", required = true)
    private String value;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @Size(max = 200, message = Messages.INVALID_DATA_LENGHT)
    //@Pattern(regexp = RegExp.EXP_REG_VALID_ALPHANUMERIC_SPECIAL, message = Messages.INVALID_DATA_ENTRY)	
    @Column(name = "default_value", nullable = false, length = 200)
    @ApiModelProperty(value = "The setting default value.", required = true)
    private String defaultValue;

    @JsonIgnore
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    private void setId(long value) {
        this.id = value;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @return
     */
    public TerminalSettingEnum getTerminalSetting() {
        return terminalSetting;
    }

    /**
     *
     * @param terminalSetting
     */
    public void setTerminalSetting(TerminalSettingEnum terminalSetting) {
        this.terminalSetting = terminalSetting;
    }

    /**
     *
     * @return
     */
    public TerminalSettingGroupEnum getTerminalSettingGroup() {
        return terminalSettingGroup;
    }

    /**
     *
     * @param terminalSettingGroup
     */
    public void setTerminalSettingGroup(TerminalSettingGroupEnum terminalSettingGroup) {
        this.terminalSettingGroup = terminalSettingGroup;
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

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
