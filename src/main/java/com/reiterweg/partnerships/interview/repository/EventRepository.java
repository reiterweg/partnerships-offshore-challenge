package com.reiterweg.partnerships.interview.repository;

import com.reiterweg.partnerships.interview.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
