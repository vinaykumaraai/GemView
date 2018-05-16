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
package com.luretechnologies.client.restlib.service;

import com.luretechnologies.client.restlib.Utils;
import com.luretechnologies.client.restlib.common.ApiException;
import com.luretechnologies.client.restlib.common.CommonConstants;
import com.luretechnologies.client.restlib.service.model.UserSession;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DashboardTest {

    private static RestClientService service;
    private static UserSession userSession;

    @BeforeClass
    public static void createService() {

        service = new RestClientService(Utils.serviceUrl + "/admin/api", Utils.serviceUrl + "/payment/api");

        assumeNotNull(service);

        try {
            userSession = service.getAuthApi().login(CommonConstants.testStandardUsername, CommonConstants.testStandardPassword);
            assertTrue("User logged in", userSession != null);
        } catch (ApiException ex) {
            fail(ex.getResponseBody());
        }
    }
//    
//    @Test
//    public void listLatestTransactions() {
//        try {
//            List<Transaction> res = service.listLatestTransactions(10);
//            assertNotNull(res);
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void getTotalTransactions() {
//        try {
//            Long count = service.getTotalTransactions();
//            assertNotNull(count);
//            System.out.println("Count: " + count);
//            
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void getRefundInformation(){
//        try{
//            TransactionData refund = service.getRefundInformation();
//            assertNotNull(refund);
//            System.out.println("Count: " + refund.getCount() + " - Amount: " + refund.getAmount());            
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void getVoidInformation(){
//        try{
//            TransactionData voidT = service.getVoidInformation();
//            assertNotNull(voidT);
//            System.out.println("Count: " + voidT.getCount() + " - Amount: " + voidT.getAmount());
//            
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        }
//    }
//    
//    @Test
//    public void getMonthlySales(){
//        try{
//            Date mydate = new Date();
//            DateFormat format = new SimpleDateFormat("yyMMdd");
//            String mydateStr = format.format(mydate);
//
//            SalesData sale = service.getMonthlySales(mydateStr);
//            assertNotNull(sale);
//            System.out.println("Sale Amount: " + sale.getSaleAmount() + " - Sale count: " + sale.getSaleCount() + " - Void count: " + sale.getVoidCount());
//            
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        } 
//    }
//    
//    @Test
//    public void getTodaySales(){
//        try{
//            Date mydate = new Date();
//            DateFormat format = new SimpleDateFormat("yyMMdd");
//            String mydateStr = format.format(mydate);
//            
//            SalesData sale = service.getTodaySales(mydateStr);
//            assertNotNull(sale);
//            System.out.println("Sale Amount: " + sale.getSaleAmount() + " - Sale count: " + sale.getSaleCount() + " - Void count: " + sale.getVoidCount());
//            
//        } catch (ApiException ex) {
//            fail(ex.getResponseBody());
//        } 
//    }
}
