package org.example.backend.Repository;

import org.example.backend.Model.Customer;
import org.example.backend.Model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

     @Query("SELECT et.markup FROM EmailTemplate et ORDER BY et.id DESC LIMIT 1")
     public String getLatestEmailTemplate();
}


