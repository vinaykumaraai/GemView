package com.luretechnologies.server.service.supporting.email;

import com.luretechnologies.server.data.model.tms.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceConnector.class);

    /**
     *
     * @param email
     * @param correlationId
     * @return
     * @throws Exception
     */
    public Boolean process(Email email, String correlationId) throws Exception {

        LOGGER.info("EmailServiceConnector " + correlationId);
        LOGGER.info("  --> Account: " + email.getAccount());
        LOGGER.info("  --> From: " + email.getFrom());
        LOGGER.info("  --> FromName: " + email.getFromName());
        LOGGER.info("  --> To: " + email.getTo());
        LOGGER.info("  --> CC: " + email.getCC());
        LOGGER.info("  --> Subject: " + email.getSubject());
        LOGGER.info("  --> Body: " + email.getBody());
        LOGGER.info("  --> Content-Type: " + email.getContentType());

        boolean status = EmailSenderSMTP.sendEmail(email);
        LOGGER.info("  --> Status: " + status);
        return true;
    }
}
