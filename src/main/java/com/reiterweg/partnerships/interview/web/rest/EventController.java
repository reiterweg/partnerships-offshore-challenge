package com.reiterweg.partnerships.interview.web.rest;

import com.reiterweg.partnerships.interview.service.EventService;
import com.reiterweg.partnerships.interview.service.dto.EventDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    public ResponseEntity<EventDTO> postEvent(@RequestBody EventDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        EventDTO result = eventService.save(dto);
        return ResponseEntity.created(new URI("/api/events/" + result.getId())).body(result);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

}
