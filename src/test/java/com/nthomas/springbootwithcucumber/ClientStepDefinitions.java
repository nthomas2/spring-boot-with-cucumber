package com.nthomas.springbootwithcucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    @When("the Client gets {}")
    public void when_client_gets(String endpoint) throws Exception {
        resultActions = mockMvc.perform(get(endpoint));
    }

    @When("the Client puts {} with a body like")
    public void when_client_puts(String endpoint, String body) throws Exception {
        resultActions = mockMvc.perform(put(endpoint).contentType(MediaType.APPLICATION_JSON).content(body));
    }

    @When("the Client posts {} with a body like")
    public void when_client_posts(String endpoint, String body) throws Exception {
        resultActions = mockMvc.perform(post(endpoint).contentType(MediaType.APPLICATION_JSON).content(body));
    }

    @When("the Client deletes {} with a body like")
    public void when_client_deletes(String endpoint, String body) throws Exception {
        resultActions = mockMvc.perform(delete(endpoint).contentType(MediaType.APPLICATION_JSON).content(body));
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

    @Then("the Client receives an error of {}")
    public void then_error_message(String errorMessage) throws Exception {
        resultActions.andExpect((mvcResult) -> {
            assertThat(mvcResult.getResolvedException(), is(notNullValue()));
            assertThat(mvcResult.getResolvedException(), is(instanceOf(ResponseStatusException.class)));
            ResponseStatusException e = (ResponseStatusException) mvcResult.getResolvedException();
            assertThat(e.getReason(), is(equalTo(errorMessage)));
        });
    }

}
