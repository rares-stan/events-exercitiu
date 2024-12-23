package com.example.demo.event;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record Event(
        @Id
        Integer id,
        @NotNull
        EventType type,
        @NotNull
        LocalDateTime createdOn,
        @NotNull
        Integer deviceId,
        @NotNull
        Integer userId,
        @Version
        Integer version
) {}
