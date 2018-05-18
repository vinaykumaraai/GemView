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

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.*;

import java.io.IOException;

/**
 *
 * Gemstone
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-15T15:20:45.337-04:00")
public class JSON {

    private final ObjectMapper mapper;

    /**
     *
     */
    public JSON() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.registerModule(new JodaModule());
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj
     * @return
     */
    public String serialize(Object obj) throws ApiException {
        try {
            if (obj != null) {
                return mapper.writeValueAsString(obj);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>
     * @param body The JSON string
     * @param returnType The type to deserialize inot
     * @return The deserialized Java object
     */
    public <T> T deserialize(String body, TypeRef returnType) throws ApiException {
        JavaType javaType = mapper.constructType(returnType.getType());
        try {
            return mapper.readValue(body, javaType);
        } catch (IOException e) {
            if (returnType.getType().equals(String.class)) {
                return (T) body;
            } else {
                throw new ApiException(500, e.getMessage(), null, body);
            }
        }
    }
}
