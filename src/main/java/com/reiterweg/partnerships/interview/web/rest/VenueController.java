package com.reiterweg.partnerships.interview.web.rest;

import com.reiterweg.partnerships.interview.service.VenueService;
import com.reiterweg.partnerships.interview.service.dto.VenueDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VenueController {

    private VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping("/venues")
    public ResponseEntity<VenueDTO> postVenue(@RequestBody VenueDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        VenueDTO result = venueService.save(dto);
        return ResponseEntity.created(new URI("/api/venues/" + result.getId())).body(result);
    }

    @GetMapping("/venues")
    public ResponseEntity<List<VenueDTO>> getVenues() {
        return ResponseEntity.ok(venueService.findAll());
    }

}
