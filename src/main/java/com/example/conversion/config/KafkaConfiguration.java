package com.example.conversion.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    //создали 2 топика
    @Bean
    public NewTopic inputTopic(@Value("${conversion.kafka.topics.input}") String topic){
        return new NewTopic(topic, 1, (short) 1);
    }

    @Bean
    public NewTopic outputTopic(@Value("${conversion.kafka.topics.output}") String topic) {
        return new NewTopic(topic, 2, (short) 1);
    }
}
