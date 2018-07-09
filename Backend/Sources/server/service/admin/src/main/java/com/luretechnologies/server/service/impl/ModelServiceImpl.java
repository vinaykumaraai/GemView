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

import com.luretechnologies.server.data.dao.ModelDAO;
import com.luretechnologies.server.data.display.ModelDisplay;
import com.luretechnologies.server.data.model.Model;
import com.luretechnologies.server.service.ModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 *
 * @author
 */
@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelDAO modelDAO;
    @Override
    public ModelDisplay create(ModelDisplay modelDisplay) throws Exception {
        Model model = new Model();
        model.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        model.setActive(true);
        if (modelDisplay.getAvailable() != null) {
            model.setAvailable(modelDisplay.getAvailable());
        } else {
            model.setAvailable(true);
        }
        model.setDescription(modelDisplay.getDescription());
        model.setManufacturer(modelDisplay.getManufacturer());
        model.setName(modelDisplay.getName());
        model.setOsUpdate(modelDisplay.getOsUpdate());
        model.setRkiCapable(modelDisplay.getRkiCapable());
        modelDAO.persist(model);
        modelDisplay.setId(model.getId());
        modelDisplay.setUpdatedAt(new Date (model.getUpdatedAt().getTime()));
        modelDisplay.setId(model.getId());
        return modelDisplay;
    }

    @Override
    public List<ModelDisplay> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (((pageNumber - 1) >= 0) ? (pageNumber - 1) : 0) * rowsPerPage;
        List<Model> models = modelDAO.search(filter, firstResult, firstResult + rowsPerPage);
        List<ModelDisplay> displays = new ArrayList<>();
        if (models != null && models.size() > 0) {
            for (Model model : models) {
                ModelDisplay display = new ModelDisplay();
                display.setAvailable(model.getAvailable());
                display.setUpdatedAt(new Date(model.getUpdatedAt().getTime()));
                display.setDescription(model.getDescription());
                display.setManufacturer(model.getManufacturer());
                display.setRkiCapable(model.getRkiCapable());
                display.setOsUpdate(model.getOsUpdate());
                display.setName(model.getName());
                display.setId(model.getId());
                displays.add(display);
            }
            return displays;
        } else {
            return null;
        }
    }

    @Override
    public List<ModelDisplay> list(int firstResult, int lastResult) throws Exception {
        List<Model> models = modelDAO.list(firstResult, lastResult);
        List<ModelDisplay> displays = new ArrayList<>();
        if (models != null && models.size() > 0) {
            for (Model model : models) {
                ModelDisplay display = new ModelDisplay();
                display.setAvailable(model.getAvailable());
                display.setUpdatedAt(new Date(model.getUpdatedAt().getTime()));
                display.setDescription(model.getDescription());
                display.setManufacturer(model.getManufacturer());
                display.setRkiCapable(model.getRkiCapable());
                display.setOsUpdate(model.getOsUpdate());
                displays.add(display);
            }
            return displays;
        } else {
            return null;
        }
    }

    @Override
    public ModelDisplay findByName(String name) throws Exception {
        Model model = modelDAO.findByName(name);
        if (model != null) {
            ModelDisplay display = new ModelDisplay();
            display.setAvailable(model.getAvailable());
            display.setUpdatedAt(new Date(model.getUpdatedAt().getTime()));
            display.setDescription(model.getDescription());
            display.setManufacturer(model.getManufacturer());
            display.setRkiCapable(model.getRkiCapable());
            display.setOsUpdate(model.getOsUpdate());
            return display;
        } else {
            return null;
        }

    }

    @Override
    public ModelDisplay update(ModelDisplay modelDisplay) throws Exception {
     Model model = modelDAO.findById(modelDisplay.getId());
        model.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (modelDisplay.getAvailable() != null) {
            model.setAvailable(modelDisplay.getAvailable());
        } 
        model.setDescription(modelDisplay.getDescription());
        model.setManufacturer(modelDisplay.getManufacturer());
        model.setName(modelDisplay.getName());
        model.setOsUpdate(modelDisplay.getOsUpdate());
        model.setRkiCapable(modelDisplay.getRkiCapable());
        modelDAO.merge(model);
        modelDisplay.setId(model.getId());
        return modelDisplay;
    }

    @Override
    public void delete(Long id) throws Exception {
        Model model = modelDAO.findById(id);
        model.setActive(false);
        modelDAO.merge(model);
    }

    @Override
    public ModelDisplay get(Long id) throws Exception {
        Model model = modelDAO.findById(id);
        if (model != null) {
            ModelDisplay display = new ModelDisplay();
            display.setAvailable(model.getAvailable());
            display.setUpdatedAt(new Date(model.getUpdatedAt().getTime()));
            display.setDescription(model.getDescription());
            display.setManufacturer(model.getManufacturer());
            display.setRkiCapable(model.getRkiCapable());
            display.setOsUpdate(model.getOsUpdate());
            display.setName(model.getName());
            display.setId(model.getId());
            
            return display;
        } else {
            return null;
        }
    }
}
