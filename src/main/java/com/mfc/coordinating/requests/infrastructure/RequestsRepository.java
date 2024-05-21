package com.mfc.coordinating.requests.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mfc.coordinating.requests.domain.Requests;

@Repository
public interface RequestsRepository extends JpaRepository<Requests, Long> {

	@Query("SELECT r.requestId, r.title, r.description, r.deadline FROM Requests r WHERE r.userId = :userId")
	Page<Object[]> findByUserId(@Param("userId") String userId, Pageable pageable);

	Optional<Requests> findByRequestId(Long requestId);

	@Query("SELECT r FROM Requests r WHERE r.requestId = :requestId AND r.userId = :userId")
	Optional<Requests> findByRequestIdAndUserId(@Param("requestId") Long requestId,@Param("userId") String userId);

	void deleteByRequestId(Long requestId);

	@Query("SELECT r.requestId, r.title, r.description, r.deadline FROM Requests r WHERE r.partnerId = :partnerId")
	Page<Object[]> findByPartnerId(@Param("partnerId") String partnerId, Pageable pageable);
}
