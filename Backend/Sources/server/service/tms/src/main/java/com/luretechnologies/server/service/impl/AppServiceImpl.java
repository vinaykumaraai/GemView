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

import com.luretechnologies.server.data.dao.AppDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.service.AppService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinay
 */
@Service
@Transactional
public class AppServiceImpl implements AppService{
    
    @Autowired
    private AppDAO appDAO;
    
    @Autowired
    private EntityDAO entityDAO;
    
    /**
     *
     * @param app
     * @return
     * @throws Exception
     */
    
    @Override
    public App create(App app) throws Exception {
        //App newApp = new App();
        Entity entity = app.getEntity();

        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findByEntityId(app.getEntity().getEntityId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, app.getEntity().getEntityId());
            }
            app.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            app.setEntity(entityDAO.getFirstResult());
        }
        
        // Copy properties from -> to
        //BeanUtils.copyProperties(app, newApp);

        appDAO.persist(app);

        return app;
    }

    @Override
    public App update(long id, App app) throws Exception {
        App existentApp = appDAO.getAppByID(id);
        Entity entity = app.getEntity();
        
        // Check entity existence
        if (entity != null) {
            Entity existentEntity = entityDAO.findByEntityId(app.getEntity().getEntityId());

            if (existentEntity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, app.getEntity().getEntityId());
            }

            app.setEntity(existentEntity);
        } else {
            // If not Entity defined set Default (Enterprise)
            app.setEntity(entityDAO.getFirstResult());
        }
        
        // Copy properties from -> to
        BeanUtils.copyProperties(app, existentApp);
        
        return appDAO.merge(existentApp);
    }

    @Override
    public void delete(long id) throws Exception {
        App app = appDAO.getAppByID(id);
        //appDAO.delete(app);
        
        //Not delete the app, just change activate value to false.
        app.setActive((short)0);
        appDAO.merge(app);
    }
    
     /**
     *
     * @param name
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<App> list(String name, Boolean active, int pageNumber, int rowsPerPage) throws Exception {

        int firstResult = (pageNumber - 1) * rowsPerPage;

        if (name == null && active == null) {
            return appDAO.list(firstResult, rowsPerPage);
        } else {
            return appDAO.list(name, active, firstResult, rowsPerPage);
        }
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public App get(long id) throws Exception {
        App app = appDAO.getAppByID(id);
        
        return app;
    }
    
    /*private App updateApp(App app){
        app.setName("Updated");
        appDAO.merge(app);
        return app;
    }*/
    
    /**
     *
     * @param filter
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<App> search(String filter, Boolean active, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        if (filter == null) {
            return appDAO.list(firstResult, rowsPerPage);
        } else {
            return appDAO.search(filter, active, firstResult, rowsPerPage);
        }
    }
}



