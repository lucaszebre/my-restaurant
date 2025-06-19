package com.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.storage.StorageProperties;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig {
} 