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
package com.luretechnologies.server.jms.utils;

/**
 *
 *
 * @author developer
 */
public class JmsConstants {

    /**
     * Time in milliseconds that the process in the Front will be blocked
     * waiting for a response message.
     *
     */
//    public final static int FRONT_TIME_OUT = 35000;     // 35 secs = CORE_TIME_OUT (25 secs) + core processing in (~5 secs) + core processing out (~5 secs) 
    public final static int FRONT_TIME_OUT = 85000;     // DURING DEVELOPMENT 85 secs = CORE_TIME_OUT (75 secs) + core processing in (~5 secs) + core processing out (~5 secs) 

    /**
     * Time in milliseconds that the message will live on a the queue.
     *
     */
//    public final static int FRONT_TIME_TO_LIVE = 40000; // 40 secs 
    public final static int FRONT_TIME_TO_LIVE = 90000; // DURING DEVELOPMENT 90 secs 

    /**
     * Time in milliseconds that the process in the Core will be blocked waiting
     * for a response message.
     *
     */
//    public final static int CORE_TIME_OUT = 25000;      // 25 secs = HOST_TIME_OUT (15 secs) + host processing in (~5 secs) + host processing out (~5 secs) 
    public final static int CORE_TIME_OUT = 75000;      // DURING DEVELOPMENT 75 secs = HOST_TIME_OUT (65 secs) + host processing in (~5 secs) + host processing out (~5 secs) 

    /**
     * Time in milliseconds that the message will live on a the queue.
     *
     */
//    public final static int CORE_TIME_TO_LIVE = 30000;  // 30 secs 
    public final static int CORE_TIME_TO_LIVE = 80000;  // DURING DEVELOPMENT 80 secs 

    /**
     * Time in milliseconds that the process in the Host will be blocked waiting
     * for a response message.
     *
     */
//    public final static int HOST_TIME_OUT = 15000;      // 15 secs
    public final static int HOST_TIME_OUT = 65000;      // DURING DEVELOPMENT 65 secs

    /**
     * Time in milliseconds that the message will live on a the queue.
     *
     */
//    public final static int HOST_TIME_TO_LIVE = 20000;  // 20 secs
    public final static int HOST_TIME_TO_LIVE = 70000;  // DURING DEVELOPMENT 70 secs
}
