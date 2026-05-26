package com.example.conversion.kafka.producer;

import com.example.conversion.model.OutputEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class FileProducer {
    private final KafkaTemplate<Object, Object> kafkaTemplate;
    @Value("${conversion.kafka.topics.output}")
    private String topic;
    //отправляет в топик output-topic путь файла OutputEvent
    public CompletableFuture<SendResult<Object, Object>> send(OutputEvent event){
        return kafkaTemplate.send(topic, event);
    }
}
