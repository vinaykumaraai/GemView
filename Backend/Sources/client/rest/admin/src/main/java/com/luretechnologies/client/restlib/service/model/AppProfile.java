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
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T15:20:45.337-04:00")
public class AppProfile {

    private Long id = null;
    
    private String name = null;
    
    private Boolean active = null;
    
    private List<AppProfileParamValue> appprofileparamvalueCollection = null;
    
    private List<EntityAppProfile> entityappprofileCollection = null;
    
    private List<AppProfileFileValue> appprofilefilevalueCollection = null;
    
    private Long appId = null;
    
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
     * @return the appprofileparamvalueCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileParamValueCollection")
    public List<AppProfileParamValue> getAppProfileParamValueCollection() {
        return appprofileparamvalueCollection;
    }

    /**
     * @param appprofileparamvalueCollection the appprofileparamvalueCollection to set
     */
    public void setAppProfileParamValueCollection(List<AppProfileParamValue> appprofileparamvalueCollection) {
        this.appprofileparamvalueCollection = appprofileparamvalueCollection;
    }

    /**
     * @return the entityAppProfileCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("entityAppProfileCollection")
    public List<EntityAppProfile> getEntityAppProfileCollection() {
        return entityappprofileCollection;
    }

    /**
     * @param entityappprofileCollection
     */
    
    public void setEntityAppProfileCollection(List<EntityAppProfile> entityappprofileCollection) {
        this.entityappprofileCollection = entityappprofileCollection;
    }

    /**
     * @return the appprofilefilevalueCollection
     */
    @ApiModelProperty(value = "")
    @JsonProperty("appProfileFileValueCollection")
    public List<AppProfileFileValue> getAppProfileFileValueCollection() {
        return appprofilefilevalueCollection;
    }

    /**
     * @param appprofilefilevalueCollection the appprofilefilevalueCollection to set
     */
    public void setAppProfileFileValueCollection(List<AppProfileFileValue> appprofilefilevalueCollection) {
        this.appprofilefilevalueCollection = appprofilefilevalueCollection;
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

    public AppProfile() {
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppProfile {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    active: ").append(StringUtil.toIndentedString(getActive())).append("\n");
        sb.append("    appprofileparamvalueCollection: ").append(StringUtil.toIndentedString(getAppProfileParamValueCollection())).append("\n");
        sb.append("    entityappprofileCollection: ").append(StringUtil.toIndentedString(getEntityAppProfileCollection())).append("\n");
        sb.append("    appprofilefilevalueCollection: ").append(StringUtil.toIndentedString(getAppProfileFileValueCollection())).append("\n");
        sb.append("    appId: ").append(StringUtil.toIndentedString(appId)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
