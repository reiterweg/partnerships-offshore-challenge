package com.reiterweg.partnerships.interview.service.impl;

import com.reiterweg.partnerships.interview.domain.Venue;
import com.reiterweg.partnerships.interview.repository.VenueRepository;
import com.reiterweg.partnerships.interview.service.VenueService;
import com.reiterweg.partnerships.interview.service.dto.VenueDTO;
import com.reiterweg.partnerships.interview.service.mapper.VenueMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VenueServiceImpl implements VenueService {

    private VenueRepository venueRepository;
    private VenueMapper venueMapper;

    public VenueServiceImpl(VenueRepository venueRepository, VenueMapper venueMapper) {
        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
    }

    @Override
    public VenueDTO save(VenueDTO dto) {
        Venue venue = venueMapper.toEntity(dto);
        venue = venueRepository.save(venue);
        return venueMapper.toDTO(venue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VenueDTO> findAll() {
        return venueRepository.findAll().stream()
            .map(venueMapper::toDTO)
            .collect(Collectors.toList());
    }

}
