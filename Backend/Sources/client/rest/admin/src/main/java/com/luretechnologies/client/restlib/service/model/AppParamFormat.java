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
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-24T13:45:45.337-04:00")
public class AppParamFormat {

    private Long id = null;
    
    private String name = null;
    
    private String value = null;

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
     * @return the value
     */
    @ApiModelProperty(value = "")
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    public AppParamFormat() {
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppParamFormat {\n");

        sb.append("    id: ").append(StringUtil.toIndentedString(getId())).append("\n");
        sb.append("    name: ").append(StringUtil.toIndentedString(getName())).append("\n");
        sb.append("    value: ").append(StringUtil.toIndentedString(getValue())).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
}
