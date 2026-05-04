package com.example.conversion.model;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "inbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboxEntity {
    @Id
    @Column(name = "event_id")
    public String eventId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}
