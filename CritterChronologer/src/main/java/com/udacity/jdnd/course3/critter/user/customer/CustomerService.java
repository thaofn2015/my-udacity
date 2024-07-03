package com.udacity.jdnd.course3.critter.user.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }
}
