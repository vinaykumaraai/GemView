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

import com.luretechnologies.server.data.dao.ActionDAO;
import com.luretechnologies.server.data.model.tms.Action;
import com.luretechnologies.server.service.ActionService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinay
 */
@Service
@Transactional
public class ActionServiceImpl implements ActionService{
    
    @Autowired
    ActionDAO actionDAO;
    
    /**
     *
     * @param action
     * @return
     * @throws Exception
     */
    @Override
    public Action createAction(Action action) throws Exception{
        Action newAction = new Action();
        
         // Copy properties from -> to
        BeanUtils.copyProperties(action , newAction);
        actionDAO.persist(newAction);
        return newAction;
        
    }
    
    /**
     *
     * @param ID
     * @param action
     * @return
     * @throws Exception
     */
    @Override
    public Action updateAction(long ID, Action action) throws Exception{
        Action existentAction = actionDAO.getActionByID(ID);
        // Copy properties from -> to
        BeanUtils.copyProperties(action, existentAction);
        //Action updatedAction=  updateAction(action);
        return actionDAO.merge(existentAction);
        
    }
    
    /**
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public void deleteAction(long ID) throws Exception{
        Action deletedAction = actionDAO.getActionByID(ID);
        actionDAO.delete(deletedAction);
        
    }
    
    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<Action> getActionList(List<Long> ids) throws Exception{
        List<Action> appFileList = actionDAO.getActionList(ids);
        return appFileList;
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Action getActionByID(Long id) throws Exception{
        Action action = actionDAO.getActionByID(id);
        return action;
        
    }
    
    private Action updateAction(Action action)throws Exception{
        action.setName("Updated");
        actionDAO.merge(action);
        return action;
    }
}