package com.mfc.coordinating.reviews.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.coordinating.reviews.entity.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Long>, ReviewsRepositoryCustom {
	@Query("SELECT COUNT(r.id) FROM Reviews r WHERE r.partnerId = :partnerId")
	Integer getReviewCountByPartnerId(@Param("partnerId") String partnerId);
}
