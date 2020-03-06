package com.nthomas.springbootwithcucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    @When("the Client requests {} from {}")
    public void when_client_gets(String type, String endpoint) throws Exception {
        resultActions = mockMvc.perform(request(type, URI.create(endpoint)));
    }

    @When("the Client gets {}")
    public void when_client_gets(String endpoint) throws Exception {
        resultActions = mockMvc.perform(get(endpoint));
    }

    @Then("the Client receives status code of {}")
    public void then_status_code(Integer status) throws Exception {
        resultActions.andExpect(status().is(status));
    }

    @Then("the Client receives body parameter {} of {}")
    public void then_body_parameter(String parameter, Object value) throws Exception {
        resultActions.andExpect(jsonPath(parameter, is(value)));
    }

    @Then("the Client receives a body like")
    public void then_body_json(String body) throws Exception {
        resultActions.andExpect(content().json(body));
    }
}
