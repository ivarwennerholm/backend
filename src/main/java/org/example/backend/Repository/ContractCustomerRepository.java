package org.example.backend.Repository;

import org.example.backend.Model.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractCustomerRepository extends JpaRepository<ContractCustomer, Long> {

    // For search term without sorting
    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(cc.contactName) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(cc.country) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<ContractCustomer> search(@Param("searchTerm") String searchTerm);

    // For descending order with search term
    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(cc.contactName) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(cc.country) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "ORDER BY " +
            "CASE :column " +
            "WHEN 'company' THEN cc.companyName " +
            "WHEN 'contact' THEN cc.contactName " +
            "WHEN 'country' THEN cc.country " +
            "END DESC")
    List<ContractCustomer> searchAndSortDesc(@Param("searchTerm") String searchTerm, @Param("column") String column);

    // For ascending order with search term
    @Query("SELECT cc FROM ContractCustomer cc WHERE LOWER(cc.companyName) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(cc.contactName) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(cc.country) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "ORDER BY " +
            "CASE :column " +
            "WHEN 'company' THEN cc.companyName " +
            "WHEN 'contact' THEN cc.contactName " +
            "WHEN 'country' THEN cc.country " +
            "END ASC")
    List<ContractCustomer> searchAndSortAsc(@Param("searchTerm") String searchTerm, @Param("column") String column);

    // For descending order without search term
    @Query("SELECT cc FROM ContractCustomer cc ORDER BY " +
            "CASE :column " +
            "WHEN 'company' THEN cc.companyName " +
            "WHEN 'contact' THEN cc.contactName " +
            "WHEN 'country' THEN cc.country " +
            "END DESC")
    List<ContractCustomer> sortDesc(@Param("column") String column);

    // For ascending order without search term
    @Query("SELECT cc FROM ContractCustomer cc ORDER BY " +
            "CASE :column " +
            "WHEN 'company' THEN cc.companyName " +
            "WHEN 'contact' THEN cc.contactName " +
            "WHEN 'country' THEN cc.country " +
            "END ASC")
    List<ContractCustomer> sortAsc(@Param("column") String column);

}
