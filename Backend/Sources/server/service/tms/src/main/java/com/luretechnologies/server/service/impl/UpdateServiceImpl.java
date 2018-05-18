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

import com.luretechnologies.common.Constants;
import com.luretechnologies.common.enums.DayEnum;
import com.luretechnologies.common.enums.TimeEnum;
import com.luretechnologies.server.common.Messages;
import com.luretechnologies.server.common.utils.Utils;
import com.luretechnologies.server.common.utils.exceptions.CustomException;
import com.luretechnologies.server.data.dao.TerminalDAO;
import com.luretechnologies.server.data.display.tms.ParameterDisplay;
import com.luretechnologies.server.data.display.tms.PaymentProductDisplay;
import com.luretechnologies.server.data.display.tms.PaymentProfileDisplay;
import com.luretechnologies.server.data.display.tms.PaymentSystemDisplay;
import com.luretechnologies.server.data.display.tms.UpdatePackage;
import com.luretechnologies.server.data.model.Terminal;
import com.luretechnologies.server.data.model.tms.Application;
import com.luretechnologies.server.data.model.tms.ApplicationPackage;
import com.luretechnologies.server.data.model.tms.Parameter;
import com.luretechnologies.server.data.model.tms.PaymentProductProfile;
import com.luretechnologies.server.data.model.tms.PaymentProfile;
import com.luretechnologies.server.data.model.tms.PaymentSystemProfile;
import com.luretechnologies.server.data.model.tms.Rule;
import com.luretechnologies.server.data.model.tms.ScheduleGroup;
import com.luretechnologies.server.data.model.tms.TerminalApplicationParameter;
import com.luretechnologies.server.data.model.tms.TimeInterval;
import com.luretechnologies.server.service.UpdateService;
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
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private TerminalDAO terminalDAO;

    /**
     *
     * @param application
     * @param serialNumber
     * @return
     * @throws Exception
     */
    @Override
    public UpdatePackage getUpdate(Application application, String serialNumber) throws Exception {

        Terminal terminal = terminalDAO.findBySerialNumber(serialNumber);

        if (terminal == null) {
            throw new ObjectRetrievalFailureException(Terminal.class, serialNumber);
        }

        // Check schedule update rule
        if (!allowUpdate(terminal.getScheduleGroup())) {
            throw new CustomException(Constants.CODE_NOTHING_TO_UPDATE, Messages.NOTHING_TO_UPDATE);
        }

        // Check if the Application is set up to this Terminal
        for (ApplicationPackage applicationPkg : terminal.getApplicationPackages()) {
            if (applicationPkg.getApplication().getId() == application.getId()) {
                // Create new UpdatePackage
                UpdatePackage updatePkg = new UpdatePackage();

                // Set KeyBlock
//                updatePkg.setKeyBlock(terminal.getKeyBlock());

                // Check for Parameters Update
                for (Parameter parameter : application.getParameters()) {
                    ParameterDisplay parameterDisplay;
                    if (parameter.getApplicationWide()) {
                        parameterDisplay = new ParameterDisplay(parameter);
                        updatePkg.getParameters().add(parameterDisplay);
                    } else {
                        for (TerminalApplicationParameter terminalApplicationParameter : applicationPkg.getParameters()) {
                            if (terminalApplicationParameter.getParameter().equals(parameter)) {
                                parameterDisplay = new ParameterDisplay(terminalApplicationParameter);
                                updatePkg.getParameters().add(parameterDisplay);
                                break;
                            }
                        }
                    }
                }

                PaymentProfile paymentProfile = applicationPkg.getPaymentProfile();
                PaymentProfileDisplay paymentProfileDisplay = new PaymentProfileDisplay(paymentProfile);

                // Get Payment Systems
                for (PaymentSystemProfile paymentSystemProfile : paymentProfile.getPaymentSystemProfiles()) {
                    PaymentSystemDisplay paymentSystemDisplay = new PaymentSystemDisplay(paymentSystemProfile);

                    // Get Payment Products
                    for (PaymentProductProfile paymentProductProfile : paymentSystemProfile.getPaymentProductProfiles()) {
                        PaymentProductDisplay paymentProductDisplay = new PaymentProductDisplay(paymentProductProfile);
                        paymentSystemDisplay.getPaymentProducts().add(paymentProductDisplay);
                    }

                    // Get CAKeys
                    paymentSystemDisplay.getCAKeys().addAll(paymentSystemProfile.getPaymentSystem().getCAKeys());

                    paymentProfileDisplay.getPaymentSystems().add(paymentSystemDisplay);
                }

                updatePkg.setPaymentProfile(paymentProfileDisplay);

                return updatePkg;
            }
        }

        // Throw error in the Application is not set up to this Terminal
        throw new ObjectRetrievalFailureException(Application.class, application.getId());
    }

    private boolean allowUpdate(ScheduleGroup scheduleGroup) {
        for (Rule rule : scheduleGroup.getRules()) {
            DayEnum day = rule.getDay();
            TimeInterval timeInterval = rule.getTimeInterval();

            if ((Utils.getDayOfTheWeek() == day) || (day == DayEnum.ALL)) {
                TimeEnum startTime = timeInterval.getStart();
                TimeEnum endTime = timeInterval.getEnd();

                TimeEnum currentHour = Utils.getHour();

                if ((startTime == TimeEnum._24HOURS) || (endTime == TimeEnum._24HOURS)) {
                    return true;
                } else if ((startTime.getId() <= currentHour.getId()) && (endTime.getId() > currentHour.getId())) {
                    return true;
                }
            }
        }

        return false;
    }
}
