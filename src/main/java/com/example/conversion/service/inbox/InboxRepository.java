package com.example.conversion.service.inbox;

import com.example.conversion.model.InboxEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface InboxRepository extends JpaRepository<InboxEntity, String> {
}
