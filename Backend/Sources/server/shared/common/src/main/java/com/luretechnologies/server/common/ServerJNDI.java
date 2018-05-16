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
package com.luretechnologies.server.common;

/**
 * Constants for JNDI resources.
 *
 */
public class ServerJNDI {

    /**
     * JDBC
     *
     */
    public static final String JDBC = "jdbc/tms";

    /**
     * ActiveMQ Broker URL
     *
     */
    public static final String BROKER_URL = (System.getProperty("BROKER_URL") != null) ? System.getProperty("BROKER_URL") : "tcp://127.0.0.1:61616";

    /**
     * Core In Queue
     *
     */
    public static final String CORE_IN_QUEUE = "jms_tms_Queue_CoreIn";

    /**
     * Core Out Queue
     *
     */
    public static final String CORE_OUT_QUEUE = "jms_tms_Queue_CoreOut";

    /**
     * Host Out Queue
     *
     */
    public static final String HOST_OUT_QUEUE = "jms_tms_HostOut_Queue";

    /**
     * Service In Queue - Email
     *
     */
    public static final String SERVICE_IN_QUEUE_EMAIL = "jms_ServerX_Queue_ServiceIn_Email";

    /**
     * Service Out Queue
     *
     */
    public static final String SERVICE_OUT_QUEUE = "jms_tms_ServiceOut_Queue";

    private ServerJNDI() {
    }

}
