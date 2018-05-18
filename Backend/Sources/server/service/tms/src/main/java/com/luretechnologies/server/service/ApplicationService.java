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
package com.luretechnologies.server.service;

import com.luretechnologies.common.enums.EncoderEnum;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.Parameter;
import com.luretechnologies.server.data.model.tms.Software;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author developer
 */
public interface ApplicationService {

    /**
     *
     * @param application
     * @return
     * @throws Exception
     */
    public Application create(Application application) throws Exception;

    /**
     *
     * @param id
     * @param application
     * @return
     * @throws Exception
     */
    public Application update(long id, Application application) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Application get(String name) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Application get(long id) throws Exception;

    /**
     *
     * @param id
     * @param uploadedFile
     * @param version
     * @param encoder
     * @return
     * @throws Exception
     */
    public Application saveSoftware(long id, MultipartFile uploadedFile, String version, EncoderEnum encoder) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Software getSoftware(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public String getSoftwareFile(long id) throws Exception;

    /**
     *
     * @param id
     * @param uploadedFile
     * @param encoder
     * @return
     * @throws Exception
     */
    public Application addFile(long id, MultipartFile uploadedFile, EncoderEnum encoder) throws Exception;

    /**
     *
     * @param id
     * @param idFile
     * @return
     * @throws Exception
     */
    public Application deleteFile(long id, long idFile) throws Exception;

    /**
     *
     * @param id
     * @param idParameter
     * @return
     * @throws Exception
     */
    public Application deleteParameter(long id, long idParameter) throws Exception;

    /**
     *
     * @param id
     * @param parameter
     * @return
     * @throws Exception
     */
    public Application saveParameters(long id, List<Parameter> parameter) throws Exception;

    /**
     *
     * @param id
     * @throws Exception
     */
    public void delete(long id) throws Exception;

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Application> list(int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param filter
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Application> search(String filter, int pageNumber, int rowsPerPage) throws Exception;

    /**
     *
     * @param filter
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public int getSearchTotalPages(String filter, int rowsPerPage) throws Exception;

    /**
     *
     * @param name
     * @param active
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    public List<Application> list(String name, Boolean active, int pageNumber, int rowsPerPage) throws Exception;
}
