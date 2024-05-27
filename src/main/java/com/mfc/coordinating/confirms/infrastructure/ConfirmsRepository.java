package com.mfc.coordinating.confirms.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.coordinating.confirms.domain.Confirms;

public interface ConfirmsRepository extends JpaRepository<Confirms, Long> {
}
