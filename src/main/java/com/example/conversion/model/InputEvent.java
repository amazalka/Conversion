package com.example.conversion.model;

import lombok.Data;

@Data
public class InputEvent {
    private String eventId;
    private String filePath;
    private FileType type;
}
