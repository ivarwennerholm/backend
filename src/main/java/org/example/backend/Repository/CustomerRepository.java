package org.example.backend1.Repository;

import org.example.backend1.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByName(String name);
}
