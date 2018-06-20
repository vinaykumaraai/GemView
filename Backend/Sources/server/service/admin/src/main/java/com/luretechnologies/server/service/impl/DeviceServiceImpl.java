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

import com.luretechnologies.common.enums.EntityTypeEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.DeviceDAO;
import com.luretechnologies.server.data.dao.EntityDAO;
import com.luretechnologies.server.data.model.Device;
import com.luretechnologies.server.data.model.Entity;
import com.luretechnologies.server.service.DeviceService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private EntityDAO entityDAO;

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Device create(Device entity) throws Exception {
        Entity parent = entityDAO.findById(entity.getParentId());

        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parent.getEntityId());
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.DEVICE, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        entity.setParent(parent);
        entity.setActive(true);
        entity.setAvailable(entity.isAvailable());

        entity.setType(EntityTypeEnum.DEVICE);
        entityDAO.updatePath(entity, true);

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse("19800101");
        entity.setLastContact(date);
        entity.setLastDownload(date);

        deviceDAO.persist(entity);

        return entity;
    }

    /**
     *
     * @param entityId
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public Device update(String entityId, Device entity) throws Exception {
        Device existentEntity = deviceDAO.findByDeviceId(entityId);

        if (existentEntity == null) {
            throw new ObjectRetrievalFailureException(Device.class, entityId);
        }

        Entity parent = entityDAO.findById(existentEntity.getParentId());

        if (parent == null) {
            throw new PersistenceException("No parent id.");
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.DEVICE, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        BeanUtils.copyProperties(entity, existentEntity, new String[]{"entityId", "lastContact", "lastContact","active","lastDownload"});
        existentEntity.setParent(parent);
        existentEntity.setType(EntityTypeEnum.DEVICE);
        entityDAO.updatePath(existentEntity, false);
        entityDAO.updateActive(existentEntity);
        return deviceDAO.merge(existentEntity);
    }

    /**
     *
     * @param deviceId
     * @throws Exception
     */
    @Override
    public void delete(String deviceId) throws Exception {
        Device device = deviceDAO.findByDeviceId(deviceId);

        if (device == null) {
            throw new ObjectRetrievalFailureException(Device.class, deviceId);
        }
        device.setActive(false);
        entityDAO.updateActive( device );
        deviceDAO.merge(device);
    }

    /**
     *
     * @param deviceId
     * @return
     * @throws Exception
     */
    @Override
    public Device get(String deviceId) throws Exception {
        //Get device basic data and parent info
        return deviceDAO.findByDeviceId(deviceId);
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
    public List<Device> list(Entity entity, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return deviceDAO.list(entity, firstResult, rowsPerPage);
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
    public List<Device> search(Entity entity, String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return deviceDAO.search(entity, filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(deviceDAO.search(entity, filter, -1, -1).size(), rowsPerPage);
    }

    @Override
    public Entity copy(String entityId, long parentId) {
        Device original = deviceDAO.findByDeviceId(entityId);
        if (original == null) {
            throw new ObjectRetrievalFailureException(Entity.class, entityId);
        }

        Entity parent = entityDAO.findById(parentId);
        if (parent == null) {
            throw new ObjectRetrievalFailureException(Entity.class, parentId);
        }

        if (!entityDAO.isParentCorrect(EntityTypeEnum.DEVICE, parent)) {
            throw new PersistenceException("Incorrect parent type.");
        }

        Device copy = new Device();

        copy.setParent(parent);
        copy.setType(EntityTypeEnum.DEVICE);
        BeanUtils.copyProperties(original, copy, new String[]{"entityId, transactions"});
        entityDAO.updatePath(copy, true);
        deviceDAO.persist(copy);

        return copy;
    }
}
