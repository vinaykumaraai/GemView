/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Entity_Level")
@Table(name = "Entity_Level")
@NamedQueries({
    @NamedQuery(name = "EntityLevel.findAll", query = "SELECT e FROM Entity_Level e")})
public class EntityLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "entityLevel", fetch = FetchType.LAZY)
    private Collection<AppParam> appparamCollection;*/

    public EntityLevel() {
    }

    public EntityLevel(Long id) {
        this.id = id;
    }

    public EntityLevel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Collection<AppParam> getAppParamCollection() {
        return appparamCollection;
    }

    public void setAppParamCollection(Collection<AppParam> appparamCollection) {
        this.appparamCollection = appparamCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityLevel)) {
            return false;
        }
        EntityLevel other = (EntityLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.EntityLevel[ id=" + id + " ]";
    }
    
}
