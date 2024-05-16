package org.example.backend.Repository;

import org.example.backend.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByName(String name);

    @Query("SELECT c FROM Customer c WHERE c.name = :name AND c.phone = :phone AND c.email = :email")
    public Optional<Customer> getCustomerByNamePhoneAndEmail(String name, String phone, String email);

    @Query("SELECT c FROM Customer c ORDER BY c.id DESC LIMIT 1")
    public Customer getLastCustomer();
}
