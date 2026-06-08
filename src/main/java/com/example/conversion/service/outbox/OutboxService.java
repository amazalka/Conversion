package com.example.conversion.service.outbox;

import com.example.conversion.exception.SaveOutboxException;
import com.example.conversion.model.EventStatus;
import com.example.conversion.model.EventType;
import com.example.conversion.model.OutboxEvent;
import lombok.*;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public void saveEvent(EventType eventType, Object payload) {
        try {
            outboxRepository.save(new OutboxEvent(null, eventType, objectMapper.writeValueAsString(payload), LocalDateTime.now(), null));
        } catch (RuntimeException e) {
            throw new SaveOutboxException("Failed to save outbox", e);
        }
    }

    public List<OutboxEvent> getUnsent() {
        return outboxRepository.findBySentAtIsNull();
    }

    public void markSent(Long id) {
        outboxRepository.findById(id).ifPresent(e -> {
            e.setSentAt(LocalDateTime.now());
            outboxRepository.save(e);
        });
    }
}
