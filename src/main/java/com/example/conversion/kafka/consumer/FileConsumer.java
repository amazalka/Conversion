package com.example.conversion.kafka.consumer;

import com.example.conversion.model.EventType;
import com.example.conversion.model.InputEvent;
import com.example.conversion.model.OutputEvent;
import com.example.conversion.service.conversion.ConversionService;
import com.example.conversion.service.inbox.InboxService;
import com.example.conversion.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileConsumer {
    private final ConversionService conversionService;
    private final InboxService inboxService;
    private final OutboxService outboxService;
    //из топика input-topic получает путь и тип файла InputEvent
    @KafkaListener(topics = "${conversion.kafka.topics.input}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(InputEvent event) throws Exception {
        if (!inboxService.tryStart(event.getEventId())){
            return;
        }
        try {
            String outputPath = conversionService.convert(event);
            OutputEvent outputEvent = new OutputEvent(event.getEventId(), outputPath);
            outboxService.saveEvent(EventType.FILE_CONVERTED, outputEvent);
            inboxService.markProcessed(event.getEventId());
        } catch (Exception e) {
            inboxService.markFailed(event.getEventId());
            throw e;
        }
    }
}
