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
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.server.data.model.tms.App;
import com.luretechnologies.server.data.model.tms.AppProfile;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import com.luretechnologies.server.data.dao.AppProfileDAO;

/**
 *
 * @author Vinay
 */
@Repository
public class AppProfileDAOImpl extends BaseDAOImpl<AppProfile, Long> implements AppProfileDAO{
    
    @Override
    public List<AppProfile> getAppProfileList(List<Long> IDS)throws PersistenceException{
        List<AppProfile> appProfileList = new ArrayList<>();
        for(Long ID : IDS) {
            appProfileList.add(getEntityManager().find(AppProfile.class, ID));
        }
        return appProfileList;
    }

    @Override
    public AppProfile getAppProfileByID(Long ID) throws PersistenceException{
       App app = new App();
       return  getEntityManager().find(AppProfile.class, ID);
        
    }
}