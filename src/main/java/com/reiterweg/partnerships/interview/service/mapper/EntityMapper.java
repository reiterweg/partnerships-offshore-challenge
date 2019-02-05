package com.reiterweg.partnerships.interview.service.mapper;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDTO(E entity);

}
