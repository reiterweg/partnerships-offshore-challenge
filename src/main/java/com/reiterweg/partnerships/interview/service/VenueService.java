package com.reiterweg.partnerships.interview.service;

import com.reiterweg.partnerships.interview.service.dto.VenueDTO;

import java.util.List;

public interface VenueService {

    VenueDTO save(VenueDTO dto);

    List<VenueDTO> findAll();

}
