package com.mfc.coordinating.requests.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.coordinating.requests.domain.MyImage;

public interface MyImageRepository extends JpaRepository<MyImage, Long> {

	@Query("select r from MyImage r where r.requests.requestId = :requestId")
	List<MyImage> findByRequestId(@Param("requestId") Long requestId);

	@Query("select r from MyImage r where r.requests.requestId = :requestId")
	void deleteByRequestId(@Param("requestId") Long requestId);
}
