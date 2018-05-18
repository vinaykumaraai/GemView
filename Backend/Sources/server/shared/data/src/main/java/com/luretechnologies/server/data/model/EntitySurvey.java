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

import java.io.Serializable;

/**
 * Store which surveys are going to be run per Organization, Region and/or
 * Merchant.
 */
//@Entity
//@Table(name = "Entity_Survey", uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"survey", "organization"}),
//    @UniqueConstraint(columnNames = {"survey", "region"}),
//    @UniqueConstraint(columnNames = {"survey", "merchant"})})
public class EntitySurvey implements Serializable {

    /**
     *
     */
    public EntitySurvey() {
    }

//    @Column(name = "id", nullable = false, length = 20)
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @ManyToOne(targetEntity = Survey.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//     @JoinColumns({
//        @JoinColumn(name = "survey", referencedColumnName = "id", nullable = false)})
//    private Survey survey;
//
//    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
//    @JoinColumns({
//        @JoinColumn(name = "organization", referencedColumnName = "id", nullable = false)})
//    private Organization organization;
//
//    @ManyToOne(targetEntity = Region.class, fetch = FetchType.LAZY)
//    @JoinColumns({
//        @JoinColumn(name = "region", referencedColumnName = "id", nullable = false)})
//    private Region region;
//
//    @ManyToOne(targetEntity = Merchant.class, fetch = FetchType.LAZY)
//    @JoinColumns({
//        @JoinColumn(name = "merchant", referencedColumnName = "id", nullable = false)})
//    private Merchant merchant;
//
//    @Column(name = "starting_survey", nullable = false, length = 1)
//    private Boolean startingSurvey;
    /**
     * First survey to be run in the Region.
     *
     * @param value
     */
//    public void setStartingSurvey(Boolean value) {
//        this.startingSurvey = value;
//    }
//
//    /**
//     * First survey to be run in the Region.
//     *
//     * @return
//     */
//    public Boolean getStartingSurvey() {
//        return startingSurvey;
//    }
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
//
//    /**
//     *
//     * @param value
//     */
//    public void setRegion(Region value) {
//        this.region = value;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public Region getRegion() {
//        return region;
//    }
//
//    /**
//     *
//     * @param value
//     */
//    public void setSurvey(Survey value) {
//        this.survey = value;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public Survey getSurvey() {
//        return survey;
//    }
//
//    /**
//     *
//     * @param value
//     */
//    public void setOrganization(Organization value) {
//        this.organization = value;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public Organization getOrganization() {
//        return organization;
//    }
//
//    /**
//     *
//     * @param value
//     */
//    public void setMerchant(Merchant value) {
//        this.merchant = value;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public Merchant getMerchant() {
//        return merchant;
//    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
