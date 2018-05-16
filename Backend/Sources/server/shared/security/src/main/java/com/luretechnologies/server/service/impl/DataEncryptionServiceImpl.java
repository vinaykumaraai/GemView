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
package com.luretechnologies.server.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luretechnologies.server.security.encryption.util.CardData;
import com.luretechnologies.server.service.DataEncryptionService;
import com.luretechnologies.server.service.HSMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author developer
 */
@Service
public class DataEncryptionServiceImpl implements DataEncryptionService {

    @Autowired
    HSMService hsmService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public String encryptCardData(String data) throws Exception {
        // Initialize HSM Service
        hsmService.init();

        // Use HSM service for decrypting the data
        // TODO!
        CardData cardData = new CardData();
        cardData.setPan("4242424242424242");
        cardData.setExpDate("1604");
        cardData.setCardholderName("Jane Doe");

        // Convert Object to JSON String
        String stringCardAccount = mapper.writeValueAsString(cardData);
        System.out.println("~~~~~~~~~~ " + stringCardAccount);

        // Convert JSON String to Object
        cardData = mapper.readValue(stringCardAccount, new TypeReference<CardData>() {
        });
        System.out.println("~~~~~~~~~~ " + cardData.getCardholderName());

        return "";
    }

}
