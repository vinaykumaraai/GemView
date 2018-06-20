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

@Singleton
@LocalBean
@Startup
public class Scheduler {

    @Resource
    private TimerService timerService;

    Timer timerAlerting = null;
    Timer timerPackaging = null;

    @PostConstruct
    private void activate() {

        System.out.println("PostConstruct: Activating timers...");

        if (timerAlerting == null) {
            timerAlerting = createTimer("ALERTING");
        } else {
            System.out.println("PostConstruct: Already Created Alerting Activity Timer...");
        }

        if (timerPackaging == null) {
            timerPackaging = createTimer("PACKAGING");
        } else {
            System.out.println("PostConstruct: Already Created Packaging Activity Timer...");
        }
    }

    @PreDestroy
    private void deactivate() {

        System.out.println("PrePassivate: Deactivating timers...");

        if (timerAlerting != null) {
            timerAlerting.cancel();
            System.out.println("PrePassivate: Canceled Alerting Activity Timer...");
            timerAlerting = null;
        }

        if (timerPackaging != null) {
            timerPackaging.cancel();
            System.out.println("PrePassivate: Canceled Packaging Packaging Timer...");
            timerPackaging = null;
        }
    }

    @Timeout
    public void execute(Timer timer) {
        System.out.println("Timeout: " + timer.getInfo());
    }

    private Timer createTimer(String name) {

        String enabled;
        int delayStart;
        int interval;
        Timer timer = null;

        enabled = System.getProperty(name + "_ACTIVITY_ENABLED");

        if (enabled != null && !enabled.isEmpty() && enabled.equalsIgnoreCase("true")) {

            try {
                delayStart = Integer.valueOf(System.getProperty(name + "_ACTIVITY_START_DELAY"));
                interval = Integer.valueOf(System.getProperty(name + "_ACTIVITY_INTERVAL"));
                timer = timerService.createTimer(delayStart, interval, name);
                System.out.println("PostConstruct: Created " + name + " Activity Timer...");
            } catch (IllegalArgumentException | IllegalStateException | EJBException ex) {
            }
        }

        return timer;
    }
}
