package com.sep.paypal.repository;

import com.sep.paypal.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findSellerByJournalMail(String mail);
    Seller findSellerByJournalName(String name);
    Seller findSellerByJournalNameOrJournalMail(String name, String mail);
    Seller findSellerByJournalNameAndJournalMail(String name, String mail);


}
