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
package com.luretechnologies.common.performance;

import java.io.Serializable;
import static java.lang.System.currentTimeMillis;

/**
 * The PerformanceTimer Class - track component processing times.
 *
 *
 */
public class PerformanceTiming implements Serializable {

    private long FRONT_RECEIVED_REQUEST;        //   ("frontend began processing"),
    private long FRONT_SEND_REQUEST;            //   ("frontend send message to core"),
    private long CORE_RECEIVED_REQUEST;         //   ("core began processing"),
    private long CORE_SEND_REQUEST;             //   ("core sent message to host module"),
    private long HOST_RECEIVED_REQUEST;         //   ("host module began processing"),
    private long PROCESSOR_CONNECT_START;       //   ("host module starts connects with processor"),
    private long PROCESSOR_SEND_REQUEST;        //   ("host module began communications with processor"),
    private long PROCESSOR_RECEIVED_RESPONSE;   //   ("host module completed communications with processor"),
    private long HOST_SEND_RESPONSE;            //   ("host module completed processing"),
    private long CORE_RECEIVED_RESPONSE;        //   ("core received message from host module"),
    private long CORE_SEND_RESPONSE;            //   ("core completed processing"),
    private long FRONT_RECEIVED_RESPONSE;       //   ("frontend received message from core"),
    private long FRONT_SEND_RESPONSE;           //   ("frontend completed processing"),

    /**
     *
     */
    public PerformanceTiming() {
    }

    /**
     *
     * @return
     */
    public long getFRONT_RECEIVED_REQUEST() {
        return FRONT_RECEIVED_REQUEST;
    }

