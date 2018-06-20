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
package com.luretechnologies.server.data.model.tms;

import java.io.Serializable;

/**
 *
 * @author romer
 */
public class SMS implements Serializable {

    private String account;
    private String from;
    private String to;
    private String body;

    /**
     * Default constructor
     */
    public SMS() {
    }

    /**
     * Enhanced constructor
     *
     * @param account
     * @param from
     * @param to
     * @param body
     */
    public SMS(String account, String from, String to, String body) {
        this.account = account;
        this.from = from;
        this.to = to;
        this.body = body;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return status
     */
    public boolean hasAccount() {

        return (account != null && !account.isEmpty());
    }

    /**
     * @return status
     */
    public boolean hasFrom() {

        return (from != null && !from.isEmpty());
    }

    /**
     * @return status
     */
    public boolean isValid() {

        // cc is optional
        return (account != null && !account.isEmpty()
                && from != null && !from.isEmpty()
                && to != null && !to.isEmpty()
                && body != null && !body.isEmpty());
    }
}
