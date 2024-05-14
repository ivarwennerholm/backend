package org.example.backend.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Service.BookingService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    final BookingService bookingService;

    // ANSI colors for readability
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public double getTotalPriceWithDiscounts(Date checkin, Date checkout, Room room, Customer customer) {
        long nights = bookingService.getNumberOfDaysBetweenTwoDates(checkin, checkout);
        double pricePerNight = room.getRoomType().getPricePerNight();
        double totalPrice = nights * pricePerNight;
        System.out.println(ANSI_GREEN + "Discount 1: " + getDiscountSundayMonday(checkin, checkout, room) + ANSI_RESET);
        totalPrice -= getDiscountSundayMonday(checkin, checkout, room);
        System.out.println(ANSI_GREEN + "Discount 2: " + getDiscountTwoOrMoreNights(checkin, checkout, totalPrice) + ANSI_RESET);
        totalPrice -= getDiscountTwoOrMoreNights(checkin, checkout, totalPrice);
        return totalPrice;
    }

    public double getDiscountSundayMonday(Date checkin, Date checkout, Room room) {
        int numberOfDiscountedNights = getNumberOfDiscountedNights(checkin, checkout);
        return room.getRoomType().getPricePerNight() * numberOfDiscountedNights * 0.02;
    }

    public double getDiscountTwoOrMoreNights(Date checkin, Date checkout, double totalPrice) {
        if (bookingService.getNumberOfDaysBetweenTwoDates(checkin, checkout) >= 2) {
            return totalPrice * 0.005;
        } else {
            return 0;
        }
    }

    public int getNumberOfDiscountedNights(Date checkin, Date checkout) {
        List<Date> interval = bookingService.createDateInterval(checkin, checkout);
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
}
