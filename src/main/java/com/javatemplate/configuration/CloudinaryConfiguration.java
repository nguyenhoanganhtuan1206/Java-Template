package com.javatemplate.configuration;

import com.cloudinary.Cloudinary;
import com.javatemplate.properties.CloudinaryProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class CloudinaryConfiguration {

    private final CloudinaryProperties cloudinaryProperties;

    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudinaryProperties.getName());
        config.put("api_key", cloudinaryProperties.getKey());
        config.put("api_secret", cloudinaryProperties.getSecret());
        return new Cloudinary(config);
    }
}
