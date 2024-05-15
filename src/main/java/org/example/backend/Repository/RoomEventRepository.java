package org.example.backend.Repository;

import org.example.backend.Events.Log;
import org.example.backend.Events.RoomEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomEventRepository extends JpaRepository<RoomEvent,Long> {
}
