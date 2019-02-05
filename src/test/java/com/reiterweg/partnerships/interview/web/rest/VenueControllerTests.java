package com.reiterweg.partnerships.interview.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reiterweg.partnerships.interview.InterviewApplication;
import com.reiterweg.partnerships.interview.domain.Venue;
import com.reiterweg.partnerships.interview.repository.VenueRepository;
import com.reiterweg.partnerships.interview.service.VenueService;
import com.reiterweg.partnerships.interview.service.dto.VenueDTO;
import com.reiterweg.partnerships.interview.service.mapper.VenueMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterviewApplication.class)
public class VenueControllerTests {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String DEFAULT_STATE = "AAAAAAAAAA";

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private VenueMapper venueMapper;

    @Autowired
    private VenueService venueService;

    @Autowired
    private EntityManager entityManager;

    private MockMvc restVenueMockMvc;
    private ObjectMapper objectMapper;
    private Venue venue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VenueController venueController = new VenueController(venueService);
        this.restVenueMockMvc = MockMvcBuilders.standaloneSetup(venueController).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.venue = createEntity(entityManager);
    }

    public static Venue createEntity(EntityManager entityManager) {
        Venue venue = new Venue();
        venue.setName(DEFAULT_NAME);
        venue.setCity(DEFAULT_CITY);
        venue.setState(DEFAULT_STATE);

        return venue;
    }

    @Test
    @Transactional
    public void createVenue() throws Exception {
        int databaseSizeBeforeCreate = venueRepository.findAll().size();

        VenueDTO venueDTO = venueMapper.toDTO(venue);
        restVenueMockMvc.perform(post("/api/venues")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(venueDTO)))
            .andExpect(status().isCreated());

        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeCreate + 1);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVenue.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testVenue.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createVenueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = venueRepository.findAll().size();

        venue.setId(1);
        VenueDTO venueDTO = venueMapper.toDTO(venue);

        restVenueMockMvc.perform(post("/api/venues")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(venueDTO)))
            .andExpect(status().isBadRequest());

        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVenues() throws Exception {
        venueRepository.saveAndFlush(venue);

        restVenueMockMvc.perform(get("/api/venues"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }

}
