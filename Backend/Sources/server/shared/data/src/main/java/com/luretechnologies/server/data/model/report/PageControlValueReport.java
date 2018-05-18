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
package com.luretechnologies.server.data.model.report;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Control that takes a value inside a page.
 */
@Entity
@Table(name = "Page_Control_Value")
public class PageControlValueReport implements Serializable {

    /**
     *
     */
    public PageControlValueReport() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    /**
     * Identification of the control inside the page.
     *
     * @param value
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Identification of the control inside the page.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * This is the literal Answer, Question or Info that is displayed in the
     * control (Label, button, text).
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * This is the literal Answer, Question or Info that is displayed in the
     * control (Label, button, text).
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
