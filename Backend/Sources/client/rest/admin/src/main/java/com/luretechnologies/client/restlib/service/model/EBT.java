/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.common.enums.BenefitTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 *
 *
 *
 * @author developer
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-11-09T17:35:21.421-05:00")
public class EBT implements Serializable {

    protected BenefitTypeEnum benefitType = BenefitTypeEnum.NONE;
    protected String ebtVoucherNumber = null;
    protected String ebtVoucherApprovalCode = null;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Lodging {\n");
        //sb.append("    ebtVoucherNumber: ").append(StringUtil.toIndentedString(getEbtVoucherNumber())).append("\n");
        //sb.append("    ebtVoucherApprovalCode: ").append(StringUtil.toIndentedString(getEbtVoucherApprovalCode())).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * @return the benefitType
     */
    @ApiModelProperty(value = "Benefit Type.")
    @JsonProperty("benefitType")
    public BenefitTypeEnum getBenefitType() {
        return benefitType;
    }

    /**
     * @param benefitType the benefitType to set
     */
    public void setBenefitType(BenefitTypeEnum benefitType) {
        this.benefitType = benefitType;
    }

    /**
     * @return the ebtVoucherNumber
     */
    @ApiModelProperty(value = "The voucher number used for the EBT transaction.")
    @JsonProperty("ebtVoucherNumber")
    public String getEbtVoucherNumber() {
        return ebtVoucherNumber;
    }

    /**
     * @param ebtVoucherNumber the ebtVoucherNumber to set
     */
    public void setEbtVoucherNumber(String ebtVoucherNumber) {
        this.ebtVoucherNumber = ebtVoucherNumber;
    }

    /**
     * @return the ebtVoucherApprovalCode
     */
    @ApiModelProperty(value = "The approval code received for the EBT transaction.")
    @JsonProperty("ebtVoucherApprovalCode")
    public String getEbtVoucherApprovalCode() {
        return ebtVoucherApprovalCode;
    }

    /**
     * @param ebtVoucherApprovalCode the ebtVoucherApprovalCode to set
     */
    public void setEbtVoucherApprovalCode(String ebtVoucherApprovalCode) {
        this.ebtVoucherApprovalCode = ebtVoucherApprovalCode;
    }

}
