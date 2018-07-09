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
package com.luretechnologies.server.service.supporting;

import com.luretechnologies.server.common.utils.Utils;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@LocalBean
@Startup
public class Packaging {

    @Resource
    private TimerService timerService;

    static final Logger LOGGER = LoggerFactory.getLogger(Packaging.class);
    final String TIMER_NAME = "PACKAGING";
    Timer timer = null;

    @PostConstruct
    private void activate() {

        LOGGER.info("PostConstruct: Activating " + TIMER_NAME + " Activity Timer...");

        if (timer == null) {
            timer = createTimer(TIMER_NAME);
        } else {
            LOGGER.info("PostConstruct: Already Created " + TIMER_NAME + " Activity Timer...");
        }
    }

    @PreDestroy
    private void deactivate() {

        LOGGER.info("PrePassivate: Deactivating " + TIMER_NAME + " Activity Timer...");

        if (timer != null) {
            timer.cancel();
            LOGGER.info("PrePassivate: Canceled " + TIMER_NAME + " Activity Timer...");
            timer = null;
        }
    }

    @Timeout
    public void execute(Timer timer) {

        try {
            LOGGER.debug("begin execute: " + timer.getInfo());
            String api = System.getProperty("TMS_API_URL");
            String result = Utils.doGet(api + "/packaging/createPackaging", 30, true);
            LOGGER.debug("finish execute: " + timer.getInfo() + " --> " + result);
        } catch (IllegalStateException | EJBException ex) {
        } catch (Exception ex) {
        }
    }

    private Timer createTimer(String name) {

        String enabled;
        int delayStart;
        int minutes;

        enabled = System.getProperty(name + "_ACTIVITY_ENABLED");

        if (enabled != null && !enabled.isEmpty() && enabled.equalsIgnoreCase("true")) {

            try {
                delayStart = Integer.valueOf(System.getProperty(name + "_ACTIVITY_START_DELAY_MINUTES"));
                minutes = Integer.valueOf(System.getProperty(name + "_ACTIVITY_INTERVAL_MINUTES"));
                Timer t = timerService.createTimer(Utils.MS_MINUTE * delayStart, Utils.MS_MINUTE * minutes, name);
                LOGGER.info("PostConstruct: Created " + name + " Activity Timer to run every " + minutes + " minute(s)...");
                return timer;
            } catch (IllegalArgumentException | IllegalStateException | EJBException ex) {
            }
        }

        LOGGER.warn("PostConstruct: " + name + " Activity Timer was NOT activated...");
        return null;
    }
}
