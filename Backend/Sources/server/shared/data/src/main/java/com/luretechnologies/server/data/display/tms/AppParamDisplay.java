/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.display.tms;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Column;

/**
 *
 * @author michael
 */
public class AppParamDisplay {
    
    @Column(name = "id")
    @ApiModelProperty(value = "The table primary key.", required = true)
    private Long id;
    
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    
    @ApiModelProperty(value = "The Value.", required = true)
    private String value;
    
    @ApiModelProperty(value = "Last updated", required = false)
    private Date updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public AppParamDisplay() {
    }

    /**
     *
     * @param name
     * @param value
     */
    public AppParamDisplay(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
