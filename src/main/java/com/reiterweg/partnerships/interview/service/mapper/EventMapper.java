package com.reiterweg.partnerships.interview.service.mapper;

import com.reiterweg.partnerships.interview.domain.Event;
import com.reiterweg.partnerships.interview.service.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { VenueMapper.class })
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mappings({
        @Mapping(source = "venue.id", target = "venueId"),
        @Mapping(source = "venue.name", target = "venueName"),
        @Mapping(source = "venue.city", target = "venueCity"),
        @Mapping(source = "venue.state", target = "venueState")
    })
    EventDTO toDTO(Event entity);

    @Mapping(source = "venueId", target = "venue")
    Event toEntity(EventDTO dto);

    default Event fromId(Integer id) {
        if (id == null) {
            return null;
        }

        Event entity = new Event();
        entity.setId(id);
        return entity;
    }

}
