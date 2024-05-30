package com.mfc.coordinating.requests.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.coordinating.requests.domain.RequestHistory;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {

	@Query("SELECT r.requestId, r.userId, r.partnerId, r.deadline, r.status FROM RequestHistory r WHERE r.partnerId = :partnerId")
	Page<Object[]> findByPartnerId(@Param("partnerId") String partnerId, Pageable pageable);

	@Query("SELECT r.requestId, r.userId, r.partnerId, r.deadline, r.status FROM RequestHistory r WHERE r.userId = :userId")
	Page<RequestHistory> findByUserId(@Param("userId") String userId, Pageable pageable);

}
