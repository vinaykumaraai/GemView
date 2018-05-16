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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * Relation between a form and its controls.
 */
@Entity
@Table(name = "Form_Control")
//@DynamicUpdate
//@SelectBeforeUpdate
public class FormControl implements Serializable {

    /**
     *
     */
    public FormControl() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Form.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "form", referencedColumnName = "id", nullable = false)})
    private Form form;

    @ManyToOne(targetEntity = Control.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "control", referencedColumnName = "id", nullable = false)})
    private Control control;

    @Column(name = "control_parent", nullable = true, length = 20)
    private Long controlParent;

    @Column(name = "id_control_form", nullable = false, length = 2)
    private int idControlForm;

    @Column(name = "property", nullable = true, length = 255)
    private String property;

    @OneToMany(mappedBy = "formControl", targetEntity = PageControlValue.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private java.util.Set<PageControlValue> pageControlValues = new HashSet<>();

    /**
     * Identification for the control inside the form.
     */
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Identification for the control inside the form.
     *
     * @return
     */
    public long getId() {
        return id;
    }

//    /**
//     * In case control is Question or Answer, it stores which is the
//     * parent(question) for the answer.
//     *
//     * @param value
//     */
//    public void setControlParent(long value) {
//        setControlParent(new Long(value));
//    }
    /**
     * In case control is Question or Answer, it stores which is the
     * parent(question) for the answer.
     *
     * @param value
     */
    public void setControlParent(Long value) {
        this.controlParent = value;
    }

    /**
     * In case control is Question or Answer, it stores which is the
     * parent(question) for the answer.
     *
     * @return
     */
    public Long getControlParent() {
        return controlParent;
    }

    /**
     * Identification of the control inside the form. This is relative to the
     * form.
     *
     * @param value
     */
    public void setIdControlForm(int value) {
        this.idControlForm = value;
    }

    /**
     * Identification of the control inside the form. This is relative to the
     * form.
     *
     * @return
     */
    public int getIdControlForm() {
        return idControlForm;
    }

    /**
     *
     * @param value
     */
    public void setForm(Form value) {
        this.form = value;
    }

    /**
     *
     * @return
     */
    public Form getForm() {
        return form;
    }

    /**
     *
     * @param value
     */
    public void setControl(Control value) {
        this.control = value;
    }

    /**
     *
     * @return
     */
    public Control getControl() {
        return control;
    }

    /**
     *
     * @return
     */
    public String getProperty() {
        return property;
    }

    /**
     *
     * @return
     */
    public Set<PageControlValue> getPageControlValues() {
        return pageControlValues;
    }

    /**
     *
     * @param property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     *
     * @param pageControlValues
     */
    public void setPageControlValues(Set<PageControlValue> pageControlValues) {
        this.pageControlValues = pageControlValues;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
