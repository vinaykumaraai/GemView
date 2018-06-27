/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModelProperty;

public class AuditUserLogType {


    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;

    public AuditUserLogType() {
    }
    /**
     * @return the id
     */
    @ApiModelProperty(value = "Id")
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
    @ApiModelProperty(value = "Name")
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
    @ApiModelProperty(value = "Description")
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuditAction {\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(getDescription())).append("\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("}");
        return sb.toString();
    }    
    
}