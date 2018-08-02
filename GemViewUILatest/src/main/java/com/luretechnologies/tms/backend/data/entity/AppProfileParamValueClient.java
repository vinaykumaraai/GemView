/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.tms.backend.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Generated;

@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T15:34:45.337-04:00")
public class AppProfileParamValueClient {

    private Long id = null;
    
    private String defaultValue;
    
    private Boolean forceUpdate;
    
    private Long appProfileId;
    
    private Long appParamId;
    
    public AppProfileParamValueClient(Long id, String defaultValue, Boolean forceUpdate, Long appProfileId, Long appParamId) {
    	super();
    	this.id=id;
    	this.defaultValue=defaultValue;
    	this.forceUpdate=forceUpdate;
    	this.appProfileId=appProfileId;
    	this.appParamId=appParamId;
    			
    }
    
    public AppProfileParamValueClient() {
    			
    }
    
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
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the forceUpdate
     */
    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    /**
     * @param forceUpdate the forceUpdate to set
     */
    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    /**
     * @return the appProfile
     */
    public Long getAppProfileId() {
        return appProfileId;
    }

    /**
     * @param appProfileId
     */
    public void setAppProfileId(Long appProfileId) {
        this.appProfileId = appProfileId;
    }

    /**
     * @return the appParam
     */
    public Long getAppParamId() {
        return appParamId;
    }

    /**
     * @param appParamId
     */
    public void setAppParamId(Long appParamId) {
        this.appParamId = appParamId;
    }

    @Override
    public String toString() {
        return defaultValue;
    }
    
}
