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
package com.luretechnologies.server.data.model;

import com.luretechnologies.server.data.model.survey.PageControlValue;
import com.luretechnologies.server.data.model.survey.Survey;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Stores survey's result.
 */
@Entity
@Table(name = "Result")
public class Result implements Serializable {

    /**
     *
     */
    public Result() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Survey.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "survey", referencedColumnName = "id", nullable = false)})
    private Survey survey;

    @ManyToOne(targetEntity = Terminal.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "terminal", referencedColumnName = "id", nullable = false)})
    private Terminal terminal;

    @ManyToOne(targetEntity = PageControlValue.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "control_question", referencedColumnName = "id", nullable = false)})
    private PageControlValue question;

    @ManyToOne(targetEntity = PageControlValue.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "control_answer", referencedColumnName = "id", nullable = false)})
    private PageControlValue answer;

    @Column(name = "guid", nullable = false, length = 100)
    private String guid;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    /**
     * Database identification.
     *
     * @param value
     */
    public void setId(long value) {
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
     * Reference to customer who took the survey.
     *
     * @param value
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Reference to customer who took the survey.
     *
     * @return
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Result's creation date.
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * Result's creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
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
    public void setTerminal(Terminal value) {
        this.terminal = value;
    }

    /**
     *
     * @return
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     *
     * @return
     */
    public PageControlValue getAnswer() {
        return answer;
    }

    /**
     *
     * @return
     */
    public PageControlValue getQuestion() {
        return question;
    }

    /**
     *
     * @param answer
     */
    public void setAnswer(PageControlValue answer) {
        this.answer = answer;
    }

    /**
     *
     * @param question
     */
    public void setQuestion(PageControlValue question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
