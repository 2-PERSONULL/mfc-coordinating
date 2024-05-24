package com.mfc.coordinating.reviews.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.reviews.entity.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
