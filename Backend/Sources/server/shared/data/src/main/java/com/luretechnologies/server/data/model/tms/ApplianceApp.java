/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Terminal;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity(name = "Appliance_App")
@Table(name = "Appliance_App")
@NamedQueries({
    @NamedQuery(name = "ApplianceApp.findAll", query = "SELECT a FROM Appliance_App a")})
public class ApplianceApp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @ApiModelProperty(value = "The CreateAt.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @ApiModelProperty(value = "The UpdatedAt.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applianceApp")
    private Collection<ApplianceAppFileValue> applianceappfilevalueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applianceApp")
    private Collection<ApplianceAppParamValue> applianceappparamvalueCollection;
    @JoinColumn(name = "App_Profile", referencedColumnName = "id")
    @ApiModelProperty(value = "The App Profile.", required = true)
    @ManyToOne(optional = false)
    private AppProfile appProfile;
    @JoinColumn(name = "Terminal", referencedColumnName = "id")
    @ApiModelProperty(value = "The Terminal.", required = true)
    @ManyToOne(optional = false)
    private Terminal terminal;
    @JoinColumn(name = "Device", referencedColumnName = "id")
    @ApiModelProperty(value = "The Device.", required = true)
    @ManyToOne(optional = false)
    private Device device;

    public ApplianceApp() {
    }

    public ApplianceApp(Long id) {
        this.id = id;
    }

    public ApplianceApp(Long id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<ApplianceAppFileValue> getApplianceAppFileValueCollection() {
        return applianceappfilevalueCollection;
    }

    public void setApplianceAppFileValueCollection(Collection<ApplianceAppFileValue> applianceappfilevalueCollection) {
        this.applianceappfilevalueCollection = applianceappfilevalueCollection;
    }

    public Collection<ApplianceAppParamValue> getApplianceAppParamValueCollection() {
        return applianceappparamvalueCollection;
    }

    public void setApplianceAppParamValueCollection(Collection<ApplianceAppParamValue> applianceappparamvalueCollection) {
        this.applianceappparamvalueCollection = applianceappparamvalueCollection;
    }

    public AppProfile getAppProfile() {
        return appProfile;
    }

    public void setAppProfile(AppProfile appProfile) {
        this.appProfile = appProfile;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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
        if (!(object instanceof ApplianceApp)) {
            return false;
        }
        ApplianceApp other = (ApplianceApp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.Appliance_App[ id=" + id + " ]";
    }
    
}
