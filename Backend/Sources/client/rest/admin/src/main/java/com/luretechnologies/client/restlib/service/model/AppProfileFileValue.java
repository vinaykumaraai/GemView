/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Generated;

@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T15:30:45.337-04:00")
public class AppProfileFileValue {

    private Long id = null;
    
    private String defaultValue = null;
    
    private Boolean forceUpdate = null;
    
    private Long appProfileId = null;
    
    private Long appFileId = null;
    
    /**
     * @return the id
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
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
    @ApiModelProperty(value = "")
    @JsonProperty("defaultValue")
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
    @ApiModelProperty(value = "")
    @JsonProperty("forceUpdate")
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
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileId")
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
     * @return the appFileId
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appFileId")
    public Long getAppFileId() {
        return appFileId;
    }

    /**
     * @param appFileId the appFileId to set
     */
    public void setAppFileId(Long appFileId) {
        this.appFileId = appFileId;
    }

    public AppProfileFileValue() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppProfileFileValue {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    defaultValue: ").append(StringUtil.toIndentedString(getDefaultValue())).append("\n");
        sb.append("    forceUpdate: ").append(StringUtil.toIndentedString(getForceUpdate())).append("\n");
        sb.append("    appProfileId: ").append(StringUtil.toIndentedString(getAppProfileId())).append("\n");
        sb.append("    appFileId: ").append(StringUtil.toIndentedString(getAppFileId())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
