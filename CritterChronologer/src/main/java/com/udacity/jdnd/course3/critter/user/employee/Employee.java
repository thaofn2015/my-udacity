package com.udacity.jdnd.course3.critter.user.employee;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable = new HashSet<>();

    public Employee() {
    }

    public Employee(Set<DayOfWeek> daysAvailable, Set<EmployeeSkill> skills, String name, Long id) {
        this.daysAvailable = daysAvailable;
        this.skills = skills;
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
