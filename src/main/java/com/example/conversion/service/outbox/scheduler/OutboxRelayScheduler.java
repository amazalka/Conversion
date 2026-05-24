package com.example.conversion.service.outbox.scheduler;

import com.example.conversion.kafka.producer.FileProducer;
import com.example.conversion.model.OutboxEvent;
import com.example.conversion.model.OutputEvent;
import com.example.conversion.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxRelayScheduler {
    private final OutboxService outboxService;
    private final FileProducer fileProducer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "outbox_relay", lockAtMostFor = "30S", lockAtLeastFor = "5S")
    public void relay() {
        try {
            List<OutboxEvent> events = outboxService.getUnsent();
            for (OutboxEvent outboxEvent : events) {
                OutputEvent payload = objectMapper.readValue(outboxEvent.getPayload(), OutputEvent.class);
                fileProducer.send(payload);
                outboxService.markSent(outboxEvent.getEventId());
            }
        } catch (Exception e) {

        }
    }
}