    /**
     *
     */
    public void setFRONT_RECEIVED_REQUEST() {
        this.FRONT_RECEIVED_REQUEST = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getFRONT_SEND_REQUEST() {
        return FRONT_SEND_REQUEST;
    }

    /**
     *
     */
    public void setFRONT_SEND_REQUEST() {
        this.FRONT_SEND_REQUEST = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getCORE_RECEIVED_REQUEST() {
        return CORE_RECEIVED_REQUEST;
    }

    /**
     *
     */
    public void setCORE_RECEIVED_REQUEST() {
        this.CORE_RECEIVED_REQUEST = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getCORE_SEND_REQUEST() {
        return CORE_SEND_REQUEST;
    }

    /**
     *
     */
    public void setCORE_SEND_REQUEST() {
        this.CORE_SEND_REQUEST = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getHOST_RECEIVED_REQUEST() {
        return HOST_RECEIVED_REQUEST;
    }

    /**
     *
     */
    public void setHOST_RECEIVED_REQUEST() {
        this.HOST_RECEIVED_REQUEST = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getPROCESSOR_CONNECT_START() {
        return PROCESSOR_CONNECT_START;
    }

    /**
     *
     */
    public void setPROCESSOR_CONNECT_START() {
        this.PROCESSOR_CONNECT_START = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getPROCESSOR_SEND_REQUEST() {
        return PROCESSOR_SEND_REQUEST;
    }

    /**
     *
     */
    public void setPROCESSOR_SEND_REQUEST() {
        this.PROCESSOR_SEND_REQUEST = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getPROCESSOR_RECEIVED_RESPONSE() {
        return PROCESSOR_RECEIVED_RESPONSE;
    }

    /**
     *
     */
    public void setPROCESSOR_RECEIVED_RESPONSE() {
        this.PROCESSOR_RECEIVED_RESPONSE = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getHOST_SEND_RESPONSE() {
        return HOST_SEND_RESPONSE;
    }

    /**
     *
     */
    public void setHOST_SEND_RESPONSE() {
        this.HOST_SEND_RESPONSE = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getCORE_RECEIVED_RESPONSE() {
        return CORE_RECEIVED_RESPONSE;
    }

    /**
     *
     */
    public void setCORE_RECEIVED_RESPONSE() {
        this.CORE_RECEIVED_RESPONSE = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getCORE_SEND_RESPONSE() {
        return CORE_SEND_RESPONSE;
    }

    /**
     *
     */
    public void setCORE_SEND_RESPONSE() {
        this.CORE_SEND_RESPONSE = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getFRONT_RECEIVED_RESPONSE() {
        return FRONT_RECEIVED_RESPONSE;
    }

    /**
     *
     */
    public void setFRONT_RECEIVED_RESPONSE() {
        this.FRONT_RECEIVED_RESPONSE = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public long getFRONT_SEND_RESPONSE() {
        return FRONT_SEND_RESPONSE;
    }

    /**
     *
     */
    public void setFRONT_SEND_RESPONSE() {
        this.FRONT_SEND_RESPONSE = currentTimeMillis();
    }

    /**
     *
     * @return
     */
    public int getRequestInFront() {
        return (int) (FRONT_SEND_REQUEST - FRONT_RECEIVED_REQUEST);
    }

    /**
     *
     * @return
     */
    public int getRequestInCore() {
        return (int) (CORE_SEND_REQUEST - CORE_RECEIVED_REQUEST);
    }

    /**
     *
     * @return
     */
    public int getRequestInHost() {
        return (int) (PROCESSOR_CONNECT_START - HOST_RECEIVED_REQUEST);
    }

    /**
     *
     * @return
     */
    public int getProcessorConnect() {
        return (int) (PROCESSOR_SEND_REQUEST - PROCESSOR_CONNECT_START);
    }

    /**
     *
     * @return
     */
    public int getProcessorTransact() {
        return (int) (PROCESSOR_RECEIVED_RESPONSE - PROCESSOR_SEND_REQUEST);
    }

    /**
     *
     * @return
     */
    public int getResponseInHost() {
        return (int) (HOST_SEND_RESPONSE - PROCESSOR_RECEIVED_RESPONSE);
    }

    /**
     *
     * @return
     */
    public int getResponseInCore() {
        return (int) (CORE_SEND_RESPONSE - CORE_RECEIVED_RESPONSE);
    }

    /**
     *
     * @return
     */
    public int getResponseInFront() {
        return (int) (FRONT_SEND_RESPONSE - FRONT_RECEIVED_RESPONSE);
    }

    /**
     *
     * @return
     */
    public int getTotalFront() {
        return (int) (getRequestInFront() + getResponseInFront());
    }

    /**
     *
     * @return
     */
    public int getTotalCore() {
        return (int) (getRequestInCore() + getResponseInCore());
    }

    /**
     *
     * @return
     */
    public int getTotalHost() {
        return (int) (getRequestInHost() + getResponseInHost());
    }

    /**
     *
     * @return
     */
    public int getRequestInCoreQueue() {
        return (int) (CORE_RECEIVED_REQUEST - FRONT_SEND_REQUEST);
    }

    /**
     *
     * @return
     */
    public int getRequestInHostQueue() {
        return (int) (HOST_RECEIVED_REQUEST - CORE_SEND_REQUEST);
    }

    /**
     *
     * @return
     */
    public int getResponseInHostQueue() {
        return (int) (CORE_RECEIVED_RESPONSE - HOST_SEND_RESPONSE);
    }

    /**
     *
     * @return
     */
    public int getResponseInCoreQueue() {
        return (int) (FRONT_RECEIVED_RESPONSE - CORE_SEND_RESPONSE);
    }

    /**
     *
     * @return
     */
    public int getTotalJms() {
        return (int) (getRequestInCoreQueue() + getRequestInHostQueue() + getResponseInHostQueue() + getResponseInCoreQueue());
    }

    /**
     *
     * @return
     */
    public int getTotal() {
        return (int) (FRONT_RECEIVED_RESPONSE - FRONT_SEND_REQUEST);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder processesTimes = new StringBuilder();

        processesTimes = processesTimes.append("\n");
        processesTimes = processesTimes.append("TIMINGS\n");
        processesTimes = processesTimes.append("-------------------------------------\n");
        processesTimes = processesTimes.append("FRONT IN:      ").append(getRequestInFront()).append("ms\n");
        processesTimes = processesTimes.append("CORE IN:       ").append(getRequestInCore()).append("ms\n");
        processesTimes = processesTimes.append("HOST IN:       ").append(getRequestInHost()).append("ms\n");
        processesTimes = processesTimes.append("HOST OUT:      ").append(getResponseInHost()).append("ms\n");
        processesTimes = processesTimes.append("CORE OUT:      ").append(getResponseInCore()).append("ms\n");
        processesTimes = processesTimes.append("FRONT OUT:     ").append(getResponseInFront()).append("ms\n");
        processesTimes = processesTimes.append("-------------------------------------\n");
        processesTimes = processesTimes.append("COREIN QUEUE:  ").append(getRequestInCoreQueue()).append("ms\n");
        processesTimes = processesTimes.append("HOSTIN QUEUE:  ").append(getRequestInHostQueue()).append("ms\n");
        processesTimes = processesTimes.append("HOSTOUT QUEUE: ").append(getResponseInHostQueue()).append("ms\n");
        processesTimes = processesTimes.append("COREOUT QUEUE: ").append(getResponseInCoreQueue()).append("ms\n");
        processesTimes = processesTimes.append("-------------------------------------\n");
        processesTimes = processesTimes.append("TOTAL FRONT:   ").append(getTotalFront()).append("ms\n");
        processesTimes = processesTimes.append("TOTAL CORE:    ").append(getTotalCore()).append("ms\n");
        processesTimes = processesTimes.append("TOTAL HOST:    ").append(getTotalHost()).append("ms\n");
        processesTimes = processesTimes.append("PROC CONNECT:  ").append(getProcessorConnect()).append("ms\n");
        processesTimes = processesTimes.append("PROC TRANSACT: ").append(getProcessorTransact()).append("ms\n");
        processesTimes = processesTimes.append("TOTAL JMS:     ").append(getTotalJms()).append("ms\n");
        processesTimes = processesTimes.append("-------------------------------------\n");
        processesTimes = processesTimes.append("TOTAL TX TIME: ").append(getTotal()).append("ms\n");
        processesTimes = processesTimes.append("\n");

        return new String(processesTimes);
    }
}
