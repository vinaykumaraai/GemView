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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Stores all surveys.
 */
@Entity
@Table(name = "Survey")
public class Survey implements Serializable {

    /**
     *
     */
    public Survey() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", nullable = false, length = 244)
    private String description;

    @Column(name = "start_date", nullable = false)
    //@Generated(GenerationTime.INSERT)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    //@Generated(GenerationTime.INSERT)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "survey", targetEntity = Result.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Result> results = new HashSet<>();

//    @OneToMany(mappedBy = "survey", targetEntity = EntitySurvey.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private Set<EntitySurvey> entitySurveys = new HashSet<>();
    @OneToMany(mappedBy = "survey", targetEntity = SurveyPage.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<SurveyPage> surveyPages = new HashSet<>();

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
     * Survey's name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Survey's name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Survey's description.
     *
     * @param value
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Survey's description.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Survey's starting date.
     *
     * @param value
     */
    public void setStartDate(Timestamp value) {
        this.startDate = value;
    }

    /**
     * Survey's starting date.
     *
     * @return
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * Survey's ending date.
     *
     * @param value
     */
    public void setEndDate(Timestamp value) {
        this.endDate = value;
    }

    /**
     * Survey's ending date.
     *
     * @return
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * Survey's creation date.
     *
     * @param value
     */
    public void setCreatedAt(Timestamp value) {
        this.createdAt = value;
    }

    /**
     * Survey's creation date.
     *
     * @return
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Survey's last update date.
     *
     * @param value
     */
    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    /**
     * Survey's last update date.
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
    public void setResults(java.util.Set<Result> value) {
        this.results = value;
    }

    /**
     *
     * @param entitySurveys
     */
//    public void setEntitySurveys(Set<EntitySurvey> entitySurveys) {
//        this.entitySurveys = entitySurveys;
//    }
    /**
     *
     * @param surveyPages
     */
    public void setSurveyPages(Set<SurveyPage> surveyPages) {
        this.surveyPages = surveyPages;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public java.util.Set<Result> getResults() {
        return results;
    }

    /**
     *
     * @return
     */
    //   @JsonIgnore
//    public Set<EntitySurvey> getEntitySurveys() {
//        return entitySurveys;
//    }
    /**
     *
     * @return
     */
    @JsonIgnore
    public Set<SurveyPage> getSurveyPages() {
        return surveyPages;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
