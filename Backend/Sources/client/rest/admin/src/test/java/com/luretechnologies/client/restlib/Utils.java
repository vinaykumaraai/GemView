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
package com.luretechnologies.client.restlib;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;

public class Utils {

    public static String serviceUrl = "http://mia.warp68.net:54061";
//    public static String serviceUrl = "http://127.0.0.1:4001/";
//    public static String serviceUrl = "http://localhost:13374";
//    public static String serviceUrl = "https://mia.lure68.net:54052";
//    public static String serviceUrl = "http://192.168.40.220:4051";

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static int getNumberOfTestCases(String className) {
        int NumTests = 0;

        try {
            Class c = Class.forName(className);

            Method ma[] = c.getMethods();
            for (Method m : ma) {

                if (m.getName().startsWith("TestCase")) {
                    NumTests++;
                }
            }

        } catch (Exception ex) {
        }

        return NumTests;
    }

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String alphabet_lowercase = "abcdefghijklmnopqrstuvwxyz";
    private static final String alphabet_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String special_characters = "!$.@_";
    private static final String numeric = "1234567890";

    public static String generatePassword(int len) {

        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        sb.append(numeric.charAt(r.nextInt(numeric.length() - 1)));
        sb.append(alphabet_uppercase.charAt(r.nextInt(alphabet_uppercase.length() - 1)));
        sb.append(alphabet_lowercase.charAt(r.nextInt(alphabet_lowercase.length() - 1)));
        sb.append(special_characters.charAt(r.nextInt(special_characters.length() - 1)));

        while (sb.length() < len) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length() - 1)));
        }

        String pw = sb.toString();

        List<Character> chars = new ArrayList<>();
        for (char c : pw.toCharArray()) {
            chars.add(c);
        }

        StringBuilder output = new StringBuilder(pw.length());

        while (!chars.isEmpty()) {
            int randPicker = (int) (Math.random() * chars.size());
            output.append(chars.remove(randPicker));
        }

        return output.toString();
    }

    public static String encryptPassword(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return toHexString(hash);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array).toUpperCase();
    }
}
