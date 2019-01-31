package com.reiterweg.partnerships.interview.service;

import com.reiterweg.partnerships.interview.service.dto.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO save(EventDTO dto);

    List<EventDTO> findAll();

}
