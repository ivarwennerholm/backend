package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.example.backend.Service.ShipperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository repo;

    @Override
    public List<Shipper> getAllShippers() {
        return repo.findAll();
    }
}
