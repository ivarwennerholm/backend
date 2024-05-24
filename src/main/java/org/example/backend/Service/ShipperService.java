package org.example.backend.Service;

import org.example.backend.DTO.ShipperDto;
import org.example.backend.Model.Shipper;

import java.util.List;

public interface ShipperService {
    public List<Shipper> getAllShippers();
}
