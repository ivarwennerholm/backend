package org.example.backend.Repository;

import org.example.backend.Model.ContractCustomer;
import org.example.backend.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractCustomerRepository extends JpaRepository<ContractCustomer, Long> {

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :searchTerm, '%'))")
    //@Query("SELECT cc FROM ContractCustomer cc WHERE cc.companyName LIKE %:searchTerm%")
    List<ContractCustomer> findByCompanyNameContaining(String searchTerm);

}
