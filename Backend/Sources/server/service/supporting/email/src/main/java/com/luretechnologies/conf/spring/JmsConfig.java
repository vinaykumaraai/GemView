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
package com.luretechnologies.conf.spring;

import com.luretechnologies.server.common.ServerJNDI;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 *
 * @author developer
 */
@EnableJms
@Configuration
public class JmsConfig {

    public static final String SERVICE_IN_QUEUE = "jms_ServerX_Queue_ServiceIn_Email";

    /**
     *
     * @return @throws Exception
     */
    @Bean
    public ActiveMQConnectionFactory connectionFactory() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(ServerJNDI.BROKER_URL);

        return connectionFactory;
    }

    /**
     *
     * @return
     */
    @Bean
    public ActiveMQQueue serviceInQueue() {
        ActiveMQQueue coreInQueue = new ActiveMQQueue();
        coreInQueue.setPhysicalName(SERVICE_IN_QUEUE);

        return coreInQueue;
    }

    /**
     *
     * @return @throws Exception
     */
    @Bean
    public JmsTemplate jmsTemplate() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestination(serviceInQueue());
        jmsTemplate.setTimeToLive(8000);
        jmsTemplate.setReceiveTimeout(5000);
        jmsTemplate.setSessionTransacted(true);
//        jmsTemplate.setExplicitQosEnabled(true);
        jmsTemplate.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        jmsTemplate.afterPropertiesSet();

        return jmsTemplate;
    }

    /**
     *
     * @return @throws Exception
     */
    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws Exception {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("3-10");

        return factory;
    }

}
