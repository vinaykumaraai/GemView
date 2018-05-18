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

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utilities to parse the track data from common payment cards.
 *
 *
 */
public class PaymentCardUtils {

    /**
     * Weather this object was constructed with card data or manual data.
     */
    protected boolean swiped;
    /**
     * The track 1 data.
     */
    private final String track1;
    /**
     * The track 2 data.
     */
    private final String track2;
    /**
     * The track 3 data.
     */
    private final String track3;
    /**
     * The cached value of the PAN.
     */
    private String pan = null;
    /**
     * The cached value of the PAN masked with only the first and last four
     * digits displayed.
     */
    private String maskedPan = null;
    /**
     * The cached value of the card holder name from track 1.
     */
    private String cardholderName = null;
    /**
     * The cached value of the expiration date.
     */
    private String expiryMonth = null;
    private String expiryYear = null;
    /**
     * The cached value of the service code.
     */
    private String serviceCode = null;
    private String tk1DiscretionaryData = null;
    private String tk2DiscretionaryData = null;
    private String cvv = null;
    private static final Pattern track1Regex;
    private static final int TRACK_ONE_PAN_GROUP = 1;
    private static final int TRACK_ONE_CARDHOLDER_NAME_GROUP = 2;
    private static final int TRACK_ONE_EXP_YEAR_GROUP = 3;
    private static final int TRACK_ONE_EXP_MONTH_GROUP = 4;
    private static final int TRACK_ONE_SERVICE_CODE_GROUP = 5;
    private static final int TRACK_ONE_DISCRETIONARY_GROUP = 6;
    private static final Pattern track2Regex;
    private static final int TRACK_TWO_PAN_GROUP = 1;
    private static final int TRACK_TWO_EXP_YEAR_GROUP = 2;
    private static final int TRACK_TWO_EXP_MONTH_GROUP = 3;
    private static final int TRACK_TWO_SERVICE_CODE_GROUP = 4;
    private static final int TRACK_TWO_DISCRETIONARY_GROUP = 5;
    private static final int[][] LUHN_TABLE = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {0, 2, 4, 6, 8, 1, 3, 5, 7, 9}
    };

    static {
        //The regex should match a string with the format:
        // 1. Optionally begins with an '%'
        // 2. Has the 'B' track type specification
        // 3. Has a string of numeric digits between 1 and 19 long is captured as the PAN.
        // 4. The digits are followed by a '^'
        // 5. Everything except to another '^' is captured as the cardholder name.
        // 6. The cardholder name is followed by another '^'
        // 7. Four numeric digits captured as the expiration date
        // 8. Optionally the next three numeric digits captured as the service code
        // 9. Every following character except the final '?' is captured as discretionary data
        // 10. An optional end sentinal of '?'
        track1Regex = Pattern.compile("^%B([0-9]{1,19})\\^([^\\^]{2,26})\\^([0-9]{2}|\\^)([0-9]{2}|\\^)([0-9]{3}|\\^)([^\\?]+)\\?$");

        //This regex should match a string with the format:
        // 1. Optinally begins with a ';'.
        // 2. Followed by a string of numeric digits between 1 and 19 long captured as the PAN.
        // 3. The PAN is followed by a '='.
        // 4. The next four numeric digits are caputred as the expiration date.
        // 5. Optionally the next three characters are captured as the service code
        // 6. Every following character except the final '?' is captured as discretionary data
        // 7. An optional end sentinal of '?'
        track2Regex = Pattern.compile(";?(\\d{1,19})=(\\d{2})(\\d{2})(\\d{3})?([^?]*)\\??");
    }

    /**
     * Constructs a payment card using the three tracks of card data.
     *
     * @param trackOne Track one data
     * @param trackTwo Track two data
     * @param trackThree Track three data
     * @throws IllegalArgumentException if all three track datum are null.
     */
    public PaymentCardUtils(String trackOne, String trackTwo, String trackThree) {

        if (trackOne == null && trackTwo == null && trackThree == null) {
            throw new IllegalArgumentException("You must provide at least one track.");
        }

        swiped = true;

        // format Track1
        if (!(trackOne != null && trackOne.startsWith("%"))) {
            trackOne = "%" + trackOne;
        }

        if (!(trackOne != null && trackOne.endsWith("?"))) {
            trackOne = trackOne + "?";
        }

        track1 = trackOne;

        // format Track2
        if (!(trackTwo != null && trackTwo.startsWith(";"))) {
            trackTwo = ";" + trackTwo;
        }

        if (!(trackTwo != null && trackTwo.endsWith("?"))) {
            trackTwo = trackTwo + "?";
        }

        track2 = trackTwo;

        // format Track3
        if (!(trackThree != null && trackThree.startsWith(";"))) {
            trackThree = ";" + trackThree;
        }

        if (!(trackThree != null && trackThree.endsWith("?"))) {
            trackThree = trackThree + "?";
        }

        track3 = trackThree;
    }

    /**
     * Constructs a payment card using the PAN and expiration date.
     *
     * @param account The Primary Account Number
     * @param exp The expiration date as YYMM
     */
    public PaymentCardUtils(String account, String exp) {

        this.swiped = false;

        track1 = null;
        track2 = String.format(";%s=%s?", pan, exp);
        track3 = null;

        this.pan = account;
        this.expiryMonth = exp.substring(0, 2);
        this.expiryYear = exp.substring(2);
    }

    /**
     * Gets the first track data.
     *
     * @param includeSentinels
     * @return The track data or null if the card has no track one.
     */
    public String getTrackOne(boolean includeSentinels) {

        if (includeSentinels == true) {
            return track1;
        } else {
            String temp = track1.replace("%", "");
            temp = temp.replace("?", "");
            return temp;
        }
    }

    /**
     * Gets the second track data.
     *
     * @param includeSentinels
     * @return The track data or null if the card had no track two.
     */
    public String getTrackTwo(boolean includeSentinels) {

        if (includeSentinels == true) {
            return track2;
        } else {
            String temp = track2.replace(";", "");
            temp = temp.replace("?", "");
            return temp;
        }
    }

    /**
     * Gets the third track data.
     *
     * @param includeSentinels
     * @return The track data or null if the card had no track three.
     */
    public String getTrackThree(boolean includeSentinels) {

        if (includeSentinels == true) {
            return track3;
        } else {
            String temp = track3.replace(";", "");
            temp = temp.replace("?", "");
            return temp;
        }
    }

    /**
     * Gets the PAN, parsing it from the track data if it has not done so
     * previously.
     *
     * @return The PAN
     * @throws Exception if the pan unknown and cannot be parsed.
     */
    public String getPan() throws Exception {
        if (pan == null) {
            if (track1 != null) {
                parseTrack1();
            }

            if (pan == null && track2 != null) {
                parseTrack2();
            }

            if (pan == null) {
                return null;
            }
        }

        return pan;
    }

    /**
     * Returns the PAN masked to only show the first and last 4 characters.
     *
     * @return The masked pan.
     * @throws Exception if the pan is unavailable.
     */
    public String getMaskedPan() throws Exception {
        if (maskedPan == null) {
            StringBuilder b = new StringBuilder();
            String p = getPan();

            b.append(p.substring(0, 4));
            for (int i = 0; i < p.length() - 8; ++i) {
                b.append('*');
            }
            b.append(p.substring(p.length() - 4));

            maskedPan = b.toString();
        }

        return maskedPan;
    }

    /**
     * Checks the Luhn sum of the PAN.
     *
     * @param number The account number to check.
     * @return The value of the check digit.
     */
    public static boolean isValidPan(String number) {
        if (number == null || number.length() == 0) {
            throw new IllegalArgumentException("number was invalid");
        }

        int sum = 0, flip = 0;

        for (int i = number.length() - 1; i >= 0; i--) {
            sum += LUHN_TABLE[flip++ & 0x1][Character.digit(number.charAt(i), 10)];
        }

        return sum % 10 == 0;
    }

    /**
     * Tests weather the pan passes the Luhn algorithm.
     *
     * @return true if the pan passes.
     * @throws Exception if the pan is not available.
     */
    public boolean isValidPan() throws Exception {
        return isValidPan(getPan());
    }

    /**
     * Gets the card holder name from track one, parsing it if it has not done
     * so previously.
     *
     * @return The card holder name.
     * @throws Exception if the name could not be parsed.
     */
    public String getCardholderName() throws Exception {
        if (cardholderName == null) {
            if (track1 != null) {
                parseTrack1();
            }

            if (cardholderName == null) {
                return null;
            }
        }

        return cardholderName;
    }

    /**
     * Gets the expiration date as a YYMM string, parsing the tracks if it has
     * not done so.
     *
     * @return The expiration date as YYMM
     * @throws Exception if the date could not be parsed.
     */
    public String getExpirationDate() throws Exception {
        if (expiryMonth == null || expiryYear == null) {
            if (track1 != null) {
                parseTrack1();
            }

            if ((expiryMonth == null || expiryYear == null) && track2 != null) {
                parseTrack2();
            }

            if (expiryMonth == null || expiryYear == null) {
                return null;
            }
        }

        return String.format("%s%s", expiryYear, expiryMonth);
    }

    /**
     * Gets the expiration date as a java.util.Calendar object.
     *
     * @return The expiration date as a java.util.Calendar.
     * @throws Exception if the date was unavailable.
     * @throws IllegalArgumentException if the date is not in the expected
     * format.
     */
    public Calendar getExpirationDateObject() throws Exception {
        return parseExpirationDate(getExpirationDate());
    }

    /**
     * Compare the expiration date to a date in YYMM format.
     *
     * @param date The date in the format YYMM
     * @return boolean -- true if matches, false if not
     * @throws Exception if we don't have an expiry for this card
     */
    public boolean expirationDateMatches(String date) throws Exception {
        if (date.equals(getExpirationDate())) {
            return true;
        }
        return false;
    }

    /**
     * Parse a card expiration date to a java.util.Calendar object.
     *
     * @param date The date in the format YYMM
     * @return The expiration date as a java.util.Calendar
     * @throws IllegalArgumentException if the date is not formatted correctly.
     */
    public static Calendar parseExpirationDate(String date) {
        try {
            int month = Integer.parseInt(date.substring(0, 2));
            int year = Integer.parseInt(date.substring(2));

            if (month > 12) {
                throw new IllegalArgumentException("The month of the expiration date is not valid");
            }

            Calendar c = Calendar.getInstance();

            //the date is in YYMM we have to set down to the millisecond so the
            //dates can compare equal.
            c.set(2000 + year,
                    month - 1, // months are zero-indexed so this fix is required for correctness
                    1, // setting to first rather than zeroth day (?) of month
                    0, 0, 0);
            c.set(Calendar.MILLISECOND, 0);

            return c;

        } catch (NumberFormatException nfex) {
            throw new IllegalArgumentException("The expiration date is not valid.", nfex);
        }
    }

    /**
     * Check if the given string is a valid expiration date.
     *
     * @param s The string to check.
     * @return true if this is a valid date.
     */
    public static boolean isValidExpirationDate(String s) {
        try {
            if (s.length() != 4) {
                return false;
            }

            int month = Integer.parseInt(s.substring(0, 2));

            if (month > 12) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfex) {
            return false;
        }
    }

    /**
     * Returns if the card has expired.
     *
     * @return true if the card has expired.
     * @throws Exception if the expiration data could not be parsed.
     * @throws IllegalArgumentException if the date is not formatted correctly.
     */
