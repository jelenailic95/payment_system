package com.sep.paypal.repository;

import com.sep.paypal.model.entity.JournalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalPlanRepository extends JpaRepository<JournalPlan, Long> {

    List<JournalPlan> findJournalPlansByJournal(String name);
    JournalPlan findJournalPlanByPlanId(String planId);
}
