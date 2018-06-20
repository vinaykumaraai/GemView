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
import com.luretechnologies.mailslurperclient.MailApiClient;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthTest {

    private static String temporaryPassword = null;
    private static String verificationCode = null;

    private static RestClientService service;
    private static UserSession userSession;
    private static MailApiClient mail_test_standard;
    private static MailApiClient mail_test_updatepw;
    private static MailApiClient mail_test_twofactor;

    private static long startTime;

    @BeforeClass
    public static void createService() {
        try {

            service = new RestClientService(Utils.ADMIN_SERVICE_URL, Utils.TMS_SERVICE_URL);
            assumeNotNull(service);

            mail_test_standard = new MailApiClient("atl.lure68.net", 1100, "test_standard", "anything");
            mail_test_updatepw = new MailApiClient("atl.lure68.net", 1100, "test_updatepw", "anything");
            mail_test_twofactor = new MailApiClient("atl.lure68.net", 1100, "test_twofactor", "anything");
        } catch (Exception ex) {
        }
    }

    @AfterClass
    public static void removeService() {
    }

    @Before
    public void setUp() {

        startTime = System.currentTimeMillis();

        try {
            DeleteAllEmail();
        } catch (Exception ex) {
        }
    }

    @After
    public void tearDown() {
        try {
            DeleteAllEmail();
        } catch (Exception ex) {
        }
    }

    @Test
    public void auth_001a_loginStandard() {

        try {
            userSession = service.getAuthApi().login(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User requires two factor", userSession.isPerformTwoFactor());
            assertFalse("User requires password update", userSession.isRequirePasswordUpdate());
        } catch (ApiException ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_001b_loginStandard() {

        try {
            userSession = service.getAuthApi().login(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User requires two factor", userSession.isPerformTwoFactor());
            assertFalse("User requires password update", userSession.isRequirePasswordUpdate());
            service.getAuthApi().logout();
        } catch (ApiException ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_001c_loginEx() {

        try {
            userSession = service.getAuthApi().loginEx(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User requires two factor", userSession.isPerformTwoFactor());
            assertFalse("User requires password update", userSession.isRequirePasswordUpdate());
            service.getAuthApi().logout();
        } catch (ApiException ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_002a_loginTwoFactor() {

        try {
            verificationCode = null;
            userSession = null;

            userSession = service.getAuthApi().login(CommonConstants.testTwoFactorUsername, CommonConstants.testTwoFactorPassword);
            assertNotNull("User failed login", userSession);

            assertTrue("User did not require two factor auth.", userSession.isPerformTwoFactor());

            verificationCode = getContentFromEmail(mail_test_twofactor, "Verification Code: ");
            assertNotNull("Verification code not retrieved.", verificationCode);

            service.getAuthApi().verifyCode(verificationCode);

            service.getAuthApi().logout();

        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_002b_loginTwoFactorWithResend() {

        try {
            verificationCode = null;
            userSession = null;

            userSession = service.getAuthApi().login(CommonConstants.testTwoFactorUsername, CommonConstants.testTwoFactorPassword);
            assertNotNull("User failed login", userSession);

            assertTrue("User did not require two factor auth.", userSession.isPerformTwoFactor());

            verificationCode = getContentFromEmail(mail_test_twofactor, "Verification Code: ");
            assertNotNull("Verification code not retrieved.", verificationCode);

            System.out.println("Got 1st verification code: " + verificationCode);

            verificationCode = null;
            DeleteAllEmail();

            // ask for another verification code
            service.getAuthApi().resendCode();

            verificationCode = getContentFromEmail(mail_test_twofactor, "Verification Code: ");
            assertNotNull("Verification code not retrieved.", verificationCode);

            System.out.println("Got 2nd verification code: " + verificationCode);

            service.getAuthApi().verifyCode(verificationCode);

            service.getAuthApi().logout();

        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_003a_loginUpdatePassword() {

        try {
            temporaryPassword = null;
            userSession = null;

            service.getAuthApi().forgotPassword(CommonConstants.testUpdatePwEmail);
            temporaryPassword = getContentFromEmail(mail_test_updatepw, "Temporary Password: ");
            assertNotNull("Temporary password not retrieved.", temporaryPassword);
            System.out.println("Temporary Password: [" + temporaryPassword + "] [" + Utils.encryptPassword(temporaryPassword) + "]");

            // verify password update is required
            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, temporaryPassword);
            assertNotNull("User failed login", userSession);
            assertTrue("User did not require password update.", userSession.isRequirePasswordUpdate());

            service.getAuthApi().logout();

            CommonConstants.testUpdatePwPassword = Utils.generatePassword(10);
            System.out.println("New Password: [" + CommonConstants.testUpdatePwPassword + "] [" + Utils.encryptPassword(CommonConstants.testUpdatePwPassword) + "]");

            service.getAuthApi().updatePassword(CommonConstants.testUpdatePwEmail, temporaryPassword, CommonConstants.testUpdatePwPassword);

            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, CommonConstants.testUpdatePwPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User required two factor", userSession.isPerformTwoFactor());
            assertFalse("User required password update", userSession.isRequirePasswordUpdate());

            service.getUserApi().getUsers();

            service.getAuthApi().logout();

        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_003b_loginUpdatePassword() {

        try {
            temporaryPassword = null;
            userSession = null;

            service.getAuthApi().forgotPassword(CommonConstants.testUpdatePwEmail);
            temporaryPassword = getContentFromEmail(mail_test_updatepw, "Temporary Password: ");
            assertNotNull("Temporary password not retrieved.", temporaryPassword);
            System.out.println("Temporary Password: [" + temporaryPassword + "] [" + Utils.encryptPassword(temporaryPassword) + "]");

            CommonConstants.testUpdatePwPassword = Utils.generatePassword(10);
            System.out.println("New Password: [" + CommonConstants.testUpdatePwPassword + "] [" + Utils.encryptPassword(CommonConstants.testUpdatePwPassword) + "]");

            service.getAuthApi().updatePasswordEx(CommonConstants.testUpdatePwEmail, temporaryPassword, CommonConstants.testUpdatePwPassword);

            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, CommonConstants.testUpdatePwPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User required two factor", userSession.isPerformTwoFactor());
            assertFalse("User required password update", userSession.isRequirePasswordUpdate());

            service.getUserApi().getUsers();

            service.getAuthApi().logout();

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    @Test
    public void auth_003c_loginUpdatePasswordEx() {

        try {
            temporaryPassword = null;
            userSession = null;

            service.getAuthApi().forgotPassword(CommonConstants.testUpdatePwEmail);
            temporaryPassword = getContentFromEmail(mail_test_updatepw, "Temporary Password: ");
            assertNotNull("Temporary password not retrieved.", temporaryPassword);
            System.out.println("Temporary Password: [" + temporaryPassword + "] [" + Utils.encryptPassword(temporaryPassword) + "]");

            // verify password update is required
            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, temporaryPassword);
            assertNotNull("User failed login", userSession);
            assertTrue("User did not require password update.", userSession.isRequirePasswordUpdate());

            service.getAuthApi().logout();

            CommonConstants.testUpdatePwPassword = Utils.generatePassword(10);
            System.out.println("New Password: [" + CommonConstants.testUpdatePwPassword + "] [" + Utils.encryptPassword(CommonConstants.testUpdatePwPassword) + "]");

            service.getAuthApi().updatePassword(CommonConstants.testUpdatePwEmail, temporaryPassword, CommonConstants.testUpdatePwPassword);

            userSession = service.getAuthApi().login(CommonConstants.testUpdatePwUsername, CommonConstants.testUpdatePwPassword);
            assertNotNull("User failed login", userSession);
            assertFalse("User required two factor", userSession.isPerformTwoFactor());
            assertFalse("User required password update", userSession.isRequirePasswordUpdate());

            service.getUserApi().getUsers();

            service.getAuthApi().logout();

        } catch (Exception ex) {
            fail(ex.getMessage());
        } finally {
            System.out.println(String.format("%s took %dms", Utils.getMethodName(), System.currentTimeMillis() - startTime));
        }
    }

    private String getContentFromEmail(MailApiClient inbox, String afterText) throws Exception {

        int waitTimeSeconds = 15;
        int count = 0;
        long beginTime = System.currentTimeMillis();

        while (Utils.elapsedSecondsSince(beginTime) < waitTimeSeconds) {

            count = inbox.GetEmailCount();

            if (count != 0) {
                break;
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
            }
        }

        if (count > 0) {

            String regexString = Pattern.quote(afterText) + "(.*?)" + Pattern.quote("<br>");
            Pattern p = Pattern.compile(regexString);

            String content = inbox.GetMailItemContent();
            System.out.println(String.format("Message [%s]\n", content));
            Matcher m = p.matcher((String) content);
            if (m.find()) {
                return m.group(1);
            }
        }

        throw new IOException("Got no mail.");
    }

    private static void DeleteAllEmail() throws Exception {
        mail_test_standard.DeleteAllEmail();
        mail_test_updatepw.DeleteAllEmail();
        mail_test_twofactor.DeleteAllEmail();
    }
}
