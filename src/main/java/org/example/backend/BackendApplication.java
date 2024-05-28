package org.example.backend;

import org.example.backend.Model.*;
import org.example.backend.Repository.*;
import org.example.backend.Service.Impl.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@SpringBootApplication
public class BackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication app = new SpringApplication(BackendApplication.class);
            logActiveProfiles(app);
            app.run(args);
        } else if (Objects.equals(args[0], "fetchshippingcontractors")) {
            SpringApplication app = new SpringApplication(FetchShippingContractors.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            logActiveProfiles(app);
            app.run(args);
        } else if (Objects.equals(args[0], "fetchcontractcustomers")) {
            SpringApplication app = new SpringApplication(FetchContractCustomers.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            logActiveProfiles(app);
            app.run(args);
        } else if (Objects.equals(args[0], "readevents")) {
            SpringApplication app = new SpringApplication(ReadEventsApp.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            logActiveProfiles(app);
            app.run(args);
        } else if (Objects.equals(args[0], "resetemailtemplate")) {
            SpringApplication app = new SpringApplication(ResetEmailTemplate.class);
            app.setWebApplicationType(WebApplicationType.NONE);
            logActiveProfiles(app);
            app.run(args);
        }
    }

    static void logActiveProfiles(SpringApplication app) {
        app.setBanner((environment, sourceClass, out) -> {
            String[] activeProfiles = environment.getActiveProfiles();
            logger.info(ANSI_PURPLE + "Active Profiles: " + String.join(", ", activeProfiles) + ANSI_RESET);
        });
    }

    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository cRepo, RoomTypeRepository rtRepo, RoomRepository rRepo, BookingRepository bRepo, DiscountService discountService, CustomerRepository customerRepository, BookingRepository bookingRepository, EmailTemplateRepository etRepo) {
        return (args) -> {
            // Delete all
            cRepo.deleteAll();
            bRepo.deleteAll();
            rRepo.deleteAll();
            rtRepo.deleteAll();

            // Customers
            Customer c1 = new Customer("Venus", "111-1111111", "venus@pear.com");
            Customer c2 = new Customer("Alex", "222-2222222", "alex@apple.com");
            Customer c3 = new Customer("Ivar", "333-3333333", "ivar@orange.com");
            cRepo.save(c1);
            cRepo.save(c2);
            cRepo.save(c3);

            // Room Types
            RoomType rt1 = new RoomType(1L, "Single", 0, 1,500);
            RoomType rt2 = new RoomType(2L, "Double", 1, 3,1000);
            RoomType rt3 = new RoomType(3L, "Large double", 2, 4,1500);
            rtRepo.save(rt1);
            rtRepo.save(rt2);
            rtRepo.save(rt3);

            // Rooms
            Room r1 = new Room(101, rt1);
            Room r2 = new Room(102, rt2);
            Room r3 = new Room(103, rt3);
            rRepo.save(r1);
            rRepo.save(r2);
            rRepo.save(r3);

            // Bookings
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            double totalPrice = discountService.getTotalPriceWithDiscounts(new java.sql.Date(df.parse("2024-06-01").getTime()),
                    new java.sql.Date(df.parse("2024-06-07").getTime()), r1.getId(), c1.getId(), null, false);
            Booking b1 = new Booking(new java.sql.Date(df.parse("2024-06-01").getTime()),
                    new java.sql.Date(df.parse("2024-06-07").getTime()), 1, 0, totalPrice, c1, r1);
            totalPrice = discountService.getTotalPriceWithDiscounts(new java.sql.Date(df.parse("2024-08-22").getTime()),
                    new java.sql.Date(df.parse("2024-08-23").getTime()), r2.getId(), c2.getId(), null, false);
            Booking b2 = new Booking(new java.sql.Date(df.parse("2024-08-22").getTime()),
                    new java.sql.Date(df.parse("2024-08-23").getTime()), 3, 1, 1299, c2, r2);
            totalPrice = discountService.getTotalPriceWithDiscounts(new java.sql.Date(df.parse("2024-12-2").getTime()),
                    new java.sql.Date(df.parse("2024-12-25").getTime()), r3.getId(), c3.getId(), null, false);
            Booking b3 = new Booking(new java.sql.Date(df.parse("2024-12-23").getTime()),
                    new java.sql.Date(df.parse("2024-12-25").getTime()), 4, 2, 16300, c3, r3);
            totalPrice = discountService.getTotalPriceWithDiscounts(new java.sql.Date(df.parse("2024-12-23").getTime()),
                    new java.sql.Date(df.parse("2024-12-30").getTime()), r2.getId(), c2.getId(), null, false);
            Booking b4 = new Booking(new java.sql.Date(df.parse("2024-12-23").getTime()),
                    new java.sql.Date(df.parse("2024-12-30").getTime()), 3, 1, 7599, c2, r2);
            bRepo.save(b1);
            bRepo.save(b2);
            bRepo.save(b3);
            bRepo.save(b4);
        };
    }

}