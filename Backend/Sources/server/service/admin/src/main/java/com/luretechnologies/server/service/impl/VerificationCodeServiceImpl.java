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

import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.UserDAO;
import com.luretechnologies.server.data.model.User;
import com.luretechnologies.server.data.model.tms.Email;
import com.luretechnologies.server.service.VerificationCodeService;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.ws.rs.NotAuthorizedException;

@Service
@Transactional
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Email sendVerifyCode(User user) {

        // TODO -- make email text elements configurable
        Email email = new Email();

        String fromName = "DoNotReply";
        String subject = "Verification Code";
        String code = Utils.getRandomNumberAsString(8);

        String bodyMessage
                = "Dear Member," + "<br>"
                + "<br>"
                + "For Security Reasons, we have sent you a verification code." + "<br>"
                + "<br>"
                + "Verification Code: " + code + "<br>"
                + "<br>"
                + "Click " + "<a href=" + "http://www.gemstonepay.com/gem/" + "> here</a> to enter verification code." + "<br>"
                + "<br>"
                + "Important note:" + "<br>"
                + "The code above will only work for 10 minutes. If expired, you will need to request a new verification code.";

        email.setBody(bodyMessage);
        email.setContentType("text/html");
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setFromName(fromName);

        User updateUser = userDAO.findById(user.getId());
        
        // TODO -- make expiration time configurable
        updateUser.setVerificationCodeExpiration(new Timestamp(Utils.fromNow(Utils.VERIFICATION_CODE_TIME_LIMIT)));
        updateUser.setVerificationCode(Utils.encryptPassword(code));
        userDAO.persist(updateUser);

        return email;
    }

    @Override
    public boolean validateVerificationCode(User user, String code) throws Exception {

        if (user.getVerificationCodeExpiration().getTime() < System.currentTimeMillis()) {
            throw new NotAuthorizedException(Messages.EXPIRED_VERIFICATION_CODE, new Throwable());
        }

        if (user.getVerificationCode().equalsIgnoreCase(Utils.encryptPassword(code))) {
            return true;
        }

        throw new NotAuthorizedException(Messages.INVALID_VERIFICATION_CODE, new Throwable());
    }
}
