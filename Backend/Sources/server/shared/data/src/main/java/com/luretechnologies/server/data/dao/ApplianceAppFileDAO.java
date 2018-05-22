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
package com.luretechnologies.server.data.dao;

import com.luretechnologies.server.data.model.tms.ApplianceAppFileValue;
import java.util.List;
import javax.persistence.PersistenceException;

/**
 *
 * @author Vinay
 */
public interface ApplianceAppFileDAO extends BaseDAO<ApplianceAppFileValue, Long>{
 
     /**
     *
     * @param ID
     * @return
     */
    public List<ApplianceAppFileValue> getApplianceAppFileList(List<Long> ID)throws PersistenceException;
    
    public ApplianceAppFileValue getApplianceAppFileByID(Long ID)throws PersistenceException;
    
    public String getValueByID(Long id)throws PersistenceException;
}