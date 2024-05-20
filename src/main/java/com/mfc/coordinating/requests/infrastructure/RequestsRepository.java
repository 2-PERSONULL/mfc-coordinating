package com.mfc.coordinating.requests.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfc.coordinating.requests.domain.Requests;

@Repository
public interface RequestsRepository extends JpaRepository<Requests, Long> {
}
