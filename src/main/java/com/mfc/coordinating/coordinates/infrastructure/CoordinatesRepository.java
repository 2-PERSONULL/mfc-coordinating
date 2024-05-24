package com.mfc.coordinating.coordinates.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.coordinates.domain.Coordinates;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
