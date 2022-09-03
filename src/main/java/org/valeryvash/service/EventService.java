package org.valeryvash.service;

import org.valeryvash.dto.EventPostResponseDto;
import org.valeryvash.model.Event;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNotPositive;
import static org.valeryvash.util.ServiceChecker.throwIfNull;

public class EventService {
    private EventRepository eventRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventPostResponseDto get(Long id) {
        throwIfNull(id);
        throwIfNotPositive(id);

        Event event = eventRepository.get(id);

        EventPostResponseDto eventPostResponseDto = new EventPostResponseDto(event);

        return eventPostResponseDto;
    }


    public List<EventPostResponseDto> getAll() {
        List<EventPostResponseDto> responseDtos = new ArrayList<>();

        List<Event> events = eventRepository.getAll();

        for (Event event : events) {
            responseDtos.add(new EventPostResponseDto(event));
        }

        return responseDtos;
    }
}
