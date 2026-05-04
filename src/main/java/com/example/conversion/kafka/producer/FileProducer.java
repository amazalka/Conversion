package com.example.conversion.kafka.producer;

import com.example.conversion.model.OutputEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FileProducer {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    //отправляет в топик output-topic путь файла OutputEvent
    public void send(OutputEvent event){
        kafkaTemplate.send("output-topic", event);
    }
}
