package org.valeryvash.repository;

import org.valeryvash.model.Event;

import java.util.List;

public interface EventRepository extends GenericRepository<Long, Event> {

    @Override
    Event add(Event entity);

    @Override
    Event get(Long entityId);

    @Override
    Event update(Event entity);

    @Override
    Event remove(Long entityId);

    @Override
    List<Event> getAll();
}
