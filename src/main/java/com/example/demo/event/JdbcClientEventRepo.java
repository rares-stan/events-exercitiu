package com.example.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class JdbcClientEventRepo {
    private static final Logger log = LoggerFactory.getLogger(JdbcClientEventRepo.class);
    private final JdbcClient jdbcClient;

    public JdbcClientEventRepo(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Event> findAll() {
        return jdbcClient.sql("select * from event")
                .query(Event.class)
                .list();
    }

    public void create(Event event) {
        var updated = jdbcClient.sql("INSERT into event(id, type, created_on, device_id, user_id) VALUES(?,?,?,?,?)")
                .params(List.of(event.id(), event.type().toString(), event.createdOn(), event.deviceId(), event.userId()))
                .update();

        Assert.state(updated==1, "Failed to create event " + event.type());
    }
}
