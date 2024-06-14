// package com.mfc.coordinating.requests.infrastructure;
//
// import java.util.List;
//
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
//
// import com.mfc.coordinating.requests.domain.ReferenceImage;
//
// public interface ReferenceImageRepository extends JpaRepository<ReferenceImage, Long> {
//
// 	@Query("select r from ReferenceImage r where r.requests.requestId = :requestId")
// 	List<ReferenceImage> findByRequestId(@Param("requestId") Long requestId);
//
// 	@Query("select r from ReferenceImage r where r.requests.requestId = :requestId")
// 	void deleteByRequestId(@Param("requestId") Long requestId);
// }
