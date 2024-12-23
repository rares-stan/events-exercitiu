package com.example.demo.event;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepo eventRepo;
    private final EventService eventService;

    public EventController(EventRepo eventRepo, EventService eventService) {
        this.eventRepo = eventRepo;
        this.eventService = eventService;
    }

    @GetMapping()
    List<Event> allEvents() {
        return eventRepo.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    Event create(@Valid @RequestBody Event event) {
        return eventRepo.save(event);
    }

    @GetMapping("/hourly/{rDate}/device/{deviceId}")
    List<HourlyEvents> getHourly(@PathVariable("rDate") String rDate, @PathVariable("deviceId") Integer deviceId) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return eventService.hourlyEventCount(LocalDate.parse(rDate, f), deviceId);
    }


    @GetMapping("/hourly/{rDate}/device/{deviceId}/except")
    List<HourlyEvents> getHourlyExcept(@PathVariable("rDate") String rDate, @PathVariable("deviceId") Integer deviceId) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return eventService.hourlyEventMeanExceptDate(LocalDate.parse(rDate, f), deviceId);
    }

    @GetMapping("/how-busy/{rDate}/device/{deviceId}")
    List<HourlyBusyGrade> howBusy(@PathVariable("rDate") String rDate, @PathVariable("deviceId") Integer deviceId) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var d = LocalDate.parse(rDate, f);
        return eventService.hourlyBusyGrades(d, deviceId);
    }
}
