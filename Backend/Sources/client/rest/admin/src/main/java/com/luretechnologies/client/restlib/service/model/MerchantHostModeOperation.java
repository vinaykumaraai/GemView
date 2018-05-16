/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.common.enums.ModeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Generated;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class MerchantHostModeOperation {

    private final Long id = null;
    private Merchant merchant = null;
    private HostModeOperation hostModeOperation = null;

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("merchant")
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant; 
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "")
    @JsonProperty("hostModeOperation")
    public HostModeOperation getHostModeOperation() {
        return hostModeOperation;
    }

    public void setHostModeOperation(HostModeOperation hostModeOperation) {
        this.hostModeOperation = hostModeOperation;
    }

}
