package com.example.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.storage.StorageService;

@Component
public class StorageInitializer implements CommandLineRunner {

    private final StorageService storageService;

    public StorageInitializer(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.init();
    }
} 