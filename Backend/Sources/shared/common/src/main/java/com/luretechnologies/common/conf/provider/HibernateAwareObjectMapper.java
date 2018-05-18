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
package com.luretechnologies.common.conf.provider;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.USE_GETTERS_AS_SETTERS;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import static com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature.FORCE_LAZY_LOADING;
import static com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS;
import static com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION;
import java.text.SimpleDateFormat;

/**
 *
 *
 * @author developer
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
     *
     */
    public HibernateAwareObjectMapper() {
        Hibernate4Module hm = new Hibernate4Module();
        hm.configure(FORCE_LAZY_LOADING, false);
        hm.configure(USE_TRANSIENT_ANNOTATION, false);
        hm.configure(SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, false);
        registerModule(hm);

        //The format for the date should be 'yyyy-MM-dd'T'HH:mm:ssX' but the 'X' is just for java 1.7 or greater
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));

        configure(WRITE_NUMBERS_AS_STRINGS, false);
        configure(FAIL_ON_EMPTY_BEANS, false);
        configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(USE_GETTERS_AS_SETTERS, false);

        setSerializationInclusion(NON_EMPTY);
    }
}
