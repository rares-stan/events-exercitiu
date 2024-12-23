package com.example.demo.event;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepo extends ListCrudRepository<Event, Integer> {

    @Query("select extract(hour from e.created_on) as hour, count(e.id) as count from event e where e.device_id = :deviceId and e.created_on::Date =:rDate::Date group by extract(hour from e.created_on) order by hour")
    List<HourlyEvents> eventsByHourForDate(LocalDate rDate, Integer deviceId);

    @Query("select extract(hour from ce.dh) as hour, avg(ce.c) as count from (select date_trunc('hour', e.created_on) as dh, count(e.id) as c from event e where e.device_id = :deviceId and e.created_on::date != :rDate::date group by date_trunc('hour', e.created_on)) as ce group by extract(hour from ce.dh) order by 1")
    List<HourlyEvents> eventsByHourExceptDate(LocalDate rDate, Integer deviceId);
}
