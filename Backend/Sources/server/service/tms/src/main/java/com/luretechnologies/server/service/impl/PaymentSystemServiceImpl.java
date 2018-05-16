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
import com.luretechnologies.server.data.dao.PaymentSystemDAO;
import com.luretechnologies.server.data.model.tms.CAKey;
import com.luretechnologies.server.data.model.tms.PaymentProduct;
import com.luretechnologies.server.data.model.tms.PaymentSystem;
import com.luretechnologies.server.service.PaymentSystemService;
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
public class PaymentSystemServiceImpl implements PaymentSystemService {

    @Autowired
    private PaymentSystemDAO paymentSystemDAO;

    /**
     *
     * @param paymentSystem
     * @return
     * @throws Exception
     */
    @Override
    public PaymentSystem save(PaymentSystem paymentSystem) throws Exception {
        String rid = paymentSystem.getRid();

        if (rid != null) {
            PaymentSystem existentPaymentSystem = paymentSystemDAO.findByRID(rid);

            if (existentPaymentSystem == null) {
                throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
            }

            paymentSystem.setPaymentProducts(existentPaymentSystem.getPaymentProducts());
            paymentSystem.setCAKeys(existentPaymentSystem.getCAKeys());
        }

        return paymentSystemDAO.merge(paymentSystem);
    }

    /**
     *
     * @param rid
     * @throws Exception
     */
    @Override
    public void delete(String rid) throws Exception {
        PaymentSystem paymentSystem = paymentSystemDAO.findByRID(rid);

        if (paymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
        }

        paymentSystemDAO.delete(paymentSystem.getId());
    }

    /**
     *
     * @param rid
     * @return
     * @throws Exception
     */
    @Override
    public PaymentSystem get(String rid) throws Exception {
        PaymentSystem paymentSystem = paymentSystemDAO.findByRID(rid);

        if (paymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
        }

        return paymentSystem;
    }

    /**
     *
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<PaymentSystem> list(int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;

        return paymentSystemDAO.list(firstResult, rowsPerPage);
    }

    /**
     *
     * @param rid
     * @param product
     * @return
     * @throws Exception
     */
    @Override
    public PaymentSystem saveProduct(String rid, PaymentProduct product) throws Exception {
        PaymentSystem paymentSystem = paymentSystemDAO.findByRID(rid);

        if (paymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
        }

        // Set the current Payment System to the Products
        product.setPaymentSystem(paymentSystem);

        // If the product already exists for the paymentSystem, update product
        if (paymentSystem.getPaymentProducts().contains(product)) {
            // Update the existent product
            for (PaymentProduct existentProduct : paymentSystem.getPaymentProducts()) {
                if (existentProduct.equals(product)) {
                    paymentSystem.getPaymentProducts().remove(existentProduct);
                    paymentSystem.getPaymentProducts().add(product);
                    break;
                }
            }
        } else {
            // If the product does not exist for the Payment System and has an id defined
            if (product.getId() != 0) {
                throw new ObjectRetrievalFailureException(PaymentProduct.class, product.getId());
            }

            paymentSystem.getPaymentProducts().add(product);
            paymentSystem = paymentSystemDAO.merge(paymentSystem);
        }

        return paymentSystemDAO.merge(paymentSystem);
    }

    /**
     *
     * @param rid
     * @param aid
     * @return
     * @throws Exception
     */
    @Override
    public PaymentSystem deleteProduct(String rid, String aid) throws Exception {
        PaymentSystem paymentSystem = paymentSystemDAO.findByRID(rid);

        if (paymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
        }

        for (PaymentProduct product : paymentSystem.getPaymentProducts()) {
            if (product.getAid().equals(aid)) {
                paymentSystem.getPaymentProducts().remove(product);

                return paymentSystemDAO.merge(paymentSystem);
            }
        }

        throw new ObjectRetrievalFailureException(PaymentProduct.class, aid);
    }

    /**
     *
     * @param rid
     * @param caKey
     * @return
     * @throws Exception
     */
    @Override
    public PaymentSystem saveCAKey(String rid, CAKey caKey) throws Exception {
        PaymentSystem paymentSystem = paymentSystemDAO.findByRID(rid);

        if (paymentSystem == null) {
            throw new ObjectRetrievalFailureException(PaymentSystem.class, rid);
        }

        // If the caKey already exists for the paymentSystem, update caKey
        if (paymentSystem.getCAKeys().contains(caKey)) {
            for (CAKey existentCAKey : paymentSystem.getCAKeys()) {
                if (existentCAKey.equals(caKey)) {
                    paymentSystem.getCAKeys().remove(existentCAKey);

                    // Copy properties from -> to
                    BeanUtils.copyProperties(caKey, existentCAKey, new String[]{"version", "createdAt"});

                    // Update CAKey version number
                    updateVersion(existentCAKey);

                    paymentSystem.getCAKeys().add(existentCAKey);
                    break;
                }
            }
        } else {
            paymentSystem.getCAKeys().add(caKey);
            paymentSystem = paymentSystemDAO.merge(paymentSystem);
        }

        return paymentSystemDAO.merge(paymentSystem);

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
    public List<PaymentSystem> search(String filter, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;
        return paymentSystemDAO.search(filter, firstResult, rowsPerPage);
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
        return Utils.getTotalPages(paymentSystemDAO.search(filter, -1, -1).size(), rowsPerPage);
    }

    private void updateVersion(CAKey caKey) {
        int currentVersion = caKey.getVersion();

        if (currentVersion == 255) {
            caKey.setVersion(0);
        } else {
            caKey.setVersion(++currentVersion);
        }
    }

    /**
     *
     * @param name
     * @param rid
     * @param pageNumber
     * @param rowsPerPage
     * @return
     * @throws Exception
     */
    @Override
    public List<PaymentSystem> list(String name, String rid, int pageNumber, int rowsPerPage) throws Exception {
        int firstResult = (pageNumber - 1) * rowsPerPage;

        if (name == null && rid == null) {
            return paymentSystemDAO.list(firstResult, rowsPerPage);
        } else {
            return paymentSystemDAO.list(name, rid, firstResult, rowsPerPage);
        }
    }
}
