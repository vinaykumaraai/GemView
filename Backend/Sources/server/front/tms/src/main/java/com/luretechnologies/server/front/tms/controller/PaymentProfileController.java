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

import com.luretechnologies.server.data.model.tms.PaymentProduct;
import com.luretechnologies.server.data.model.tms.PaymentProfile;
import com.luretechnologies.server.data.model.tms.PaymentSystem;
import com.luretechnologies.server.service.PaymentProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Payment Profile Controller Class
 *
 *
 */
@RestController
@RequestMapping("/paymentProfiles")
@Api(value = "Payment Profiles")
public class PaymentProfileController {

    @Autowired
    PaymentProfileService service;

    /**
     * Creates a new payment profile
     *
     * @param paymentProfile
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "POST", value = "Create payment profile", notes = "Creates a new payment profile")
    public PaymentProfile create(@ApiParam(value = "Payment profile object", required = true) @RequestBody PaymentProfile paymentProfile) throws Exception {

        return service.create(paymentProfile);
    }

    /**
     * Updates a payment profile information
     *
     * @param paymentProfile
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "PUT", value = "Update payment profile", notes = "Updates a payment profile")
    public PaymentProfile update(@ApiParam(value = "Payment profile id", required = true) @PathVariable("id") long id,
            @ApiParam(value = "Payment profile object", required = true) @RequestBody PaymentProfile paymentProfile) throws Exception {

        return service.update(id, paymentProfile);
    }

    /**
     * Deletes a payment profile information
     *
     * @param id
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "DELETE", value = "Delete payment profile", notes = "Deletes a payment profile")
    public void delete(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id) throws Exception {

        service.delete(id);
    }

    /**
     * Retrieve a payment profile information
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "GET", value = "Get payment profile", notes = "Get payment profile by id")
    public PaymentProfile get(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id) throws Exception {

        return service.get(id);
    }

    /**
     * List all payment profiles information.
     *
     * @param name
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "GET", value = "List payment profiles", notes = "Lists payment profiles. Will return 50 records if no paging parameters defined", response = PaymentProfile.class, responseContainer = "List")
    public List<PaymentProfile> list(@ApiParam(value = "Payment profile name") @RequestParam(required = false) String name,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {
        return service.list(name, pageNumber, rowsPerPage);
    }

    /**
     * Adds a Payment System to a payment profile
     *
     * @param id
     * @param paymentSystem
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/paymentSystems", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "POST", value = "Add payment system", notes = "Adds a payment system to a payment profile")
    public PaymentProfile addPaymentSystem(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id,
            @ApiParam(value = "Payment system object", required = true) @RequestBody PaymentSystem paymentSystem) throws Exception {

        return service.addPaymentSystem(id, paymentSystem);
    }

    /**
     * Remove a Payment System from a payment profile
     *
     * @param id
     * @param rid
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/paymentSystems/{rid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "DELETE", value = "Remove payment system", notes = "Removes a payment system from a payment profile")
    public PaymentProfile removePaymentSystem(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id,
            @ApiParam(value = "Payment system rid", required = true) @PathVariable String rid) throws Exception {

        return service.removePaymentSystem(id, rid);
    }

    /**
     * Adds a Product to a payment system profile
     *
     * @param id
     * @param rid
     * @param paymentProduct
     * @return
     * @throws java.lang.Exception
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/paymentSystems/{rid}/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "POST", value = "Add product to a payment system", notes = "Adds a product to a payment system in a payment profile")
    public PaymentProfile addPaymentProduct(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id,
            @ApiParam(value = "Payment system rid", required = true) @PathVariable String rid,
            @ApiParam(value = "Product object", required = true) @RequestBody PaymentProduct paymentProduct) throws Exception {

        return service.addPaymentProduct(id, rid, paymentProduct);
    }

    /**
     * Remove a Payment Product from a payment system profile
     *
     * @param id
     * @param rid
     * @param aid
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/{id}/paymentSystems/{rid}/products/{aid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "DELETE", value = "Remove product from a payment system", notes = "Removes a product from a payment system in a payment profile")
    public PaymentProfile removePaymentProduct(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id,
            @ApiParam(value = "Payment system rid", required = true) @PathVariable String rid,
            @ApiParam(value = "Product aid", required = true) @PathVariable String aid) throws Exception {

        return service.removePaymentProduct(id, rid, aid);
    }

    /**
     * Load Payment System Profile default data
     *
     * @param id
     * @param rid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/paymentSystems/{rid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "PUT", value = "Load payment system default data", notes = "Load the default data of a payment system")
    public PaymentProfile loadDefaultData(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id,
            @ApiParam(value = "Payment system rid", required = true) @PathVariable String rid) throws Exception {

        return service.loadDefaultData(id, rid);
    }

    /**
     * Load Payment Product Profile default data
     *
     * @param id
     * @param rid
     * @param aid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/paymentSystems/{rid}/products/{aid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "PUT", value = "Load product default data", notes = "Load the default data of a product")
    public PaymentProfile loadDefaultData(@ApiParam(value = "Payment profile id", required = true) @PathVariable long id,
            @ApiParam(value = "Payment system rid", required = true) @PathVariable String rid,
            @ApiParam(value = "Product aid", required = true) @PathVariable String aid) throws Exception {

        return service.loadDefaultData(id, rid, aid);
    }

    /**
     * Search terminals by a given filter
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(name = "Search", value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Payment Profiles", httpMethod = "POST", value = "Search payment profiles", notes = "Search payment profiles that match a given filter. Will return 50 records if no paging parameters defined", response = PaymentProfile.class, responseContainer = "List")
    public List<PaymentProfile> search(@ApiParam(value = "filter", required = true) @RequestParam("filter") String filter,
            @ApiParam(value = "Page number", required = false, defaultValue = "1") @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @ApiParam(value = "Rows per page", required = false, defaultValue = "50") @RequestParam(name = "rowsPerPage", defaultValue = "50") Integer rowsPerPage) throws Exception {

        return service.search(filter, pageNumber, rowsPerPage);
    }
}
