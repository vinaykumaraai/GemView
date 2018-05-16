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
package com.luretechnologies.server.front.tms.controller;

import com.luretechnologies.server.data.model.tms.CAKey;
import com.luretechnologies.server.data.model.tms.PaymentSystem;
import com.luretechnologies.server.service.PaymentSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Payment System Controller Class
 *
 *
 */
@RestController
@RequestMapping("/paymentSystems")
@Api(value = "Payment Systems")
public class PaymentSystemController {

    @Autowired
    PaymentSystemService service;

//    /**
//     * Saves a payment system information
//     *
//     * @param paymentSystem
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public PaymentSystem save(@RequestBody PaymentSystem paymentSystem) throws Exception {
//
//        return service.save(paymentSystem);
//    }
//
//    /**
//     * Deletes a payment system information
//     *
//     * @param rid
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{rid}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable("rid") String rid) throws Exception {
//
//        service.delete(rid);
//    }
    /**
     * Retrieve a payment system information
     *
     * @param rid
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{rid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Systems", httpMethod = "GET", value = "Get payment system", notes = "Get payment system by rid")
    public PaymentSystem get(@ApiParam(value = "Payment system rid", required = true) @PathVariable("rid") String rid) throws Exception {

        return service.get(rid);
    }

    /**
     * List all payment systems information.
     *
     * @param name
     * @param rid
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Systems", httpMethod = "GET", value = "List payment systems", notes = "Lists payment systems. Will return 50 records if no paging parameters defined", response = PaymentSystem.class, responseContainer = "List")
    public List<PaymentSystem> list(@ApiParam(value = "Payment system name") @RequestParam(required = false) String name,
            @ApiParam(value = "Payment system identifier") @RequestParam(required = false) String rid,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {
        return service.list(name, rid, pageNumber, rowsPerPage);
    }

//    /**
//     * Saves a payment system product
//     *
//     * @param rid
//     * @param product
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{rid}/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public PaymentSystem saveProduct(@PathVariable String rid, @RequestBody PaymentProduct product) throws Exception {
//
//        return service.saveProduct(rid, product);
//    }
//
//    /**
//     * Deletes a payment system product
//     *
//     * @param rid
//     * @param aid
//     * @return
//     * @throws java.lang.Exception
//     */
//    @RequestMapping(value = "/{rid}/products/{aid}", method = RequestMethod.DELETE)
//    public PaymentSystem deleteProduct(@PathVariable String rid, @PathVariable String aid) throws Exception {
//
//        return service.deleteProduct(rid, aid);
//    }
    /**
     * Saves a certification authority key (caKey)
     *
     * @param rid
     * @param caKey
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{rid}/cakeys", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Systems", httpMethod = "POST", value = "Save certification authority key", notes = "Saves a payment system's certification authority key")
    public PaymentSystem saveCAKey(@ApiParam(value = "Payment system rid", required = true) @PathVariable String rid,
            @ApiParam(value = "Certification Authority Key object", required = true) @RequestBody CAKey caKey) throws Exception {

        return service.saveCAKey(rid, caKey);
    }

    /**
     * Search Payment Systems by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Systems", httpMethod = "POST", value = "Search payment systems", notes = "Search payment systems that match a given filter. Will return 50 records if no paging parameters defined", response = PaymentSystem.class, responseContainer = "List")
    public List<PaymentSystem> search(@ApiParam(value = "filter", required = true) @RequestParam("filter") String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }
}
