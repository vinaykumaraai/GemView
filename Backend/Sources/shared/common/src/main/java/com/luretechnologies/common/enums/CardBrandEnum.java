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

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author developer
 */
public enum CardBrandEnum {

    /**
     * Visa Card
     *
     */
    VISA(1, "Visa", "Visa"),
    /**
     * MasterCard Card
     *
     */
    MASTERCARD(2, "MasterCard", "MasterCard"),
    /**
     * American Express Card
     *
     */
    AMEX(3, "Amex", "American Express"),
    /**
     * Discover Card
     *
     */
    DISCOVER(4, "Discover", "Discover"),
    /**
     * Debit Card
     *
     */
    DEBIT(5, "Debit", "Debit"),
    /**
     * ChinaUnion Card
     *
     */
    CHINAUNION(6, "ChinaUnion", "ChinaUnion"),
    /**
     * CarteBlanche Card
     *
     */
    CARTEBLANCHE(7, "CarteBlanche", "CarteBlanche"),
    /**
     * DinersClub Card
     *
     */
    DINERSCLUB(8, "DinersClub", "DinersClub"),
    /**
     * InstaPayment Card
     *
     */
    INSTAPAYMENT(9, "InstaPayment", "InstaPayment"),
    /**
     * JCB Card
     *
     */
    JCB(10, "JCB", "JCB"),
    /**
     * Maestro Card
     *
     */
    MAESTRO(11, "Maestro", "Maestro"),
    /**
     * Visa Electron Card
     *
     */
    VISAELECTRON(12, "Visa Electron", "Visa Electron"),
    /**
     * Other Card
     *
     */
    OTHER(13, "Other", "Other"),
    /**
     * PayPal Card
     *
     */
    PAYPAL(14, "PayPal", "PayPal"),
    /**
     * Laser Card
     *
     */
    LASER(15, "Laser", "Laser"),
    /**
     * NONE
     *
     */
    NONE(999, "None", "None");

    private final Integer id;
    private final String name;
    private final String description;

    private static final List<BIN> binList;

