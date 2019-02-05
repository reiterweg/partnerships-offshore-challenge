package com.reiterweg.partnerships.interview.service;

import com.reiterweg.partnerships.interview.InterviewApplication;
import com.reiterweg.partnerships.interview.domain.Venue;
import com.reiterweg.partnerships.interview.service.dto.VenueDTO;
import com.reiterweg.partnerships.interview.service.mapper.VenueMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterviewApplication.class)
public class VenueServiceTests {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String DEFAULT_STATE = "AAAAAAAAAA";

    @Autowired
    private VenueMapper venueMapper;

    @Autowired
    private VenueService venueService;

    @Autowired
    private EntityManager entityManager;

    private Venue venue;

    @Before
    public void setup() {
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
    public void saveVenue() {
        VenueDTO testVenueDTO = venueService.save(venueMapper.toDTO(venue));

        assertThat(testVenueDTO.getId()).isNotNull();
        assertThat(testVenueDTO.getId()).isGreaterThan(0);
        assertThat(testVenueDTO.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVenueDTO.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testVenueDTO.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void saveVenueWithExistingId() {
        venue.setId(1);
        VenueDTO testVenueDTO = venueService.save(venueMapper.toDTO(venue));

        assertThat(testVenueDTO.getId()).isEqualTo(1);
        assertThat(testVenueDTO.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVenueDTO.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testVenueDTO.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void findAllVenues() {
        venueService.save(venueMapper.toDTO(venue));

        List<VenueDTO> venueListDTO = venueService.findAll();
        VenueDTO testVenueDTO = venueListDTO.get(venueListDTO.size() - 1);

        assertThat(testVenueDTO.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVenueDTO.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testVenueDTO.getState()).isEqualTo(DEFAULT_STATE);
    }

}
