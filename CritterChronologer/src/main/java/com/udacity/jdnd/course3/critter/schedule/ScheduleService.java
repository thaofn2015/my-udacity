package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(long id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public Schedule save(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    List<Schedule> findByEmployeesId(Long employeeId) {
        return scheduleRepository.findSchedulesByEmployeesId(employeeId);
    }

    List<Schedule> findSchedulesByPetsId(Long petId) {
        return scheduleRepository.findSchedulesByPetsId(petId);
    }
}
