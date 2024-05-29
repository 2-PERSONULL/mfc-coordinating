package com.mfc.coordinating.coordinates.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.coordinates.domain.CoordinatesImage;

public interface CoordinatesImageRepository extends JpaRepository<CoordinatesImage, Long> {
	List<CoordinatesImage> findByCoordinatesId(Long coordinatesId);
	void deleteByCoordinatesId(Long coordinatesId);
}