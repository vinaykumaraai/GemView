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

import com.luretechnologies.mailslurper.MailCollection;
import com.luretechnologies.mailslurper.MailCount;
import com.luretechnologies.mailslurper.MailItem;
import java.io.IOException;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;

public class MailApiClient {

    HttpClientEx http;

    public MailApiClient(String protocol, String address) {
        http = new HttpClientEx(protocol, address);
    }

    public MailItem GetMailItem(String id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpResponseEx response = http.get("/mail/" + id);
        String content = response.getContent();
        content = content.replace(",\"transferEncoding\":\"\",\"Message\":null,\"InlineAttachments\":null,\"TextBody\":\"\",\"HTMLBody\":\"\"", "");
        //debug("GetMailItem", content);
        MailItem item = mapper.readValue(content, MailItem.class);
        return item;
    }

    public MailCollection GetMailCollection() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpResponseEx response = http.get("/mail");
        String content = response.getContent();
        content = content.replace(",\"transferEncoding\":\"\",\"Message\":null,\"InlineAttachments\":null,\"TextBody\":\"\",\"HTMLBody\":\"\"", "");
        //debug("GetMailCollection", content);
        MailCollection mc = mapper.readValue(content, MailCollection.class);
        return mc;
    }

    public int GetEmailCount() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpResponseEx response = http.get("/mailcount");
        String content = response.getContent();
        //debug("GetEmailCount", content);
        MailCount mc = mapper.readValue(content, MailCount.class);
        return mc.getMailCount();
    }

    public void DeleteAllEmail() throws IOException {
        StringEntity entity = new StringEntity("{\"pruneCode\":\"all\"}");
        http.delete("/mail", entity);
    }

    private void debug(String method, String content) {
        System.out.println("===========================================================");
        System.out.println("Method: " + method);
        System.out.println("Content: " + content);
    }
}
