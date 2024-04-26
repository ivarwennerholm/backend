package org.example.backend1.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend1.DTO.RoomTypeDto;
import org.example.backend1.Model.RoomType;
import org.example.backend1.Repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepository repo;

    public RoomTypeDto roomTypeToRoomTypeDtoDto(RoomType rt) {
        return RoomTypeDto.builder().id(rt.getId()).type(rt.getType()).maxExtraBed(rt.getMaxExtraBed()).maxPerson(rt.getMaxPerson()).build();
    }
    public RoomType roomTypeDtoToRoomType(RoomTypeDto rtd) {
        return RoomType.builder().id(rtd.getId()).type(rtd.getType()).maxExtraBed(rtd.getMaxExtraBed()).maxPerson(rtd.getMaxPerson()).build();
    }

    public List<RoomTypeDto> getAllRoomTypes() {
            return repo.findAll().stream().map(this::roomTypeToRoomTypeDtoDto).toList();
        }

    public void addRoomType(RoomTypeDto rtd) {
        repo.save(roomTypeDtoToRoomType(rtd));
    }
}
