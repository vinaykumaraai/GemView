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
import com.luretechnologies.server.data.dao.PaymentProductDAO;
import com.luretechnologies.server.data.dao.PaymentProfileDAO;
import com.luretechnologies.server.data.dao.PaymentSystemDAO;
import com.luretechnologies.server.data.model.tms.PaymentProduct;
import com.luretechnologies.server.data.model.tms.PaymentProductProfile;
import com.luretechnologies.server.data.model.tms.PaymentProfile;
import com.luretechnologies.server.data.model.tms.PaymentSystem;
import com.luretechnologies.server.data.model.tms.PaymentSystemProfile;
import com.luretechnologies.server.service.PaymentProfileService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * @author developer
 */
@Service
@Transactional
public class PaymentProfileServiceImpl implements PaymentProfileService {

    @Autowired
    private PaymentProfileDAO paymentProfileDAO;

    @Autowired
    private PaymentSystemDAO paymentSystemDAO;

    @Autowired
    private PaymentProductDAO paymentProductDAO;

    /**
     *
     * @param paymentProfile
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile create(PaymentProfile paymentProfile) throws Exception {
        PaymentProfile newPaymentProfile = new PaymentProfile();

        // Copy properties from -> to
        BeanUtils.copyProperties(paymentProfile, newPaymentProfile);

        paymentProfileDAO.persist(newPaymentProfile);

        return newPaymentProfile;
    }

    /**
     *
     * @param id
     * @param paymentProfile
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile update(long id, PaymentProfile paymentProfile) throws Exception {
        PaymentProfile existentPaymentProfile = paymentProfileDAO.findById(id);

        // Copy properties from -> to
        BeanUtils.copyProperties(paymentProfile, existentPaymentProfile, new String[]{"applicationPackages", "paymentSystemProfiles"});

        return paymentProfileDAO.merge(existentPaymentProfile);
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(long id) throws Exception {
        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        paymentProfileDAO.delete(paymentProfile.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile get(long id) throws Exception {
        return paymentProfileDAO.findById(id);
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<PaymentProfile> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return paymentProfileDAO.list(firstResult, rowsPerPage);
    }

    /**
     *
     * @param id
     * @param paymentSystem
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile addPaymentSystem(long id, PaymentSystem paymentSystem) throws Exception {
        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        PaymentSystem existentPaymentSystem = paymentSystemDAO.findByRID(paymentSystem.getRid());

        if (existentPaymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, paymentSystem.getRid());
        }

        // Check if exist a payment system with same rid already registered for the payment profile
        for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
            PaymentSystem profilePaymentSystem = paymentSystemProfile.getPaymentSystem();

            if ((profilePaymentSystem != null) && (profilePaymentSystem.getRid().equals(paymentSystem.getRid()))) {

                // Copy properties from -> to
                BeanUtils.copyProperties(paymentSystem, paymentSystemProfile);

                // Update Payment System Profile version number
                updateVersion(paymentSystemProfile);

                return paymentProfileDAO.merge(paymentProfile);
            }
        }

        PaymentSystemProfile paymentSystemProfile = new PaymentSystemProfile();
        paymentSystemProfile.setPaymentSystem(existentPaymentSystem);
        paymentSystemProfile.setPaymentProfile(paymentProfile);

        // Copy properties from -> to
        BeanUtils.copyProperties(paymentSystem, paymentSystemProfile);

        paymentProfile.getPaymentSystemProfiles().add(paymentSystemProfile);

        return paymentProfileDAO.merge(paymentProfile);
    }

    /**
     *
     * @param id
     * @param rid
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile removePaymentSystem(long id, String rid) throws Exception {
        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        // Look for the payment system with the provided rid
        for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
            PaymentSystem profilePaymentSystem = paymentSystemProfile.getPaymentSystem();

            if ((profilePaymentSystem != null) && (profilePaymentSystem.getRid().equals(rid))) {
                paymentProfile.getPaymentSystemProfiles().remove(paymentSystemProfile);

                return paymentProfileDAO.merge(paymentProfile);
            }
        }

        throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
    }

    /**
     *
     * @param id
     * @param rid
     * @param paymentProduct
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile addPaymentProduct(long id, String rid, PaymentProduct paymentProduct) throws Exception {
        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        PaymentSystem paymentSystem = paymentSystemDAO.findByRID(rid);

        PaymentProduct existentPaymentProduct = paymentProductDAO.findByAID(paymentProduct.getAid());

        if (paymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
        }

        if (existentPaymentProduct == null) {
            throw new ObjectRetrievalFailureException(PaymentProduct.class, paymentProduct.getAid());
        }

        // Check if exist a payment system with same aid already registered for the payment profile
        for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
            PaymentSystem profilePaymentSystem = paymentSystemProfile.getPaymentSystem();

            if ((profilePaymentSystem != null) && (profilePaymentSystem.getRid().equals(paymentSystem.getRid()))) {

                // Check if exist a product with same aid already registered for a payment system in the payment profile
                for (PaymentProductProfile paymentProductProfile : paymentSystemProfile.getPaymentProductProfiles()) {
                    PaymentProduct paymentProductSystem = paymentProductProfile.getPaymentProduct();

                    if ((paymentProductSystem != null) && (paymentProductSystem.getAid().equals(paymentProduct.getAid()))) {

                        // Copy properties from -> to
                        BeanUtils.copyProperties(paymentProduct, paymentProductProfile);

                        // Update Payment Product Profile version number
                        updateVersion(paymentProductProfile);

                        return paymentProfileDAO.merge(paymentProfile);
                    }
                }

                PaymentProductProfile paymentProductProfile = new PaymentProductProfile();
                paymentProductProfile.setPaymentProduct(existentPaymentProduct);
                paymentProductProfile.setPaymentSystemProfile(paymentSystemProfile);

                // Copy properties from -> to
                BeanUtils.copyProperties(paymentProduct, paymentProductProfile);

                paymentSystemProfile.getPaymentProductProfiles().add(paymentProductProfile);

                return paymentProfileDAO.merge(paymentProfile);
            }
        }

        throw new ObjectRetrievalFailureException(PaymentSystem.class, paymentSystem.getRid());
    }

    /**
     *
     * @param id
     * @param rid
     * @param aid
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile removePaymentProduct(long id, String rid, String aid) throws Exception {
        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        // Look for the payment system with the provided rid
        for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
            PaymentSystem profilePaymentSystem = paymentSystemProfile.getPaymentSystem();

            if ((profilePaymentSystem != null) && (profilePaymentSystem.getRid().equals(rid))) {

                // Check if exist a product with same aid already registered for a payment system in the payment profile
                for (PaymentProductProfile paymentProductProfile : paymentSystemProfile.getPaymentProductProfiles()) {
                    PaymentProduct paymentProductSystem = paymentProductProfile.getPaymentProduct();

                    if ((paymentProductSystem != null) && (paymentProductSystem.getAid().equals(aid))) {
                        paymentSystemProfile.getPaymentProductProfiles().remove(paymentProductProfile);

                        return paymentProfileDAO.merge(paymentProfile);
                    }
                }

                throw new ObjectRetrievalFailureException(PaymentProduct.class, aid);
            }
        }

        throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
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
    public List<PaymentProfile> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return paymentProfileDAO.search(filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(paymentProfileDAO.search(filter, -1, -1).size(), rowsPerPage);
    }

    private void updateVersion(PaymentProductProfile paymentProductProfile) {
        int currentVersion = paymentProductProfile.getVersion();

        if (currentVersion == 255) {
            paymentProductProfile.setVersion(0);
        } else {
            paymentProductProfile.setVersion(++currentVersion);
        }
    }

    /**
     *
     * @param id
     * @param rid
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile loadDefaultData(long id, String rid) throws Exception {

        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        // Look for the payment system with the provided rid
        for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
            PaymentSystem paymentSystem = paymentSystemProfile.getPaymentSystem();

            if ((paymentSystem != null) && (paymentSystem.getRid().equals(rid))) {
                // Copy properties from -> to
                BeanUtils.copyProperties(paymentSystem, paymentSystemProfile, "CAKeys");

                // Update Payment System Profile version number
                updateVersion(paymentSystemProfile);

                return paymentProfileDAO.merge(paymentProfile);
            }
        }

        throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
    }

    /**
     *
     * @param id
     * @param rid
     * @param aid
     * @return
     * @throws Exception
     */
    @Override
    public PaymentProfile loadDefaultData(long id, String rid, String aid) throws Exception {

        PaymentProfile paymentProfile = paymentProfileDAO.findById(id);

        // Look for the payment system with the provided rid
        for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
            PaymentSystem profilePaymentSystem = paymentSystemProfile.getPaymentSystem();

            if ((profilePaymentSystem != null) && (profilePaymentSystem.getRid().equals(rid))) {

                // Check if exist a product with same aid already registered for a payment system in the payment profile
                for (PaymentProductProfile paymentProductProfile : paymentSystemProfile.getPaymentProductProfiles()) {
                    PaymentProduct paymentProduct = paymentProductProfile.getPaymentProduct();

                    if ((paymentProduct != null) && (paymentProduct.getAid().equals(aid))) {
                        // Copy properties from -> to
                        BeanUtils.copyProperties(paymentProduct, paymentProductProfile);

                        // Update Payment Product Profile version number
                        updateVersion(paymentProductProfile);

                        return paymentProfileDAO.merge(paymentProfile);
                    }
                }

                throw new ObjectRetrievalFailureException(PaymentProduct.class, aid);
            }
        }

        throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
    }

    /**
     *
     * @param name
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<PaymentProfile> list(String name, int pageNumber, int rowsPerPage) throws Exception {

        int firstResult = (pageNumber - 1) * rowsPerPage;

        if (name == null) {
            return paymentProfileDAO.list(firstResult, rowsPerPage);
        } else {
            return paymentProfileDAO.list(name, firstResult, rowsPerPage);
        }
    }

    private void updateVersion(PaymentSystemProfile paymentSystemProfile) {
        int currentVersion = paymentSystemProfile.getVersion();

        if (currentVersion == 255) {
            paymentSystemProfile.setVersion(0);
        } else {
            paymentSystemProfile.setVersion(++currentVersion);
        }
    }
}
