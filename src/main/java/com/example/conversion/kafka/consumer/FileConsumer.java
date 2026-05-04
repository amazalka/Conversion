package com.example.conversion.kafka.consumer;

import com.example.conversion.kafka.producer.FileProducer;
import com.example.conversion.model.InputEvent;
import com.example.conversion.model.OutputEvent;
import com.example.conversion.service.conversion.ConversionService;
import com.example.conversion.service.inbox.InboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileConsumer {
    private final ConversionService conversionService;
    private final InboxService inboxService;
    private final FileProducer fileProducer;
    //из топика input-topic получает путь и тип файла InputEvent
    @KafkaListener(topics = "input-topic", groupId = "converter-group")
    public void listen(InputEvent event) throws Exception {
        if (!inboxService.tryStart(event.getEventId())){
            return;
        }
        try {
            String outputPath = conversionService.convert(event);
            fileProducer.send(new OutputEvent(event.getEventId(), outputPath));
            inboxService.markProcessed(event.getEventId());
        } catch (Exception e) {
            inboxService.markFailed(event.getEventId());
            throw e;
        }
        System.out.println("Received event = " + event);
    }
}
