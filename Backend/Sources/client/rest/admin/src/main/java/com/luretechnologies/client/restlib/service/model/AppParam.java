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
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T11:20:56.337-04:00")
public class AppParam {

    private Long id = null;
    
    private String name = null;
    
    private String description = null;
    
    private String defaultValue = null;
    
    private Boolean modifiable = null;
    
    private String restrict = null;
    
    private String regex = null;
    
    private Boolean forceUpdate = null;
    
    private Long appId = null;
    
    private EntityLevel entityLevel = null;
    
    private AppParamFormat appParamFormat = null;
    
    private Action action = null;

    public AppParam() {
    }
    
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
     * @return the name
     */
    @ApiModelProperty(value = "")
    @JsonProperty("name")
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
     * @return the description
     */
    @ApiModelProperty(value = "")
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the modifiable
     */
    @ApiModelProperty(value = "")
    @JsonProperty("modifiable")
    public Boolean getModifiable() {
        return modifiable;
    }

    /**
     * @param modifiable the modifiable to set
     */
    public void setModifiable(Boolean modifiable) {
        this.modifiable = modifiable;
    }

    /**
     * @return the restrict
     */
    @ApiModelProperty(value = "")
    @JsonProperty("restrict")
    public String getRestrict() {
        return restrict;
    }

    /**
     * @param restrict the restrict to set
     */
    public void setRestrict(String restrict) {
        this.restrict = restrict;
    }

    /**
     * @return the regex
     */
    @ApiModelProperty(value = "")
    @JsonProperty("regex")
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(String regex) {
        this.regex = regex;
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
     * @return the appId
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appId")
    public Long getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * @return the entityLevel
     */
    @ApiModelProperty(value = "")
    @JsonProperty("entityLevel")
    public EntityLevel getEntityLevel() {
        return entityLevel;
    }

    /**
     * @param entityLevel the entityLevel to set
     */
    public void setEntityLevel(EntityLevel entityLevel) {
        this.entityLevel = entityLevel;
    }

    /**
     * @return the appParamFormat
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appParamFormat")
    public AppParamFormat getAppParamFormat() {
        return appParamFormat;
    }

    /**
     * @param appParamFormat the appParamFormat to set
     */
    public void setAppParamFormat(AppParamFormat appParamFormat) {
        this.appParamFormat = appParamFormat;
    }

    /**
     * @return the action
     */
    @ApiModelProperty(value = "")
    @JsonProperty("action")
    public Action getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppParam {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(getDescription())).append("\n");
        sb.append("    modifiable: ").append(StringUtil.toIndentedString(getModifiable())).append("\n");
        sb.append("    restrict: ").append(StringUtil.toIndentedString(getRestrict())).append("\n");
        sb.append("    regex: ").append(StringUtil.toIndentedString(getRegex())).append("\n");
        sb.append("    forceUpdate: ").append(StringUtil.toIndentedString(getForceUpdate())).append("\n");
        sb.append("    appId: ").append(StringUtil.toIndentedString(appId)).append("\n");
        sb.append("    entityLevel: ").append(StringUtil.toIndentedString(entityLevel)).append("\n");
        sb.append("    appParamFormat: ").append(StringUtil.toIndentedString(appParamFormat)).append("\n");
        sb.append("    action: ").append(StringUtil.toIndentedString(action)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
