//package org.example.backend.Events;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//@Entity
//@Data
//@RequiredArgsConstructor
//public class RoomEvent2 {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "log_id")
//    private RoomEvent roomlog;
//
//    public RoomEvent2(RoomEvent roomlog){
//        this.roomlog = roomlog;
//    }
//}
