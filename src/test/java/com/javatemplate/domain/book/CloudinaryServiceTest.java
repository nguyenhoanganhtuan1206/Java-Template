package com.javatemplate.domain.book;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @Test
    void shouldUploadImage_Successfully() throws IOException {
        final var bytes = "abc".getBytes();

        final var cloudinaryResponse = Map.of("secure_url", "https://res.cloudinary.com/test/image/upload/test.jpg");
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResponse);

        final var result = cloudinaryService.upload(bytes);

        assertEquals(cloudinaryResponse.get("secure_url"), result);
    }
}
