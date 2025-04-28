package ru.otus.hw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractSecurityTest {

    @Autowired
    protected MockMvc mvc;

    protected void checkRequest(HttpMethod method,
                                String url,
                                String user,
                                String request,
                                Integer statusCode,
                                String redirectTo) throws Exception {
        checkRequest(method, url, user, request, statusCode, redirectTo, null, null, null);
    }

    protected void checkRequest(HttpMethod method,
                                String url,
                                String user,
                                String request,
                                Integer statusCode,
                                String redirectTo,
                                String view,
                                String model,
                                Object modelValue) throws Exception {
        MockHttpServletRequestBuilder builder = prepareBuilder(method, url, user, request);
        ResultActions result = mvc.perform(builder)
                .andExpect(status().is(statusCode));
        if (user == null) {
            result.andExpect(redirectedUrlPattern("**/login"));
        } else if (redirectTo != null) {
            result.andExpect(redirectedUrl(redirectTo));
        }
        if (view != null) {
            result.andExpect(view().name(view));
        }
        if (model != null) {
            result.andExpect(model().attribute(model, modelValue));
        }
    }

    private MockHttpServletRequestBuilder prepareBuilder(HttpMethod method, String url, String user, String request) {
        MockHttpServletRequestBuilder builder = request(method, url);
        if (user != null) {
            builder.with(user(user));
        }
        if (request != null) {
            builder.contentType("application/x-www-form-urlencoded");
            builder.content(request);
        }
        return builder;
    }
}
