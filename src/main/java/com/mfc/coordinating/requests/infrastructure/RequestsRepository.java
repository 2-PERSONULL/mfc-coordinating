package com.mfc.coordinating.requests.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mfc.coordinating.requests.domain.Requests;
import com.mfc.coordinating.requests.dto.res.MyRequestListResponse;

@Repository
public interface RequestsRepository extends JpaRepository<Requests, Long> {

	@Query("SELECT new com.mfc.coordinating.requests.dto.res.MyRequestListResponse(r.requestId, r.title) FROM Requests r WHERE r.userId = :userId")
	Page<MyRequestListResponse> findByUserId(@Param("userId") String userId, Pageable pageable);


	Optional<Requests> findByRequestId(Long requestId);

	@Query("SELECT r FROM Requests r WHERE r.requestId = :requestId AND r.userId = :userId")
	Optional<Requests> findByRequestIdAndUserId(@Param("requestId") Long requestId,@Param("userId") String userId);

	void deleteByRequestId(Long requestId);
}
