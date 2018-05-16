/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Appliance_App_File_Value")
@Table(name = "Appliance_App_File_Value")
@NamedQueries({
    @NamedQuery(name = "ApplianceAppFileValue.findAll", query = "SELECT a FROM Appliance_App_File_Value a")})
public class ApplianceAppFileValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "Value")
    @ApiModelProperty(value = "The Value.", required = true)
    private String value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UpdatedAt")
    @ApiModelProperty(value = "The UpdatedAt.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "ApplianceApp", referencedColumnName = "id")
    @ApiModelProperty(value = "The ApplianceApp.", required = true)
    @ManyToOne(optional = false)
    private ApplianceApp applianceApp;
    @JoinColumn(name = "AppProfileFileValue", referencedColumnName = "AppProfile")
    @ApiModelProperty(value = "The AppProfileFileValue.", required = true)
    @ManyToOne(optional = false)
    private AppProfileFileValue appProfileFileValue;

    public ApplianceAppFileValue() {
    }

    public ApplianceAppFileValue(Long id) {
        this.id = id;
    }

    public ApplianceAppFileValue(Long id, String value, Date updatedAt) {
        this.id = id;
        this.value = value;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ApplianceApp getApplianceApp() {
        return applianceApp;
    }

    public void setApplianceApp(ApplianceApp applianceApp) {
        this.applianceApp = applianceApp;
    }

    public AppProfileFileValue getAppProfileFileValue() {
        return appProfileFileValue;
    }

    public void setAppProfileFileValue(AppProfileFileValue appProfileFileValue) {
        this.appProfileFileValue = appProfileFileValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplianceAppFileValue)) {
            return false;
        }
        ApplianceAppFileValue other = (ApplianceAppFileValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.Appliance_App_File_Value[ id=" + id + " ]";
    }
    
}
