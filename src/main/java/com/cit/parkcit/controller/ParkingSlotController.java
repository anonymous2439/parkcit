package com.cit.parkcit.controller;

import com.cit.parkcit.model.ParkingSlot;
import com.cit.parkcit.model.User;
import com.cit.parkcit.repository.ParkingLotRepository;
import com.cit.parkcit.repository.ParkingSlotRepository;
import com.cit.parkcit.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parking-slots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all parking slots
    @GetMapping
    public List<ParkingSlot> getAllParkingSlots() {
        return (List<ParkingSlot>) parkingSlotRepository.findAll();
    }

    // Get a specific parking slot by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getParkingSlotById(@PathVariable("id") int id) {
        Optional<ParkingSlot> optionalParkingSlot = parkingSlotRepository.findById(id);
        return optionalParkingSlot.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a new parking slot
    @PostMapping
    public ResponseEntity<ParkingSlot> createParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        // Ensure the associated parkingLot exists
        if (parkingSlot.getParkingLot() != null && parkingLotRepository.existsById(parkingSlot.getParkingLot().getParkingLotID())) {
            ParkingSlot savedParkingSlot = parkingSlotRepository.save(parkingSlot);
            return ResponseEntity.ok(savedParkingSlot);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update a parking slot
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateParkingSlot(@PathVariable("id") int id, @RequestBody ParkingSlot updatedParkingSlot) {
        Optional<ParkingSlot> optionalParkingSlot = parkingSlotRepository.findById(id);

        if (optionalParkingSlot.isPresent()) {
            ParkingSlot existingParkingSlot = optionalParkingSlot.get();
            existingParkingSlot.setParkingLot(updatedParkingSlot.getParkingLot());
            existingParkingSlot.setEmployee(updatedParkingSlot.isEmployee());
            existingParkingSlot.setAvailable(updatedParkingSlot.isAvailable());

            ParkingSlot savedParkingSlot = parkingSlotRepository.save(existingParkingSlot);
            return ResponseEntity.ok(savedParkingSlot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Toggle parking slot availability
    @PutMapping("/{slot_id}/user/{user_id}")
    public ResponseEntity<ParkingSlot> toggleParkingSlotAvailability(
            @PathVariable("slot_id") int slot_id,
            @PathVariable("user_id") int user_id) {

        Optional<User> optionalUser = userRepository.findById(user_id);
        Optional<ParkingSlot> optionalParkingSlot = parkingSlotRepository.findById(slot_id);

        if (optionalParkingSlot.isPresent() && optionalUser.isPresent()) {
            ParkingSlot existingParkingSlot = optionalParkingSlot.get();
            User existingUser = optionalUser.get();

            /* 
                can only toggle the slot if the slot is reserved for employee and the current user is an employee
                or if the slot is not reserved for employee for all types of user types
            */
            if (("employee".equals(existingUser.getUserType().getUserType()) && existingParkingSlot.isEmployee())
                    || !existingParkingSlot.isEmployee()) {
                existingParkingSlot.setAvailable(!existingParkingSlot.isAvailable());
                ParkingSlot savedParkingSlot = parkingSlotRepository.save(existingParkingSlot);
                return ResponseEntity.ok(savedParkingSlot);
            } 
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a parking slot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSlot(@PathVariable("id") int id) {
        if (parkingSlotRepository.existsById(id)) {
            parkingSlotRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
