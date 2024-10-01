package com.mateus_back.leilao.controller;

import com.mateus_back.leilao.model.Profile;
import com.mateus_back.leilao.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public Profile create(@RequestBody Profile profile) {
        return profileService.create(profile);
    }

    @PutMapping
    public Profile update(@RequestBody Profile profile) {
        return profileService.update(profile);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
         profileService.delete(id);
    }

    @GetMapping
    public List<Profile> findAll() {
        return profileService.findAll();
    }
}
