/*
 * Copyright (C) 2023 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import java.util.Arrays;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author comrad
 */
@Configuration
@EnableJms
public class JmsConfig {
    
    @Value("${amq.brokerUrl}")
    private String amqBrokerUrl;
    @Value("${amq.username}")
    private String amqUsername;
    @Value("${amq.password}")
    private String amqPassword;
    
    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(amqBrokerUrl);
        connectionFactory.setUserName(amqUsername);
        connectionFactory.setPassword(amqPassword);
        
        connectionFactory.setTrustedPackages(Arrays.asList("de.holarse.backend.api.drückblick", "de.holarse.backend.types", "de.holarse.queues.commands", "java.util.HashMap"));
        
        return connectionFactory;        
    }    
    
    @Bean
    @Qualifier("jmsTemplate")    
    public JmsTemplate jmsTemplateConnectionFactory() {
        final JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setConnectionFactory(jmsConnectionFactory());
        return jmsTemplate;        
    }
    
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        final DefaultJmsListenerContainerFactory listenerFactory = new DefaultJmsListenerContainerFactory();
        listenerFactory.setConnectionFactory((jmsConnectionFactory()));
        return listenerFactory;
    }
    
}
