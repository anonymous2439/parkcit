package com.cit.parkcit.repository;

import org.springframework.data.repository.CrudRepository;

import com.cit.parkcit.model.ParkingLot;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer> {

}