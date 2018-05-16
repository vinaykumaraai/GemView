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

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author developer
 */
public enum CurrencyEnum {

    /**
     *
     */
    USD(1, "840"),
    /**
     *
     */
    BTN(2, "064"),
    /**
     *
     */
    INR(3, "356"),
    /**
     *
     */
    BOB(4, "068"),
    /**
     *
     */
    BOV(5, "984"),
    /**
     *
     */
    BAM(6, "977"),
    /**
     *
     */
    BWP(7, "072"),
    /**
     *
     */
    NOK(8, "578"),
    /**
     *
     */
    BRL(9, "986"),
    /**
     *
     */
    BGN(10, "975"),
    /**
     *
     */
    XOF(11, "952"),
    /**
     *
     */
    BIF(12, "108"),
    /**
     *
     */
    CVE(13, "132"),
    /**
     *
     */
    KHR(14, "116"),
    /**
     *
     */
    XAF(15, "950"),
    /**
     *
     */
    CAD(16, "124"),
    /**
     *
     */
    KYD(17, "136"),
    /**
     *
     */
    CLF(18, "990"),
    /**
     *
     */
    CLP(19, "152"),
    /**
     *
     */
    CNY(20, "156"),
    /**
     *
     */
    AUD(21, "036"),
    /**
     *
     */
    COP(22, "170"),
    /**
     *
     */
    COU(23, "970"),
    /**
     *
     */
    KMF(24, "174"),
    /**
     *
     */
    CDF(25, "976"),
    /**
     *
     */
    NZD(26, "554"),
    /**
     *
     */
    CRC(27, "188"),
    /**
     *
     */
    HRK(28, "191"),
    /**
     *
     */
    CUC(29, "931"),
    /**
     *
     */
    CUP(30, "192"),
    /**
     *
     */
    ANG(31, "532"),
    /**
     *
     */
    EUR(32, "978"),
    /**
     *
     */
    CZK(33, "203"),
    /**
     *
     */
    DKK(34, "208"),
    /**
     *
     */
    DJF(35, "262"),
    /**
     *
     */
    XCD(36, "951"),
    /**
     *
     */
    DOP(37, "214"),
    /**
     *
     */
    EGP(38, "818"),
    /**
     *
     */
    SVC(39, "222"),
    /**
     *
     */
    ERN(40, "232"),
    /**
     *
     */
    ETB(41, "230"),
    /**
     *
     */
    FKP(42, "238"),
    /**
     *
     */
    FJD(43, "242"),
    /**
     *
     */
    XPF(44, "953"),
    /**
     *
     */
    GMD(45, "270"),
    /**
     *
     */
    GEL(46, "981"),
    /**
     *
     */
    GHS(47, "936"),
    /**
     *
     */
    GIP(48, "292"),
    /**
     *
     */
    GTQ(49, "320"),
    /**
     *
     */
    GBP(50, "826"),
    /**
     *
     */
    GNF(51, "324"),
    /**
     *
     */
    GYD(52, "328"),
    /**
     *
     */
    HTG(53, "332"),
    /**
     *
     */
    HNL(54, "340"),
    /**
     *
     */
    HKD(55, "344"),
    /**
     *
     */
    HUF(56, "348"),
    /**
     *
     */
    ISK(57, "352"),
    /**
     *
     */
    IDR(58, "360"),
    /**
     *
     */
    XDR(59, "960"),
    /**
     *
     */
    IRR(60, "364"),
    /**
     *
     */
    IQD(61, "368"),
    /**
     *
     */
    ILS(62, "376"),
    /**
     *
     */
    JMD(63, "388"),
    /**
     *
     */
    JPY(64, "392"),
    /**
     *
     */
    JOD(65, "400"),
    /**
     *
     */
    KZT(66, "398"),
    /**
     *
     */
    KES(67, "404"),
    /**
     *
     */
    KPW(68, "408"),
    /**
     *
     */
    KRW(69, "410"),
    /**
     *
     */
    KWD(70, "414"),
    /**
     *
     */
    KGS(71, "417"),
    /**
     *
     */
    LAK(72, "418"),
    /**
     *
     */
    LBP(73, "422"),
    /**
     *
     */
    LSL(74, "426"),
    /**
     *
     */
    ZAR(75, "710"),
    /**
     *
     */
    LRD(76, "430"),
    /**
     *
     */
    LYD(77, "434"),
    /**
     *
     */
    CHF(78, "756"),
    /**
     *
     */
    MOP(79, "446"),
    /**
     *
     */
    MKD(80, "807"),
    /**
     *
     */
    MGA(81, "969"),
    /**
     *
     */
    MWK(82, "454"),
    /**
     *
     */
    MYR(83, "458"),
    /**
     *
     */
    MVR(84, "462"),
    /**
     *
     */
    MRO(85, "478"),
    /**
     *
     */
    MUR(86, "480"),
    /**
     *
     */
    XUA(87, "965"),
    /**
     *
     */
    MXN(88, "484"),
    /**
     *
     */
    MXV(89, "979"),
    /**
     *
     */
    MDL(90, "498"),
    /**
     *
     */
    MNT(91, "496"),
    /**
     *
     */
    MAD(92, "504"),
    /**
     *
     */
    MZN(93, "943"),
    /**
     *
     */
    MMK(94, "104"),
    /**
     *
     */
    NAD(95, "516"),
    /**
     *
     */
    NPR(96, "524"),
    /**
     *
     */
    NIO(97, "558"),
    /**
     *
     */
    NGN(98, "566"),
    /**
     *
     */
    OMR(99, "512"),
    /**
     *
     */
    PKR(100, "586"),
    /**
     *
     */
    PAB(101, "590"),
    /**
     *
     */
    PGK(102, "598"),
    /**
     *
     */
    PYG(103, "600"),
    /**
     *
     */
    PEN(104, "604"),
    /**
     *
     */
    PHP(105, "608"),
    /**
     *
     */
    PLN(106, "985"),
    /**
     *
     */
    QAR(107, "634"),
    /**
     *
     */
    RON(108, "946"),
    /**
     *
     */
    RUB(109, "643"),
    /**
     *
     */
    RWF(110, "646"),
    /**
     *
     */
    SHP(111, "654"),
    /**
     *
     */
    WST(112, "882"),
    /**
     *
     */
    STD(113, "678"),
    /**
     *
     */
    SAR(114, "682"),
    /**
     *
     */
    RSD(115, "941"),
    /**
     *
     */
    SCR(116, "690"),
    /**
     *
     */
    SLL(117, "694"),
    /**
     *
     */
    SGD(118, "702"),
    /**
     *
     */
    XSU(119, "994"),
    /**
     *
     */
    SBD(120, "090"),
    /**
     *
     */
    SOS(121, "706"),
    /**
     *
     */
    SSP(122, "728"),
    /**
     *
     */
    LKR(123, "144"),
    /**
     *
     */
    SDG(124, "938"),
    /**
     *
     */
    SRD(125, "968"),
    /**
     *
     */
    SZL(126, "748"),
    /**
     *
     */
    SEK(127, "752"),
    /**
     *
     */
    CHE(128, "947"),
    /**
     *
     */
    CHW(129, "948"),
    /**
     *
     */
    SYP(130, "760"),
    /**
     *
     */
    TWD(131, "901"),
    /**
     *
     */
    TJS(132, "972"),
    /**
     *
     */
    TZS(133, "834"),
    /**
     *
     */
    THB(134, "764"),
    /**
     *
     */
    TOP(135, "776"),
    /**
     *
     */
    TTD(136, "780"),
    /**
     *
     */
    TND(137, "788"),
    /**
     *
     */
    TRY(138, "949"),
    /**
     *
     */
    TMT(139, "934"),
    /**
     *
     */
    UGX(140, "800"),
    /**
     *
     */
    UAH(141, "980"),
    /**
     *
     */
    AED(142, "784"),
    /**
     *
     */
    USN(143, "997"),
    /**
     *
     */
    UYI(144, "940"),
    /**
     *
     */
    UYU(145, "858"),
    /**
     *
     */
    UZS(146, "860"),
    /**
     *
     */
    VUV(147, "548"),
    /**
     *
     */
    VEF(148, "937"),
    /**
     *
     */
    VND(149, "704"),
    /**
     *
     */
    YER(150, "886"),
    /**
     *
     */
    ZMW(151, "967"),
    /**
     *
     */
    ZWL(152, "932"),
    /**
     *
     */
    XBA(153, "955"),
    /**
     *
     */
    XBB(154, "956"),
    /**
     *
     */
    XBC(155, "957"),
    /**
     *
     */
    XBD(156, "958"),
    /**
     *
     */
    XTS(157, "963"),
    /**
     *
     */
    XXX(158, "999"),
    /**
     *
     */
    XAU(159, "959"),
    /**
     *
     */
    XPD(160, "964"),
    /**
     *
     */
    XPT(161, "962"),
    /**
     *
     */
    XAG(162, "961");

