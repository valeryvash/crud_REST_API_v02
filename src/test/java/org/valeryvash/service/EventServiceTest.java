package org.valeryvash.service;

import org.junit.jupiter.api.Test;
import org.valeryvash.dto.FilePostResponseDto;
import org.valeryvash.repository.EventRepository;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EventServiceTest {

    private EventService eventService = null;

    public EventServiceTest() {
        this.eventService = new EventService(mock(EventRepository.class));
    }

    @Test
    void passNullToMethods() {
        assertThrows(
                IllegalArgumentException.class,
                () -> eventService.get(null)
        );
    }

    @Test
    void passNotPositiveId() {

        LongStream.range(-10000L, 1).forEach(
                id -> {
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> eventService.get(id)
                    );
                });
    }
}