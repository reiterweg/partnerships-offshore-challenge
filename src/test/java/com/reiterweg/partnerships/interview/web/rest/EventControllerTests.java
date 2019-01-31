package com.reiterweg.partnerships.interview.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reiterweg.partnerships.interview.InterviewApplication;
import com.reiterweg.partnerships.interview.domain.Event;
import com.reiterweg.partnerships.interview.domain.Venue;
import com.reiterweg.partnerships.interview.service.EventService;
import com.reiterweg.partnerships.interview.service.dto.EventDTO;
import com.reiterweg.partnerships.interview.service.mapper.EventMapper;
import com.reiterweg.partnerships.interview.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterviewApplication.class)
public class EventControllerTests {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private EntityManager entityManager;

    private MockMvc restEventMockMvc;
    private ObjectMapper objectMapper;
    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventController eventController = new EventController(eventService);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public static Event createEntity(EntityManager entityManager) {
        Venue venue = VenueControllerTests.createEntity(entityManager);
        entityManager.persist(venue);
        entityManager.flush();

        Event event = new Event();
        event.setName(DEFAULT_NAME);
        event.setDate(DEFAULT_DATE);
        event.setVenue(venue);

        return event;
    }

    @Before
    public void initTest() {
        event = createEntity(entityManager);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        EventDTO eventDTO = eventMapper.toDTO(event);
        restEventMockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(eventDTO)))
            .andExpect(status().isCreated());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvent.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        event.setId(1);
        EventDTO eventDTO = eventMapper.toDTO(event);

        restEventMockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        eventRepository.saveAndFlush(event);

        restEventMockMvc.perform(get("/api/events"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

}
