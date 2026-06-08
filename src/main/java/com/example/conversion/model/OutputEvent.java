package com.example.conversion.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputEvent {
    private String eventId;
    private String filePath;
    private EventStatus status;
}
