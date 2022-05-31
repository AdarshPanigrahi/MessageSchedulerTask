package com.app.MessageScheduler.controller;


import com.app.MessageScheduler.entity.Client;
import com.app.MessageScheduler.entity.Request;
import com.app.MessageScheduler.entity.Response;
import com.app.MessageScheduler.errorhandler.SqlErrorException;
import com.app.MessageScheduler.util.AuthorizationUtil;
import com.app.MessageScheduler.util.MessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
 class MessageControllerTest {

    @MockBean
    MessageUtil messageUtil;

    @MockBean
    AuthorizationUtil authorizationUtil;

    @Autowired
    private MockMvc mvc;


    public static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void authenticationTest() throws Exception{
        Request request = new Request("test message", "8455997196", "2022-05-30T20:20:20");
        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/message").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);


        assertThat(response.getResponseCode()).isEqualTo(1001);
    }

    @Test
    void authenticationTestForInvalidToken() throws Exception {
        Request request = new Request("test message", "8455997196", "2022-05-30T20:20:20");
        String jsonString = objectMapper.writeValueAsString(request);
        when(authorizationUtil.validateToken(any())).thenReturn(null);
        MvcResult result = mvc.perform(post("/schedule/message").header("token", "Invalid token").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);
        System.out.println(response.toString());

        assertThat(response.getResponseCode()).isEqualTo(1001);
    }


    @Test
    void validationTestForPhoneNumber() throws Exception {
        Client dummyclient = new Client(101, "dummy", "dummytoken");
        when(authorizationUtil.validateToken("dummytoken")).thenReturn(dummyclient);
        //phone number is missing....
        Request request = new Request("test message", "", "2022-05-30T20:20:20");
        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/message").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "dummytoken").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);

        assertThat(response.getResponseCode()).isEqualTo(1002);
        assertThat(response.getResponseMessage() == "phone number is invalid");
    }

    @Test
    void validationTestForScheduledTime() throws Exception {
        Client dummyclient = new Client(101, "dummy", "dummytoken");
        when(authorizationUtil.validateToken("dummytoken")).thenReturn(dummyclient);

        Request request = new Request("test message", "8455997196", "2022-05-30Invalid");
        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/message").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "dummytoken").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);


        assertThat(response.getResponseCode()).isEqualTo(1002);
        assertThat(response.getResponseMessage() == "Invalid date format");
    }

    @Test
    void validationTestForMessage() throws Exception {
        Client dummyclient = new Client(101, "dummy", "dummytoken");
        when(authorizationUtil.validateToken("dummytoken")).thenReturn(dummyclient);

        Request request = new Request("test message", "8455997196", "2022-05-30Invalid");
        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/message").contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "dummytoken").content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);


        assertThat(response.getResponseCode()).isEqualTo(1002);
        assertThat(response.getResponseMessage() == "message should not be empty");
    }

    @Test
    void scheduleMessageTest() throws Exception {
        Client dummyclient = new Client(10178, "dummy", "dummytoken");
        Request request = new Request("test message", "8455997196", "2022-05-30T20:20:20");
        when(messageUtil.saveMessage(any(), any())).thenReturn(1);
        when(authorizationUtil.validateToken("dummytoken")).thenReturn(dummyclient);

        String jsonString = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/schedule/message").header("token", "dummytoken").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);

        assertThat(response.getResponseCode()).isEqualTo(1000);

    }

    @Test
    void scheduleMessageTestForSQLException() throws Exception {
        Client dummyclient = new Client(101, "dummy", "dummytoken");
        Request request = new Request("test message", "8455997196", "2022-05-30T20:20:20");

        when(messageUtil.saveMessage(any(), any())).thenThrow(new SqlErrorException("sql error while inserting message"));
        when(authorizationUtil.validateToken("dummytoken")).thenReturn(dummyclient);

        String jsonString = objectMapper.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/schedule/message").header("token", "dummytoken").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();


        String actualResponseString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(actualResponseString, Response.class);
        System.out.println(response.toString());

        assertThat(response.getResponseCode()).isEqualTo(1003);
    }
}
