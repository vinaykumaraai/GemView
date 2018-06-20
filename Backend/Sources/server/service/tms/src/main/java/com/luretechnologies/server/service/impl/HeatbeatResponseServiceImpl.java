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

import com.luretechnologies.server.data.dao.DownloadInfoDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.dao.HeartbeatResponseDAO;
import com.luretechnologies.server.data.dao.RKIInfoDAO;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.DownloadInfoDisplay;
import com.luretechnologies.server.data.display.tms.HeartbeatResponseDisplay;
import com.luretechnologies.server.data.display.tms.RKIInfoDisplay;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.service.HeartbeatResponseService;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.PersistenceException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author
 */
@Service
@Transactional
public class HeatbeatResponseServiceImpl implements HeartbeatResponseService {

    @Autowired
    EntityDAO entityDAO;

    @Autowired
    HeartbeatResponseDAO heartbeatResponseDAO;

    @Autowired
    DownloadInfoDAO downloadInfoDAO;

    @Autowired
    RKIInfoDAO rkiInfoDAO;

    @Autowired
    TerminalDAO terminalDAO;

    @Override
    public HeartbeatResponseDisplay create(String entityid, HeartbeatResponseDisplay heartbeatResponse) throws Exception {
        try {

            DownloadInfoDisplay downloadInfo = heartbeatResponse.getDownloadInfo();
            RKIInfoDisplay RKIInfo = heartbeatResponse.getRkiInfo();

            heartbeatResponse.setDownloadInfo(null);
            heartbeatResponse.setRkiInfo(null);

            com.luretechnologies.server.data.model.tms.HeartbeatResponse heartbeatResponseModel = new com.luretechnologies.server.data.model.tms.HeartbeatResponse();
            com.luretechnologies.server.data.model.tms.DownloadInfo downloadInfoModel = null;
            com.luretechnologies.server.data.model.tms.RKIInfo RKIInfoModel = null;

            Entity entity = terminalDAO.findBySerialNumber(entityid);
            if (entity == null) {
                throw new ObjectRetrievalFailureException(Entity.class, entityid);
            }
            heartbeatResponseModel.setEntity(entity);
            heartbeatResponseModel.setDebug(heartbeatResponse.getDebug());
            heartbeatResponseModel.setInterval(heartbeatResponse.getInterval());
            heartbeatResponseModel.setMessage(heartbeatResponse.getMessage());
            heartbeatResponseModel.setStatus(heartbeatResponse.getStatus());
            heartbeatResponseModel.setOccurred(new Timestamp(System.currentTimeMillis()));
            heartbeatResponseDAO.persist(heartbeatResponseModel);

            if (downloadInfo != null) {
                downloadInfoModel = new com.luretechnologies.server.data.model.tms.DownloadInfo();
                downloadInfoModel.setHeartbeatResponse(heartbeatResponseModel);
                downloadInfoModel.setOccurred(new Timestamp(System.currentTimeMillis()));
                downloadInfoModel.setFilename(downloadInfo.getFilename());
                downloadInfoModel.setFtpsUrl(downloadInfo.getFtpsUrl());
                downloadInfoModel.setHttpsUrl(downloadInfo.getHttpsUrl());
                downloadInfoModel.setPassword(downloadInfo.getPassword());
                downloadInfoModel.setUsername(downloadInfo.getUsername());
                downloadInfoDAO.persist(downloadInfoModel);
                downloadInfo.setId(downloadInfoModel.getId());
                heartbeatResponse.setDownloadInfo(downloadInfo);
            }

            if (RKIInfo != null) {
                RKIInfoModel = new com.luretechnologies.server.data.model.tms.RKIInfo();
                RKIInfoModel.setHeartbeatResponse(heartbeatResponseModel);
                RKIInfoModel.setKeyType(RKIInfo.getKeyType());
                RKIInfoModel.setUrl(RKIInfo.getUrl());
                rkiInfoDAO.persist(RKIInfoModel);
                RKIInfo.setId(RKIInfoModel.getId());
                heartbeatResponse.setRkiInfo(RKIInfo);
            }

            heartbeatResponse.setSequence(heartbeatResponseModel.getId().toString());
            heartbeatResponse.setId(heartbeatResponseModel.getId());
            return heartbeatResponse;
        } catch (Exception ex) {
            throw new PersistenceException("Creating heartbeat response " + ex.getMessage());
        }
    }

    @Override
    public void delete(Long Id) throws Exception {
        try {
            heartbeatResponseDAO.delete(Id);
        } catch (Exception ex) {

        }
    }

    @Override
    public HeartbeatResponseDisplay getLastOne(Long entityId) throws Exception {
        HeartbeatResponseDisplay heartbeatResponse = new HeartbeatResponseDisplay();

        com.luretechnologies.server.data.model.tms.HeartbeatResponse heartbeatResponseModel = heartbeatResponseDAO.getLastOne(entityId);

        if (heartbeatResponseModel != null) {
            heartbeatResponse.setId(heartbeatResponseModel.getId());
            heartbeatResponse.setDebug(heartbeatResponseModel.getDebug());
            heartbeatResponse.setEntity(heartbeatResponseModel.getEntity().getEntityId());
            heartbeatResponse.setInterval(heartbeatResponseModel.getInterval());
            heartbeatResponse.setMessage(heartbeatResponseModel.getMessage());
            heartbeatResponse.setOccurred(new Date(heartbeatResponseModel.getOccurred().getTime()));
            heartbeatResponse.setStatus(heartbeatResponseModel.getStatus());

            if (heartbeatResponseModel.getDownloadInfo() != null) {
                DownloadInfoDisplay downloadInfo = new DownloadInfoDisplay();
                downloadInfo.setFilename(heartbeatResponseModel.getDownloadInfo().getFilename());
                downloadInfo.setPassword(heartbeatResponseModel.getDownloadInfo().getPassword());
                downloadInfo.setId(heartbeatResponseModel.getDownloadInfo().getId());
                downloadInfo.setFtpsUrl(heartbeatResponseModel.getDownloadInfo().getFtpsUrl());
                downloadInfo.setHttpsUrl(heartbeatResponseModel.getDownloadInfo().getHttpsUrl());
                downloadInfo.setOccurred(new Date(heartbeatResponseModel.getDownloadInfo().getOccurred().getTime()));
                heartbeatResponse.setDownloadInfo(downloadInfo);
            }

            if (heartbeatResponseModel.getRkiInfo() != null) {
                RKIInfoDisplay rkiInfo = new RKIInfoDisplay();
                rkiInfo.setId(heartbeatResponseModel.getRkiInfo().getId());
                rkiInfo.setKeyType(heartbeatResponseModel.getRkiInfo().getKeyType());
                rkiInfo.setUrl(heartbeatResponseModel.getRkiInfo().getUrl());
                heartbeatResponse.setRkiInfo(rkiInfo);
            }
            heartbeatResponse.setSequence(heartbeatResponseModel.getId().toString());
        } else {
            return null;
        }
        return heartbeatResponse;
    }

    @Override
    public HeartbeatResponseDisplay getLastOne(String serialNumber) throws Exception {
        Entity entity = terminalDAO.findBySerialNumber(serialNumber);
        return getLastOne(entity.getId());
    }

}