//    @Override
    public boolean isExpired() throws Exception {
        return isExpired(getExpirationDate());
    }

    /**
     * Returns true if the card has/will expired by the date provided.
     *
     * @param exp
     * @param date
     * @return true if the card will/has expired by the given date
     */
    public static boolean isExpiredBy(String exp, Calendar date) {
        Calendar cardExpiry = parseExpirationDate(exp);

        // this transformation takes a given date and sets it to the last day of the month (at 11:59:59.999 PM)
        cardExpiry.add(Calendar.MONTH, +1);
        cardExpiry.set(Calendar.DAY_OF_MONTH, 1);
        cardExpiry.add(Calendar.DAY_OF_MONTH, -1);
        cardExpiry.set(Calendar.HOUR_OF_DAY, 23);
        cardExpiry.set(Calendar.MINUTE, 59);
        cardExpiry.set(Calendar.SECOND, 59);
        cardExpiry.set(Calendar.MILLISECOND, 999);

        return cardExpiry.before(date);
    }

    /**
     *
     * Checks an expiration date to see if it has expired.
     *
     * @param exp The expiration date as MMYY
     * @return true if the date given has passed.
     * @throws IllegalArgumentException if the date is not in the correct
     * format.
     */
    public static boolean isExpired(String exp) {
        Calendar currentDate = Calendar.getInstance();
        return isExpiredBy(exp, currentDate);
    }

    /**
     * Extracts the last four of the PAN if available (throws an error
     * otherwise)
     *
     * -- no params --
     *
     * @return
     * @throws Exception if the pan is unavailable
     */
    public String getLastFour() throws Exception {
        int panLength = this.getPan().length();
        return this.getPan().substring(panLength - 4, panLength);
    }

    /**
     * Extracts the last four of the PAN if available
     *
     * @param pan
     * @return
     */
    public static String getLastFour(String pan) {
        if( pan == null || pan.isEmpty()) return null;
        int panLength = pan.length();
        return pan.substring(panLength - 4, panLength);
    }

    /**
     * Checks if the last four of the PAN matches the string given.
     *
     * @param lastFour The string to match.
     * @return true if the lastFour match.
     * @throws Exception If the pan is not available.
     */
    public boolean lastFourMatch(String lastFour) throws Exception {
        String myLastFour = getLastFour();
        if (myLastFour.equals(lastFour)) {
            return true;
        }
        return false;
    }

    /**
     * Extracts first xxx of the PAN if available (throwing an error otherwise)
     *
     * @param num Num of digits to return
     * @return digits
     * @throws Exception if pan unavailable
     */
    public String getFirst(int num) throws Exception {

        if (num < 1 || num > 6) {
            num = 4;
        }

        return this.getPan().substring(0, num);
    }

    /**
     * Checks if the first two of the PAN matches a given string.
     *
     * @param digits the string to match
     * @return true if firstTwo matches
     * @throws Exception if pan unavailable
     *
     */
    public boolean firstMatch(String digits) throws Exception {
        String firstDigits = getFirst(digits.length());
        if (firstDigits.equals(digits)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the service code, parsing it from the track data if necessary.
     *
     * @return The service code
     * @throws Exception The service code could not be parsed.
     */
    public String getServiceCode() throws Exception {
        if (serviceCode == null) {
            if (track1 != null) {
                parseTrack1();
            }

            if (serviceCode == null && track2 != null) {
                parseTrack2();
            }

            if (serviceCode == null) {
                return null;
            }
        }

        return serviceCode;
    }

    /**
     * Gets the track one discretionary data, parsing it from track one if
     * necessary.
     *
     * @return The discretionary data.
     * @throws Exception if the data could not be parsed.
     */
    public String getTrackOneDiscretionary() throws Exception {
        if (tk1DiscretionaryData == null) {
            if (track1 != null) {
                parseTrack1();
            }

            if (tk1DiscretionaryData == null) {
                return null;
            }
        }

        return tk1DiscretionaryData;
    }

    /**
     * Gets the track two discretionary data, parsing it from track two if
     * necessary.
     *
     * @return The discretionary data.
     * @throws Exception if the data could not be parsed.
     */
    public String getTrackTwoDiscretionary() throws Exception {
        if (tk2DiscretionaryData == null) {
            if (track2 != null) {
                parseTrack2();
            }

            if (tk2DiscretionaryData == null) {
                return null;
            }
        }

        return tk2DiscretionaryData;
    }

    /**
     * Returns the CVV value, this is not parsed from the tracks.
     *
     * @return The CVV data
     * @throws Exception if the CVV has not been set.
     */
    public String getCvv() throws Exception {
        if (cvv == null) {
            return null;
        }

        return cvv;
    }

    /**
     * Sets the CVV value
     *
     * @param cvv The CVV
     * @throws Exception if the CVV has not been set.
     */
    public void setCvv(String cvv) throws Exception {
        this.cvv = cvv;
    }

    /**
     * Returns weather this object contains any track data.
     *
     * @return True if this object was constructed with track data.
     */
    public boolean hasTrackData() {
        return swiped;
    }

    /**
     * Uses the regular expression to parse track one.
     *
     */
    private void parseTrack1() {
        Matcher matcher = track1Regex.matcher(track1);

        if (matcher.matches()) {
            this.pan = matcher.group(TRACK_ONE_PAN_GROUP);

            this.expiryMonth = matcher.group(TRACK_ONE_EXP_MONTH_GROUP);
            this.expiryYear = matcher.group(TRACK_ONE_EXP_YEAR_GROUP);
            this.serviceCode = matcher.group(TRACK_ONE_SERVICE_CODE_GROUP);
            this.tk1DiscretionaryData = matcher.group(TRACK_ONE_DISCRETIONARY_GROUP);

            String name = matcher.group(TRACK_ONE_CARDHOLDER_NAME_GROUP);

            if (name != null) {

                String[] names = name.split("/", 2);
                this.cardholderName = String.format("%s %s", names[1], names[0]);

            }
        }
    }

    /**
     * Uses the regular expression to parse track two.
     *
     */
    private void parseTrack2() {
        Matcher matcher = track2Regex.matcher(track2);

        if (matcher.matches()) {
            this.pan = matcher.group(TRACK_TWO_PAN_GROUP);
            this.expiryMonth = matcher.group(TRACK_TWO_EXP_MONTH_GROUP);
            this.expiryYear = matcher.group(TRACK_TWO_EXP_YEAR_GROUP);
            this.serviceCode = matcher.group(TRACK_TWO_SERVICE_CODE_GROUP);
            this.tk2DiscretionaryData = matcher.group(TRACK_TWO_DISCRETIONARY_GROUP);
        }
    }

    /**
     * Return a debug representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        try {
            return String.format("StandardPaymentCard - pan: |%s| exp: |%s| ", getMaskedPan(), getExpirationDate());
        } catch (Exception mdex) {
            return "StandardPaymentCard - invalid card data";
        }
    }
}
