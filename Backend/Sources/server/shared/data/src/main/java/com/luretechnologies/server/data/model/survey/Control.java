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
package com.luretechnologies.server.data.model.survey;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * Master table which stores all the controls type. Ex lbl_info, lbl-quest,
 * btn_answ, btn_nav, chck_answ
 */
@Entity
@Table(name = "Control")
//@DynamicUpdate
//@SelectBeforeUpdate
public class Control implements Serializable {

    /**
     * Constructor
     *
     */
    public Control() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "abbr", nullable = false, length = 10)
    private String abbr;

    @Column(name = "size", nullable = false, length = 3)
    private int size;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "action", nullable = false, length = 1)
    private int action;

    @OneToMany(mappedBy = "control", targetEntity = FormControl.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<FormControl> formControls = new HashSet<>();

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
     * Control's name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Control's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Control abbreviation. Ex lbl, btn.
     *
     * @param value
     */
    public void setAbbr(String value) {
        this.abbr = value;
    }

    /**
     * Control abbreviation. Ex lbl, btn.
     *
     * @return
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * Maximum text size that control support.
     *
     * @param value
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Maximum text size that control support.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Control's description.
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Control's description.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Defines if the control is going to have action when pressed.
     *
     * @param value
     */
    public void setAction(int value) {
        this.action = value;
    }

    /**
     * Defines if the control is going to have action when pressed.
     *
     * @return
     */
    public int getAction() {
        return action;
    }

    /**
     *
     * @return
     */
    public Set<FormControl> getFormControls() {
        return formControls;
    }

    /**
     *
     * @param formControls
     */
    public void setFormControls(Set<FormControl> formControls) {
        this.formControls = formControls;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
