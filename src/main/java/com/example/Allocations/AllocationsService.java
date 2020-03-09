package com.example.Allocations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocationsService {

    @Autowired
    private AllocationsRepository allocationsRepository;

    public List<Allocations> findAll() {
        return allocationsRepository.findAll();
    }



}
