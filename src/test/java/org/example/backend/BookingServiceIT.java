/*
package org.example.backend;

import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceIT {




    @Test // TODO: Not working
    public void testCreateAndAddBookingToDatabase() throws Exception {
        // Setup
        String checkinStr = "2024-06-01";
        String checkoutStr = "2024-06-07";
        Date checkin = new java.sql.Date(df.parse(checkinStr).getTime());
        Date checkout = new java.sql.Date(df.parse(checkoutStr).getTime());
        int guests = 1;
        int extraBeds = 0;
        Room mockRoom = r1;
        long roomId = mockRoom.getId();
        Customer mockCustomer = c1;
        Optional<Customer> optional;
        Customer customer = null;
        String customerName = mockCustomer.getName();
        String customerPhone = mockCustomer.getPhone();
        String customerEmail = mockCustomer.getEmail();

        // Mock necessary behavior
        when(customerService.getCustomerByNamePhoneAndEmail(customerName, customerPhone, customerEmail)).thenReturn(Optional.of(mockCustomer));
        optional = customerService.getCustomerByNamePhoneAndEmail(customerName, customerPhone, customerEmail);
        if (optional.isPresent())
            customer = optional.get();
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(mockRoom));

        bookingRepository.deleteAll();
        // Execution
        sut.createAndAddBookingToDatabase(checkin, checkout, guests, extraBeds, roomId, customerName, customerPhone, customerEmail);

        verify(bookingRepository, times(1)).save(any());
        List<Booking> list = bookingRepository.findAll().stream().toList();
        for (Booking booking : list) {
            System.out.println(booking);
        }
        assertEquals(1, (long) bookingRepository.findAll().size());
        //verify(bookingRepository.times(3).).save

        // Verification
//         verify(customerService).getCustomerByNamePhoneAndEmail(customerName, customerPhone, customerEmail);
        // verify(roomRepository).findById(roomId);
        // verify(bookingRepository).save(any(Booking.class)); // Ensure that the save method is called with a Booking object

        // Date checkin = new java.sql.Date(df.parse("2024-06-01").getTime());
        // Date checkout = new java.sql.Date(df.parse("2024-06-07").getTime());
        // int guestAmt = 1;
        // int extraBedAmt = 0;
        // Room room = r1;
        // Long roomId = room.getId();
        // Customer customer = c1;
        // String name = customer.getName();
        // String phone = customer.getPhone();
        // Booking booking = new Booking(checkin, checkout, guestAmt, extraBedAmt, customer, room);
    }
}
*/
