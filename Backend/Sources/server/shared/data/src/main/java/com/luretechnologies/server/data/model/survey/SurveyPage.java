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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Relation between survey and pages.
 */
@Entity
@Table(name = "Survey_Page")
public class SurveyPage implements Serializable {

    /**
     *
     */
    public SurveyPage() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @PrimaryKeyJoinColumn
    @ManyToOne(targetEntity = Survey.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "survey", referencedColumnName = "id", nullable = false)})
    private Survey survey;

    @PrimaryKeyJoinColumn
    @ManyToOne(targetEntity = Page.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumns({
        @JoinColumn(name = "page", referencedColumnName = "id", nullable = false)})
    private Page page;

    @ManyToOne(targetEntity = Survey.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumns({
        @JoinColumn(name = "link_to_survey", referencedColumnName = "id")})
    private Survey linkToSurvey;

    @Column(name = "starting_page", nullable = false, length = 1)
    private boolean startingPage = false;

    @Column(name = "level", nullable = false, length = 2)
    private int level;

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
     * Flag that defines whether or not it is the first page of the survey.
     *
     * @param value
     */
    public void setStartingPage(boolean value) {
        this.startingPage = value;
    }

    /**
     * Flag that defines whether or not it is the first page of the survey.
     *
     * @return
     */
    public boolean getStartingPage() {
        return startingPage;
    }

    /**
     * Represent the level that the page has inside the survey tree.
     *
     * @param value
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Represent the level that the page has inside the survey tree.
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param value
     */
    public void setSurvey(Survey value) {
        this.survey = value;
    }

    /**
     *
     * @return
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     *
     * @param value
     */
    public void setPage(Page value) {
        this.page = value;
    }

    /**
     *
     * @return
     */
    public Page getPage() {
        return page;
    }

    /**
     *
     * @param value
     */
    public void setLinkToSurvey(Survey value) {
        this.linkToSurvey = value;
    }

    /**
     *
     * @return
     */
    public Survey getLinkToSurvey() {
        return linkToSurvey;
    }

    @Override
    public String toString() {
        return String.valueOf(((getSurvey() == null) ? "" : String.valueOf(getSurvey().getId())) + " " + ((getPage() == null) ? "" : String.valueOf(getPage().getId())));
    }

    @Transient
    private boolean _saved = false;

    /**
     *
     */
    public void onSave() {
        _saved = true;
    }

    /**
     *
     */
    public void onLoad() {
        _saved = true;
    }

    /**
     *
     * @return
     */
    public boolean isSaved() {
        return _saved;
    }

}
