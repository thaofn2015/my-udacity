package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = dtoToCustomer(customerDTO);
        return customerToDTO(customerService.save(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getCustomers(){
        List<Customer> customers = customerService.findAll();
        return customers.stream().map(x -> customerToDTO(x)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findById(petId);
        return customerToDTO(pet.getCustomer());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = dtoToEmployee(employeeDTO);
        return employeeToDTO(employeeService.save(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeToDTO(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setDaysAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        DayOfWeek daysAvailable = employeeRequestDTO.getDate().getDayOfWeek();

        Set<EmployeeSkill> employeeSkills = employeeRequestDTO.getSkills();
        List<Employee> employees = employeeService.findByDaysAvailable(daysAvailable);

        for(Employee employee : employees){
            if(employee.getSkills().containsAll(employeeSkills)){
                employeeDTOs.add(employeeToDTO(employee));
            }
        }

        return employeeDTOs;
    }

    public CustomerDTO customerToDTO(Customer customer) {
        List<Long> petIds = null;
        if (CollectionUtils.isEmpty(customer.getPets())) {
            petIds = Collections.emptyList();
        } else {
            petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        }

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    public Customer dtoToCustomer(CustomerDTO customerDTO) {
        List<Pet> pets = null;
        if (CollectionUtils.isEmpty(customerDTO.getPetIds())) {
            pets = Collections.emptyList();
        } else {
            pets = customerDTO.getPetIds().stream().map(x -> petService.findById(x)).collect(Collectors.toList());
        }

        Customer customer  = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPets(pets);

        return customer;
    }

    public EmployeeDTO employeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public Employee dtoToEmployee(EmployeeDTO employeeDTO) {
        Employee employee  = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
