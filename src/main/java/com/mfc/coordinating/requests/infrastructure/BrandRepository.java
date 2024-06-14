// package com.mfc.coordinating.requests.infrastructure;
//
// import java.util.List;
//
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
//
// import com.mfc.coordinating.requests.domain.Brand;
//
// public interface BrandRepository extends JpaRepository<Brand, Long> {
// 	@Query("select r from Brand r where r.requests.requestId = :requestId")
// 	void deleteByRequestId(@Param("requestId") Long requestId);
//
// 	@Query("select r from Brand r where r.requests.requestId = :requestId")
// 	List<Brand> findByRequestId(@Param("requestId") Long requestId);
// }
