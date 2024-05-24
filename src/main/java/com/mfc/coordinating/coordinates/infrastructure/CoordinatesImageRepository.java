package com.mfc.coordinating.coordinates.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.coordinates.domain.Coordinates;
import com.mfc.coordinating.coordinates.domain.CoordinatesImage;

public interface CoordinatesImageRepository extends JpaRepository<CoordinatesImage, Long> {
	void deleteByCoordinates(Coordinates coordinates);
}
