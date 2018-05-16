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
package com.luretechnologies.server.common.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * String Utils Class
 *
 */
public class StringUtils {

    /**
     * Pads a string
     *
     * @param s The String to be pad.
     * @param n Number of spaces to pad.
     * @return Right justifies the string.
     */
    public static String padRightSpaces(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * Pads a string
     *
     * @param s The String to be pad.
     * @param n Number of spaces to pad.
     * @return Left justifies the string
     */
    public static String padLeftSpaces(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    /**
     * Pads a string with zeroes.
     *
     * @param i Position from which to pad with zeroes.
     * @param n Number of spaces to pad.
     * @return Right justifies the string with zeroes.
     */
    public static String padRightZeroes(Integer i, int n) {
        return padRightSpaces(String.valueOf(i), n).replace(' ', '0');
    }

    /**
     * Pads a string
     *
     * @param number
     * @param n Number of spaces to pad.
     * @return Left justifies the string with zeroes
     */
    public static String padLeftZeroes(String number, int n) {
        return padLeftSpaces(number, n).replace(' ', '0');
    }

    /**
     * Masks a string completely with "X".
     *
     * @param str String to be mask.
     * @return The masked string
     */
    public static String maskAll(String str) {
        if (str == null) {
            return "";
        }
        return String.format(String.format("%%0%dd", str.length()), 0).replace("0", "X");
    }

    /**
     * Masks a string according to its length to show only last four.
     *
     * @param str String to be mask.
     * @return The masked string
     */
    public static String mask(String str) {

        if (str == null) {
            return "";
        }

        String lastDigits = "";
        int len = str.length();

        // fyi: bank cards are 12-19 digits long
        if (len >= 12) {
            lastDigits = str.substring(len - 4, len);
        } else {
            // show none
        }

        // make a series of XXXXs according to len of string so resulting string is exact len as input string
        String x = String.format(String.format("%%0%dd", len - lastDigits.length()), 0).replace("0", "X");
        return String.format("%s%s", x, lastDigits);
    }

    public static String maskCardData(String data) {

        Pattern p = Pattern.compile(
                "(?:2[0-9]{15}" // cards starting with 2
                + "?|"
                + "3[0-9]{11,18}" // cards starting with 3
                + "?|"
                + "4[0-9]{15,18}" // cards starting with 4
                + "?|"
                + "5[0-9]{15,18}" // cards starting with 5
                + "?|"
                + "6[0-9]{15,18}" // cards starting with 6
                + "?|"
                + "=(.+?)\"" // track 2 data after =
                + ")");

        Matcher m = p.matcher(data);

        while (m.find()) {
            Boolean isTrack = (data.substring(m.start())).startsWith("=");
            data = maskDataInString(data, m.start() + (isTrack ? 1 : 6), m.end() + (isTrack ? 0 : 4));
        }

        return data;
    }

    /**
     * Finds a value in json string and masks it
     *
     * @param json The json string
     * @param fields
     * @return The masked json string
     */
    public static String maskJsonFields(String json, List<MaskItem> fields) {

        Boolean spacing = false;

        // remove the spacing in json
        if (json.contains("\" : \"")) {
            spacing = true;
            json = json.replace("\" : \"", "\":\"");
        }

        for (MaskItem field : fields) {

            String name = String.format("\"%s\":\"", field.getField());
            String finder = String.format("%s(.*?)\"", name);

            Pattern p = Pattern.compile(finder);
            Matcher m = p.matcher(json);

            while (m.find()) {
                json = maskDataInString(json, m.start() + name.length() + field.getStartOffset(), m.end() - 1 - field.getEndOffset());
            }
        }

        // restore the spacing in json
        if (spacing) {
            json = json.replace("\":\"", "\" : \"");
        }

        return json;
    }

    public static String prettyXml(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    /**
     * Finds a value in xml string and masks it
     *
     * @param xml The xml string
     * @param fields
     * @return The masked xml string
     */
    public static String maskXmlFields(String xml, List<MaskItem> fields) {

        for (MaskItem field : fields) {

            String name = String.format("<%s>", field.getField());
            String finder = String.format("%s(.*?)<\\/%s>", name, field.getField());

            Pattern p = Pattern.compile(finder);
            Matcher m = p.matcher(xml);

            while (m.find()) {
                xml = maskDataInString(xml, m.start() + name.length() + field.getStartOffset(), m.end() - field.getEndOffset() - (name.length() + 1));
            }
        }

        return prettyXml(xml, 2);
    }

    /**
     * Masks fields according to method details
     *
     * @param json The transaction as a json string
     * @return The masked json transaction string
     */
    public static String maskJsonTransaction(String json) {

        List<MaskItem> fields = new ArrayList<>();

        fields.add(new MaskItem("cardExpDate", 0, 0));
        fields.add(new MaskItem("cardExpDateMonth", 0, 0));
        fields.add(new MaskItem("cardExpDateYear2", 0, 0));
        fields.add(new MaskItem("cardExpDateYear4", 0, 0));
        fields.add(new MaskItem("cardHolderName", 0, 0));
        fields.add(new MaskItem("cardPan", 6, 4));
        fields.add(new MaskItem("cardTrack", 0, 0));
        fields.add(new MaskItem("cardZip", 0, 0));
        fields.add(new MaskItem("cvv", 0, 0));
        fields.add(new MaskItem("password", 0, 0));
        fields.add(new MaskItem("ssn", 0, 0));

        return maskJsonFields(json, fields);
    }

    /**
     * Masks a specific region within a string
     *
     * @param data The data
     * @param start The starting position
     * @param end The ending position
     * @return The masked string
     */
    public static String maskDataInString(String data, int start, int end) {

        StringBuffer buf = new StringBuffer(data);

        for (int index = start; index < (end); index++) {
            buf.replace(index, index + 1, "X");
        }

        return buf.toString();
    }
}
