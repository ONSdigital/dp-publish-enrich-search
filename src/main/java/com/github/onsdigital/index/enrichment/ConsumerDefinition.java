package com.github.onsdigital.index.enrichment;

import com.github.onsdigital.index.enrichment.kafka.KafkaConfig;
import com.github.onsdigital.index.enrichment.kafka.KafkaConsumerConfig;
import com.github.onsdigital.index.enrichment.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.integration.dsl.kafka.KafkaMessageDrivenChannelAdapterSpec;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.messaging.MessageChannel;

import javax.annotation.PostConstruct;

/**
 * Created by fawks on 31/01/2017.
 */
@Configuration
public class ConsumerDefinition {
    public static final String ERROR_CHANNEL = "exceptionChannel";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDefinition.class);


    @Autowired
    private KafkaConsumerConfig kafkaListenerConfig;
    @Autowired
    private KafkaConfig kafkaConfig;
    @Autowired
    private DocumentLoaderService loadDocumentService;
    @Autowired
    private EnrichmentService enrichmentService;
    @Autowired
    private UpsertService upsertService;
    @Autowired
    private TransformService transformService;
    @Autowired
    private ErrorService errorService;

    public DocumentLoaderService getLoadDocumentService() {
        return loadDocumentService;
    }

    @PostConstruct
    public void init() {
        LOGGER.warn("init([]) : Initialising complete(ish)...");
    }

    @Bean
    public IntegrationFlow errorHandling() {
        return IntegrationFlows.from(customErrorChannel())
                               .handle(errorService)
                               .get();
    }

    @Bean
    public MessageChannel customErrorChannel() {
        return MessageChannels.direct(ERROR_CHANNEL)
                              .get();
    }


    @Bean
    public IntegrationFlow fromKafka(AbstractMessageListenerContainer container) {
        KafkaMessageDrivenChannelAdapterSpec messageConsumer = Kafka.messageDrivenChannelAdapter(container);
        messageConsumer.ackDiscarded(true);
        messageConsumer.errorChannel(customErrorChannel());

        return IntegrationFlows
                .from(messageConsumer)
                .handle(transformService)
                .handle(loadDocumentService)
                .split()
                .handle(enrichmentService)
                .handle(upsertService)
                .get();
    }


    @Bean
    public DefaultKafkaConsumerFactory<?, ?> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<Integer, String>(this.kafkaListenerConfig.getConfig());
    }

    @Bean
    public ConcurrentMessageListenerContainer<?, ?> concurrentKafkaMessageListenerContainer(
            DefaultKafkaConsumerFactory factory) {
        ContainerProperties props = new ContainerProperties(kafkaConfig.getTopic());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(factory,
                                                                                              props);
        container.setConcurrency(kafkaConfig.getConsumers());

        return container;
    }

}
