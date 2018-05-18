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
package com.luretechnologies.client.restlib.service;

import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.UserSession;
import com.luretechnologies.mailslurper.MailCollection;
import com.luretechnologies.mailslurper.MailItem;
import com.luretechnologies.mailslurperclient.MailApiClient;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuthTest {

    private static String temporaryPassword = null;
    private static String verificationCode = null;

    private static RestClientService service;
    private static UserSession userSession;
    private static MailApiClient mail;

    @BeforeClass
    public static void createService() {
        service = new RestClientService(Utils.serviceUrl + "/admin/api", Utils.serviceUrl + "/payment/api");
        assumeNotNull(service);
        mail = new MailApiClient("http", "mia.lure68.net:58124");
    }

    @Test
    public void _001_loginStandard() {

        try {
            userSession = service.getAuthApi().login(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User requires two factor", userSession.isPerformTwoFactor());
            assertFalse("User requires password update", userSession.isRequirePasswordUpdate());
            service.getAuthApi().logout();
        } catch (ApiException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void _004_loginUpdatePassword() {

        try {
            temporaryPassword = null;
            userSession = null;

            service.getAuthApi().forgotPassword(CommonConstants.testUpdatePwEmail);
            temporaryPassword = getContentFromEmail("Temporary Password: ");
            assertNotNull("Temporary password not retrieved.", temporaryPassword);

            // verify password update is required
            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, temporaryPassword);
            assertNotNull("User failed login", userSession);
            assertTrue("User did not require password update.", userSession.isRequirePasswordUpdate());

            service.getAuthApi().logout();

            CommonConstants.testUpdatePwPassword = Utils.generatePassword(10);

            service.getAuthApi().updatePassword(CommonConstants.testUpdatePwEmail, temporaryPassword, CommonConstants.testUpdatePwPassword);

            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, CommonConstants.testUpdatePwPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User required two factor", userSession.isPerformTwoFactor());
            assertFalse("User required password update", userSession.isRequirePasswordUpdate());

            service.getHostApi().getHosts();

            service.getAuthApi().logout();

        } catch (ApiException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void _003_loginTwoFactor() {

        try {
            verificationCode = null;
            userSession = null;

            userSession = service.getAuthApi().login(CommonConstants.testTwoFactorUsername, CommonConstants.testTwoFactorPassword);
            assertNotNull("User failed login", userSession);

            assertTrue("User did not require two factor auth.", userSession.isPerformTwoFactor());

            verificationCode = getContentFromEmail("Verification Code: ");
            assertNotNull("Verification code not retrieved.", verificationCode);

            service.getAuthApi().verifyCode(verificationCode);

            service.getAuthApi().logout();

        } catch (ApiException | IOException ex) {
            fail(ex.getMessage());
        }
    }

    //@Test
    public void _002_loginUpdatePassword() {

        try {
            temporaryPassword = null;
            userSession = null;

            service.getAuthApi().forgotPassword(CommonConstants.testUpdatePwEmail);
            temporaryPassword = getContentFromEmail("Temporary Password: ");
            assertNotNull("Temporary password not retrieved.", temporaryPassword);
            System.out.println("Temporary Password: " + temporaryPassword + " [" + Utils.encryptPassword(temporaryPassword) + "]");

            CommonConstants.testUpdatePwPassword = Utils.generatePassword(10);
            System.out.println("New Password: " + CommonConstants.testUpdatePwPassword + " [" + Utils.encryptPassword(CommonConstants.testUpdatePwPassword) + "]");

            service.getAuthApi().updatePassword(CommonConstants.testUpdatePwEmail, temporaryPassword, CommonConstants.testUpdatePwPassword);

            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, CommonConstants.testUpdatePwPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User required two factor", userSession.isPerformTwoFactor());
            assertFalse("User required password update", userSession.isRequirePasswordUpdate());

            service.getHostApi().getHosts();

            service.getAuthApi().logout();

        } catch (ApiException | IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
            fail(ex.getMessage());
        }
    }

    private String getContentFromEmail(String afterText) throws IOException {

        int limit = 25;
        int count = mail.GetEmailCount();
        System.out.println(String.format("Mail Count = %d", count));

        while (count == 0 && limit > 0) {
            try {
                Thread.sleep(250);
                count = mail.GetEmailCount();
            } catch (InterruptedException ex) {
            }

            limit--;
        }

        MailCollection mc = mail.GetMailCollection();
        System.out.println(String.format("getTotalPages = %d", mc.getTotalPages()));
        System.out.println(String.format("getTotalRecords = %d", mc.getTotalRecords()));

        String regexString = Pattern.quote(afterText) + "(.*?)" + Pattern.quote("<br>");
        Pattern p = Pattern.compile(regexString);

        for (MailItem item : mc.getMailItems()) {
            System.out.println(String.format("MailItem [%s] [%s] [%s]\n", item.getId(), item.getSubject(), item.getBody()));
            Matcher m = p.matcher(item.getBody());
            if (m.find()) {
                return m.group(1);
            }
        }

        throw new IOException("Got no mail.");
    }
}
