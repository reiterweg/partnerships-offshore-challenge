package com.reiterweg.partnerships.interview.service.impl;

import com.reiterweg.partnerships.interview.domain.Event;
import com.reiterweg.partnerships.interview.repository.EventRepository;
import com.reiterweg.partnerships.interview.service.EventService;
import com.reiterweg.partnerships.interview.service.dto.EventDTO;
import com.reiterweg.partnerships.interview.service.mapper.EventMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public EventDTO save(EventDTO dto) {
        Event event = eventMapper.toEntity(dto);
        event = eventRepository.save(event);
        return eventMapper.toDTO(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream()
            .map(eventMapper::toDTO)
            .collect(Collectors.toList());
    }

}
