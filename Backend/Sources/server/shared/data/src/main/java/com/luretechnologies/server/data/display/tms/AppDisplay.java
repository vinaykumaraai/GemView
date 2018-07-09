/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.display.tms;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;

/**
 *
 * @author michael
 */
public class AppDisplay {
    
    @Column(name = "id")
    @ApiModelProperty(value = "The table primary key.", required = true)
    private Long id;
    
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    
    @ApiModelProperty(value = "The Version.", required = true)
    private String version;
    
    @ApiModelProperty(value = "Last updated", required = false)
    private Date updatedAt;
    
    @ApiModelProperty(value = "Params.", required = false)
    private List<AppParamDisplay> appParams = null;

    @ApiModelProperty(value = "Files", required = false)
    private List<AppFileDisplay> appFiles = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public AppDisplay() {
        appFiles = new ArrayList<>();
        appParams = new ArrayList<>();
    }

    /**
     *
     * @param name
     * @param version
     */
    public AppDisplay(String name, String version) {
        super();
        this.name = name;
        this.version = version;
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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the appParams
     */
    public List<AppParamDisplay> getAppParams() {
        return appParams;
    }

    /**
     * @param appParams the appParams to set
     */
    public void setAppParams(List<AppParamDisplay> appParams) {
        this.appParams = appParams;
    }

    /**
     * @return the appFiles
     */
    public List<AppFileDisplay> getAppFiles() {
        return appFiles;
    }

    /**
     * @param appFiles the appFiles to set
     */
    public void setAppFiles(List<AppFileDisplay> appFiles) {
        this.appFiles = appFiles;
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
