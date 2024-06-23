package com.mfc.coordinating.coordinates.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.coordinates.domain.Coordinates;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
	Optional<Coordinates> findByRequestId(String requestId);
	void deleteByRequestId(String requestId);
}
