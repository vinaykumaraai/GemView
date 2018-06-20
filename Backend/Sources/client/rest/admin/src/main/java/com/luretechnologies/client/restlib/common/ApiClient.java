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
package com.luretechnologies.client.restlib.common;

import com.luretechnologies.client.restlib.service.model.ErrorResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import javax.annotation.Generated;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status.Family;

@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public final class ApiClient {

    private final Map<String, Client> hostMap = new HashMap<>();
    private final Map<String, String> defaultHeaderMap = new HashMap<>();
    private boolean debugging = false;
    private String basePath = "http://localhost:3080/admin/api/";
    private final JSON json = new JSON();

    private static String authToken;
    private int statusCode;
    private Map<String, List<String>> responseHeaders;

    private DateFormat dateFormat;

    public ApiClient() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        addDefaultHeader("User-Agent", "Java-Swagger");
    }

    public boolean hasAuthToken() {
        return (authToken != null && !authToken.isEmpty());
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String token) {
        authToken = token;
    }

    /**
     *
     * @return
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     *
     * @param basePath
     * @return
     */
    public ApiClient setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    /**
     * Gets the status code of the previous request
     *
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the response headers of the previous request
     *
     * @return
     */
    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Add a default header.
     *
     * @param key The header key
     * @param value The header value
     * @return
     */
    public ApiClient addDefaultHeader(String key, String value) {
        defaultHeaderMap.put(key, value);
        return this;
    }

    /**
     * Check that whether debugging is enabled for this API client.
     *
     * @return
     */
    public boolean isDebugging() {
        return debugging;
    }

    /**
     * Enable/disable debugging for this API client.
     *
     * @param debugging To enable (true) or disable (false) debugging
     * @return
     */
    public ApiClient setDebugging(boolean debugging) {
        this.debugging = debugging;
        return this;
    }

    /**
     * Get the date format used to parse/format date parameters.
     *
     * @return
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * Set the date format used to parse/format date parameters.
     *
     * @param dateFormat
     * @return
     */
    public ApiClient getDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    /**
     * Parse the given string into Date object.
     *
     * @param str
     * @return
     */
    public Date parseDate(String str) {
        try {
            return dateFormat.parse(str);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Format the given Date object into string.
     *
     * @param date
     * @return
     */
    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Format the given parameter object into string.
     *
     * @param param
     * @return
     */
    public String parameterToString(Object param) {
        if (param == null) {
            return "";
        } else if (param instanceof Date) {
            return formatDate((Date) param);
        } else if (param instanceof Collection) {
            StringBuilder b = new StringBuilder();
            for (Object o : (Collection) param) {
                if (b.length() > 0) {
                    b.append(",");
                }
                b.append(String.valueOf(o));
            }
            return b.toString();
        } else {
            return String.valueOf(param);
        }
    }

    /*
     Format to {@code Pair} objects.
     */
    /**
     *
     * @param collectionFormat
     * @param name
     * @param value
     * @return
     */
    public List<Pair> parameterToPairs(String collectionFormat, String name, Object value) {
        List<Pair> params = new ArrayList<>();

        // preconditions
        if (name == null || name.isEmpty() || value == null) {
            return params;
        }

        Collection valueCollection;
        if (value instanceof Collection) {
            valueCollection = (Collection) value;
        } else {
            params.add(new Pair(name, parameterToString(value)));
            return params;
        }

        if (valueCollection.isEmpty()) {
            return params;
        }

        // get the collection format
        collectionFormat = (collectionFormat == null || collectionFormat.isEmpty() ? "csv" : collectionFormat); // default: csv

        // create the params based on the collection format
        if (collectionFormat.equals("multi")) {
            for (Object item : valueCollection) {
                params.add(new Pair(name, parameterToString(item)));
            }

            return params;
        }

        String delimiter = ",";

        switch (collectionFormat) {
            case "csv":
                delimiter = ",";
                break;
            case "ssv":
                delimiter = " ";
                break;
            case "tsv":
                delimiter = "\t";
                break;
            case "pipes":
                delimiter = "|";
                break;
        }

        StringBuilder sb = new StringBuilder();
        for (Object item : valueCollection) {
            sb.append(delimiter);
            sb.append(parameterToString(item));
        }

        params.add(new Pair(name, sb.substring(1)));

        return params;
    }

    /**
     * Select the Accept header value from the given accepts array: if JSON
     * exists in the given array, use it; otherwise use all of them (joining
     * into a string)
     *
     * @param accepts The accepts array to select from
     * @return The Accept header to use. If the given array is empty, null will
     * be returned (not to set the Accept header explicitly).
     */
    public String selectHeaderAccept(String[] accepts) {
        if (accepts.length == 0) {
            return null;
        }
        if (StringUtil.containsIgnoreCase(accepts, "application/json")) {
            return "application/json";
        }
        return StringUtil.join(accepts, ",");
    }

    /**
     * Select the Content-Type header value from the given array: if JSON exists
     * in the given array, use it; otherwise use the first one of the array.
     *
     * @param contentTypes The Content-Type array to select from
     * @return The Content-Type header to use. If the given array is empty, JSON
     * will be used.
     */
    public String selectHeaderContentType(String[] contentTypes) {
        if (contentTypes.length == 0) {
            return "application/json";
        }
        if (StringUtil.containsIgnoreCase(contentTypes, "application/json")) {
            return "application/json";
        }
        return contentTypes[0];
    }

    /**
     * Escape the given string to be used as URL query value.
     *
     * @param str
     * @return
     */
    public String escapeString(String str) {
        try {
            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * Serialize the given Java object into string according the given
     * Content-Type (only JSON is supported for now).
     *
     * @param obj
     * @param contentType
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    protected String serialize(Object obj, String contentType) throws ApiException {
        if (contentType.startsWith("application/json")) {
            return json.serialize(obj);
        } else {
            throw new ApiException(400, "can not serialize object into Content-Type: " + contentType);
        }
    }

    /**
     * Deserialize response body to Java object according to the Content-Type.
     *
     * @param response
     * @param returnType
     * @return
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    protected DeserializedResponse deserialize(ClientResponse response, TypeRef returnType) throws ApiException {
        String contentType = null;
        List<String> contentTypes = response.getHeaders().get("Content-Type");
        if (contentTypes != null && !contentTypes.isEmpty()) {
            contentType = contentTypes.get(0);
        }
        if (contentType == null) {
            throw new ApiException(500, "missing Content-Type in response");
        }

        DeserializedResponse desResp = new DeserializedResponse();

        desResp.setBody("");
        if (response.hasEntity()) {
            desResp.setBody((String) response.getEntity(String.class));
        }

        if (!contentType.startsWith("application/json")) {
            throw new ApiException(500, "can not deserialize Content-Type: " + contentType);
        }
        desResp.setEntity(json.deserialize(desResp.getBody(), returnType));
        return desResp;
    }

    private ClientResponse getAPIResponse(String path, String method, List<Pair> queryParams, Object body, byte[] binaryBody, Map<String, String> headerParams, Map<String, Object> formParams, String accept, String contentType) throws ApiException {

        if (body != null && binaryBody != null) {
            throw new ApiException(500, "either body or binaryBody must be null");
        }

        System.out.println("=====================");

        Client client = getClient();

        StringBuilder b = new StringBuilder();
        b.append("?");
        if (queryParams != null) {
            for (Pair queryParam : queryParams) {
                if (!queryParam.getName().isEmpty()) {
                    b.append(escapeString(queryParam.getName()));
                    b.append("=");
                    b.append(escapeString(queryParam.getValue()));
                    b.append("&");
                }
            }
        }

        String querystring = b.substring(0, b.length() - 1);
        String uri = getBasePath() + path + querystring;

        Builder builder;
        if (accept == null) {
            builder = client.resource(uri).getRequestBuilder();
        } else {
            builder = client.resource(uri).accept(accept);
        }

        headerParams.put("Content-Type", contentType);
        if (authToken != null && !authToken.isEmpty()) {
            headerParams.put("X-Auth-Token", authToken);
            System.out.println(String.format("Using token for [%s]: %s", path + querystring, authToken.substring(authToken.length() - 15)));
        }
        for (String key : headerParams.keySet()) {
            builder = builder.header(key, headerParams.get(key));
        }
        for (String key : defaultHeaderMap.keySet()) {
            if (!headerParams.containsKey(key)) {
                builder = builder.header(key, defaultHeaderMap.get(key));
            }
        }

        String encodedFormParams = null;
        if (contentType.startsWith("multipart/form-data")) {
            FormDataMultiPart mp = new FormDataMultiPart();
            for (Entry<String, Object> param : formParams.entrySet()) {
                if (param.getValue() instanceof File) {
                    File file = (File) param.getValue();
                    mp.field(param.getKey(), file.getName());
                    mp.bodyPart(new FileDataBodyPart(param.getKey(), file, MediaType.MULTIPART_FORM_DATA_TYPE));
                } else {
                    mp.field(param.getKey(), parameterToString(param.getValue()), MediaType.MULTIPART_FORM_DATA_TYPE);
                }
            }
            body = mp;
        } else if (contentType.startsWith("application/x-www-form-urlencoded")) {
            encodedFormParams = this.getXWWWFormUrlencodedParams(formParams);
        }

        ClientResponse response = null;

        if (null != method) {
            switch (method) {
                case "GET":
                    System.out.println(String.format("GET %s", path + querystring));
                    response = builder.get(ClientResponse.class);
                    break;
                case "POST":
                    if (encodedFormParams != null) {
                        response = builder.type(contentType).post(ClientResponse.class, encodedFormParams);
                    } else if (body == null) {
                        if (binaryBody == null) {
                            response = builder.post(ClientResponse.class, null);
                        } else {
                            response = builder.type(contentType).post(ClientResponse.class, binaryBody);
                        }
                    } else if (body instanceof FormDataMultiPart) {
                        response = builder.type(contentType).post(ClientResponse.class, body);
                    } else {
                        String serializedBody = serialize(body, contentType);
                        System.out.println(String.format("POST %s with %s", path + querystring, serializedBody.replace("\n", "").replace("\r", "")));
                        response = builder.post(ClientResponse.class, serializedBody);
                    }
                    break;
                case "PUT":
                    if (encodedFormParams != null) {
                        response = builder.type(contentType).put(ClientResponse.class, encodedFormParams);
                    } else if (body == null) {
                        if (binaryBody == null) {
                            response = builder.put(ClientResponse.class, null);
                        } else {
                            response = builder.type(contentType).put(ClientResponse.class, binaryBody);
                        }
                    } else {
                        String serializedBody = serialize(body, contentType);
                        System.out.println(String.format("PUT %s with %s", path + querystring, serializedBody.replace("\n", "").replace("\r", "")));
                        response = builder.type(contentType).put(ClientResponse.class, serializedBody);
                    }
                    break;
                case "DELETE":
                    System.out.println(String.format("DELETE %s", path + querystring));
                    if (encodedFormParams != null) {
                        response = builder.type(contentType).delete(ClientResponse.class, encodedFormParams);
                    } else if (body == null) {
                        if (binaryBody == null) {
                            response = builder.delete(ClientResponse.class);
                        } else {
                            response = builder.type(contentType).delete(ClientResponse.class, binaryBody);
                        }
                    } else {
                        response = builder.type(contentType).delete(ClientResponse.class, serialize(body, contentType));
                    }
                    break;
                default:
                    throw new ApiException(500, "Unknown method type " + method);
            }
        }

        System.out.println("=====================");
        return response;
    }

    /**
     * Invoke API by sending HTTP request with the given options.
     *
     * @param <T>
     * @param path The sub-path of the HTTP URL
     * @param method The request method, one of "GET", "POST", "PUT", and
     * "DELETE"
     * @param queryParams The query parameters
     * @param body The request body object - if it is not binary, otherwise null
     * @param binaryBody The request body object - if it is binary, otherwise
     * null
     * @param headerParams The header parameters
     * @param formParams The form parameters
     * @param accept The request Accept header
     * @param contentType The request Content-Type header
     * @param returnType
     * @return The response body in type of string
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public <T> T invokeAPI(String path, String method, List<Pair> queryParams, Object body, byte[] binaryBody, Map<String, String> headerParams, Map<String, Object> formParams, String accept, String contentType, TypeRef returnType) throws ApiException {

        ClientResponse response = getAPIResponse(path, method, queryParams, body, binaryBody, headerParams, formParams, accept, contentType);

        statusCode = response.getStatusInfo().getStatusCode();
        responseHeaders = response.getHeaders();

        List<String> items = responseHeaders.get(CommonConstants.HEADER_X_AUTH_TOKEN);

        if (items != null) {
            setAuthToken(items.get(0));
            System.out.println(String.format("Saving new token for [%s]: %s", path, getAuthToken().substring(getAuthToken().length() - 15)));
        }

        if (response.getStatusInfo() == ClientResponse.Status.NO_CONTENT) {
            return null;
        } else if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
            if (returnType == null) {
                return null;
            } else {
                System.out.println(String.format("%s Response [%s]: %s", method, path, response));
                return (T) deserialize(response, returnType).getEntity();
            }
        }

        String bodyOut = "";
        String message = "error";
        DeserializedResponse desResp;

        if (response.hasEntity()) {
            try {
                TypeRef errorType = new TypeRef<ErrorResponse>() {
                };
                desResp = deserialize(response, errorType);
                bodyOut = desResp.getBody();
                ErrorResponse error = (ErrorResponse) desResp.getEntity();
                message = error.getMessage();
            } catch (Exception e) {

            }
        }
        throw new ApiException(
                response.getStatusInfo().getStatusCode(),
                message,
                response.getHeaders(),
                bodyOut);
    }

    /**
     * Invoke API by sending HTTP request with the given options - return binary
     * result
     *
     * @param path The sub-path of the HTTP URL
     * @param method The request method, one of "GET", "POST", "PUT", and
     * "DELETE"
     * @param queryParams The query parameters
     * @param body The request body object - if it is not binary, otherwise null
     * @param binaryBody The request body object - if it is binary, otherwise
     * null
     * @param headerParams The header parameters
     * @param formParams The form parameters
     * @param accept The request Accept header
     * @param contentType The request Content-Type header
     * @return The response body in type of string
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public byte[] invokeBinaryAPI(String path, String method, List<Pair> queryParams, Object body, byte[] binaryBody, Map<String, String> headerParams, Map<String, Object> formParams, String accept, String contentType) throws ApiException {

        ClientResponse response = getAPIResponse(path, method, queryParams, body, binaryBody, headerParams, formParams, accept, contentType);

        if (response.getStatusInfo() == ClientResponse.Status.NO_CONTENT) {
            return null;
        } else if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
            if (response.hasEntity()) {
                DataInputStream stream = new DataInputStream(response.getEntityInputStream());
                byte[] data = new byte[response.getLength()];
                try {
                    stream.readFully(data);
                } catch (IOException ex) {
                    throw new ApiException(500, "Error obtaining binary response data");
                }
                return data;
            } else {
                return new byte[0];
            }
        } else {
            String message = "error";
            if (response.hasEntity()) {
                try {
                    message = String.valueOf(response.getEntity(String.class));
                } catch (RuntimeException e) {
                    // e.printStackTrace();
                }
            }
            throw new ApiException(
                    response.getStatusInfo().getStatusCode(),
                    message);
        }
    }

    /**
     * Encode the given form parameters as request body.
     */
    private String getXWWWFormUrlencodedParams(Map<String, Object> formParams) {
        StringBuilder formParamBuilder = new StringBuilder();

        for (Entry<String, Object> param : formParams.entrySet()) {
            String keyStr = param.getKey();
            String valueStr = parameterToString(param.getValue());
            try {
                formParamBuilder.append(URLEncoder.encode(param.getKey(), "utf8"))
                        .append("=")
                        .append(URLEncoder.encode(valueStr, "utf8"));
                formParamBuilder.append("&");
            } catch (UnsupportedEncodingException e) {
                // move on to next
            }
        }

        String encodedFormParams = formParamBuilder.toString();
        if (encodedFormParams.endsWith("&")) {
            encodedFormParams = encodedFormParams.substring(0, encodedFormParams.length() - 1);
        }

        return encodedFormParams;
    }

    /**
     * Get an existing client or create a new client to handle HTTP request.
     */
    private Client getClient() {
        if (!hostMap.containsKey(getBasePath())) {
            Client client = Client.create();

            // TODO
            client.setConnectTimeout(85000);
            client.setReadTimeout(85000);

            if (debugging) {
                client.addFilter(new LoggingFilter());
            }
            hostMap.put(getBasePath(), client);
        }
        return hostMap.get(getBasePath());
    }

    class DeserializedResponse {

        private String body;
        private Object entity;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Object getEntity() {
            return entity;
        }

        public void setEntity(Object entity) {
            this.entity = entity;
        }

    }
}
