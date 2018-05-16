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
import com.luretechnologies.server.data.model.Result;
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

/**
 * Control that takes a value inside a page.
 */
@Entity
@Table(name = "Page_Control_Value")
public class PageControlValue implements Serializable {

    /**
     *
     */
    public PageControlValue() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Page.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "page", referencedColumnName = "id", nullable = false)})
    private Page page;

    @ManyToOne(targetEntity = FormControl.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "form_control", referencedColumnName = "id", nullable = false)})
    private FormControl formControl;

    @ManyToOne(targetEntity = Page.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "action_page", referencedColumnName = "id")})
    private Page goToPage;

    @ManyToOne(targetEntity = Survey.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "link_to_survey", referencedColumnName = "id")})
    private Survey linkToSurvey;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Column(name = "show_text", nullable = false, length = 1)
    private boolean showText;

    @OneToMany(mappedBy = "answer", targetEntity = Result.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Result> resultAnswers = new HashSet<>();

    @OneToMany(mappedBy = "question", targetEntity = Result.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Result> resultQuestions = new HashSet<>();

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

    /**
     * Flag that determines if the control's caption should be show.
     *
     * @param value
     */
    public void setShowText(boolean value) {
        this.showText = value;
    }

    /**
     * Flag that determines if the control's caption should be show.
     *
     * @return
     */
    public boolean getShowText() {
        return showText;
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
    public void setFormControl(FormControl value) {
        this.formControl = value;
    }

    /**
     *
     * @return
     */
    public FormControl getFormControl() {
        return formControl;
    }

    /**
     *
     * @param value
     */
    public void setGoToPage(Page value) {
        this.goToPage = value;
    }

    /**
     *
     * @return
     */
    public Page getGoToPage() {
        return goToPage;
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

    /**
     *
     * @return
     */
    public boolean isShowText() {
        return showText;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Set<Result> getResultAnswers() {
        return resultAnswers;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Set<Result> getResultQuestions() {
        return resultQuestions;
    }

    /**
     *
     * @param resultAnswers
     */
    public void setResultAnswers(Set<Result> resultAnswers) {
        this.resultAnswers = resultAnswers;
    }

    /**
     *
     * @param resultQuestions
     */
    public void setResultQuestions(Set<Result> resultQuestions) {
        this.resultQuestions = resultQuestions;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
