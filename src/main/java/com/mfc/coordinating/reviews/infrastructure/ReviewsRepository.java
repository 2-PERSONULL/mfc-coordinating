package com.mfc.coordinating.reviews.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.reviews.entity.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Long>, ReviewsRepositoryCustom {

}
