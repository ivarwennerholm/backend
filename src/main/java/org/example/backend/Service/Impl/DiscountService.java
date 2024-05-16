package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Repository.BookingRepository;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Repository.RoomRepository;
import org.example.backend.Service.BookingService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {

//    private final BookingService bookingService;
    private DateService dateService = new DateService();
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\033[0;33m";

    public double getTotalPriceWithDiscounts(Date checkin, Date checkout, long roomId, long customerId, Date dateForTesting, boolean test) {
        long nights = dateService.getNumberOfDaysBetweenTwoDates(checkin, checkout);
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        Room room = null;
        if (optionalRoom.isPresent())
            room = optionalRoom.get();
        double pricePerNight = room.getRoomType().getPricePerNight();
        double totalPrice = nights * pricePerNight;
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = null;
        if (optionalCustomer.isPresent())
            customer = optionalCustomer.get();
        System.out.println("DiscountService: Discount 1 = " + getDiscountSundayMonday(checkin, checkout, room)); // TODO: REMOVE
        totalPrice -= getDiscountSundayMonday(checkin, checkout, room);
        System.out.println("DiscountService: Discount 2 = " + getDiscountTwoOrMoreNights(checkin, checkout, totalPrice)); // TODO: REMOVE
        totalPrice -= getDiscountTwoOrMoreNights(checkin, checkout, totalPrice);
        if (test) {
            System.out.println("DiscountService: Discount 3 = " + getDiscountReturningCustomer(customer, totalPrice, dateForTesting, true)); // TODO: REMOVE
            totalPrice -= getDiscountReturningCustomer(customer, totalPrice, dateForTesting, true);
        } else {
            System.out.println("DiscountService: Discount 3 = " + getDiscountReturningCustomer(customer, totalPrice, null, false)); // TODO: REMOVE
            totalPrice -= getDiscountReturningCustomer(customer, totalPrice, null, false);
        }
        System.out.println("DiscountService: Grand total = " + totalPrice); // TODO: REMOVE
        return totalPrice;
    }

    public double getDiscountSundayMonday(Date checkin, Date checkout, Room room) {
        int numberOfDiscountedNights = getNumberOfDiscountedNights(checkin, checkout);
        return room.getRoomType().getPricePerNight() * numberOfDiscountedNights * 0.02;
    }

    public double getDiscountTwoOrMoreNights(Date checkin, Date checkout, double totalPrice) {
        if (dateService.getNumberOfDaysBetweenTwoDates(checkin, checkout) >= 2) {
            return totalPrice * 0.005;
        } else {
            return 0;
        }
    }

    public double getDiscountReturningCustomer(Customer customer, double totalPrice, Date dateForTesting, boolean test) {
        boolean check;
        if (test)
            check = doesCustomerHaveTenOrMoreNightsBookedInTheLastYear(customer, dateForTesting, true);
        else
            check = doesCustomerHaveTenOrMoreNightsBookedInTheLastYear(customer, null, false);
        if (check) {
            return totalPrice * 0.02;
        } else {
            return 0;
        }
    }

    public int getNumberOfDiscountedNights(Date checkin, Date checkout) {
        List<Date> interval = dateService.createDateInterval(checkin, checkout);
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        int numberOfDiscountedNights = 0;
        for (int i = 0; i < (interval.size() - 1); i++) {
            today.setTime(interval.get(i));
            tomorrow.setTime(interval.get(i + 1));
            if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && tomorrow.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                numberOfDiscountedNights++;
            }
        }
        return numberOfDiscountedNights;
    }

    public boolean doesCustomerHaveTenOrMoreNightsBookedInTheLastYear(Customer customer, Date dateForTesting, boolean test) {
        List<Booking> allBookingsForCustomer = bookingRepository.getAllBookingsForCustomer(customer.getId());
        List<List<Date>> allDatesBookedWithinTheLastYear;
        allDatesBookedWithinTheLastYear = allBookingsForCustomer.
                stream().
                map(booking -> dateService.createDateInterval(booking.getCheckinDate(), booking.getCheckoutDate())).
                map(datelist -> {
                    if (test) {
                        return datelist.
                                stream().
                                filter(date -> dateService.isDateWithinAYearFromToday(date, dateForTesting, true)).
                                toList();
                    } else {
                        return datelist.
                                stream().
                                filter(date -> dateService.isDateWithinAYearFromToday(date, null, false)).
                                toList();
                    }
                }).
                filter(datelist -> !datelist.isEmpty()).
                toList();
        long counter = allDatesBookedWithinTheLastYear.
                stream().
                mapToLong(datelist -> dateService.getNumberOfDaysBetweenTwoDates(datelist.get(0), datelist.get(datelist.size() - 1))).
                sum();
        return counter >= 10;
    }
}
