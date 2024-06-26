package org.example.backend.Repository;

import org.example.backend.Events.RoomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomEventRepository extends JpaRepository<RoomEvent,Long> {

    @Modifying
    @Transactional
    @Query("select x from RoomEvent x where x.roomno=:roomnr")
    List<RoomEvent> getRoomEventByRoomNo(@Param("roomnr") int roomnr);
}
