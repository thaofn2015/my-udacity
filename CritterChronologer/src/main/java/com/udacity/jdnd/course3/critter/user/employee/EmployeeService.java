package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable, Long id) {
        Employee employee = findById(id);
        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);
    }

    public List<Employee> findByDaysAvailable(DayOfWeek daysAvailable) {
        return employeeRepository.findByDaysAvailable(daysAvailable);
    }

}
