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

import com.luretechnologies.common.enums.DayEnum;
import com.luretechnologies.common.enums.TimeEnum;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.IOUtils;
import org.hashids.Hashids;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

public class Utils {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String alpha_lowercase = "abcdefghijklmnopqrstuvwxyz";
    private static final String alpha_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String special_characters = "!#$%&'()*+,.:;<=>?@[]/\"^_`{|}~";
    private static final String special_characters_safe = "!$.@_";
    private static final String numeric = "1234567890";
    private static final Hashids hashids = new Hashids("DirexX", 10, alpha_lowercase + alpha_uppercase + numeric);

    public static final long MS = 1;
    public static final long MS_SECOND = MS * 1000;
    public static final long MS_MINUTE = MS_SECOND * 60;
    public static final long MS_HOUR = MS_MINUTE * 60;
    public static final long MS_DAY = MS_HOUR * 24;

    // TODO - make these fields configurable
    public static final String HASH_SIGNING_KEY = "wD8Cidphdiedcw7svWqOBMoK4ZAU7V/DtPfk9yaWI09x4HUiuPyypgwbIfyWFsTVDKOmJmbkE9cpuXcDHR45iA==";
    public static final long PASSWORD_TIME_LIMIT = MS_DAY * 30;
    public static final long VERIFICATION_CODE_TIME_LIMIT = MS_MINUTE * 10;
    public static final long USER_SESSION_TIME_LIMIT = MS_MINUTE * 2;

    /**
     *
     * @param howLong
     * @return
     */
    public static final long fromNow(long howLong) {
        return System.currentTimeMillis() + howLong;
    }

    /**
     *
     * @return
     */
    public static String generateToken() {
        return rndstr(48, false);
    }

    /**
     *
     * @return
     */
    public static String generatePassword(int len) {
        return rndstr(len, true);
    }

    /**
     *
     * @return
     */
    public static String generateActivationCode(int len) {
        return rndstr(len, true);
    }

    /**
     *
     * @return
     */
    public static String generateGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     *
     * @param id
     * @return
     */
    public static long decodeHashId(String id) {
        long[] ids = hashids.decode(id.substring(3, id.length()));
        return (ids.length > 0) ? ids[0] : 0;
    }

    /**
     *
     * @param prefix
     * @param id
     * @return
     */
    public static String encodeHashId(String prefix, long id) {
        return prefix + hashids.encode(id);
    }

