package com.boum.frecipe.repository.fridge;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boum.frecipe.domain.fridge.Fridge;

public interface FridgeRepository extends JpaRepository<Fridge, Long>{

	Optional<Fridge> findByFridgeNo(Long fridgeNo);
}
