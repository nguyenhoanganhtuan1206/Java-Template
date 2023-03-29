package com.javatemplate.api.auth;

import com.javatemplate.api.AbstractControllerTest;
import com.javatemplate.api.WithMockAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
public class HomeControllerTest extends AbstractControllerTest {

    @Test
    @WithMockAdmin
    public void shouldLoginPage_OK() throws Exception {
        get("/")
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"));
    }
}