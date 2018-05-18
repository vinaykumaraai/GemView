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
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author developer
 */
@Entity
@Table(name = "Time_Performance")
public class TimePerformance implements Serializable {

    /**
     *
     */
    public TimePerformance() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "rqst_in_front", nullable = false, length = 10)
    private int rqstInFront;

    @Column(name = "rqst_in_core_queue", nullable = false, length = 10)
    private int rqstInCoreQueue;

    @Column(name = "rqst_in_core", nullable = false, length = 10)
    private int rqstInCore;

    @Column(name = "rqst_in_host_queue", nullable = false, length = 10)
    private int rqstInHostQueue;

    @Column(name = "rqst_in_host", nullable = false, length = 10)
    private int rqstInHost;

    @Column(name = "rqst_in_processor", nullable = false, length = 10)
    private int rqstInProcessor;

    @Column(name = "rsps_in_host", nullable = false, length = 10)
    private int rspsInHost;

    @Column(name = "rsps_in_host_queue", nullable = false, length = 10)
    private int rspsInHostQueue;

    @Column(name = "rsps_in_core", nullable = false, length = 10)
    private int rspsInCore;

    @Column(name = "rsps_in_core_queue", nullable = false, length = 10)
    private int rspsInCoreQueue;

    @Column(name = "rsps_in_front", nullable = false, length = 10)
    private int rspsInFront;

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
    public void setRqstInFront(int value) {
        this.rqstInFront = value;
    }

    /**
     *
     * @return
     */
    public int getRqstInFront() {
        return rqstInFront;
    }

    /**
     *
     * @param value
     */
    public void setRqstInCoreQueue(int value) {
        this.rqstInCoreQueue = value;
    }

    /**
     *
     * @return
     */
    public int getRqstInCoreQueue() {
        return rqstInCoreQueue;
    }

    /**
     *
     * @param value
     */
    public void setRqstInCore(int value) {
        this.rqstInCore = value;
    }

    /**
     *
     * @return
     */
    public int getRqstInCore() {
        return rqstInCore;
    }

    /**
     *
     * @param value
     */
    public void setRqstInHostQueue(int value) {
        this.rqstInHostQueue = value;
    }

    /**
     *
     * @return
     */
    public int getRqstInHostQueue() {
        return rqstInHostQueue;
    }

    /**
     *
     * @param value
     */
    public void setRqstInHost(int value) {
        this.rqstInHost = value;
    }

    /**
     *
     * @return
     */
    public int getRqstInHost() {
        return rqstInHost;
    }

    /**
     *
     * @param value
     */
    public void setRqstInProcessor(int value) {
        this.rqstInProcessor = value;
    }

    /**
     *
     * @return
     */
    public int getRqstInProcessor() {
        return rqstInProcessor;
    }

    /**
     *
     * @param value
     */
    public void setRspsInHost(int value) {
        this.rspsInHost = value;
    }

    /**
     *
     * @return
     */
    public int getRspsInHost() {
        return rspsInHost;
    }

    /**
     *
     * @param value
     */
    public void setRspsInHostQueue(int value) {
        this.rspsInHostQueue = value;
    }

    /**
     *
     * @return
     */
    public int getRspsInHostQueue() {
        return rspsInHostQueue;
    }

    /**
     *
     * @param value
     */
    public void setRspsInCore(int value) {
        this.rspsInCore = value;
    }

    /**
     *
     * @return
     */
    public int getRspsInCore() {
        return rspsInCore;
    }

    /**
     *
     * @param value
     */
    public void setRspsInCoreQueue(int value) {
        this.rspsInCoreQueue = value;
    }

    /**
     *
     * @return
     */
    public int getRspsInCoreQueue() {
        return rspsInCoreQueue;
    }

    /**
     *
     * @param value
     */
    public void setRspsInFront(int value) {
        this.rspsInFront = value;
    }

    /**
     *
     * @return
     */
    public int getRspsInFront() {
        return rspsInFront;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
