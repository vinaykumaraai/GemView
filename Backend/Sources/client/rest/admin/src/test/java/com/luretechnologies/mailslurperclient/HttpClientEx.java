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
package com.luretechnologies.mailslurperclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author romer
 */
public class HttpClientEx {

    String protocol;
    String address;
    private String xauthtoken;

    public HttpClientEx(String protocol, String address) {
        this.protocol = protocol;
        this.address = address;
    }

    public HttpResponseEx put(String uri) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut request = new HttpPut(protocol + "://" + address + uri);
        request.addHeader("accept", "application/json");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

        if (getXauthtoken() != null && !xauthtoken.isEmpty()) {
            request.addHeader("x-auth-token", getXauthtoken());
        }

        HttpResponse response = client.execute(request);

        int code = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        //System.out.println(String.format("\nput: %s = %d [%s] \n  --> old: %s", uri, code, reason, getXauthtoken()));

        switch (code) {

            case 200:
                setToken(response.getAllHeaders());
                break;

            default:
                return new HttpResponseEx(code, response.getStatusLine().getReasonPhrase());
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder sb = new StringBuilder();
        String line = "";

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        return new HttpResponseEx(code, sb.toString());
    }

    public HttpResponseEx get(String uri) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(protocol + "://" + address + uri);
        request.addHeader("accept", "application/json");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

        if (getXauthtoken() != null && !xauthtoken.isEmpty()) {
            request.addHeader("x-auth-token", getXauthtoken());
        }

        HttpResponse response = client.execute(request);

        int code = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        //System.out.println(String.format("\nget: %s = %d [%s] \n  --> old: %s", uri, code, reason, getXauthtoken()));

        switch (code) {

            case 200:
                setToken(response.getAllHeaders());
                break;

            default:
                return new HttpResponseEx(code, reason);
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder sb = new StringBuilder();
        String line = "";

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        return new HttpResponseEx(code, sb.toString());
    }

    public HttpResponseEx get(String uri, Map items) throws IOException {

        int count = 0;
        HttpClient client = HttpClientBuilder.create().build();

        StringBuilder it = new StringBuilder();
        for (Object key : items.keySet()) {

            if (count++ == 0) {
                it.append("?");
            } else {
                it.append("&");
            }

            it.append(String.format("%s=%s", key, items.get(key)));
        }

        HttpGet request = new HttpGet(protocol + "://" + address + uri + it.toString());
        request.addHeader("accept", "application/json");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

        if (getXauthtoken() != null && !xauthtoken.isEmpty()) {
            request.addHeader("x-auth-token", getXauthtoken());
        }

        HttpResponse response = client.execute(request);

        int code = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        //System.out.println(String.format("\nget: %s = %d [%s] \n  --> old: %s", uri, code, reason, getXauthtoken()));

        switch (code) {

            case 200:
                setToken(response.getAllHeaders());
                break;

            default:
                return new HttpResponseEx(code, response.getStatusLine().getReasonPhrase());
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder sb = new StringBuilder();
        String line = "";

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        return new HttpResponseEx(code, sb.toString());
    }

    public void delete(String uri, StringEntity entity) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpDeleteEx request = new HttpDeleteEx(protocol + "://" + address + uri);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("accept", "application/json");

        if (getXauthtoken() != null && !xauthtoken.isEmpty()) {
            request.addHeader("x-auth-token", getXauthtoken());
        }

        request.setEntity(entity);

        HttpResponse response = client.execute(request);

        int code = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        //System.out.println(String.format("\ndelete: %s = %d [%s] \n  --> old: %s", uri, code, reason, getXauthtoken()));

        switch (code) {

            case 200:
                setToken(response.getAllHeaders());
                return;

            default:
                throw new IOException(String.format("Failed [%s]: %s", uri, response.getStatusLine()));
        }
        // -------------------------
    }

    public HttpResponseEx post(String uri, StringEntity entity) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(protocol + "://" + address + uri);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("accept", "application/json");

        if (getXauthtoken() != null && !xauthtoken.isEmpty()) {
            request.addHeader("x-auth-token", getXauthtoken());
        }

        request.setEntity(entity);

        HttpResponse response = client.execute(request);

        int code = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        //System.out.println(String.format("\npost: %s = %d [%s] \n  --> old: %s", uri, code, reason, getXauthtoken()));

        switch (code) {

            case 200:
            case 201:
                setToken(response.getAllHeaders());
                break;

            default:
                return new HttpResponseEx(code, response.getStatusLine().getReasonPhrase());
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        return new HttpResponseEx(code, sb.toString());
    }

    public HttpResponseEx post(String uri, Map items) throws IOException {

        int count = 0;
        HttpClient client = HttpClientBuilder.create().build();

        StringBuilder it = new StringBuilder();
        for (Object key : items.keySet()) {

            if (count++ == 0) {
                it.append("?");
            } else {
                it.append("&");
            }

            it.append(String.format("%s=%s", key, items.get(key)));
        }

        HttpPost request = new HttpPost(protocol + "://" + address + uri + it.toString());
        request.addHeader("accept", "application/json");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

        if (getXauthtoken() != null && !xauthtoken.isEmpty()) {
            request.addHeader("x-auth-token", getXauthtoken());
        }

        HttpResponse response = client.execute(request);

        int code = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();
        //System.out.println(String.format("\npost: %s = %d [%s] \n  --> old: %s", uri, code, reason, getXauthtoken()));

        switch (code) {

            case 200:
            case 201:
                setToken(response.getAllHeaders());
                break;

            default:
                return new HttpResponseEx(code, response.getStatusLine().getReasonPhrase());
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        return new HttpResponseEx(code, sb.toString());
    }

    private void setToken(Header[] headers) {
        for (Header header : headers) {
//            System.out.println("header --> " + header.getName() + ": " + header.getValue());
            if (header.getName().equalsIgnoreCase("x-auth-token")) {
                xauthtoken = header.getValue();
                System.out.println(String.format("  --> new: %s", getXauthtoken()));
            }
        }
    }

    /**
     * @return the xauthtoken
     */
    public String getXauthtoken() {
        return xauthtoken;
    }
}
