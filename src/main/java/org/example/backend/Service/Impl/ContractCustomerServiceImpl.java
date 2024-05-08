package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.ContractCustomerDto;
import org.example.backend.Model.ContractCustomer;
import org.example.backend.Repository.ContractCustomerRepository;
import org.example.backend.Service.ContractCustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {
    private final ContractCustomerRepository repo;

    @Override
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer cc) {
        return ContractCustomerDto.
                builder().
                id(cc.id).
                customerId(cc.customerId).
                companyName(cc.companyName).
                contactName(cc.contactName).
                contactTitle(cc.contactTitle).
                streetAddress(cc.streetAddress).
                city(cc.city).
                postalCode(cc.postalCode).
                country(cc.country).
                phone(cc.phone).
                fax(cc.fax).
                build();
    }

    @Override
    public List<ContractCustomerDto> getAll() {
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                toList();
    }

    @Override
    public Optional<ContractCustomerDto> getById(Long id) {
        return repo.
                findAll().
                stream().
                map(this::contractCustomerToContractCustomerDto).
                filter(cc -> Objects.equals(cc.id, id)).
                findFirst();
    }

    @Override
    public List<ContractCustomerDto> getFilteredList(String search, String sort, String cat) {
        List<ContractCustomer> list = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            if (sort == null) {
                list = repo.search(search);
            } else {
                if (sort.equals("desc")) {
                    if (cat.equals("company")) {
                        list = repo.searchDescCompany(search);
                    } else if (cat.equals("contact")) {
                        list = repo.searchDescContact(search);
                    } else if (cat.equals("country")) {
                        list = repo.searchDescCountry(search);
                    }
                } else if (sort.equals("asc")) {
                    if (cat.equals("company")) {
                        list = repo.searchAscCompany(search);
                    } else if (cat.equals("contact")) {
                        list = repo.searchAscContact(search);
                    } else if (cat.equals("country")) {
                        list = repo.searchAscCountry(search);
                    }
                }
            }
        } else {
            if (sort == null) {
                list = repo.findAll();
            } else {
                if (sort.equals("desc")) {
                    if (cat.equals("company")) {
                        list = repo.descCompany();
                    } else if (cat.equals("contact")) {
                        list = repo.descContact();
                    } else if (cat.equals("country")) {
                        list = repo.descCountry();
                    }
                } else if (sort.equals("asc")) {
                    if (cat.equals("company")) {
                        list = repo.ascCompany();
                    } else if (cat.equals("contact")) {
                        list = repo.ascContact();
                    } else if (cat.equals("country")) {
                        list = repo.ascCountry();
                    }
                }
            }
        }
        return list.
                stream().
                map(this::contractCustomerToContractCustomerDto).
                toList();
    }

}
