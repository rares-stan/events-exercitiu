package com.example.demo.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class EventsJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(EventsJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final EventRepo eventRepo;

    public EventsJsonDataLoader(ObjectMapper objectMapper, EventRepo eventRepo) {
        this.objectMapper = objectMapper;
        this.eventRepo = eventRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if(eventRepo.count() == 0) {
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/events.json")) {
                Events allEvents = objectMapper.readValue(inputStream, Events.class);
                eventRepo.saveAll(allEvents.events());
            }
        } else {
            log.info("Table already contains data skipping seeding.");
        }
    }
}
