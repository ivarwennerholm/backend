package org.example.backend;

import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Repository.RoomTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository cRepo, RoomTypeRepository rtRepo, RoomRepository rRepo, BookingRepository bRepo) {
        return (args) -> {
            // Customers
            Customer c1 = new Customer("Venus", "111-1111111");
            Customer c2 = new Customer("Alex", "222-2222222");
            Customer c3 = new Customer("Ivar", "333-3333333");
            cRepo.save(c1);
            cRepo.save(c2);
            cRepo.save(c3);

            // Room Types
            RoomType rt1 = new RoomType(1L, "single", 0, 1,500);
            RoomType rt2 = new RoomType(2L, "double", 1, 3,1000);
            RoomType rt3 = new RoomType(3L, "large_double", 2, 4,1500);
            rtRepo.save(rt1);
            rtRepo.save(rt2);
            rtRepo.save(rt3);

            // Rooms
            Room r1 = new Room(2099, rt1);
            Room r2 = new Room( 3049, rt2);
            Room r3 = new Room(3540, rt3);
            rRepo.save(r1);
            rRepo.save(r2);
            rRepo.save(r3);

            // Bookings
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Booking b1 = new Booking(new java.sql.Date(df.parse("2024-06-01").getTime()),
                    new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, c1, r1);
            Booking b2 = new Booking(new java.sql.Date(df.parse("2024-08-22").getTime()),
                    new java.sql.Date(df.parse("2024-08-23").getTime()), 3, 1, c2, r2);
            Booking b3 = new Booking(new java.sql.Date(df.parse("2024-12-23").getTime()),
                    new java.sql.Date(df.parse("2024-12-30").getTime()), 4, 2, c3, r3);
            bRepo.save(b1);
            bRepo.save(b2);
            bRepo.save(b3);

        };
    }

}