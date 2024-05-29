package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Model.Shipper;
import org.example.backend.Repository.ShipperRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository repo;

    public List<Shipper> getAllShippers() {
        return repo.findAll();
    }
}
