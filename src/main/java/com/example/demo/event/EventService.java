package com.example.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<HourlyEvents> hoursPadded(List<HourlyEvents> recorded) {
        ArrayList<HourlyEvents> start = new ArrayList<>();
        Integer startH = 0, endH = 23;

        Integer rf = recorded.getFirst().hour();
        while(startH < rf) {
            start.add(new HourlyEvents(startH, 0.0));
            startH++;
        }

        for(HourlyEvents ev:recorded) {
            if(start.isEmpty()) {
                start.add(ev);
                continue;
            }

            while(start.getLast().hour()+1 < ev.hour()) {
                start.add(new HourlyEvents(start.getLast().hour()+1, 0.0));
            }

            start.add(ev);
        }

        while (start.getLast().hour() < endH) {
            start.add(new HourlyEvents(start.getLast().hour() + 1, 0.0));
        }

        return start;
    }

    public List<HourlyEvents> hourlyEventCount(LocalDate d, Integer deviceId) {
        var recorded = eventRepo.eventsByHourForDate(d, deviceId);
        return hoursPadded(recorded);
    }

    public List<HourlyEvents> hourlyEventMeanExceptDate(LocalDate d, Integer deviceId) {
        var recorded = eventRepo.eventsByHourExceptDate(d, deviceId);
        return hoursPadded(recorded);
    }

    public List<HourlyBusyGrade> hourlyBusyGrades(LocalDate d, Integer deviceId) {
        var forD = hourlyEventCount(d, deviceId);
        var totalMean = hourlyEventMeanExceptDate(d, deviceId);
        List<HourlyBusyGrade> bussyGradeList = new ArrayList<>();

        for(int i = 0; i < forD.size(); i++) {
            var a = forD.get(i);
            var b = totalMean.get(i);
            BusyScale busy;

            if(a.count().equals(b.count()) || Math.abs(a.count() - b.count()) < b.count() * 3 /100) {
                busy =  BusyScale.Normal;
            } else {
                if(a.count() < b.count()) {
                    busy= BusyScale.Quite;
                } else {
                    busy = BusyScale.Busy;
                }
            }

            bussyGradeList.add(new HourlyBusyGrade(a.hour(), busy));
        }

        return bussyGradeList;
    }
}
