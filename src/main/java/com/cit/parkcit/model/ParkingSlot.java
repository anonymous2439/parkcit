package com.cit.parkcit.model;

import jakarta.persistence.*;

@Entity
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parkingSlotID;

    @ManyToOne
    @JoinColumn(name = "parkingLotID", nullable = false)
    private ParkingLot parkingLot;

    private boolean isEmployee;
    private boolean isAvailable;

    // Constructors, getters, and setters

    public ParkingSlot() {
    }

    public ParkingSlot(ParkingLot parkingLot, boolean isEmployee, boolean isAvailable) {
        this.parkingLot = parkingLot;
        this.isEmployee = isEmployee;
        this.isAvailable = isAvailable;
    }

    // Getters and setters

    public int getParkingSlotID() {
        return parkingSlotID;
    }

    public void setParkingSlotID(int parkingSlotID) {
        this.parkingSlotID = parkingSlotID;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
