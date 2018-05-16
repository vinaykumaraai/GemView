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

import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.AddressDAO;
import com.luretechnologies.server.data.dao.ClientDAO;
import com.luretechnologies.server.data.dao.TelephoneDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.payment.Address;
import com.luretechnologies.server.data.model.payment.Client;
import com.luretechnologies.server.data.model.payment.Telephone;
import com.luretechnologies.server.service.ClientService;
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
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDAO clientDAO;
    
    @Autowired
    private AddressDAO addressDAO;
    
    @Autowired
    private TelephoneDAO telephoneDAO;

    /**
     *
     * @param client
     * @return
     * @throws Exception
     */
    @Override
    public Client create(Client client) throws Exception {
        Client newClient = new Client();

        // Copy properties from -> to
        BeanUtils.copyProperties(client, newClient);

        return clientDAO.merge(newClient);
    }

    /**
     *
     * @param id
     * @param client
     * @return
     * @throws Exception
     */
    @Override
    public Client update(long id, Client client) throws Exception {
        Client existentClient = clientDAO.findById(id);

        // Copy properties from -> to
        BeanUtils.copyProperties(client, existentClient);

        Client updated = clientDAO.merge(existentClient);
        
        for (Address address : addressDAO.getAddressNoRelations(updated.getAddresses())) {
            addressDAO.delete(address);
        }
        
        for (Telephone telephone : telephoneDAO.getTelephonesNoRelations(updated.getTelephones())) {
            telephoneDAO.delete(telephone);
        }
        
        return updated;
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        Client client = clientDAO.findById(id);

        clientDAO.delete(client.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Client get(long id) throws Exception {
        return clientDAO.findById(id);
    }

    /**
     *
     * @param entity
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Client> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return clientDAO.list(entity, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Client> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return clientDAO.search(entity, filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param entity
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(Entity entity, String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(clientDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

}
