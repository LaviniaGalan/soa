package com.soa.tripmanager.service;

import com.soa.tripmanager.model.Booking;
import com.soa.tripmanager.model.Trip;

import java.util.List;

public interface TripService {
    List<Trip> getAllTrips();
    List<Booking> getBookingsByUser(Long userId);
    Booking makeBooking(Booking booking);
    Trip findTripById(Long tripId);
    List<Booking> findBookingsByTripId(Long tripId);
}
