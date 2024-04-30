package org.example.backend;

import org.example.backend.DTO.CustomerDto;
import org.example.backend.Model.Customer;
import org.example.backend.Repository.CustomerRepository;
import org.example.backend.Service.CustomerService;
import org.example.backend.Service.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceApplicationTest {

//    @Autowired
//    private MockMvc mvc;

    @MockBean
    private CustomerService mockService;

//    @Mock
//    private CustomerRepository mockRepo;
    CustomerDto cDto1;
    CustomerDto cDto2;
    @BeforeEach
    public void init(){
        cDto1 = new CustomerDto(1L, "Venus P", "+071111111");
        cDto2 = new CustomerDto(2L, "Alex B", "+07222222");
        Mockito.when(mockService.getAll()).thenReturn(Arrays.asList(cDto1,cDto2));
    }
    @Test
    void getAllTest(){


//        Customer c1 = new Customer("Venus P","+071111111");
//        Customer c2 = new Customer("Alex B", "+07222222");
//
//        Mockito.when(mockRepo.findAll()).thenReturn(Arrays.asList(c1,c2));


        List<CustomerDto> testList = mockService.getAll();

        Assertions.assertEquals(2,testList.size());

        Assertions.assertTrue(testList.get(0).getId()==1L);
        Assertions.assertTrue(testList.get(0).getName().equals("Venus P"));
        Assertions.assertTrue(testList.get(0).getPhone().equals("+071111111"));

        Assertions.assertTrue(testList.get(1).getId()==2L);
        Assertions.assertTrue(testList.get(1).getName().equals("Alex B"));
        Assertions.assertTrue(testList.get(1).getPhone().equals("+07222222"));
    }
    @Test
    void addCustomerTest(){
        CustomerDto cDto3 = new CustomerDto(3L, "Ivar W", "+07333333");

        Mockito.when(mockService.addCustomer(cDto3)).thenReturn("added new customer Ivar");
        Mockito.when(mockService.getAll()).thenReturn(Arrays.asList(cDto1,cDto2,cDto3));

        Assertions.assertTrue(mockService.addCustomer(cDto3).equals("added new customer Ivar"));
        Assertions.assertEquals(3,mockService.getAll().size());
    }
    @Test
    void deleteCustomerByNameTest(){
        Mockito.when(mockService.deleteCustomerByName("Venus P")).thenReturn("delete customer Venus P");
        Mockito.when(mockService.deleteCustomerByName("Alex B")).thenReturn("delete customer Alex B");

        Assertions.assertTrue(mockService.deleteCustomerByName("Venus P").equals("delete customer Venus P"));
        Assertions.assertFalse(mockService.deleteCustomerByName("Alex B").equals("delete customer Venus P"));
    }

//    @Override
//    public CustomerDto findCustomerById(Long id) {
//        return customerToCustomerDto(customerRepo.findById(id).get());
//    }



//    @Override
//    public String updateCustomer(Long id, String name, String phone) {
//        Customer c = customerRepo.findById(id).get();
//        if (!name.isEmpty()){
//            c.setName(name);
//        }
//        if (!phone.isEmpty()){
//            c.setPhone(phone);
//        }
//        customerRepo.save(c);
//        return "update customer";
//    }
}
