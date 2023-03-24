package com.javatemplate.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
public abstract class AbstractControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mvc;

    protected ResultActions get(final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions post(final String url, final Object object) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(url)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(object)));
    }

    protected ResultActions put(final String url, final Object object) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(url)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(object)));
    }

    protected ResultActions delete(final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.delete(url)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));
    }
}
