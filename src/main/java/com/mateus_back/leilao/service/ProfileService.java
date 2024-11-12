package com.mateus_back.leilao.service;

import com.mateus_back.leilao.model.entities.Profile;
import com.mateus_back.leilao.repository.interfaces.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProfileService {

    @Autowired
    private IProfileRepository iProfileRepository;

    public Profile create(Profile profile) {
        return iProfileRepository.save(profile);
    }

    public Profile update(Profile profile) {
        var savedProfile = iProfileRepository.findById(profile.getId())
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));
        savedProfile.setName(profile.getName());
        return iProfileRepository.save(savedProfile);
    }

    public void delete(Long id) {
        var savedProfile = iProfileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));
        iProfileRepository.deleteById(id);
    }

    public List<Profile> findAll() {
        return iProfileRepository.findAll();
    }
}
