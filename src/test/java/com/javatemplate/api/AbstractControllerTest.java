package com.javatemplate.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public ResultActions get(final String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions put(final String url, final Object object) throws Exception {
        final String requestBody = mapper.writeValueAsString(object);

        return perform(MockMvcRequestBuilders.put(url).content(requestBody));
    }

    public ResultActions post(final String url, final Object object) throws Exception {
        final String requestBody = mapper.writeValueAsString(object);

        return perform(MockMvcRequestBuilders.put(url).content(requestBody));
    }

    public ResultActions delete(final String url) throws Exception {
        return perform(MockMvcRequestBuilders.delete(url));
    }

    private ResultActions perform(final MockHttpServletRequestBuilder mockHttpServletRequestBuilder) throws Exception {
        return mockMvc.perform(mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON));
    }
}
