package com.sep.paypal.repository;

import com.sep.paypal.model.entity.JournalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalPlanRepository extends JpaRepository<JournalPlan, Long> {

    JournalPlan findJournalPlanByJournal(String name);
}
