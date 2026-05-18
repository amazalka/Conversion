package com.example.conversion.service.inbox;

import com.example.conversion.model.InboxEntity;
import com.example.conversion.model.InboxStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class InboxService {
    private final InboxRepository repository;

    public boolean tryStart(String eventId) {
        try {
            repository.save(new InboxEntity(
                    eventId,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    InboxStatus.PROCESSING
            ));
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    public void markProcessed(String eventId) {
        repository.findById(eventId).ifPresent(e -> {
            e.setStatus(InboxStatus.DONE);
            e.setUpdatedAt(LocalDateTime.now());
            repository.save(e);
        });
    }

    public void markFailed(String eventId) {
        repository.findById(eventId).ifPresent(e -> {
            e.setStatus(InboxStatus.FAILED);
            e.setUpdatedAt(LocalDateTime.now());
            repository.save(e);
        });
    }
}
