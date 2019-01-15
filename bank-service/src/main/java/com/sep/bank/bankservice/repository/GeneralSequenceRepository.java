package com.sep.bank.bankservice.repository;

import com.sep.bank.bankservice.entity.GeneralSequenceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralSequenceRepository extends JpaRepository<GeneralSequenceNumber, Long> {
}
