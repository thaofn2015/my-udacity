package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(long id) {
        return petRepository.findById(id).orElseThrow();
    }

    public Pet save(Pet pet){
        Pet rsPet = petRepository.save(pet);
        Customer customer = rsPet.getCustomer();

        List<Pet> pets = customer.getPets();

        if (!pets.contains(rsPet)) {
            pets.add(rsPet);
            customer.setPets(pets);
        }
        return rsPet;
    }
}
