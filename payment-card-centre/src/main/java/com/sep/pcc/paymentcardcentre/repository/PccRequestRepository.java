package com.sep.pcc.paymentcardcentre.repository;

import com.sep.pcc.paymentcardcentre.entity.PccRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PccRequestRepository extends JpaRepository<PccRequest, Long> {
}
