package com.soa.tripmanager.controller;

import com.soa.tripmanager.intercomm.UserClient;
import com.soa.tripmanager.model.Booking;
import com.soa.tripmanager.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TripController {
    @Autowired
    private TripService tripService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private UserClient userClient;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/public/instances")
    public ResponseEntity<?> getInstances() {
        return ResponseEntity.ok(discoveryClient.getInstances(serviceId));
    }

    @GetMapping("/service/private/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }

    @GetMapping("/service/private/user/{userId}")
    public ResponseEntity<?> findBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(tripService.getBookingsByUser(userId));
    }

    @GetMapping("/service/public/allTrips")
    public ResponseEntity<?> findAllBooks() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @PostMapping("/service/private/makeBooking")
    public ResponseEntity<?> saveOrder(@RequestBody Booking booking) {
        booking.setBookingDate(LocalDateTime.now());
        booking.setTrip(tripService.findTripById(booking.getTrip().getId()));
        return new ResponseEntity<>(tripService.makeBooking(booking), HttpStatus.CREATED);
    }

    @GetMapping("/service/public/tripClients/{tripId}")
    public ResponseEntity<?> findClientsOfTrip(@PathVariable Long tripId) {
        List<Booking> bookings = tripService.findBookingsByTripId(tripId);
        if (CollectionUtils.isEmpty(bookings)) {
            return ResponseEntity.notFound().build();
        }
        List<Long> userIdList = bookings.parallelStream().map(Booking::getUserId).collect(Collectors.toList());
        List<String> clients = userClient.getUserEmails(userIdList);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/service/public/{tripId}")
    public ResponseEntity<?> findTripById(@PathVariable Long tripId) {
        return ResponseEntity.ok(tripService.findTripById(tripId));
    }
}
