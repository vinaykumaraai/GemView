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
package com.luretechnologies.common.enums;

/**
 *
 * Suarez
 */
/**
 * Nomenclature class
 *
 */
public enum ControlEnum {

    /**
     * Label Information
     */
    LBL_INF(1),
    /**
     * Label Question
     */
    LBL_QST(2),
    /**
     * Check box Answer
     */
    CHK_ASW(3),
    /**
     * Button Answer
     */
    BTN_ASW(4),
    /**
     * Button Navigation
     */
    BTN_NAV(5),
    /**
     * Button Cancel
     */
    BTN_CCL(6);

    private long id;

    private ControlEnum(long id) {
        this.id = id;
    }

    /**
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return the abbreviation
     */
    public String getShortText() {
        if (this.toString().equals(LBL_INF.toString())) {
            return "LBL_INF";
        }
        if (this.toString().equals(LBL_QST.toString())) {
            return "LBL_QST";
        }
        if (this.toString().equals(CHK_ASW.toString())) {
            return "CHK_ASW";
        }
        if (this.toString().equals(BTN_ASW.toString())) {
            return "BTN_ASW";
        }
        if (this.toString().equals(BTN_NAV.toString())) {
            return "BTN_NAV";
        }
        if (this.toString().equals(BTN_CCL.toString())) {
            return "BTN_CCL";
        }
        return "";
    }

    /**
     *
     * @param control
     * @return the ControlEnum from the String
     */
    public static ControlEnum fromString(String control) {
        if (control.equals(LBL_INF.toString())) {
            return ControlEnum.LBL_INF;
        }
        if (control.equals(LBL_QST.toString())) {
            return ControlEnum.LBL_QST;
        }
        if (control.equals(CHK_ASW.toString())) {
            return ControlEnum.CHK_ASW;
        }
        if (control.equals(BTN_ASW.toString())) {
            return ControlEnum.BTN_ASW;
        }
        if (control.equals(BTN_NAV.toString())) {
            return ControlEnum.BTN_NAV;
        }
        if (control.equals(BTN_CCL.toString())) {
            return ControlEnum.BTN_CCL;
        }
        return ControlEnum.LBL_INF;
    }

    /**
     *
     * @return the name ControlEnum
     */
    public String getName() {
        switch (this) {
            case LBL_INF:
                return "Info Label";
            case LBL_QST:
                return "Question";
            case CHK_ASW:
                return "Checkbox Selection";
            case BTN_ASW:
                return "Answer Button";
            case BTN_NAV:
                return "Navigation Button";
            case BTN_CCL:
                return "Cancel Button";
            default:
                return "";
        }
    }

    /**
     *
     * @return the action default 0 (no action) 1 Yes 2 maybe or maybe not
     */
    public Integer getAction() {
        switch (this) {
            case LBL_INF:
                return 0;

            case LBL_QST:
                return 0;

            case CHK_ASW:
                return 0;

            case BTN_ASW:
                return 2;

            case BTN_NAV:
                return 1;

            case BTN_CCL:
                return 1;
            default:
                return 0;
        }
    }

    /**
     *
     * @return the Description
     */
    public String getDescription() {
        if (this.toString().equals(LBL_INF.toString())) {
            return "General informational text.";
        }
        if (this.toString().equals(LBL_QST.toString())) {
            return "The question to be asked.";
        }
        if (this.toString().equals(CHK_ASW.toString())) {
            return "Checkbox answer choice.";
        }
        if (this.toString().equals(BTN_ASW.toString())) {
            return "Button answer choice.";
        }
        if (this.toString().equals(BTN_NAV.toString())) {
            return "Button used to navigate to another page.";
        }
        if (this.toString().equals(BTN_CCL.toString())) {
            return "Button used to cancel the survey.";
        }
        return "";
    }
}
