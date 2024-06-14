// package com.mfc.coordinating.requests.infrastructure;
//
// import java.util.Optional;
//
// import org.bson.types.ObjectId;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
//
// import com.mfc.coordinating.requests.domain.RequestHistory;
//
// public interface RequestHistoryRepository extends MongoRepository<RequestHistory, String> {
//
// 	@Query("{ '_id': ?0 }")
// 	Optional<RequestHistory> findById(ObjectId id);
//
// 	@Query("{ 'partnerId': ?0 }")
// 	Page<RequestHistory> findByPartnerId(String partnerId, Pageable pageable);
//
// 	@Query("{ 'userId': ?0 }")
// 	Page<RequestHistory> findByUserId(String userId, Pageable pageable);
//
// }