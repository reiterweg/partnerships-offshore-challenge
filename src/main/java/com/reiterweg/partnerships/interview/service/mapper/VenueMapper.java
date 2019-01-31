package com.reiterweg.partnerships.interview.service.mapper;

import com.reiterweg.partnerships.interview.domain.Venue;
import com.reiterweg.partnerships.interview.service.dto.VenueDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueMapper extends EntityMapper<VenueDTO, Venue> {

    default Venue fromId(Integer id) {
        if (id == null) {
            return null;
        }

        Venue entity = new Venue();
        entity.setId(id);
        return entity;
    }

}
