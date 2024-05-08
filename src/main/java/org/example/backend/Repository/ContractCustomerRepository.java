package org.example.backend.Repository;

import org.example.backend.Model.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractCustomerRepository extends JpaRepository<ContractCustomer, Long> {

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%'))")
    List<ContractCustomer> search(String search);

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%')) ORDER BY cc.companyName DESC")
    List<ContractCustomer> searchDescCompany(String search);

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%')) ORDER BY cc.companyName ASC")
    List<ContractCustomer> searchAscCompany(String search);

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%')) ORDER BY cc.contactName DESC")
    List<ContractCustomer> searchDescContact(String search);

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%')) ORDER BY cc.contactName ASC")
    List<ContractCustomer> searchAscContact(String search);

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%')) ORDER BY cc.country DESC")
    List<ContractCustomer> searchDescCountry(String search);

    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :search, '%')) ORDER BY cc.country ASC")
    List<ContractCustomer> searchAscCountry(String search);

    @Query("SELECT cc FROM ContractCustomer cc ORDER BY cc.companyName DESC")
    List<ContractCustomer> descCompany();

    @Query("SELECT cc FROM ContractCustomer cc ORDER BY cc.companyName ASC")
    List<ContractCustomer> ascCompany();

    @Query("SELECT cc FROM ContractCustomer cc ORDER BY cc.contactName DESC")
    List<ContractCustomer> descContact();

    @Query("SELECT cc FROM ContractCustomer cc ORDER BY cc.contactName ASC")
    List<ContractCustomer> ascContact();

    @Query("SELECT cc FROM ContractCustomer cc ORDER BY cc.country DESC")
    List<ContractCustomer> descCountry();

    @Query("SELECT cc FROM ContractCustomer cc ORDER BY cc.country ASC")
    List<ContractCustomer> ascCountry();
}
