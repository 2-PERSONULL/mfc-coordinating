package com.mfc.coordinating.requests.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.coordinating.requests.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("select r from Category r where r.requests.requestId = :requestId")
	void deleteByRequestId(@Param("requestId") Long requestId);

	@Query("select r from Category r where r.requests.requestId = :requestId")
	List<Category> findByRequestId(@Param("requestId") Long requestId);
}
