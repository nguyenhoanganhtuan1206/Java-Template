package com.javatemplate.api.book;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class AbstractControllerTest {

    public static ResultActions performGetRequest(final MockMvc mvc, final String url) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON));
    }

    public static ResultActions performPutRequest(final MockMvc mvc, final String url, final String requestBody) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }

    public static ResultActions performPostRequest(final MockMvc mvc, final String url, final String requestBody) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
    }
}
