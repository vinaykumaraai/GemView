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

import com.luretechnologies.common.enums.EncoderEnum;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.data.dao.ApplicationDAO;
import com.luretechnologies.server.data.dao.ApplicationPackageDAO;
import com.luretechnologies.server.data.dao.GroupDAO;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.File;
import com.luretechnologies.server.data.model.tms.Group;
import com.luretechnologies.server.data.model.tms.Parameter;
import com.luretechnologies.server.data.model.tms.Software;
import com.luretechnologies.server.data.model.tms.TerminalApplicationParameter;
import com.luretechnologies.server.service.ApplicationService;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author developer
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDAO applicationDAO;

    @Autowired
    private ApplicationPackageDAO applicationPackageDAO;

    @Autowired
    private GroupDAO groupDAO;

    /**
     *
     * @param application
     * @return
     * @throws Exception
     */
    @Override
    public Application create(Application application) throws Exception {
        Application newApplication = new Application();

        // Copy properties from -> to
        BeanUtils.copyProperties(application, newApplication);

        applicationDAO.persist(newApplication);

        return newApplication;
    }

    /**
     *
     * @param id
     * @param application
     * @return
     * @throws Exception
     */
    @Override
    public Application update(long id, Application application) throws Exception {
        Application existentApplication = applicationDAO.findById(id);

        // TODO if the app is deactivated send WARNING CODE if associated to a Terminal or Profile
        if (!application.getActive()) {

        }

        // Copy properties from -> to
        BeanUtils.copyProperties(application, existentApplication, new String[]{"software", "files", "parameters", "models", "applicationPackages"});

        // Update application version number
        updateVersion(application);

        return applicationDAO.merge(existentApplication);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        Application application = applicationDAO.findById(id);

        applicationDAO.delete(application.getId());
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Application get(String name) throws Exception {
        Application application = applicationDAO.findByName(name);

        if (application == null) {
            throw new ObjectRetrievalFailureException(Application.class, name);
        }

        return application;
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Application get(long id) throws Exception {
        Application application = applicationDAO.findById(id);

        return application;
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Application> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return applicationDAO.list(firstResult, rowsPerPage);
    }

    /**
     *
     * @param id
     * @param file
     * @param version
     * @param encoder
     * @return
     * @throws Exception
     */
    @Override
    public Application saveSoftware(long id, MultipartFile file, String version, EncoderEnum encoder) throws Exception {
        Application application = applicationDAO.findById(id);

        Software software = application.getSoftware();

        // Update application version number
        updateVersion(application);

        if (software == null) {
            software = new Software();

            application.setSoftware(software);
        }

        // Bytes of the encoded data
        byte[] encodedData = encodeFile(file, encoder);

        software.setSize(file.getSize());
        software.setFilename(file.getOriginalFilename());
        software.setFile(new SerialBlob(encodedData));
        software.setVersion(version);

        return applicationDAO.merge(application);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Software getSoftware(long id) throws Exception {
        Application application = applicationDAO.findById(id);

        return application.getSoftware();
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String getSoftwareFile(long id) throws Exception {
        Application application = applicationDAO.findById(id);

        Software software = application.getSoftware();

        if (software != null) {
            InputStream inputStream = software.getFile().getBinaryStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);

            return new String(bytes);
        }

        return null;
    }

    /**
     *
     * @param id
     * @param uploadedFile
     * @param encoder
     * @return
     * @throws Exception
     */
    @Override
    public Application addFile(long id, MultipartFile uploadedFile, EncoderEnum encoder) throws Exception {
        Application application = applicationDAO.findById(id);

        // Update application version number
        updateVersion(application);

        // Bytes of the encoded data
        byte[] encodedData = encodeFile(uploadedFile, encoder);

        // Check if exist a file with same name already registered for the application
        for (File file : application.getFiles()) {
            if (file.getFilename().equals(uploadedFile.getOriginalFilename())) {
                // Replace existent file
                file.setSize(uploadedFile.getSize());
                file.setFilename(uploadedFile.getOriginalFilename());
                file.setFile(new SerialBlob(encodedData));

                return applicationDAO.merge(application);
            }
        }

        File file = new File();
        file.setSize(uploadedFile.getSize());
        file.setFilename(uploadedFile.getOriginalFilename());
        file.setFile(new SerialBlob(encodedData));

        application.getFiles().add(file);

        return applicationDAO.merge(application);
    }

    /**
     *
     * @param id
     * @param idFile
     * @return
     * @throws Exception
     */
    @Override
    public Application deleteFile(long id, long idFile) throws Exception {
        Application application = applicationDAO.findById(id);

        // Update application version number
        updateVersion(application);

        for (File file : application.getFiles()) {
            if (file.getId() == idFile) {
                application.getFiles().remove(file);

                return applicationDAO.merge(application);
            }
        }

        throw new ObjectRetrievalFailureException(File.class, idFile);
    }

    /**
     *
     * @param id
     * @param parameters
     * @return
     * @throws Exception
     */
    @Override
    public Application saveParameters(long id, List<Parameter> parameters) throws Exception {
        Application application = applicationDAO.findById(id);

        // Update application version number
        updateVersion(application);

        for (Parameter parameter : parameters) {
            application = saveParameter(parameter, application);
        }

        //update parameter version
        for (Parameter parameter : application.getParameters()) {
            updateVersion(parameter);
        }

        return applicationDAO.merge(application);

    }

    /**
     *
     * @param id
     * @param idParameter
     * @return
     * @throws Exception
     */
    @Override
    public Application deleteParameter(long id, long idParameter) throws Exception {
        Application application = applicationDAO.findById(id);

        // Update application version number
        updateVersion(application);

        for (Parameter parameter : application.getParameters()) {
            if (parameter.getId() == idParameter) {
                application.getParameters().remove(parameter);

                return applicationDAO.merge(application);
            }
        }

        throw new ObjectRetrievalFailureException(Parameter.class, idParameter);
    }

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<Application> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return applicationDAO.search(filter, firstResult, rowsPerPage);
    }

    /**
     *
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception {
        return Utils.getTotalPages(applicationDAO.search(filter, -1, -1).size(), rowsPerPage);
    }

    private Application saveParameter(Parameter parameter, Application application) {
        // Validate and set the correct Parameter Group to the parameter
        // If not Group defined set Default
        if (parameter.getGroup() == null) {
            parameter.setGroup(groupDAO.getFirstResult());
        } else {
            String name = parameter.getGroup().getName();

            // Find Group by name
            Group group = groupDAO.findByName(name);

            if (group == null) {
                throw new ObjectRetrievalFailureException(Group.class, name);
            }

            parameter.setGroup(group);
        }

        // If the parameter already exists for the application, update parameter
        if (application.getParameters().contains(parameter)) {
            // Update the existent parameter
            for (Parameter existentParameter : application.getParameters()) {
                if (existentParameter.equals(parameter)) {
                    application.getParameters().remove(existentParameter);
                    BeanUtils.copyProperties(parameter, existentParameter, "id", "version");
                    application.getParameters().add(existentParameter);
                    break;
                }
            }
        } else {
            application.getParameters().add(parameter);
            application = applicationDAO.merge(application);
        }

        return application;
    }

    private void updateVersion(Application application) {
        int currentVersion = application.getVersion();

        if (currentVersion == 255) {
            application.setVersion(0);
        } else {
            application.setVersion(++currentVersion);
        }
    }

    private void updateVersion(TerminalApplicationParameter parameter) {
        int currentVersion = parameter.getVersion();

        if (currentVersion == 255) {
            parameter.setVersion(0);
        } else {
            parameter.setVersion(++currentVersion);
        }
    }

    private void updateVersion(Parameter parameter) {
        int currentVersion = parameter.getVersion();

        if (currentVersion == 255) {
            parameter.setVersion(0);
        } else {
            parameter.setVersion(++currentVersion);
        }
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
    public List<Application> list(String name, Boolean active, int pageNumber, int rowsPerPage) throws Exception {

        int firstResult = (pageNumber - 1) * rowsPerPage;

        if (name == null && active == null) {
            return applicationDAO.list(firstResult, rowsPerPage);
        } else {
            return applicationDAO.list(name, active, firstResult, rowsPerPage);
        }
    }

    private byte[] encodeFile(MultipartFile file, EncoderEnum encoder) throws IOException, SQLException {
        switch (encoder) {
            case BASE64: {
                // Convert file to base64 String
                return Utils.encodeToBase64String(file).getBytes();
            }
            default: {
                // Plain file
                return file.getBytes();
            }
        }
    }

}
