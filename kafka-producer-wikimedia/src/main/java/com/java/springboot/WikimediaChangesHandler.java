package com.java.springboot;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaChangesHandler implements BackgroundEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {
        LOGGER.info("Connected to Wikimedia stream!");
    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) {
        String eventData = messageEvent.getData();
        LOGGER.info("Event data -> {}", eventData);

        kafkaTemplate.send(topic, eventData).whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.error("Failed to send message to Kafka", ex);
            } else {
                LOGGER.debug("Successfully sent message to Kafka, offset={}", result.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error in Wikimedia stream", throwable);
    }


}
