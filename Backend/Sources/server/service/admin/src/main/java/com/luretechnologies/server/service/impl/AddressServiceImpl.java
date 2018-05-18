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

import com.luretechnologies.server.data.dao.AddressDAO;
import com.luretechnologies.server.data.dao.CountryDAO;
import com.luretechnologies.server.data.model.payment.Address;
import com.luretechnologies.server.data.model.payment.Country;
import com.luretechnologies.server.data.model.payment.State;
import com.luretechnologies.server.service.AddressService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Override
    public List<Country> getCountries() throws Exception {
        return countryDAO.list();
    }

    @Override
    public List<State> getStatesByCountry(int countryId) throws Exception {
        Country country = countryDAO.get(countryId);
        return new ArrayList<>(country.getStates());
    }

    @Override
    public Address createAddress(Address address) {
        addressDAO.persist(address);
        return address;
    }

    @Override
    public Address updateAddress(long id, Address address) {
        Address existent = addressDAO.findById(id);
        BeanUtils.copyProperties(address, existent);
        return addressDAO.merge(existent);
    }
    
    @Override
    public List<Address> cleanAddresses() {
        List<Address> addresses = addressDAO.getAddressNoRelations();
        for (Address address : addresses) {
            addressDAO.delete(address);
        }
        return addresses;
    }
}
