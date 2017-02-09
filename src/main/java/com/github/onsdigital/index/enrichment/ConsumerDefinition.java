package com.github.onsdigital.index.enrichment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.kafka.KafkaConfig;
import com.github.onsdigital.index.enrichment.kafka.KafkaConsumerConfig;
import com.github.onsdigital.index.enrichment.model.EnrichAllIndexedDocumentsRequest;
import com.github.onsdigital.index.enrichment.model.EnrichIndexedDocumentsRequest;
import com.github.onsdigital.index.enrichment.model.EnrichResourceDocumentsRequest;
import com.github.onsdigital.index.enrichment.model.transformer.RequestTransformer;
import com.github.onsdigital.index.enrichment.service.DocumentLoaderService;
import com.github.onsdigital.index.enrichment.service.EnrichmentService;
import com.github.onsdigital.index.enrichment.service.UpsertService;
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
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.concurrent.Executors;

/**
 * Created by fawks on 31/01/2017.
 */
@Configuration
public class ConsumerDefinition {

  public static ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.registerSubtypes(EnrichAllIndexedDocumentsRequest.class,
                            EnrichIndexedDocumentsRequest.class,
                            EnrichResourceDocumentsRequest.class);
  }

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

  @Bean
  public IntegrationFlow fromKafka(AbstractMessageListenerContainer container) {
    KafkaMessageDrivenChannelAdapterSpec messageConsumer = Kafka.messageDrivenChannelAdapter(container);
    messageConsumer.ackDiscarded(true);

    return IntegrationFlows
        .from(messageConsumer)
        .transform(new RequestTransformer())
        .handle(loadDocumentService)
        .split()
        .channel(MessageChannels.executor("enrich", Executors.newFixedThreadPool(12)))
        .handle(enrichmentService)
        .handle(upsertService)
        .get();
  }

  @Bean
  public KafkaMessageListenerContainer<?, ?> kafkaMessageListenerContainer(DefaultKafkaConsumerFactory factory) {
    ContainerProperties props = new ContainerProperties(kafkaConfig.getTopic());
    return new KafkaMessageListenerContainer(factory, props);
  }

  @Bean
  public DefaultKafkaConsumerFactory<?, ?> kafkaConsumerFactory() {
    return new DefaultKafkaConsumerFactory<Integer, String>(this.kafkaListenerConfig.getConfig());
  }


}
