package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PetService petService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = dtoToSchedule(scheduleDTO);
        schedule = scheduleService.save(schedule);
        return scheduleToDTO(schedule);

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        return convertToListScheduleDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.findSchedulesByPetsId(petId);
        return convertToListScheduleDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.findByEmployeesId(employeeId);
        return convertToListScheduleDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        if(customer.getPets() != null){
            for(Pet pet : customer.getPets()){
                List<Schedule> schedulesByPetsId = scheduleService.findSchedulesByPetsId(pet.getId());
                scheduleDTOs.addAll(convertToListScheduleDTO(schedulesByPetsId));
            }
        }
        return scheduleDTOs;
    }

    private List<ScheduleDTO> convertToListScheduleDTO(List<Schedule> scheduleLst) {
        List<ScheduleDTO> lstScheduleDTO = new ArrayList<>();
        scheduleLst.forEach(schedule -> lstScheduleDTO.add(scheduleToDTO(schedule)));
        return lstScheduleDTO;
    }

    private ScheduleDTO scheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());

        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    private Schedule dtoToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule  = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = scheduleDTO.getPetIds().stream().map(x -> petService.findById(x)).collect(Collectors.toList());
        List<Employee> employees = scheduleDTO.getEmployeeIds().stream().map(x -> employeeService.findById(x)).collect(Collectors.toList());

        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return schedule;
    }
}
