package com.soa.tripmanager.repository;

import com.soa.tripmanager.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
