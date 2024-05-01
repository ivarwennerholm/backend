package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTO.RoomTypeDto;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.RoomTypeRepository;
import org.example.backend.Service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepository repo;

    @Override
    public RoomTypeDto roomTypeToRoomTypeDto(RoomType rt) {
        return RoomTypeDto.builder().
                id(rt.getId()).
                type(rt.getType()).
                maxExtraBed(rt.getMaxExtraBed()).
                maxPerson(rt.getMaxPerson()).
                pricePerNight(rt.getPricePerNight()).
                build();
    }

    @Override
    public RoomType roomTypeDtoToRoomType(RoomTypeDto rtd) {
        return RoomType.builder().
                id(rtd.getId()).
                type(rtd.getType()).
                maxExtraBed(rtd.getMaxExtraBed()).
                maxPerson(rtd.getMaxPerson()).
                pricePerNight(rtd.getPricePerNight()).
                build();
    }

    @Override
    public List<RoomTypeDto> getAll() {
        return repo.findAll().stream().map(this::roomTypeToRoomTypeDto).toList();
    }

    @Override
    public void addRoomType(RoomTypeDto rtd) {
        repo.save(roomTypeDtoToRoomType(rtd));
    }
}
