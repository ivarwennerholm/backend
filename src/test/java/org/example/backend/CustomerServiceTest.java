package org.example.backend;

import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Booking;
import org.example.backend.Model.Customer;
import org.example.backend.Model.Room;
import org.example.backend.Model.RoomType;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Service.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository cusRepo;

    @InjectMocks
    private CustomerServiceImpl cusService;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void customerDtoToCustomerTest(){
        CustomerDto cDto = CustomerDto.builder().id(1L).name("Venus P").phone("+0711111").build();

        Customer actual = cusService.customerDtoToCustomer(cDto);

        Assertions.assertTrue(actual.getId()==1L);
        Assertions.assertTrue(actual.getName().equals("Venus P"));
        Assertions.assertTrue(actual.getPhone().equals("+0711111"));
        Assertions.assertTrue(actual.getBookingList()==null);
    }
    @Test
    public void customerToCustomerDtoTest(){

        Customer c = Customer.builder().id(1L).name("Harry").phone("+0711111").bookingList(new ArrayList<>()).build();

        CustomerDto actual = cusService.customerToCustomerDto(c);

        Assertions.assertTrue(actual.getId()==1L);
        Assertions.assertTrue(actual.getName().equals("Harry"));
        Assertions.assertTrue(actual.getPhone().equals("+0711111"));
    }
    @Test
    public void getAllTest(){
        Customer c1 = Customer.builder().id(1L).name("Venus P").phone("+071111111").build();
        Customer c2 = Customer.builder().id(2L).name("Alex B").phone("+07222222").build();
        when(cusRepo.findAll()).thenReturn(Arrays.asList(c1,c2));

        List<CustomerDto> list = cusService.getAll();

        Assertions.assertEquals(2,list.size());

        Assertions.assertTrue(list.get(0).getId()==1L);
        Assertions.assertTrue(list.get(0).getName().equals("Venus P"));
        Assertions.assertTrue(list.get(0).getPhone().equals("+071111111"));

        Assertions.assertTrue(list.get(1).getId()==2L);
        Assertions.assertTrue(list.get(1).getName().equals("Alex B"));
        Assertions.assertTrue(list.get(1).getPhone().equals("+07222222"));
    }

    @Test
    public void addCustomerTest(){
        CustomerDto cDto = CustomerDto.builder().id(1L).name("Venus P").phone("+0711111").build();
        Customer c = Customer.builder().id(1L).name("Venus P").phone("+0711111").bookingList(new ArrayList<>()).build();

        when(cusRepo.save(Mockito.any(Customer.class))).thenReturn(c);

        Assertions.assertTrue(cusService.addCustomer(cDto).equals("added new customer"));
    }

    @Test
    public void deleteCustomerByNameTest(){
        Customer c = Customer.builder().id(1L).name("Venus P").phone("+0711111").bookingList(new ArrayList<>()).build();

        when(cusRepo.findByName("Venus P")).thenReturn(c);
        Assertions.assertTrue(cusService.deleteCustomerByName("Venus P").equals("delete customer Venus P"));
    }

    @Test
    public void deleteCustomerByIdTest() throws ParseException {
        Booking b1 = Booking.builder()
                .id(1L)
                .checkinDate(new java.sql.Date(df.parse("2024-06-01").getTime()))
                .checkoutDate(new java.sql.Date(df.parse("2024-06-07").getTime()))
                .guestAmt(1)
                .extraBedAmt(0)
                .customer(new Customer(1L,"Peter C", "+0722222",null))
                .room(new Room(1L,2099, new RoomType(1L, "single", 0, 1,500)))
                .build();

        Customer c = Customer.builder().id(1L).name("Venus P").phone("+0711111").bookingList(new ArrayList<>()).build();
        Customer c1 = Customer.builder().id(2L).name("Peter C").phone("+0722222").bookingList(new ArrayList<>(Arrays.asList(b1))).build();


        when(cusRepo.findById(1L)).thenReturn(Optional.of(c));
        when(cusRepo.findById(2L)).thenReturn(Optional.of(c1));

        Exception ex = assertThrows(RuntimeException.class,() -> cusService.deleteCustomerById(2L));
        Assertions.assertTrue(cusService.deleteCustomerById(1L).equals("delete customer"));
        Assertions.assertEquals("This customer has existing booking(s)",ex.getMessage());
    }

    @Test
    public void findCustomerByIdTest(){
        Customer c = Customer.builder().id(1L).name("Venus P").phone("+0711111").bookingList(new ArrayList<>()).build();

        when(cusRepo.findById(1L)).thenReturn(Optional.of(c));

        Assertions.assertTrue(cusService.findCustomerById(1L).getId()==1L);
        Assertions.assertTrue(cusService.findCustomerById(1L).getName().equals("Venus P"));
        Assertions.assertTrue(cusService.findCustomerById(1L).getPhone().equals("+0711111"));
    }

    @Test
    public void updateCustomerTest(){
        Customer c = Customer.builder().id(1L).name("Venus P").phone("+0711111").bookingList(new ArrayList<>()).build();

        when(cusRepo.findById(1L)).thenReturn(Optional.of(c));

        Assertions.assertTrue(cusService.updateCustomer(1L,"Peter X","+0722222").equals("update customer Peter X +0722222"));
        Assertions.assertTrue(cusService.updateCustomer(1L,"Mary ABC","").equals("update customer Mary ABC +0722222"));
        Assertions.assertTrue(cusService.updateCustomer(1L,"","+0733333").equals("update customer Mary ABC +0733333"));
    }
}
