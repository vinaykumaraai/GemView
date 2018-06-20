/**
 * COPYRIGHT @ Lure Technologies, LLC. ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or form
 * other than in accordance with and subject to the terms of a written license
 * from Lure or with the prior written consent of Lure or as permitted by
 * applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure. If you are neither the intended
 * recipient, nor an agent, employee, nor independent contractor responsible for
 * delivering this message to the intended recipient, you are prohibited from
 * copying, disclosing, distributing, disseminating, and/or using the
 * information in this email in any manner. If you have received this message in
 * error, please advise us immediately at legal@luretechnologies.com by return
 * email and then delete the message from your computer and all other records
 * (whether electronic, hard copy, or otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.client.restlib.service.api;

import com.luretechnologies.client.restlib.common.ApiClient;
import com.luretechnologies.client.restlib.common.ApiException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.Generated;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-18T13:16:30.027-04:00")
public class TestApi extends BaseApi {

    private static final String METHOD_FILE_UPLOAD = "/test/fileUpload";

    /**
     * @param apiClient
     */
    public TestApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Login user Logs user into the system
     *
     * @param uniqueId
     * @param fileName
     * @throws com.luretechnologies.client.restlib.common.ApiException
     */
    public void fileUpload(String uniqueId, String fileName) throws ApiException {

        String token = apiClient.getAuthToken();
        String queryParams = "?uniqueId=" + uniqueId;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(apiClient.getBasePath() + METHOD_FILE_UPLOAD + queryParams);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            File f = new File(fileName);
            builder.addBinaryBody(
                    "file",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            uploadFile.setHeader("X-Auth-Token", token);

            CloseableHttpResponse response = httpClient.execute(uploadFile);

            int code = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();

            setToken(response.getAllHeaders());

            // if good, just return
            if (code >= 200 && code <= 204) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                System.out.println(responseString);
                return;
            }

            String hostErrorMessage = String.format("File Upload Failed: %d [%s]", code, reason);
            throw new ApiException(hostErrorMessage);

        } catch (IOException ex) {
            String hostErrorMessage = String.format("File Upload Failed: %d [%s]", 999, ex.getMessage());
            throw new ApiException(hostErrorMessage);
        }
    }

    private void setToken(Header[] headers) {
        for (Header header : headers) {
            if (header.getName().equalsIgnoreCase("x-auth-token")) {
                apiClient.setAuthToken(header.getValue());
            }
        }
    }
}
