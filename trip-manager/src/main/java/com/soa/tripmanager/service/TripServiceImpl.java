package com.soa.tripmanager.service;

import com.soa.tripmanager.model.Booking;
import com.soa.tripmanager.model.Trip;
import com.soa.tripmanager.repository.BookingRepository;
import com.soa.tripmanager.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findAllByUserId(userId);
    }

    @Override
    public Booking makeBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Trip findTripById(Long tripId) {
        return tripRepository.findById(tripId).orElse(null);
    }

    @Override
    public List<Booking> findBookingsByTripId(Long tripId) {
        return bookingRepository.findAllByTripId(tripId);
    }
}