    private final Integer id;
    private final String code;

    // This is for JPA implementation
    // This is look up map to get Enum value from Integer value.
    private static final Map<Integer, CurrencyEnum> map = new HashMap<>();

    CurrencyEnum(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
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
     * @param code
     * @return
     */
    public static CurrencyEnum getFromCode(String code) {
        if (code.length() >= 4) {
            code = code.substring(code.length() - 3);
        }

        if (USD.getCode().contains(code)) {
            return USD;
        }
        if (BTN.getCode().contains(code)) {
            return BTN;
        }
        if (INR.getCode().contains(code)) {
            return INR;
        }
        if (BOB.getCode().contains(code)) {
            return BOB;
        }
        if (BOV.getCode().contains(code)) {
            return BOV;
        }
        if (BAM.getCode().contains(code)) {
            return BAM;
        }
        if (BWP.getCode().contains(code)) {
            return BWP;
        }
        if (NOK.getCode().contains(code)) {
            return NOK;
        }
        if (BRL.getCode().contains(code)) {
            return BRL;
        }
        if (BGN.getCode().contains(code)) {
            return BGN;
        }
        if (XOF.getCode().contains(code)) {
            return XOF;
        }
        if (BIF.getCode().contains(code)) {
            return BIF;
        }
        if (CVE.getCode().contains(code)) {
            return CVE;
        }
        if (KHR.getCode().contains(code)) {
            return KHR;
        }
        if (XAF.getCode().contains(code)) {
            return XAF;
        }
        if (CAD.getCode().contains(code)) {
            return CAD;
        }
        if (KYD.getCode().contains(code)) {
            return KYD;
        }
        if (CLF.getCode().contains(code)) {
            return CLF;
        }
        if (CLP.getCode().contains(code)) {
            return CLP;
        }
        if (CNY.getCode().contains(code)) {
            return CNY;
        }
        if (AUD.getCode().contains(code)) {
            return AUD;
        }
        if (COP.getCode().contains(code)) {
            return COP;
        }
        if (COU.getCode().contains(code)) {
            return COU;
        }
        if (KMF.getCode().contains(code)) {
            return KMF;
        }
        if (CDF.getCode().contains(code)) {
            return CDF;
        }
        if (NZD.getCode().contains(code)) {
            return NZD;
        }
        if (CRC.getCode().contains(code)) {
            return CRC;
        }
        if (HRK.getCode().contains(code)) {
            return HRK;
        }
        if (CUC.getCode().contains(code)) {
            return CUC;
        }
        if (CUP.getCode().contains(code)) {
            return CUP;
        }
        if (ANG.getCode().contains(code)) {
            return ANG;
        }
        if (EUR.getCode().contains(code)) {
            return EUR;
        }
        if (CZK.getCode().contains(code)) {
            return CZK;
        }
        if (DKK.getCode().contains(code)) {
            return DKK;
        }
        if (DJF.getCode().contains(code)) {
            return DJF;
        }
        if (XCD.getCode().contains(code)) {
            return XCD;
        }
        if (DOP.getCode().contains(code)) {
            return DOP;
        }
        if (EGP.getCode().contains(code)) {
            return EGP;
        }
        if (SVC.getCode().contains(code)) {
            return SVC;
        }
        if (ERN.getCode().contains(code)) {
            return ERN;
        }
        if (ETB.getCode().contains(code)) {
            return ETB;
        }
        if (FKP.getCode().contains(code)) {
            return FKP;
        }
        if (FJD.getCode().contains(code)) {
            return FJD;
        }
        if (XPF.getCode().contains(code)) {
            return XPF;
        }
        if (GMD.getCode().contains(code)) {
            return GMD;
        }
        if (GEL.getCode().contains(code)) {
            return GEL;
        }
        if (GHS.getCode().contains(code)) {
            return GHS;
        }
        if (GIP.getCode().contains(code)) {
            return GIP;
        }
        if (GTQ.getCode().contains(code)) {
            return GTQ;
        }
        if (GBP.getCode().contains(code)) {
            return GBP;
        }
        if (GNF.getCode().contains(code)) {
            return GNF;
        }
        if (GYD.getCode().contains(code)) {
            return GYD;
        }
        if (HTG.getCode().contains(code)) {
            return HTG;
        }
        if (HNL.getCode().contains(code)) {
            return HNL;
        }
        if (HKD.getCode().contains(code)) {
            return HKD;
        }
        if (HUF.getCode().contains(code)) {
            return HUF;
        }
        if (ISK.getCode().contains(code)) {
            return ISK;
        }
        if (IDR.getCode().contains(code)) {
            return IDR;
        }
        if (XDR.getCode().contains(code)) {
            return XDR;
        }
        if (IRR.getCode().contains(code)) {
            return IRR;
        }
        if (IQD.getCode().contains(code)) {
            return IQD;
        }
        if (ILS.getCode().contains(code)) {
            return ILS;
        }
        if (JMD.getCode().contains(code)) {
            return JMD;
        }
        if (JPY.getCode().contains(code)) {
            return JPY;
        }
        if (JOD.getCode().contains(code)) {
            return JOD;
        }
        if (KZT.getCode().contains(code)) {
            return KZT;
        }
        if (KES.getCode().contains(code)) {
            return KES;
        }
        if (KPW.getCode().contains(code)) {
            return KPW;
        }
        if (KRW.getCode().contains(code)) {
            return KRW;
        }
        if (KWD.getCode().contains(code)) {
            return KWD;
        }
        if (KGS.getCode().contains(code)) {
            return KGS;
        }
        if (LAK.getCode().contains(code)) {
            return LAK;
        }
        if (LBP.getCode().contains(code)) {
            return LBP;
        }
        if (LSL.getCode().contains(code)) {
            return LSL;
        }
        if (ZAR.getCode().contains(code)) {
            return ZAR;
        }
        if (LRD.getCode().contains(code)) {
            return LRD;
        }
        if (LYD.getCode().contains(code)) {
            return LYD;
        }
        if (CHF.getCode().contains(code)) {
            return CHF;
        }
        if (MOP.getCode().contains(code)) {
            return MOP;
        }
        if (MKD.getCode().contains(code)) {
            return MKD;
        }
        if (MGA.getCode().contains(code)) {
            return MGA;
        }
        if (MWK.getCode().contains(code)) {
            return MWK;
        }
        if (MYR.getCode().contains(code)) {
            return MYR;
        }
        if (MVR.getCode().contains(code)) {
            return MVR;
        }
        if (MRO.getCode().contains(code)) {
            return MRO;
        }
        if (MUR.getCode().contains(code)) {
            return MUR;
        }
        if (XUA.getCode().contains(code)) {
            return XUA;
        }
        if (MXN.getCode().contains(code)) {
            return MXN;
        }
        if (MXV.getCode().contains(code)) {
            return MXV;
        }
        if (MDL.getCode().contains(code)) {
            return MDL;
        }
        if (MNT.getCode().contains(code)) {
            return MNT;
        }
        if (MAD.getCode().contains(code)) {
            return MAD;
        }
        if (MZN.getCode().contains(code)) {
            return MZN;
        }
        if (MMK.getCode().contains(code)) {
            return MMK;
        }
        if (NAD.getCode().contains(code)) {
            return NAD;
        }
        if (NPR.getCode().contains(code)) {
            return NPR;
        }
        if (NIO.getCode().contains(code)) {
            return NIO;
        }
        if (NGN.getCode().contains(code)) {
            return NGN;
        }
        if (OMR.getCode().contains(code)) {
            return OMR;
        }
        if (PKR.getCode().contains(code)) {
            return PKR;
        }
        if (PAB.getCode().contains(code)) {
            return PAB;
        }
        if (PGK.getCode().contains(code)) {
            return PGK;
        }
        if (PYG.getCode().contains(code)) {
            return PYG;
        }
        if (PEN.getCode().contains(code)) {
            return PEN;
        }
        if (PHP.getCode().contains(code)) {
            return PHP;
        }
        if (PLN.getCode().contains(code)) {
            return PLN;
        }
        if (QAR.getCode().contains(code)) {
            return QAR;
        }
        if (RON.getCode().contains(code)) {
            return RON;
        }
        if (RUB.getCode().contains(code)) {
            return RUB;
        }
        if (RWF.getCode().contains(code)) {
            return RWF;
        }
        if (SHP.getCode().contains(code)) {
            return SHP;
        }
        if (WST.getCode().contains(code)) {
            return WST;
        }
        if (STD.getCode().contains(code)) {
            return STD;
        }
        if (SAR.getCode().contains(code)) {
            return SAR;
        }
        if (RSD.getCode().contains(code)) {
            return RSD;
        }
        if (SCR.getCode().contains(code)) {
            return SCR;
        }
        if (SLL.getCode().contains(code)) {
            return SLL;
        }
        if (SGD.getCode().contains(code)) {
            return SGD;
        }
        if (XSU.getCode().contains(code)) {
            return XSU;
        }
        if (SBD.getCode().contains(code)) {
            return SBD;
        }
        if (SOS.getCode().contains(code)) {
            return SOS;
        }
        if (SSP.getCode().contains(code)) {
            return SSP;
        }
        if (LKR.getCode().contains(code)) {
            return LKR;
        }
        if (SDG.getCode().contains(code)) {
            return SDG;
        }
        if (SRD.getCode().contains(code)) {
            return SRD;
        }
        if (SZL.getCode().contains(code)) {
            return SZL;
        }
        if (SEK.getCode().contains(code)) {
            return SEK;
        }
        if (CHE.getCode().contains(code)) {
            return CHE;
        }
        if (CHW.getCode().contains(code)) {
            return CHW;
        }
        if (SYP.getCode().contains(code)) {
            return SYP;
        }
        if (TWD.getCode().contains(code)) {
            return TWD;
        }
        if (TJS.getCode().contains(code)) {
            return TJS;
        }
        if (TZS.getCode().contains(code)) {
            return TZS;
        }
        if (THB.getCode().contains(code)) {
            return THB;
        }
        if (TOP.getCode().contains(code)) {
            return TOP;
        }
        if (TTD.getCode().contains(code)) {
            return TTD;
        }
        if (TND.getCode().contains(code)) {
            return TND;
        }
        if (TRY.getCode().contains(code)) {
            return TRY;
        }
        if (TMT.getCode().contains(code)) {
            return TMT;
        }
        if (UGX.getCode().contains(code)) {
            return UGX;
        }
        if (UAH.getCode().contains(code)) {
            return UAH;
        }
        if (AED.getCode().contains(code)) {
            return AED;
        }
        if (USN.getCode().contains(code)) {
            return USN;
        }
        if (UYI.getCode().contains(code)) {
            return UYI;
        }
        if (UYU.getCode().contains(code)) {
            return UYU;
        }
        if (UZS.getCode().contains(code)) {
            return UZS;
        }
        if (VUV.getCode().contains(code)) {
            return VUV;
        }
        if (VEF.getCode().contains(code)) {
            return VEF;
        }
        if (VND.getCode().contains(code)) {
            return VND;
        }
        if (YER.getCode().contains(code)) {
            return YER;
        }
        if (ZMW.getCode().contains(code)) {
            return ZMW;
        }
        if (ZWL.getCode().contains(code)) {
            return ZWL;
        }
        if (XBA.getCode().contains(code)) {
            return XBA;
        }
        if (XBB.getCode().contains(code)) {
            return XBB;
        }
        if (XBC.getCode().contains(code)) {
            return XBC;
        }
        if (XBD.getCode().contains(code)) {
            return XBD;
        }
        if (XTS.getCode().contains(code)) {
            return XTS;
        }
        if (XXX.getCode().contains(code)) {
            return XXX;
        }
        if (XAU.getCode().contains(code)) {
            return XAU;
        }
        if (XPD.getCode().contains(code)) {
            return XPD;
        }
        if (XPT.getCode().contains(code)) {
            return XPT;
        }
        if (XAG.getCode().contains(code)) {
            return XAG;
        }

        return null;
    }

    // In Enum, static block will be executed after creating all Enum values.
    static {
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            map.put(currencyEnum.getId(), currencyEnum);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public static final CurrencyEnum getCurrencyEnum(Integer id) {
        if (id != null) {
            return map.get(id);
        } else {
            return null;
        }
    }

}
