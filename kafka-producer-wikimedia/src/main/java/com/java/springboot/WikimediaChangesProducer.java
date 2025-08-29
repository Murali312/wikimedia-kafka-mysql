package com.java.springboot;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia-recentChange";
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // Event handler (sends data to Kafka)
        WikimediaChangesHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        // ✅ Build ConnectStrategy with custom headers (works in v4.1.1)
        ConnectStrategy connectStrategy = ConnectStrategy.http(URI.create(url))
                .headers(Headers.of("User-Agent", "my-wikimedia-producer/1.0 (me@example.com)"));

        // EventSource.Builder now takes ConnectStrategy
        EventSource.Builder builder = new EventSource.Builder(connectStrategy);

        // Build BackgroundEventSource
        BackgroundEventSource eventSource =
                new BackgroundEventSource.Builder(eventHandler, builder).build();

        // Start listening
        eventSource.start();

        LOGGER.info("Started streaming from Wikimedia...");

        // Keep it alive for 10 minutes
        TimeUnit.MINUTES.sleep(10);

        // (Optional) Clean shutdown after sleep
        eventSource.close();
    }
}
