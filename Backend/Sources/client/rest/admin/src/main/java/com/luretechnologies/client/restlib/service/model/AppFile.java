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
import java.util.List;
import javax.annotation.Generated;

@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T14:45:45.337-04:00")
public class AppFile {

    private Long id = null;
    
    private String name = null;
    
    private String description = null;
    
    private String defaultValue = null;
    
    private Boolean modifiable = null;
    
    private Boolean forceUpdate = null;
    
    private Long appId = null;
    
    private AppFileFormat appFileFormat = null;
    
    private List<AppFile> appprofilefilevalueCollection = null;

    public AppFile() {
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
    @JsonProperty("getModifiable")
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
     * @return the forceUpdate
     */
    @ApiModelProperty(value = "")
    @JsonProperty("getForceUpdate")
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
     * @return the appFileFormat
     */
    @ApiModelProperty(value = "")
    @JsonProperty("getAppFileFormat")
    public AppFileFormat getAppFileFormat() {
        return appFileFormat;
    }

    /**
     * @param appFileFormat the appFileFormat to set
     */
    public void setAppFileFormat(AppFileFormat appFileFormat) {
        this.appFileFormat = appFileFormat;
    }

    /**
     * @return the appprofilefilevalueCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileFileValueCollection")
    public List<AppFile> getAppprofilefilevalueCollection() {
        return appprofilefilevalueCollection;
    }

    /**
     * @param appprofilefilevalueCollection the appprofilefilevalueCollection to set
     */
    public void setAppprofilefilevalueCollection(List<AppFile> appprofilefilevalueCollection) {
        this.appprofilefilevalueCollection = appprofilefilevalueCollection;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppFile {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(getDescription())).append("\n");
        sb.append("    defaultValue: ").append(StringUtil.toIndentedString(getDefaultValue())).append("\n");
        sb.append("    modifiable: ").append(StringUtil.toIndentedString(getModifiable())).append("\n");
        sb.append("    forceUpdate: ").append(StringUtil.toIndentedString(getForceUpdate())).append("\n");
        sb.append("    appId: ").append(StringUtil.toIndentedString(appId)).append("\n");
        sb.append("    appFileFormat: ").append(StringUtil.toIndentedString(appFileFormat)).append("\n");
        sb.append("    appprofilefilevalueCollection: ").append(StringUtil.toIndentedString(getAppprofilefilevalueCollection())).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
}
