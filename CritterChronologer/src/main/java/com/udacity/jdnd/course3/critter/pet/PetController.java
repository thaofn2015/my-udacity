package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = dtoToPet(petDTO);

        return petTodto(petService.save(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petTodto(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.findAll().stream().map(x -> petTodto(x)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.findById(ownerId);
        List<Pet> pets = customer.getPets();

        return customer.getPets().stream().map(x -> petTodto(x)).collect(Collectors.toList());
    }

    private Pet dtoToPet(PetDTO petDTO) {
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.findById(petDTO.getOwnerId()));

        return pet;
    }

    private PetDTO petTodto(Pet pet) {
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }
}

