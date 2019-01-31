package com.reiterweg.partnerships.interview.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class VenueDTO implements Serializable {

    private Integer id;
    private String name;
    private String city;
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VenueDTO venueDTO = (VenueDTO) o;

        if (venueDTO.getId() == null || getId() == null) {
            return false;
        }

        return getId().equals(venueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}