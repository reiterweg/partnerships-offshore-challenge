package com.reiterweg.partnerships.interview.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "venues")
public class Venue implements Serializable {

    @Id
    @GenericGenerator(
            name = "venueGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "venues_id_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "venueGenerator")
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "city")
    public String city;

    @Column(name = "state")
    public String state;

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

        Venue venue = (Venue) o;

        if (venue.getId()  == null || getId() == null) {
            return false;
        }

        return getId().equals(venue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