    private static String rndstr(int len, boolean safe) {

        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        sb.append(alpha_uppercase.charAt(r.nextInt(alpha_uppercase.length() - 1)));
        sb.append(alpha_lowercase.charAt(r.nextInt(alpha_lowercase.length() - 1)));
        sb.append(numeric.charAt(r.nextInt(numeric.length() - 1)));

        if (safe) {
            sb.append(special_characters_safe.charAt(r.nextInt(special_characters_safe.length() - 1)));
        } else {
            sb.append(special_characters.charAt(r.nextInt(special_characters.length() - 1)));
        }

        while (sb.length() < len) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length() - 1)));
        }

        return jumbleString(sb.toString());
    }

    /**
     *
     * @param in
     * @return
     */
    public static String jumbleString(String in) {

        List<Character> chars = new ArrayList<>();
        for (char c : in.toCharArray()) {
            chars.add(c);
        }

        StringBuilder out = new StringBuilder(in.length());

        while (!chars.isEmpty()) {
            int randPicker = (int) (Math.random() * chars.size());
            out.append(chars.remove(randPicker));
        }

        return out.toString();
    }

    /**
     * Basic SHA-2 Password Encryption
     *
     * @param password
     * @return String
     */
    public static String encryptPassword(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return toHexString(hash);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.info("encryptPassword " + ex.getMessage());
            return null;
        }
    }

    /**
     * Perform password complexity validation
     *
     * @param password
     * @param minLength
     * @param maxLength
     * @param allowUppercase
     * @param requireUppercase
     * @param allowLowercase
     * @param requireLowercase
     * @param allowNumber
     * @param requireNumber
     * @param allowSpace
     * @param requireSpace
     * @param allowSymbol
     * @param requireSymbol
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public static boolean enforcePasswordComplexity(String password, int minLength, int maxLength,
            boolean allowUppercase, boolean requireUppercase,
            boolean allowLowercase, boolean requireLowercase,
            boolean allowNumber, boolean requireNumber,
            boolean allowSpace, boolean requireSpace,
            boolean allowSymbol, boolean requireSymbol) throws NoSuchAlgorithmException {

        boolean containsUppercase = false;
        boolean containsLowercase = false;
        boolean containsNumber = false;
        boolean containsSymbol = false;
        boolean containsSpace = false;

        if (password == null) {
            return false;
        }

        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    if (allowUppercase) {
                        containsUppercase = true;
                    } else {
                        return false;
                    }
                } else {
                    if (allowLowercase) {
                        containsLowercase = true;
                    } else {
                        return false;
                    }
                }
            } else if (Character.isDigit(c)) {
                if (allowNumber) {
                    containsNumber = true;
                } else {
                    return false;
                }
            } else if (c == ' ') {
                if (allowSpace) {
                    containsSpace = true;
                } else {
                    return false;
                }
            } else if (c >= 0x21 && c <= 0x2F) {
                if (allowSymbol) {
                    containsSymbol = true;
                } else {
                    return false;
                }
            } else if (c >= 0x3A && c <= 0x40) {
                if (allowSymbol) {
                    containsSymbol = true;
                } else {
                    return false;
                }
            } else if (c >= 0x5B && c <= 0x60) {
                if (allowSymbol) {
                    containsSymbol = true;
                } else {
                    return false;
                }
            } else if (c >= 0x7B && c <= 0x7E) {
                if (allowSymbol) {
                    containsSymbol = true;
                } else {
                    return false;
                }
            } else {
                return false;   // anything else, we fail
            }
        }

        if (requireSymbol && !containsSymbol) {
            return false;
        }

        if (requireUppercase && !containsUppercase) {
            return false;
        }

        if (requireLowercase && !containsLowercase) {
            return false;
        }

        if (requireNumber && !containsNumber) {
            return false;
        }

        if (requireSpace && !containsSpace) {
            return false;
        }

        if (requireSymbol && !containsSymbol) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param s
     * @return
     */
    public static boolean isValidSHA2(String s) {
        return s.matches("[a-fA-F0-9]{64}");
    }

    /**
     *
     * @param number
     * @return
     */
    public static Long getLong(Long number) {
        return number == null ? 0 : number;
    }

    /**
     *
     * @param number
     * @return
     */
    public static Double getDouble(Double number) {
        return number == null ? 0 : number;
    }

    /**
     *
     * @param total
     * @param rowsPerPage
     * @return
     */
    public static int getTotalPages(int total, int rowsPerPage) {
        return getTotalPages((long) total, rowsPerPage);
    }

    /**
     *
     * @param total
     * @param rowsPerPage
     * @return
     */
    public static int getTotalPages(long total, int rowsPerPage) {
        return (int) Math.ceil((float) total / (float) rowsPerPage);
    }

    /**
     * convert Timestamp to short date
     *
     * @param timestamp
     * @return String
     *
     */
    public static String getShortDate(Timestamp timestamp) {
        if (timestamp != null) {
            long lnMilisegundos = timestamp.getTime() + (timestamp.getNanos() / 1000000);
            java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);
            return sqlDate.toString();
        }
        return "";
    }

    /**
     * convert InputStream to java.sql.Blob
     *
     * @param inputStream
     * @return String
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     *
     */
    public static java.sql.Blob inputStreamToBlob(InputStream inputStream) throws IOException, SQLException {
        if (inputStream != null) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Blob blob = new SerialBlob(bytes);
            return blob;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static DayEnum getDayOfTheWeek() {
        Calendar calendar = Calendar.getInstance();

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: {
                return DayEnum.SUNDAY;
            }
            case Calendar.MONDAY: {
                return DayEnum.MONDAY;
            }
            case Calendar.TUESDAY: {
                return DayEnum.TUESDAY;
            }
            case Calendar.WEDNESDAY: {
                return DayEnum.WEDNESDAY;
            }
            case Calendar.THURSDAY: {
                return DayEnum.THURSDAY;
            }
            case Calendar.FRIDAY: {
                return DayEnum.FRIDAY;
            }
            case Calendar.SATURDAY: {
                return DayEnum.SATURDAY;
            }
            default:
                return DayEnum.ALL;
        }
    }

    /**
     *
     * @return
     */
    public static TimeEnum getHour() {
        Calendar calendar = Calendar.getInstance();

        switch (calendar.get(Calendar.HOUR_OF_DAY)) {
            case 1: {
                return TimeEnum._1AM;
            }
            case 2: {
                return TimeEnum._2AM;
            }
            case 3: {
                return TimeEnum._3AM;
            }
            case 4: {
                return TimeEnum._4AM;
            }
            case 5: {
                return TimeEnum._5AM;
            }
            case 6: {
                return TimeEnum._6AM;
            }
            case 7: {
                return TimeEnum._7AM;
            }
            case 8: {
                return TimeEnum._8AM;
            }
            case 9: {
                return TimeEnum._9AM;
            }
            case 10: {
                return TimeEnum._10AM;
            }
            case 11: {
                return TimeEnum._11AM;
            }
            case 12: {
                return TimeEnum._12AM;
            }
            case 13: {
                return TimeEnum._1PM;
            }
            case 14: {
                return TimeEnum._2PM;
            }
            case 15: {
                return TimeEnum._3PM;
            }
            case 16: {
                return TimeEnum._4PM;
            }
            case 17: {
                return TimeEnum._5PM;
            }
            case 18: {
                return TimeEnum._6PM;
            }
            case 19: {
                return TimeEnum._7PM;
            }
            case 20: {
                return TimeEnum._8PM;
            }
            case 21: {
                return TimeEnum._9PM;
            }
            case 22: {
                return TimeEnum._10PM;
            }
            case 23: {
                return TimeEnum._11PM;
            }
            case 24: {
                return TimeEnum._12PM;
            }
            default:
                return TimeEnum._24HOURS;
        }
    }

    /**
     * Within specified time
     *
     * @param startTime From time
     * @param maxMillis Max time
     * @return true/false
     */
    public static boolean isTime(long startTime, long maxMillis) {
        return (System.currentTimeMillis() - startTime) >= maxMillis;
    }

    /**
     * Is expired
     *
     * @param when
     * @return true/false
     */
    public static boolean isExpired(long when) {
        return (System.currentTimeMillis() > when);
    }

    /**
     * Is Current
     *
     * @param when
     * @return true/false
     */
    public static boolean isCurrent(long when) {
        return (System.currentTimeMillis() < when);
    }

    /**
     * Encode MultipartFile file to base64 String
     *
     * @param file
     * @return Blob
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static String encodeToBase64String(MultipartFile file) throws IOException, SQLException {
        byte[] fileBytes = file.getBytes();
        String base64String = Base64Utils.encodeToString(fileBytes);

        return base64String;
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
     * @param hexValue
     * @return
     */
    public static String hexToString(String hexValue) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexValue.length(); i += 2) {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString().trim();
    }

    /**
     *
     * @param emailAddress
     * @return
     */
    public static String maskEmailAddress(String emailAddress) {
        return emailAddress.replaceAll("(?<=.{1}).(?=.*@)", "*");
    }

    /**
     *
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < len) { // length of the random string.
            int index = (int) (rnd.nextFloat() * alphabet.length());
            code.append(alphabet.charAt(index));
        }
        String codeStr = code.toString();
        return codeStr;
    }

    /**
     *
     * @param len
     * @return
     */
    public static String getRandomNumberAsString(int len) {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < len) { // length of the random string.
            int index = (int) (rnd.nextFloat() * numeric.length());
            code.append(numeric.charAt(index));
        }
        String codeStr = code.toString();
        return codeStr;
    }
}
