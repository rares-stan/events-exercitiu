package com.example.demo.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class EventServiceTest {

    @MockitoBean
    EventRepo repo;

    private final List<HourlyEvents> hourlyEvents = new ArrayList<>();

    @Test
    void shouldPadAllHoursBefore23() {
        var a = new EventService(repo);
        var h23Event = new HourlyEvents(23, 1.0);
        hourlyEvents.add(h23Event);

        var res = a.hoursPadded(hourlyEvents);

        Assertions.assertEquals(24, res.size());
        Assertions.assertEquals(new HourlyEvents(0, 0.0), res.getFirst());
        Assertions.assertEquals(h23Event, res.getLast());
    }

    @Test
    void shouldPadAllHoursAfter0() {
        var a = new EventService(repo);
        var h0Event = new HourlyEvents(0, 1.0);
        hourlyEvents.add(h0Event);

        var res = a.hoursPadded(hourlyEvents);

        Assertions.assertEquals(24, res.size());
        Assertions.assertEquals(h0Event, res.getFirst());
        Assertions.assertEquals(new HourlyEvents(23, 0.0), res.getLast());
    }

    @Test
    void shouldPadHoursInBetweenNonConsecutiveHours() {
        var a = new EventService(repo);
        var h10Event = new HourlyEvents(10, 1.0);
        var h13Event = new HourlyEvents(13, 1.0);
        hourlyEvents.add(h10Event);
        hourlyEvents.add(h13Event);

        var res = a.hoursPadded(hourlyEvents);

        Assertions.assertEquals(24, res.size());
        Assertions.assertEquals(h10Event, res.get(10));
        Assertions.assertEquals(h13Event, res.get(13));
        Assertions.assertEquals(new HourlyEvents(23, 0.0), res.getLast());
        Assertions.assertEquals(new HourlyEvents(0, 0.0), res.getFirst());
    }

    @Test
    void shouldHourlyBusyGradeBeNormalOnEqualValues() {
        var a = Mockito.mock(EventService.class);
        var d = LocalDate.now();
        var deviceId = 1;
        var dayList = new ArrayList<HourlyEvents>();
        var avgList = new ArrayList<HourlyEvents>();

        dayList.add(new HourlyEvents(0, 1.0));
        dayList.add(new HourlyEvents(1, 1.0));
        dayList.add(new HourlyEvents(2, 1.0));
        dayList.add(new HourlyEvents(3, 1.0));
        dayList.add(new HourlyEvents(4, 1.0));
        dayList.add(new HourlyEvents(5, 1.0));
        dayList.add(new HourlyEvents(6, 1.0));
        dayList.add(new HourlyEvents(7, 1.0));
        dayList.add(new HourlyEvents(8, 1.0));
        dayList.add(new HourlyEvents(9, 1.0));
        dayList.add(new HourlyEvents(10, 1.0));
        dayList.add(new HourlyEvents(11, 1.0));
        dayList.add(new HourlyEvents(12, 1.0));
        dayList.add(new HourlyEvents(13, 1.0));
        dayList.add(new HourlyEvents(14, 1.0));
        dayList.add(new HourlyEvents(15, 1.0));
        dayList.add(new HourlyEvents(16, 1.0));
        dayList.add(new HourlyEvents(17, 1.0));
        dayList.add(new HourlyEvents(18, 1.0));
        dayList.add(new HourlyEvents(19, 1.0));
        dayList.add(new HourlyEvents(20, 1.0));
        dayList.add(new HourlyEvents(21, 1.0));
        dayList.add(new HourlyEvents(22, 1.0));
        dayList.add(new HourlyEvents(23, 1.0));

        avgList.add(new HourlyEvents(0, 1.0));
        avgList.add(new HourlyEvents(1, 1.0));
        avgList.add(new HourlyEvents(2, 1.0));
        avgList.add(new HourlyEvents(3, 1.0));
        avgList.add(new HourlyEvents(4, 1.0));
        avgList.add(new HourlyEvents(5, 1.0));
        avgList.add(new HourlyEvents(6, 1.0));
        avgList.add(new HourlyEvents(7, 1.0));
        avgList.add(new HourlyEvents(8, 1.0));
        avgList.add(new HourlyEvents(9, 1.0));
        avgList.add(new HourlyEvents(10, 1.0));
        avgList.add(new HourlyEvents(11, 1.0));
        avgList.add(new HourlyEvents(12, 1.0));
        avgList.add(new HourlyEvents(13, 1.0));
        avgList.add(new HourlyEvents(14, 1.0));
        avgList.add(new HourlyEvents(15, 1.0));
        avgList.add(new HourlyEvents(16, 1.0));
        avgList.add(new HourlyEvents(17, 1.0));
        avgList.add(new HourlyEvents(18, 1.0));
        avgList.add(new HourlyEvents(19, 1.0));
        avgList.add(new HourlyEvents(20, 1.0));
        avgList.add(new HourlyEvents(21, 1.0));
        avgList.add(new HourlyEvents(22, 1.0));
        avgList.add(new HourlyEvents(23, 1.0));

        Mockito.when(a.hourlyEventCount(d, deviceId)).thenReturn(dayList);
        Mockito.when(a.hourlyEventMeanExceptDate(d, deviceId)).thenReturn(avgList);
        Mockito.when(a.hourlyBusyGrades(d, deviceId)).thenCallRealMethod();

        var res = a.hourlyBusyGrades(d, deviceId);

        for(var t : res) {
            Assertions.assertEquals(BusyScale.Normal, t.grade());
        }
    }

    @Test
    void shouldHourlyBusyGradeBeNormalOnApproxEqualValues() {
        var a = Mockito.mock(EventService.class);
        var d = LocalDate.now();
        var deviceId = 1;
        var dayList = new ArrayList<HourlyEvents>();
        var avgList = new ArrayList<HourlyEvents>();

        dayList.add(new HourlyEvents(0, 1.0));
        dayList.add(new HourlyEvents(1, 1.0));
        dayList.add(new HourlyEvents(2, 1.0));
        dayList.add(new HourlyEvents(3, 1.0));
        dayList.add(new HourlyEvents(4, 1.0));
        dayList.add(new HourlyEvents(5, 1.0));
        dayList.add(new HourlyEvents(6, 1.0));
        dayList.add(new HourlyEvents(7, 1.0));
        dayList.add(new HourlyEvents(8, 1.0));
        dayList.add(new HourlyEvents(9, 1.0));
        dayList.add(new HourlyEvents(10, 1.0));
        dayList.add(new HourlyEvents(11, 1.0));
        dayList.add(new HourlyEvents(12, 1.0));
        dayList.add(new HourlyEvents(13, 1.0));
        dayList.add(new HourlyEvents(14, 1.0));
        dayList.add(new HourlyEvents(15, 1.0));
        dayList.add(new HourlyEvents(16, 1.0));
        dayList.add(new HourlyEvents(17, 1.0));
        dayList.add(new HourlyEvents(18, 1.0));
        dayList.add(new HourlyEvents(19, 1.0));
        dayList.add(new HourlyEvents(20, 1.0));
        dayList.add(new HourlyEvents(21, 1.0));
        dayList.add(new HourlyEvents(22, 1.0));
        dayList.add(new HourlyEvents(23, 1.0));

        avgList.add(new HourlyEvents(0, 1.02));
        avgList.add(new HourlyEvents(1, 1.02));
        avgList.add(new HourlyEvents(2, 1.02));
        avgList.add(new HourlyEvents(3, 1.02));
        avgList.add(new HourlyEvents(4, 1.0));
        avgList.add(new HourlyEvents(5, 1.0));
        avgList.add(new HourlyEvents(6, 1.0));
        avgList.add(new HourlyEvents(7, 1.0));
        avgList.add(new HourlyEvents(8, 1.0));
        avgList.add(new HourlyEvents(9, 1.0));
        avgList.add(new HourlyEvents(10, 1.0));
        avgList.add(new HourlyEvents(11, 1.0));
        avgList.add(new HourlyEvents(12, 1.0));
        avgList.add(new HourlyEvents(13, 1.0));
        avgList.add(new HourlyEvents(14, 1.0));
        avgList.add(new HourlyEvents(15, 1.0));
        avgList.add(new HourlyEvents(16, 1.0));
        avgList.add(new HourlyEvents(17, 1.0));
        avgList.add(new HourlyEvents(18, 1.0));
        avgList.add(new HourlyEvents(19, 1.0));
        avgList.add(new HourlyEvents(20, 1.0));
        avgList.add(new HourlyEvents(21, 1.0));
        avgList.add(new HourlyEvents(22, 1.0));
        avgList.add(new HourlyEvents(23, 1.0));

        Mockito.when(a.hourlyEventCount(d, deviceId)).thenReturn(dayList);
        Mockito.when(a.hourlyEventMeanExceptDate(d, deviceId)).thenReturn(avgList);
        Mockito.when(a.hourlyBusyGrades(d, deviceId)).thenCallRealMethod();

        var res = a.hourlyBusyGrades(d, deviceId);

        for(var t : res) {
            Assertions.assertEquals(BusyScale.Normal, t.grade());
        }
    }

    @Test
    void shouldHourlyBusyGradeBeQuiteOnSmallerValues() {
        var a = Mockito.mock(EventService.class);
        var d = LocalDate.now();
        var deviceId = 1;
        var dayList = new ArrayList<HourlyEvents>();
        var avgList = new ArrayList<HourlyEvents>();

        dayList.add(new HourlyEvents(0, 1.0));
        dayList.add(new HourlyEvents(1, 1.0));
        dayList.add(new HourlyEvents(2, 1.0));
        dayList.add(new HourlyEvents(3, 1.0));
        dayList.add(new HourlyEvents(4, 1.0));
        dayList.add(new HourlyEvents(5, 1.0));
        dayList.add(new HourlyEvents(6, 1.0));
        dayList.add(new HourlyEvents(7, 1.0));
        dayList.add(new HourlyEvents(8, 1.0));
        dayList.add(new HourlyEvents(9, 1.0));
        dayList.add(new HourlyEvents(10, 1.0));
        dayList.add(new HourlyEvents(11, 1.0));
        dayList.add(new HourlyEvents(12, 1.0));
        dayList.add(new HourlyEvents(13, 1.0));
        dayList.add(new HourlyEvents(14, 1.0));
        dayList.add(new HourlyEvents(15, 1.0));
        dayList.add(new HourlyEvents(16, 1.0));
        dayList.add(new HourlyEvents(17, 1.0));
        dayList.add(new HourlyEvents(18, 1.0));
        dayList.add(new HourlyEvents(19, 1.0));
        dayList.add(new HourlyEvents(20, 1.0));
        dayList.add(new HourlyEvents(21, 1.0));
        dayList.add(new HourlyEvents(22, 1.0));
        dayList.add(new HourlyEvents(23, 1.0));

        avgList.add(new HourlyEvents(0, 2.0));
        avgList.add(new HourlyEvents(1, 1.0));
        avgList.add(new HourlyEvents(2, 1.0));
        avgList.add(new HourlyEvents(3, 1.0));
        avgList.add(new HourlyEvents(4, 1.0));
        avgList.add(new HourlyEvents(5, 1.0));
        avgList.add(new HourlyEvents(6, 1.0));
        avgList.add(new HourlyEvents(7, 1.0));
        avgList.add(new HourlyEvents(8, 1.0));
        avgList.add(new HourlyEvents(9, 1.0));
        avgList.add(new HourlyEvents(10, 1.0));
        avgList.add(new HourlyEvents(11, 1.0));
        avgList.add(new HourlyEvents(12, 1.0));
        avgList.add(new HourlyEvents(13, 1.0));
        avgList.add(new HourlyEvents(14, 1.0));
        avgList.add(new HourlyEvents(15, 1.0));
        avgList.add(new HourlyEvents(16, 1.0));
        avgList.add(new HourlyEvents(17, 1.0));
        avgList.add(new HourlyEvents(18, 1.0));
        avgList.add(new HourlyEvents(19, 1.0));
        avgList.add(new HourlyEvents(20, 1.0));
        avgList.add(new HourlyEvents(21, 1.0));
        avgList.add(new HourlyEvents(22, 1.0));
        avgList.add(new HourlyEvents(23, 1.0));

        Mockito.when(a.hourlyEventCount(d, deviceId)).thenReturn(dayList);
        Mockito.when(a.hourlyEventMeanExceptDate(d, deviceId)).thenReturn(avgList);
        Mockito.when(a.hourlyBusyGrades(d, deviceId)).thenCallRealMethod();

        var res = a.hourlyBusyGrades(d, deviceId);

        Assertions.assertEquals(BusyScale.Quite, res.getFirst().grade());
    }

    @Test
    void shouldHourlyBusyGradeBeBusyOnBiggerValues() {
        var a = Mockito.mock(EventService.class);
        var d = LocalDate.now();
        var deviceId = 1;
        var dayList = new ArrayList<HourlyEvents>();
        var avgList = new ArrayList<HourlyEvents>();

        dayList.add(new HourlyEvents(0, 2.0));
        dayList.add(new HourlyEvents(1, 1.0));
        dayList.add(new HourlyEvents(2, 1.0));
        dayList.add(new HourlyEvents(3, 1.0));
        dayList.add(new HourlyEvents(4, 1.0));
        dayList.add(new HourlyEvents(5, 1.0));
        dayList.add(new HourlyEvents(6, 1.0));
        dayList.add(new HourlyEvents(7, 1.0));
        dayList.add(new HourlyEvents(8, 1.0));
        dayList.add(new HourlyEvents(9, 1.0));
        dayList.add(new HourlyEvents(10, 1.0));
        dayList.add(new HourlyEvents(11, 1.0));
        dayList.add(new HourlyEvents(12, 1.0));
        dayList.add(new HourlyEvents(13, 1.0));
        dayList.add(new HourlyEvents(14, 1.0));
        dayList.add(new HourlyEvents(15, 1.0));
        dayList.add(new HourlyEvents(16, 1.0));
        dayList.add(new HourlyEvents(17, 1.0));
        dayList.add(new HourlyEvents(18, 1.0));
        dayList.add(new HourlyEvents(19, 1.0));
        dayList.add(new HourlyEvents(20, 1.0));
        dayList.add(new HourlyEvents(21, 1.0));
        dayList.add(new HourlyEvents(22, 1.0));
        dayList.add(new HourlyEvents(23, 1.0));

        avgList.add(new HourlyEvents(0, 1.0));
        avgList.add(new HourlyEvents(1, 1.0));
        avgList.add(new HourlyEvents(2, 1.0));
        avgList.add(new HourlyEvents(3, 1.0));
        avgList.add(new HourlyEvents(4, 1.0));
        avgList.add(new HourlyEvents(5, 1.0));
        avgList.add(new HourlyEvents(6, 1.0));
        avgList.add(new HourlyEvents(7, 1.0));
        avgList.add(new HourlyEvents(8, 1.0));
        avgList.add(new HourlyEvents(9, 1.0));
        avgList.add(new HourlyEvents(10, 1.0));
        avgList.add(new HourlyEvents(11, 1.0));
        avgList.add(new HourlyEvents(12, 1.0));
        avgList.add(new HourlyEvents(13, 1.0));
        avgList.add(new HourlyEvents(14, 1.0));
        avgList.add(new HourlyEvents(15, 1.0));
        avgList.add(new HourlyEvents(16, 1.0));
        avgList.add(new HourlyEvents(17, 1.0));
        avgList.add(new HourlyEvents(18, 1.0));
        avgList.add(new HourlyEvents(19, 1.0));
        avgList.add(new HourlyEvents(20, 1.0));
        avgList.add(new HourlyEvents(21, 1.0));
        avgList.add(new HourlyEvents(22, 1.0));
        avgList.add(new HourlyEvents(23, 1.0));

        Mockito.when(a.hourlyEventCount(d, deviceId)).thenReturn(dayList);
        Mockito.when(a.hourlyEventMeanExceptDate(d, deviceId)).thenReturn(avgList);
        Mockito.when(a.hourlyBusyGrades(d, deviceId)).thenCallRealMethod();

        var res = a.hourlyBusyGrades(d, deviceId);

        Assertions.assertEquals(BusyScale.Busy, res.getFirst().grade());
    }

    @Test
    void shouldHourlyBusyGradeWorkOnIncompleteArrays() {
        var a = Mockito.mock(EventService.class);
        var d = LocalDate.now();
        var deviceId = 1;
        var dayList = new ArrayList<HourlyEvents>();
        var avgList = new ArrayList<HourlyEvents>();

        dayList.add(new HourlyEvents(0, 2.0));

        avgList.add(new HourlyEvents(0, 1.0));


        Mockito.when(a.hourlyEventCount(d, deviceId)).thenReturn(dayList);
        Mockito.when(a.hourlyEventMeanExceptDate(d, deviceId)).thenReturn(avgList);
        Mockito.when(a.hourlyBusyGrades(d, deviceId)).thenCallRealMethod();

        var res = a.hourlyBusyGrades(d, deviceId);

        Assertions.assertEquals(BusyScale.Busy, res.getFirst().grade());
    }

    @Test
    void shouldHourlyBusyGradeOrderOfArraysIsImplicit() {
        var a = Mockito.mock(EventService.class);
        var d = LocalDate.now();
        var deviceId = 1;
        var dayList = new ArrayList<HourlyEvents>();
        var avgList = new ArrayList<HourlyEvents>();

        dayList.add(new HourlyEvents(0, 2.0));

        avgList.add(new HourlyEvents(3, 1.0));


        Mockito.when(a.hourlyEventCount(d, deviceId)).thenReturn(dayList);
        Mockito.when(a.hourlyEventMeanExceptDate(d, deviceId)).thenReturn(avgList);
        Mockito.when(a.hourlyBusyGrades(d, deviceId)).thenCallRealMethod();

        var res = a.hourlyBusyGrades(d, deviceId);

        Assertions.assertEquals(BusyScale.Busy, res.getFirst().grade());
        Assertions.assertEquals(0, res.getFirst().hour());
    }
}