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
package com.luretechnologies.server.security.util;

import java.io.StringReader;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * The Utils class
 */
public class Utils {

    /**
     *
     */
    public enum OutputStates {

        /**
         *
         */
        Debug,
        /**
         *
         */
        Info,
        /**
         *
         */
        Production;
    }

    /**
     * Loads an XML string as an XML document
     *
     * @param xml The XML string
     * @return The XML document
     * @throws Exception
     */
    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    /**
     * Gets an XML element's value
     *
     * @param doc The XML document
     * @param elementName The element nae
     * @return The element value
     * @throws Exception
     */
    public static String getElementValue(Document doc, String elementName) throws Exception {

        if (doc == null) {
            Output(OutputStates.Debug, "getElementValue", "doc is null...");
            return null;
        }

        NodeList items = doc.getElementsByTagName(elementName);

        if (items == null) {
            Output(OutputStates.Debug, "getElementValue", "items is null...");
            return null;
        }

        if (items.getLength() > 0) {
            return items.item(0).getTextContent().trim();
        }

        return null;
    }

    /**
     * Prints the string to the output console
     *
     * @param value
     */
    public static void Output(String value) {
        Output(OutputStates.Debug, null, value);
    }

    /**
     * Prints the string to the output console
     *
     * @param state
     * @param msg
     * @param value
     */
    public static void Output(OutputStates state, String msg, String value) {

        if (msg == null || msg.isEmpty()) {
            System.out.println(">> [" + state.toString() + "] -> " + value);
        } else {
            System.out.println(">> [" + state.toString() + "] " + msg + " -> " + value);
        }
    }

    /**
     * Converts a byte array to a hex string
     *
     * @param array The byte array
     * @return The hex string
     */
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array).toUpperCase();
    }

    /**
     * Converts a hex string to a byte array
     *
     * @param s The hex string
     * @return The byte array
     */
    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s.toUpperCase());
    }

    /**
     *
     * @param bytes
     * @param title
     */
    public static void hexPrettyPrint(byte[] bytes, String title) {
        System.out.print(hexPrettyPrintCreate(bytes, title));
    }

    /**
     *
     * @param bytes
     * @param title
     * @return
     */
    public static String hexPrettyPrintToString(byte[] bytes, String title) {
        return hexPrettyPrintCreate(bytes, title);
    }

    /**
     * Prints the bytes array to formatted string
     *
     * @param bytes The byte array
     * @param title The output title
     * @return
     */
    public static String hexPrettyPrintCreate(byte[] bytes, String title) {

        int bytesPerLine = 24;
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s (%d)%n", title, bytes.length));

        int count = 0;

        String s = "";
        String c = "";

        for (byte b : bytes) {
            s = s + String.format("%02X ", b);

            if ((char) b >= (char) 0x20 && (char) b <= (char) 0x7E) {
                c = c + String.format("%c", (char) b);
            } else {
                c = c + ".";
            }

            if (count++ == bytesPerLine) {
                sb.append(String.format("%s -> [%s]%n", s, c));

                count = 0;
                s = "";
                c = "";
            }
        }

        while (count++ <= bytesPerLine) {
            s = s + "   ";
        }

        sb.append(String.format("%s -> [%s]%n", s, c));
        return sb.toString();
    }

    /**
     * Gets the contents of an array
     *
     * @param array The array
     * @param index The start index
     * @param length The number of bytes
     * @return The requested bytes
     */
    public static byte[] getArrayContents(byte[] array, int index, int length) {
        byte[] tempBuffer = new byte[length];
        System.arraycopy(array, index, tempBuffer, 0, length);
        return tempBuffer;
    }

    /**
     * Gets the contents of an array, allowing for an array larger than the
     * requested bytes
     *
     * @param array The array
     * @param index The start index
     * @param length The number of bytes
     * @param extra The number of extra bytes
     * @return The requested bytes
     */
    public static byte[] getArrayContentsEx(byte[] array, int index, int length, int extra) {
        byte[] tempBuffer = new byte[length + extra];
        System.arraycopy(array, index, tempBuffer, 0, length);
        return tempBuffer;
    }

    /**
     * Gets the contents of an array, and prints the array
     *
     * @param array The array
     * @param index The start index
     * @param length The number of bytes
     * @param title The pretty print title
     * @return The requested bytes
     */
    public static byte[] getArrayContents(byte[] array, int index, int length, String title) {
        byte[] tempBuffer = getArrayContents(array, index, length);
        hexPrettyPrint(tempBuffer, title);
        return tempBuffer;
    }

    /**
     *
     * @param acct_data
     * @return
     */
    public static String maskAccount(String acct_data) {

        // for acct numbers < 5 digits, return acct number as-is
        if (acct_data == null || acct_data.length() < 5) {
            return acct_data;
        }

        String last_four = acct_data.substring(acct_data.length() - 4, acct_data.length());
        return rightJustify(last_four, "x", acct_data.length());
    }

    /**
     *
     * @param msg
     * @param pad_char
     * @param max_len
     * @return
     */
    public static String rightJustify(String msg, String pad_char, int max_len) {
        for (int i = msg.length(); i < max_len; i++) {
            msg = pad_char + msg;
        }
        return msg;
    }

    /**
     *
     * @param msg
     * @param pad_char
     * @param max_len
     * @return
     */
    public static String leftJustify(String msg, String pad_char, int max_len) {
        for (int i = msg.length(); i < max_len; i++) {
            msg = msg + pad_char;
        }
        return msg;
    }

    /**
     *
     * @param s
     * @param width
     * @return
     */
    public static String center(String s, int width) {
        if (s == null) {
            return ""; // to avoid NPE
        }
        while (s.length() < width - 1) {
            s = " " + s + " ";
        }
        return s;
    }

    /**
     *
     * @param message
     * @return
     */
    public static String SHA256(String message) {
        return SHA256(toByteArray(message));
    }

    /**
     *
     * @param message
     * @return
     */
    public static String SHA256(byte[] message) {
        return DigestUtils.sha256Hex(message).toUpperCase();
    }

    /**
     *
     * @param input
     * @return
     */
    public static byte[] removePaddingPKCS(byte[] input) {
        return Arrays.copyOf(input, input.length - (int) input[input.length - 1]);
    }
}
