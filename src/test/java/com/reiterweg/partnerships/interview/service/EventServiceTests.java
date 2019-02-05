package com.reiterweg.partnerships.interview.service;

import com.reiterweg.partnerships.interview.InterviewApplication;
import com.reiterweg.partnerships.interview.domain.Event;
import com.reiterweg.partnerships.interview.domain.Venue;
import com.reiterweg.partnerships.interview.service.dto.EventDTO;
import com.reiterweg.partnerships.interview.service.mapper.EventMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterviewApplication.class)
public class EventServiceTests {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private EntityManager entityManager;

    private Event event;

    @Before
    public void setup() {
        this.event = createEntity(entityManager);
    }

    public static Event createEntity(EntityManager entityManager) {
        Venue venue = VenueServiceTests.createEntity(entityManager);
        entityManager.persist(venue);

        Event event = new Event();
        event.setName(DEFAULT_NAME);
        event.setDate(DEFAULT_DATE);
        event.setVenue(venue);

        return event;
    }

    @Test
    @Transactional
    public void saveEvent() {
        EventDTO eventDTO = eventMapper.toDTO(createEntity(entityManager));
        EventDTO testEventDTO = eventService.save(eventDTO);

        assertThat(testEventDTO.getId()).isNotNull();
        assertThat(testEventDTO.getId()).isGreaterThan(0);
        assertThat(testEventDTO.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void saveEventWithExistingId() {
        event.setId(1);

        EventDTO eventDTO = eventMapper.toDTO(createEntity(entityManager));
        EventDTO testEventDTO = eventService.save(eventDTO);

        assertThat(testEventDTO.getId()).isNotNull();
        assertThat(testEventDTO.getId()).isGreaterThan(0);
        assertThat(testEventDTO.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void findAllEvents() {
        eventService.save(eventMapper.toDTO(event));

        List<EventDTO> eventListDTO = eventService.findAll();
        EventDTO testEventDTO = eventListDTO.get(eventListDTO.size() - 1);

        assertThat(testEventDTO.getName()).isEqualTo(DEFAULT_NAME);
    }

}