    // This is for JPA implementation
    // This is look up map to get Enum value from int value.
    CardBrandEnum(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param name
     * @return
     */
    public static Integer getIdFromName(String name) {
        for (CardBrandEnum enumItem : CardBrandEnum.values()) {
            if (enumItem.getName().toUpperCase().contains(name.toUpperCase())) {
                return enumItem.getId();
            }
        }
        return 0;
    }

    private static class BIN {

        String name;
        int binMin;
        int binMax;
        int binLen;
        int lenMin;
        int lenMax;
        String symbol;
        String group;

        public BIN(String name, int binMin, int binMax, int binLen, int lenMin, int lenMax, String symbol, String group) {
            this.name = name;
            this.binMin = binMin;
            this.binMax = binMax;
            this.binLen = binLen;
            this.lenMin = lenMin;
            this.lenMax = lenMax;
            this.symbol = symbol;
            this.group = group;
        }
    }

    static {

        binList = new ArrayList<>();

        // LAST UPDATE: 2017/05/26
        // Visa Electron
        binList.add(new BIN("Visa Electron", 4026, 4026, 4, 16, 19, "VE", "VI"));
        binList.add(new BIN("Visa Electron", 417500, 417500, 6, 16, 19, "VE", "VI"));
        binList.add(new BIN("Visa Electron", 4508, 4058, 4, 16, 19, "VE", "VI"));
        binList.add(new BIN("Visa Electron", 4844, 4844, 4, 16, 19, "VE", "VI"));
        binList.add(new BIN("Visa Electron", 4913, 4913, 4, 16, 19, "VE", "VI"));
        binList.add(new BIN("Visa Electron", 4917, 4917, 4, 16, 19, "VE", "VI"));

        // Visa - important to list after all Visa Electron as default "4"
        binList.add(new BIN("Visa", 4, 4, 1, 16, 19, "VI", "VI"));

        // MasterCard
        binList.add(new BIN("MasterCard", 222100, 272099, 6, 16, 16, "MC", "MC"));
        binList.add(new BIN("MasterCard", 510000, 559999, 6, 16, 16, "MC", "MC"));

        // Maestro
        binList.add(new BIN("Maestro", 5018, 5018, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 5020, 5020, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 5038, 5038, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 6304, 6304, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 6759, 6759, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 6761, 6761, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 6762, 6762, 4, 12, 19, "MA", "MC"));
        binList.add(new BIN("Maestro", 6763, 6763, 4, 12, 19, "MA", "MC"));

        // Discover
        binList.add(new BIN("Discover", 30000000, 30599999, 8, 16, 19, "CB", "DS"));
        binList.add(new BIN("Discover", 30950000, 30959999, 8, 16, 19, "DCI", "DS"));
        binList.add(new BIN("Discover", 35280000, 35899999, 8, 16, 19, "JCB", "DS"));
        binList.add(new BIN("Discover", 36000000, 36999999, 8, 14, 19, "DCI", "DS"));
        binList.add(new BIN("Discover", 38000000, 39999999, 8, 14, 19, "DCI", "DS"));   // should be 16,19. Changed for testing.
        binList.add(new BIN("Discover", 60110000, 60110399, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 60110400, 60110499, 8, 16, 19, "PP", "DS"));
        binList.add(new BIN("Discover", 60110500, 60110999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 60112000, 60114999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 60117400, 60117499, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 60117700, 60117999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 60118600, 60119999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 62212600, 62292599, 8, 16, 19, "CU", "DS"));
        binList.add(new BIN("Discover", 62400000, 62699999, 8, 16, 19, "CU", "DS"));
        binList.add(new BIN("Discover", 62820000, 62889999, 8, 16, 19, "CU", "DS"));
        binList.add(new BIN("Discover", 64400000, 65059999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 65060000, 65060099, 8, 16, 19, "PP", "DS"));
        binList.add(new BIN("Discover", 65060100, 65060999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 65061000, 65061099, 8, 16, 19, "PP", "DS"));
        binList.add(new BIN("Discover", 65061100, 65999999, 8, 16, 19, "DN", "DS"));
        binList.add(new BIN("Discover", 60111111, 60111111, 8, 16, 19, "DN", "DS"));

        // Amex
        binList.add(new BIN("Amex", 34, 34, 2, 15, 15, "AX", "AX"));
        binList.add(new BIN("Amex", 37, 37, 2, 15, 15, "AX", "AX"));

        // InstaPayment
        binList.add(new BIN("InstaPayment", 637, 639, 3, 16, 16, "IP", "IP"));

        // Laser
        binList.add(new BIN("Laser", 6304, 6304, 4, 16, 19, "LA", "LA"));
        binList.add(new BIN("Laser", 6706, 6706, 4, 16, 19, "LA", "LA"));
        binList.add(new BIN("Laser", 6771, 6771, 4, 16, 19, "LA", "LA"));
        binList.add(new BIN("Laser", 6709, 6709, 4, 16, 19, "LA", "LA"));
    }

    /**
     * @param pan
     * @return The card brand enum.
     */
    public static CardBrandEnum infer(String pan) {
        
        if( pan == null ) return CardBrandEnum.OTHER; // Julio just for avoid crash 
        switch (matchBIN(pan)) {

            case "AX":
                return CardBrandEnum.AMEX;
            case "CU":
                return CardBrandEnum.CHINAUNION;
            case "CB":
                return CardBrandEnum.CARTEBLANCHE;
            case "DCI":
                return CardBrandEnum.DINERSCLUB;
            case "DN":
                return CardBrandEnum.DISCOVER;
            case "IP":
                return CardBrandEnum.INSTAPAYMENT;
            case "JCB":
                return CardBrandEnum.JCB;
            case "LA":
                return CardBrandEnum.LASER;
            case "MA":
                return CardBrandEnum.MAESTRO;
            case "MC":
                return CardBrandEnum.MASTERCARD;
            case "PP":
                return CardBrandEnum.PAYPAL;
            case "VE":
                return CardBrandEnum.VISAELECTRON;
            case "VI":
                return CardBrandEnum.VISA;
            default:
                return CardBrandEnum.OTHER;
        }
    }

    private static String matchBIN(String card) {

        String brand = "OTHER";

        for (BIN b : binList) {

            int bin = Integer.parseInt(card.substring(0, b.binLen));
            if (bin >= b.binMin && bin <= b.binMax && card.length() >= b.lenMin && card.length() <= b.lenMax) {
                return b.symbol;
            }
        }

        return brand;
    }

    /**
     *
     * @param id
     * @return
     */
    public static final CardBrandEnum getCardBrandEnum(Integer id) {
        for (CardBrandEnum cardBrandEnum : CardBrandEnum.values()) {
            if (cardBrandEnum.getId().equals(id)) {
                return cardBrandEnum;
            }
        }
        return null;
    }

}
