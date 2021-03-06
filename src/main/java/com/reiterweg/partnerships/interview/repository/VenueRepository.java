package com.reiterweg.partnerships.interview.repository;

import com.reiterweg.partnerships.interview.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
