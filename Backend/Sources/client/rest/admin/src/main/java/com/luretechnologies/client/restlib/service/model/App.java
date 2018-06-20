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
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T11:20:45.337-04:00")
public class App {

    private Long id = null;
    
    private String name = null;
    
    private String description = null;
    
    private String version = null;
    
    private Boolean active = null;
    
    private Boolean available = null;
    
    private Long ownerId = null;
        
    private List<AppFile> appfileCollection = null;
    private List<AppParam> appparamCollection = null;
    private List<AppProfile> appprofileCollection = null;

    public App() {
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
     * @return the version
     */
    @ApiModelProperty(value = "")
    @JsonProperty("version")
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
     * @return the active
     */
    @ApiModelProperty(value = "")
    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    /**
     * @return the ownerId
     */
    @ApiModelProperty(value = "")
    @JsonProperty("ownerId")
    public long getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the available
     */
    @ApiModelProperty(value = "")
    @JsonProperty("available")
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    /**
     * @return the appfileCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appFileCollection")
    public List<AppFile> getAppfileCollection() {
        return appfileCollection;
    }

    /**
     * @param appfileCollection the appfileCollection to set
     */
    public void setAppfileCollection(List<AppFile> appfileCollection) {
        this.appfileCollection = appfileCollection;
    }

    /**
     * @return the appparamCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appParamCollection")
    public List<AppParam> getAppparamCollection() {
        return appparamCollection;
    }

    /**
     * @param appparamCollection the appparamCollection to set
     */
    public void setAppparamCollection(List<AppParam> appparamCollection) {
        this.appparamCollection = appparamCollection;
    }

    /**
     * @return the appprofileCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileCollection")
    public List<AppProfile> getAppprofileCollection() {
        return appprofileCollection;
    }

    /**
     * @param appprofileCollection the appprofileCollection to set
     */
    public void setAppprofileCollection(List<AppProfile> appprofileCollection) {
        this.appprofileCollection = appprofileCollection;
    }
        
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class App {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(getDescription())).append("\n");
        sb.append("    version: ").append(StringUtil.toIndentedString(getVersion())).append("\n");
        sb.append("    active: ").append(StringUtil.toIndentedString(getActive())).append("\n");
        sb.append("    available: ").append(StringUtil.toIndentedString(getAvailable())).append("\n");
        sb.append("    ownerId: ").append(StringUtil.toIndentedString(ownerId)).append("\n");
        sb.append("    appfileCollection: ").append(StringUtil.toIndentedString(getAppfileCollection())).append("\n");
        sb.append("    appparamCollection: ").append(StringUtil.toIndentedString(getAppparamCollection())).append("\n");
        sb.append("    appprofileCollection: ").append(StringUtil.toIndentedString(getAppprofileCollection())).append("\n");
        sb.append("}");
        return sb.toString();
    } 
    
}
