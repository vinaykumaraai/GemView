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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
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

/**
 * Stores all pages.
 */
@Entity
@Table(name = "Page")
public class Page implements Serializable {

    /**
     *
     */
    public Page() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Form.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "form", referencedColumnName = "id", nullable = false)})
    private Form form;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "time_out", nullable = false, length = 10)
    private int timeOut;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "page", targetEntity = SurveyPage.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<SurveyPage> surveyPages = new HashSet<>();

    @OneToMany(mappedBy = "page", targetEntity = PageControlValue.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<PageControlValue> pageControlValues = new HashSet<>();

    /**
     * Database id.
     */
    private void setId(long value) {
        this.id = value;
    }

    /**
     * Database identificator.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Page's name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Page's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Time that the page is going to be active.
     *
     * @param value
     */
    public void setTimeOut(int value) {
        this.timeOut = value;
    }

    /**
     * Time that the page is going to be active.
     *
     * @return
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * Page's creation date.
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * Page's creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Page's last update date.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Page's last update date.
     *
     * @return
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
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
     * @return
     */
    @JsonIgnore
    public Set<SurveyPage> getSurveyPages() {
        return surveyPages;
    }

    /**
     *
     * @param surveyPages
     */
    public void setSurveyPage(Set<SurveyPage> surveyPages) {
        this.surveyPages = surveyPages;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Set<PageControlValue> getPageControlValues() {
        return pageControlValues;
    }

    /**
     *
     * @param pageControlValues
     */
    public void setPageControlValue(Set<PageControlValue> pageControlValues) {
        this.pageControlValues = pageControlValues;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
