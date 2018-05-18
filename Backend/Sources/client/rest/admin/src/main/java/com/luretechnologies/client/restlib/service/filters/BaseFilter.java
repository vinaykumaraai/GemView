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
package com.luretechnologies.client.restlib.service.filters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author User
 */
public abstract class BaseFilter {

    private static final String AND_PATTERN = "@&";
    private static final String EQUAL_PATTERN = "@=";
    private static final String NO_FILTER_KEY = "@NO_FILTER";

    private Map<String, String> filters;
    private List<String> filtersNoKey;
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public BaseFilter() {
        filters = new HashMap<>();
        filtersNoKey = new ArrayList<>();
    }

    public void addFilter(Date value) {
        addFilter(format.format(value));
    }

    public void addFilter(boolean value) {
        addFilter(String.valueOf(value));
    }

    public void addFilter(int value) {
        addFilter(String.valueOf(value));
    }

    public void addFilter(double value) {
        addFilter(String.valueOf(value));
    }

    public void addFilter(String value) {
        filtersNoKey.add(value);
    }

    public void addFilter(String key, Date value) {
        addFilter(key, format.format(value));
    }

    public void addFilter(String key, boolean value) {
        addFilter(key, format.format(value));
    }

    public void addFilter(String key, int value) {
        addFilter(key, String.valueOf(value));
    }

    public void addFilter(String key, double value) {
        addFilter(key, String.valueOf(value));
    }

    public void addFilter(String key, String value) {
        filters.put(key, value);
    }

    @Override
    public String toString() {
        String str = "";
        int pos = 0;
        for (Map.Entry<String, String> entrySet : filters.entrySet()) {
            Object key = entrySet.getKey();
            Object value = entrySet.getValue();
            if (pos != 0) {
                str += AND_PATTERN;
            }
            str += key + EQUAL_PATTERN + value;
            pos++;
        }
        for (String item : filtersNoKey) {
            if (pos != 0) {
                str += AND_PATTERN;
            }
            str += item;
            pos++;
        }
        return str;
    }
}
