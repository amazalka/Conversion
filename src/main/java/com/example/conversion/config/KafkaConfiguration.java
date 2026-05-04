package com.example.conversion.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    //создали 2 топика
    @Bean
    public NewTopic inputTopic() {
        return new NewTopic("input-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic outputTopic() {
        return new NewTopic("output-topic", 2, (short) 1);
    }
}
