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

import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.data.model.Merchant;
import java.io.Serializable;
import java.sql.Timestamp;
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

/**
 * Stores the mode and operation that a merchant support by host.
 */
@Entity
@Table(name = "Merchant_Host_Mode_Operation")
public class MerchantHostModeOperation implements Serializable {

    /**
     *
     */
    public MerchantHostModeOperation() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Merchant.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "merchant", referencedColumnName = "id", nullable = false)})
    private Merchant merchant;

    @NotNull(message = Messages.VALUE_IS_EMPTY)
    @ManyToOne(targetEntity = HostModeOperation.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "host_mode_operation", referencedColumnName = "id")})
    private HostModeOperation hostModeOperation;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    /**
     *
     * @return
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     *
     * @param merchant
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     * Merchant host mode operation's creation date.
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * Merchant host mode operation's creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Merchant host mode operation's last update date.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Merchant host mode operation's last update date.
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
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
     * @param value
     */
    public void setHostModeOperation(HostModeOperation value) {
        this.hostModeOperation = value;
    }

    /**
     *
     * @return
     */
    public HostModeOperation getHostModeOperation() {
        return hostModeOperation;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
