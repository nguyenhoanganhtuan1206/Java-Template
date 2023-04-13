package com.javatemplate.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@ActiveProfiles("TEST")
public abstract class AbstractControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mvc;

    protected ResultActions get(final String url) throws Exception {
        return perform(MockMvcRequestBuilders.get(url));
    }

    protected ResultActions post(final String url, final Object requestBody) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder;

        if (requestBody instanceof MultipartFile) {
            requestBuilder = MockMvcRequestBuilders.multipart(url).file((MockMultipartFile) requestBody);
        } else {
            final String jsonBody = objectMapper.writeValueAsString(requestBody);
            requestBuilder = MockMvcRequestBuilders.post(url).content(jsonBody);
        }

        return perform(requestBuilder.with(csrf()));
    }

    protected ResultActions put(final String url, final Object object) throws Exception {
        return perform(MockMvcRequestBuilders.put(url)
                .with(csrf())
                .content(objectMapper.writeValueAsString(object)));
    }

    protected ResultActions delete(final String url) throws Exception {
        return perform(MockMvcRequestBuilders.delete(url).with(csrf()));
    }

    private ResultActions perform(final MockHttpServletRequestBuilder mockHttpServletRequestBuilder) throws Exception {
        return mvc.perform(mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON));
    }

}
