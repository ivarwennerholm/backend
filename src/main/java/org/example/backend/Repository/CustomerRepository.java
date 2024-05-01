package org.example.backend.Repository;

import org.example.backend.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByName(String name);

    @Query("SELECT c FROM Customer c WHERE c.name = :name AND c.phone = :phone")
    public Customer getCustomerByNameAndPhone(String name, String phone);

}
