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
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Table which stores the form package.
 */
@Entity
@Table(name = "Form_Package")
public class FormPackage implements Serializable {

    /**
     * Constructor
     *
     */
    public FormPackage() {
    }

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "version", nullable = false, length = 1)
    private String version;

    @Column(name = "file", nullable = false)
    private Blob file;

    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

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
     * Form package name.
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Form package name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Form package version.
     *
     * @param value
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Form package version.
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * Binary which describes the forms.
     *
     * @param value
     */
    public void setFile(Blob value) {
        this.file = value;
    }

    /**
     * Binary which describes the forms.
     *
     * @return
     */
    public Blob getFile() {
        return file;
    }

    /**
     * Form package file name
     *
     * @param value
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Form package file name
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
